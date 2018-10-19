<%@ include file="include/header.jsp"%>

<div class="container">
    <div class="row">
        <div class='col s10 offset-s1 center-align'>
            <div class="col s12">
                <div class="card blue">
                    <div class="card-content white-text">
                        <span class="card-title center-align">${advisor.firstName} ${advisor.lastName}</span>
                        <div class="row">
                            <div class="col s12">
                                <p>${advisor.agency}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12">
                                <p>${advisor.phone}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12">
                                <a class="white-text hoverable" href="<c:url value='/account.htm' />"><b>Envoyez un message </b><i class="material-icons">message</i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>  

<%@ include file="include/footer.jsp"%>
