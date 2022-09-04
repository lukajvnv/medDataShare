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
