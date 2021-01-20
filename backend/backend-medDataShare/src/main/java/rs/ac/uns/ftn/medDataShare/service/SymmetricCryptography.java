package rs.ac.uns.ftn.medDataShare.service;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

@Service
public class SymmetricCryptography {

	private static final SecureRandom RANDOM = new SecureRandom();
    private static final byte[] IV =
        { 16, 74, 71, -80, 32, 101, -47, 72, 117, -14, 0, -29, 70, 65, -12, 74 };

	@Autowired
	private SecretKey key;

	public SecretKey generateKey() {
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(256, RANDOM);
			return keyGen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
        return null;
	}
	
	public byte[] encrypt(String plainText) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			// AlgorithmParameterSpec paramSpec=new PBEParameterSpec(salt,iterationCount);
	        AlgorithmParameterSpec paramSpec=new IvParameterSpec(IV);    //KONF?????
			cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec, RANDOM);
	        byte[] encrypted = cipher.doFinal(plainText.getBytes());
			return encrypted;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public byte[] decrypt(byte[] cipherText) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			// AlgorithmParameterSpec paramSpec=new PBEParameterSpec(salt,iterationCount);
	        AlgorithmParameterSpec paramSpec=new IvParameterSpec(IV);
			cipher.init(Cipher.DECRYPT_MODE, key, paramSpec, RANDOM);
	        byte[] encrypted = cipher.doFinal(cipherText);
			return encrypted;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public String getInfoFromDb(String dbData) {
		return new String( decrypt(Hex.decode(dbData) ));
	}

	public String putInfoInDb(String text) {
		return Hex.toHexString(encrypt(text));
	}
}
