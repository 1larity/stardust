<?php
/** (c) Richard Beech (rp.beech@gmail.com
 * @author rbeech
 * Provides encryption functions*/

include ('padstring.php');

class MCrypt
{
	/** Declare key and init. vector must be 16 bytes for AES128
	 * or 32 bytes for AES128
	 */
	private $iv = 'fedcba9876543210';
	private $key = '8092214q87iPs43Q';


	function __construct()
	{
	}

	function encrypt($str) 
		{
		/*create a random init vector, convert to hex with some salt*/
		$iv = bin2hex(mcrypt_create_iv( 8, MCRYPT_RAND ));
		/*open encryption module for AES128 CBC*/
		$td = mcrypt_module_open('rijndael-128', '', 'cbc', $iv);
		/*init module*/
		mcrypt_generic_init($td, $this->key, $iv);
		/*encrypt*/
		$encrypted = mcrypt_generic($td, padstring($str));
		/*cleanup*/
		mcrypt_generic_deinit($td);
		mcrypt_module_close($td);
		//concat iv and message
		/** concat iv and message,
		 * TODO scramble iv into message more thoroughly
		 */
		return $iv.bin2hex($encrypted);
	}

	function decrypt($code)
	{
		/*init vector is first 16 bytes of message, discard salt*/
		$iv = substr($code,0,16);
		if (debug)echo $iv;
		/*message is whatever is left*/
		$code=substr($code,16);
		/*convert Hex to bin*/
		$code = $this->hex2bin($code);
		/*init encryption module for AES128 CBC*/
		$td = mcrypt_module_open('rijndael-128', '', 'cbc', $iv);
		/*init module*/
		mcrypt_generic_init($td, $this->key, $iv);
		/*decypt*/
		$decrypted = mdecrypt_generic($td, $code);
		/*cleanup*/
		mcrypt_generic_deinit($td);
		mcrypt_module_close($td);

		return utf8_encode(trim($decrypted));
	}

	protected function hex2bin($hexdata) 
	{
		$bindata = '';
		/*iterate through string, converting each Hex pair to bytes*/
		for ($i = 0; $i < strlen($hexdata); $i += 2) {
			$bindata .= chr(hexdec(substr($hexdata, $i, 2)));
		}

		return $bindata;
	}

}
?>