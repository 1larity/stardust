<?php
// Copyright Richard Beech 2012
function getchat($channelID){
/*get avatar definitions*/
global $dbc;

	/* connect to the db */
	mysql_select_db('test',$dbc) or die('Cannot select the DB');
	$data="";
	/* if the channel id is negative, use the personal messages view*/
	if ($channelID<0)
	{
		$query = mysql_query("select * from privatechatlist where chatchannel=".$channelID." order by chattimestamp DESC limit 500");
		/* if the channel id is all, use the personal messages uninoned with public message view*/
	}else if($channelID==10){
		$query = mysql_query("(select * from privatechatlist where chatchannel=".$channelID." order by chattimestamp DESC limit 250)
		union
		(select * from chatlist  order by chattimestamp DESC limit 250)");
	}
	/* if the channel id is positive, use the public messages view*/
	else
	{
		$query = mysql_query("select * from chatlist where chatchannel=".$channelID." order by chattimestamp DESC limit 500");
	}
	
	
	
	while($row = mysql_fetch_assoc ( $query ))
	$data=$data. $row['firstname']." ".
	$row['surname']."#".
	 $row['chattimestamp']."#".
	 $row['chatmessage']."#".
 	 $row['channelname'].
	"\n";

	/* disconnect from the db */
	return $data;
	@mysql_close($dbc);
}
?>