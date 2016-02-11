<?php
 DEFINE('DBUSER','root');
 DEFINE('DBPW','universal40');
 DEFINE('DBHOST','localhost');
 DEFINE('DBNAME','test');
 
 if(@$dbc = mysql_connect(DBHOST, DBUSER, DBPW)){
  if(!mysql_select_db(DBNAME)){
   @trigger_error("Could not select ".mysql_error());
   echo "<font color='red' size='+2'>Notice: Could not select the unknown database.</font>";
   exit();
  }
 }else {
   @trigger_error("Could not connect to MySQL");
   echo "<font color='red' size='+2'>Notice: Some problem connecting to the host. Check your credentials</font>";
   exit();
  }
  
function escape_data($data){
 if(function_exists('mysql_real_escape_string')){
  global $dbc;
  $data = mysql_real_escape_string(trim($data),$dbc);
  $data = strip_tags($data);
 } else {
  $data = mysql_escape_string(trim($data));
  $data = strip_tags($data);
 }
 return $data;
}
?>