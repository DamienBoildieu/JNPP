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
                        </div>
                    </div>
                </div>
                 <ul class="collapsible">
                    <li>
                        <div class="collapsible-header">Compte courant</div>
                        <div class="collapsible-body white center-align">
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
                        <div class="collapsible-header">Livret</div>
                        <div class="collapsible-body white center-align">
                            <div class="col s12">
                                <form method="POST" id="savingAccount"action="opensavingaccount.htm">                                                   
                                    <div class="row">
                                        <div class="input-field col s12">
                                            <select name="bookName" form="savingAccount">
                                                <c:forEach items="${books}" var="book">
                                                    <option value="${book.name}">${book.name}</option>        
                                                </c:forEach>
                                            </select>
                                            <label>Nom du livret</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col s12">
                                            <input type="submit" value="Livret" class="btn blue" />
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="collapsible-header">Compte joint</div>
                        <div class="collapsible-body white center-align">
                            <div class="col s12">
                                <form method="POST" id="jointAccount" action="openjointaccount.htm">                                                
                                    <input type="hidden" id="nbClients" name="nbClients" value=${nbClients}>
                                    <c:forEach begin="1" end="${nbClients}" var="index">
                                        <div class="row">
                                            <div class="input-field col s4">
                                                <label for="lastName${index}">Nom</label> <input type="text"
                                                                                                  class="validate" name="lastName${index}" id="lastName${index}" required>
                                            </div>
                                            <div class="input-field col s4">
                                                <label for="firstName${index}">Prénom</label> <input type="text"
                                                                                                  class="validate" name="firstName${index}" id="firstName${index}" required>
                                            </div>
                                            <div class="input-field col s4">
                                                <select name="gender${index}" form="jointAccount">
                                                    <c:forEach items="${genders}" var="gender">
                                                        <option value="${gender}">${gendersMap[gender]}</option>        
                                                    </c:forEach>
                                                </select>
                                                <label>Sexe</label>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <div class="row">
                                        <div class="col s6">
                                            <a class="btn-floating blue" href="<c:url value='/openaccount.htm' />?nbClients=${nbClients-1}"><i class="material-icons">remove</i></a>
                                        </div> 
                                        <div class="col s6">
                                            <a class="btn-floating blue" href="<c:url value='/openaccount.htm' />?nbClients=${nbClients+1}"><i class="material-icons">add</i></a>
                                        </div>                                       
                                    </div>
                                    <div class="row">
                                        <div class="col s12">
                                            <input type="submit" value="Compte joint" class="btn blue" />    
                                        </div>                                           
                                    </div>
                                </form>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="collapsible-header">Compte titres</div>
                        <div class="collapsible-body white center-align">
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
            <%@ include file="../include/notif.jsp"%>       
        </main>
        <footer>
        </footer>
        <%@ include file="../include/javascript.jsp"%>
        <%@ include file="../include/commonscripts.jsp"%>
    </body>
</html>