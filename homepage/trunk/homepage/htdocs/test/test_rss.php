<?php

require_once '../lib/rss.php';

$flickr_rss_url = 'http://api.flickr.com/services/feeds/photos_public.gne?id=75474139@N00&lang=en-us&format=rss_200';
new FlickrRssFilter($flickr_rss_url);
