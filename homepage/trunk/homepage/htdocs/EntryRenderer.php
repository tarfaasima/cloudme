<?php
require_once 'lib/util.php';

class EntryRenderer {
    public function renderLink($entry) {
        echo $entry->url;
    }

    public function renderTitle($entry) {
        echo $entry->title;
    }

    public function renderContent($entry) {
        echo $entry->content;
    }

    public function renderDate($entry) {
        echo date('Y-m-d', $entry->date);
    }

    public function renderHost($entry) {
        echo get_host($entry->url);
    }
}
?>
