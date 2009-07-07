<?php
require_once 'AbstractDao.php';

class EntryDao extends AbstractDao {
    public function __construct() {
        parent::__construct('entry');
    }

    function insertEntries($entries) {
        foreach ($entries as $entry) {
            $sql = 'INSERT INTO ' . $this->table . ' (title, content, url, date, origin) VALUES ('.
                '"' . $entry->title . '", ' .
                '"' . $entry->content . '", ' .
                '"' . $entry->url . '", ' .
                'FROM_UNIXTIME(' . $entry->date . '), ' .
                '"' . $entry->origin . '")';
            error_log($sql);
            mysql_query($sql);
        }
    }

    function readEntries() {
        $sql = "SELECT id, title, content, url, date, origin FROM $this->table LIMIT 10 ORDER BY date DESC";
        $result = mysql_query($sql);
        $entries = array();

        return $entries;
    }
}
?>
