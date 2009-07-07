<?php
$sql = <<< SQL
-- phpMyAdmin SQL Dump
-- version 2.11.7.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 07. Juli 2009 um 19:35
-- Server Version: 5.0.41
-- PHP-Version: 5.2.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Datenbank: `homepage`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `h_entries`
--

CREATE TABLE `h_entries` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `title` varchar(255) NOT NULL,
  `content` varchar(3000) NOT NULL,
  `url` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  `origin` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `origin` (`origin`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Daten für Tabelle `h_entries`
--


-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `h_meta`
--

CREATE TABLE `h_meta` (
  `lastUpdateDate` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`lastUpdateDate`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `h_meta`
--
SQL
?>
