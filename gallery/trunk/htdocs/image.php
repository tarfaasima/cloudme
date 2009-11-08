<?php
include_once 'image/Image.php';

$image = new Image();
$image->load($_GET);
$image->writeImage($_GET);
?>
