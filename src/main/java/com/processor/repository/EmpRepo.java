package com.processor.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpRepo {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> getAllData() {

		String sql = "select * from mock_data";
		List<Map<String, Object>> queryForList = new ArrayList<>();
		try {
			queryForList = jdbcTemplate.queryForList(sql);

		} catch (Exception ex) {
			logger.error("ERROR {}", ex.getMessage());
		}

		return queryForList;
	}

}