<?php
include_once 'config/config_data.php';

/**
 * Description of MySql
 *
 * @author Moritz
 */
class MySql {
    private $link;
    private $prefix;

    public function __construct() {
        global $configData;
        $this->open($configData['db_host'], $configData['db_user'], $configData['db_password'], $configData['db_name']);
        $this->prefix = $configData['db_table_prefix'];
    }

    public function __destruct() {
        $this->close();
    }

    private function open($host, $user, $password, $name) {
        $this->link = mysql_connect($host, $user, $password);
        mysql_select_db($name, $this->link);
    }

    public function close() {
        mysql_close($this->link);
    }

    public function selectManyResults($query, $callback) {
        $query = $this->transformQuery($query, null);
        $result = mysql_query($query, $this->link);
        if ($result) {
            $results = array();
            while ($row = mysql_fetch_array($result)) {
                $results[] = $row;
            }
            return array_map($callback, $results);
        }
        return false;
    }

    public function selectSingleResult($query, $params) {
        $query = $this->transformQuery($query, $params);
        $result = mysql_query($query, $this->link);
        if ($result) {
            return mysql_fetch_array($result);
        }
        return false;
    }

    public function executeQuery($query, $params) {
        $query = $this->transformQuery($query, $params);
        $result = mysql_query($query, $this->link);
        if ($result) {
            return mysql_insert_id($this->link);
        }
        return false;
    }

    private function transformQuery($query, $params) {
        $query = str_replace('$_', $this->prefix, $query);
        if (isset($params)) {
            foreach ($params as $key => $value) {
                $query = str_replace('$' . $key, mysql_real_escape_string($value), $query);
            }
        }
        return $query;
    }
}
?>
