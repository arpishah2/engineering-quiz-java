package com.revinate.instagram;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.*;
/**
 * Your Instagram object will be instantiated and called as such:
 * Instagram instagram = new Instagram();
 * instagram.postMedia(userId,mediaId);
 * List<Integer> feed = instagram.getMediaFeed(userId);
 * instagram.follow(followerId,followedId);
 * instagram.unfollow(followerId,followedId);
 */

public class Instagram {

    HashMap<Integer, ArrayList<Integer>> follow;        //user_id of (follower, followed[])
    HashMap<Integer, ArrayList<Integer>> mediaCreation; //user_id, media_id[] - media created by user
    ArrayList<Integer> media;                           //to store media feed in order 
    Set<Integer> users;                                 //keep track of user ids
  
    /** Initialize your data structure here. */
    public Instagram() {
    	follow = new HashMap<Integer, ArrayList<Integer>>();
    	mediaCreation  = new HashMap<Integer, ArrayList<Integer>>();
    	media = new ArrayList<Integer>();
      users = new HashSet<Integer>();
    }
    

    /** Add a new media. */
    public void postMedia(int userId, int mediaId) {
 
      if(!users.contains(userId)) users.add(userId);  //check if user exists- otherwise add it to Users
		  media.add(mediaId);							                //put media in arraylist to keep track of latest event

    	if(!mediaCreation.containsKey(userId)){				  //if new userId
        mediaCreation.put(userId, new ArrayList<Integer>());
      }
      mediaCreation.get(userId).add(mediaId);
    	
      /*
    	if(!mediaCreation.containsKey(userId)){				  //if new userId
    		ArrayList<Integer> mediaInfo = new ArrayList<Integer>();
    		mediaInfo.add(mediaId);
    		mediaCreation.put(userId, mediaInfo);
    	}
    	else{                                           //if userID exists, then add mediaId in the value  
    		ArrayList<Integer> mediaInfo = new ArrayList<Integer>();
      	mediaInfo.add(mediaId);
        mediaInfo.addAll(mediaCreation.get(userId));
    		mediaCreation.put(userId,mediaInfo);        //overrides the old value
    	}  
      */
    }

    /** Retrieve the 10 most recent media ids in the user's media feed.
     * Each media must be posted by the user herself or by someone the user follows
     * Media must be ordered from most recent to least recent. */
    public List<Integer> getMediaFeed(int userId) {
    	
    	if(!users.contains(userId))
    		throw new IllegalArgumentException(userId+" does not exist");
    	
    	ArrayList<Integer> result = new ArrayList<Integer>();
    	ArrayList<Integer> userIds = new ArrayList<Integer>();  //stores all user id whose feeds are to be considered
      userIds.add(userId);                //add current user as well
      userIds.addAll(follow.get(userId)); //user ids of all the users followed by userId 
    	
    	Set<Integer> allMedia = new HashSet<Integer>();
    	for(Integer uid: userIds){ 						 //for all userid get all the media and put in Set so it is easy to retrieve
        ArrayList<Integer> medias = mediaCreation.get(uid);
        allMedia.addAll(medias);
      }
  
      //traverse list in reverse order
      for(int i=media.size()-1; i>=0; i--){
        int val = media.get(i);
        if(allMedia.contains(val)){
          result.add(val);         
          if(result.size()>10)  //get top 10 results
            break;
        }
      }
      return result;
    }

    /** A Follower follows a followed. Nothing happens if invalid */
    public void follow(int followerId, int followedId) {
    	if(!users.contains(followedId))
    		throw new IllegalArgumentException(followedId+" does not exist");
    	if(!users.contains(followerId))
    			users.add(followerId);
    	if(!follow.containsKey(followerId)){
        	ArrayList<Integer> val = new ArrayList<Integer>();
          val.add(followedId);
          follow.put(followerId,val);
      }
      else{
    		ArrayList<Integer> following = follow.get(followerId);
    		for(Integer i: following){				//check if already following
    			if(i == followedId)
    				throw new IllegalArgumentException(followerId+" is already following "+followedId);
    		}
    		following.add(followedId);				//otherwise add 
    		follow.put(followerId, following);
    	}		
    }

    /** A Follower unfollows a followed. Nothing happens if invalid */
    public void unfollow(int followerId, int followedId) {
    	if(!users.contains(followerId))
    		throw new IllegalArgumentException(followerId +" does not exist");
    	if(!users.contains(followedId))
    		throw new IllegalArgumentException(followedId +" does not exist");
    	if(!follow.containsKey(followerId))
    		throw new IllegalArgumentException(followerId+" was not being followed !!");
    	ArrayList<Integer> following = follow.get(followerId);
      Integer rem = new Integer(followedId);
      following.remove(rem);		  
    }
}


