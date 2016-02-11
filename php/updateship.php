<?php
function updateship ($sx, $sy,$sz, $charid, $velocity,$pitch,$yaw){
global $dbc;
if (debug)echo "entering ship update  <br>";
	// To protect MySQL injection 
	$sx = escape_data($sx);
	$sy = escape_data($sy);
	$sz = escape_data($sz);
	$charid = escape_data($charid);
	$velocity = escape_data($velocity);
	$pitch = escape_data($pitch);
	$yaw = escape_data($yaw);
	/* connect to the db */
mysql_select_db('test',$dbc) or die('Cannot select the DB');

	/* grab the see if a query for that account/ pw combo returns anthing */

	$query = mysql_query("update player_char set sysx='$sx', sysy='$sy',sysz='$sz', yawangle='$yaw',pitchangle='$pitch', lastupdate=(select NOW() from landscape limit 1),velocity='$velocity' WHERE (uid = '$charid')");
	if(debug) echo "update player_char set sysx='$sx', sysy='$sy',sysz='$sz', velocity='$velocity',  lastupdate=(select NOW() from landscape limit 1) WHERE (uid = '$charid')<br>";
	if(debug) echo $query;
	/* disconnect from the db */
	@mysql_close($dbc);
}
?>