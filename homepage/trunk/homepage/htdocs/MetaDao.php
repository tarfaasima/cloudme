<?php
require_once 'AbstractDao.php';

class MetaDao extends AbstractDao {
    private $time;

    public function __construct() {
        parent::__construct('meta');
    }

    function readLastUpdateDate() {
        if (!isset($this->time)) {
            $sql = "SELECT lastUpdateDate FROM $this->table LIMIT 1";
            $result = mysql_query($sql);
            if (!$result OR mysql_num_rows($result) == 0) {
                return false;
            }
            $row = mysql_fetch_row($result);
            $this->time = $row[0];
        }
        return $this->time;
    }

    function writeLastUpdateDate($time) {
        if (!$this->readLastUpdateDate()) {
            $sql = "INSERT INTO $this->table (lastUpdateDate) VALUES (:0)";
        }
        else {
            $sql = "UPDATE $this->table SET lastUpdateDate=:0";
        }
        $this->executeQuery($sql, array($time));
    }
}
?>
