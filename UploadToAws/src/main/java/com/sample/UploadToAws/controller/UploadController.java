package com.sample.UploadToAws.controller;

import java.io.File;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

@RestController
public class UploadController {
	
	AWSCredentials credentials = new BasicAWSCredentials(
			  "AKIA24WQTFXCL6QNKQBR", 
			  "SBerAuG5x0wBUEkT1NPCF1luFiPP6VuWvb4yVrZu"
			);
	
	AmazonS3 s3client = AmazonS3ClientBuilder
			  .standard()
			  .withCredentials(new AWSStaticCredentialsProvider(credentials))
			  .withRegion(Regions.AP_SOUTH_1)
			  .build();
	

	@GetMapping("/hello")
	public String checkController()
	{
		return "Hello there..!";
	}
	
	@GetMapping("/listBuckets")
	public String uploadFile()
	{
		
		
		StringBuilder bucketNames = new StringBuilder().append("List of Buckets in AWS: ");
		
		List<Bucket> buckets = s3client.listBuckets();
		for(Bucket bucket : buckets) {
		    System.out.println(bucket.getName());
		    bucketNames.append(bucket+" ,\n");
		}
				
		return bucketNames.toString();
	}
	
	@GetMapping("/uploadToAWS")
	public String uploadToAws()
	{
		
		try {
		
		  String bucketName = "my-prog-files";
		  
		  if(!s3client.doesBucketExist(bucketName)) {
		  System.out.println("Bucket name is not available.Try again with a different Bucket name.");
		  
		  return  "bucket is not available"; 
		  }
		  
		  s3client.putObject(
				  bucketName, 
				  "Document/hello.txt", 
				  new File("/home/mariraja/hello.txt")
				);
		  
		  //** For creating a new bucket use the below line
		  // s3client.createBucket(bucketName);
		 
		
		return "success";
		}
		catch (Exception e)
		{
			
			
			e.printStackTrace();
			return "issue on uploading file";
		}
	}
	
	
	
}
