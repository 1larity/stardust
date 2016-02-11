<?php
function  getrandomfemalehumanname($race){

	if (debug)echo $race."<br>";

	/* connect to the db */
	global $dbc;
	mysql_select_db('test',$dbc) or die('Cannot select the DB');


	$query = mysql_query("select * from getrandfullfrfemname limit 1");

	while($row = mysql_fetch_assoc ( $query ))
		return $row['firstname'].",".$row['surname'];
	//return "Cecily,Testcase\n";
	/* disconnect from the db */
	@mysql_close($dbc);
	/*}*/
}
?>