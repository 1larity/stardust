<?php
// (c) Richard Beech (rp.beech@gmail.com
//  @author rbeech
// interface for client calls
// this should intecept all client posts, decrypt them, run the appropriate
// data access PHP script and pass any result back, encrypted to the client.
include('configmsgbrd.php');
include('mcrypt.php');
include('createuser.php');
include('getrandommalehumanname.php');
include('login.php');
include('getchars.php');
include('getcharacter.php');
include('getrandomfemalehumanname.php');
include('makeavatar.php');
include('getavatardefs.php');
include('getshipdefs.php');
include('getfreeuser.php');
include('dockship.php');
include('undockship.php');
include('updateship.php');
include('postchat.php');
include('getchat.php');
include('getchatdefs.php');
include('hitOpponent.php');
include('getassetdefs.php');
include('sell.php');
if (debug)echo '<p>';
//get php data
$data= ($_GET['data']);
//clean up to prevent sql injection 
$data=escape_data($data);
if (debug)echo "incoming data".$data;
if (debug)echo '<br>';
//decrypt data
$mcrypt = new MCrypt();
$request=$mcrypt->decrypt($data);
if (debug)echo "<br>decrypted data line ".$request;
if (debug)echo '<br>';
//seperate out the data items from the string, delimited by "#" 
$rowdata = explode("#", $request);
if (debug)echo $rowdata[0];
//See which function we need first char in decrypted data holds function ID
switch(trim($rowdata[0])){
	case "0":
		if (debug)echo "<br>getrandommalehumanname requested <br>";
		$response=getrandommalehumanname(trim($rowdata[1]));
		break;
	case "1":
		if (debug)echo "<br>getrandomfemmalehumanname requested <br>";
		$response=getrandomfemalehumanname(trim($rowdata[1]));
		break;
	case "2":
		if (debug)echo "<br>new account requested <br>";
		$response=createuser(trim($rowdata[1]),trim($rowdata[2]),str_replace("~","",trim($rowdata[3])));
		break;
	case "3":
		if (debug)echo "<br>login requested <br>";
		$response=login(trim($rowdata[1]),str_replace("~","",trim($rowdata[2])));
		break;
	case "4":
		if (debug)echo "<br>characterlist requested <br>";
		$response=getchars(str_replace("~","",trim($rowdata[1])));
		break;
	case "5":
		if (debug)echo "<br>currentchar requested <br>";
		$response=getcharacter(str_replace("~","",trim($rowdata[1])));
		break;
	case "6":
		if (debug)echo "<br>new avatar requested <br>";
		$response=makeavatar(trim($rowdata[1]),trim($rowdata[2]),trim($rowdata[3]),trim($rowdata[4]),trim($rowdata[5]),str_replace("~","",trim($rowdata[6])));
		break;
	case "7":
		if (debug)echo "<br> avatar defs requested <br>";
		$response=getavatardefs(trim($rowdata[1]));
		break;
	case "8":
		if (debug)echo "<br> ship defs requested <br>";
		$response=getshipdefs(trim($rowdata[1]));
		break;
	case "9":
		if (debug)echo "<br> free user requested <br>";
		$response=getfreeuser(trim($rowdata[1]));
		break;
	case "10":
		if (debug)echo "<br> dock requested <br>";
		$response=dockship(trim($rowdata[1]));
		break;
	case "11":
		if (debug)echo "<br> undock requested <br>";
		$response=undockship(trim($rowdata[1]));
		break;
	case "12":
		if (debug)echo "<br> ship update requested <br>";
		$response=updateship(trim($rowdata[1]),trim($rowdata[2]),trim($rowdata[3]),trim($rowdata[4]),trim($rowdata[5]),trim($rowdata[6]),str_replace("~","",trim($rowdata[7])));
		break;
	case "13":
		if (debug)echo "<br>post chat requested <br>";
		$response=postchat(trim($rowdata[1]),trim($rowdata[2]),str_replace("~","",trim($rowdata[3])));
		break;
	case "14":
		if (debug)echo "<br> get chat requested <br>";
		$response=getchat(trim($rowdata[1],trim($rowdata[2])));
		break;
	case "15":
		if (debug)echo "<br> get chat channels <br>";
		$response=getchatdefs(trim($rowdata[1]));
		break;
	case "16":
		if (debug)echo "<br> hit opponent <br>";
		$response=hitOpponent(trim($rowdata[1]),trim($rowdata[2]),trim($rowdata[3]));
		break;
	case "17":
			if (debug)echo "<br> get assetdefs <br>";
			$response=getassetdefs(trim($rowdata[1]));
			break;
	case "18":
			if (debug)echo "<br> sale <br>";
			$response=sell(trim($rowdata[1]),trim($rowdata[2]),trim($rowdata[3]));
			break;
	default:
		echo "<br> wtf, somethings gone horribly wrong : Unknown Data request<br>.$rowdata[0]";
		break;
}
if (debug)echo $response.'<br>';
$encrypted = $mcrypt->encrypt($response);
echo $encrypted;
?>