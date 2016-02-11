
<?php
/*provide web service to get inventory via Http Post */

/* the request should be in the form http://localhost/everworld/getcharacter.php?char=35*/

/* require the user as the parameter */
/* if(isset($_GET['char'])) {*/





	/* soak in the passed variable or set our own */
	$char = ($_GET['char']); //no default

	// To protect MySQL injection 
	$char = stripslashes($char);


	/* connect to the db */
	$link = mysql_connect('localhost','Playeraccount','akira01') or die('Cannot connect to the DB');
	mysql_select_db('test',$link) or die('Cannot select the DB');

	/* grab the see if a query for that account/ pw combo returns anthing */
	$query = mysql_query("select * from getinventory WHERE (characteruid = '$char')");
	
	while($row = mysql_fetch_assoc ( $query ))
	echo $row['slot_id']."#".
	 $row['inventoryid']."#".
 	$row['uid']."#".
	 $row['category']."#".
	 $row['subcat']."#".
	 $row['item']."#".
	 $row['mass']."#".
	 $row['stacks']."#".
	 $row['description']."#".
	 $row['contraband']."#".
	 $row['recipe']."#".
	 $row['capacity']."#".
	 $row['level']."#".
	 $row['icon']."#".
	$row['quality']."#".
	$row['basevalue']."#".
	$row['effect'].

	"\n";

	/* disconnect from the db */
	@mysql_close($link);
/*}*/
