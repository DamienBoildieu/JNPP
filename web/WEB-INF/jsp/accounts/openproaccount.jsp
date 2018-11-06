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
            <div class="container" style="margin-top: 3%">
                <div class="row">
                    <div class='card-panel white col s12 center-align'>
                        <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                            <div class="row">
                                <div class="card blue s12">
                                    <p class="white-text flow-text">Choisissez le type de compte à ouvrir</p>
                                </div>
                            </div>
                            <ul class="collapsible">
                                <li>
                                    <div class="collapsible-header">Compte courant</div>
                                    <div class="collapsible-body">
                                        <div class="col s12">
                                            <form method="POST" action="opencurrentaccount.htm">
                                                <div class="row">
                                                    <div class="col s12">
                                                        <input type="submit" value="Compte courant" class="btn blue" />
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="collapsible-header">Compte titres</div>
                                    <div class="collapsible-body">
                                        <div class="col s12">
                                            <form method="POST" action="openshareaccount.htm">
                                                <div class="row">
                                                    <div class="col s12">
                                                        <input type="submit" value="Compte titres" class="btn blue" />
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </li>
                            </ul>                        
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