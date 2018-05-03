package com.hust.platform.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hust.platform.model.Knowledge;

@Mapper
public interface KnowledgeDAO {
	String tableName = " knowledge ";
	String insertField = " name,summary,content,securityLevel,permission,status ";
	String selectField = " id, "+ insertField;
	
	/**
	 * 添加知识点
	 * @param knowledge
	 * @return
	 */
	@Insert("insert into " + tableName + " ( "+ insertField + " ) " + "values(name=#{name},summary=#{summary},content=#{content},"
			+ "securityLevel=#{securityLevel},permission=#{permission},status=#{status})")
	int addKnowledge(Knowledge knowledge);
	
	/**
	 * 根据name查找知识点
	 * @param name
	 * @return
	 */
	@Select("select "+ selectField + " from "+ tableName + "where name = #{name}")
	Knowledge selectByName(String name);
	
	/**
	 * 根据id查找知识点
	 * @param id
	 * @return
	 */
	@Select("select "+ selectField + " from "+ tableName + "where id = #{id}")
	Knowledge selectById(int id);
	
	int updateContents(@Param("summary") String summary,@Param("content") String content );
	
}
