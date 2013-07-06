package com.upreader.aws;

import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.util.Date;

import org.jets3t.service.CloudFrontService;
import org.jets3t.service.CloudFrontServiceException;
import org.jets3t.service.utils.ServiceUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.upreader.UpreaderApplication;
import com.upreader.util.Configurable;
import com.upreader.util.ConfigurationProperties;

public class AmazonService implements Configurable {
	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	private final String DISTRIBUTION_DOMAIN = "content.upreader.com";
	private final long EXPIRE_TIME = 1000 * 5 * 60; 	// expire in 5 mins
	
	private final UpreaderApplication application;
	private AWSCredentials credentials;
	private String bucketName;
	private String cfAccessKey;
	byte[] derPrivateKey;
	
	public AmazonService(UpreaderApplication application) {
		this.application = application;
		this.application.getConfigurator().addConfigurable(this);
	}

	@Override
	public void configure(ConfigurationProperties props) {
		this.bucketName = props.getProperty("S3ContentBucketName");
		String accessKey = props.getProperty("AWSAccessKeyId");
		String secretKey = props.getProperty("AWSSecretKey");
		this.cfAccessKey = props.getProperty("CFAccessKey");
		if (accessKey != null && secretKey != null)
			this.credentials = new BasicAWSCredentials(accessKey, secretKey);
		
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream("cf.der");
			derPrivateKey = ServiceUtils.readInputStreamToBytes(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Upload uploadFile(String fileName, InputStream is, ProgressListener listener) {
		TransferManager tx = new TransferManager(credentials);
		ObjectMetadata meta = new ObjectMetadata();
		PutObjectRequest request = new PutObjectRequest(bucketName, fileName,is, meta).withProgressListener(listener);

		return tx.upload(request);
	}

	public String getSignedURL(String s3ObjectKey) {
		String policyResourcePath = "http://" + DISTRIBUTION_DOMAIN + "/" + s3ObjectKey;

		try {
			String signedUrlCanned = CloudFrontService.signUrlCanned(policyResourcePath, cfAccessKey, derPrivateKey,
					new Date(System.currentTimeMillis() + EXPIRE_TIME));
			return signedUrlCanned;
		} catch (CloudFrontServiceException e) {
			e.printStackTrace();
		}

		return null;
	}
}
