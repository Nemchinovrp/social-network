<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="menu.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Conversation</title>
    <style>
        .conversation{
            overflow: scroll;
            width: 100%;
            height: 500px;
            padding: 10px;
            overflow-x: hidden;
        }
    </style>
</head>
<body>

<div class="container">
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
    <div class="col-md-8">
        <div class="conversation">
            <c:forEach var="msg" items="${messageList}">
                <c:choose>
                    <c:when test="${msg.sender == sessionScope.acc}">
                        <div style="width: 51%; float: left" class="alert alert-success" role="alert">
                            <small>
                            <fmt:formatDate timeZone="" pattern="yy.MM.dd HH:mm" value="${msg.creatingDate}"/></small><br>
                        ${msg.message}
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div style="width: 51%; float: right" class="alert alert-info" role="alert">
                            <small>
                            <fmt:formatDate timeZone="" pattern="yy.MM.dd HH:mm" value="${msg.creatingDate}"/></small><br>
                        ${msg.message}</div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

        </div>
        <div class="panel panel-success">
            <div class="panel-body">
                <form action="${pageContext.request.contextPath}/sendMsg?senderId=${sessionScope.acc.id}&receiverId=${friend.id}"
                      method="post">
                    <div class="well well-sm">
                        <textarea style="resize: vertical" class="form-control" name="text" rows="3"></textarea>
                    </div>
                    <input type="submit" value="Send" class="btn btn-default pull-right">
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-2 hidden-xs">
        <c:choose>
            <c:when test="${friend.image == null}">
                <img src="${pageContext.request.contextPath}/resources/img/default.jpg" class="img-responsive">
            </c:when>
            <c:otherwise>
                <img src="${pageContext.request.contextPath}/account/friend/display_avatar?id=${friend.id}" class="img-responsive">
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>
