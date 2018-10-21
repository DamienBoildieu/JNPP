<%-- 
    Document   : footer
    Created on : 26 sept. 2018, 18:05:09
    Author     : damien
--%>
<c:if test="${isConnected}">
    <div class="fixed-action-btn">
        <a href="<c:url value='/resume.htm' />" class="btn-floating btn-large blue">
            <i class="large material-icons">notifications</i>
        </a>
    </div>
</c:if>
</main>
<footer>
</footer>
    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0-alpha1/jquery.min.js"></script>
    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
    <script>
        $(document).ready(function(){
            $('.sidenav').sidenav();
        });
    </script>
    <script>
        $(document).ready(function(){
            $('.tabs').tabs();
        });
    </script>   
    <script>
        $(document).ready(function(){
            $('.carousel').carousel();
            setInterval(function() {
                $('.carousel').carousel('next');
            }, 3000);
        });
    </script>
    <script>       
        $('.carousel.carousel-slider').carousel({
            fullWidth: true
        });
    </script>
    <script>
        $( document ).ready(function(){
            $('#errorMessage').fadeIn('slow', function(){
               $('#errorMessage').delay(300).fadeOut(); 
            });
        });
    </script>
    <script>
        $( document ).ready(function(){
            $('#successMessage').fadeIn('slow', function(){
               $('#successMessage').delay(3000).fadeOut(); 
            });
        });
    </script>
        
</body>
</html>
