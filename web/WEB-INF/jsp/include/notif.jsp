<c:if test="${info.connected}">
    <div class="fixed-action-btn">
        <c:choose>
            <c:when test="${info.hasNotif}">
                <a href="<c:url value='/notifs.htm' />" class="btn-floating pulse btn-large blue">
            </c:when>    
            <c:otherwise>
                <a href="<c:url value='/notifs.htm' />" class="btn-floating btn-large blue">
            </c:otherwise>
        </c:choose>
            <i class="large material-icons">notifications</i>
        </a>
    </div>
</c:if>