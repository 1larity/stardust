<?php
	function setuserlogintimestamp($userid){
		if (debug)echo "setting login time for".$userid;
		global $dbc;
		mysql_select_db('test',$dbc) or die('Cannot select the DB');
	$query = "UPDATE player_account SET lastlogin=(select NOW()from landscape limit 1) WHERE (uid=$userid)";		
	$result = mysql_query ($query) or trigger_error("Query: $query\n<br />MySQL Error: " . mysql_error());
		@mysql_close($dbc);
		return $result;
	}
?>