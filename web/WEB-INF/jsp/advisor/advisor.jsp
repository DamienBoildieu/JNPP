<%@ include file="../include/jsptags.jsp"%>
<html>
    <head>
        <%@ include file="../include/materialize.jsp"%>
        <%@ include file="../include/title.jsp"%>
    </head>
    <body>
        <header>
            <%@ include file="../include/banner.jsp"%>
        </header>
        <main>
            <%@ include file="../include/alerts.jsp"%>
            <div class="container">
                <div class="row">
                    <div class='col s10 offset-s1 center-align'>
                        <div class="col s12">
                            <div class="card blue">
                                <div class='container' style="margin-bottom: 20px; margin-top: 40px;">
                                    <div class="card-content white-text">
                                        <span class="card-title center-align">${advisor.getIdentity().getFirstname()} ${advisor.getIdentity().getLastname()}</span>
                                        <%--<div class="row">
                                            <div class="col s12">
                                                <p>${advisor.agency}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col s12">
                                                <p>${advisor.phone}</p>
                                            </div>
                                        </div>--%>
                                        <div class="row">
                                            <div class="col s12">
                                                <a class="white-text hoverable" href="<c:url value='/message.htm' />"><b>Envoyez un message </b><i class="material-icons">message</i></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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