<?php
require_once 'DefaultPageRequest.php';
require_once 'EntryManager.php';
require_once 'EntryRenderer.php';

class IndexEntryRenderer extends EntryRenderer {
    public function renderLink($entry) {
        echo $this->isPersonal($entry) ? $entry->url : "show_entry.php?id=$entry->id";
    }

    private function isPersonal($entry) {
        $url = $entry->url;
        return strpos($url, 'moritzpetersen.de') OR strpos($url, 'cloudme.org');
    }
}

class IndexRequest extends DefaultPageRequest {
    protected function errorMessage() {
        return "At the moment, no entries are available. Please try again later.";
    }

    protected function fillEntries($args, $em) {
        $params = array();
        $params['renderer'] = new IndexEntryRenderer();
        foreach ($em->getEntries() as $entry) {
            $params['entries'][] = $entry;
        }
        return $params;
    }
}

$request = new IndexRequest();
$request->execute('index_template.php');
?>