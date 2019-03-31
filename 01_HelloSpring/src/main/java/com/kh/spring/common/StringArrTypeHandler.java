package com.kh.spring.common;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

// T는 특정타입을 고정
public class StringArrTypeHandler implements TypeHandler<String[]>{

	@Override
	public void setParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
		//parameter가 있을땐
		if(parameter!=null) {
			//join : 배열을 ,로 구분해서 집어넣는것
			ps.setString(i, String.join(",", parameter));
		}
		else {
			ps.setString(i, "");
		}
	}

	@Override
	public String[] getResult(ResultSet rs, String columnName) throws SQLException {
		//columnName = 지금 호출한게 들어옴 여기선 devLang
		String temp=rs.getString(columnName);
		String[] values=temp.split(",");
		return values;
	}

	@Override
	public String[] getResult(ResultSet rs, int columnIndex) throws SQLException {
		String temp=rs.getString(columnIndex);
		return temp.split(",");
	}

	@Override
	public String[] getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String temp=cs.getString(columnIndex);
		return temp.split(",");		
	}
	
	
	

}
