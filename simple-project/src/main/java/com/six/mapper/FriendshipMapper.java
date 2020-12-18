package com.six.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.six.entities.User;

@Mapper
public interface FriendshipMapper {
	List<User> getFriends(@Param("id")String id);
	
	User getFriend(@Param("id1")String id1, @Param("id2")String id2);
	
	void deleteFriend(@Param("id1")String id1, @Param("id2")String id2);
	
	void addFriend(@Param("id1")String id1, @Param("id2")String id2);
}
