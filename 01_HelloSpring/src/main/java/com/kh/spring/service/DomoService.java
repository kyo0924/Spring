package com.kh.spring.service;

import java.util.List;

import com.kh.spring.model.vo.Dev;
import com.kh.spring.model.vo.Member;

public interface DomoService {
	
	void print();
	
	int insert(Dev dev);
	
	
	//리스트 받아오기
	List<Dev> selectList();

	
	
	
}
