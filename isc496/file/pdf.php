<?php
	$id = $_POST['id'];
	$link = include('connect.php');
	$query = "SELECT resume, rMime FROM Application WHERE id = '$id'"; 
	
	if($result = mysqli_query($link,$query)) {	
		while($row = mysqli_fetch_array($result)){
			$type = $row['rMime'];
			$file = $row['resume'];
			$len = strlen($file);
			header("Content-Type: " . $type);
			header("Content-Length: " . $len);
			print($file);
		}
		mysqli_free_result($result);
	}
	mysqli_close($link);
	
?>