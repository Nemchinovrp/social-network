<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="menu.jsp" %>
<html lang="en">
<head>
    <title>Update ${sessionScope.acc.name}'s info</title>

    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/date_picker.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.mask.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/date_picker.css"/>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.validate.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/additional-methods.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/phone.date.masks.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/phone_adding.js"></script>

    <style>
        .error {
            color: red;
        }
    </style>

</head>
<body>
<p style="color:red" align="center">${requestScope.error}</p><br>
<p style="color:green" align="center">${requestScope.success}</p><br>

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
            <form id="updateform" action="${pageContext.request.contextPath}/account/update/check" method="post" enctype="multipart/form-data">
                <div class="thumbnail">
                    <div class="caption-full">
                        <h4>Update Info</h4>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <tr>
                                    <td>First name</td>
                                    <td><input type="text" class="form-control" name="name"
                                               value="${sessionScope.acc.name}"></td>
                                </tr>

                                <tr>
                                    <td>Last name</td>
                                    <td><input type="text" class="form-control"
                                               value="${sessionScope.acc.lastName}"
                                               name="lastName"></td>
                                </tr>

                                <tr>
                                    <td>Email</td>
                                    <td><input type="text" class="form-control" name="email"
                                               value="${sessionScope.acc.email}"></td>
                                </tr>

                                <tr>
                                    <td>Password</td>
                                    <td><input type="text" class="form-control" name="password"
                                               value="${sessionScope.acc.password}"></td>
                                </tr>

                                <tr>
                                    <td>Image</td>
                                    <td>
                                        <input type="file" name="img" value="Upload file">
                                    </td>
                                </tr>

                                <tr>
                                    <td>Home address</td>
                                    <td><input type="text" class="form-control"
                                               value="${sessionScope.acc.homeAddress}" name="homeAddress"></td>
                                </tr>

                                <tr>
                                    <td>Work address</td>
                                    <td><input type="text" class="form-control"
                                               value="${sessionScope.acc.workAddress}" name="workAddress"></td>
                                </tr>

                                <tr>
                                    <td>ICQ</td>
                                    <td><input type="text" class="form-control" value="${sessionScope.acc.icq}"
                                               name="icq"></td>
                                </tr>

                                <tr>
                                    <td>Skype</td>
                                    <td><input type="text" class="form-control" value="${sessionScope.acc.skype}"
                                               name="skype"></td>
                                </tr>

                                <tr>
                                    <td>
                                        <p>Birth date: </p>
                                    </td>
                                    <td>
                                        <input type="text" class="form-control" id="datepicker" name="dateOfBirth"
                                               value="${sessionScope.acc.dateOfBirth}"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Phones:</td>
                                    <td>
                                        <div id="addPhone">

                                                <c:choose>
                                                    <c:when test="${sessionScope.acc.phones.size() == 0}">

                                                        <div class="input-group">
                                                            <input type="text"
                                                                   class="form-control"
                                                                   name="phones[]">
                                                            <span class="input-group-btn">
                                                                    <button class="btn btn-default" type="button"
                                                                            onclick="addPhone()">
                                                                        <span class="glyphicon glyphicon-plus"></span>
                                                                    </button>
                                                            </span>
                                                        </div>

                                                    </c:when>

                                                    <c:otherwise>

                                                        <c:forEach varStatus="loop" var="phone" items="${sessionScope.acc.phones}">
                                                            <c:choose>

                                                                <c:when test="${loop.index == 0}">
                                                                    <div class="input-group">
                                                                        <input type="text"
                                                                               class="form-control" value="${phone}"
                                                                               name="phones[]">
                                                                        <span class="input-group-btn">
                                                                    <button class="btn btn-default" type="button"
                                                                            onclick="addPhone()">
                                                                        <span class="glyphicon glyphicon-plus"></span>
                                                                    </button>
                                                            </span>
                                                                    </div>
                                                                </c:when>

                                                                <c:otherwise>
                                                                    <div class="input-group">
                                                                        <input type="text"
                                                                               class="form-control" value="${phone}"
                                                                               name="phones[]">
                                                                        <span class="input-group-btn">
                                                                    <button class="btn btn-default" type="button"
                                                                            onclick="removePhone(this)">
                                                                        <span class="glyphicon glyphicon-minus"></span>
                                                                    </button>
                                                                </span>
                                                                    </div>
                                                                </c:otherwise>

                                                            </c:choose>
                                                        </c:forEach>

                                                    </c:otherwise>

                                                </c:choose>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>About me</td>
                                    <td>
                                        <input type="text" class="form-control" name="addInfo"
                                               value="${sessionScope.acc.addInfo}"/>
                                    </td>
                                </tr>
                            </table>
                            <button type="submit" class="btn btn-default pull-right">Submit</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
