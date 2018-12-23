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
            <div class="container" style="margin-top: 10%">
                <div class="row">
                    <div class='card-panel blue col s8 offset-s2'>
                        <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                            <p class=" flow-text white-text">Inscription réussie, vous allez recevoir un mail contenant votre identifiant et votre mot de passe</p>
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
