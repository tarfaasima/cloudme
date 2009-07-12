<?php
require_once 'MySqlRequest.php';

abstract class DefaultPageRequest extends MySqlRequest {
    public function prepare($args) {
        parent::prepare($args);
        $em = new EntryManager();
        $params = $this->fillEntries($args, $em);
        if (!isset($params['entries'])) {
            $params['errors'][] = $this->errorMessage();
        }
        $params['copyright'] = copyright(2008, 'Moritz Petersen');
        return $params;
    }

    protected abstract function fillEntries($args, $em);

    protected abstract function errorMessage();
}
?>
