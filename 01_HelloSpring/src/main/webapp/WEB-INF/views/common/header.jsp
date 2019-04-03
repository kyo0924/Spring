<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<!-- 부트스트랩관련 라이브러리 -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
	integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4"
	crossorigin="anonymous">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"
	integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm"
	crossorigin="anonymous"></script>
	
	
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width"/>
<title>Login Demo - Kakao JavaScript SDK</title>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>


	
<!-- 사용자작성 css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/style.css" />
<meta charset="UTF-8">
<title>메인화면</title>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>

</head>
<body>

	<div id="container">
		<header>
			<div id="header-container">
				<h2>${param.title }</h2>
			</div>
			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<a class="navbar-brand" href="#"> <img
					src="${pageContext.request.contextPath }/resources/images/logo-spring.png"
					alt="스프링로고" width="50px" />
				</a>
				<!-- 반응형으로 width 줄어들경우, collapse버튼관련 -->
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarNav" aria-controls="navbarNav"
					aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarNav">
					<ul class="navbar-nav mr-auto">
						<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}">Home</a></li>
						<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/board/boardList">게시판</a></li>
						<%-- <li class="nav-item"><a href="${pageContext.request.contextPath }/domo/domo.do">domo</a></li> --%>
						<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false">demo</a>
						<div class="dropdown-menu" aria-labelledy="navbarDropdown">
							<a class="dropdown-item" href="${pageContext.request.contextPath }/domo/domo.do">demo 등록</a>
							<a class="dropdown-item" href="${pageContext.request.contextPath }/domo/selectList.do">demo 리스트</a>
						</div>
						</li>
					
						<!-- 2019-03-19 추가 -->	
						<li class="nav-item">
							<a class="nav-link" href="${path }/memo/memo.do">메모</a>
						</li>
					</ul>
					<c:if test="${loggedMember==null }">
						<button class="btn btn-outline-success my-2 my-sm-0" 
						type="button" data-toggle="modal" data-target="#loginModal">로그인</button>
						&nbsp;
						<button class="btn btn-outline-success my-2 my-sm-0" 
							type="button" onclick="location.href='${path}/member/memberEnroll.do'">회원가입</button>
					</c:if>
					<!-- 카카오로그인 -->
							<a id="kakao-login-btn"></a>
							<a href="http://developers.kakao.com/logout">logout</a>
					
					
					<c:if test="${loggedMember!=null }">
												<!-- 프라이머리키를 넘겨줘야함 -->
							<%-- <a href="${path}/member/update.do?userId=${loggedMember.userId}">${loggedMember.userName }</a> --%>
						<span><a href="${pageContext.request.contextPath }/member/update.do?userId=${loggedMember.userId }">${loggedMember.userName }</a>님 환영합니다 </span>
						<button class="btn btn-outline-success my-2 my-sm-0" 
							type="button" onclick="location.href='${path}/member/LogOut.do'">로그아웃</button>
					</c:if>
				</div>
			</nav>
		</header>
		
		
		
		<!-- 로그인 모달 만들기 -->
		<div class="modal fade" id="loginModal" tabindex="-1" 
		role="dialog" aria-labelledby="exampleModallabel" aria-hidden="true">
			<div class="modal-dialog" role="documnet">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">로그인</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<form action="${path }/member/memberLogin.do" method="post">
					<div class="modal-body">
						<input type="text" class="form-control" 
							name="userId" placeholder="아이디" required/><br/>
						<input type="password" class="form-control" 
							name="password" placeholder="비밀번호" required/><br/>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-outline-success">
							로그인</button>
						<button type="button" class="btn btn-outline-success" 
							data-dismiss="modal">취소</button>
					</div>
					</form>
				</div>			
			</div>
		</div>
		
		
		
<script type='text/javascript'>
    // 사용할 앱의 JavaScript 키를 설정해 주세요.
    Kakao.init('3936fbb46415d0ad3589f5b20380fa77');
    // 카카오 로그인 버튼을 생성합니다.
    Kakao.Auth.createLoginButton({
      container: '#kakao-login-btn',
      success: function(authObj){
         Kakao.API.request({
            url: '/v1/user/me',
            success:function(res){
               console.log(res);
             //  console.log(profile);
               $('#kakaoId').val(res.id);
               $('#kakaoNick').val(res.properties['memberNickname']);
               $('#kakaoLoginForm').submit();     
            }
         })      
      },
      fail:function(err){ alert(JSON.stringify(err));}
   });
</script>	
		