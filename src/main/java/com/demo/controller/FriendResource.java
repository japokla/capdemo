/*
  * Modified by: santosj
  * Last updated: May 2, 2019 10:15:30 AM
  */
package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;

import com.demo.model.*;
import com.demo.service.FriendService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/friend")
public class FriendResource {

	@Autowired
	FriendService friendService;

	@RequestMapping(value = "/getFriends/{user}", method = RequestMethod.GET)
	@ApiOperation(value = "Get All Friends of User", response = Object.class)
	public ResponseEntity<List<String>> getFriends(@PathVariable(name = "user") String user) {
		List<String> friends = friendService.getFriends(user);
		if (CollectionUtils.isEmpty(friends)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(friendService.getFriends(user), HttpStatus.OK);
	}

	@RequestMapping(value = "/addFriend/{user}/{friend}", method = RequestMethod.POST)
	@ApiOperation(value = "Add new friend", response = Object.class)
	public ResponseEntity<Person> addFriend(@PathVariable(name = "user") String user,
			@PathVariable(name = "friend") String friend) {
		Person p = friendService.addFriend(user, friend);
		if (ObjectUtils.isEmpty(p)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCommonFriends/{user1}/{user2}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Common Friends of two emails", response = Object.class)
	public ResponseEntity<List<String>> getCommonFriends(@PathVariable(name = "user1") String user1,
			@PathVariable(name = "user2") String user2) {
		List<String> friends = friendService.getCommonFriends(user1, user2);
		if (CollectionUtils.isEmpty(friends)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(friends, HttpStatus.OK);
	}

	@RequestMapping(value = "/blockUser/{requestor}/{target}", method = RequestMethod.POST)
	@ApiOperation(value = "Block specific user", response = Object.class)
	public ResponseEntity<Blocklist> blockUser(@PathVariable(name = "requestor") String requestor,
			@PathVariable(name = "target") String target) {
		Blocklist b = friendService.blockUser(requestor, target);
		if (ObjectUtils.isEmpty(b)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(b, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllSubscriber/{sender}/{text}", method = RequestMethod.GET)
	@ApiOperation(value = "Get All Subscribers including Friends and Followed Users", response = Object.class)
	public ResponseEntity<List<String>> getAllSubscriber(@PathVariable(name = "sender") String sender,
			@PathVariable(name = "text") String text) {
		List<String> recipients = friendService.getAllSubscriber(sender, text);
		return new ResponseEntity<>(recipients, HttpStatus.OK);
	}

	@RequestMapping(value = "/subscribe/{requestor}/{target}", method = RequestMethod.POST)
	@ApiOperation(value = "Follow specific user", response = Object.class)
	public ResponseEntity<Subscriber> subscribe(@PathVariable(name = "requestor") String requestor,
			@PathVariable(name = "target") String target) {
		Subscriber s = friendService.subscribe(requestor, target);
		if (ObjectUtils.isEmpty(s)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(s, HttpStatus.OK);
	}

}