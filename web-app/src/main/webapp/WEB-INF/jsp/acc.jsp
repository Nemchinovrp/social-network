<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="menu.jsp" %>

<html>
<head>
    <title>${requestScope.acc.name}'s home page</title>
    <style type="text/css">
        .table th, .table td {
            border-top: none !important;
        }

        .table tr td:first-child {
            text-align: right;
        }
    </style>
</head>

<body>

<div class="container">

    <div class="row">

        <div class="col-md-2 hidden-xs">
            <c:choose>
                <c:when test="${requestScope.acc.image == null}">
                    <img src="${pageContext.request.contextPath}/resources/img/default.jpg" class="img-responsive">
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/account/friend/display_avatar?id=${requestScope.acc.id}"
                         class="img-responsive">
                </c:otherwise>
            </c:choose>
            <br>
            <c:choose>
            <c:when test="${isFriend}">
                <a href="${pageContext.request.contextPath}/account/deleteFromFriend?id=${acc.id}"
                   class="btn btn-danger btn-block" role="button">Delete</a>
                <a href="${pageContext.request.contextPath}/account/messages?friendId=${acc.id}"
                   class="btn btn-info btn-block" role="button">Message</a>
            </c:when>
            <c:otherwise>
            <c:choose>
            <c:when test="${isRequested}">
                <button type="button" class="btn btn-primary btn-block disabled">Pending...</button>
            </c:when>
            <c:otherwise>
            <a href="${pageContext.request.contextPath}/account/requestFriend?id=${acc.id}"
               class="btn btn-primary btn-block">Add to friend<a>
                </c:otherwise>
                </c:choose>
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
                            <td><strong>${requestScope.acc.name}</strong></td>
                        </tr>
                        <tr>
                            <td>Surname:</td>
                            <td><strong>${requestScope.acc.lastName}</strong></td>
                        </tr>
                        <tr>
                            <td>Email:</td>
                            <td><strong>${requestScope.acc.email}</strong></td>
                        </tr>
                        <tr>
                            <td>Birth date:</td>
                            <td><strong>${requestScope.acc.dateOfBirth}</strong></td>
                        </tr>

                        <c:if test="${requestScope.acc.skype != null && sessionScope.acc.skype.length() != 0}">
                            <tr>
                                <td>Skype:</td>
                                <td><strong>${requestScope.acc.skype }</strong>
                                </td>
                            </tr>
                        </c:if>

                        <c:if test="${requestScope.acc.icq != null && sessionScope.acc.icq.length() != 0}">
                            <tr>
                                <td>Icq:</td>
                                <td><strong>${requestScope.acc.icq}</strong></td>
                            </tr>
                        </c:if>

                        <c:if test="${requestScope.acc.homeAddress != null && sessionScope.acc.homeAddress.length() != 0}">
                            <tr>
                                <td>Home address:</td>
                                <td><strong>${requestScope.acc.homeAddress}</strong></td>
                            </tr>
                        </c:if>

                        <c:if test="${requestScope.acc.workAddress != null && sessionScope.acc.workAddress.length() != 0}">
                            <tr>
                                <td>Work address:</td>
                                <td><strong>${requestScope.acc.workAddress}</strong></td>
                            </tr>
                        </c:if>

                        <c:if test="${requestScope.acc.addInfo != null && sessionScope.acc.addInfo.length() != 0}">
                            <tr>
                                <td>Additional Info:</td>
                                <td><strong>${requestScope.acc.addInfo}</strong>
                                </td>
                            </tr>
                        </c:if>

                        <c:if test="${requestScope.acc.phones != null && requestScope.acc.phones.size() != 0}">
                            <tr>
                                <td>Phones:</td>
                                <td>
                                    <c:forEach var="phone" items="${requestScope.acc.phones}">
                                        <strong>${phone}</strong><br>
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:if>

                        <tr>
                            <td>Registration date:</td>
                            <td><strong>${requestScope.acc.registrationDate}</strong></td>
                        </tr>
                    </table>
                </div>
            </div>

            <c:forEach var="sender" items="${wallMessages}">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h6>
                            <a href="${pageContext.request.contextPath}/account/acc?id=${sender.sender.id}"><strong>   ${sender.sender.name} ${sender.sender.lastName}</strong></a>
                            wrote message at <fmt:formatDate pattern="yy.MM.dd HH:mm" value="${sender.creatingDate}"/>
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
                    <form action="${pageContext.request.contextPath}/addWallMsg?senderId=${sessionScope.acc.id}&receiverId=${requestScope.acc.id}"
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
