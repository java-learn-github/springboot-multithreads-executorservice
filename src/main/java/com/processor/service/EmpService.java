package com.processor.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.processor.repository.EmpRepo;

@Component
public class EmpService {

	@Autowired
	EmpRepo empRepo;

	public List<Map<String, Object>> getAllData() throws InterruptedException {

		return empRepo.getAllData();

	}

	public String write(List<Map<String, Object>> allData) throws IOException {
		Path path = null;
		int i = 1;
		for (Map<String, Object> map : allData) {
			System.err.println(i++ + "--" + Thread.currentThread().getId());
			String write = "";
			String id = String.valueOf(map.get("id"));
			String name = String.valueOf(map.get("first_name"));
			String email = String.valueOf(map.get("email"));
			write = id + " = " + name + " " + email + "\n";
			String dynamicPath = "C:\\Shoaib\\dev\\logs\\logger\\" + id + "_" + name + System.currentTimeMillis()
					+ ".txt";
			path = Files.write(Paths.get(dynamicPath), write.getBytes());
		}

		return path.toString();

	}

}
