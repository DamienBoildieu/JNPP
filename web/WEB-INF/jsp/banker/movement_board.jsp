<%@ include file="../include/jsptags.jsp" %>
<html>
    <head>
        <%@ include file="../include/materialize.jsp" %>
        <title>Gestion des transactions</title>                         
    </head>
    <body>
        <%@ include file="banker_header.jsp" %>
        <div class="container">
            <ul class="collapsible popout">
                <li>
                    <div class="collapsible-header">
                        Depot
                    </div>
                    <div class="collapsible-body blue">
                        <%@ include file="deposit_board.jsp" %>
                    </div>
                </li>
                <li>
                    <div class="collapsible-header">
                        Transfert-Debit
                    </div>
                    <div class="collapsible-body blue">
                        <%@ include file="transfert_debit_board.jsp" %>
                    </div>
                </li>
                <li>
                    <div class="collapsible-header">
                        Achat-Vente
                    </div>
                    <div class="collapsible-body blue">
                        <%@ include file="purchase_sale_board.jsp" %>
                    </div>
                </li>
            </ul>
        </div>        
        <%@ include file="../include/javascript.jsp"%>
        <%@ include file="../include/commonscripts.jsp"%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0-alpha1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
    <script src="${pageContext.request.contextPath}/scripts/auto.js"></script>
    </body>
</html>
