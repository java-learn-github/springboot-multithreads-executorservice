package com.processor.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.processor.service.EmpService;
import com.processor.service.EmpWriteService;

@RestController
public class EmpController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EmpService empService;

	@GetMapping("/")
	public String get() throws InterruptedException, IOException, ExecutionException {
		logger.info("get() entered");
		String path = null;
		List<Map<String, Object>> allData = empService.getAllData();
		long startTime = System.currentTimeMillis();

		
		int lcount = 0;
		int numThreads = Runtime.getRuntime().availableProcessors();
		logger.info("numThreads = {} ", numThreads);
		logger.info("allData.size() --> {}", allData.size());
		ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
		List<Future<String>> futureTaskList = new ArrayList<>();
		while (lcount < allData.size()) {
			EmpWriteService empWriteService = new EmpWriteService(allData.get(lcount));
			Future<String> future = executorService.submit(empWriteService);
			futureTaskList.add(future);
			lcount++;
		}

		for (Future<String> futureTask : futureTaskList) {
			String namePath = futureTask.get();
			logger.info("futureTask :: {}", namePath);
		}

		executorService.shutdown();

//		path = empService.write(allData);

		
		long endTime = System.currentTimeMillis();
		logger.info("total time :: {}", endTime - startTime);
		logger.info("get() exit");
		return path;
	}

}
