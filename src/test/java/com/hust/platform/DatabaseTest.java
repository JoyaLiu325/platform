package com.hust.platform;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hust.platform.dao.FrameDAO;
import com.hust.platform.model.FrameNode;
import com.hust.platform.service.FrameService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseTest {
	@Autowired
	FrameService frameService;

	@Test
	public void TestDatebase() {
		List<FrameNode> list = frameService.getSubnodes();
		for (FrameNode frameNode : list) {
			System.out.println(frameNode.getId());
		}
	}
}
