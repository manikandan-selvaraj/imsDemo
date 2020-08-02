package com.cognizant.ims.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AmazonClientServiceImpl implements AmazonClientService {
	private String awsS3Bucket;
	private AmazonS3 amazonS3;
	private static final Logger logger = LoggerFactory.getLogger(AmazonClientServiceImpl.class);

	@Autowired
	public AmazonClientServiceImpl(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider,
			String awsS3Bucket) {
		this.amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(awsCredentialsProvider)
				.withRegion(awsRegion.getName()).build();
		this.awsS3Bucket = awsS3Bucket;
	}

	@Async
	public void uploadFileToS3Bucket(MultipartFile multipartFile) {
		String fileName = multipartFile.getOriginalFilename();

		try (FileOutputStream fos = new FileOutputStream(new File(fileName))) {
			fos.write(multipartFile.getBytes());

			PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3Bucket, fileName, new File(fileName));

			putObjectRequest.withCannedAcl(CannedAccessControlList.Private);
			this.amazonS3.putObject(putObjectRequest);
		} catch (IOException | AmazonServiceException ex) {
			logger.error("error  {}  occurred while uploading {} ", ex.getMessage(), fileName);
		}
	}

	@Async
	public void deleteFileFromS3Bucket(String fileName) {
		try {
			amazonS3.deleteObject(new DeleteObjectRequest(awsS3Bucket, fileName));
		} catch (Exception ex) {
			logger.error("error  {}  occurred while removing {} ", ex.getMessage(), fileName);
		}
	}
}
