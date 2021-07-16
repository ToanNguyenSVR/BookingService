<%-- 
    Document   : Register
    Created on : May 15, 2021, 10:25:57 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        <h1>Create Account</h1>

        <form method="POST" action="register">
            Email <input type="text" name="email" value="${requestScope.email}" /><br/>
            <c:if test="${not empty requestScope.errorRegister.emailError}">
                <span style="color: red">${requestScope.errorRegister.emailError}</span> <br/>    
            </c:if>

            <c:if test="${not empty requestScope.errorRegister.emailErrorFormat}">
                <span style="color: red">${requestScope.errorRegister.emailErrorFormat}</span> <br/> 
            </c:if>
            <c:if test="${not empty requestScope.errorRegister.emailDuplicate}">
                <span style="color: red">${requestScope.errorRegister.emailDuplicate}</span> <br/>
            </c:if>
            Phone <input type="text" name="phone" value="${requestScope.phone}" /><br/>
            <c:if test="${not empty requestScope.errorRegister.phoneError}">
                <span style="color: red">${requestScope.errorRegister.phoneError}</span> <br/>
            </c:if>
            <c:if test="${not empty requestScope.errorRegister.phoneErrorFormat}">
                <span style="color: red">${requestScope.errorRegister.phoneErrorFormat}</span><br/>
            </c:if>
            Name <input type="text" name="name" value="${requestScope.name}" /><br/>
            <c:if test="${not empty requestScope.errorRegister.nameError}">
                <span style="color: red">${requestScope.errorRegister.nameError}</span> <br/>
            </c:if>
            Password <input type="text" name="password" value="${requestScope.password}" /><br/>
            <c:if test="${not empty requestScope.errorRegister.passwordError}">
                <span style="color: red">${requestScope.errorRegister.passwordError}</span> <br/>
            </c:if>
            Address <input type="text" name="address" value="${requestScope.address}" /><br/>
            <c:if test="${not empty requestScope.errorRegister.addressError}">
                 <span style="color: red">${requestScope.errorRegister.addressError}</span> <br/>
            </c:if>
            <input type="submit" value="Create" name="btnAction"/>
        </form>
    </body>
</html>
