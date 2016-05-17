package com.c09.cinpockema.entities;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Session {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;  
	
	@Column(nullable=false, unique=true, length=36)
	private String token;

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)   
    @JoinColumn(name="user_id")
	private User user;

	private long createTime;
	
	public Session() {
		this.createTime = System.currentTimeMillis();
	}
	
	public long getCreateTime() {
		return createTime;
	}


	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}
	
	@JsonBackReference
	public boolean isNotExpired() {
//		long createTime = session.getCreateTime();
//		long nowTime = System.currentTimeMillis();
//		return (nowTime - createTime) < 30 * 60;
		return true;
	}
}
