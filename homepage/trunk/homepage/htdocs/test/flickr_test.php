<?php
require_once '../lib/Flickr.php';
require_once '../config/config_data.php';

$flickr_api_key = $configData['flickr_api_key'];
$flickr_user_id = $configData['flickr_user_id'];

$flickr = new Flickr();
$flickr->set_api_key($flickr_api_key);
$response = $flickr->get_public_photos($flickr_user_id);

print_r($response);
?>
