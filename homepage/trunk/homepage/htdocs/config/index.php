<?php
 include_once 'Configuration.php';
 $configuration = new Configuration();
 $config = $configuration->saveRequest($_REQUEST);
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Configuration</title>
    <link rel="stylesheet" type="text/css" href="style.css">
  </head>
  <body>
    <h1>Configuration</h1>
<?php if ($config): ?>
    <p>Please enter the configuration parameters:</p>
    <form action="." method="post">
      <table>
<?php foreach ($config as $key => $value): ?>
      <tr>
      <td><?php echo $key ?>:</td>
      <td><input type="text" value="<?php echo $value ?>" name="<?php echo $key ?>"/></td>
      </tr>
<?php endforeach; ?>
      <tr><td colspan="2" class="submit"><input type="submit" name="submit"/></td></tr>
      </table>
    </form>
<?php elseif (isset($_REQUEST['submit'])): ?>
    <p>The configuration data has been stored successfully.</p>
<?php else: ?>
    <p>The configuration data is up to date. If you need to update the 
    configuration of your system you have three options:</p>
    <ul>
      <li>Add the configuration key to <tt>configKeys.php</tt> and 
      <a href="index.php">click here</a>. You will have to enter the new
      configuration parameters.</li>
      <li>Update the file <tt>configData.php</tt> manually.</li>
      <li>Remove the file <tt>configData.php</tt> and <a href="index.php">click 
      here</a>. You will have to enter all configuration parameters again.</li>
    </ul>
<?php endif ?>
  </body>
</html>
