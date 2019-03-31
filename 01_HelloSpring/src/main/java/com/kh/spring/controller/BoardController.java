package com.kh.spring.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.common.PageBarFactory;
import com.kh.spring.model.vo.Attachment;
import com.kh.spring.model.vo.Board;
import com.kh.spring.service.BoardService;
import com.kh.spring.service.BoardServiceImpl;

@Controller
public class BoardController {

	private Logger logger=LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService service;

	@RequestMapping("/board/boardList")
	public ModelAndView boardList(@RequestParam(value="cPage", required=false, defaultValue="1") int cPage) {

		int numPerPage=10;
		ModelAndView mv = new ModelAndView();

		List<Board> list=service.selectList(cPage, numPerPage);
		int totalList=service.selectCount();

		//받아오는건 다 끝남. 페이징처리

		mv.addObject("list", list);
		mv.addObject("totalList", totalList);
		//PageBarFactory는 common에서 생성
		mv.addObject("pageBar", PageBarFactory.getPageBar(totalList, cPage, numPerPage,"/spring/board/boardList"));
		mv.setViewName("board/boardList");
		return mv;
	}	


	//얘는 페이지 전환용 그냥 return해서 넘겨주면 됨.
	@RequestMapping("/board/boardForm")
	public String boardForm() {
		return "board/boardForm";
	}


	@RequestMapping("/board/boardFormEnd.do")
	public ModelAndView insertBoard(Board board, MultipartFile[] upFile, HttpServletRequest re) {
		//배열로 선언!
		ModelAndView mv = new ModelAndView();
		System.out.println(board);
		/*	System.out.println(upFile[0].getOriginalFilename());
		System.out.println(upFile[1].getOriginalFilename());*/

		//파일경로설정
		String saveDir = re.getSession().getServletContext().getRealPath("/resources/upload");

		File dir = new File(saveDir);

		if(!dir.exists()) { //upload라는 폴더가 없으면
			dir.mkdirs();
		}


		/*	단일파일에 대한부분 -> 배열로 바껴서 아래 다시 구현
		 * if(!upFile.isEmpty()) { //넘어온 파일이 있으면!
			String oriFileName=upFile.getOriginalFilename();
			String ext=oriFileName.substring(oriFileName.indexOf("."));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy_mm_dd_HHmmssSSS");
			int rndNum=(int)(Math.random()*1000);
			String reNamedFile=sdf.format(new Date(System.currentTimeMillis()))+"_"+rndNum+ext;

			try {
				//파일입출력이라 try~catch해줘야함
				upFile.transferTo(new File(saveDir+"/"+reNamedFile));
			}
			catch(IOException e){
				e.printStackTrace();
			}

		}*/

		List<Attachment> list = new ArrayList();
		for(MultipartFile f : upFile) {
			if(!f.isEmpty()) {
				String OriFileName=f.getOriginalFilename();
				String ext=OriFileName.substring(OriFileName.indexOf("."));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				int rndNum=(int)(Math.random()*10000);
				String reNamedFile=sdf.format(new Date(System.currentTimeMillis()))+"_"+rndNum+ext;
				try {
					f.transferTo(new File(saveDir+"/"+reNamedFile));
				}
				catch(IOException e) {
					e.printStackTrace();
				}
				Attachment a = new Attachment();
				a.setOriginalFileName(OriFileName);
				a.setRenamedFileName(reNamedFile);
				list.add(a);
				//위에 방식이 가독성이 더 좋음
				//list.add(new Attachment(0,0,OriFileName,reNamedFile,null,0,null));
			}
		}
		int result=service.insertBoard(board, list);
		mv.setViewName("common/msg");
		return mv;
	}


	//게시판 상세페이지
	@RequestMapping("/board/boardView.do")
	public ModelAndView selectOne(@RequestParam(value="boardNo", defaultValue="1") int boardNo) {
		ModelAndView mv= new ModelAndView();
		mv.addObject("board", service.selectBoard(boardNo));
		mv.addObject("attachmentList", service.selectAttachment(boardNo));
		mv.setViewName("board/boardView");
		return mv;
	}

	//파일 다운로드
	@RequestMapping("/board/boardDown.do")
	public void fileDown(String oName, String rName, HttpServletRequest request, HttpServletResponse response) {
		logger.warn(oName + " : " + rName);
		BufferedInputStream bis=null;
		ServletOutputStream sos=null;

		//1. 실제 파일 저장경로 가져오기
		String saveDir=request.getSession().getServletContext().getRealPath("/resources/upload");
		
		//2.입출력 스트립 생성
		try {
		
			FileInputStream fis=new FileInputStream(new File(saveDir+"/"+rName));
			bis=new BufferedInputStream(fis);
			sos=response.getOutputStream();
			
			//3한글 파일을 위한 파일명 분기 처림(한글 깨지는거 방지)
			String resFilename="";
			boolean isMSIE=request.getHeader("user-agent").indexOf("MSIE")!=-1
					||request.getHeader("user-agent").indexOf("Trident")!=-1;
			if(isMSIE) {
				resFilename=URLEncoder.encode(oName, "UTF-8");
				resFilename=resFilename.replaceAll("\\+", "%20");
			}
			else {
				resFilename=new String(oName.getBytes("UTF-8"), "ISO-8859-1");
			}
			
			//4.헤더세팅
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=\""+resFilename+"\"");

			//5.파일보내주기
			int read=0;

			while((read=bis.read())!=-1) {
				sos.write(read);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				bis.close();
				sos.close();
			}
			catch(IOException e){
				e.printStackTrace();

			}
		}
	}



}
