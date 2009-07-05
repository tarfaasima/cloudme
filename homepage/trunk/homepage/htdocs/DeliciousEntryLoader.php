<?php
require_once 'config/config_data.php';
require_once 'RssEntryLoader.php';

class DeliciousEntryLoader extends RssEntryLoader {
    public function __construct() {
        global $configData;
        $user_id = $configData['delicious_user_id'];
        $url = "http://feeds.delicious.com/v2/plain/$user_id?count=10";
        parent::__construct($url);
    }
}
?>
