<?php
// Copyright Richard Beech 2012
include('header.html');
	$teststring="Testcase";


    $mcrypt = new MCrypt();
    #Encrypt
    $encrypted = $mcrypt->encrypt($teststring);
	echo '<p>';
	echo $teststring.'<br>';
	echo $encrypted.'<br>';
    #Decrypt
    $decrypted = $mcrypt->decrypt($encrypted);
	echo $decrypted.'<br>';
	$decrypted = $mcrypt->decrypt("90c8a551a7bb1812fdd5d3691a1ee6fec999e1ee0e58acb21d6153eb3957f35a4b373da95bad99451d49b922cba9075f");
			echo $decrypted.'<br>';
    ?>