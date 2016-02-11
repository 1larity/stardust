/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * Avatardef Class, stores Avatar graphic information, mainly used in char creator
 */
package com.digitale.connex;

import java.math.BigInteger;



public class MissionDef
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

	
	
	public MissionDef(
			BigInteger uid,
			String title,
			String text,
			String type,
			String conditions,
			String rewards,
			BigInteger giver,
			BigInteger handin,
			BigInteger prereq
			) {
		this.setUid(uid);
		this.setTitle(title);
		this.setText(text);
		this.setType(type);
		this.setConditions(conditions);
		this.setRewards(rewards);
		this.setGiver(giver);
		this.setHandin(handin);
		this.setPrereq(prereq);
		
	
	}
	


	public MissionDef() {
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return the uid
	 */
	public BigInteger getUid() {
		return uid;
	}



	/**
	 * @param uid the uid to set
	 */
	public void setUid(BigInteger uid) {
		this.uid = uid;
	}



	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}



	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}



	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}



	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}



	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}



	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}



	/**
	 * @return the conditions
	 */
	public String getConditions() {
		return conditions;
	}



	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}



	/**
	 * @return the rewards
	 */
	public String getRewards() {
		return rewards;
	}



	/**
	 * @param rewards the rewards to set
	 */
	public void setRewards(String rewards) {
		this.rewards = rewards;
	}



	/**
	 * @return the giver
	 */
	public BigInteger getGiver() {
		return giver;
	}



	/**
	 * @param giver the giver to set
	 */
	public void setGiver(BigInteger giver) {
		this.giver = giver;
	}



	/**
	 * @return the handin
	 */
	public BigInteger getHandin() {
		return handin;
	}



	/**
	 * @param handin the handin to set
	 */
	public void setHandin(BigInteger handin) {
		this.handin = handin;
	}



	/**
	 * @return the prereq
	 */
	public BigInteger getPrereq() {
		return prereq;
	}



	/**
	 * @param prereq the prereq to set
	 */
	public void setPrereq(BigInteger prereq) {
		this.prereq = prereq;
	}


}