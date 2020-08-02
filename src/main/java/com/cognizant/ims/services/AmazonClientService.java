package com.cognizant.ims.services;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonClientService {
	void uploadFileToS3Bucket(MultipartFile multipartFile);

    void deleteFileFromS3Bucket(String fileName);
}
