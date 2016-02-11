<?php
// Copyright Richard Beech 2012
function postchat($text,$channel,$senderid){

/*post in a chat channel  */
	global $dbc;

	// To protect MySQL injection 
	$text = escape_data($text);
	$channel = escape_data($channel);
	$senderid = escape_data($senderid);
	
		mysql_select_db('test',$dbc) or die('Cannot select the DB');

	/* grab the see if a query for that account/ pw combo returns anthing */

	$query = "Insert into chatmessages (`chatmessage`,`senderid`,`chatchannel`)
			values( '$text','$senderid','$channel')";
	
	
	if (!mysql_query($query,$dbc))
	{	
		return ('Error: ' . mysql_error());
		die('Error: ' . mysql_error());
	}
if (debug)echo "1 chat line added";
	

	@mysql_close($dbc);
}
?>