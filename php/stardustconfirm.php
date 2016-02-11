<?php
	include('header.html');
	include('starduststyle.html');
?>

<font color="white">
<h1>Activate Stardust Account</h1>

<?php

// Validate $_GET['x'] and $_GET['y'].

if (isset($_GET['x'])) {

	$x = (int) $_GET['x'];

} else {

	$x = 0;

}

if (isset($_GET['y'])) {

	$y = $_GET['y'];

} else {

	$y = 0;

}
//get account name for personalised welcome.
//	$query = "Select account_id from player_account WHERE (uid=$x AND active='" . escape_data($y) . "') LIMIT 1";
$query = mysql_query("Select account_id from player_account WHERE (uid=$x ) LIMIT 1");
echo "<p>Welcome to Stardust ";
while($row = mysql_fetch_assoc ( $query ))
	echo $row['account_id'];
echo ", if you haven't done so already, check out the <a href=https://sites.google.com/site/digitaletec/>Stardust website</a>  to find out<br>
 more about the game, catch up with the latest news and meet other players. If you use gmail, you can join <br>
our <a href=https://groups.google.com/forum/?fromgroups#!forum/stardust-exodus>google group</a> and chat with the team </p>";
// If $x and $y aren't correct, redirect the user.

if ( ($x > 0) && (strlen($y) == 32)) {

	$query = "UPDATE player_account SET active='TRUE', activateddate=(select NOW()from landscape limit 1) WHERE (uid=$x AND active='" . escape_data($y) . "') LIMIT 1";		

	$result = mysql_query ($query) or trigger_error("Query: $query\n<br />MySQL Error: " . mysql_error());

	

	// Print a customized message.
	//if account confirmed ok
	if (mysql_affected_rows() == 1) {

		echo "<br><br><h3>Your Stardust account is now active. 
		</br> Thanks for registering to play Stardust. You may now log in.</h3>";

	} else {
		//something has gone wrong, lets see if it has been confirmed.
		$query = "Select active from player_account WHERE (uid=$x AND active='TRUE') LIMIT 1";
		
		$result = mysql_query ($query) or trigger_error("Query: $query\n<br />MySQL Error: " . mysql_error());
		
		
		
		// account is already confirmed
		
		if (mysql_affected_rows() == 1) {
		
			echo '<br><br><h3><font color="red" size="+1">This account has already been activated.</font></h3>';
		
		} else {
		//no account was found 
			echo '<br><br><p><font color="red" size="+1">Your account could not be activated. Please re-check the link or contact the system administrator.</font></p>';
		
		}
		
	}

	// mysql_close();

} else { // Redirect.

	// Start defining the URL.

	$url = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['PHP_SELF']);

	// Check for a trailing slash.

	if ((substr($url, -1) == '/') OR (substr($url, -1) == '\\') ) {

		$url = substr ($url, 0, -1); // Chop off the slash.
	}

	// Add the page.
	$url .= '/index.php';

	//ob_end_clean(); // Delete the buffer.

	header("Location: $url");

	// exit(); // Quit the script.

} // End of main IF-ELSE.
?>

</div>
</body>
</html>
			
			
			
			
			
			
			
			
			
			
			
		