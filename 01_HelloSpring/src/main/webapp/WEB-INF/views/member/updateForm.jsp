<%@page import="com.kh.spring.model.vo.Member,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path" value="${pageContext.request.contextPath}" />

<!-- 취미부분 값을 El로 표현하기에는 상당히 제한적이다 그래서 아래처럼 해줌 -->
<%
	Member m=(Member)request.getAttribute("m");
	String[] hobbys=m.getHobby();
	List<String> hobbyList=new ArrayList();
	if(hobbys!=null){
		hobbyList=Arrays.asList(hobbys);
	}
%>


<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="회원등록" name="pageTitle" />
</jsp:include>

<style>
div#enroll-container {
	width: 400px;
	margin: 0 auto;
	text-align: center;
}

div#enroll-container input, div#enroll-container select {
	margin-bottom: 10px;
}
</style>
<section>

	<div id="enroll-container">
		<form name="memberEnrollFrm" action="${pageContext.request.contextPath}/member/memberEnrollEnd.do"
			method="post" >
			<input type="text" class="form-control" value="${m.userId }" name="userId" id="userId_" readonly> 
			<input type="text" class="form-control" value="${m.userName }" name="userName" id="userName" required> 
														<!-- number는 띄어쓰기를 넣으면 안됨! -->
			<input type="number" class="form-control" value="${loggedMember.age }" name="age" id="age"> 
			<input type="email" class="form-control" value="${m.email }" name="email" id="email" required> 
			<input type="tel" class="form-control" value="${m.phone }" name="phone" id="phone" maxlength="11" required> 
			<input type="text" class="form-control" value="${m.address }" name="address" id="address">
			<select class="form-control" name="gender" required>
				<option value="" disabled selected>성별</option>
				<option value="M" ${m.gender eq "M"?"selected":"" }>남</option>
				<option value="F" ${m.gender eq "F"?"selected":"" }>여</option>
			</select>
			
			<div class="form-check-inline form-check">
				취미 : &nbsp; 
				<input type="checkbox" class="form-check-input" name="hobby" id="hobby0" value="운동" <%=hobbyList!=null&&hobbyList.contains("운동")?"checked":"" %>>
				<label for="hobby0" class="form-check-label">운동</label>&nbsp; 
				<input type="checkbox" class="form-check-input" name="hobby" id="hobby1" value="등산" <%=hobbyList!=null&&hobbyList.contains("등산")?"checked":"" %>>
				<label for="hobby1" class="form-check-label">등산</label>&nbsp; 
				<input type="checkbox" class="form-check-input" name="hobby" id="hobby2" value="독서" <%=hobbyList!=null&&hobbyList.contains("독서")?"checked":"" %>>
				<label for="hobby2" class="form-check-label">독서</label>&nbsp;
				<input type="checkbox" class="form-check-input" name="hobby" id="hobby3" value="게임" <%=hobbyList!=null&&hobbyList.contains("게임")?"checked":"" %>>
				<label for="hobby3" class="form-check-label">게임</label>&nbsp; 
				<input type="checkbox" class="form-check-input" name="hobby" id="hobby4" value="여행" <%=hobbyList!=null&&hobbyList.contains("여행")?"checked":"" %>>
				<label for="hobby4" class="form-check-label">여행</label>&nbsp;
			</div>
			<br /> 
			<input type="button" class="btn btn-outline-success" value="수정" onclick="fn_updateMember();">&nbsp; 
			<input type="button" class="btn btn-outline-success" value="탈퇴" onclick="fn_deleteMember();">
		</form>
	</div>
	
	<script>
	function fn_updateMember(){
		$('[name="memberEnrollFrm"]').attr("action","${path}/member/updateEnd.do");
		memberEnrollFrm.submit();
	};

	function fn_deleteMember(){
		var flag=confirm("정말로 탈퇴하시겠습니까?")
		if(flag){
			location.href="{pageContext.request.contextPath}/member/deleteMember.do";
		}
	};
	
	function validate(){
		var id=$("#userId_").val().trim();
		if(id.length<4){
			alert("id를 4글자 이상 작성하세요");
			return false;
		}
	}
	</script>
		
	
</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>