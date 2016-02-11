<?php
function getassetdefs($dummy){
/*get avatar definitions*/
global $dbc;

	/* connect to the db */
	mysql_select_db('test',$dbc) or die('Cannot select the DB');

	$data="";
	$query = mysql_query("select uid,assetname,ext,type from assets order by uid");
	
	while($row = mysql_fetch_assoc ( $query ))
	$data=$data. $row['uid']."#".
	$row['assetname']."#".
	 $row['ext']."#".
	 $row['type'].
	"\n";

	/* disconnect from the db */
	return $data;
	@mysql_close($dbc);
}
?>