<?php
function getavatardefs($dummy){
/*get avatar definitions*/
global $dbc;

	/* connect to the db */
	mysql_select_db('test',$dbc) or die('Cannot select the DB');

	$data="";
	$query = mysql_query("select * from avatardefs");
	
	while($row = mysql_fetch_assoc ( $query ))
	$data=$data. $row['idavatardefs']."#".
	$row['description']."#".
	 $row['imagename']."#".
	 $row['race']."#".
	 $row['sex'].
	"\n";

	/* disconnect from the db */
	return $data;
	@mysql_close($dbc);
}
?>