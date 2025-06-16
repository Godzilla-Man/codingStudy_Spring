package kr.or.iei.mybatis.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.or.iei.mybatis.model.dto.MybatisMember;

@Mapper
public interface MybatisDao {

	ArrayList<MybatisMember> selectIfTest(MybatisMember m);

	ArrayList<MybatisMember> selectAllMemberList();

	ArrayList<MybatisMember> selectForTest(String[] members);

	ArrayList<MybatisMember> selectChooseTest(HashMap<String, String> map);

	ArrayList<MybatisMember> selectDynamicTest1(MybatisMember member);

	ArrayList<MybatisMember> selectDynamicTest2(String sFlag1);

	ArrayList<MybatisMember> selectDynamicTest3(MybatisMember member);
	
}
