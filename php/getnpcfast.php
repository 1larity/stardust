
<?php
//demo query
mysql_connect("localhost","Playeraccount","akira01");
mysql_select_db("test");
     

       $query = mysql_query ("select npc.race, 
npc.x,
npc.y,
npc.z,
npc.is_static,
firstnames.firstname,
surnames.surname 
from firstnames,surnames,npc
where (npc.firstnames_uid=firstnames.uid 
and npc.surnames_uid=surnames.uid)");


while($row = mysql_fetch_assoc ( $query ))
echo $row['race'].','.
$row['x'].','.
$row['y'].','.
$row['z'].','.
$row['is_static'].','.
$row['firstname'].','.
$row['surname']."\n";


mysql_close();

?>
