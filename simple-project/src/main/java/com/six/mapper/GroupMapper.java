package com.six.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.six.entities.GroupMessage;
import com.six.entities.UserGroup;

public interface GroupMapper {
	
	void addGroup(UserGroup userGroup);
	
	void addUserToGroup( int groupId, @Param("userId") String userId);
	
	void sendMessage(GroupMessage groupMessage);
	
	void eiteAll(@Param("id")int id);
	
	void cancelEite(int groupId, @Param("userId") String userId);
	
	int count();
	
	List<UserGroup> getAll();
	
	List<UserGroup> getGroups(@Param("userId")String Id);
	
	List<UserGroup> searchGroups(@Param("groupName")String groupName);
	
	List<GroupMessage> getMessages(@Param("id")int id);
}
