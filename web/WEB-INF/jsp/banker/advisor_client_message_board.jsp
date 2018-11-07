<%@ include file="../include/jsptags.jsp" %>
<html>
    <head>
        <%@ include file="../include/materialize.jsp" %>
        <title>Gestion des conseillers</title>
    </head>
    <body>
        <%@ include file="banker_header.jsp" %>
        <div class="container" style="overflow: auto; max-height: 60vh;">
            <table class="table striped card">
                <tbody>
                    <c:forEach items="${messages}" var="message">
                    <tr>
                        <td class="
                            <c:choose>
                                <c:when test="${message.direction eq 'CLIENT_TO_ADVISOR'}">left-align light-grey</c:when>
                                <c:otherwise>right-align white</c:otherwise>
                            </c:choose>">
                            ${message.content}
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="container">
            <form method="POST" action="/JNPP/banquier/conseiller/client/messages.htm" class="card">
                <div class="row">
                    <div class="col s10">
                        <input type="text" name="content" class="left-align">
                    </div>
                    <input type="hidden" name="login" value="${client.login}">
                    <div class="col s2">
                        <input type="submit" value="Envoyer" class=" btn blue center-align" style="width: 100%; margin-top: 5%;">
                    </div>
                </div>
            </form>
        </div>        
        <%@ include file="../include/javascript.jsp"%>
        <%@ include file="../include/commonscripts.jsp"%>
    </body>
</html>
