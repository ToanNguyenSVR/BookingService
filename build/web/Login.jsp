<%-- 
    Document   : Login
    Created on : May 13, 2021, 11:24:34 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <div>
            <form action="login" method="Post">
                Email <input type="text" name="email" value="${requestScope.valueEmail}" /> <br/> 
                <p style="color: red">${requestScope.email}</p>
                Password <input type="text" name="password" value="${requestScope.valuePW}" /> <br/>
                <p style="color: red">${requestScope.password}</p>
                <input type="submit" value="login" name="btnAction" />
                <div class="g-recaptcha"
                     data-sitekey="6LdE0IIbAAAAAMEVaCGVdteSq8RySrrFMKvdbym-"></div>
                
                <p style="color: red">${requestScope.errorCapCha}</p>

            </form>
            <p style="color: red">${requestScope.error}</p>
        </div>
        <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8084/ResouceWeb/loginWithGG&response_type=code
           &client_id=615227338866-m3gfr9t515654et27ou88mj5r7gu3kae.apps.googleusercontent.com&approval_prompt=force">Login With Google</a> 
        <a href="registerAccount">Register Account</a>
        <div>
            <h1 style="color: red">${requestScope.unConfirmedEmail}</h1>
            <c:if test="${not empty requestScope.unConfirmedEmail}">
                <a href="DispatchController?btnAction=verifyEmail&email=${requestScope.valueEmail}&password=${requestScope.valuePW}">Verify Email</a>
            </c:if>
        </div>
        <script src='https://www.google.com/recaptcha/api.js'></script>
    </body>
</html>
