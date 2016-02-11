
<?php
//demo query
mysql_connect("localhost","Playeraccount","akira01");
mysql_select_db("test");
     

       $q = mysql_query("SELECT * FROM `ground_types`");

      while($e=mysql_fetch_assoc($q))

              $output[]=$e;

           print(json_encode($output));
     
    mysql_close();
?>