<?php
function dockship($charid){
	global $dbc;
	// To protect MySQL injection 
	$charid = escape_data($charid);


	/* connect to the db */
	mysql_select_db('test',$dbc) or die('Cannot select the DB');

	/* grab the see if a query for that account/ pw combo returns anthing */
	$query = mysql_query("update player_char set status=0, hitpoints=1000  WHERE (uid = '$charid')");
	


	/* disconnect from the db */
	@mysql_close($dbc);
}
?>