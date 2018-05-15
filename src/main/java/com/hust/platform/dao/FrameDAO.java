package com.hust.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hust.platform.model.FrameNode;

@Mapper
public interface FrameDAO {
	String tableName = " frame ";
	String insertField = " node_name,pid,pnode_name ";
	String selectField = " id, "+ insertField;
	
	/**
	 * 根据名字查找一个节点
	 * @param nodeName
	 * @return
	 */
	@Select({"select "+ selectField + " from " + tableName + "where node_name=#{nodeName} limit 1"})
	FrameNode selectByName(String nodeName);
	
	/**
	 * 根据id查找节点
	 * @param id
	 * @return
	 */
	@Select({"select "+ selectField + " from " + tableName + " where id=#{id} "})
	FrameNode selectById(int id);
	
	/**
	 * 添加多个知识点
	 * @param frame
	 * @return 成功
	 */
	void addNodes(List<FrameNode> list);
	
	/**
	 * 查询某一节点的儿子节点，可多次动态查询使用Ajax，提高每次查询效率
	 * @param pid
	 * @return 子节点列表
	 */
	@Select({"select "+ selectField + " from " + tableName + " where pid=#{pid} "})
	List<FrameNode> getSubnodesByPid(int pid);
	
	/**
	 * 查询某一节点的儿子节点，可多次动态查询使用Ajax，提高每次查询效率
	 * @param pid
	 * @return 子节点列表
	 */
	@Select({"select "+ selectField + " from " + tableName + " where pnode_name=#{pnodeName} "})
	List<FrameNode> getSubnodesByName(String pnodeName);
	
	@Select({"select node_name from " + tableName + " where pid=#{pid}"})
	List<String> getSubNameByPid(int pid);
	
	/**
	 * 根据父节点名字查找所有子节点
	 * @param pnodeName
	 * @return
	 */
	@Select({"select node_name from " + tableName + " where pnode_name=#{pnodeName}"})
	List<String> getSubNameByPname(String pnodeName);
	
	/**
	 * 根据子节点获取父节点名字
	 * @param nodeName
	 * @return
	 */
	@Select({"select pnode_name from " + tableName + " where node_name=#{nodeName}"})
	List<String> getPnameBySubName(String nodeName);
	
	@Select({"select distinct node_name from " + tableName })
	List<String> getNodeName();
	
	/**
	 * 删除/恢复节点，status为0删除，status为1存在
	 * @param status
	 * @param id
	 * @return
	 */
	@Update({"update " + tableName + " set status = #{status}  where id=#{id} "} )
	int updateStatus(int status,int id);

	@Delete({"delete from "+ tableName + " where node_name=#{nodeName}"})
	int deleteNode(String nodeName);
}
