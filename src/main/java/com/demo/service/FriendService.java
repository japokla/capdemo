package com.demo.service;

import java.util.List;

import com.demo.model.Blocklist;
import com.demo.model.Person;
import com.demo.model.Subscriber;


public interface FriendService {
	
	public Person addFriend(String user, String friend);

	public List<String> getFriends(String user);

	public List<String> getCommonFriends(String user1, String user2);

	public Blocklist blockUser(String requestor, String target);

	public List<String> getAllSubscriber(String sender, String text);

	public Subscriber subscribe(String requestor, String target);



}