package com.hust.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.platform.dao.KnowledgeDAO;
import com.hust.platform.model.Knowledge;

@Service
public class KnowledgeService {
	
	@Autowired
	KnowledgeDAO knowledgeDAO;
	
	public Knowledge selectByName(String name) {
		return knowledgeDAO.selectByName(name);
	}
}
