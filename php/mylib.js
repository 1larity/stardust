//following function is used on PopulateDays.html used on myForm
function updateSubCat() {
	dd = document.getElementById("subcats");
	dd.options.length = 0;
	dd.options[0] = new Option("Select the category", 0);
 var categories = document.getElementById("categories").value;
	switch (categories){
		case "lifeforms":
		dd.options[0] = new Option("liform 1",0);
		dd.options[1]= new Option("lifeform 2",1);
		dd.options[2]= new Option("lifeform 3",2);
		break;
		
		case "vendortrash":
		dd.options[0] = new Option("vendortrash 1",0);
		dd.options[1]= new Option("vendortrash 2",1);
		dd.options[2]= new Option("vendortrash 3",2);
		break;
		
		case "tradegoods":
		dd.options[0] = new Option("tradegoods 1",0);
		dd.options[1]= new Option("tradegoods 2",1);
		dd.options[2]= new Option("tradegoods 3",2);
		break;
		
		
		case "consumables":
		dd.options[0] = new Option("consumables 1",0);
		dd.options[1]= new Option("consumables 2",1);
		dd.options[2]= new Option("consumables 3",2);
		break;
		
		
		case "ship":
		dd.options[0] = new Option("ship 1",0);
		dd.options[1]= new Option("ship 2",1);
		dd.options[2]= new Option("ship 3",2);
		break;
		
		case "mod":
		dd.options[0] = new Option("mod 1",0);
		dd.options[1]= new Option("mod 2",1);
		dd.options[2]= new Option("mod 3",2);
		break;
		
		case "weapons":
		dd.options[0] = new Option("weapons 1",0);
		dd.options[1]= new Option("weapons 2",1);
		dd.options[2]= new Option("weapons 3",2);
		break;
		
		case "armour":
		dd.options[0] = new Option("armour 1",0);
		dd.options[1]= new Option("armour 2",1);
		dd.options[2]= new Option("armour 3",2);
		break;
		
		default:
		dd.options[0]= new Option("",0);
		
	}
}


function updateContraband() {
	dd = document.getElementById("definitions");
	dd.options.length = 0;
	dd.options[0] = new Option("Select the contraband", 0);
 var contraband = document.getElementById("contraband").value;
	switch (contraband){
		case "0":
		dd.options[0] = new Option("legal",0);
		break;
		case "1":
		dd.options[0] = new Option("misdemeanor",0);
		break;		
		case "2":
		dd.options[0] = new Option("slaves",0);
		break;
		case "3":
		dd.options[0] = new Option("drugs",0);
		break;
		case "4":
		dd.options[0] = new Option("medal",0);
		break;
		case "5":
		dd.options[0] = new Option("illicit",0);
		break;
		
		default:
		dd.options[0] = new Option("",0);		
	}
}


//function used on the secondForm
function goSomeWhere() {
	var url = document.getElementById("siteList").value;
	window.open(url);
}

//The following two functions are used under the third form
function toggle_innerDiv() {
	var innerDiv = document.getElementById("innerDiv");
	if (innerDiv.style.visibility == "visible") {
		innerDiv.style.visibility = "hidden";
	} else {
		innerDiv.style.visibility = "visible";
	}
}
function toggle_outerDiv() {
	var outerDiv = document.getElementById("outerDiv");
	if (outerDiv.style.visibility == "visible") {
		outerDiv.style.visibility = "hidden";
	} else {
		outerDiv.style.visibility = "visible";
	}
}

//Next three functions are used under the fourthForm on the page
function clear() {
	document.getElementById("countryList").value = " ";
	document.getElementById("txtEntry").value = " ";
}

function get_list() {
	entryBox = document.getElementById("txtEntry");
	entry_value = entryBox.value.toUpperCase();
	list = document.getElementById("countryList");
	list.value = " ";
	var countries = new Array("Afghanistan", "Albania", "Bahamas", "Bahrain", "Barbados", "Cambodia", "Cameroon",
									"Chad", "Senegal", "SanDomingo");
	
	for (i=0; i<countries.length; i++) {
		if (countries[i].toUpperCase().indexOf(entry_value, 0) == 0) {
			list.value = list.value + " " + countries[i];
		}
	}
}

function fill_box() {
	var txt = " ";
	if (window.getSelection) {
		txt = window.getSelection().toString();
	} else if (document.getSelection) {
		txt = document.getSelection();
	} else if (document.getSelection) {
		txt = document.getSelection.createRange().text;
	} else return;
		entryBox = document.getElementById("txtEntry");
		entryBox.value = txt;
}

//Next two functions under fithForm on page
function highlight_on(spanname) {
	var s = document.getElementById(spanname)	
			s.style.color = "red";
}

function highlight_off(spanname) {
	var s = document.getElementById(spanname)
			s.style.color = "black";
}


function checkform(){
	var name = document.getElementById("firsName").value;
	if(name==""){
		alert("Must enter your name!");
		return false;
	}
	if(isNaN(name)== false){
		alert("Numbers are not allowed in the name field!\nTry again");
		return false;
	}
	if(document.getElementById("lastName").value==""){
		alert("Must enter your age!");
		return false;
	}
	if(isNaN(document.getElementById("lastName").value)){
		alert("Only numbers are alowed in the age box!");
		return false;
	}
	return true;
	
// placeName = document.getElementById("newName");
	//placeName.innerHTML = name;
}

function start_time() {
	var start = new Date();
	document.getElementById("st").innerHTML = start;
}
function what_time() {
	var start = new Date();
	document.getElementById("st").innerHTML = start;
	
	//var start = document.getElementById("st").innerHTML;
	var counter = document.getElementById("counter");
	var d2 = Date.parse(start);
	var d = new Date();
	var elapsed = ((d-d2)/1000);
	counter.innerHTML = "Elapsed time: " + (elapsed/60).toFixed(2) + " minutes";
}



