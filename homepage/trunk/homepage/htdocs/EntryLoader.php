<?php
abstract class EntryLoader {
    public function loadEntries() {
        $entries = array();
        foreach ($this->loadRawEntries() as $rawEntry) {
            $entries[] = $this->transformEntry($rawEntry);
        }
        return $entries;
    }

    protected abstract function loadRawEntries();

    protected abstract function transformEntry($rawEntry);
}
?>
