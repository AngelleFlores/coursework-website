<!DOCTYPE html>
<html>
<link type="text/css" rel="stylesheet" href="translate.css"/>
<head> 
<?php
  $englishWord = $_POST["englishWord"];
  try{
	  $file = 'words.db';
	  $db = new PDO('sqlite:' . $file);
	  $result = $db->query('SELECT DISTINCT spanishWord FROM Words WHERE englishWord = ' 
	  . "'$englishWord'");
	  foreach ($result as $row) {
		  $spanishWord = $row['spanishWord'];
		  break;
	  }
  } catch(PDOException $e){
	  echo $e->getMessage();
      exit(0);
  }
  $db = null;
?>
<title> Translation </title>
</head>
<body>
<h3> Translation </h3>

  <table id="this">
    <tr>
	  <td>English</td>
	  <td>Spanish</td>
	</tr>
	<tr>
	  <td><?php echo $englishWord; ?></td>
	  <td><?php echo $spanishWord; ?></td>
	</tr>
  </table>
  <table style="width:100%">
  <td></td>
  <td> <br> <br> <form action="page1.html">
    <input type="submit" 
	id="Tid"
	value="Try Again">
  </form> </td>
  <td> </td>
 
  </table>
</body>
</html>