<!DOCTYPE html>
<html>
<link type="text/css" rel="stylesheet" href="translate.css"/>
<head> 
<?php 
  $category = $_POST["category"];
  try{
	  $db = new PDO('sqlite:words.db');
	  $words = $db->query('SELECT DISTINCT englishWord FROM Words WHERE category = ' 
	  . "'$category'");
  } catch(PDOException $e){
	  echo $e->getMessage();
      exit(0);
  }
  $db = null;
?>
<title> <?php echo $category; ?> </title>
<style type = "text/css">
select{
	width:150px;
	height:22px;
}
</style>
</head>
<body>
<form action="page3.php" method="post">
  <fieldset id = "el">
	<legend id = "heading"><?php echo $category; ?>
	</legend>
	<select name ="englishWord">
	<?php
		foreach($words as $word) {
			$englishWord = $word['englishWord'];
			echo '<option value="'. $englishWord . '">' . 
			$englishWord . '</option>\n';
		}
	?>
	</select>
	&nbsp; &nbsp;
    <input type="submit"
	id="Sid"
	value="Translate"/>
  </fieldset>
</form>
</body>
</html>