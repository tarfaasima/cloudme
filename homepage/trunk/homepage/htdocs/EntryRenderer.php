<?php
require_once 'lib/util.php';

class EntryRenderer {
    public function renderLink($entry, $direct = false) {
        echo (!$entry->isExternal() OR $direct) ? $entry->url : "show_entry.php?id=$entry->id";
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
