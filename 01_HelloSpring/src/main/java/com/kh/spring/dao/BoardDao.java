package com.kh.spring.dao;

import java.util.List;

import com.kh.spring.model.vo.Attachment;
import com.kh.spring.model.vo.Board;

public interface BoardDao {
	
	int selectCount();
	List<Board> selectList(int cPage, int numPerPage);
	
	//파일업로드
	int insertBoard(Board b);
	int insertAttachment (Attachment a);
	

	//게시판 상세페이지
	Board selectBoard(int boardNo);
	List<Attachment> selectAttachment(int boardNo);
	
	
	
	
}
