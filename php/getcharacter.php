<?php
/* Copyright Richard Beech 2012
 * return detailed information on the requested character ID
 */
function getcharacter ($char){
	/* To protect MySQL injection*/
	$char = escape_data($char);
	if (debug)echo "<br>get current char entered ".$char;

	/* connect to the db */
	global $dbc;
	mysql_select_db('test',$dbc) or die('Cannot select the DB');

	/* formulate query*/
	$query = mysql_query("select * from currentcharships WHERE (uid = '$char')");

	while($row = mysql_fetch_assoc ( $query ))
		return $row['uid']."#".
	 $row['firstname']."#".
	 $row['surname']."#".
	 $row['stamina']."#".
	 $row['intelligence']."#".
	 $row['social']."#".
	 $row['dexterity']."#".
	 $row['leadership']."#".
	 $row['recuperation']."#".
	 $row['exp']."#".
	 $row['x']."#".
	 $row['y']."#".
	 $row['z']."#".
	 $row['sysname']."#".
	 $row['currentship']."#".
	 $row['hitpoints']."#".
	 $row['shield']."#".
	 $row['power']."#".
	 $row['cpu']."#".
	 $row['item']."#".
	 $row['icon']."#".
	 $row['sysx']."#".
	 $row['sysy']."#".
	 $row['sysz']."#".
	 $row['status']."#".
	 $row['avatar']."#".
	 $row['credits'].
	 "\n";

	/* disconnect from the db */
	@mysql_close($dbc);
}
?>