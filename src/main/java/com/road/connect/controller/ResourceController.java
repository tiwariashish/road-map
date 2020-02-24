package com.road.connect.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.road.connect.exception.BusinessException;
import com.road.connect.model.EntityModel;
import com.road.connect.util.Graph;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
public class ResourceController {

	@Value("${file.input}")
	private String inputFilePath;

	static Graph graph = null;

	public Graph readInputFile() {
		Graph graph = new Graph();
		String line = "";
		BufferedReader br = null;
		try {
			File inputDataFile = new File(inputFilePath);
			br = new BufferedReader(new FileReader(inputDataFile));
			while ((line = br.readLine()) != null && !line.isEmpty()) {
				String[] inputDataAttributes = line.split(",");
				if (inputDataAttributes == null
						|| inputDataAttributes.length >= 1 && StringUtils.isEmpty(inputDataAttributes[0])) {
					break;
				}
				if (inputDataAttributes.length > 0) {

					graph.addVertex(inputDataAttributes[0].trim(), inputDataAttributes[1].trim());
				}
			}
			return graph;
		} catch (FileNotFoundException e) {
			throw new BusinessException(e.getMessage());
		} catch (IOException e) {
			throw new BusinessException(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new BusinessException(e.getMessage());
				}
			}
		}

	}

	@ApiOperation(value = "return road connectivity", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved response"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("/connected")
	public String checkRoadMap(@RequestParam("origin") String origin, @RequestParam("destination") String destination) {
		EntityModel model = new EntityModel();
		model.setOrigin(origin);
		model.setDestination(destination);
		if (graph == null) {
			graph = readInputFile();
		}
		if (graph.findPath(origin, destination)) {
			return "Yes";
		}
		return "No";
	}

}
