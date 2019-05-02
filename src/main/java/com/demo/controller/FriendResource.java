package com.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Blocklist;
import com.demo.model.Person;
import com.demo.model.Subscriber;
import com.demo.service.FriendService;

@RestController
@RequestMapping(value= "/friend")
public class FriendResource {

	@Autowired
	FriendService friendService;

	@GetMapping(value= "/getFriends/{user}")
	public ResponseEntity<List<String>> getFriends(@PathVariable(name= "user") String user) {
		List<String> friends = friendService.getFriends(user);
		if(CollectionUtils.isEmpty(friends)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(friendService.getFriends(user), HttpStatus.OK);
	}
	
	@GetMapping(value= "/addFriend/{user}/{friend}")
	public ResponseEntity<Person> addFriend(@PathVariable(name= "user") String user, @PathVariable(name= "friend") String friend) {
		Person p = friendService.addFriend(user, friend);
		if(ObjectUtils.isEmpty(p)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(p, HttpStatus.OK);
	}
	
	@GetMapping(value= "/getCommonFriends/{user1}/{user2}")
	public ResponseEntity<List<String>> getCommonFriends(@PathVariable(name= "user1") String user1, @PathVariable(name= "user2") String user2) {
		List<String> friends = friendService.getCommonFriends(user1, user2);
		if(CollectionUtils.isEmpty(friends)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(friends, HttpStatus.OK);
	}
	
	@GetMapping(value= "/blockUser/{requestor}/{target}")
	public ResponseEntity<Blocklist> blockUser(@PathVariable(name= "requestor") String requestor, @PathVariable(name= "target") String target) {
		Blocklist b = friendService.blockUser(requestor, target);
		if(ObjectUtils.isEmpty(b)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(b, HttpStatus.OK);
	}

	@GetMapping(value= "/getAllSubscriber/{sender}/{text}")
	public ResponseEntity<List<String>> getAllSubscriber(@PathVariable(name= "sender") String sender, @PathVariable(name= "text") String text) {
		List<String> recipients = friendService.getAllSubscriber(sender, text);
		return new ResponseEntity<>(recipients, HttpStatus.OK);
	}
	
	@GetMapping(value= "/subscribe/{requestor}/{target}")
	public ResponseEntity<Subscriber> subscribe(@PathVariable(name= "requestor") String requestor, @PathVariable(name= "target") String target) {
		Subscriber s = friendService.subscribe(requestor, target);
		if(ObjectUtils.isEmpty(s)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(s, HttpStatus.OK);
	}
	
}