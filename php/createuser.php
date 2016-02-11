<?php
function createuser($username,$pw,$em){
/*provide web service to run query via Http Post */
	if (debug)echo "creating account with user ".$username." password ".$pw." email ".$em.'<br>';
/* the request should be in the form http://localhost/Everworld/web-service.php?user=USERNAME&num=10 */

/* require the user as the parameter */

	if (debug)echo "username accepted";
	
	// To protect MySQL injection (more detail about MySQL injection)
	$username = escape_data($username);
	$pw = escape_data($pw);
	$em = escape_data($em);

	/* connect to the db */
	global $dbc;
		mysql_select_db('test',$dbc) or die('Cannot select the DB');

// Make sure the userid is available.



$result = mysql_query ("SELECT account_id FROM player_account WHERE (account_id='$username')") or trigger_error('Sorry there is an account assigned to that userid');

if (mysql_num_rows($result) == 0) { // Available.
echo 'name is availble \n';
// Create the activation code.
// Create a random number with rand.
// Use it as a seed for uniqid, which when set to true generates a random number 23 digits in length
// Use it to seed md5 that creates a random string 32 characters in length
$a = md5(uniqid(rand(), true));
echo 'hashcode'.$a;
// Add the user. By entering values in a different order from the form sql injection can be limited 
// By using mysql_query I can make sure only one query is submitted blocking sql injection
// Never use the php multi_query function

$result = mysql_query ("INSERT INTO player_account ( email, password, active, account_id) VALUES ('$em','$pw','$a','$username')") or trigger_error('Sorry an error occurred and the account could not be created');
// Check that the effected rows was equal to 1 in the last query. Should log if greater than
echo 'effectedrows'.mysql_affected_rows();
if (mysql_affected_rows() == 1) { // If it ran OK.
echo 'effected rows'.mysql_affected_rows();
// Send the email.
// mysql_insert_id() retrieves the value of the last auto_incremented id
// Attach the random activation code in the link sent to the email

 	$ToEmail = $em; 
	$EmailSubject = 'Stardust Registration'; 
	$mailheader = 'From: rp.beech@gmail.com'.'\r\n'; 
	$mailheader .= 'Reply-To: rp.beech@gmail.com'.'\r\n'; 
	$mailheader .= 'Content-type: text/html; charset=iso-8859-1\r\n'; 
	$MESSAGE_BODY = 'Thank you for registering to play Stardust. If you did not register to play Stardust,please disregard this mail. To activate your account, please click on this link:';
	$MESSAGE_BODY .= 'http:/digitale.no-ip.org/everworld/stardustconfirm.php?x=' . mysql_insert_id() . '&y='.$a; 
	mail($ToEmail, $EmailSubject, $MESSAGE_BODY, $mailheader) or die ('Failure'); 
// Finish the page.
echo 'Thank you for registering! A confirmation email has been sent to your address. Please click on the link in that email in order to activate your account.';
@mysql_close($dbc);
} //end if affected rows
}//end if name is free
}
?>