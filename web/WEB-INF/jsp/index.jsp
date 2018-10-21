<%@ include file="include/header.jsp"%>
<div class="container" style="margin-top: 10%">
    <div class="row">
        <div class='card blue col s10 offset-s1'>
            <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                <div class="carousel carousel-slider">
                    <a class="carousel-item"><img src="${pageContext.request.contextPath}/pictures/cloud.jpg"></a>
                    <a class="carousel-item"><img src="${pageContext.request.contextPath}/pictures/forest.jpg"></a>
                    <a class="carousel-item"><img src="${pageContext.request.contextPath}/pictures/mountain.jpg"></a>
                    <a class="carousel-item"><img src="${pageContext.request.contextPath}/pictures/city.jpg"></a>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class='card-panel blue col s6 offset-s3 center-align'>
            <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                <p class=" flow-text white-text"> La banque qui vous fait voyager</p>
            </div>
        </div>
    </div>
</div>
<%@ include file="include/footer.jsp"%>
