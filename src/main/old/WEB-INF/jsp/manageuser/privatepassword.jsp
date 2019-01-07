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
            <div class="container" style="margin-top: 5%">
                <div class="row">
                    <div class='card-panel white col s6 offset-s3 center-align'>
                        <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                            <div class="card blue s12">
                                <p class="flow-text white-text">Changez votre mot de passe</p>
                            </div>
                            <form method="POST" action="privatepassword.htm">
                                <div class="row">
                                    <div class="input-field col s12">
                                        <label for="id">Votre identifiant</label> <input type="text"
                                                                                          class="validate" name="id" id="id" required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12">
                                        <label for="firstName">Votre prénom</label> <input type="text"
                                                                                          class="validate" name="firstName" id="firstName" required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12">
                                        <label for="lastName">Votre nom de famille</label> <input type="text"
                                                                                         class="validate" name="lastName" id="lastName" required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12">
                                            <label for="email">Votre adresse email</label> <input type="email"
                                                                                    class="validate" name="email" id="email" required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12">
                                        <input type="submit" value="Confirmer" class="btn blue" />
                                    </div>
                                </div>
                            </form>
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