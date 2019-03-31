<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
    					<!-- 위에 isErrorPage를 쓰게 되면 이건 error page -->
    
    
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
   <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/views/common/header.jsp">
   <jsp:param value="에러발생" name="pageTitle"/>
</jsp:include>
<section>
	<div>
		<h1>에.러.발.생</h1>
		<h2 style="color:crimson;">
		<%=exception.getMessage() %></h2>
		<h4>
			<a href="${path }/">메인화면으로</a>
		</h4>
		</div>

</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>