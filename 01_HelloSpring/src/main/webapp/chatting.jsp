<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅창구현</title>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>

	<input type="text" id="msg"/>
	<input type="button" id="btn" value="전송"/>
	<input type="file" id="file" name="upfile"/>
	<input type="button" id="filebtn" value="전송"/>
	<div id="container"></div>
	
	
	<script>
		var socket;
		var nickname=prompt("대화명 입력 : ");
		
		$(function(){
			
			
			//파일업로드 기능 구현
			$("#filebtn").on("click", function(){
				var file=$('#file')[0].files[0];
				
				//console.log(file);
				
				//binaryType을 바꿈
				socket.binaryType="arraybuffer";
				var reader = new FileReader();
				reader.onload=function(e){
					
					//message를 구분해야한다. 원하는 값으로 갈 수 있게 flag값을 주면되
					var msg={"nickname" : nickname,"msg": file.name,"flag" :"file"}
					socket.send(JSON.stringify(msg));
					console.log(e.target.result);
					//얘는 일반 텍스트가 아니다 파일이다.
					socket.send(e.target.result);
				}
				
				//ArrayBuffer로서 읽어라
				reader.readAsArrayBuffer(file);
			
			});
			
			
			
			
			
			socket = new WebSocket("ws://172.168.120.14:9090/spring/chatting.do");
			/* 웹소켓 객체안의 메소드를 구현하면 됨 
				속성 : onopen, onmessage, onclose, onerror
				onopen - 
				onmessage - message가 도착했을 때
				onclose - session끊겼을 때
				onerror - error났을 때
				구현함수를 해당속성에 대입하면 됨!
				소켓 메세지를 서버로 전송할 때 사용하는 
				send()가 있음.
			*/
			
			//소켓세션이 연결되면 실행되는 함수
			socket.onopen=function(e){
				console.log(e);
			}
			
			socket.onmessage=function(e){
				console.log(e);
				var msg = JSON.parse(e.data);
				$("#container").append("<p>"+msg["nickname"]+":"+msg["msg"]+"</p>");
			}
			socket.onclose=function(e){
				console.log(e);
			}

			//아래처럼 이렇게 메소드를 하나 만들어서 사용할 수도 있다.		
			function message(e){
				
			}
			$("#btn").click(function(){
				console.log("click");
			})
			$("#btn").on("click", function(){
				
				var msg={"nickname":nickname,"msg":$("#msg").val()}
				socket.send(JSON.stringify(msg));
				
				$("#msg").val("");
			})
		});
		
		
		
	</script>
	
	


</body>
</html>