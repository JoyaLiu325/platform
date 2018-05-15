package com.hust.platform.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hust.platform.model.FrameNode;
import com.hust.platform.model.Knowledge;
import com.hust.platform.service.FrameService;
import com.hust.platform.service.KnowledgeService;

@Controller
public class KnowledgeController {
	private static final Logger log = LoggerFactory.getLogger(KnowledgeController.class);
	
	@Autowired
	KnowledgeService knowledgeService;
	@Autowired
	FrameService frameService;
	
/*	@RequestMapping(path= "/knowledge",method= {RequestMethod.GET})
	public String knowledge() {
		return "detail";
	}*/
	
	@RequestMapping(path="/knowledge",method= {RequestMethod.GET})
	public String knowledgeDetail(@RequestParam("_nodeName") String nodeName,Model model) {
		FrameNode frameNode = frameService.selectByName(nodeName);
		//获取所有父节点 子节点
		List<String> nodeList = frameService.getSubNameByPname(nodeName);
		List<String> pnodeList = frameService.getPnameBySubName(nodeName);
		model.addAttribute("nodeList",nodeList);
		model.addAttribute("pnodeList",pnodeList);
		model.addAttribute("frameNode",frameNode);
		return "detail";
	}
}
