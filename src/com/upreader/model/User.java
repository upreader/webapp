package com.upreader.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(name = "UK_USER_EMAIL", columnNames = { "email" }),
		@UniqueConstraint(name = "UK_USER_USERNAME", columnNames = { "username" }) })
@NamedQueries({
		@NamedQuery(name = User.NQ_FIND_BY_USERNAME, query = "SELECT u from User u where u.username = :username"),
		@NamedQuery(name = User.NQ_FIND_BY_EMAIL, query = "SELECT u from User u where u.email = :email"),
		@NamedQuery(name = User.NQ_FIND_ALL, query = "SELECT u from User u") })
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role;

	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public static final String NQ_FIND_BY_USERNAME = "User_findByUsername";
	public static final String NQ_FIND_BY_EMAIL = "User_findByEmail";
	public static final String NQ_FIND_ALL = "User_findAll";
}
