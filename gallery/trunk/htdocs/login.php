<?php
include_once 'security/Security.php';
Security::login($_POST);
?>
<a href="<?php echo $_SESSION['login_referer'] ?>">back</a>
<form action="login.php" method="post">
    <p>Login:</p>
    <p><input type="text" name="login"/></p>
    <p>Password:</p>
    <p><input type="password" name="password"></p>
    <p><input type="submit" value="Login"/></p>
</form>