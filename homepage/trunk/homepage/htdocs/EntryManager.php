<?php
require_once 'FlickrEntryLoader.php';
require_once 'DeliciousEntryLoader.php';
require_once 'RssEntryLoader.php';

class EntryManager {
    private $entryLoaders;

    public function __construct() {
        $this->entryLoaders = array(
            new FlickrEntryLoader(),
            new DeliciousEntryLoader(),
            new RssEntryLoader('http://blog.moritzpetersen.de/feed/'),
            new RssEntryLoader('http://feeds.feedburner.com/cloudme-blog?format=xml')
        );
    }

    function getEntries() {
        $entries = array();
        foreach ($this->entryLoaders as $loader) {
            foreach ($loader->loadEntries() as $entry) {
                $entries[] = $entry;
            }
        }
        usort($entries, array("Entry", "compareTo"));
        return $entries;
    }
}
?>
