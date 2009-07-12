<?php echo '<?xml version="1.0"?>' ?>
<rss version="2.0">
   <channel>
      <title>Moritz Petersen</title>
      <link>http://moritzpetersen.de</link>
      <description>The RSS feed from Moritz Petersen.</description>
      <language>en-us</language>
      <!--
      <pubDate>Tue, 10 Jun 2003 04:00:00 GMT</pubDate>
      <lastBuildDate>Tue, 10 Jun 2003 09:41:01 GMT</lastBuildDate>
      -->
      <managingEditor>mail@moritzpetersen.de</managingEditor>
      <webMaster>mail@moritzpetersen.de</webMaster>
      <?php if (isset($params['entries'])) foreach ($params['entries'] as $entry): ?>
      <item>
         <title><?php $params['renderer']->renderTitle($entry) ?></title>
         <link><?php $params['renderer']->renderLink($entry) ?></link>
         <description><?php $params['renderer']->renderContent($entry) ?></description>
         <pubDate><?php $params['renderer']->renderDate($entry) ?></pubDate>
         <guid><?php $params['renderer']->renderEntryLink($entry) ?></guid>
      </item>
      <?php endforeach; ?>
   </channel>
</rss>
