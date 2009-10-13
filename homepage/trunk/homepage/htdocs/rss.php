<?php
require_once 'DefaultPageRequest.php';
require_once 'EntryRenderer.php';

class RssEntryRenderer extends EntryRenderer {
    public function renderLink($entry) {
        if ($entry->isExternal) {
            $this->renderEntryLink($entry);
        }
        else {
            echo $entry->url;
        }
    }

    public function renderDate($entry) {
        echo date('Y-m-d H:m', $entry->date);
    }
    
    public function renderEntryLink($entry) {
        echo "show_entry.php?id=$entry->id";
    }
}

class RssRequest extends DefaultPageRequest {
    protected function fillEntries($args, $em) {
        $params = array();
        $params['renderer'] = new RssEntryRenderer();
        foreach ($em->getEntries() as $entry) {
            $params['entries'][] = $entry;
        }
        return $params;
    }
    protected function errorMessage() {
    }
}

$request = new RssRequest();
$request->execute('rss_template.php');
?>
