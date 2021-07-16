<%-- 
    Document   : Verify
    Created on : May 15, 2021, 12:20:45 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify Page</title>
    </head>
    <body>
        <h1>Verify Page</h1>
        <form method="Post" action="VerifyAccount">
            Code <input type="text" name="code" value="" />
            <input type="hidden" name="email" value="${requestScope.emailUser}" />
            <input type="submit" value="Submit Code" name="btnAction"/>
        </form>
        <p style="color: green;">${requestScope.verifySuccess}</p>
        <p style="color: red;">${requestScope.verifyFail}</p>
        <a href="index">Login</a>
    </body>
</html>
