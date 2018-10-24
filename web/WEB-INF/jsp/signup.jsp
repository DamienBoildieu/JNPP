<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Connexion</title>
    </head>
    <body>
        <h1>Je m'inscris !</h1>
        <form method="POST" action="signup.htm">
            <label for="login">Identifiant</label>
            <input type="text" name="login" id="login">
            <label for="password">Mot de passe</label>
            <input type="password" name="password" id="password">
            <input type="submit" value="Valider">
        </form>
        <br>
        <h1>Je me connecte !</h1>
        <form method="POST" action="signin.htm">
            <label for="login">Identifiant</label>
            <input type="text" name="login" id="login">
            <label for="password">Mot de passe</label>
            <input type="password" name="password" id="password">
            <input type="submit" value="Connexion">
        </form>
    </body>
</html>
