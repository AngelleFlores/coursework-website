<html>
<head>
<title>Application Search - Results</title>
<link rel="stylesheet" type="text/css" href="application.css">
<script type="text/javascript">
	function goBack() {
		window.history.back();
	}
</script>
</head>
<body>
<h2>Application Search - Results</h2>
<?php
	$department = $_POST['department'];
	echo '<p>List of students who have applied for the <em>' . $department 
	. '</em> department.</p>';
?>
 <table class="wide">
	<tr>
	<th class="center"> First Name </th>
	<th class="center"> Last Name </th>
	<th class="center"> Major </th>
	<th class="center"> Gpa </th>
	<th class="center"> Resume </th>
	</tr>

<?php
	$link = include('connect.php');
	$sqll = "SELECT id, fName, lName, major, gpa FROM Application WHERE depart = '$department'"; 
	
	if($ress = mysqli_query($link,$sqll)) {	
		while($row = mysqli_fetch_array($ress)){
			echo '<tr>';
			echo '<td class="center">' . $row['fName'] . '</td>';
			echo '<td class="center">' . $row['lName'] . '</td>';
			echo '<td class="center">' . $row['major'] . '</td>';
			echo '<td class="center">'  . $row['gpa']. '</td>';
			echo '<td class="center"><form action="pdf.php" method="post" target="_blank">' . 
			'<input type="hidden" name="id" value="' . $row['id'] . 
			'"><input type="submit" value="' . $row['fName'] . '\'s resume"></form></td>';
			echo '</tr>';
		}
		echo '</table>';
		mysqli_free_result($ress);
	}
	mysqli_close($link);

?>
<br>
<button onclick="goBack()">Go Back</button>
</body>
</html>