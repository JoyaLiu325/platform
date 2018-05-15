package com.hust.platform.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.platform.model.FrameNode;
import com.hust.platform.service.FrameService;
import com.hust.platform.util.PlatformUtil;

import ch.qos.logback.core.net.SyslogOutputStream;

@Controller
public class FrameController {
	 private static final Logger log = LoggerFactory.getLogger(FrameController.class);
	 
	@Autowired
	FrameService frameService;
	
	/**
	 * 整体体系框架
	 * @param model
	 * @return
	 */
	@RequestMapping(path = {"/frame","/"},method= {RequestMethod.GET})
	public String frame(Model model) {
		return "frame";
	}
	
	/**
	 * 异步加载框架
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(path = {"/frame/asynchro"},method= {RequestMethod.GET})
	public String asynchroFrame(@RequestParam("pid") int pid,Model model) {
		return "asynchroFrame";
	}
	
	/**
	 * 返回添加知识点页面
	 * @param model
	 * @return
	 */
	@RequestMapping(path = {"/frame/add"},method= {RequestMethod.GET})
	public String addFrame(Model model) {
		return "addFrame";
	}
	
	/**
	 * 返回删除知识点页面
	 * @param model
	 * @return
	 */
	@RequestMapping(path = {"/frame/delete"},method= {RequestMethod.GET})
	public String deleteFrame(Model model) {
		return "deleteFrame";
	}
	
	/**
	 * 异步加载三层节点
	 * @param pid
	 * @param pnodeName
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/frame/loadmore",method= {RequestMethod.GET})
	@ResponseBody
	public String loadmore(@RequestParam(value = "pid",required = false) Integer pid,
						   @RequestParam(value = "pnodeName",required = false) String pnodeName,
						   Model model) {
		if(pnodeName!=null) {
			System.out.println(pnodeName);
			List<FrameNode> nodeList = frameService.getSubnodes(pnodeName);
			return PlatformUtil.getJsonString(999, nodeList);
		}
		else {
			List<FrameNode> nodeList = frameService.getSubnodes();	
			return PlatformUtil.getJsonString(999, nodeList);
		}
		}
	
	/**
	 * 加载所有节点   	
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/frame/loadAll",method= {RequestMethod.GET})
	@ResponseBody
	public String loadAll(Model model) {
		List<FrameNode> nodeList = frameService.getAllSubnodes();
		return PlatformUtil.getJsonString(999, nodeList);
		}
	
	@RequestMapping(path = "/frame/getNames",method= {RequestMethod.GET})
	@ResponseBody
	public String getNames(Model model) {
		List<String> nodeList = frameService.getNodeName();
		return PlatformUtil.getJsonString1(999, nodeList);
		}
	
	/**
	 * 添加节点
	 * @param nodeName
	 * @param pnodeName
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/frame/addNode",method= {RequestMethod.POST})
	@ResponseBody
	public String addNode(@RequestParam("_pnodeName[]") List<String> pNodelist,
			@RequestParam("_nodeName[]") List<String> Nodelist, 
			Model model) {
		for(String str : pNodelist) {
			System.out.println(str);
		}
		return PlatformUtil.getJsonString(999);
/*		try {
			List<FrameNode> nodeList = new ArrayList<>();
			for(FrameNode node : list) {
				System.out.println("nodeName:"+node.getNodeName());
				System.out.println("pnodeName:"+node.getPnodeName());
//				环路检测
				String result = frameService.loopDetection(node.getPnodeName(), node.getNodeName());
				if(result!=null)
					return PlatformUtil.getJsonString(443,result);
				int pid = frameService.selectByName(node.getPnodeName()).getId();
				FrameNode _node = new FrameNode();
				_node.setNodeName(node.getNodeName());
				_node.setPid(pid);
				_node.setPnodeName(node.getPnodeName());
				nodeList.add(_node);
				log.info("添加节点"+" "+"id:"+node.getId()+" "+"nodeName:"+node.getNodeName()+" "+"pnodeName:"+node.getPnodeName());
			}
			frameService.addNodes(nodeList);
			return  PlatformUtil.getJsonString(999,"添加成功");
		} catch (Exception e) {
			return PlatformUtil.getJsonString(444,"添加失败");
		}*/
}
	
	/**
	 * 删除节点
	 * @param nodeName
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/frame/deleteNode",method= {RequestMethod.GET})
	@ResponseBody
	public String addNode(@RequestParam("_nodeName") String nodeName,Model model) {
		if(frameService.deleteNode(nodeName)>0) 
			return PlatformUtil.getJsonString(999,"删除成功");
		else
			return PlatformUtil.getJsonString(444,"删除失败");
	}
	
	
}
