<?php
include_once 'mysql/MySql.php';

class Image {
    public $id;
    public $width;
    public $height;
    public $login;
    public $type;
    public $name;
    public $size;
    private static $BASE_DIR = 'media';
    private static $IMAGE_PREFIX = 'image_';
    public static $MEDIUM_FORMAT = 'm';
    public static $SMALL_FORMAT = 's';
    private static $IMAGE_FORMATS = array(
        'm' => array('max' => 500, 'isSquare' => false),
        's' => array('max' => 120, 'isSquare' => false)
    );

    public function createFromUpload($params, $login) {
        $tmpName = $params['tmp_name'];
        $dim = getimagesize($tmpName);
        $params['width'] = $dim[0];
        $params['height'] = $dim[1];
        $params['login'] = $login;
        $this->init($params);
        $mysql = new MySql();
        $query = 'INSERT INTO $_image (width, height, type, name, size, login) VALUES ($width, $height, "$type", "$name", "$size", "$login");';
        $this->id = $mysql->executeQuery($query, $params);
        if ($this->id) {
            $this->move($tmpName);
        }
    }

    public function load($params) {
        assert(isset($params['id']));
        $mysql = new MySql();
        $query = 'SELECT * FROM $_image WHERE id = $id';
        $result = $mysql->selectSingleResult($query, $params);
        $this->init($result);
    }

    public function writeTag($format = null) {
        list($width, $height) = $this->computeSizeOfFormat($format);
        echo '<img src="image.php?id=' . $this->id
            . '&format=' . $format
            . '" alt="' . $this->name
            . '" width="' . $width
            . '" height="' . $height
            . '"/>';
    }

    public function getLocation($format = null) {
        return self::$BASE_DIR . '/' . self::$IMAGE_PREFIX . $this->id . ($format == null ? '' : '_' . $format);
    }

    public function writeImage($params) {
        header('Content-Type: ' . $this->type);

        $format = isset($params['format']) ? $params['format'] : null;
        $location = $this->getLocation($format);
        if ($format != null OR !file_exists($location)) {
            $image = null;
            if ($this->type == 'image/jpeg') {
                $image = imagecreatefromjpeg($this->getLocation());
            }
            list($sw, $sh) = $this->computeSizeOfFormat($format);
            $scaledImage = imagecreatetruecolor($sw, $sh);
            imagecopyresampled($scaledImage, $image, 0, 0, 0, 0, $sw, $sh, $this->width, $this->height);
            if ($this->type == 'image/jpeg') {
                imagejpeg($scaledImage, $location);
            }
        }
//        header('Content-Disposition: attachment; filename="' . $this->name . '"');
        readfile($location);
    }

    private function init($params) {
        if (isset($params['id'])) {
            $this->id = $params['id'];
        }
        $this->width = $params['width'];
        $this->height = $params['height'];
        $this->login = $params['login'];
        $this->type = $params['type'];
        $this->name = $params['name'];
        $this->size = $params['size'];
        $this->login = $params['login'];
    }

    private function move($tmpName) {
        if (!file_exists(self::$BASE_DIR)) {
            mkdir(self::$BASE_DIR);
        }
        move_uploaded_file($tmpName, $this->getLocation());
    }

    private function computeSizeOfFormat($format) {
        if ($format == null) {
            return array($this->width, $this->height);
        }
        assert(isset(self::$IMAGE_FORMATS[$format]));
        $imageFormat = self::$IMAGE_FORMATS[$format];
        $max = $imageFormat['max'];
        $isSquare = $imageFormat['isSquare'];
        $dim = array();
        if ($isSquare) {
            $dim[0] = $dim[1] = min($max, max($this->width, $this->height));
        }
        else {
            if ($this->width > $this->height) {
                $dim[0] = $max;
                $dim[1] = round($max / $this->width * $this->height);
            }
            else {
                $dim[0] = round($max / $this->height * $this->width);
                $dim[1] = $max;
            }
        }
        return $dim;
    }
}
?>
