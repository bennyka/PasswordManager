package de.fh.passwordmanager.encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;




public class Encryption {
	

	
	/*
	 * Funktion zur Verschlüsselung des Passwortes
	 */
	public byte[] encryptString(String password){
		
		byte[] encryptedPassword = null;
		KeyGenerator keygenerator = null;
		try {
			keygenerator = KeyGenerator.getInstance("DES");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    SecretKey myDesKey = keygenerator.generateKey();
		
		try{
            Cipher desCipher;
                        
            // Create the cipher 
            desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
 
            // Initialize the cipher for encryption
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
 
            //sensitive information
            byte[] text = password.getBytes();
 
            // Encrypt the text
            encryptedPassword = desCipher.doFinal(text);
            
            System.out.println("verschlüsselter String: " + encryptedPassword );
            
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch(NoSuchPaddingException e){
            e.printStackTrace();
        }catch(InvalidKeyException e){
            e.printStackTrace();
        }catch(IllegalBlockSizeException e){
            e.printStackTrace();
        }catch(BadPaddingException e){
            e.printStackTrace();
        };
        return encryptedPassword;
	};
	
	public String decryptString(byte[] password){
		
		String decryptedPassword = "";
		
		try{
			
			KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            SecretKey myDesKey = keygenerator.generateKey();
 
            Cipher desCipher;
 
            // Create the cipher 
            desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
 
			// Initialize the same cipher for decryption
            desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
            
            byte[] pw_field = null;
			// Decrypt the text
            try {
            	pw_field  = desCipher.doFinal(password);
            	for (int i = 0; i < pw_field.length; i++){
        			decryptedPassword += (char) pw_field[i];
            	}
            	
//            	String result = new String(decryptedPassword.getBytes(),"UTF-8");
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Verschlüsselung fehlgeschlagen (Fehler 1): "+e);
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Verschlüsselung fehlgeschlagen (Fehler 2): "+e);
			}

        	System.out.println("Entschlüsseltes Passwort: blubb :"+decryptedPassword);
            
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch(NoSuchPaddingException e){
            e.printStackTrace();
        }catch(InvalidKeyException e){
            e.printStackTrace();
        };
        return decryptedPassword;
	};
};
