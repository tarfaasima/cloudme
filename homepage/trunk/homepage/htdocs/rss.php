<?php
require_once 'DefaultPageRequest.php';
require_once 'EntryRenderer.php';

class RssEntryRenderer extends EntryRenderer {
    public function renderLink($entry) {
        if ($this->isPersonal($entry)) {
            echo $entry->url;
        }
        else {
            $this->renderEntryLink($entry);
        }
    }

    public function renderDate($entry) {
        echo date('Y-m-d H:m', $entry->date);
    }

    private function isPersonal($entry) {
        $url = $entry->url;
        return strpos($url, 'moritzpetersen.de') OR strpos($url, 'cloudme.org');
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
