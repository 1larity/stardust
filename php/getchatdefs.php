<?php
function getchatdefs($dummy){
/*get chat channel definitions*/
global $dbc;

	/* connect to the db */
	mysql_select_db('test',$dbc) or die('Cannot select the DB');

	$data="";
	$query = mysql_query("select * from chatchannels order by idchatchannels");
	
	while($row = mysql_fetch_assoc ( $query ))
	$data=$data. $row['idchatchannels']."#".
	$row['channelname']."#".
	 $row['channeltype'].
	"\n";

	/* disconnect from the db */
	return $data;
	@mysql_close($dbc);
}
?>