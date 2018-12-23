<c:forEach items="${info.alerts}" var="element">
    <c:choose>
        <c:when test="${element.alertType == 'ERROR'}">
            <div id="errorMessage" class='card-panel red lighten-1 white-text left-align' style="font-size: 20px;">
                ${element.message} <i class="material-icons right">warning</i>
            </div>
        </c:when>
        <c:when test="${element.alertType == 'SUCCESS'}">
            <div id="successMessage" class='card-panel green lighten-1 white-text left-align' style="font-size: 20px;">
                ${element.message} <i class="material-icons right">warning</i>
            </div>
        </c:when>
        <c:otherwise>
        </c:otherwise>
   </c:choose>
</c:forEach>