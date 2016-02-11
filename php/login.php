<?php
include('setuserlogintimestamp.php');
function login($user,$pw){
/*provide web service to run query via Http Post */
	if (debug)echo "log in with user ".$user." password ".$pw.'<br>';
	// To protect MySQL injection (more detail about MySQL injection)
	$user = escape_data($user);
	$pw = escape_data($pw);
	/* connect to the db */
	global $dbc;
	mysql_select_db('test',$dbc) or die('Cannot select the DB');

	/* grab the see if a query for that account/ pw combo returns anthing */
	$query = mysql_query("SELECT count(*) as uid, active, uid as accuid FROM player_account WHERE (account_id = '$user'and password ='$pw')limit 1");
	$row = mysql_fetch_assoc ( $query );
	$uid= $row['uid'];
	$active=$row['active'];
	$accuid=$row['accuid'];	
if (debug)echo "userid".$accuid;
setuserlogintimestamp($accuid);
	/* disconnect from the db */
return $uid.$active.'#1'.$accuid."\n";
	@mysql_close($dbc);
}
?>