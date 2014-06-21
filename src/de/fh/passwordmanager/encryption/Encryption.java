package de.fh.passwordmanager.encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;




public class Encryption {
	
	/*
	 * Erstellen eines initialen 16bit Passwortes zur Verschlüsselung und Entschlüsselung
	 * 16 Werte zwischen 0, 255
	 */
	private final byte[] _internalPassword = new byte[] { 20, 10, 66, 66, 77, 88, 99, 98 }; 
	
	/*
	 * Funktion zur Verschlüsselung des Passwortes
	 */
	public byte[] encryptString(String password){
		
		byte[] encryptedPassword = null;
		// Erstellen des SecretKey
		SecretKeySpec specs = new SecretKeySpec(this._internalPassword, "DES");
		
	    //SecretKey myDesKey = new SecretKey();
		try{
            Cipher desCipher;
                        
            // Create the cipher 
            desCipher = Cipher.getInstance("DES");
 
            // Initialize the cipher for encryption
            desCipher.init(Cipher.ENCRYPT_MODE, specs);
 
            //sensitive information
            byte[] text = password.getBytes();
 
            // Encrypt the text
            encryptedPassword = desCipher.doFinal(text);
            
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
	/*
	 * Funktion zur Entschlüsselung
	 */
	public String decryptString(byte[] password){
		
		String decryptedPassword = "";
		
		try{
			
			// Erstellen des Secretkey
			SecretKeySpec specs = new SecretKeySpec(this._internalPassword, "DES");
			
 
            Cipher desCipher;
 
            // Create the cipher 
            desCipher = Cipher.getInstance("DES");
 
			// Initialize the same cipher for decryption
            desCipher.init(Cipher.DECRYPT_MODE, specs);
            
            byte[] pw_field = null;
			// Decrypt the text
            try {
            	pw_field  = desCipher.doFinal(password);
            	for (int i = 0; i < pw_field.length; i++){
        			decryptedPassword += (char) pw_field[i];
            	}
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
            
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
