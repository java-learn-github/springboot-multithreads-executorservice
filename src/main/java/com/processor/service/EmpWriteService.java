package com.processor.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmpWriteService implements Callable<String> {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private Map<String, Object> map;

	public EmpWriteService(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public String call() throws Exception {
		Path path = null;
		logger.info("INSIDE THREADS");		
		System.err.println(Thread.currentThread().getId());
		String write = "";
		String id = String.valueOf(map.get("id"));
		String name = String.valueOf(map.get("first_name"));
		String email = String.valueOf(map.get("email"));
		write = id + " = " + name + " " + email + "\n";
		String dynamicPath = "C:\\Shoaib\\dev\\logs\\logger\\" + id + "_" + name + System.currentTimeMillis() + ".txt";
		path = Files.write(Paths.get(dynamicPath), write.getBytes());

		return path.toString();
	}

}
