package com.kh.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.model.vo.Dev;
import com.kh.spring.model.vo.Member;
import com.kh.spring.service.DomoService;

@Controller
public class DomoController {
	
	@Autowired
	/*@Qualifier("choice")*/
	private DomoService service;
	
	@RequestMapping("/domo/domo.do")
	public String domo() {
		
		//return하는건 결국 view
		return "domo/domo";
		
		/*System.out.println("Controller");
		service.print();*/
	}

	
	
	@RequestMapping("/domo/domo1.do")
	public String domo1(HttpServletRequest re) {
		String devName=re.getParameter("devName");
		int devAge=Integer.parseInt(re.getParameter("devAge"));
		String devEmail=re.getParameter("devEmail");
		String devGender=re.getParameter("devGender");
		String[] devLang=re.getParameterValues("devLang");
		//System.out.println(devName+" : "+devAge+" : "+devEmail+" : "+devGender+" : "+devLang);
		Dev dev = new Dev(devName, devAge, devEmail, devGender, devLang);
		re.setAttribute("dev", dev);
		return "domo/domoResult";
	}
	
	
	@RequestMapping("/domo/domo2.do")
	public String domo2(
			//여기서 value는 Name으로 맵핑된 값을 가져와서 해줌
			@RequestParam(value="Name")	String devName, 
			//속성값 - required="", defaultValue=""
			//required 디폴트가 true : 여기엔 반드 값을 받아야해 / false : 없으면 그냥 무시한다!
			//defaultValue="" 값을 안 받을시 설정해놓은 값으로 나옴
			@RequestParam(value="devAge", defaultValue="100" )int devAge, 
			String devEmail, 
			String devGender, 
			String[] devLang,
			HttpServletRequest re) {
		System.out.println(devName+" : "+devAge+" : "+devEmail+" : "+devGender+" : "+devLang);
		re.setAttribute("dev", new Dev(devName, devAge, devEmail, devGender, devLang));
		return "domo/domoResult";
	}
	
	
	//제일 간단하게 model만 사용 - 객체를 생성하고 사용할 필요도 없고요 객체의 멤버변수명과 name명만 같게만 해주면 들어감
	@RequestMapping("/domo/domo3.do")
	public String domo3(Dev dev, Model model) {
		model.addAttribute("dev", dev);
		return "domo/domoResult";
	}
	
	
	@RequestMapping("/domo/insert.do")
	public String insert(Dev dev) {
		int result=service.insert(dev);
		System.out.println(dev);
		return "redirect:/";
	}
	
	
	//외부에서 받아오는건 매개변수가 없다 , spring에선 model이 공용객체 = request랑 비슷하다
	@RequestMapping("/domo/selectList.do")
	public String domoList(Model model) {
		//Map<String,Dev> map = new HashMap();
		List<Dev> list =service.selectList();
		model.addAttribute("list", list);
		System.out.println(list);
		return "domo/domoList";
	}
	

	
	
	

}
