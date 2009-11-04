<?php
include_once 'mysql/MySql.php';

$mysql = new MySql();
$result = $mysql->selectSingleResult('SELECT type FROM $_image WHERE id = $id', $_GET);

header('Content-Type: ' . $result[0]);
readfile('media/image_' . $_GET['id']);
?>
