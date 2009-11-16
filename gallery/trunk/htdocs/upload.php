<?php
    include_once 'image/Image.php';
    include_once 'security/Security.php';

    Security::checkAuthenticate('login.php');

    $params = $_FILES['image'];
    $user = Security::getCurrentUser();

    $image = new Image();
    $image->createFromUpload($params, $user['login']);

    $image->writeTag(Image::$SMALL_FORMAT);
?>
