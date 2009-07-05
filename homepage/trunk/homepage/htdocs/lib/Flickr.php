<?php
class Flickr {
    private $api_key;

    function set_api_key($api_key) {
        $this->api_key = $api_key;
    }

    function call($method, $params = null) {
        if (!isset($params)) {
            $params = array();
        }
        $params['method'] = $method;
        if (isset($this->api_key)) {
            $params['api_key'] = $this->api_key;
        }
        $params['format'] = 'php_serial';
        $url = $this->create_url($params);
        echo $url;
        $response = file_get_contents($url);
        return unserialize($response);
    }

    function get_public_photos($user_id) {
        $params = array(
            'user_id' => $user_id,
            'extras' => 'date_taken,url_m',
            'per_page' => '10'
        );
        $response = $this->call('flickr.people.getPublicPhotos', $params);
        return $response['photos']['photo'];
    }

    function create_url($params) {
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
}
?>
