<?php
  $credential_file = 'credentials.php';
  $creds = include $credential_file;
  $host = $creds['host'];
  $user = $creds['user'];
  $password = $creds['password'];
  $database = $creds['database'];

  return mysqli_connect("$host","$user","$password","$database", null, "/tmp/od-6566");
?>