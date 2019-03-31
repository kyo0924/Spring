package com.kh.spring.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.model.vo.Dev;
import com.kh.spring.model.vo.Member;


//저장공간은 respository라고 써주면됨 Dao XXX
@Repository
public class DomoDaoImpl implements DomoDao {

	
	@Autowired
	private SqlSessionTemplate session;
	
	@Override
	public void print() {
		System.out.println("DAO");
	}

	//domo4 insert관련
	@Override
	public int insert(Dev dev) {
		return session.insert("dev.insert", dev);
	}

	
	//selectList
	@Override
	public List<Dev> selectList() {
		// TODO Auto-generated method stub
		return session.selectList("dev.selectList");
	}



	

}
