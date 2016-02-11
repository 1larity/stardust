
<?php
/*provide web service to get inventory via Http Post */

/* the request should be in the form http://localhost/everworld/getcharacter.php?char=35*/

/* require the user as the parameter */
/* if(isset($_GET['item'])) {*/





	/* soak in the passed variable or set our own */
	$item = ($_GET['item']); //no default

	// To protect MySQL injection 
	$item = stripslashes($item);


	/* connect to the db */
	$link = mysql_connect('localhost','Playeraccount','akira01') or die('Cannot connect to the DB');
	mysql_select_db('test',$link) or die('Cannot select the DB');

	/* grab the see if a query for that account/ pw combo returns anthing */
	$query = mysql_query("delete from inventory WHERE (uid = '$item')");
	


	/* disconnect from the db */
	@mysql_close($link);
/*}*/
