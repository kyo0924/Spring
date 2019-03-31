package com.kh.spring.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.model.vo.Attachment;
import com.kh.spring.model.vo.Board;

@Repository
public class BoardDaoImpl implements BoardDao {

	@Autowired
	private SqlSessionTemplate session;
	
	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return session.selectOne("board.selectCount");
	}

	@Override
	public List<Board> selectList(int cPage, int numPerPage) {
		// TODO Auto-generated method stub
		return session.selectList("board.selectList", null, new RowBounds((cPage-1)*numPerPage, numPerPage));
	}

	
	
	//파일 업로드
	@Override
	public int insertBoard(Board b) {
		// TODO Auto-generated method stub
		return session.insert("board.insertBoard",b);
	}
	@Override
	public int insertAttachment(Attachment a) {
		// TODO Auto-generated method stub
		return session.insert("board.insertAttachment",a);
	}

	
	//게시판 상세페이지
	@Override
	public Board selectBoard(int boardNo) {
		// TODO Auto-generated method stub
		return session.selectOne("board.selectBoard", boardNo);
	}
	@Override
	public List<Attachment> selectAttachment(int boardNo) {
		// TODO Auto-generated method stub
		return session.selectList("board.selectAttachment", boardNo);
	}

	

	
	
	
	
	
	
}
