<%-- 
    Document   : userView
    Created on : Jul 9, 2021, 5:59:50 PM
    Author     : toann
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
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
            <c:set var="user" value="${sessionScope.USER}"/>
            <c:if test="${not empty user}">
                <font color ="red">Welcome, ${user.getName()}</font>
            </c:if>
            <c:if test="${not empty sessionScope.loginGG}">
                <font color ="red">Welcome,${sessionScope.loginGG}</font>
            </c:if>
            <form action="logout">
                <input type="submit" value="Logout" name="btAction" />
            </form>

            <h1>User Page</h1>
            <form action="search">
                Search Value<input type="text" name="nameSearch" value="${param.nameSearch}" />
                <input type="submit" value="Search" name="btAction" />

            </form>
            <form action="searchByDay">
                <h4>find the day you want to book</h4>
                <input type="date" id="date" name="dateSearch"
                       value="${param.dateSearch}"
                       min="" max="">  
                <input type="submit" value="submit" />
            </form>    
            <form action="search">
                <div class="single-sidebar">
                    <h2 class="sidebar-title">Filter Products</h2>

                    <h3 class="sidebar-title">Category</h3>


                    <c:set var="catergory" value="${sessionScope.CATEGORY}"/>
                    <c:if test="${not empty catergory}">
                        <c:forEach var="dto" items="${catergory}" >
                            <input type="checkbox" name="category" value="${dto.categoryID}" />
                            ${dto.categoryName}
                            </br>
                        </c:forEach>
                    </c:if>

                </div>
                <input type="hidden" name="nameSearch" value="${param.nameSearch}" />
                <input type="submit" value="apply" name="btnAction" />
            </form>

            <br>
            <c:if test="${not empty requestScope.bookingFail}">
                <p style="color: red">
                    ${requestScope.bookingFail}
                </p>
            </c:if> 
            <c:set value="${sessionScope.SERVICE}" var="Service"/>
            <c:if test="${not empty Service}">
                <table style="border: none; text-align: center">
                    <thead>
                        <tr>

                            <th>Name</th>
                            <th>Color</th>
                            <th>CategoryName</th>
                            <th>Quanlity</th>
                            <th>Date Booking</th>
                            <th>Function</th>
                        </tr>
                    </thead>
                    <tbody>

                        <c:forEach var="item" items="${Service}">
                            <tr>
                        <form method="POST" action="Booking">
                            </br>
                            <td>${item.getServiceName()}</td>
                            <td>${item.getColor()}</td>


                            <c:forEach var="itemCategory" items="${catergory}">
                                <c:if test="${item.categoryID == itemCategory.categoryID}">
                                    <td>${itemCategory.getCategoryName()}</td>       
                                </c:if>
                            </c:forEach>


                            <td>${item.getQuanlity()}</td>
                            <td><input type="date" id="date" name="date"
                                       value="${param.dateSearch}"
                                       min="" max="">  </td>
                            <td>

                                <input type="hidden" name="ServiceID" value="${item.getServiceID()}" />
                                <input type="submit" name="btnAction" value="Booking" ${item.getQuanlity() == 0 ? "disabled": ""}/>

                            </td>
                        </form>

                    </tr>
                </c:forEach>

            </tbody>
        </table>
    </c:if>
    <c:if test="${not empty requestScope.bookingSuccess}">
        <p>  style="color: green">
            ${requestScope.bookingSuccess}
        </p>
    </c:if> 
    <c:if test="${not empty requestScope.errBooking}">
    <td style="color: red">
        ${requestScope.errBooking}
    </td>
</c:if>  


<h2>Notifiction NEW</h2>
<c:set var="notification" value="${sessionScope.NOTIFICATION}"/>
<c:if test="${ not empty notification}">
    <c:forEach var="noti" items="${notification}"  >
        <h4>  Hi , ${noti.getUserID()} ,  ${noti.getContent()} </h4>

    </c:forEach> 
</c:if>

<c:set var="request" value="${sessionScope.HISTORYBOOKING}"/>
<c:if test="${not empty request}">
    
    <h2>History Request</h2>        
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

        </tbody>
    </table>
</c:if>






</c:if>
</body>
</html>
