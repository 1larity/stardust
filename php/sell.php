<?php
function sell ($charid, $itemid,$vendorid){
global $dbc;
if (debug)echo "entering vendor item <br>";
	// To protect MySQL injection 
	$charid = escape_data($charid);
	$itemid = escape_data($itemid);
	$vendorid = escape_data($vendorid);
	/* connect to the db */
mysql_select_db('test',$dbc) or die('Cannot select the DB');

$queryString="select basevalue from item, inventory where (item.uid=inventory.item_ID and inventory.uid=".$itemid.")";
$query = mysql_query($queryString);
if (debug)echo $queryString."<br>";
while($row = mysql_fetch_assoc ( $query )){
	$data=$row['basevalue'];
}
	$queryString="update inventory set characteruid=".-$vendorid." WHERE (characteruid = ".$charid." and uid= ".$itemid.")";
	$query = mysql_query($queryString);
	if (debug)echo $queryString."<br>";

	if (debug)echo "sell  <br>";
	$queryString="update player_char set credits=credits +".$data."  WHERE (player_char.uid = ".$charid.")";
	$query = mysql_query($queryString);
	if (debug)echo $queryString."<br>";
	
	
	if (debug)echo $data."<br>";
	$queryString ="INSERT INTO chatmessages (`chaituid`, `chatmessage`, `senderid`, `chatchannel`) "
	. " VALUES (NULL,'You receive ".$data." \$D'"
	. ",'48','".-$charid. "')";
	$query = mysql_query($queryString);
	if (debug)echo $queryString."<br>";
	/* disconnect from the db */
	@mysql_close($dbc);
	return $data;
}
?>