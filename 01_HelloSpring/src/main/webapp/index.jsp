<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="hello Spring" name="title" />
</jsp:include>

<section id="content">
	<img
		src="${pageContext.request.contextPath }/resources/images/logo-spring.png"
		id="center-image" alt="스프링로고">
</section>
<button onclick="ajaxTest()">testAjax</button>
<button onclick="ajaxTest2()">vo객체 받기</button>

<input type="text" id="userId"/>

<div id="result"></div>
<script>
	function ajaxTest() {
		$.ajax({
			url : "${pageContext.request.contextPath}/ajaxTest.do",
			dataType:"json",
			success:function(data){
				console.log(data);
				var table=$("<table>");
				table.append("<tr><th>boardNo</th><th>Title</th><th>Writer</th><th>Content</th><th>Date</th><th>readCount</th></tr>")
				for(var i=0;i<data.length;i++){
					var tr=$("<tr>");
					for(var key in data[i]){
						var td=$("<td>");
						td.append(data[i][key]);
						tr.append(td);
					}
					table.append(tr);
				}
				$("#result").html(table);
				
				/* var tr=$("<tr></tr>");
				var th="<th>제목</th>";
					th+="<th>작성자</th>";
					th+="<th>내용</th>";
				var container=$('<div id="result"></div>'); */
			}
		})
	}
	
	
	function ajaxTest2(){
		var userId=$("#userId").val().trim();
		if(userId.length<=0){
			alert("ID를 입력하세요!");
			return;
		}
		$.ajax({
			url : "${pageContext.request.contextPath}/ajaxTest2.do",
			dataType :"json",
			data : {"userId":userId},		//userId를 AjaxTest Class(controller역할)에 넘겨주는 부분
			success:function(data){
				console.log(data);
			}
		});
	}
	
	
	
	
	
	
	
	
</script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>