<?php
/*retrieve news data for load screen */
include('configmsgbrd.php');
	/* connect to the db */
	global $dbc;
	mysql_select_db('test',$dbc) or die('Cannot select the DB');

	/* grab the see if a query for that account/ pw combo returns anthing */
	$query = mysql_query("SELECT news_text,news_title,news_date FROM news order by news_date  DESC Limit 5") ;
	
	while($row = mysql_fetch_assoc ( $query ))
	echo"<p><b>".$row['news_title']."</b> Date ".
	$row['news_date']." ".
	$row['news_text'].
	"<br></p>";

	/* disconnect from the db */
	@mysql_close($dbc);

exit();
?>