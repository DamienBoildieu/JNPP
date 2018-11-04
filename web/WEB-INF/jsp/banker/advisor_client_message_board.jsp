<%@ include file="../include/jsptags.jsp" %>
<html>
    <head>
        <%@ include file="../include/materialize.jsp" %>
        <title>Gestion des conseillers</title>
    </head>
    <body>
        <%@ include file="banker_header.jsp" %>
        <div class="container">
            <table class="responsive-table striped card">
                <tbody>
                    <c:forEach items="${messages}" var="message">
                    <tr>
                        <td class="
                            <c:choose>
                                <c:when test="${message.direction eq 'CLIENT_TO_ADVISOR'}">left-align</c:when>
                                <c:otherwise>right-align</c:otherwise>
                            </c:choose>">
                            ${message.content}
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="container">
            <form method="POST" action="/JNPP/conseiller/client/messages.htm" class="card">
                <input type="text" name="content" class="center-align">
                <input type="hidden" name="login" value="${client.login}">
                <input type="submit" value="Envoyer" class="waves-effect waves-light btn-small center-align">
            </form>
        </div>
    </body>
</html>
