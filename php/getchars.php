<?php
function getchars($user){
 /**create a character as per user spec and give them a ship to fly 
  * @author rbeech
  *  Copyright 2012 Richard Beech rp.beech@gmail.com
  * To protect MySQL injection
  */
	$user = escape_data($user);

	$data="";
	/* connect to the db */
	global $dbc;
	mysql_select_db('test',$dbc) or die('Cannot select the DB');

	/* forumulate query */
	$query = mysql_query("select  player_account.account_id, currentcharships.*
			from player_account
			INNER JOIN currentcharships
			ON player_account.UID=currentcharships.player_account_UID 
			WHERE (player_account.uid = '$user')");

	while($row = mysql_fetch_assoc ( $query ))
		$data=$data. $row['uid']."#".
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
	 $row['item']."#".
	 $row['icon']."#".
	 $row['avatar'].
	 "\n";

	/* disconnect from the db */
	@mysql_close($dbc);
	return $data;
}
?>