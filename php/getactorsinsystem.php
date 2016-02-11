<?php
/*provide web service to return NPCs and Players in my current system
 * requires the x,y,z, of my current system for where clause */

/* the request should be in the form http://localhost/everworld/getcharacter.php?char=35*/

/* require the user as the parameter */
/* if(isset($_GET['char'])) {*/
include('configmsgbrd.php');




/* soak in the passed variable or set our own */
$x = ($_GET['x']); //no default
$y = ($_GET['y']); //no default
$z = ($_GET['z']); //no default
$charid= ($_GET['charid']);
$lu= ($_GET['lu']);
// To protect MySQL injection
$x = stripslashes($x);
$y = stripslashes($y);
$z = stripslashes($z);
$charid = stripslashes($charid);
$lu= stripslashes($lu);
/* connect to the db */
global $dbc;
mysql_select_db('test',$dbc) or die('Cannot select the DB');

/* grab the see if a query for that account/ pw combo returns anthing */
$query = mysql_query("select uid,firstname, surname, sysx,sysy,sysz, hitpoints, status, pitchangle, yawangle,shipname,velocity from actors WHERE (x = '$x'and y='$y'and z='$z'and uid !='$charid' and lastupdate>'$lu')");

while($row = mysql_fetch_assoc ( $query ))
	echo $row['firstname'].",".
	$row['surname'].",".
	$row['sysx'].",".
	$row['sysy'].",".
	$row['sysz'].",".
	$row['hitpoints'].",".
	$row['uid'].",".
	$row['status'].",".
	$row['pitchangle'].",".
	$row['yawangle'].",".
	$row['shipname'].",".
	$row['velocity'].
	"\n";

/* disconnect from the db */
@mysql_close($dbc);
?>