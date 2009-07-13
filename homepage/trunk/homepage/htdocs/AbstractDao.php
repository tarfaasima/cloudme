<?php
require_once 'config/config_data.php';

abstract class AbstractDao {
    public $table;

    public function __construct($name) {
        global $configData;
        $this->table = $configData['db_table_prefix'] . $name;
    }

    protected function executeQuery($sql, $args) {
        foreach ($args as $key => $value) {
            $sql = str_replace(":$key", $this->escape($value), $sql);
        }
        error_log($sql);
        return mysql_query($sql);
    }

    function escape($str) {
        return mysql_real_escape_string(stripslashes($str));
    }
}
?>
