package com.digitale.utils;

import com.digitale.mygdxgame.Stardust3d;

//Demonstration of aes encryption use
public class Checker {

    public static void CheckEncryption() throws Exception {

        String password = "Testcase";
        MCrypt mcrypt = new MCrypt();
        /* Encrypt */
        String encrypted = ( mcrypt.encrypt(password) );
        /* Decrypt */
        String decrypted = new String( mcrypt.decrypt( encrypted ) );

        if(Stardust3d.DEBUG)System.out.println("Plain Text : " + password);
        if(Stardust3d.DEBUG)System.out.println("Encrypted Text : " + encrypted);
        if(Stardust3d.DEBUG)System.out.println("Decrypted Text : " + decrypted);
        decrypted = new String( mcrypt.decrypt( "a9e88a3511dfa65627ab49a1af32be07104dece4e4b53421" ) );
        if(Stardust3d.DEBUG)System.out.println("quick decrypt "+decrypted);
    }
}

