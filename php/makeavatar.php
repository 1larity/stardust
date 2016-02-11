<?php
/*create a character as per user spec and give them a ship to fly */
function makeavatar($avatar,$race,$sex,$fname,$sname,$uname){
	
	global $dbc;

	// To protect MySQL injection
	$avatar = escape_data($avatar);
	$race = escape_data($race);
	$sex = escape_data($sex);
	$fname = escape_data($fname);
	$sname = escape_data($sname);
	$uname = escape_data($uname);
	/* connect to the db */
	mysql_select_db('test',$dbc) or die('Cannot select the DB');

	/* forumulate query */

	$query = "Insert into player_char (`avatar`,`race`,`sex`,`firstname`,`surname`,`player_account_UID`,`x`,`y`,`z`)
	values( '$avatar','$race','$sex', '$fname', '$sname','$uname',1,1,10)";


	if (!mysql_query($query,$dbc))
	{
		return ('Error: ' . mysql_error());
		die('Error: ' . mysql_error());
	}
	if (debug)echo "1 character added";

	/*get the id of the new charater*/
	$query = mysql_query("select uid from player_char where (firstname = '$fname' and surname='$sname')");

	while($row = mysql_fetch_assoc ( $query ))
		$charuid= $row['uid'];
	if (debug)echo "uid is ". $charuid;
	/* create noobship belonging to this charater uid */
	$query = "Insert into inventory (`characteruid`,`item_ID`)
	values( '$charuid',56)";


	if (!mysql_query($query,$dbc))
	{
		die('Error: ' . mysql_error());
	}
	if (debug)echo " 1 ship added";
	/*get the id of the new ship*/
	$query = mysql_query("select uid from inventory where (characteruid = '$charuid' and item_ID='56')");

	while($row = mysql_fetch_assoc ( $query ))
		$shipuid= $row['uid'];
	if (debug)echo  " ship uid is ". $shipuid;
	/*update character to make this thier current ship*/
	$query = mysql_query("update player_char set currentship='$shipuid' WHERE (uid = '$charuid')");


	/* disconnect from the db */
	@mysql_close($dbc);
}
?>