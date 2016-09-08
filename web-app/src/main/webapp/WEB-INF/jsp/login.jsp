<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Login</title>
    <link href="<c:url value="/resources/css/login_form_css.css"/> " rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.validate.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/additional-methods.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
</head>
<body>

<section class="container">
    <div class="login">
        <h1>Please login</h1>
        <form id="loginform" method="post" action="${pageContext.request.contextPath}/login-check">
            <p><input type="text" name="email" placeholder="Email"></p>
            <p><input type="password" name="password" placeholder="Password"></p>
            <p class="remember_me">
                <label>
                    <input type="checkbox" name="isRemember" id="remember_me">
                    Remember me
                </label>
            </p>
            <p class="submit"><input type="submit" name="submit" value="Confirm"></p>
        </form>
    </div>
    <div class="login-help">
        <p>Don't have an account? <a href="${pageContext.request.contextPath}/registration">Sign Up</a></p>
    </div>
</section>

</body>
</html>
