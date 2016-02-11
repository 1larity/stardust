<?php
/*retrieve news data for load screen */
include('configmsgbrd.php');
	/* connect to the db */
	global $dbc;
	mysql_select_db('test',$dbc) or die('Cannot select the DB');

	/* grab the see if a query for that account/ pw combo returns anthing */
	$query = mysql_query("SELECT firstname,surname,kills FROM player_char order by kills  DESC Limit 100") ;
	
	while($row = mysql_fetch_assoc ( $query ))
	echo"<p>".$row['firstname']." ".
	$row['surname']."      ".
	$row['kills'].
	"<br></p>";

	/* disconnect from the db */
	@mysql_close($dbc);

exit();
?>