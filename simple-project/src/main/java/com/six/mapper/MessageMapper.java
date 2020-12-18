package com.six.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.six.entities.Message;

@Mapper
public interface MessageMapper {
	List<Message> getMessages(@Param("id1")String id1, @Param("id2")String id2);
	
	void sendMessage(Message message);
}
