<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="menu.jsp" %>
<html>
<head>
    <title>${sessionScope.acc.name}'s friends</title>
    <style>
        .well {
            margin: auto;
            width: 320px;
        }
    </style>
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <c:choose>
            <c:when test="${requests.size() == 0}">
            </c:when>
            <c:otherwise>
                <div class="col-md-2"></div>
                <div class="col-md-8 row">
                    <div class="panel panel-success">
                        <div class="panel-heading"><strong>Incoming friendRequests</strong></div>
                        <div class="panel-body">

                            <c:forEach var="request" items="${requests}">
                                <div class="col-md-4 media well well-sm">
                                    <div class="media-left">
                                        <c:choose>
                                            <c:when test="${request.sender.image == null}">
                                                <a href="${pageContext.request.contextPath}/account/acc?id=${request.sender.id}">
                                                    <img src="${pageContext.request.contextPath}/resources/img/default.jpg"
                                                         alt="d"
                                                         width="64"
                                                         height="64">
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/account/acc?id=${request.sender.id}">
                                                    <img src="${pageContext.request.contextPath}/account/friend/display_avatar?id=${request.sender.id}"
                                                         alt="a" width="64" height="64">
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="media-body">
                                        <a href="${pageContext.request.contextPath}/account/acc?id=${request.sender.id}">
                                                ${request.sender.name}
                                                ${request.sender.lastName}
                                        </a><br>
                                        <a href="${pageContext.request.contextPath}/account/declineFriendShip?reqId=${request.id}"
                                           class="btn btn btn-danger" role="button">Decline</a>
                                        <a href="${pageContext.request.contextPath}/account/acceptFriendship?id=${request.sender.id}&reqId=${request.id}"
                                           class="btn btn btn-info" role="button">Accept</a>
                                    </div>
                                </div>
                            </c:forEach>

                        </div>
                    </div>
                </div>
                <div class="col-md-2"></div>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-sm-8">
            <c:forEach var="friend" items="${sessionScope.acc.friends}">
                <div class="col-md-4 media well well-sm">
                    <div class="media-left">
                        <c:choose>
                            <c:when test="${friend.image == null}">
                                <a href="${pageContext.request.contextPath}/account/acc?id=${friend.id}">
                                    <img src="${pageContext.request.contextPath}/resources/img/default.jpg" alt="d"
                                         width="64"
                                         height="64">
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/account/acc?id=${friend.id}">
                                    <img src="${pageContext.request.contextPath}/account/friend/display_avatar?id=${friend.id}"
                                         alt="a" width="64" height="64">
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="media-body">
                        <a href="${pageContext.request.contextPath}/account/acc?id=${friend.id}">
                                ${friend.name}
                                ${friend.lastName}
                        </a><br>
                        <a href="${pageContext.request.contextPath}/account/deleteFromFriend?id=${friend.id}"
                           class="btn btn btn-danger" role="button">Delete</a>
                        <a href="${pageContext.request.contextPath}/account/messages?friendId=${friend.id}"
                           class="btn btn btn-info" role="button">Message</a>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="col-md-2"></div>
    </div>
</div>
</body>
</html>