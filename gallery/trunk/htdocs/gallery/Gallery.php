<?php
include_once 'mysql/MySql.php';

class Gallery {
    public $id;
    public $name;

    public function __construct($id, $name) {
        $this->id = $id;
        $this->name = $name;
    }

    public static function loadAllGalleries() {
        $mysql = new MySql();
        return $mysql->selectManyResults('SELECT * FROM gallery', 'Gallery::createGallery');
    }

    public static function createGallery($args) {
        return new Gallery($args['id'], $args['name']);
    }
}
?>
