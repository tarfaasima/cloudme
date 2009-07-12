<?php
require_once 'config/config_data.php';
require_once 'DeliciousEntryLoader.php';
require_once 'EntryDao.php';
require_once 'FlickrEntryLoader.php';
require_once 'MetaDao.php';
require_once 'RssEntryLoader.php';

class EntryManager {
    private $entryLoaders;

    public function __construct() {
        $this->entryLoaders = array(
            new FlickrEntryLoader(),
            new DeliciousEntryLoader(),
            new RssEntryLoader('http://blog.moritzpetersen.de/feed/', 'http://blog.moritzpetersen.de'),
            new RssEntryLoader('http://feeds.feedburner.com/cloudme-blog?format=xml', 'http://blog.cloudme.org')
        );
    }

    function getEntry($id) {
        $entryDao = new EntryDao();
        return $entryDao->readEntry($id);
    }

    function getEntries() {
        $entryDao = new EntryDao();
        $metaDao = new MetaDao();
        $entries = array();
        if ($this->needsUpdate($metaDao->readLastUpdateDate())) {
            foreach ($this->entryLoaders as $loader) {
                foreach ($loader->loadEntries() as $entry) {
                    $entries[] = $entry;
                }
            }
            usort($entries, array("Entry", "compareTo"));
            $entryDao->insertEntries($entries);
            $metaDao->writeLastUpdateDate(time());
            $entries =  array_splice($entries, 0, 10);
        }
        if (count($entries) == 0) {
            $entries = $entryDao->readEntries(10);
        }
        return $entries;
    }

    private function needsUpdate($updateDate) {
        global $configData;
        return !(isset($updateDate)) OR ($updateDate < time() - $configData['update_delay']);
    }
}
?>
