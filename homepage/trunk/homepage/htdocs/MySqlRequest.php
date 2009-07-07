<?php
require_once 'config/config_data.php';
require_once 'Request.php';

abstract class MySqlRequest extends Request {
    private $db;

    public function prepare() {
        parent::prepare();
        global $configData;
        $server = $configData['db_host'];
        $username = $configData['db_user'];
        $password = $configData['db_password'];
        $this->db = mysql_connect($server, $username, $password);
        $database_name = $configData['db_name'];
        mysql_select_db($database_name);
    }

    public function cleanUp() {
        mysql_close($this->db);
        parent::cleanUp();
    }
}
?>
