<?php
function getshipdefs($dummy){
$data="";
	/* connect to the db */
	global $dbc;
	mysql_select_db('test',$dbc) or die('Cannot select the DB');

	/* grab the see if a query for that account/ pw combo returns anthing */
	$query = mysql_query("select * from shipdefs");
	
	while($row = mysql_fetch_assoc ( $query ))
	$data=$data. $row['uid']."#".
	$row['itemid']."#".
	 $row['jet1x']."#".
 	 $row['jet1y']."#".
	 $row['jet1z']."#".
	  $row['jet2x']."#".
 	 $row['jet2y']."#".
	 $row['jet2z']."#".
	  $row['jet3x']."#".
 	 $row['jet3y']."#".
	 $row['jet3z']."#".
	  $row['jet4x']."#".
 	 $row['jet4y']."#".
	 $row['jet4z']."#".
	  $row['jet5x']."#".
 	 $row['jet5y']."#".
	 $row['jet5z']."#". 
	  $row['maingunx']."#".
 	 $row['mainguny']."#".
	 $row['maingunz']."#".	 
	  $row['turret1x']."#".
 	 $row['turret1y']."#".
	 $row['turret1z']."#".
	   $row['turret2x']."#".
 	 $row['turret2y']."#".
	 $row['turret2z']."#".
	   $row['turret3x']."#".
 	 $row['turret3y']."#".
	 $row['turret3z']."#".
	   $row['turret4x']."#".
 	 $row['turret4y']."#".
	 $row['turret4z']."#".
	   $row['turret5x']."#".
 	 $row['turret5y']."#".
	 $row['turret5z']."#".
	 $row['model']."#".
	 $row['texture'].
	"\n";

	/* disconnect from the db */
	@mysql_close($dbc);
	return $data;
}
?>