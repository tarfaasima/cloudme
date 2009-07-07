<?php
class Flickr {
    private $apiKey;

    function setApiKey($api_key) {
        $this->apiKey = $api_key;
    }

    function call($method, $params = null) {
        if (!isset($params)) {
            $params = array();
        }
        $params['method'] = $method;
        if (isset($this->apiKey)) {
            $params['api_key'] = $this->apiKey;
        }
        $params['format'] = 'php_serial';
        $url = $this->createUrl($params);
        $response = file_get_contents($url);
        return unserialize($response);
    }
    
    function getPublicPhotos($user_id) {
        $params = array(
            'user_id' => $user_id,
            'extras' => 'date_upload,url_m',
            'per_page' => '10'
        );
        $response = $this->call('flickr.people.getPublicPhotos', $params);
        return $response['photos']['photo'];
    }

    function createUrl($params) {
        $enc_params = $this->encode($params);
        return 'http://api.flickr.com/services/rest/?' . implode('&', $enc_params);
    }

    function encode($arr) {
        $enc = array();
        foreach ($arr as $key => $value) {
            $enc[] = urlencode($key) . '=' . urldecode($value);
        }
        return $enc;
    }

    static function createPageUrl($photo) {
        $owner = $photo['owner'];
        $id = $photo['id'];
        return "http://www.flickr.com/photos/$owner/$id";
    }

    static function createUserUrl($userId) {
        return "http://www.flickr.com/photos/$userId";
    }
}
?>
