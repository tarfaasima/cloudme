<?php
    include_once 'mysql/MySql.php';
    include_once 'security/Security.php';

    Security::checkAuthenticate('login.php');

    $image = $_FILES['image'];
    $tmpName = $image['tmp_name'];

    $dim = getimagesize($tmpName);
    $image['width'] = $dim[0];
    $image['height'] = $dim[1];
    $user = Security::getCurrentUser();
    $image['login'] = $user['login'];

    $mysql = new MySql();
    $query = 'INSERT INTO $_image (width, height, type, name, size, login) VALUES ($width, $height, "$type", "$name", "$size", "$login");';
    $id = $mysql->executeQuery($query, $image);

    if ($id) {
        if (!file_exists('media')) {
            mkdir('media');
        }
        move_uploaded_file($tmpName, 'media/image_' . $id);
    }
?>
