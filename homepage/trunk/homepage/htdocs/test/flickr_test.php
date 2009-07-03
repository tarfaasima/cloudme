<?php
require_once '../lib/Flickr.php';
$flickr = new Flickr();
$flickr->set_api_key('07719f1befbb229e7683480c13b9dd61');
$response = $flickr->get_public_photos('75474139@N00');
print_r($response);
?>
