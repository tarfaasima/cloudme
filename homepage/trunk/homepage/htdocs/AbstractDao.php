<?php
require_once 'config/config_data.php';

abstract class AbstractDao {
    public $table;

    public function __construct($name) {
        $this->table = $configData['db_table_prefix'] . $name;
    }
}
?>
