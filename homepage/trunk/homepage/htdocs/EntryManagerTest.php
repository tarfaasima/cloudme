<?php
require_once 'EntryManager.php';

$em = new EntryManager();
foreach ($em->getEntries() as $entry) {
    echo "<h1>$entry->title</h1>";
    echo $entry->content;
}
?>
