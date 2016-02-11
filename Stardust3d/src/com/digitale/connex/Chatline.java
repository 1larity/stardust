package com.digitale.connex;

import java.sql.Timestamp;



public class Chatline
{
	private String name;
	private String message;
	private String channelname;
	private Timestamp timestamp;
	
	
	public Chatline(
			String name, 
			String message,
			String channelname,
	Timestamp timestamp) {
		this.setName(name);
		this.setMessage(message);
		this.setChannelname(channelname);
		this.setTimestamp(timestamp);
			}
	


	public Chatline() {
		// TODO Auto-generated constructor stub
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getChannelname() {
		return channelname;
	}



	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}



	public Timestamp getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	
}