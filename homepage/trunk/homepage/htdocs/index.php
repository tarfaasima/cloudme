<?php
require_once 'DefaultPageRequest.php';
require_once 'EntryManager.php';
require_once 'EntryRenderer.php';

class IndexRequest extends DefaultPageRequest {
    protected function errorMessage() {
        return "At the moment, no entries are available. Please try again later.";
    }

    protected function fillEntries($args, $em) {
        $params = array();
        $params['renderer'] = new EntryRenderer();
        foreach ($em->getEntries() as $entry) {
            $params['entries'][] = $entry;
        }
        return $params;
    }
}

$request = new IndexRequest();
$request->execute('index_template.php');
?>