package com.kh.spring.common.socket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;



/* 소켓 핸들러 객체는 두개의 상속 객체를 가질 수 있음!
 * 1. TextWebSocketHandler   : 문자만 전송 채팅전용, 알람 기타 문자들만 사용하는 경우
 * 2. BinaryWebSocketHandler : 문자도 되고 파일까지 받을 수 있음! 그래서 Binary에요 2진수죠 2진수


public class SocketHandler extends TextWebSocketHandler {
 */
public class SocketHandler extends BinaryWebSocketHandler {

	//logger를 사용할거야
	private Logger logger= LoggerFactory.getLogger(SocketHandler.class);
	private List<WebSocketSession> list=new ArrayList();
	private String fileName;



	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("session id: "+session.getId());
		//session보관
		list.add(session);
		super.afterConnectionEstablished(session);
	}



	//파일업로드 관련은 전부 얘가 
	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {

		logger.debug("들어온 값 확인 : "+session.getId()+" : "+message);
		String dir="C:\\Users\\kein0\\testFolder\\";

		//중복처리 방지
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HHmmssSSS");
		int rnd=(int)(Math.random()*1000);
		
		String refilename = sdf.format(new Date(System.currentTimeMillis()))+"_"+rnd+fileName;
		
		//이 스트림이랑 연결된 파일이 생성됬겠죠	???
		//FileOutputStream fos = new FileOutputStream(new File(dir+session.getId()));
		//refilename을 만든 후 아래처럼 바꿔줌
		FileOutputStream fos = new FileOutputStream(new File(dir+refilename));

		
		//payload의 반환값이 byteBuffer에요 - import 잘 확인 nio임
		ByteBuffer bb = message.getPayload();

		//byte배열로 바껴서 꽂힘
		fos.write(bb.array());
		fos.close();
		//여기까지 파일 업로드가 끝난것

		Map<String,String> map = new HashMap();
		map.put("nickname",	"관리자");
		map.put("msg", session.getId()+"님의 파일업로드가 완려되었습니다.");
		//Json형식에 맞춰야해서
		ObjectMapper mapper = new ObjectMapper();
		for(WebSocketSession s : list) {
			s.sendMessage(new TextMessage(mapper.writeValueAsString(map)));
		}
	}




	//메세지 
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		logger.debug("message : "+message);
		String msg=message.getPayload();
		//얘를 가공처리할수있다
		//session.sendMessage : session에다가 메세지를 보내는 메소드
		//전달된 메소드는 페이지의 onmessage 함수를 호출
		logger.debug(msg);

		//메세지 파싱처리하기~
		Map<String,String> map = new HashMap<>();
		//object로 온걸 다른 형식으로 바꿔줄 수 있다.
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(msg,HashMap.class);
			//받아온 msg를 HashMap
		}
		catch(IOException e){
			e.printStackTrace();
		}

		logger.debug("메세지 변환 후 : "+map);
		//session.sendMessage : session에 메세지를 보내는 메소드
		//전달된 메소드는 페이지의 onmessage함수를 호출

		//chatting.jsp 의 flag값이 file이랑 같으면
		if(map.get("flag")!=null && map.get("flag").equals("file")) {
			fileName=map.get("msg");
		}
		else {
			for(WebSocketSession s : list) {
				//if(s==session) {continue;}
				/*if(map.containsKey("whisper")) {
					if(map.get("whisper").equals(list))
				}*/
				try {
					s.sendMessage(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/*@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		logger.debug("message : "+message);
		String msg=message.getPayload();
		//얘를 가공처리할수있다
		//session.sendMessage : session에다가 메세지를 보내는 메소드
		//전달된 메소드는 페이지의 onmessage 함수를 호출
		logger.debug(msg);

		//메세지 파싱처리하기~
		Map<String,String> map = new HashMap<>();
		//object로 온걸 다른 형식으로 바꿔줄 수 있다.
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(msg,HashMap.class);
			//받아온 msg를 HashMap
		}
		catch(IOException e){
			e.printStackTrace();
		}

		logger.debug("메세지 변환 후 : "+map);

		for(WebSocketSession s : list) {
			if(s==session) {continue;}
			if(map.containsKey("whisper")) {
				if(map.get("whisper").equals(list))
			}
			s.sendMessage(message);
		}

	//	super.handleTextMessage(session, message);
	}*/







	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.debug("close : "+session.getId());

		//session끊어질 때 지워줌
		list.remove(session);

		//	super.afterConnectionClosed(session, status);
	}






}
