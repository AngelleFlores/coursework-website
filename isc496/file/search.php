<html>
<head>
<title>Application Search</title>
<link rel="stylesheet" type="text/css" href="application.css">
</head>

<body>
<h2>Application Search</h2>
<?php
	$link = include('connect.php');
	mysqli_select_db($link, $database);
	
	$statement = "SELECT DISTINCT depart FROM Application";
	
	$result = mysqli_query($link, $statement);
	$departments = array();
	$index = 0;
	while($row = mysqli_fetch_array($result)){
	    $depart = $row['depart'];
		$departments[$index] = $depart;
		++$index;
	}
	
	mysqli_close($link);
?>
<form action="view.php" method="post">
<p>Select a department to view a list of students who have 
applied for that particular department.</p>

Department: &nbsp;
<select name="department">
<?php
	foreach($departments as $d) {
		echo '<option value="' . $d . '">'. $d . '</option>';
	}
?>
</select>
&nbsp;&nbsp;&nbsp;
<input type="submit" value="Submit" />
</form>
</body>
</html>