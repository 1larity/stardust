<?php
function getfreeuser($user){
//check if this account name is unused
	global $dbc;
	$user = escape_data($user);


	/* connect to the db */
	mysql_select_db('test',$dbc) or die('Cannot select the DB');

	/* grab the see if a query for that account/ pw combo returns anthing */
	$query = mysql_query("SELECT count(account_id) as free FROM player_account WHERE (account_id='$user')");
	
	while($row = mysql_fetch_assoc ( $query ))
	return $row['free'];
	

	/* disconnect from the db */
	@mysql_close($dbc);
}
?>