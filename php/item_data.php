<?php
include('page.php');
?>
<body>
<div id="header">
<center><div id="title">STARDUST DATABASE ENTRY</div></center>
</div>
<div id="mainBody">
 <div id="rightBox">
<?php

/*Checking values sent by the form
*/
 if(isset($_POST['submitted'])){
  
  //set values from the form to standard variables
  @$cat = $_POST['category'];
  @$subcat = $_POST['subcat'];
  @$iem = $_POST['item'];
  @$base = $_POST['basevalue'];
  @$vdeviate = $_POST['value_deviation'];
  @$scarcity = $_POST['scarcity'];
  @$mass = $_POST['mass'];
  @$stacks = $_POST['stacks'];
  @$desc = $_POST['description'];
  @$contrab = $_POST['contraband'];
  @$recipe = $_POST['recipe'];
  @$capa = $_POST['capacity'];
  @$effect = $_POST['effect'];
  @$level = $_POST['level'];
  @$icon = $_POST['icon'];
  @$quality = $_POST['quality'];
 
  if(@$_POST['category']){
    @$cat = escape_data($_POST['category']);
  } else {
   $cat = false;
   echo "<p><font color='red' size='-1'>Choose a category RICHARD!</font></p>";
  }
  if(@$_POST['subcat']){
    echo "<script>;
	dd = document.getElementById(\"subcats\");
	for(i=0;i<dd.options.length;i++){
	  $subcat = dd.options[i].value;
	}
	</script>";  
   } else {
   $subcat = false;
   echo "<p><font color='red' size='-1'>Choose a sub category RICHARD!</font></p>";
  }
  
  if(@$_POST['item']){
   @$item = escape_data($_POST['item']);
  } else {
   $item = false;
   echo "<p><font color='red' size='-1'>Choose an item</font></p>";
  }
  
  if(@$_POST['basevalue']){
   @$base = escape_data($_POST['basevalue']);
  } else {
   $base = false;
   echo "<p><font color='red' size='-1'>Choose an base value</font></p>";
  }
  
  if(@$_POST['value_deviation']){
   @$vdeviate = escape_data($_POST['value_deviation']);
  } else {
   $vdeviate = false;
   echo "<p><font color='red' size='-1'>Choose a vaue deviation</font></p>";
  }
  
  if(@$_POST['scarcity']){
   @$scarcity = escape_data($_POST['scarcity']);
  } else {
   $scarcity = false;
   echo "<p><font color='red' size='-1'>Choose scarcity</font></p>";
  }
  
  if(@$_POST['mass']){
   @$mass = escape_data($_POST['mass']);
  } else {
   $mass = false;
   echo "<p><font color='red' size='-1'>Choose mass</font></p>";
  }
  
  if(@$_POST['stacks']){
   @$stacks = escape_data($_POST['stacks']);
  } else {
   $stacks = false;
   echo "<p><font color='red' size='-1'>Choose scarcity</font></p>";
  }
   
  if(@$_POST['contraband']){
   @$contrab = escape_data($_POST['contraband']);
  } else {
   $contrab = false;
   echo "<p><font color='red' size='-1'>Choose contraband</font></p>";
  }
  
  if(@$_POST['recipe']){
   @$recipe = escape_data($_POST['recipe']);
  } else {
   $recipe = false;
   echo "<p><font color='red' size='-1'>Choose recipe</font></p>";
  }
  
  if(@$_POST['capacity']){
   @$capa = escape_data($_POST['capacity']);
  } else {
   $capa = false;
   echo "<p><font color='red' size='-1'>Choose capacity</font></p>";
  }
  
  if(@$_POST['effect']){
   @$effect = escape_data($_POST['effect']);
  } else {
   $effect = false;
   echo "<p><font color='red' size='-1'>Choose effect</font></p>";
  }
  
  if(@$_POST['level']){
   @$level = escape_data($_POST['level']);
  } else {
   $level = false;
   echo "<p><font color='red' size='-1'>Choose level</font></p>";
  }
  
  if(@$_POST['icon']){
   @$icon = escape_data($_POST['icon']);
  } else {
   $icon = false;
   echo "<p><font color='red' size='-1'>Choose icon</font></p>";
  }
  
  if(@$_POST['quality']){
   @$quality = escape_data($_POST['quality']);
  } else {
   $quality = false;
   echo "<p><font color='red' size='-1'>Choose quality</font></p>";
  }
  
  if(@$_POST['description']){
   @$desc = escape_data(trim($_POST['description']));
  } else {
   $desc = false;
   echo "<p><font color='red' size='-1'>Choose description</font></p>";
  }
  
  /*
  The following tests make sure all variables passed from the form are actually  existant 
  These variables have been passed to the form via the $_POST['name_of_var_from_form']
  example:  $cat = $_POST['category'];  
  */
 if($cat && $subcat && $item && $base && $vdeviate && $scarcity && $mass && $stacks && $desc && $contrab && $recipe && $capa && $effect && $level && $icon && $quality){
 $query = "INSERT INTO ITEM(category, subcat, item, basevalue, value_deviation, scarcity, mass, stacks, description, contraband, recipe, capacity, effect, level, icon, quality) VALUES
				('$cat','$subcat','$item','$base','$vdeviate','$scarcity','$mass','$stacks','$desc','$contrab','$recipe','$capa','$effect','$level','$icon','$quality')";
	$result = mysql_query($query) or trigger_error("Sorry an error occurred.");
	
	if(mysql_affected_rows() == 1){
	
	 echo "<h1>One Item Added.</h1>";
	 echo "<a href='item_data.php'>Back</a><br />";
	 exit();
	} else{
	  echo "<p><font color='red' size='+1'>Sorry something broke</font></p>";
	}
  } 
}
?>
 </div>
<div id="formDiv">
<center>
<form action="item_data.php" method="post">
<fieldset style="color:#fff;
background:url(img2.jpg) repeat ;   
background-repeat:repeat-y;"
>
<legend style="color:black;">
 
</legend>
<table style='font-family:tahoma;font-size:20px;' >
<th colspan="3" style="color:#fff;font-size:18px;">ITEMS TABLE </th>
 <tr>
  <td>Category</td>
  <td>
  <select name="category" id="categories" onchange="updateSubCat()" style="background-color:lightblue">
	<option value=""></option>
	<option value="1">lifeforms</option>
	<option value="2">vendortrash</option>
	<option value="3">tradegoods</option>
	<option value="4">consumables</option>
	<option value="5">ship</option>
	<option value="6">mod</option>
	<option value="7">weapons</option>
	<option value="8">armour</option>
  </select>
  </td>
  <td>
   <select name="subcat[]" id="subcats" style="background-color:lightblue">
	<option value="none">Sub-Category</option>
	</select>
	</td>  
 </tr>
 <tr>
  <td>Item:</td>
  <td><input type="text" name="item" size="12" maxlength="20" value="<?php if(isset($_POST['item'])) echo $_POST['item'];?>"/></td>
  <td><small>* varchar(20)</small></td>
 </tr> 
<tr>
  <td>Base Value:</td>
  <td><input type="text" name="basevalue" size="5" maxlength="6" value="<?php if(isset($_POST['basevalue'])) echo $_POST['basevalue'];?>" /></td>
  <td><small>* int(11)</small></td>
 </tr>
 <tr>
  <td>Value Deviation:</td>
  <td><input type="text" name="value_deviation" size="5" maxlength="6" value="<?php if(isset($_POST['value_deviation'])) echo $_POST['value_deviation'];?>" /></td>
  <td><small>* int(11)</small></td>
  </tr>
 <tr>
  <td>Scarcity:</td>
  <td>
  <select name="scarcity" id="scarcity" style="background-color:lightblue">
	<option value=""></option>
	<option value="1">1</option>
	<option value="2">2</option>
	<option value="3">3</option>
	<option value="4">4</option>
	<option value="5">5</option>
	<option value="6">6</option>
	<option value="7">7</option>
	<option value="8">8</option>
	<option value="9">9</option>
	<option value="10">10</option>
  </select>
  </td>
 </tr>
 <tr>
  <td>Mass:</td>
  <td><input type="text" name="mass" size="5" maxlength="6" value="<?php if(isset($_POST['mass'])) echo $_POST['mass'];?>" /></td>
  <td><small>* int(11)</small></td>
 </tr>
 <tr>
  <td>Stacks:</td>
  <td><input type="text" name="stacks" size="5" maxlength="6" value="<?php if(isset($_POST['stacks'])) echo $_POST['stacks'];?>" /></td>
  <td><small>* int(11)</small></td>
 </tr>
  <tr>
  <td>Contraband:</td>
  <td>
  <select name="contraband" id="contraband" onchange="updateContraband()" style="background-color:lightblue" >
	<option value=""></option>
	<option value="0">0</option>
	<option value="1">1</option>
	<option value="2">2</option>
	<option value="3">3</option>
	<option value="4">4</option>
	<option value="5">5</option>
  </select>
  </td>
  <td>
   <select name="definitions" id="definitions" style="background-color:lightblue">
   <option value="none">Definitions</option>
   </select>
 </td>
 </tr>
 <tr>
  <td>Recipe:</td>
  <td><input type="text" name="recipe" size="5" maxlength="6" value="<?php if(isset($_POST['recipe'])) echo $_POST['recipe'];?>" /></td>
  <td><small>* int(11)</small></td>
 </tr>
 <tr>
  <td>Capacity:</td>
  <td><input type="text" name="capacity" size="5" maxlength="6" value="<?php if(isset($_POST['capacity'])) echo $_POST['capacity'];?>" /></td>
  <td><small>* int(11)</small></td>
 </tr>
 <tr>
  <td>Effect:</td>
  <td><input type="text" name="effect" size="12" maxlength="45" value="<?php if(isset($_POST['effect'])) echo $_POST['effect'];?>" /></td>
  <td><small>* varchar(45)</small></td>
 </tr>
 <tr>
  <td>Level:</td>
  <td><input type="text" name="level" size="5" maxlength="6" value="<?php if(isset($_POST['level'])) echo $_POST['level'];?>" /></td>
  <td><small>* int(11)</small></td>
 </tr>
 <tr>
  <td>Icon:</td>
  <td><input type="text" name="icon" size="12" maxlength="45" value="<?php if(isset($_POST['icon'])) echo $_POST['icon'];?>" /></td>
  <td><small>* varchar(45)</small></td>
 </tr>
 <tr>
  <td>Quality:</td>
  <td><input type="text" name="quality" size="5" maxlength="5" value="<?php if(isset($_POST['quality'])) echo $_POST['quality'];?>" /></td>
  <td><small>* int(11)</small></td>
 </tr>
 </table></fieldset>
<fieldset style="color:#fff;
background:url(img2.jpg) repeat ;   
background-repeat:repeat-y;">


   <td>Description:<br>&nbsp;<textarea name="description" cols"80" rows="5"  maxlength="1000" value="<?php if(isset($_POST['description'])) echo $_POST['description'];?>">
  </textarea></td>
  <td>
  <div align="left"><button class="btn023" name="submit">Save<span><img src="images/p1.png" alt="" /></span></button></div>
  <input type="hidden"  name="submitted" vlaue="TRUE" />

</fieldset>
<hr style="border:2px solid red" />
</form>
</center>
</div>
<div id="leftBox">Choose a Table:</div>
</div>
</body>
</html>
