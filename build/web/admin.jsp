<%-- 
    Document   : manager
    Created on : Jun 8, 2021, 2:55:35 PM
    Author     : toann
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MY_APP</title>
        <script type="text/javascript">
            function disableBack() {
                window.history.forward();
            }
            setTimeout("disableBack()", 0);
            window.onunload = function () {
                null
            };
        </script>
    </head>
    <body>

        <c:set var="session" value="${sessionScope}"/>
        <c:if test="${not empty session}">
            <c:set var="admin" value="${sessionScope.USER_ADMIN}"/>
            <c:if test="${not empty admin}">
                <font color ="red">Welcome, ${admin.name}</font>

                <form action="logout">
                    <input type="submit" value="Logout" name="btAction" />
                </form>

                <h1>Admin Page</h1>
                <form action="searchRequest">
                    Search Value <input type="date" id="date" name="dateSearch"
                           value="${param.dateSearch}"
                           min="" max="">
                    <input type="submit" value="Search" name="btAction" />
                    <input type="hidden" name="txtrootUser" value="${admin.name}" />
                </form>
                <br>
                <c:if test="${not empty equestScope.successConfirm}">
                    <h3 style="color: red">
                        ${requestScope.successConfirm}
                    </h3>

                </c:if>  
                <c:if test="${not empty requestScope.errorConfirm}">
                    <h3 style="color: red">
                        ${requestScope.errorConfirm}
                    </h3>
                </c:if>  
                <h2> Request Need Confirm </h2>
                <table style="border: none; text-align: center">
                    <thead>
                        <tr>
                            <th>RequestID</th>
                            <th>DateBook</th>
                            <th>email</th>
                            <th>ServiceName</th>
                            <th>status</th>
                            <th>Accept</th>
                            <th>Delete</th>

                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="request" value="${sessionScope.REQUEST}"/>
                        <c:if test="${not empty request}">
                            <c:forEach var="item" items="${request}">
                                <tr>
                                    <td>${item.requestID}</td>
                                    <td>${item.dateBook}</td>
                                    <td>${item.email}</td>
                                    <td>
                                        ${item.serviceID}
                                    </td>
                                    <td > ${item.getStatusReq()}</td>

                                    <td>
                                        <form method="Post" action="Accept" >
                                            <input type="hidden" name="requestID" value="${item.requestID}" />
                                            <input type="hidden" name="email" value="${item.email}" />
                                            <input type="hidden" name="serviceID" value="${item.serviceID}" />
                                            <input type="submit" value="Accept" name="statusConfig" />
                                        </form>
                                    </td>

                                    <td>
                                        <form method="Post" action="Accept" >
                                            <input type="hidden" name="requestID" value="${item.requestID}" />
                                            <input type="hidden" name="email" value="${item.email}" />
                                            <input type="hidden" name="serviceID" value="${item.serviceID}" />
                                            <input type="submit" value="Cancel" name="statusConfig" />
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </tbody>
                </table>
                        </br>
                        <h2>History Request Confirmed </h2>
                <table style="border: none; text-align: center">
                    <thead>
                        <tr>
                            <th>RequestID</th>
                            <th>DateBook</th>
                            <th>email</th>
                            <th>ServiceName</th>
                            <th>status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="request" value="${sessionScope.REQUESTCONFIRMED}"/>
                        <c:if test="${not empty request}">
                            <c:forEach var="item" items="${request}">
                                <tr>
                                    <td>${item.requestID}</td>
                                    <td>${item.dateBook}</td>
                                    <td>${item.email}</td>
                                    <td>
                                        ${item.serviceID}
                                    </td>
                                    <td > ${item.getStatusReq()}</td>


                                </tr>
                            </c:forEach>
                        </c:if>
                    </tbody>
                </table>
            </c:if>
        </c:if>
    </body>
</html>
