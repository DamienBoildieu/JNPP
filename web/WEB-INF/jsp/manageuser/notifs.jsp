<%@ include file="../include/jsptags.jsp"%>
<html>
    <head>
        <%@ include file="../include/head.jsp"%>
    </head>
    <body>
        <header>
            <%@ include file="../include/banner.jsp"%>
        </header>
        <main>
            <%@ include file="../include/alerts.jsp"%>
            <div class="container" >
                <div class="row">
                    <div class='card-panel white col s6 offset-s3 center-align'>
                        <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                            <c:forEach items="${notifs}" var="element">
                                <c:choose>
                                    <c:when test="${!element.seen}">
                                        <div class="card-panel blue s12">
                                            <span class="white-text">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="card-panel white s12">
                                            <span class="blue-text">
                                    </c:otherwise>
                                </c:choose>
                                               ${element.day}/${element.month}/${element.year} : ${element.message}
                                            </span>
                                        </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <%@ include file="../include/notif.jsp"%>
        </main>
        <footer>
        </footer>
        <%@ include file="../include/javascript.jsp"%>
        <%@ include file="../include/commonscripts.jsp"%>
    </body>
</html>