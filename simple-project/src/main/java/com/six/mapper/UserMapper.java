package com.six.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.six.entities.User;
@Mapper
public interface UserMapper {
	List<User> getAll();
	
	User getOne(String id);
	
	User searchOne(String id);
 
	void insert(User user);
 
	void update(User user);
 
	void delete(@Param("id")String id);
	
	void setVisiable(@Param("id")String id);
}