<?php

require_once 'magpierss-0.72/rss_fetch.inc';

abstract class RssFilter {
    protected $url;

    function RssFilter($url) {
        $this->url = $url;
    }

    function get_filtered_items() {
        $rss = fetch_rss($this->url);
        foreach (array_slice($rss->items, 0, 10) as $item) {
            $filtered_items[] = filter($item);
        }
        return $filtered_items;
    }

    abstract protected function filter($item);
}

class FlickrRssFilter extends RssFilter {
    protected function filter($item) {
        foreach ($item as $key => $value) {
            echo $key . '=>' . $value . '<br>';
        }
    }
}
