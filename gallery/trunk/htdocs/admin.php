<?php
include_once 'security/Security.php';
Security::checkAuthenticate('login.php');
?>
<a href="logout.php">logout</a>
<form action="upload.php" method="post" enctype="multipart/form-data">
    <input type="hidden" name="MAX_FILE_SIZE" value="2000000">
    <input type="file" name="image" accept="image/*"/>
    <input type="submit" value="Upload"/>
</form>