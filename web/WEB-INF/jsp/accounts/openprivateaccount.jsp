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
                            <div class="row">
                                <div class="card blue s12">
                                    <p class="white-text flow-text">Choisissez le type de compte � ouvrir</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s6">
                                    <form method="POST" action="opencurrentaccount.htm">
                                        <div class="row">
                                            <div class="col s12">
                                                <input type="submit" value="Compte courant" class="btn blue" />
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="col s6">
                                    <form method="POST" action="opensavingaccount.htm">
                                        <div class="row">
                                            <div class="row">
                                                <div class="col s12">
                                                    <input type="submit" value="Livret" class="btn blue" />
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="input-field col s12">
                                                    <select name="book">
                                                        <c:forEach items="${books}" var="book">
                                                            <option value="${book.name}">${book.name}</option>        
                                                        </c:forEach>
                                                    </select>
                                                    <label>Nom du livret</label>
                                                </div>
                                            </div>
                                        </div>                                        
                                    </form>
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
                <script src="${pageContext.request.contextPath}/scripts/select.js"></script>

    </body>
</html>