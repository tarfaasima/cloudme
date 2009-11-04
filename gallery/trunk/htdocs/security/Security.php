<?php
include_once 'mysql/MySql.php';

/**
 * Description of Security
 *
 * @author Moritz
 */
class Security {
    public static function checkAuthenticate($login) {
        session_start();
        if (!isset($_SESSION['current_user'])) {
            $_SESSION['login_referer'] = $_SERVER['REQUEST_URI'];
            header('Location: ' . $login);
        }
    }

    public static function login($params) {
        session_start();
        $mysql = new MySql();
        $result = $mysql->selectSingleResult('SELECT * FROM $_user WHERE login = "$login" AND password = md5("$password");', $params);
        if ($result) {
            $_SESSION['current_user'] = $result;
            header('Location: ' . $_SESSION['login_referer']);
        }
    }

    public static function logout($redirect = null) {
        session_start();
        unset ($_SESSION['current_user']);
        if ($redirect != null) {
            header('Location: ' . $redirect);
        }
    }

    public static function getCurrentUser() {
        if (session_id() == "") {
            session_start();
        }
        return $_SESSION['current_user'];
    }
}
?>
