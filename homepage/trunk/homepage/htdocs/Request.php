<?php
abstract class Request {
    function execute($template) {
        global $_REQUEST;
        $params = $this->prepare($_REQUEST);
        include $template;
        $this->cleanUp();
    }

    function prepare($args) {
    }

    function cleanUp() {
    }
}
?>
