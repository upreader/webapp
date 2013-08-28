package com.upreader.aws;

import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClient;
import com.amazonaws.services.simpleemail.model.*;
import com.upreader.dto.AmazonS3FileDetails;
import com.upreader.dto.AmazonS3FileDetailsBuilder;
import com.upreader.helper.AmazonS3Helper;
import com.upreader.util.JsonWriter;
import org.jets3t.service.CloudFrontService;
import org.jets3t.service.CloudFrontServiceException;
import org.jets3t.service.utils.ServiceUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
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
    private final String BUCKETS_ZONE_URL = "s3-eu-west-1.amazonaws.com";
    private final long EXPIRE_TIME = 1000 * 5 * 60;    // expire in 5 mins

    private final UpreaderApplication application;
    private AWSCredentials credentials;
    private String public_bucketName;
    private String private_bucketName;
    private String cfAccessKey;
    private JsonWriter jsonWriter;
    byte[] derPrivateKey;
    private AmazonS3 s3;

    public AmazonService(UpreaderApplication application, JsonWriter jsonWriter) {
        this.application = application;
        this.application.getConfigurator().addConfigurable(this);
        this.jsonWriter = jsonWriter;
    }

    @Override
    public void configure(ConfigurationProperties props) {
        this.public_bucketName  = props.getProperty("S3PublicContentBucketName");
        this.private_bucketName = props.getProperty("S3PrivateContentBucketName");
        String accessKey = props.getProperty("AWSAccessKeyId");
        String secretKey = props.getProperty("AWSSecretKey");
        this.cfAccessKey = props.getProperty("CFAccessKey");
        if (accessKey != null && secretKey != null)
            this.credentials = new BasicAWSCredentials(accessKey, secretKey);
            this.s3 = new AmazonS3Client(this.credentials);
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("cf.der");
            derPrivateKey = ServiceUtils.readInputStreamToBytes(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Upload file to Amazon S3 bucket
     *
     * @param isPublic
     * @param key
     * @param is
     * @param listener
     * @return
     */
    public Upload uploadFile(Boolean isPublic, String key, InputStream is, ProgressListener listener) {
        TransferManager tx = new TransferManager(credentials);
        ObjectMetadata meta = new ObjectMetadata();
        PutObjectRequest request = new PutObjectRequest(isPublic ? public_bucketName : private_bucketName,
                                                        key,
                                                        is,
                                                        meta).withProgressListener(listener);

        return tx.upload(request);
    }

    /**
     * Get CloudFront signed URL that is valid for the duration set in EXPIRE_TIME
     *
     * @param s3ObjectKey
     * @return
     */
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

    public String listFolderContents(String folderPath, boolean isPublic){
        ArrayList<AmazonS3FileDetails> result = new ArrayList<AmazonS3FileDetails>();

        ObjectListing objectListing = s3.listObjects(new ListObjectsRequest().
                                                     withBucketName(isPublic ? this.getPublicBucketName() : this.getPublicBucketName()).
                                                     withPrefix(folderPath).
                                                     withDelimiter("/"));

        for(S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                final String s3ObjectKey = objectSummary.getKey();
                final String bucket = isPublic ? this.getPublicBucketName() : this.getPublicBucketName();
                String resourcePath = "http://" + BUCKETS_ZONE_URL + "/" + bucket + "/" + s3ObjectKey;

                if (AmazonS3Helper.isImmediateDescendant(folderPath, s3ObjectKey)) {
                    result.add(new AmazonS3FileDetailsBuilder().
                                        withKey(s3ObjectKey).
                                        withLink(isPublic ? resourcePath : getSignedURL(s3ObjectKey)).
                                        withFileName(AmazonS3Helper.trimPath(folderPath, s3ObjectKey)).
                                        withSize(Long.toString(objectSummary.getSize())).
                                        withFolder(folderPath).
                                        withExt(AmazonS3Helper.getExt(folderPath, s3ObjectKey)).
                                        withIsImageFlag(AmazonS3Helper.isImage(folderPath, s3ObjectKey)).
                                        withLastModified(objectSummary.getLastModified()).
                                        build());
                }
        }
        return jsonWriter.write(result);
    }

    public void sendEmail(String from, String to, String subject, String htmlBody) {
        // Construct an object to contain the recipient address.
        Destination destination = new Destination().withToAddresses(new String[]{to});

        // Create the subject and body of the message.
        Content _subject = new Content().withData(subject);
        Content _htmlBody = new Content().withData(htmlBody);
        Body _body = new Body().withHtml(_htmlBody);

        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(_subject).withBody(_body);

        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(from).withDestination(destination).withMessage(message);

        try {
            // Instantiate an Amazon Async SES client, which will make the service call with the supplied AWS credentials.
            AmazonSimpleEmailServiceAsyncClient client = new AmazonSimpleEmailServiceAsyncClient(credentials);
            client.sendEmail(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getPublicBucketName() {
        return public_bucketName;
    }

    public String getPrivateBucketName() {
        return private_bucketName;
    }
}
