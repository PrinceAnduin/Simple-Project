package com.six.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.six.entities.User;
@Mapper
public interface UserMapper {
	List<User> getAll();
	
	User getOne(String id);
 
	void insert(User user);
 
	void update(User user);
 
	void delete(String id);
}