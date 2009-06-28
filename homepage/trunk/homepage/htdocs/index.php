<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<?php
require_once('lib/magpierss-0.72/rss_fetch.inc');
require_once('lib/util.php');

unset($params);

$url = 'http://feeds.feedburner.com/moritzpetersen/rss2';

// Disabling MagpieRSS errors:
ini_set('display_errors', 0);

try {
  $rss = fetch_rss($url);
  if ($rss) {
    foreach (array_slice($rss->items, 0, 10) as $item) {
      $entry['link'] = $item['link'];
      $entry['title'] = $item['title'];
      $entry['text'] = $item['description'];
      $entry['date'] = date('Y-m-d', $item['date_timestamp']);
      $entry['host'] = get_host($item['link']);
      $entry['show_title'] = strip_html($item['link']) != strip_html($item['description']);
      $params['entries'][] = $entry;
    }
  }
}
catch (Exception $e) {
  $params['errors'][] = $e->getMessage();
}
if (!$params['entries']) {
  $params['errors'][] = "At the moment, no entries are available. Please try again later.";
}
$params['copyright'] = copyright(2008, 'Moritz Petersen');
?>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Moritz Petersen</title>
    <link rel="icon" type="image/png" href="favicon.ico" />
    <link rel="alternate" type="application/rss+xml" title="Moritz Petersen (RSS)" href="http://feeds.feedburner.com/moritzpetersen/rss2" />
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="jquery.js"></script>
    <script type="text/javascript">
      $(document).ready(function() {
        $("div.entry > code").wrap("<pre></pre>");
      });
    </script>
  </head>
  <body>
    <div id="container">
      <div id="header">
        <a href="#"><img src="media/logo.png" alt="Moritz Petersen"/></a>
      </div>
      <div id="main">
        <div id="sidebar">
          <ul>
            <li><a href="http://blog.moritzpetersen.de">Blog</a></li>
            <li><a href="http://photos.moritzpetersen.de">Photos</a></li>
            <li><a href="http://blog.cloudme.org">Projects</a></li>
            <li><a href="impressum.html">Contact</a></li>
            <li class="small"><a href="http://feeds.feedburner.com/moritzpetersen/rss2">RSS Feed</a></li>
          </ul>
        </div>
        <div id="body">
          <?php if ($params['errors']) foreach($params['errors'] as $error): ?>
          <div class="error">
            <?php echo $error; ?>
          </div>
          <?php endforeach; ?>
          <?php if ($params['entries']) foreach ($params['entries'] as $entry): ?>
          <div class="entry">
            <h1>
              <a href="<?php out($entry, 'link') ?>" rel="nofollow">
                <?php out($entry, 'title') ?>
              </a>
            </h1>
            <div class="timestamp">
              <?php out($entry, 'date') ?>
            </div>
            <p>
              <?php out($entry, 'text') ?>
            </p>
            <div class="link">
              <a href="<?php out($entry, 'link') ?>" rel="nofollow">
                <?php out($entry, 'host') ?>
              </a>
            </div>
          </div>
          <?php endforeach; ?>
        </div>
        <div id="footer">
          <?php out($params, 'copyright'); ?>
        </div>
      </div>
    </div>
    <script type="text/javascript">
      var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
      document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
    </script>
    <script type="text/javascript">
      try {
        var pageTracker = _gat._getTracker("UA-350767-1");
        pageTracker._trackPageview();
      } catch(err) {}</script>
  </body>
</html>
