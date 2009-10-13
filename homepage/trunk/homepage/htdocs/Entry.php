<?php
class Entry {
    public $id;
    public $title;
    public $content;
    public $url;
    public $date;
    public $origin;

    public static function compareTo($a, $b) {
        return $b->date - $a->date;
    }

    public function isExternal() {
        return !strpos($this->url, 'moritzpetersen.de') AND !strpos($this->url, 'cloudme.org');
    }
}
?>
