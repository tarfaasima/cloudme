<?php
require_once 'DefaultPageRequest.php';
require_once 'EntryRenderer.php';

class AboutRequest extends DefaultPageRequest {
    protected function errorMessage() {
        return "This page is temporarily not available.";
    }

    protected function fillEntries($args, $em) {
        $params = array();
        $params['renderer'] = new EntryRenderer();
        $entry = new Entry();
        $entry->content = file_get_contents('impressum.html');
        $entry->date = filemtime('impressum.html');
        $entry->origin = 'http://moritzpetersen.de';
        $entry->title = 'About';
        $entry->url = 'http://moritzpetersen.de';
        $params['entries'][] = $entry;
        return $params;
    }
}

$request = new AboutRequest();
$request->execute('index_template.php');
?>
