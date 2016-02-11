<?php
function  getrandommalehumanname($race){
/*provide random male human name for character creator */


if (debug)echo $race."<br>";
	/* connect to the db */
global $dbc;
	mysql_select_db('test',$dbc) or die('Cannot select the DB');

	
	$query = mysql_query("select * from getrandfullfrmalename");
	
	while($row = mysql_fetch_assoc ( $query ))
	return $row['firstname'].",".
	$row['surname'].
	"\n";

	/* disconnect from the db */
	@mysql_close($dbc);

}
?>