<?php
require_once 'AbstractDao.php';

class EntryDao extends AbstractDao {
    private $selectFields = "id, title, content, url, UNIX_TIMESTAMP(date) AS date, origin";

    public function __construct() {
        parent::__construct('entry');
    }

    function insertEntries($entries) {
        foreach ($entries as $entry) {
            $sql = "INSERT INTO $this->table (title, content, url, date, origin) " .
                "VALUES (':0', ':1', ':2', FROM_UNIXTIME(:3), ':4')";
            $args = array($entry->title, $entry->content, $entry->url, $entry->date, $entry->origin);
            $this->executeQuery($sql, $args);
        }
    }

    function readEntry($id) {
        $sql = "SELECT $this->selectFields FROM $this->table WHERE id=$id";
        $result = mysql_query($sql);
        if ($row = mysql_fetch_array($result)) {
            return $this->createEntry($row);
        }
        return false;
    }

    function readEntries($size = 10) {
        $sql = "SELECT $this->selectFields FROM $this->table ORDER BY date DESC LIMIT $size";
        $result = mysql_query($sql);
        $entries = array();
        while ($row = mysql_fetch_array($result)) {
            $entries[] = $this->createEntry($row);
        }
        return $entries;
    }

    private function createEntry($row) {
        $entry = new Entry();
        $entry->content = $row['content'];
        $entry->date = $row['date'];
        $entry->id = $row['id'];
        $entry->origin = $row['origin'];
        $entry->title = $row['title'];
        $entry->url = $row['url'];
        return $entry;
    }
}
?>
