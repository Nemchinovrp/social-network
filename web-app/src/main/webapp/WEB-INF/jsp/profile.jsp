<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="menu.jsp" %>

<html>
<head>
    <title>${sessionScope.acc.name}'s home page</title>
</head>

<style type="text/css">
    .table th, .table td {
        border-top: none !important;
    }

    .table tr td:first-child {
        text-align: right;
    }
</style>

<body>

<div class="container">

    <div class="row">

        <div class="col-md-2 hidden-xs">
            <c:choose>
                <c:when test="${sessionScope.acc.image == null}">
                    <img src="${pageContext.request.contextPath}/resources/img/default.jpg" class="img-responsive">
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/account/display_avatar" class="img-responsive">
                </c:otherwise>
            </c:choose>
        </div>

        <div class="col-md-6">
            <div class="thumbnail">
                <div class="caption-full">
                    <h4>Account Info</h4>
                    <table class="table table-condensed">
                        <tr>
                            <td>Name:</td>
                            <td><strong>${sessionScope.acc.name}</strong></td>
                        </tr>
                        <tr>
                            <td>Surname:</td>
                            <td><strong>${sessionScope.acc.lastName}</strong></td>
                        </tr>
                        <tr>
                            <td>Email:</td>
                            <td><strong>${sessionScope.acc.email}</strong></td>
                        </tr>
                        <tr>
                            <td>Birth date:</td>
                            <td><strong>${sessionScope.acc.dateOfBirth}</strong></td>
                        </tr>

                        <c:if test="${sessionScope.acc.skype != null}">
                            <tr>
                                <td>Skype:</td>
                                <td><strong>${sessionScope.acc.skype}</strong>
                                </td>
                            </tr>
                        </c:if>

                        <c:if test="${sessionScope.acc.icq != null}">
                            <tr>
                                <td>Icq:</td>
                                <td><strong>${sessionScope.acc.icq}</strong></td>
                            </tr>
                        </c:if>

                        <c:if test="${sessionScope.acc.homeAddress != null}">
                            <tr>
                                <td>Home address:</td>
                                <td><strong>${sessionScope.acc.homeAddress}</strong></td>
                            </tr>
                        </c:if>

                        <c:if test="${sessionScope.acc.workAddress != null}">
                            <tr>
                                <td>Work address:</td>
                                <td><strong>${sessionScope.acc.workAddress}</strong></td>
                            </tr>
                        </c:if>

                        <c:if test="${sessionScope.acc.addInfo != null}">
                            <tr>
                                <td>Additional Info:</td>
                                <td><strong>${sessionScope.acc.addInfo}</strong>
                                </td>
                            </tr>
                        </c:if>

                        <c:if test="${sessionScope.acc.phones != null && sessionScope.acc.phones.size() != 0}">
                            <tr>
                                <td>Phones:</td>
                                <td>
                                    <c:forEach var="phone" items="${sessionScope.acc.phones}">
                                        <strong>${phone}</strong><br>
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:if>

                        <tr>
                            <td>Registration date:</td>
                            <td><strong>${sessionScope.acc.registrationDate}</strong></td>
                        </tr>
                    </table>
                </div>
            </div>

            <c:forEach var="sender" items="${wallMessages}">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h6>
                            <a href="${pageContext.request.contextPath}/account/acc?id=${sender.sender.id}"><strong>   ${sender.sender.name} ${sender.sender.lastName}</strong></a>
                            wrote message at <fmt:formatDate timeZone="" pattern="yy.MM.dd HH:mm" value="${sender.creatingDate}"/>
                            <a href="${pageContext.request.contextPath}/account/deleteWallMsg?msgId=${sender.id}">
                            <button type="button" class="close" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            </a>
                        </h6>
                    </div>
                    <div class="panel-body">
                        <blockquote>
                            <p>${sender.message}</p>
                        </blockquote>
                    </div>
                </div>
            </c:forEach>

            <div class="panel panel-success">
                <div class="panel-heading">Write a message:</div>
                <div class="panel-body">
                    <form action="${pageContext.request.contextPath}/addWallMsg?senderId=${sessionScope.acc.id}&receiverId=${sessionScope.acc.id}"
                          method="post">
                        <div class="well well-sm">
                            <textarea style="resize: vertical" class="form-control" name="wallPost" rows="3"></textarea>
                        </div>
                        <input type="submit" value="Send" class="btn btn-default pull-right">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
