package com.kh.spring.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.common.exception.BoardException;
import com.kh.spring.dao.BoardDao;
import com.kh.spring.model.vo.Attachment;
import com.kh.spring.model.vo.Board;

@Service
public class BoardServiceImpl implements BoardService {

	private Logger logger=LoggerFactory.getLogger(BoardServiceImpl.class);
	
	
	@Autowired
	private BoardDao dao;

	@Override
	public int selectCount() {
		return dao.selectCount();
	}

	@Override
	public List<Board> selectList(int cPage, int numPerPage) {
		return dao.selectList(cPage, numPerPage);
	}

	
	//파일 업로드 - boardException은 따로 만들어줌
	@Override
	/*@Transactional*/
	public int insertBoard(Board b, List<Attachment> attachmentList) throws BoardException {
		int result=0;
		/*try {*/
			logger.warn("입력되기전값 : "+b.getBoardNo());
			result=dao.insertBoard(b);
			if(result==0) throw new BoardException();
			logger.warn("새로입력된값 : "+b.getBoardNo());
			if(attachmentList.size()>0) {
				
				//1개 이상이면
				for(Attachment a : attachmentList) {
					a.setBoardNo(b.getBoardNo());
					result=dao.insertAttachment(a);
	//				if(result==0) throw new BoardException();
				}
			}
		/*}
		catch(Exception e) {
			throw e;
			e.printStackTrace();
		}*/
		return 0;
	}

	
	//게시판 상세페이지
	@Override
	public Board selectBoard(int boardNo) {
		// TODO Auto-generated method stub
		return dao.selectBoard(boardNo);
	}

	@Override
	public List<Attachment> selectAttachment(int boardNo) {
		// TODO Auto-generated method stub
		return dao.selectAttachment(boardNo);
	}

	
	
	
	
	

}
