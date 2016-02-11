/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * Avatardef Class, stores Avatar graphic information, mainly used in char creator
 */
package com.digitale.connex;

import java.math.BigInteger;

import com.digitale.mygdxgame.Stardust3d;



public class MissionLog
{
	
	private BigInteger uid;
	private String title;
	private String text;
	private String type;
	private String conditions;
	private String rewards;
	private BigInteger giver;
	private BigInteger handin;
	private BigInteger prereq;
	private String completetext;
	private String firstname;
	private String surname;
	private String faction;
	private int x;
	private int y;
	private int z;
	private String sysname;
	private int inprogress;
	
	
	public MissionLog(
			 BigInteger uid,
				 String title,
				 String text,
				 String type,
				 String conditions,
				 String rewards,
				 BigInteger giver,
				 BigInteger handin,
				 BigInteger prereq,
				 String completetext,
				 String firstname,
				 String surname,
				 String faction,
				 int x,
				 int y,
				 int z,
				 String sysname,
				 int inprogress
			) {
		this.setUid(uid);
		this.setTitle (title);
		this.setText (text);
		this.setType (type);
		this.setConditions (conditions);
		this.setRewards (rewards);
		this.setGiver (giver);
		this.setHandin (handin);
		this.setPrereq (prereq);
		this.setCompletetext (completetext);
		this.setFirstname (firstname);
		this.setSurname (surname);
		this.setFaction (faction);
		this.setX (x);
		this.setY (y);
		this.setZ (z);
		this.setSysname (sysname);
		this.setInprogress(inprogress);
		
	
	}
	public boolean initConditions(){
		String[] RowData = this.conditions.split("%");
		boolean initComplete = false;
		if (RowData[1].equals("kill")){
			
		}else if (RowData[1].equals("collect")){
			
		}else{
			if (Stardust3d.DEBUG)
				System.out.println("mission init parser error"); 
			return initComplete;
		}
		initComplete=true;
		return initComplete;
	}
	
public boolean CheckComplete(){
	//
	
	
	boolean completed = false;
	return completed;
}

	public MissionLog() {
		// TODO Auto-generated constructor stub
	}



	public BigInteger getUid() {
		return uid;
	}



	public void setUid(BigInteger uid) {
		this.uid = uid;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}


	public String getConditions() {
		return conditions;
	}



	public void setConditions(String conditions) {
		this.conditions = conditions;
	}



	public String getRewards() {
		return rewards;
	}



	public void setRewards(String rewards) {
		this.rewards = rewards;
	}



	public BigInteger getGiver() {
		return giver;
	}



	public void setGiver(BigInteger giver) {
		this.giver = giver;
	}



	public BigInteger getHandin() {
		return handin;
	}



	public void setHandin(BigInteger handin) {
		this.handin = handin;
	}



	public BigInteger getPrereq() {
		return prereq;
	}



	public void setPrereq(BigInteger prereq) {
		this.prereq = prereq;
	}



	public String getCompletetext() {
		return completetext;
	}



	public void setCompletetext(String completetext) {
		this.completetext = completetext;
	}



	public String getFirstname() {
		return firstname;
	}



	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}



	public String getSurname() {
		return surname;
	}



	public void setSurname(String surname) {
		this.surname = surname;
	}



	public String getFaction() {
		return faction;
	}



	public void setFaction(String faction) {
		this.faction = faction;
	}



	public int getX() {
		return x;
	}



	public void setX(int x) {
		this.x = x;
	}



	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}



	public int getZ() {
		return z;
	}



	public void setZ(int z) {
		this.z = z;
	}



	public String getSysname() {
		return sysname;
	}



	public void setSysname(String sysname) {
		this.sysname = sysname;
	}



	/**
	 * @return the inprogress
	 */
	public int getInprogress() {
		return inprogress;
	}



	/**
	 * @param inprogress the inprogress to set
	 */
	public void setInprogress(int inprogress) {
		this.inprogress = inprogress;
	}



	
}