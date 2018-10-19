<%@ include file="include/header.jsp"%>

<div class="row">
    <div class="col s12">
        <div class="card blue">
            <div class="card-content white-text">
                <div class="center-align">
                    <i class="large material-icons">person</i>
                </div>
                <span class="card-title center-align">${advisor.firstName} ${advisor.lastName}</span>
                <p class="center-align">${advisor.agency}</p>
                <p class="center-align">${advisor.phone}</p>
                <div class="card-action center-align">
                    <a class="white-text hoverable" href="<c:url value='/account.htm' />">Envoyez un message <i class="material-icons">message</i></a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="include/footer.jsp"%>
