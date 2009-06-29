<?php

require_once '../lib/rss.php';

$feed = new RssFeed();
$feed->read('http://api.flickr.com/services/feeds/photos_public.gne?id=75474139@N00&lang=en-us&format=rss_200');