<?php
require_once 'config/config_data.php';
require_once 'Flickr.php';
require_once 'EntryLoader.php';
require_once 'Entry.php';

class FlickrEntryLoader extends EntryLoader {
    protected function loadRawEntries() {
        global $configData;
        $apiKey = $configData['flickr_api_key'];
        $userId = $configData['flickr_user_id'];
        $this->origin = Flickr::createUserUrl($userId);
        $flickr = new Flickr();
        $flickr->setApiKey($apiKey);
        return $flickr->getPublicPhotos($userId);
    }

    protected function transformEntry($photo) {
        global $configData;
        $userId = $configData['flickr_user_id'];
        $url = Flickr::createPageUrl($photo);
        $title = $photo['title'];
        $photoUrl = $photo['url_m'];
        $width = $photo['width_m'];
        $height = $photo['height_m'];
        $date = $photo['dateupload'];
        $origin = Flickr::createUserUrl($userId);

        $entry = new Entry();
        $entry->title = '[Flickr] ' . $title;
        $entry->content = "<a href='$url'><img src='$photoUrl' width='$width' height='$height' alt='$title'/></a>";
        $entry->url = $url;
        $entry->date = $date;
        $entry->origin = $origin;

        return $entry;
    }
}
?>
