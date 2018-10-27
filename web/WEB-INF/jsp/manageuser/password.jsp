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
                    <div class='card-panel white col s6 offset-s3 center-align'>
                        <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                            <div class="card blue s12">
                                <p class="flow-text white-text">Changez votre mot de passe</p>
                            </div>
                            <form method="POST" action="personalsignup.htm">
                                <div class="row">
                                    <div class="col s12">
                                        <div class="input-field">
                                            <label for="password">Nouveau mot de passe</label> <input type="password"
                                                                                              class="validate" name="password" id="password" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12">
                                        <div class="input-field">
                                            <label for="confim">Confirmer le mot de passe</label> <input type="password"
                                                                                             class="validate" name="confirm" id="confrim" required>
                                        </div>
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