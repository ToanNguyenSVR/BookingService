<%-- 
    Document   : ListProduct
    Created on : May 12, 2021, 10:45:32 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ListProduct JSP</title>
        <style>
            .pagination a {
                color: black;
                float: left;
                padding:  12px 18px;
                text-decoration: none;
            }
            /* thiết lập style cho class active */
            .pagination a.active {
                background-color: dodgerblue;
                color: white;
                /*Thiết kế hình tròn với CSS*/
                border-radius: 50%;
            }
            /* thêm màu nền khi người dùng hover vào class không active */
            .pagination a:hover:not(.active) {
                background-color: #ddd;
                /*Thiết kế hình tròn với CSS*/
                border-radius: 50%;
            }
        </style>
    </head>
    <body>
        <c:if test="${empty sessionScope.user && empty sessionScope.loginGG && empty sessionScope.admin}">
            <c:redirect url="Login.jsp"/>
        </c:if>
        <c:if test="${sessionScope.user.statusID eq  1}">
            <c:redirect url="Login.jsp"/>
        </c:if>
        <div style="display: flex; align-items: center">
            <c:if test="${not empty sessionScope.user.getName()}">
                <h1>hello ${sessionScope.user.getName()}</h1>
            </c:if>
            <c:if test="${not empty sessionScope.loginGG}">
                <h1>hello ${sessionScope.loginGG}</h1>
            </c:if>
            <form action="DispatchController">
                <input type="submit" value="logout" name="btnAction" />
            </form>
        </div>
        <h1>ListProduct</h1>
        <form method="Post" action="DispatchController">
            <input type="submit" value="View List Booking" name="btnAction" />
        </form>
        <h1 style="color: green">${requestScope.bookingSuccess}</h1>
        <h1 style="color: red">${requestScope.bookingFail}</h1>
        <label for="categories">Choose a categories</label>
        <form action="DispatchController" method="POST">
            <select id="categories" name="category">
                <c:forEach var="item" items="${requestScope.listCategories}">
                    <option value="${item.getCategoryID()}" ${requestScope.categorySelect eq item.getCategoryID() ? "selected" : ""}>${item.getCategoryName()}</option>
                </c:forEach>
                <option value="default" ${requestScope.categorySelect eq 'default' ? "selected" : ""}>ALL</option>
            </select>
            Search <input type="text" name="nameSearch" value="${requestScope.nameSearch}" />
            <label for="date">date</label>
            <input type="date" id="date" name="date"
                   value="${requestScope.date}"
                   min="" max="">
            <input type="submit" name="btnAction" value="search" />
        </form>
        <h1 style="color: red">${requestScope.errBooking}</h1>
        <table style="border: none; text-align: center">
            <thead>
                <tr>
                    <th>ProductID</th>
                    <th>Name</th>
                    <th>Color</th>
                    <th>CategoryName</th>
                    <th>Quanlity</th>
                    <th>CreateDate</th>
                    <th>function</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty requestScope.listReourcesPagnination}">
                    <c:forEach var="item" items="${requestScope.listReourcesPagnination}">
                        <tr style="color: ${requestScope.idProductOutOfNumber eq item.getProductID() || item.getQuanlity() eq 0  ? "red" : "black"}">
                            <td>${item.getProductID()}</td>
                            <td>${item.getProductName()}</td>
                            <td>${item.getColor()}</td>
                            <c:forEach var="itemCategory" items="${requestScope.listCategories}">
                                <c:if test="${item.getCategoryID() eq itemCategory.getCategoryID()}">
                                    <td>${itemCategory.getCategoryName()}</td>       
                                </c:if>
                            </c:forEach>
                            <td>${item.getQuanlity()}</td>
                            <td>${item.getCreateDate()}</td>                            
                            <td>
                                <form method="POST" action="DispatchController">
                                    <input type="hidden" name="productID" value="${item.getProductID()}" />
                                    <input type="submit" name="btnAction" value="Booking" ${item.getQuanlity() eq 0 ? "disabled": ""}/>
                                </form>
                            </td>
                            <c:if test="${requestScope.productID eq item.productID}">
                                <c:if test="${not empty requestScope.bookingSuccess}">
                                    <td style="color: green">
                                        ${requestScope.bookingSuccess}
                                    </td>
                                </c:if>     
                            </c:if>
                            <c:if test="${requestScope.productID eq item.productID}">
                                <c:if test="${not empty requestScope.errBooking}">
                                    <td style="color: red">
                                        ${requestScope.errBooking}
                                    </td>
                                </c:if>      
                            </c:if>
                        </tr>
                    </c:forEach>
                </c:if>
                <!--search by category-->
                <c:if test="${not empty requestScope.noValue}">
                <h1 style="color: green">${requestScope.noValue} </h1>
            </c:if>
        </tbody>
    </table>

    <div class="pagination">
        <c:forEach  begin="1" end="${requestScope.pageSize}" var="i">
            <a id="${i}" href="DispatchController?btnAction=search&index=${i}&nameSearch=${requestScope.nameSearch}&category=${requestScope.category}&date=${requestScope.date}">${i}</a>
        </c:forEach>
    </div>
    <script>
        document.getElementById('${index}').style.color = "red";
    </script>
</body>
</html>
