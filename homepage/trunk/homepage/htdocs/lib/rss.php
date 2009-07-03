<?php

require_once 'magpierss-0.72/rss_fetch.inc';
require_once 'util.php';

abstract class RssFilter {
    protected $url;

    function RssFilter($url) {
        $this->url = $url;
    }

    function get_filtered_items() {
        $rss = fetch_rss($this->url);
        foreach (array_slice($rss->items, 0, 10) as $item) {
            $filtered_items[] = $this->filter($item);
        }
        return $filtered_items;
    }

    /**
     * Filters the item and converts into a standardized format:
     * - title
     * - content
     * - link
     * - source
     * - timestamp
     * @param <array> $item
     */
    abstract protected function filter($item);
}

class FlickrRssFilter extends RssFilter {
    
    /**
     * title
     * link
     * description
     * pubdate
     * dc
     * author
     * guid
     * media
     * summary
     * date_timestamp
     */
    protected function filter($item) {
        $map = array('title' => 'title', 
            'link' => 'link',
            'description' => 'content',
            'date_timestamp' => 'timestamp');
        $result = map($item, $map);
    }
}
