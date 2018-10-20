<%@ include file="include/header.jsp"%>
<div class="container" style="margin-top: 5%">
    <div class="row">
        <div class='col s10 offset-s1 center-align'>
            <div class="col s12 m4">
                <div class="card-image blue" style="cursor: pointer;" onclick="window.location='<c:url value="/resume.htm" />';">
                    <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                    <img src="${pageContext.request.contextPath}/pictures/wallet.png" alt='"http://www.freepik.com" Designed by Macrovector / Freepik'>
                    <div class="card-content white-text">
                        <span class="card-title center-align">Mes comptes</span>
                        <div class="row">
                            <div class="col s12">
                                <p class="text-flow">Consultez vos comptes, vos dernières transactions...</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="include/footer.jsp"%>
