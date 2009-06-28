<?php
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