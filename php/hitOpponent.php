<?php
function hitOpponent ($charid, $damage,$attacker){
global $dbc;
if (debug)echo "entering damage opponent  <br>";
	// To protect MySQL injection 
	$damage = escape_data($damage);
	$charid = escape_data($charid);
	$attacker = escape_data($attacker);
	/* connect to the db */
mysql_select_db('test',$dbc) or die('Cannot select the DB');


if ($charid>0)
{
	if (debug)echo "damage player  <br>";
	$queryString="update player_char set hitpoints=hitpoints - ".$damage.",lastattackeruid=".$attacker.", lastattacktime=(select NOW()from landscape limit 1) WHERE (uid = ".-$charid.")";
	$query = mysql_query($queryString);
	if (debug)echo $queryString."<br>";
}
else 
{
	if (debug)echo "damage npc  <br>";
	$queryString="update npc set hitpoints=hitpoints - ".$damage.",lastattackeruid=".$attacker.", lastattacktime=(select NOW()from landscape limit 1) WHERE (uid = ".-$charid.")";
	$query = mysql_query($queryString);
	if (debug)echo $queryString."<br>";
	//need some kind of ...if firstattackerUID=null set firstattacker time and id
	
}	
	/* disconnect from the db */
	@mysql_close($dbc);
}
?>