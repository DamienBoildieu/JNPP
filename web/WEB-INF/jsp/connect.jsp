<%@ include file="include/jsptags.jsp"%>
<html>
    <head>
        <%@ include file="include/materialize.jsp"%>
        <%@ include file="include/title.jsp"%>
    </head>
    <body>
        <header>
            <%@ include file="include/banner.jsp"%>
        </header>
        <main>
            <%@ include file="include/alerts.jsp"%>
            <div class="container" style="margin-top: 10%">
                <div class="row">
                    <div class='card-panel white col s6 offset-s3 center-align'>
                        <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                            <div class="card blue s12">
                                <p class="white-text flow-text">Connectez-vous</p>
                            </div>
                            <form method="POST" action="connect.htm">
                                <div class="row">
                                    <div class="col s12">
                                        <div class="input-field">
                                            <label for="account"> Identifiant </label> <input type="text"
                                                                                              class="validate" name="account" id="account">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12">
                                        <div class="input-field">
                                            <label for="psswd"> Mot de passe </label> <input type="password"
                                                                                             class="validate" name="password" id="password">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12">
                                        <input type="submit" value="Se connecter" class="btn blue" />
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <%@ include file="include/notif.jsp"%>
        </main>
        <footer>
        </footer>
        <%@ include file="include/javascript.jsp"%>
        <%@ include file="include/commonscripts.jsp"%>
    </body>
</html>