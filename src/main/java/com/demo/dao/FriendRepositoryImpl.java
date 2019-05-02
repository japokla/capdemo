package com.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.demo.model.Blocklist;
import com.demo.model.Person;
import com.demo.model.Subscriber;


@Repository
public class FriendRepositoryImpl implements FriendRepository {
	
	@PersistenceContext
    private EntityManager entityManager;


	@Override
	public Person addFriend(Person p) {
		entityManager.persist(p);
		entityManager.flush();
		return p;
	}

	@Override
	public List<String> getFriends(String user) {
		try {
			String qry = "SELECT p.friend FROM Person p WHERE p.user =?1 ";
			TypedQuery<String> result = entityManager.createQuery(qry, String.class);
			result.setParameter(1, user);
			return result.getResultList();
		}catch (NoResultException e) {
			return new ArrayList<String>();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCommonFriends(String user1, String user2) {
		String query = "SELECT FRIEND FROM PERSON WHERE USER =:user1 " + 
						"INTERSECT " + 
						"SELECT FRIEND FROM PERSON WHERE USER =:user2";
		Query q = entityManager.createNativeQuery(query);
		q.setParameter("user1", user1);
		q.setParameter("user2", user2);
		List<String> list = q.getResultList();
		return list;
	}
	
	@Override
	public List<String> getAllSubscriber(String sender) {
		String query = "SELECT FRIEND FROM PERSON WHERE USER =:user1 " + 
						"UNION " + 
						"SELECT USER FROM SUBSCRIBER WHERE FOLLOWER =:user1";
		Query q = entityManager.createNativeQuery(query);
		q.setParameter("user1", sender);
		List<String> list = q.getResultList();
		return list;
	}
	

	@Override
	public Blocklist blockUser(Blocklist b) {
		entityManager.persist(b);
		entityManager.flush();
		return b;
	}

	@Override
	public String findBlockedUsersByUser(String user, String friend) {
		try {
			String qry = "SELECT b.blockedUser FROM Blocklist b WHERE b.user =?1 AND b.blockedUser =?2 ";
			TypedQuery<String> result = entityManager.createQuery(qry, String.class);
			result.setParameter(1, user);
			result.setParameter(2, friend);
			return result.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<String> findAllBlockedUsersByUser(String sender) {
		try {
			String qry = "SELECT b.blockedUser FROM Blocklist b WHERE b.user =?1 ";
			TypedQuery<String> result = entityManager.createQuery(qry, String.class);
			result.setParameter(1, sender);
			return result.getResultList();
		}catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Subscriber subscribe(Subscriber s) {
		entityManager.persist(s);
		entityManager.flush();
		return s;
	}

}