<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/ajax-search.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/account/main">
                ${sessionScope.acc.lastName} ${sessionScope.acc.name}
            </a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <c:choose>
                    <c:when test="${requestScope.count == 0}">
                        <li><a href="${pageContext.request.contextPath}/account/friends">Friends</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/account/friends">Friends <span class="badge">${requestScope.count}</span></a></li>
                    </c:otherwise>
                </c:choose>

                <c:choose>
                    <c:when test="${requestScope.msgCount == 0}">
                        <li><a href="${pageContext.request.contextPath}/account/friends">Messages</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Messages <span class="badge">${requestScope.msgCount}</span></a>
                            <ul class="dropdown-menu">
                                <c:forEach var="sender" items="${requestScope.senders}">
                                    <li><a href="${pageContext.request.contextPath}/account/messages?friendId=${sender.id}">${sender.name} ${sender.lastName} <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
                <li><a href="${pageContext.request.contextPath}/account/update">Update Account</a></li>
            </ul>
            <form class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <input id="accountName" type="text" class="form-control" placeholder="Search">
                </div>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<script>
    $(function () {
        $("#accountName").autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "<c:url value="/searchByAccounts"/>",
                    data: {
                        filter: request.term
                    },
                    success: function (data) {
                        response($.map(data, function (account, i) {
                            return {id: account.id, label: account.name + ' ' + account.lastName}
                        }));
                    }
                });
            },
            minLength: 2,
            select: function (event, ui) {
                location.href = '<c:url value="/account/acc?id="/>' + ui.item.id;
            }
        });
    });
</script>

</body>
</html>
