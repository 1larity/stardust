	<?php
	function  padstring( $source)
    	{
    		 $paddingChar = "~";
    		 $size = 16;
    		$x = strlen($source) % $size;
    		$padLength = $size - $x;
    	
    		for ($i = 0; $i < $padLength; $i++)
    		{
    		$source =$source.$paddingChar;
    		}
    		if (debug)echo"padded string". $source."<br>";
    		return $source;
    	}
    	?>