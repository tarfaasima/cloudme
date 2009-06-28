<?php
function copyright($year, $owner) {
  return '&copy; ' . $year . (date('Y') > $year ? '&ndash;' . date('Y') : '') . ' by ' . $owner;
}

function out($arr, $index) {
  echo $arr[$index];
}

function strip_html($str) {
  return ereg_replace('<[^>]*>', '', $str);
}

function get_host($url) {
  if (eregi('http://([^/]*).*', $url, $args)) {
    return $args[1];
  }
  return false;
}
?>