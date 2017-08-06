<html>
<head>
<title>Submission</title>
<link rel="stylesheet" type="text/css" href="application.css">
</head>
<body>
<h2>Submission</h2>
<?php
	$link = include('connect.php');
	if ($link) {
		print('<p>Successful connection.</p>');
	} else {
		print('<p>Could not connect.</p>');
		print(mysqli_connect_error());
		exit;
	}

	$depart = $_POST['department'];
	$fName = $_POST['first_name'];
	$lName = $_POST['last_name'];
	$major = $_POST['major'];
	$gpa = $_POST['gpa'];
	
	if(empty($depart)) {
		print('<p>Department not specified. Application has not been submitted.</p>');
		exit;
	} elseif(empty($fName)) {
		print('<p>First name not specified. Application has not been submitted.</p>');
		exit;
	} elseif(empty($lName)) {
		print('<p>Last name not specified. Application has not been submitted.</p>');
		exit;
	} elseif(empty($major)) {
		print('<p>Major not specified. Application has not been submitted.</p>');
		exit;
	} elseif(empty($gpa)) {
		print('<p>GPA not specified. Application has not been submitted.</p>');
		exit;
	}
	
	$file = 'null';
	$resume = 'resume';
	$rName = '';
	$rMime = '';
	if (isset($_FILES[$resume])) {
		if (!$_FILES[$resume]['error']) {
			$upload_filename = $_FILES[$resume]['tmp_name'];
			$file = "'" . mysqli_real_escape_string($link, fread(fopen($upload_filename, "r"), 1000000)) . "'";
			$rName = $_FILES[$resume]['name'];
			$rMime = trim(exec('/usr/bin/file -b --mime-type ' . escapeshellarg($upload_filename)));
			print('<p>File size: ' . $_FILES[$resume]["size"] . '</p>');
			print('<p>File type: ' . $_FILES[$resume]["type"] . '</p>');
		} else {
			print('<p>No file specified for resume. Application has not been submitted.</p>');
			exit;
		}
	}
	
	mysqli_select_db($link, $database);
	
	$sql = "INSERT INTO Application " .
	       "(fName, lName, depart, major, gpa, resume, rName, rMime) VALUES " .
		   "('$fName', '$lName', '$depart', '$major', $gpa, $file, '$rName', '$rMime')";
	if ($result = mysqli_query($link, $sql)) {
		print('<p>Submitted application successfully!</p>');
	} else {
		print('<p>Did not submit application.</p>');
		exit;
	}
	mysqli_close($link);
?>
</body>
</html>