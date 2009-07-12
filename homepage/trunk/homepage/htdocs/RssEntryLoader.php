<?php
require_once 'EntryLoader.php';

class RssEntryLoader extends EntryLoader {
    protected $url;
    protected $origin;

    public function __construct($url, $origin) {
        $this->url = $url;
        $this->origin = $origin;
    }

    protected function loadRawEntries() {
        $xml = simplexml_load_file($this->url);
        return isset($xml->channel) ? $xml->channel->item : array();
    }

    protected function transformEntry($rawEntry) {
        $entry = new Entry();
        $entry->content = (string) $rawEntry->description;
        $entry->date = strtotime($rawEntry->pubDate);
        $entry->title = (string) $rawEntry->title;
        $entry->url = (string) $rawEntry->link;
        $entry->origin = $this->origin;
        return $entry;
    }
}
?>
