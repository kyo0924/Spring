package com.kh.spring.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.model.vo.Member;


@Repository
public class MemberDaoImpl implements MemberDao {

	@Autowired
	private SqlSessionTemplate session;

	@Override
	public int insertMember(Member m) {
		// TODO Auto-generated method stub
		return session.insert("member.insertMember", m);
	}

	@Override
	public Member selectOne(Member m) {
		// TODO Auto-generated method stub
		return session.selectOne("member.selectone", m);
	}
	
	@Override
	public int update(Member m) {
		// TODO Auto-generated method stub
		return session.update("member.update", m);
	}
	
	
}
