package com.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
		String addedFriend = friendRepository.getSpecificFriend(user, friend);
		if(!StringUtils.isEmpty(blockedUser) || !StringUtils.isEmpty(blockedUser2) || !StringUtils.isEmpty(addedFriend)) {
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
		List<String> commonFriends = friendRepository.getCommonFriends(user1, user2);
		List<String> blockedUser1 = friendRepository.findAllBlockedUsersByUser(user1);
		List<String> blockedUser2 = friendRepository.findAllBlockedUsersByUser(user2);
		List<String> filteredCommon = commonFriends.stream()
                .filter(e -> (blockedUser1.stream()
                        .filter(d -> d.equals(e))
                        .count())<1)
                        .collect(Collectors.toList());
		filteredCommon = commonFriends.stream()
                .filter(e -> (blockedUser2.stream()
                        .filter(d -> d.equals(e))
                        .count())<1)
                        .collect(Collectors.toList());
		return filteredCommon;
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