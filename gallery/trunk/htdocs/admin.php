<?php
include_once 'security/Security.php';
Security::checkAuthenticate('login.php');
?>
<a href="logout.php">logout</a>