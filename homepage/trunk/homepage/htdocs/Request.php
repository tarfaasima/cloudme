<?php
abstract class Request {
    function execute($template) {
        $params = $this->prepare();
        include $template;
        $this->cleanUp();
    }

    function prepare() {
    }

    function cleanUp() {
    }
}
?>
