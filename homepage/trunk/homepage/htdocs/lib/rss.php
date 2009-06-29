<?php

require_once 'magpierss-0.72/rss_fetch.inc';

class RssFeed {
    function read($url) {
        $rss = fetch_rss($url);
        foreach (array_slice($rss->items, 0, 10) as $item) {
            print_array("", $item);
        }
    }
}

function print_array($key, $value) {
    echo $key . ' => ';
    if (is_array($value)) {
        foreach ($value as $key => $value) {
            print_array($key, $value);
        }
    }
    else {
        echo $value . '<br>';
    }
}