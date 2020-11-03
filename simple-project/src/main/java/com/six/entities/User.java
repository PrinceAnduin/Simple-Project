package com.six.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {
	
	private String id;
	private String name;
	private String password;
	
}
