<%-- 
    Document   : client_cof
    Created on : feb 18, 2019, 4:01:03 p.m.
    Author     : antoniotortosa
--%>

<%@page import="edu.iit.sat.itmd4515.atortosagarrido.model.Client"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Client Confirmation</title>
    </head>
    <body>
        <h1>Your information is good and saved!</h1>
        <div>
            <p><strong>Full name:</strong> ${requestScope.client.name} ${requestScope.client.surname}</p>
            <p><strong>Birth date:</strong> ${requestScope.dateString}</p>
            <p><strong>Type of membership:</strong>${requestScope.membership}</p>
            <p><strong>Height:</strong> ${requestScope.client.height}m</p>
            <p><strong>Weight:</strong> ${requestScope.client.weight}kg</p>
        </div>
    </body>
</html>
