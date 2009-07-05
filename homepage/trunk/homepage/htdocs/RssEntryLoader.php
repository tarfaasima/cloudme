<?php
require_once 'EntryLoader.php';

class RssEntryLoader extends EntryLoader {
    protected $url;

    public function __construct($url) {
        $this->url = $url;
    }

    protected function loadRawEntries() {
        $xml = simplexml_load_file($this->url);
        return $xml->channel->item;
    }

    protected function transformEntry($rawEntry) {
        $entry = new Entry();
        $entry->content = (string) $rawEntry->description;
        $entry->date = strtotime($rawEntry->pubDate);
        $entry->title = (string) $rawEntry->title;
        $entry->url = (string) $rawEntry->link;
        return $entry;
    }
}
?>
