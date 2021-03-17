package org.highmed.dsf.bpe.crypto;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyStore;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class SecretKeyConsumerImpl implements KeyConsumer, InitializingBean
{
	private static final Logger logger = LoggerFactory.getLogger(SecretKeyConsumerImpl.class);

	private final Path keystoreFile;
	private final char[] keystorePassword;

	private KeyStore keystore;

	public SecretKeyConsumerImpl(Path keystoreFile, char[] keystorePassword)
	{
		this.keystoreFile = keystoreFile;
		this.keystorePassword = keystorePassword;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		Objects.requireNonNull(keystoreFile, "keystoreFile");
		Objects.requireNonNull(keystorePassword, "keystorePassword");

		try
		{
			keystore = KeyStoreIo.readJceks(keystoreFile, keystorePassword);
		}
		catch (FileNotFoundException e)
		{
			logger.warn("Could not find keystore at {}, creating an empty keystore", keystoreFile);

			keystore = KeyStoreHelper.createPkcs12(keystorePassword);
			KeyStoreIo.write(keystore, keystoreFile, keystorePassword);
		}
	}

	@Override
	public void store(String alias, Key key)
	{
		try
		{
			keystore.setKeyEntry(alias, key, keystorePassword, null);
			KeyStoreIo.write(keystore, keystoreFile, keystorePassword);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Could not store secret key", e);
		}
	}
}
