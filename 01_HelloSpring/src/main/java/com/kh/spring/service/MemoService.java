package com.kh.spring.service;

import java.util.List;
import java.util.Map;

public interface MemoService {

	List<Map<String,String>> selectMemo();
	
	int insertMemo(Map<String, String> map);
	
	
}
