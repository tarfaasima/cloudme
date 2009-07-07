<?php
require_once 'lib/util.php';
require_once 'EntryManager.php';
require_once 'MySqlRequest.php';

class IndexRequest extends MySqlRequest {
    public function prepare() {
        parent::prepare();
        $params = array();
        $em = new EntryManager();
        foreach (array_slice($em->getEntries(), 0, 10) as $item) {
            $entry['link'] = $item->url;
            $entry['title'] = $item->title;
            $entry['text'] = $item->content;
            $entry['date'] = date('Y-m-d', $item->date);
            $entry['host'] = get_host($item->url);
            $entry['show_title'] = strip_html($item->url) != strip_html($item->content);
            $params['entries'][] = $entry;
        }
        if (!$params['entries']) {
            $params['errors'][] = "At the moment, no entries are available. Please try again later.";
        }
        $params['copyright'] = copyright(2008, 'Moritz Petersen');
        return $params;
    }
}

$request = new IndexRequest();
$request->execute('index_template.php');
?>