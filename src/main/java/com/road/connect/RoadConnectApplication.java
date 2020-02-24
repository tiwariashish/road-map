package com.road.connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoadConnectApplication {

//	@Autowired
//	private ResourceController resourceController;

	public static void main(String[] args) {

		SpringApplication.run(RoadConnectApplication.class, args);

//		ResourceController fileEngine = (ResourceController) context.getBean("resourceController");
//		fileEngine.readInputFile();
	}

}
