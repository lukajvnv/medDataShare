package rs.ac.uns.ftn.medDataShare.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class KeyStoreHelper {

	private KeyStore keyStore;
	
	public KeyStoreHelper() {
		try {
			Security.addProvider(new BouncyCastleProvider());
			keyStore = KeyStore.getInstance("JKS", "SUN");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public Certificate readCertificate(String alias) {
		try {
			if(keyStore.isCertificateEntry(alias)) {
				Certificate cert = keyStore.getCertificate(alias);
				return cert;
			}
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return null;
	}
    
//    /**
//	 * Ucitava sertifikate is KS fajla
//	 */
//    public ArrayList<Certificate> readCertificates(String keyStoreFile, String keyStorePass) {
//			ArrayList<Certificate> listaC = new ArrayList<Certificate>();
//			String[] lista = new String[] {"eureka", "zuul", "requesthandler"};
//			//ucitavamo podatke
//			for(String a : lista) {
//				Certificate c = readCertificate(keyStoreFile, keyStorePass, a);
//				if(c != null) {
//					listaC.add(c);
//				}
//			}
//			return listaC;
//		
//	}
	
	public void loadKeyStore(String fileName, char[] password) {
		try {
			if(fileName != null) {
				keyStore.load(new FileInputStream(fileName), password);
			} else {
				//Ako je cilj kreirati novi KeyStore poziva se i dalje load, pri cemu je prvi parametar null
				keyStore.load(null, password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveKeyStore(String fileName, char[] password) {
		try {
			keyStore.store(new FileOutputStream(fileName), password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
									//koji ovde tacno ide kljuc, sa cim mora biti kompatibilan(onim u subject data???)
//	public void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate) {
//		try {
//	
//			keyStore.setKeyEntry(alias, privateKey, password, new Certificate[] {certificate});
//		} catch (KeyStoreException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void write(String alias, PrivateKey key, char[] password, Certificate cer) {
		try {
			keyStore.setKeyEntry(alias, key, password, null);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}

	public PrivateKey readPrivateKey(String alias, String pass) {
		try {
			if(keyStore.isKeyEntry(alias)) {
				PrivateKey pk = (PrivateKey) keyStore.getKey(alias, pass.toCharArray());
				return pk;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public byte[] encrypt(byte[] input, PublicKey key) {
		try {
			Cipher rsaCipherEnc = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
			rsaCipherEnc.init(Cipher.ENCRYPT_MODE, key);
			
			byte[] cipherText = rsaCipherEnc.doFinal(input);
			return cipherText;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public byte[] decrypt(byte[] cipherText, PrivateKey key) {
		try {			
			Cipher rsaCipherDec = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
			rsaCipherDec.init(Cipher.DECRYPT_MODE, key);
			
			byte[] plainText = rsaCipherDec.doFinal(cipherText);
			return plainText;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
