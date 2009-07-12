<?php
require_once 'DefaultPageRequest.php';
require_once 'EntryManager.php';
require_once 'EntryRenderer.php';

class ShowEntryRequest extends DefaultPageRequest {
    protected function errorMessage() {
        return "This entry has been removed.";
    }
    
    protected function fillEntries($args, $em) {
        $params = array();
        $params['renderer'] = new EntryRenderer();
        $entry = $em->getEntry($args['id']);
        if ($entry) {
            $params['entries'][] = $entry;
        }
        return $params;
    }
}

$request = new ShowEntryRequest();
$request->execute('index_template.php');
?>
