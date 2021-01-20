package rs.ac.uns.ftn.medDataShare.config;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import rs.ac.uns.ftn.medDataShare.util.KeyStoreHelper;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.security.PrivateKey;
import java.util.Properties;

@Configuration
public class SecurityConfig {

	@Autowired
	private Environment environment;

	@Bean
	public SecretKey key() throws Exception {
		String CRYPTO_FILEPATH = environment.getProperty("CRYPTO_FILEPATH");
		String CUSTOM_PROPERTIES_PATH = environment.getProperty("CUSTOM_PROPERTIES_PATH");
		String KEYSTORE_PASS = environment.getProperty("KEYSTORE_PASS");
		String ALIAS = environment.getProperty("ALIAS");
		String PASS = environment.getProperty("PASS");

		KeyStoreHelper helper = new KeyStoreHelper();
		helper.loadKeyStore(CRYPTO_FILEPATH, KEYSTORE_PASS.toCharArray());
		PrivateKey key = helper.readPrivateKey(ALIAS, PASS);

		Properties p = new Properties();
		p.load(new FileInputStream(CUSTOM_PROPERTIES_PATH));
		String encodedEncryptedKey = p.getProperty("symKey");

		byte[] decodedEncryptedKey = Hex.decode(encodedEncryptedKey);
		byte[] decodedDecryptedKey = helper.decrypt(decodedEncryptedKey, key);

		return new SecretKeySpec(decodedDecryptedKey, 0, decodedDecryptedKey.length, "AES");
	}

}
