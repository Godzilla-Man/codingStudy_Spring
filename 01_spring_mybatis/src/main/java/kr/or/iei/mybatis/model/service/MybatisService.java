package kr.or.iei.mybatis.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.iei.mybatis.model.dao.MybatisDao;
import kr.or.iei.mybatis.model.dto.MybatisMember;

@Service
public class MybatisService {

	@Autowired
	private MybatisDao dao;

	public ArrayList<MybatisMember> selectIfTest(MybatisMember m) {
				
		return dao.selectIfTest(m);
	}

	public ArrayList<MybatisMember> selectAllMemberList() {
		
		return dao.selectAllMemberList();
	}

	public ArrayList<MybatisMember> selectForTest(String[] members) {
		
		return dao.selectForTest(members);
	}

	public ArrayList<MybatisMember> selectChoosetest(String select, String keyword) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("select", select);
		map.put("keyword", keyword);
				
		return dao.selectChooseTest(map);
	}

	public ArrayList<MybatisMember> selectDynamicTest1(MybatisMember member) {
		
		return dao.selectDynamicTest1(member);
	}

	public ArrayList<MybatisMember> selectDynamicTest2(String sFlag1) {
		
		return dao.selectDynamicTest2(sFlag1);
	}

	public ArrayList<MybatisMember> selectDynamicTest3(MybatisMember member) {
				
		return dao.selectDynamicTest3(member);
	}
}
