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
            <div class="container">
                <div class="row">
                    <div class='card-panel white col s6 offset-s3 center-align'>
                        <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                            <c:forEach items="${appoints}" var="appoint">
                                <div class="card blue s12">
                                    <div class="row">
                                        <p class="white-text">
                                            ${appoint.day}/${appoint.month}/${appoint.year} ${appoint.hour}h${appoint.minute} avec ${appoint.getAdvisor().getIdentity().getFirstname()} ${appoint.getAdvisor().getIdentity().getLastname()}
                                        </p>
                                    </div>
                                    <div class="row">
                                        <form method="POST" action="cancelappoint.htm">
                                            <input type="hidden" id="id" name="id" value=${appoint.id}>
                                            <div class="row">
                                                <div class="col s12">
                                                    <input type="submit" value="Annuler" class="btn-small blue" />
                                                </div>
                                            </div>
                                        </form>      
                                    </div>
                                </div>
                            </c:forEach>
                            <div class="row">
                                <form method="POST" action="makeappoint.htm" class="valign-wrapper">
                                    <input type="hidden" id="id" name="id" value=${appoint.id}>
                                    <div class="col s4">
                                        <label for="date">Jour</label> <input type="date"
                                                                                  class="validate" name="date" id="date" required>
                                    </div>
                                    <div class="col s4">
                                        <label for="time">Heure</label> <input type="time"
                                                                                  class="validate" name="time" id="time" required>
                                    </div>
                                    <div class="col s4">
                                        <input type="submit" value="Prendre rendez-vous" class="btn blue" />
                                    </div>
                                </form>      
                            </div>
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