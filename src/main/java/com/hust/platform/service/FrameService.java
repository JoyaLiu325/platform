package com.hust.platform.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.platform.dao.FrameDAO;
import com.hust.platform.model.FrameNode;

@Service
public class FrameService {
	@Autowired
	FrameDAO frameDAO;
//	考虑第一层使用pid获取，第二层使用pnodeName获取
//	默认获取三层数据
	public List<FrameNode> getSubnodes() {
		// 所有节点列表
		List<FrameNode> nodeList = new ArrayList<>();
		// 未访问的节点name
		Queue<String> unVisitedPnames = new LinkedList<>();
		List<FrameNode> tempList = null;
		
		int pid = 0;
		// 第一层子节点
		tempList = frameDAO.getSubnodesByPid(pid);
		if(tempList == null )
			return nodeList;
		nodeList.addAll(tempList);
		unVisitedPnames.addAll(frameDAO.getSubNameByPid(pid));
		 
		// 第二层子节点
		
		int size = unVisitedPnames.size();
		String pname  = "";
		for (int i = 0; i < size; i++) {
			pname = unVisitedPnames.poll();
			if ((tempList = frameDAO.getSubnodesByName(pname)) != null) {
				nodeList.addAll(tempList);
				unVisitedPnames.addAll(frameDAO.getSubNameByPname(pname));
			}
		}
		
		if(unVisitedPnames == null || unVisitedPnames.isEmpty()) return nodeList;
		
		// 第三层子节点
		for (int i = 0; i < unVisitedPnames.size(); i++) {
			pname = unVisitedPnames.poll();
			if ((tempList = frameDAO.getSubnodesByName(pname)) != null) {
				nodeList.addAll(tempList);
			}
		}
		
		return nodeList;
	}
	
//	 获取三层数据
	public List<FrameNode> getSubnodes(String pnodeName) {
		// 所有节点列表
		List<FrameNode> nodeList = new ArrayList<>();
		// 未访问的节点name
		Queue<String> unVisitedPnames = new LinkedList<>();
		List<FrameNode> tempList = null;
		
		// 第一层子节点
		
		tempList = frameDAO.getSubnodesByName(pnodeName);
		if(tempList == null )
			return nodeList;
		nodeList.addAll(tempList);
		unVisitedPnames.addAll(frameDAO.getSubNameByPname(pnodeName));
		 
		// 第二层子节点
		String pname = "";
		int size = unVisitedPnames.size();
		for (int i = 0; i < size; i++) {
			pname = unVisitedPnames.poll();
			if ((tempList = frameDAO.getSubnodesByName(pname)) != null) {
				nodeList.addAll(tempList);
				unVisitedPnames.addAll(frameDAO.getSubNameByPname(pname));
			}
		}
		
		if(unVisitedPnames == null || unVisitedPnames.isEmpty()) return nodeList;
		
		// 第三层子节点
		for (int i = 0; i < unVisitedPnames.size(); i++) {
			pname = unVisitedPnames.poll();
			if ((tempList = frameDAO.getSubnodesByName(pname)) != null) {
				nodeList.addAll(tempList);
			}
		}
		return nodeList;
	}
	
//	获取所有节点
	public List<FrameNode> getAllSubnodes(){
		// 所有节点列表
		List<FrameNode> nodeList = new ArrayList<>();
		// 未访问的节点name
		Queue<String> unVisitedPnames = new LinkedList<>();
		List<FrameNode> tempList = null;
		
		int pid = 0;
		// 第一层子节点
		tempList = frameDAO.getSubnodesByPid(pid);
		if(tempList == null )
			return nodeList;
		nodeList.addAll(tempList);
		unVisitedPnames.addAll(frameDAO.getSubNameByPid(pid));
		 
		// 第二层子节点
		
		int size = 0;
		String pname  = "";
		while((size = unVisitedPnames.size()) > 0) {
			for(int i = 0;i<size;i++) {
				pname = unVisitedPnames.poll();
				if ((tempList = frameDAO.getSubnodesByName(pname)) != null) {
					nodeList.addAll(tempList);
					unVisitedPnames.addAll(frameDAO.getSubNameByPname(pname));
				}
			}
		}
		
		return nodeList;
	}
	
//	添加节点
	public int addNode(FrameNode node) {
		return frameDAO.addNode(node);
	}
// 删除节点
	public int deleteNode(String nodeName ){
		return frameDAO.deleteNode(nodeName);
	}
	
	public FrameNode selectByName(String nodeName) {
		return frameDAO.selectByName(nodeName);
	}
	

}
