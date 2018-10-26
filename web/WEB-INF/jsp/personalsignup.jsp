<%@ include file="include/jsptags.jsp"%>
<html>
    <head>
        <%@ include file="include/head.jsp"%>
    </head>
    <body>
        <header>
            <%@ include file="include/banner.jsp"%>
        </header>
        <main>
            <%@ include file="include/alerts.jsp"%>
            <div class="container" >
                <div class="row">
                    <div class='card-panel white col s6 offset-s3 center-align'>
                        <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                            <div class="card blue s12">
                                <p class="flow-text white-text">Inscrivez-vous</p>
                            </div>
                            <form method="POST" action="personalsignup.htm">
                                <div class="row">
                                    <div class="col s12">
                                        <div class="input-field">
                                            <label for="lastName"> Nom </label> <input type="text"
                                                                                              class="validate" name="lastName" id="lastName" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12">
                                        <div class="input-field">
                                            <label for="firstName"> Prénom </label> <input type="text"
                                                                                             class="validate" name="firstName" id="firstName" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12">
                                        <select name="genre">
                                            <option value="1">Homme</option>
                                            <option value="2">Femme</option>
                                        </select>
                                        <label>Sexe</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12">
                                        <label for="birthday"> Date de naissance </label> <input type="date"
                                                                                             class="validate" name="birthday" id="birthday" required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12">
                                        <div class="input-field">
                                            <label for="email"> Email </label> <input type="email"
                                                                                             class="validate" name="email" id="email" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12">
                                        <div class="input-field">
                                            <label for="phone"> Téléphone </label> <input type="tel"
                                                                                             class="validate" name="phone" id="phone" 
                                                                                             pattern="[0-9]{10}" required>
                                        </div>
                                    </div>
                                </div>                                
                                <div class="row">
                                    <div class="col s12">
                                        <input type="submit" value="S'isncrire" class="btn blue" />
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
        <script src="${pageContext.request.contextPath}/scripts/select.js"></script>
    </body>
</html>