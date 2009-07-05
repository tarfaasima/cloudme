<?php
class Entry {
    public $title;
    public $content;
    public $url;
    public $date;

    public static function compareTo($a, $b) {
        return $b->date - $a->date;
    }
}
?>
