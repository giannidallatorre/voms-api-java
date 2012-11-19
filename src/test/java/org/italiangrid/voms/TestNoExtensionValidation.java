package org.italiangrid.voms;

import static org.junit.Assert.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateParsingException;
import java.util.List;

import junit.framework.Assert;

import org.italiangrid.voms.ac.VOMSACValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.emi.security.authn.x509.impl.PEMCredential;
import eu.emi.security.authn.x509.proxy.ProxyCertificate;
import eu.emi.security.authn.x509.proxy.ProxyCertificateOptions;
import eu.emi.security.authn.x509.proxy.ProxyGenerator;
import eu.emi.security.authn.x509.proxy.ProxyType;

public class TestNoExtensionValidation implements Fixture{
	
	PEMCredential cred;
	
	@Before
	public void setUp() throws Exception {
		cred = new PEMCredential(holderKey, holderCert, keyPassword.toCharArray());
	}

	@After
	public void tearDown() throws Exception {
		cred = null;
	}

	@Test
	public void testNoExtensionValidation() throws InvalidKeyException, CertificateParsingException, SignatureException, NoSuchAlgorithmException {
		ProxyCertificateOptions options = new ProxyCertificateOptions(cred.getCertificateChain());
		options.setType(ProxyType.LEGACY);
		
		ProxyCertificate proxy = ProxyGenerator.generate(options, cred.getKey());
		
		VOMSACValidator validator = Utils.getVOMSValidator();
		List<VOMSAttribute> attrs = validator.validate(proxy.getCertificateChain());
		
		Assert.assertNotNull(attrs);
		Assert.assertTrue(attrs.isEmpty());
		
	}

}
