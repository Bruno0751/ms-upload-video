package com.dev.ms_upload_video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class MsUploadVideoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsUploadVideoApplication.class, args);
	}

}
