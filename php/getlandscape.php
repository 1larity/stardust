
<?php
//demo query
mysql_connect("localhost","Playeraccount","akira01");
mysql_select_db("test");
     

       $q = mysql_query("SELECT * FROM `systems` WHERE 
`x`>=0 
and `x` <=64
and `y`>=0 
and `y` <=64");

      while($e=mysql_fetch_assoc($q))

              $output[]=$e;

           print(json_encode($output));
     
    mysql_close();
?>