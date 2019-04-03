package com.kh.spring.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.UnsupportedCharsetException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.model.vo.Member;
import com.kh.spring.service.MemberService;


/*	HTTPSession을 이용할거면 Annotation에 주석달고  
 * session.setAttribute("loggedMember", result);을 주석을 풀고
 * model.addAttribute("loggedMember", result);에 주석을 달아
 * @SessionAttributes("loggedMember")*/
@SessionAttributes(value= {"loggedMember"})
@Controller
public class MemberController {

	//log를 찍기 위해서는 logger라는 객체가 반드시 필요
	private Logger logger=Logger.getLogger(MemberController.class);
	
	
	//slf4j를 사용한것 이친구는 string이라서 logger.debug(""+m); 이런식으로 써줘야함
//	private Logger logger=LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService service;

	//암호화하기
	@Autowired
	private BCryptPasswordEncoder bcEncoder;

	@RequestMapping("/member/memberEnroll.do")
	public String memberEnroll() {
		/*List<Member> list =service.insertMember();
		model.addAttribute("list", list);*/
		return "/member/memberEnroll";
	}


	@RequestMapping("/member/memberEnrollEnd.do")
	public String memberEnrollEnd(Member m, Model model) {
		//암호화
		String rawPw=m.getPassword();
		logger.debug("받은 pw : "+rawPw);

		String enPw=bcEncoder.encode(rawPw);
		logger.debug("인코딩된 pw : "+enPw);

		m.setPassword(enPw);
		//위에껄 한줄로 쓰게되면 m.setPassword(beEncoder.encode(rawPw));

		int result=service.insertMember(m);
		String msg="";
		String loc="/";

		if(result>0) {
			msg="회원가입 완료";
		}
		else {
			msg="회원가입 실패";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("loc", loc);
		return "common/msg";
	}


	@RequestMapping("/member/memberLogin.do")
	public String login(Member m, Model model, HttpSession session) {
		
		logger.debug(m);
		logger.debug(session);
		
		Member result=service.selectOne(m);
		String msg="";
		String loc="";
		
		try {
			throw new RuntimeException("경고! 일부러 발생! ");
		}
		catch(RuntimeException e) {
			//logger.error("로그인 에러 : "+e.getMessage()+" : "+e.getStackTrace());
		}

	//	String enPw=bcEncoder.encode(m.getPassword());

		if(result!=null) {
			// 암호화 과정에 salt가 임의의 난수이기 때문에(난수가 계속 바뀌기 때문에) 따로 비교해주는 matches method를 써야함
			//enPw.equals(result.getPassword()) 이게 아니고 

			if(bcEncoder.matches(m.getPassword(), result.getPassword())) {
				msg="로그인 성공";
				/*
				 session.setAttribute("loggedMember", result);
				 session말고 model에 넣어볼게
				 annotation SessionAttributes라는게 있어 얘는 class에 선언을 해줘야되 
				 */
				model.addAttribute("loggedMember", result);
			}
			else {
				msg="password 불일치";
			}
		}
		else {
			msg="ID 존재 X";
		}

		model.addAttribute("msg", msg);
		model.addAttribute("loc", loc);
		//메인화면으로 보내주면 됨
		return "common/msg";
	}


	@RequestMapping("/member/LogOut.do")
	public String logOut(SessionStatus session, HttpSession session1) {
		
		session1.invalidate();
		//isComplete는 session이 완료됬니? 끝났니? 라고 물어보는 것 
		if(!session.isComplete()) {
			session.setComplete();
			//session이 끊어짐
		}
		//redirect를 언제 어떻게 쓰는건지 공부하자
		return "redirect:/";
	}

	
	//update-수정-강사님
	@RequestMapping("/member/update.do")
	public ModelAndView update(Member m) {
		//selectOne 위에서 로그인할때 만들어놨음 
		Member re=service.selectOne(m);
		logger.debug("re : "+re);
	
		//ModelAndView 
		ModelAndView mv = new ModelAndView();
		
		//아 이건 view구나
		mv.setViewName("member/updateForm");
		
		//얘는 데이터구나 라고 스프링이 인식해서 보냄
		mv.addObject("m", re);
		
		return mv;
		
	}
	
	
	@RequestMapping("/member/updateEnd.do")
	public ModelAndView updateEnd(Member m) {
		logger.info("m : "+m);
		int result=service.update(m);
		String msg="";
		String loc="/member/update.do?userId="+m.getUserId();
		if(result>0) {
			msg="수정완료";
		}
		else {
			msg="수정실패";
		}
		ModelAndView mv=new ModelAndView();
		mv.setViewName("common/msg");
		mv.addObject("msg",msg);
		mv.addObject("loc", loc);
		return mv;
	}
	
	
	
	//ajax활용 id중복체크 방법 1
	/*@RequestMapping("/member/checkId.do")
	public void checkId(String userId, HttpServletResponse response) throws IOException {
		Member m = new Member();
		m.setUserId(userId);
		Member result=service.selectOne(m);
		boolean isOk = result!=null?false:true;
		response.getWriter().println(isOk);
		
	}*/
	
	//ajax활용 id중복체크 방법2. ModelAndView - viewResolver가 받음 (servlet-context에 bean등록한 것 참고)
	@RequestMapping("/member/checkId.do")
	public ModelAndView checkId(String userId) throws UnsupportedEncodingException {
		ModelAndView mv = new ModelAndView();
		Member m = new Member();
		m.setUserId(userId);
		Member result=service.selectOne(m);
		boolean isOk = result!=null?false:true;
		mv.addObject("isOk",isOk);
		mv.addObject("msg", URLEncoder.encode("고마워요~ 감동!","UTF-8"));
		mv.addObject("su",19);
		
		mv.setViewName("jsonView");
		return mv;
		
	}
	
	
	
	
	
	
	
	
	
/*	내가 한 부분
	@RequestMapping("/member/myPage.do")
	public String myPage(SessionStatus session) {
		return "/member/myPage";
	}
	@RequestMapping("/member/deleteMember.do")
	public String deleteMember() {
		int result;
		return ;
	}
*/

}
