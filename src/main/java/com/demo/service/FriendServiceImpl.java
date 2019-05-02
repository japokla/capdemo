package com.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo.dao.FriendRepository;
import com.demo.model.Blocklist;
import com.demo.model.Person;
import com.demo.model.Subscriber;

@Service
public class FriendServiceImpl implements FriendService {
	
	@Autowired
	private FriendRepository friendRepository; 
	
	@Override
	@Transactional
	public Person addFriend(String user, String friend) {
		String blockedUser = friendRepository.findBlockedUsersByUser(user, friend);
		String blockedUser2 = friendRepository.findBlockedUsersByUser(friend, user);
		if(!StringUtils.isEmpty(blockedUser) || !StringUtils.isEmpty(blockedUser2)) {
			return null;
		}
		Person p = new Person();
		p.setUser(user);
		p.setFriend(friend);
		return friendRepository.addFriend(p);
	}

	@Override
	public List<String> getFriends(String user) {
		List<String> friends = friendRepository.getFriends(user);
		List<String> blockedUsers = friendRepository.findAllBlockedUsersByUser(user);
		List<String> filteredFriends = friends.stream()
                .filter(e -> (blockedUsers.stream()
                        .filter(d -> d.equals(e))
                        .count())<1)
                        .collect(Collectors.toList());
		return filteredFriends;
	}

	@Override
	public List<String> getCommonFriends(String user1, String user2) {
		return friendRepository.getCommonFriends(user1, user2);
	}

	@Override
	@Transactional
	public Blocklist blockUser(String requestor, String target) {
		String blockedUser = friendRepository.findBlockedUsersByUser(requestor, target);
		String blockedUser2 = friendRepository.findBlockedUsersByUser(target, requestor);
		if(!StringUtils.isEmpty(blockedUser) || !StringUtils.isEmpty(blockedUser2) ) {
			return null;
		}
		Blocklist b = new Blocklist();
		b.setUser(requestor);
		b.setBlockedUser(target);
		return friendRepository.blockUser(b);
	}

	@Override
	public List<String> getAllSubscriber(String sender, String text) {
		List<String> listOfFriends = friendRepository.getAllSubscriber(sender);
		List<String> blockedUsers = friendRepository.findAllBlockedUsersByUser(sender);
		List<String> recipients = listOfFriends.stream()
                .filter(e -> (blockedUsers.stream()
                        .filter(d -> d.equals(e))
                        .count())<1)
                        .collect(Collectors.toList());
		return recipients;
	}

	@Override
	@Transactional
	public Subscriber subscribe(String requestor, String target) {
		String blockedUser = friendRepository.findBlockedUsersByUser(target, requestor);
		if(!StringUtils.isEmpty(blockedUser)) {
			return null;
		}
		
		Subscriber s = new Subscriber();
		s.setFollower(requestor);
		s.setUser(target);
		return friendRepository.subscribe(s);
	}
	
	
	
}