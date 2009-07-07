<?php
/**
 * The EntryManager is the entry point for handling
 * entries.
 *
 * @author Moritz
 */
class EntryManager {
    private $db;

    public function __construct() {
        $this->db = new Database();
    }

    function getEntries() {
        $this->checkUpdate();
    }

    function checkUpdate() {
        ;
    }
}
?>
