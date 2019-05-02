package com.demo.dao;

import java.util.List;

import com.demo.model.Blocklist;
import com.demo.model.Person;
import com.demo.model.Subscriber;

public interface FriendRepository {

	public Person addFriend(Person p);

	public List<String> getFriends(String user);

	public List<String> getCommonFriends(String user1, String user2);

	public Blocklist blockUser(Blocklist b);

	public String findBlockedUsersByUser(String user, String friend);

	public List<String> findAllBlockedUsersByUser(String sender);

	public Subscriber subscribe(Subscriber s);

	public List<String> getAllSubscriber(String sender);

	public String getSpecificFriend(String user, String friend);


}