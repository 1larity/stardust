
<?php
/*provide web service to run query via Http Post */

/* the request should be in the form http://localhost/everworld/getcharacter.php?char=35*/

/* require the user as the parameter */
/* if(isset($_GET['char'])) {*/





	/* soak in the passed variable or set our own */
	$x = ($_GET['x']); //no default
	$y = ($_GET['y']); //no default
	$z = ($_GET['z']); //no default
	// To protect MySQL injection 
	$x = stripslashes($x);
$y = stripslashes($y);
$z = stripslashes($z);

	/* connect to the db */
	$link = mysql_connect('localhost','Playeraccount','akira01') or die('Cannot connect to the DB');
	mysql_select_db('test',$link) or die('Cannot select the DB');

	/* grab the see if a query for that account/ pw combo returns anthing */
	$query = mysql_query("select * from structureswithdetails WHERE (x = '$x'and y='$y'and z='$z')");
	
	while($row = mysql_fetch_assoc ( $query ))
	echo $row['x'].",".
	 $row['y'].",".
 $row['z'].",".
	 $row['sysx'].",".
	 $row['sysy'].",".
	 $row['sysz'].",".
	 $row['modelscale'].",".
	 $row['activation_radius'].",".
	 $row['name'].",".
	 $row['structname'].
	"\n";

	/* disconnect from the db */
	@mysql_close($link);
/*}*/
