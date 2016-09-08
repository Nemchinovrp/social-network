<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link href="${pageContext.request.contextPath}/resources/css/login_form_css.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.validate.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/additional-methods.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/validation.js"></script>

    <style>
        .error {
            color: red;
        }
        .errorTxt{
            text-align: center;
        }
    </style>

</head>
<body>
<section class="container">
    <p style="color:red;font-size: 16px;" align="center">${requestScope.error}</p><br>
    <div class="login">
        <h1>Registration</h1>
        <form id="registrationform" method="post" action="${pageContext.request.contextPath}/create_account">
            <p><input type="text" name="email" placeholder="Email" required="required"></p>
            <p><input type="text" name="name" placeholder="First name" required="required"></p>
            <p><input type="text" placeholder="Surname" name="lastName" required="required"></p>

            <p><input id="password" type="password" placeholder="Password" name="password" required="required"></p>
            <p><input type="password" placeholder="Repeat password" name="repeatPassword" required="required"></p>

            <p style="text-align: center"><input type="submit" name="commit" value="Register"></p>
        </form>
    </div>
    <div class="login-help">
        <p>Have an account? <a href="${pageContext.request.contextPath}/">Sign In</a></p>
    </div>
</section>
</body>
</html>
