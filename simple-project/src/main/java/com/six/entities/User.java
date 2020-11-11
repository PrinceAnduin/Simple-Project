package com.six.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String email;
	private String password;
}
