<%@ include file="../include/jsptags.jsp" %>
<html>
    <head>
        <%@ include file="../include/materialize.jsp" %>
        <title>Gestion des conseillers</title>
    </head>
    <body>
        <%@ include file="banker_header.jsp" %>
        <div class="container" style="overflow: scroll; max-height: 60vh;">
            <table class="table striped card">
                <tbody>
                    <c:forEach items="${messages}" var="message">
                    <tr>
                        <td class="
                            <c:choose>
                                <c:when test="${message.direction eq 'CLIENT_TO_ADVISOR'}">left-align light-grey</c:when>
                                <c:otherwise>right-align white</c:otherwise>
                            </c:choose>">
                            ${message.content}
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="container">
            <form method="POST" action="/JNPP/conseiller/client/messages.htm" class="card">
                <div class="row">
                    <div class="col s10">
                        <input type="text" name="content" class="left-align">
                    </div>
                    <input type="hidden" name="login" value="${client.login}">
                    <div class="col s2">
                        <input type="submit" value="Envoyer" class="waves-effect waves-light btn-small center-align" style="width: 100%; margin-top: 7%;">
                    </div>
                </div>
            </form>
        </div>
                    
         
<div class="container">   
    <div class="card">
        <div class="card-content">  
            
            <div class="card">
                <div class="card-content"> 
                    <p>Hello</p>
                </div>
                
            </div>   
        </div>
    </div>
</div>
         
<div class="container">        
    <div class="row">
        <div class="col m12">
            <div class="card blue-grey darken-1">
                
                <div class="row card-content">
                    <div class="col m10">
                        <div class="card white">
                            <div class="card-content">
                                <p>Hello</p>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="card blue-grey">
                    <div class="card-content">
                        <p>Hello tou</p>
                    </div>
                </div>
                
                
                
                
                <div class="card-content white-text">
                    <span class="card-title">Card Title</span>
                    <p>I am a very simple card. I am good at containing small bits of information.
                    I am convenient because I require little markup to use effectively.</p>
                </div>
                <div class="card-action">
                    <a href="#">This is a link</a>
                    <a href="#">This is a link</a>
                </div>
            </div>
        </div>
    </div>
</div>
                    
                    
<div class="container">   
    <div class="card blue-grey darken-1">    
        <div class="card-content white-text">
            <p>I am a very simple card. I am good at containing small bits of information.
            I am convenient because I require little markup to use effectively.</p>
        </div>
        <div class="card-content white-text right-align">
            <p class="left-align">I am a very simple card. I am good at containing small bits of information.
            I am convenient because I require little markup to use effectively.</p>
        </div>
    </div>   
</div>              
                    
                    
                    
                    
                    
              

<div class="panel panel-info pb-chat-panel">
<div class="panel-body">
<form>
<div class="form-group">
    <span class="fa fa-lg fa-user pb-chat-fa-user"></span><span class="label label-default pb-chat-labels pb-chat-labels-left">Hi, how are you?</span>
</div>
<hr>
<div class="form-group pull-right pb-chat-labels-right">
    <span class="label label-primary pb-chat-labels pb-chat-labels-primary">Hi, i'm fine, you?</span><span class="fa fa-lg fa-user pb-chat-fa-user"></span>
</div>
<div class="clearfix"></div>
<hr>
<div class="form-group">
    <span class="fa fa-lg fa-user pb-chat-labels pb-chat-fa-user"></span><span class="label label-default pb-chat-labels pb-chat-labels-left">I'm great, wanna hangout?</span>
</div>
<hr>
<div class="form-group pull-right pb-chat-labels-right">
    <span class="label label-primary pb-chat-labels pb-chat-labels-primary">No, huehuehue</span><span class="fa fa-lg fa-user pb-chat-fa-user"></span>
</div>
<div class="clearfix"></div>
<hr>
<div class="form-group pull-right pb-chat-labels-right">
    <span class="label label-primary pb-chat-labels pb-chat-labels-primary">I'm busy hanging out :D</span><span class="fa fa-lg fa-user pb-chat-fa-user"></span>
</div>
<div class="clearfix"></div>
</form>        
</div>
</div>                    
                    
                    
                    
                    
                    
                    



    </body>
</html>
