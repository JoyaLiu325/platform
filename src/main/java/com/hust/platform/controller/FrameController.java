package com.hust.platform.controller;

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

@Controller
public class FrameController {
	 private static final Logger log = LoggerFactory.getLogger(FrameController.class);
	 
	@Autowired
	FrameService frameService;
	
	@RequestMapping(path = "/frame",method= {RequestMethod.GET})
	public String frame(@RequestParam("pid") int pid,Model model) {
		return "frame";
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
	
	@RequestMapping(path = "/frame/test",method= {RequestMethod.GET})
	public String frame() {
		
		log.info("访问frame框架");
		return "frame";
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
	public String addNode(@RequestParam("nodeName") String nodeName,
						  @RequestParam("pnodeName") String pnodeName,
						  	Model model) {
		int pid = frameService.selectByName(pnodeName).getId();
		FrameNode node = new FrameNode();
		node.setNodeName(nodeName);
		node.setPid(pid);
		node.setPnodeName(pnodeName);
		if(frameService.addNode(node)>0) {
			return  PlatformUtil.getJsonString(999,"添加成功");
		}
		else 
			return PlatformUtil.getJsonString(444,"添加失败");
		
		}
	
	/**
	 * 删除节点
	 * @param nodeName
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/frame/deleteNode",method= {RequestMethod.GET})
	@ResponseBody
	public String addNode(@RequestParam("nodeName") String nodeName,Model model) {
		if(frameService.deleteNode(nodeName)>0) 
			return PlatformUtil.getJsonString(999,"删除成功");
		else
			return PlatformUtil.getJsonString(444,"删除失败");
	}
}
