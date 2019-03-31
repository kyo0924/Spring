package com.kh.spring.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.dao.DomoDao;
import com.kh.spring.model.vo.Dev;
import com.kh.spring.model.vo.Member;

@Service
public class DomoServiceImpl implements DomoService {
	//service의 역할이기 때문에 annotation으로 service지정 
	
	@Autowired
	private DomoDao dao;

	@Override
	public void print() {
		System.out.println("Service");
		dao.print();
	}

	@Override
	public int insert(Dev dev) {
		return dao.insert(dev);
	}

	
	//트랜잭션 처리를 이곳에서 한다
	@Override
	public List<Dev> selectList() {
		
		return dao.selectList();
	}



	
	
	
	

}
