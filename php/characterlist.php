//demo query
<?php
mysql_connect("localhost","Playeraccount","akira01");
mysql_select_db("test");
     

      //  $q=mysql_query("SELECT * FROM people WHERE birthyear>'".$_REQUEST['year']."'");
	 $q=mysql_query("SELECT * FROM player_account WHERE account_id > Richard Beech");

      while($e=mysql_fetch_assoc($q))

              $output[]=$e;

           print(json_encode($output));
     
    mysql_close();
?>