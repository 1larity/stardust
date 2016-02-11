
<?php
/*provide web service to run query via Http Post */

/* the request should be in the form http://localhost/Everworld/setrouterequest.php?user=35&startx=10&starty=10&endx=20&endy=20*/

/* require the user as the parameter */
/* if(isset($_GET['user'])) {*/

	/* soak in the passed variable or set our own */
	$user = ($_GET['user']); //no default
	$startx = ($_GET['startx']);
	$starty = ($_GET['starty']);
	$endx = ($_GET['endx']);
	$endy = ($_GET['endy']);
	// To protect MySQL injection 
	$user = stripslashes($user);
	$startx = ($_GET['startx']);
	$starty = ($_GET['starty']);
	$endx = ($_GET['endx']);
	$endy = ($_GET['endy']);

	/* connect to the db */
	$link = mysql_connect('localhost','Playeraccount','akira01') or die('Cannot connect to the DB');
	mysql_select_db('test',$link) or die('Cannot select the DB');

	/* insert a route request into the database.*/
	$query = mysql_query("insert into pathrequest VALUES (null, '$user','$startx','$starty','$endx','$endy',0)");


	/* disconnect from the db */
	@mysql_close($link);
/*}*/
