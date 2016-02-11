<?php
ob_start();
session_start();
require_once('/config/config.php');
//require_once('./include/recaptchalib.php');
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <title>StartDust_DB</title>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" name="description" />
 <title>StarDust</title>
 <link   type="text/css"        rel="stylesheet" href="css_lib/css_default.css" />  
 <script src="mylib.js" type="text/javascript"></script>
 <style>
#header{
width: 98%;
margin-left: auto;
margin-right: auto;
border: 1px #110000 solid; 
/*background:url(img2.jpg) repeat ;   
background-repeat:repeat-y; */
background-color: #fff;
height: 100px;
color: #380000;
}

#title{
position: relative;
width: 350px;
font-size: 18pt;
top: 30px;
padding-left: 15px;
/*border: 1pt solid black;*/
}

#mainBody{
width: 98%;
margin-left: auto;
margin-right: auto;
border: 1px #110000 solid; 
/*background:url(img2.jpg) repeat ;   
background-repeat:repeat-y; */
background-color: #fff; 
height: 600px;
color: #380000;
}

#leftBox{
width: 19.75%;
margin-right: auto;
border: 1px #110000 solid; 
/*background:url(img2.jpg) repeat ;   
background-repeat:repeat-y; */
background-color: #fff;height: 600px;
color: #380000;
position: relative;
top: -1px;
}

#rightBox{
width: 29.95%;
margin-left: auto;
border: 1px #110000 solid; 
/*background:url(img2.jpg) repeat ;   
background-repeat:repeat-y; */
background-color: #fff;
height: 600px;
color: #380000;
float: right;
position: relative;
top: -1px;
}


#formDiv{
width: 50%;
margin-left: auto;
border: 1px #110000 solid; 
/*background:url(img2.jpg) repeat ;   
background-repeat:repeat-y; */
background-color: #fff;
height: 600px;
color: #380000;
float: right;
position: relative;
top: -1px;

}

#phpForm{

}
 </style>
 </head>

















 