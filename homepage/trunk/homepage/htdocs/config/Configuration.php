<?php
$keys_file = 'config_keys.php';
$data_file = 'config_data.php';

require_once $keys_file;
if (file_exists($data_file)) { require_once $data_file; }

class Configuration {
  /**
   * Saves the request and returns an array that contains all undefined
   * configuration parameters as key => value pair.
   *
   * If all data has been stored successfully, false is returned.
   * 
   * @param array $request The request
   */
  function saveRequest($request) {
    global $configData;
    global $configKeys;
    $configParams = array();
    $invalidValue = false;
    foreach ($configKeys as $key) {
      if (!isset($configData[$key])) {
        $value = $request[$key];
        if (!$this->validate($value)) {
          $invalidValue = true;
        }
        $configParams[$key] = $value;
      }
    }
    if ($invalidValue) {
      return $configParams;
    }
    else {
      global $data_file;
      $file = fopen($data_file, 'w');
      fwrite($file, '<?php $configData = array(');
      $flag = $this->writeConfig($file, $configData);
      $this->writeConfig($file, $configParams, $flag);
      fwrite($file, '); ?>');
      fclose($file);
      return false;
    }
  }

private function writeConfig($file, $config, $flag = false) {
    foreach ($config as $key => $value) {
      if ($flag) {
        fwrite($file, ', ');
      }
      fwrite($file, '\'' . $key . '\' => \'' . $value . '\'');
      $flag = true;
    }
    return $flag;
  }

  private function validate($value) {
    return $value != '';
  }
}
