package com.demo.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.service.FriendService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TEst {
	
	@Autowired
	private FriendService friendService;
	
	@Test
	public void testAddNewFriend() {
		
		assertEquals(1L, 1L);
	}
	

}
