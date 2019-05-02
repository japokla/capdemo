# capdemo

Here I have created a demo project which is created in a Spring Boot with embbeded Tomcat Server.

Initially I have created 3 Models Person which is considered as the user of Mini Facebook App, 
Blocklist Model which will serves as a storage for all User who made a block list of specific User and 
a Subscriber Model which will also serves as a storage for all the User who will follow a specific user.

With the application architecture I have created the 3 Tier Architecture which are the Presentation Layer which we considered the REST Api Layer,
The Business Layer which is the Service Layer where all the business logics or implementation of the code is written and lastly the Data Access Layer.

We have created 6 REST API on FriendResource;
1. An API which the user can use to get all friends of a specific email.
2. An API where the user can add new friend using email for both field. 
   One is "user" which represent the requestor and the "friend" which represent the user who will be added as a new friend.
3. An API get common friends for 2 specific User.
4. An API which will serves as a facility to blocker specific user.
5. An API to subscribe to specific user, so a user can get notified with latest feed of the user without adding the user.
6. An API to get all subscriber this compose of Friends and Followed user and excluding all the blocked users.


And also, I have created a simple Junit to test the created services. 


