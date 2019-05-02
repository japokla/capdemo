package com.demo.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.demo.model.Person;
import com.demo.service.FriendService;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FriendResourceTest {
	@Autowired
	private FriendService friendService;
	
	@Test
	public void testAaddNewFriend() {
		Person p = friendService.addFriend("santosjervis@gmail.com", "juandelacruz@gmail.com");
		assertNotNull(friendService.getFriends(p.getUser()));
	}
	
	@Test
	public void testBsubscribe() {
		friendService.subscribe("santosjervis@gmail.com", "pedro@gmail.com");
		assertNotNull(friendService.getAllSubscriber("santosjervis@gmail.com", "Test").size() > 1);
	}
	
	@Test
	public void testCblockFriend() {
		friendService.blockUser("santosjervis@gmail.com", "pedro@gmail.com");
		friendService.blockUser("santosjervis@gmail.com", "juandelacruz@gmail.com");
		assertTrue(CollectionUtils.isEmpty(friendService.getAllSubscriber("santosjervis@gmail.com", "Test")));
	}
	
	@Test
	public void testDgetFriends() {
		friendService.blockUser("santosjervis@gmail.com", "juandelacruz@gmail.com");
		assertTrue(CollectionUtils.isEmpty(friendService.getFriends("santosjervis@gmail.com")));
	}
	
	@Test
	public void testEgetCommonFriends() {
		friendService.addFriend("santosjervis@gmail.com", "capgem@gmail.com");
		friendService.addFriend("juandelacruz@gmail.com", "capgem@gmail.com");
		assertTrue(!CollectionUtils.isEmpty(friendService.getCommonFriends("santosjervis@gmail.com", "juandelacruz@gmail.com")));
	}
	

}
