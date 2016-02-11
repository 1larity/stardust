/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.utils;

import java.math.BigInteger;

public interface DataOp {
	
	String getLandTypeData(String returnString);
	String getLandscapeData(String returnString);
	String getNPCData(String returnString);
	void getServerTime();
	String login(String accountName,String password);
	String getInventory(int characterId,boolean init);
	String getVendorInventory(int characterId);
	void deleteItem(int uid);
	String getNews();
	String getFreeUser(String ac);
	String createUser(String ac, String mpw1, String memail);
	String get3dChar(int characterName);
	void getSolarSystem(float sysx, float sysy, float sysz);
	String getChars(int userid);
	void dockship(int uid);
	void undockship(int uid);
	void updateDBShip();
	void getLocalPlayers(int x, int y, int z, boolean init);
	void getShipDefs();
	void getAvatarDefs();
	void getRandomHumanFemaleName();
	void getRandomHumanMaleName();
	String makeAvatar(String selectedAvatar, String selectedRace,
			String selectedSex, String firstName, String surName, int muser);
	String postChat(String text, int i, int muser);
	void getChatDefs();
	String hitOpponent(BigInteger get_uid, int i,int myID);
	void start();
	void setRunning(boolean b);
	void newRequest(String string);
	void getAssetDefs();
	String sell(BigInteger itemID, BigInteger charID, BigInteger vendorID);
	String purchase(BigInteger itemID, BigInteger charID, BigInteger vendorID);
	String inventoryOp(BigInteger itemID, int operation, BigInteger bigInteger);
	void getMissionDefs();
	//void getMyMissions(Integer characterID);
	void acceptMission(BigInteger uid);
	void getMissionLog(int completed);
	void getchat(int selectedChannel, boolean reset, boolean serverMessages);
	String logout(int muser);
	void changeShip(int uid, int inventoryid);
	void getShipFitting(int uid);
	
}
