<%-- 
    Document   : clientform
    Created on : feb 18, 2019, 12:30:13 p.m.
    Author     : antoniotortosa
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./resources/css/style.css">
        <title>New client page</title>
    </head>
    <body>
        <h1>Client sign-in</h1>
        
        <c:if test="${not empty requestScope.mistakes}">
            <c:forEach items="${requestScope.mistakes}" var="mistake">   
                <div class="isa_error">
                    <em class="fa fa-times-circle"></em>
                    Please correct this issue: ${mistake.message}
                </div>
            </c:forEach>
        </c:if>
        
        <form method="POST" action="client">
            <div> 
                <label for="name">First name</label><br>
                <input value="${requestScope.client.name}" type="text" id="name" name="firstName" />
            </div>
            <div>
                <label for="surname">Family name</label><br>
                <input value="${requestScope.client.surname}" type="text" id="surname" name="familyName" />
            </div>
            <div>
                <label for="birthdate">Date of birth</label><br>
                <input value="${requestScope.dateString}" type="date" id="birthdate" name="birthDate" />
            </div>
            <div>
                <label for="membership">Choose a membership</label><br>
                <select name="membership" id="membership">
                    <option <c:if test="${requestScope.client.membershipType == 1}"> selected="selected"</c:if> value="1">Standard</option>
                    <option <c:if test="${requestScope.client.membershipType == 2}"> selected="selected"</c:if> value="2">Premium</option>
                    <option <c:if test="${requestScope.client.membershipType == 3}"> selected="selected"</c:if> value="3">Gold</option>
                    <option <c:if test="${requestScope.client.membershipType == 4}"> selected="selected"</c:if> value="4">VIP</option>
                </select>
            </div>
            <div>
                <label for="height">Height</label><br>       
                <input value="${requestScope.client.height}" type="number" step="0.01" id="height" name="height" />
            </div>
            <div>
                <label for="weight">Weight</label><br>
                <input value="${requestScope.client.weight}" type="number" step="0.01" id="weight" name="weight" />
            </div>
            <button type="submit">Submit</button>
        </form>
    </body>
</html>
