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
                    <div class='col s10 offset-s1 center-align'>
                        <div class="col s12 m4">
                            <div class="card medium blue" style="cursor: pointer;" onclick="window.location='<c:url value="/resume.htm" />';">
                                <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                                <img src="${pageContext.request.contextPath}/images/wallet.png" alt='"http://www.freepik.com" Designed by Macrovector / Freepik'>
                                    <div class="card-content white-text">
                                        <span class="card-title center-align">Mes comptes</span>
                                        <div class="row">
                                            <div class="col s12">
                                                <p class="text-flow">Consultez vos comptes</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col s12 m4">
                            <div class="card medium blue" style="cursor: pointer;" onclick="window.location='<c:url value="/advisor.htm" />';">
                                <div class='container' style="margin-bottom: 20px; margin-top: 40px;">
                                <img src="${pageContext.request.contextPath}/images/advisor.png" alt='advisor'>
                                    <div class="card-content white-text">
                                        <span class="card-title center-align">Mon conseiller</span>
                                        <div class="row">
                                            <div class="col s12">
                                                <p class="text-flow">Contactez votre conseiller</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col s12 m4">
                            <div class="card medium blue" style="cursor: pointer;" onclick="window.location='<c:url value="/userinfo.htm" />';">
                                <div class='container' style="margin-bottom: 20px; margin-top: 40px;">
                                <img src="${pageContext.request.contextPath}/images/client.png" alt='"https://www.freepik.com/free-vector/collection-of-people-avatar-in-flat-design_867151.htm">Designed by Freepik</a>'>
                                    <div class="card-content white-text">
                                        <span class="card-title center-align">Mes infos</span>
                                        <div class="row">
                                            <div class="col s12">
                                                <p class="text-flow">Vos informations personnelles</p>
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
