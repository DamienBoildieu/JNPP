<div class="container">
    <ul class="collapsible popout">
        <li>
            <div class="collapsible-header">
                Transfert
            </div>
            <div class="collapsible-body blue">
                <%@ include file="movement_transfert.jsp" %>
            </div>
        </li>
        <li>
            <div class="collapsible-header">
                Debit
            </div>
            <div class="collapsible-body blue">
                <%@ include file="movement_debit.jsp" %>
            </div>
        </li>
        <li>
            <div class="collapsible-header">
                Achat d'actions
            </div>
            <div class="collapsible-body blue">
                <%@ include file="movement_purchase.jsp" %>
            </div>
        </li>
        <li>
            <div class="collapsible-header">
                Vente d'actions
            </div>
            <div class="collapsible-body blue">
                <%@ include file="movement_sale.jsp" %>
            </div>
        </li>
    </ul>
</div>