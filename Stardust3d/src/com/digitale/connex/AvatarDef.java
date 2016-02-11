/*
 * Copyright 2012 Richard Beech (digitale001@gmail.com)
 * Avatardef Class, stores Avatar graphic information, mainly used in char creator
 */
package com.digitale.connex;



public class AvatarDef
{
	
	private int uid;
	private String description;
	private String imagename;
	private String race;
	private String sex;

	
	
	public AvatarDef(
			int uid,
			String description,
			String imagename,
			String race,
			String sex
			) {
		this.setUid(uid);
		this.setDescription(description);
		this.setImagename(imagename);
		this.setRace(race);
		this.setSex(sex);
		
	
	}
	


	public AvatarDef() {
		// TODO Auto-generated constructor stub
	}



	public int getUid() {
		return uid;
	}



	public void setUid(int uid) {
		this.uid = uid;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getImagename() {
		return imagename;
	}



	public void setImagename(String imagename) {
		this.imagename = imagename;
	}



	public String getRace() {
		return race;
	}



	public void setRace(String race) {
		this.race = race;
	}



	public String getSex() {
		return sex;
	}



	public void setSex(String sex) {
		this.sex = sex;
	}

}