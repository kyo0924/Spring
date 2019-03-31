package com.kh.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.dao.MemberDao;
import com.kh.spring.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService{

	
	//의존성 주입해줄 객체가 필요하다
	@Autowired
	private MemberDao dao;

	@Override
	public int insertMember(Member m) {
		// TODO Auto-generated method stub
		return dao.insertMember(m);
	}

	@Override
	public Member selectOne(Member m) {
		// TODO Auto-generated method stub
		return dao.selectOne(m);
	}

	
	//회원수정
	@Override
	public int update(Member m) {
		// TODO Auto-generated method stub
		return dao.update(m);
	}
	

	
	
	
	
}
