/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.NoRouteToHostException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.badlogic.gdx.math.Vector3;
import com.digitale.connex.Actor;
import com.digitale.connex.AssetDef;
import com.digitale.connex.AvatarDef;
import com.digitale.connex.Channeldef;
import com.digitale.connex.Chatline;
import com.digitale.connex.Inventory;
import com.digitale.connex.MissionDef;
import com.digitale.connex.MissionLog;
import com.digitale.connex.ShipDef;
import com.digitale.mygdxgame.Renderer;
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.Stardust3d;
import com.digitale.screens.GameLoop;
import com.digitale.sim.Explosion;
import com.digitale.sim.Ship;
import com.digitale.sim.Simulation;
import com.digitale.utils.DataOp;
import com.digitale.utils.HttpClientFactory;
import com.digitale.utils.MCrypt;

public class DataOpImpl extends Thread implements DataOp {
	public boolean running = true;
	public static List<String> clientRequests = new ArrayList<String>();
	public static Timer timer = new Timer();
	int ticks = 0;

	public void run() {
		timer.scheduleAtFixedRate(new TimerTask() {
			private int longticks;

			public void run() {
				getServerTime();

				if (Ship.STATUS == 1) {
					Stardust3d.refreshActors();
					Simulation.updateReticules();
					Simulation.updateActorFontCache();
					Stardust3d.MyDataOp.updateDBShip();
					get3dChar(Integer.valueOf(Stardust3d.currencharacteruid));
					if (GameLoop.androidVersion != 0) {
						Renderer.statusFontCache.setWrappedText(
								Renderer.status, 000, (480 - 10)
										* GameLoop.screenScaleY,
								(800) * GameLoop.screenScaleX);
					} else {
						Renderer.statusFontCache.setWrappedText(
								Renderer.status, 000, (480 - 10), 800);
					}
				}
				processRequest();

				if (longticks == 1) {

					Stardust3d.chatListReset = false;
					Stardust3d.refreshChat(Stardust3d.chatChannel, false);
					Stardust3d.chatListReset = false;
					Stardust3d.refreshChat(1, true);
					if (Renderer.minichatFontCache != null) {
						if (GameLoop.androidVersion != 0) {
							Renderer.minichatFontCache.setWrappedText(
									Stardust3d.mshortChatLines,
									128 * GameLoop.screenScaleX,
									64 * GameLoop.screenScaleY,
									(800 - 128 - 64) * GameLoop.screenScaleX);
						} else {
							Renderer.minichatFontCache.setWrappedText(
									Stardust3d.mshortChatLines, 128, 64,
									800 - 128 - 64);
						}
					}
				}
				if (Ship.STATUS == 0 && ticks == 9) {
					Stardust3d.MyDataOp.updateDBShip();
					get3dChar(Integer.valueOf(Stardust3d.currencharacteruid));
				}
				ticks++;
				longticks++;
				if (ticks >= 10)

					ticks = 0;
				if (longticks >= 25)
					longticks = 0;
				if (!running)
					timer.cancel();
			}
		}, 0, 100);

	}

	private static String LOG_TAG = "DataOps";
	// server constants
	private static String IP = "127.0.0.1"; // localhost
	 //private static String IP = "82.2.189.174"; // laptop IP
	 //private static String IP = "192.168.1.16"; // laptop IP
	//private static String IP = "digitale.no-ip.org"; // internet IP
	// private static String IP="pluto"; //ubuntu IP
	 private static String stardustDir="/stardust";
	Calendar cal = Calendar.getInstance();
	java.text.SimpleDateFormat sdfin = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	java.text.SimpleDateFormat sdfout = new java.text.SimpleDateFormat(
			"yyyy-MM-dd%20HH:mm:ss");
	// php page constants
	// page address for encryption root
	public static final String REQUEST_ENCRYPTED = "http://" + IP
			+ stardustDir+"/publicinterface.php?data="; // private IP
	// get news php script
	public static final String REQUEST_NEWS = "http://" + IP
			+ stardustDir+"/getnews.php"; // private IP

	// get local players
	public static final String REQUEST_LOCAL_PLAYERS = "http://" + IP
			+ stardustDir+"/getactorsinsystem.php?x="; // private IP

	// get current solar system data
	public static final String REQUEST_SOLAR_SYSTEM = "http://" + IP
			+ stardustDir+"/getsolarsystem.php?x="; // private IP
	// get the different system types
	public static final String REQUEST_GROUND_TYPES = "http://" + IP
			+ stardustDir+"/getgroundtypes.php"; // private IP
	// get current sector data
	public static final String REQUEST_LANDSCAPE = "http://" + IP
			+ stardustDir+"/getlandscape.php"; // private IP
	// get npcs
	public static final String REQUEST_NPC = "http://" + IP
			+ "//everworld/getnpcfast.php"; // private IP
	// get servertime
	public static final String REQUEST_TIME = "http://" + IP
			+ stardustDir+"/gettime.php"; // private IP
	// get inventory
	public static final String REQUEST_INVENTORY = "http://" + IP
			+ stardustDir+"/getinventory.php?char="; // private IP
	// delete item from inventory
	public static final String REQUEST_ITEMDELETE = "http://" + IP
			+ stardustDir+"/itemdelete.php?item="; // private IP

	private HttpPost requestGroundTypesHTTPPost = new HttpPost(
			REQUEST_GROUND_TYPES);
	private HttpPost requestLandscapeHTTPPost = new HttpPost(REQUEST_LANDSCAPE);
	private HttpPost requestNPCHTTPPost = new HttpPost(REQUEST_NPC);
	private HttpPost requestTimeHTTPPost = new HttpPost(REQUEST_TIME);
	private BufferedReader reader;
	
	// private InputStream is = null;
	private String line = null;
	private String stream = null;
	public long mNetworkPing;
	public long mParsePing;

	public String getLandTypeData(String returnString) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;

		String result = "";

		// http post
		try {

			// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient
					.execute(requestGroundTypesHTTPPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {

		}

		// convert response to string
		try {
			reader = new BufferedReader(
					new InputStreamReader(is, "iso-8859-1"), 2096);

			line = null;
			while ((line = reader.readLine()) != null) {

				if (Stardust3d.DEBUG)
					System.out.println(LOG_TAG + "Everworld: line: " + line);
			}
			is.close();

			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// "Everworld: Result: "+result);
		} catch (Exception e) {

		}
		// parse json data

		return returnString;
	}

	public String getLandscapeData(String returnString) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;

		String result = "";

		// http post
		try {

			// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient
					.execute(requestLandscapeHTTPPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "Stardust landscape getting data"
						+ is);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}

		// convert response to string
		try {
			reader = new BufferedReader(
					new InputStreamReader(is, "iso-8859-1"), 2096);
			line = null;

			while ((line = reader.readLine()) != null) {

				// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
				// "Everworld: line: "+line);
			}
			is.close();

			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// "Everworld: Result: "+result);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ "Everworld: Error converting result " + e.toString());
		}
		returnString = "";
		String jArray = null;
		for (int i = 0; i < jArray.length(); i++) {
			String json_data;
			/*
			 * if(Stardust3d.DEBUG)
			 * System.out.println(LOG_TAG+"ground: "+json_data
			 * .getString("ground")+ ", x: "+json_data.getInt("x")+
			 * ", y: "+json_data.getInt("y")+ ", z: "+json_data.getInt("z") );
			 */
			// load into global array
			/*
			 * Stardust3d.LandScape[json_data.getInt("x") - 1][json_data
			 * .getInt("y") - 1].set_ground(json_data .getString("ground"));
			 * Stardust3d.LandScape[json_data.getInt("x") - 1][json_data
			 * .getInt("y") - 1].set_z((byte) json_data.getInt("z"));
			 * Stardust3d.LandScape[json_data.getInt("x") - 1][json_data
			 * .getInt("y") - 1].set_sysname(json_data .getString("sysname"));
			 * Stardust3d.LandScape[json_data.getInt("x") - 1][json_data
			 * .getInt("y") - 1].set_level((byte) json_data .getInt("level"));
			 * Stardust3d.LandScape[json_data.getInt("x") - 1][json_data
			 * .getInt("y") - 1].setLeftlink((byte) json_data
			 * .getInt("leftlink")); Stardust3d.LandScape[json_data.getInt("x")
			 * - 1][json_data .getInt("y") - 1].setDownlink((byte) json_data
			 * .getInt("downlink"));
			 */
			// Get an output to the screen
			// returnString += "\n\t" + jArray.getJSONObject(i);
			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// "Everworld: return string current "+
			// returnString);
		}
		return returnString;
	}

	public String getNPCData(String returnString) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;

		// http post
		try {

			// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(requestNPCHTTPPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "Stardust  NPC getting data" + is);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}

		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 2096);
			line = null;
			int i = 0;
			// for some reason first line is blank so skip
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] RowData = line.split(",");
				// RowData = line.split(",");
				Stardust3d.npcList[i].set_race(RowData[0]);
				int mybyte = Integer.valueOf(RowData[1]);
				Stardust3d.npcList[i].set_x((byte) mybyte);

				mybyte = Integer.valueOf(RowData[2]);
				Stardust3d.npcList[i].set_y((byte) mybyte);

				mybyte = Integer.valueOf(RowData[3]);
				Stardust3d.npcList[i].set_z((byte) mybyte);
				if (RowData[4].equals("1")) {
					Stardust3d.npcList[i].set_is_static(true);
				} else {
					Stardust3d.npcList[i].set_is_static(false);
				}
				Stardust3d.npcList[i].set_firstname(RowData[5]);
				Stardust3d.npcList[i].set_surname(RowData[6]);
				// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
				// "Everworld: line: "+line);
				i++;
			}
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ "Everworld: Error converting npc result "
						+ e.toString());

			e.printStackTrace();
		}

		return null;
	}

	public void getServerTime() {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		long pingSQLget = 0;
		long pingStart = 0;
		long pingSQLparse = 0;
		String result = "";
		// http post
		try {
			// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			pingStart = System.currentTimeMillis();
			HttpResponse response = httpclient.execute(requestTimeHTTPPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			if (Stardust3d.DEEPDEBUG)
				System.out
						.println(LOG_TAG + "Stardust  getting time data" + is);
			pingSQLget = System.currentTimeMillis();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}

		// convert response to string
		try {
			reader = new BufferedReader(
					new InputStreamReader(is, "iso-8859-1"), 2096);

			line = null;
			while ((line = reader.readLine()) != null) {
				String[] RowData = line.split("#");

				Stardust3d.mserverTime = (RowData[0]);
			}
			is.close();
			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// "Everworld: Result: "+result);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ "Stardust Error converting time result "
						+ e.toString());
			e.printStackTrace();
		}

		pingSQLparse = System.currentTimeMillis();
		Stardust3d.mnetworkPing = pingSQLget - pingStart;
		Stardust3d.mParsePing = pingSQLparse - pingSQLget;

	}

	@Override
	public String login(String accountName, String password) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "false";
		String accountrow = "";
		String accountid = "";
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "3#" + accountName + "#" + password;
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "login plaintext:" + data);
		String encrypted = "";
		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "login request:" + request
						+ encrypted);

			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " data:"
						+ response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ " Stardust getting login data     " + is);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 8192);
			line = null;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() > 1) {
					line = new String(mcrypt.decrypt(line.trim()));
					if (Stardust3d.DEBUG)
						System.out.println("decrypted " + i + " line" + line);
					line = line.replace('~', ' ');
					line = line.trim();
					String[] RowData = line.split("#");

					accountrow = (RowData[0]);
					accountid = (RowData[1]);

				}

				if (Stardust3d.DEBUG)
					System.out.println(LOG_TAG + "accountdata " + accountrow
							+ " id " + accountid);
				i++;
				// if valid account id set golbal
				if (accountid.length() > 1) {
					accountid = accountid.substring(1);
					if (Stardust3d.DEBUG)
						System.out.println(LOG_TAG + "trimmed accountid "
								+ accountid);

					Stardust3d.muser = Integer.valueOf(accountid);
				}
			}
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "Everworld: Error logging in "
						+ e.toString());
			e.printStackTrace();
		}
		// if account exists and is active
		if (accountrow.contains("TRUE")) {
			result = "true";

		} else
		// if result is MD5
		if (accountrow.length() > 20) {
			result = "NOT_ACTIVE";

		} else {
			// no such account or password is wrong

			result = "false";
		}
		return result;
	}

	@Override
	public String getChars(int userid) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "4#" + userid + "#";
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "charlist plaintext:" + data);
		String encrypted = "";
		String linebuffer = null;
		stream = null;
		String decodedstream = null;
		int i = 0;
		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "login request:" + request
						+ encrypted);

			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " data:"
						+ response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Charselect getting data for "
						+ userid);

		} catch (NoRouteToHostException e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ " Error in http connection (network down) "
						+ e.toString());
			Stardust3d.Error = Stardust3d.NETWORK_DOWN;
		} catch (IllegalStateException e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ " Error in http connection (state exception) "
						+ e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ " Error in http connection (IO exception) "
						+ e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		// convert response to string
		try {

			reader = new BufferedReader(new InputStreamReader(is), 2096);

			while ((stream = reader.readLine()) != null) {
				if (stream.trim().length() > 1) {
					stream = new String(mcrypt.decrypt(stream.trim()));
					stream = new String(stream.replace('~', ' '));
					decodedstream = new String(stream.trim());

				}
				i++;
			}
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ "Everworld: Error getting character " + e.toString());
			e.printStackTrace();
		}
		String currentline;
		if (Stardust3d.DEBUG)
			System.out.println("decrypted " + i + " stream" + decodedstream);
		i = 0;
		StringReader stringr = new StringReader(decodedstream);
		BufferedReader reader = new BufferedReader(stringr);

		try {
			while ((linebuffer = reader.readLine()) != null) {
				if (Stardust3d.DEBUG)
					System.out.println("decrypted " + i + " line" + linebuffer);
				if (linebuffer.length() > 1) {
					currentline = new String(linebuffer + "");
					String[] RowData = currentline.split("#");
					Stardust3d.charList[i].setUid(Integer.valueOf(RowData[0]));
					Stardust3d.charList[i].setFirstname(RowData[1]);
					Stardust3d.charList[i].setSurname(RowData[2]);
					Stardust3d.charList[i].setStamina(Integer
							.valueOf(RowData[3]));
					Stardust3d.charList[i].setIntelligence(Integer
							.valueOf(RowData[4]));
					Stardust3d.charList[i].setSocial(Integer
							.valueOf(RowData[5]));
					Stardust3d.charList[i].setDexterity(Integer
							.valueOf(RowData[6]));
					Stardust3d.charList[i].setLeadership(Integer
							.valueOf(RowData[7]));
					Stardust3d.charList[i].setRecuperation(Integer
							.valueOf(RowData[8]));
					Stardust3d.charList[i].setExp(Integer.valueOf(RowData[9]));
					Stardust3d.charList[i].setX(Integer.valueOf(RowData[10]));
					Stardust3d.charList[i].setY(Integer.valueOf(RowData[11]));
					Stardust3d.charList[i].setZ(Integer.valueOf(RowData[11]));
					Stardust3d.charList[i].setSystem(RowData[13]);
					Stardust3d.charList[i].setShipname(RowData[14]);
					Stardust3d.charList[i].setShipicon(RowData[15]);
					Stardust3d.charList[i].setAvatar(RowData[16]);
					if (Stardust3d.DEBUG)
						System.out.println(LOG_TAG + "Everworld: char: "
								+ Stardust3d.charList[i].getFirstname() + " "
								+ Stardust3d.charList[i].getFirstname());
				}
				i++;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	@Override
	public String getInventory(int characterId, boolean init) {

		// TODO need to encypt
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		try {

			String request = REQUEST_INVENTORY + characterId;

			HttpResponse response = httpclient.execute(new HttpPost(request));
			// Log.i(LOG_TAG+ " data:"+response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Stardust getting inventory data"
						+ request);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 8192);
			line = null;
			int i = 0;

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ " *****Everworld  getting inventory data" + is);
			while ((line = reader.readLine()) != null ) {
				// have to use hash as delimiter or description field breaks csv
				// import
				Inventory thisItem = new Inventory();
				String[] RowData = line.split("#");

				thisItem.setSlot_id(Integer.valueOf(RowData[0]));
				thisItem.setInventoryid(Integer.valueOf(RowData[1]));
				thisItem.setitemUid(Integer.valueOf(RowData[2]));
				thisItem.setCategory(RowData[3]);
				thisItem.setSubcat(RowData[4]);
				thisItem.setItem(RowData[5]);
				thisItem.setMass(Integer.valueOf(RowData[6]));
				thisItem.setStacks(Integer.valueOf(RowData[7]));
				thisItem.setDescription(RowData[8]);
				thisItem.setContraband(Integer.valueOf(RowData[9]));
				thisItem.setRecipe(Integer.valueOf(RowData[10]));
				thisItem.setCapacity(Integer.valueOf(RowData[11]));
				thisItem.setLevel(Integer.valueOf(RowData[12]));
				thisItem.setIcon(RowData[13]);
				thisItem.setQuality(Integer.valueOf(RowData[14]));
				thisItem.setValue(Integer.valueOf(RowData[15]));
				thisItem.setEffect(RowData[16]);
				thisItem.setBind(Integer.valueOf(RowData[17]));
				thisItem.setCount(Integer.valueOf(RowData[18]));
				if (Stardust3d.DEBUG)
					System.out.println(i + thisItem.getItem());
				if (thisItem.getItem() != null) {
					Stardust3d.myInventory.add(thisItem);
					i++;
				}
			}
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ "Everworld: Error getting inventory for main "
						+ e.toString());
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public void deleteItem(int uid) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "deleteing data item: " + uid);
		try {

			String request = REQUEST_ITEMDELETE + uid;

			HttpResponse response = httpclient.execute(new HttpPost(request));
			// Log.i(LOG_TAG+ "deleteing item: "+ uid+
			// " Delete status:"+response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			// consume any lines to release connection
			is = entity.getContent();
			// Log.w(LOG_TAG+" *****Everworld NPC getting delete" +is);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
			e.printStackTrace();
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 8192);
			line = null;
			// for some reason first line is blank so skip
			line = reader.readLine();
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " *****Everworld deleting data  "
						+ is);
			while ((line = reader.readLine()) != null) {
				// have to use hash as delimeter or description field breaks csv
				// import
				String[] RowData = line.split("#");

				if (Stardust3d.DEBUG)
					System.out.println(LOG_TAG + "Everworld: deletedata: "
							+ RowData);
			}
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ " Error in http connection when deleteing item "
						+ e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public void dockship(int uid) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "docking ship: " + uid);
		try {
			MCrypt mcrypt = new MCrypt();
			String request = REQUEST_ENCRYPTED;
			String data = "10#" + uid + "#a whole bunch of salt";
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "dockship plaintext:" + data);
			String encrypted = "";
			/* Encrypt */
			try {
				encrypted = (mcrypt.encrypt(data));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "dockship request:" + request
						+ encrypted);

			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));

			HttpEntity entity = response.getEntity();
			// consume any lines to release connection
			is = entity.getContent();
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public void undockship(int uid) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "Undocking ship: " + uid);
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "11#" + uid + "#a whole lot of salting going on";
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "undockship plaintext:" + data);
		String encrypted = "";
		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "undockship request:" + request
					+ encrypted);
		try {
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));

			HttpEntity entity = response.getEntity();
			// consume any lines to release connection
			is = entity.getContent();
			// Log.w(LOG_TAG+" *****Everworld NPC getting delete" +is);
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
			e.printStackTrace();
		}
		// convert response to string

	}

	@Override
	public String getNews() {
		// not encrypted so data can be accessed externally
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		try {
			String request = REQUEST_NEWS;
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "news request:" + request);
			HttpResponse response = httpclient.execute(new HttpPost(request));
			// Log.i(LOG_TAG+ " data:"+response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			if (Stardust3d.DEBUG)
				System.out
						.println(LOG_TAG + " Stardust getting news data" + is);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in news http connection "
						+ e.toString());
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 8192);
			line = null;
			int i = 0;
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ " *****Everworld  getting news data" + is);
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() > 1) {

					if (Stardust3d.DEBUG)
						System.out.println("decrypted " + i + " line" + line);

					// have to use hash as delimiter or description field breaks
					// csv
					// import
					String[] RowData = line.split("#");

					Stardust3d.news[i].set_text(RowData[0]);
					Stardust3d.news[i].set_title(RowData[1]);
					Stardust3d.news[i].set_date(RowData[2]);
					i++;
				}
			}
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "Everworld: Error getting news "
						+ e.toString());
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public String getFreeUser(String username) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "9#" + username + "#" + "more random salting";
		String encrypted = "";

		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "free user request:" + request
					+ encrypted);
		try {
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			// Log.i(LOG_TAG+ " data:"+response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Stardust getting free user data"
						+ is);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out
						.println(LOG_TAG
								+ " Error in free user http connection "
								+ e.toString());
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 8192);
			line = null;
			int i = 0;

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ " *****Everworld  getting free user data" + is);
			while ((line = reader.readLine()) != null) {
				if (Stardust3d.DEBUG)
					System.out.println("encrypted " + i + " line" + line);
				if (line.trim().length() > 1) {
					line = new String(mcrypt.decrypt(line.trim()));
					if (Stardust3d.DEBUG)
						System.out.println("decrypted " + i + " line" + line);
					line = line.replace('~', ' ');
					line = line.trim();
				}
				// have to use hash as delimiter or description field breaks csv
				// import
				String[] RowData = line.split("#");
				if (RowData[0] != null) {
					Stardust3d.muserExists = (RowData[0]);
					i++;
				}

			}
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out
						.println(LOG_TAG
								+ "Everworld: Error getting free users "
								+ e.toString());
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public String createUser(String ac, String mpw1, String memail) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "2#" + ac + "#" + mpw1 + "#" + memail;
		String encrypted = "";

		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "new account request:" + request
						+ encrypted);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			if (Stardust3d.DEBUG)
				System.out
						.println(LOG_TAG + " Stardust creating new user" + is);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ " Error in new user http connection " + e.toString());
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 8192);
			line = null;

			// for some reason first line is blank so skip
			line = reader.readLine();
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " *****Everworld  making new user"
						+ is);
			while ((line = reader.readLine()) != null) {
				// have to use hash as delimiter or description field breaks csv
				// import
				String[] RowData = line.split("#");

				Stardust3d.muserExists = (RowData[0]);

			}
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ "Everworld: Error making new user " + e.toString());
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public String get3dChar(int characterName) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "5#" + characterName;
		if (Stardust3d.DEEPDEBUG)
			System.out.println(LOG_TAG + "get current character plaintext:"
					+ data);
		String encrypted = "";
		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (Stardust3d.DEEPDEBUG)
			System.out.println(LOG_TAG + "get current character request:"
					+ request + encrypted);

		try {

			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));

			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			if (Stardust3d.DEEPDEBUG)
				System.out.println(LOG_TAG
						+ " *****Everworld char getting data" + is);
		} catch (Exception e) {
			if (Stardust3d.DEEPDEBUG)
				System.out.println(LOG_TAG
						+ "Everworld: Error getting char data " + e.toString());
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 8192);
			line = null;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() > 1) {
					line = new String(mcrypt.decrypt(line.trim()));
					if (Stardust3d.DEEPDEBUG)
						System.out.println("decrypted " + i + " line" + line);
					line = line.replace('~', ' ');
					line = line.trim();
					String[] RowData = line.split("#");

					Stardust3d.myCharacter.setUid(Integer.valueOf(RowData[0]));
					Stardust3d.myCharacter.setFirstname(RowData[1]);
					Stardust3d.myCharacter.setSurname(RowData[2]);
					Stardust3d.myCharacter.setStamina(Integer
							.valueOf(RowData[3]));
					Stardust3d.myCharacter.setIntelligence(Integer
							.valueOf(RowData[4]));
					Stardust3d.myCharacter.setSocial(Integer
							.valueOf(RowData[5]));
					Stardust3d.myCharacter.setDexterity(Integer
							.valueOf(RowData[6]));
					Stardust3d.myCharacter.setLeadership(Integer
							.valueOf(RowData[7]));
					Stardust3d.myCharacter.setRecuperation(Integer
							.valueOf(RowData[8]));
					Stardust3d.myCharacter.setExp(Integer.valueOf(RowData[9]));
					Stardust3d.myCharacter.setX(Integer.valueOf(RowData[10]));
					Stardust3d.myCharacter.setY(Integer.valueOf(RowData[11]));
					Stardust3d.myCharacter.setZ(Integer.valueOf(RowData[12]));
					Stardust3d.myCharacter.setSystem(RowData[13]);
					Stardust3d.myCharacter.setShipid(Integer
							.valueOf(RowData[14]));
					Stardust3d.myCharacter.setHitpoints(Integer
							.valueOf(RowData[15]));
					Stardust3d.myCharacter.setShield(Integer
							.valueOf(RowData[16]));
					Stardust3d.myCharacter.setPower(Integer
							.valueOf(RowData[17]));
					Stardust3d.myCharacter.setCpu(Integer.valueOf(RowData[18]));
					Stardust3d.myCharacter.setShipname(RowData[19]);
					Stardust3d.myCharacter.setShipicon(RowData[20]);
					Stardust3d.myCharacter.setSysx(Float.valueOf(RowData[21]));
					Stardust3d.myCharacter.setSysy(Float.valueOf(RowData[22]));
					Stardust3d.myCharacter.setSysz(Float.valueOf(RowData[23]));
					Stardust3d.myCharacter.setStatus(RowData[24]);
					Stardust3d.myCharacter.setCredits(Integer
							.valueOf(RowData[26]));
					i++;
					if (Stardust3d.DEEPDEBUG)
						System.out.println("data ops enter game as"
								+ Stardust3d.myCharacter.getFirstname()
								+ "sysx " + Stardust3d.myCharacter.getSysx()
								+ "sysy " + Stardust3d.myCharacter.getSysy()
								+ "sysz " + Stardust3d.myCharacter.getSysz()
								+ "status "
								+ Stardust3d.myCharacter.getStatus());
				}
			}
			is.close();
		} catch (Exception e) {
			System.out.println("decrypted line" + line);
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public void getShipFitting(int characterName) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "29#" + characterName;
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "get fitting plaintext:" + data);
		String encrypted = "";
		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "get current character request:"
					+ request + encrypted);

		try {

			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));

			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			if (Stardust3d.DEEPDEBUG)
				System.out.println(LOG_TAG
						+ " *****Everworld char getting data" + is);
		} catch (Exception e) {
			if (Stardust3d.DEEPDEBUG)
				System.out.println(LOG_TAG
						+ "Everworld: Error getting char data " + e.toString());
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 8192);
			line = null;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() > 1) {
					line = new String(mcrypt.decrypt(line.trim()));
					if (Stardust3d.DEEPDEBUG)
						System.out.println("decrypted " + i + " line" + line);
					line = line.replace('~', ' ');
					line = line.trim();
					String[] RowData = line.split("#");
					if (RowData != null) {
						Stardust3d.myFitting.defList.set(0,
								(Integer.valueOf(RowData[0])));
						Stardust3d.myFitting.defList.set(1,
								(Integer.valueOf(RowData[1])));
						Stardust3d.myFitting.defList.set(2,
								(Integer.valueOf(RowData[2])));
						Stardust3d.myFitting.defList.set(3,
								(Integer.valueOf(RowData[3])));
						Stardust3d.myFitting.defList.set(4,
								(Integer.valueOf(RowData[4])));

						Stardust3d.myFitting.offList.set(0,
								(Integer.valueOf(RowData[5])));
						Stardust3d.myFitting.offList.set(1,
								(Integer.valueOf(RowData[6])));
						Stardust3d.myFitting.offList.set(2,
								(Integer.valueOf(RowData[7])));
						Stardust3d.myFitting.offList.set(3,
								(Integer.valueOf(RowData[8])));
						Stardust3d.myFitting.offList.set(4,
								(Integer.valueOf(RowData[9])));

						Stardust3d.myFitting.augList.set(0,
								(Integer.valueOf(RowData[10])));
						Stardust3d.myFitting.augList.set(1,
								(Integer.valueOf(RowData[11])));
						Stardust3d.myFitting.augList.set(2,
								(Integer.valueOf(RowData[12])));
						Stardust3d.myFitting.augList.set(3,
								(Integer.valueOf(RowData[13])));
						Stardust3d.myFitting.augList.set(4,
								(Integer.valueOf(RowData[14])));
					}
				}
			}
			is.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void getSolarSystem(float x, float y, float z) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		try {

			String request = REQUEST_SOLAR_SYSTEM + x + "&y=" + y + "&z=" + z;

			HttpResponse response = httpclient.execute(new HttpPost(request));
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "solar sys request:" + request);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// " Charselect getting data for " + accountName);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 2096);
			line = null;
			int i = 0;
			// for some reason first line is blank so skip
			line = reader.readLine();

			while ((line = reader.readLine()) != null) {

				String[] RowData = line.split(",");
				Stardust3d.solarSystem[i].setX(Integer.valueOf(RowData[0]));
				Stardust3d.solarSystem[i].setY(Integer.valueOf(RowData[1]));
				Stardust3d.solarSystem[i].setZ(Integer.valueOf(RowData[2]));
				Stardust3d.solarSystem[i].setSysx(Integer.valueOf(RowData[3]));
				Stardust3d.solarSystem[i].setSysy(Integer.valueOf(RowData[4]));
				Stardust3d.solarSystem[i].setSysz(Integer.valueOf(RowData[5]));
				Stardust3d.solarSystem[i].setModelScale(Integer
						.valueOf(RowData[6]));
				Stardust3d.solarSystem[i].setActivationRadius(Integer
						.valueOf(RowData[7]));
				Stardust3d.solarSystem[i].setName(RowData[8]);
				Stardust3d.solarSystem[i].setStructname(RowData[9]);

				i++;
			}
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ "Everworld: Error getting solarsystem "
						+ e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public void getLocalPlayers(int x, int y, int z, boolean init) {
		// init=false;

		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String currentTime = null;
		if (Stardust3d.mserverTime != null) {

			try {
				cal.setTime(sdfin.parse(Stardust3d.mserverTime));
				if (Stardust3d.DEEPDEBUG) {
					System.out.println(LOG_TAG + "calendar " + cal);
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			cal.add(Calendar.MINUTE, -1);
			currentTime = sdfout.format(cal.getTime());
		}
		if (init == true) {

			currentTime = "0000-00-00%2000:00:00";
		}

		String result = "";
		try {

			String request = REQUEST_LOCAL_PLAYERS + x + "&y=" + y + "&z=" + z
					+ "&charid=" + Stardust3d.myCharacter.getUid() + "&lu="
					+ currentTime;
			if (Stardust3d.DEEPDEBUG) {
				System.out.println(LOG_TAG + "local actor request:" + request);
			}
			HttpResponse response = httpclient.execute(new HttpPost(request));

			HttpEntity entity = response.getEntity();
			if (Stardust3d.DEEPDEBUG)
				System.out.println(LOG_TAG + "local actor data:"
						+ response.getStatusLine().toString() + "  "
						+ response.getEntity().toString());
			is = entity.getContent();
			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// " Charselect getting data for " + accountName);
		} catch (Exception e) {
			if (Stardust3d.DEEPDEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}
		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is), 2096);
			line = null;
			int i = 0;
			// for some reason first line is blank so skip
			// line = reader.readLine();
			if (init)
				Stardust3d.actorsList.clear();
			while ((line = reader.readLine()) != null) {
				if (Stardust3d.DEEPDEBUG) {
					System.out.println(LOG_TAG + " actorline " + line);
				}
				Actor thisActor = new Actor();
				String[] RowData = line.split(",");
				thisActor.set_firstname(RowData[0]);
				thisActor.set_surname(RowData[1]);
				thisActor.set_position(new Vector3(Float.valueOf(RowData[2]),
						Float.valueOf(RowData[3]), Float.valueOf(RowData[4])));
				thisActor.setHitpoints(Integer.valueOf(RowData[5]));
				thisActor.set_uid(new BigInteger(RowData[6]));
				thisActor.setStatus(Integer.valueOf(RowData[7]));
				thisActor.setPitchangle(Float.valueOf(RowData[8]));
				thisActor.setYawangle(Float.valueOf(RowData[9]));
				thisActor.setShipname(RowData[10]);
				thisActor.setVelocity(Float.valueOf(RowData[11]));
				boolean updated = false;
				String thisActorid = "" + thisActor.get_uid();
				for (int j = 0; j < Stardust3d.actorsList.size(); j++) {
					String currentActorid = ""
							+ Stardust3d.actorsList.get(j).get_uid();
					// if actorlist contains this actor already and we havent
					// already updated
					if (Stardust3d.DEEPDEBUG) {
						System.out.println(LOG_TAG + " check " + currentActorid
								+ " = " + thisActorid);
					}

					if (currentActorid.equals(thisActorid)) {
						if (thisActor.getShipname().equals("dead")
								|| thisActor.getHitpoints() < 0) {
							if ( !Stardust3d.actorsList.get(j).exploding) {
								Simulation.explosions
										.add(new Explosion(thisActor.position,
												Explosion.SWANSONG));
								Stardust3d.actorsList.get(j).exploding = true;
								if (Simulation.listener != null)
									Simulation.listener.boom();
								// Stardust3d.actorsList.remove(j);
							}
							Stardust3d.actorsList.get(j).shipname = thisActor.shipname;
							// Stardust3d.actorsList.remove(j);

							// maybe notify renderer for explosion
						} else {
							// update the existing actor
							Stardust3d.actorsList.get(j).setHitpoints(
									thisActor.getHitpoints());
							Stardust3d.actorsList.get(j).setPitchangle(
									thisActor.getPitchangle());
							Stardust3d.actorsList.get(j).setYawangle(
									thisActor.getYawangle());
							Stardust3d.actorsList.get(j).position = thisActor
									.get_position();
							Stardust3d.actorsList.get(j).shipname = thisActor.shipname;
							Stardust3d.actorsList.get(j).setStatus(
									thisActor.getStatus());
						}
						updated = true;

						if (Stardust3d.DEEPDEBUG) {
							System.out.println(LOG_TAG + " update");
							break;
						}
					}
				}
				// if we didnt find the uid, so updated is false it must be a
				// new actor
				if (updated == false) {
					Stardust3d.actorsList.add(thisActor);
					if (Stardust3d.DEEPDEBUG) {
						System.out.println(LOG_TAG + " add");
					}
				}
				i++;
				if (Stardust3d.DEEPDEBUG) {
					System.out.println(LOG_TAG + "actor"
							+ thisActor.get_firstname() + " "
							+ thisActor.getVelocity() + " "
							+ thisActor.get_position().y);
				}
			}
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEEPDEBUG)
				System.out.println(LOG_TAG
						+ "Everworld: Error getting local actors "
						+ e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public String makeAvatar(String selectedAvatar, String selectedRace,
			String selectedSex, String firstName, String surName, int muser) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + " making char: ");
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "6#" + selectedAvatar + "#" + selectedRace + "#"
				+ selectedSex + "#" + firstName + "#" + surName + "#" + muser;
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "new char plaintext:" + data);
		String encrypted = "";
		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "new character request:" + request
					+ encrypted);
		try {
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			// Log.i(LOG_TAG+ "deleteing item: "+ uid+
			if (Stardust3d.DEBUG)
				System.out.println("dbstatus "
						+ response.getStatusLine().toString());
			if ((response.getStatusLine().toString()).equals("")) {
			}
			;
			HttpEntity entity = response.getEntity();
			// consume any lines to release connection
			is = entity.getContent();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
			e.printStackTrace();
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 8192);
			line = null;
			int i = 0;

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " *****Everworld make avatar  "
						+ is);
			while ((line = reader.readLine()) != null) {
				// have to use hash as delimeter or description field breaks csv
				// import
				if (line.trim().length() > 1) {
					line = new String(mcrypt.decrypt(line.trim()));
					if (Stardust3d.DEBUG)
						System.out.println("decrypted " + i + " line" + line);
					line = line.replace('~', ' ');
					line = line.trim();
					String[] RowData = line.split("#");
					result = RowData[0];
				}
				if (Stardust3d.DEBUG)
					System.out.println(LOG_TAG
							+ "Everworld: make avatar result: " + result);
				i++;
			}
			is.close();

		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ " Error in http connection when making avatar "
						+ e.toString());
			e.printStackTrace();
		}
		if (result.length() > 9) {
			return result.substring(0, 10);
		} else {
			return "ok";
		}

	}

	@Override
	public String postChat(String chattext, int chatchannel, int muser) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + " posting chat: ");
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "13#" + chattext + "#" + chatchannel + "#" + muser;
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "new chat post plaintext:" + data);
		String encrypted = "";
		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "new chat post request:" + request
					+ encrypted);
		try {
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			// Log.i(LOG_TAG+ "deleteing item: "+ uid+
			if (Stardust3d.DEBUG)
				System.out.println("dbstatus "
						+ response.getStatusLine().toString());
			if ((response.getStatusLine().toString()).equals("")) {
			}
			;
			HttpEntity entity = response.getEntity();
			// consume any lines to release connection
			is = entity.getContent();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
			e.printStackTrace();
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 8192);
			line = null;
			int i = 0;

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " post chat  " + is);
			while ((line = reader.readLine()) != null) {
				// have to use hash as delimeter or description field breaks csv
				// import
				if (line.trim().length() > 1) {
					line = new String(mcrypt.decrypt(line.trim()));
					if (Stardust3d.DEBUG)
						System.out.println("decrypted " + i + " line" + line);
					line = line.replace('~', ' ');
					line = line.trim();
					String[] RowData = line.split("#");
					result = RowData[0];
				}
				if (Stardust3d.DEBUG)
					System.out.println(LOG_TAG + "post chat result: " + result);
				i++;
			}
			is.close();

		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ " Error in http connection when posting chat "
						+ e.toString());
			e.printStackTrace();
		}
		if (result.length() > 9) {
			return result.substring(0, 10);
		} else {
			return "ok";
		}

	}

	@Override
	public void getAssetDefs() {
		// get graphics stuff for Avatars
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String linebuffer = null;
		stream = null;
		int i = 0;
		String decodedstream = null;
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "17#" + "some random salt";
		String encrypted = "";

		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "AssetDef request:" + request
						+ encrypted);
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "AssetDef data:"
						+ response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// " Charselect getting data for " + accountName);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}
		// convert response to string
		try {
			try {
				reader = new BufferedReader(new InputStreamReader(is), 2096);
				while ((stream = reader.readLine()) != null) {
					if (stream.trim().length() > 1) {
						stream = new String(mcrypt.decrypt(stream.trim()));
						stream = new String(stream.replace('~', ' '));
						decodedstream = new String(stream.trim());
					}
					i++;
				}
				is.close();
			} catch (Exception e) {
				if (Stardust3d.DEBUG)
					System.out.println(LOG_TAG
							+ "Everworld: Error getting AssetDef  "
							+ e.toString());
				System.out.println(LOG_TAG + "AssetDef request:" + request
						+ encrypted);
				e.printStackTrace();
			}
			String currentline;
			if (Stardust3d.DEBUG)
				System.out
						.println("decrypted " + i + " stream" + decodedstream);
			i = 0;
			StringReader stringr = new StringReader(decodedstream);
			BufferedReader reader = new BufferedReader(stringr);
			while ((linebuffer = reader.readLine()) != null) {
				if (Stardust3d.DEBUG)
					System.out.println("decrypted " + i + " line" + linebuffer);
				if (linebuffer.length() > 1) {
					currentline = new String(linebuffer + "");
					String[] RowData = currentline.split("#");
					AssetDef thisAssetDef = new AssetDef();
					thisAssetDef.setUid(Integer.valueOf(RowData[0]));
					thisAssetDef.setAssetName(RowData[1]);
					thisAssetDef.setExt(RowData[2]);
					thisAssetDef.setType(RowData[3]);
					// thisAssetDef.setData(RowData[4]);
					if (Stardust3d.DEBUG)
						System.out.println(i + thisAssetDef.getAssetName());
					Stardust3d.assetList.add(thisAssetDef);
					i++;
				}
			}

		} catch (Exception e) {
			System.out.println(LOG_TAG + "Everworld: Error getting AssetDef "
					+ e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public void getChatDefs() {
		// get graphics stuff for Avatars
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String linebuffer = null;
		stream = null;
		int i = 0;
		String decodedstream = null;
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "15#" + "some random salt";
		String encrypted = "";

		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "chat channel def request:"
						+ request + encrypted);
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "chat channeldef data:"
						+ response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// " Charselect getting data for " + accountName);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}
		// convert response to string
		try {
			try {
				reader = new BufferedReader(new InputStreamReader(is), 2096);
				while ((stream = reader.readLine()) != null) {
					if (stream.trim().length() > 1) {
						stream = new String(mcrypt.decrypt(stream.trim()));
						stream = new String(stream.replace('~', ' '));
						decodedstream = new String(stream.trim());
					}
					i++;
				}
				is.close();
			} catch (Exception e) {
				if (Stardust3d.DEBUG)
					System.out.println(LOG_TAG
							+ "Everworld: Error getting chat channel def "
							+ e.toString());
				e.printStackTrace();
			}
			String currentline;
			if (Stardust3d.DEBUG)
				System.out
						.println("decrypted " + i + " stream" + decodedstream);
			i = 0;
			StringReader stringr = new StringReader(decodedstream);
			BufferedReader reader = new BufferedReader(stringr);
			while ((linebuffer = reader.readLine()) != null) {
				if (Stardust3d.DEBUG)
					System.out.println("decrypted " + i + " line" + linebuffer);
				if (linebuffer.length() > 1) {
					currentline = new String(linebuffer + "");
					String[] RowData = currentline.split("#");
					Channeldef thisChannelDef = new Channeldef();
					thisChannelDef.setUid(Integer.valueOf(RowData[0]));
					thisChannelDef.setChannelname(RowData[1]);
					thisChannelDef.setChanneltype(Integer.valueOf(RowData[2]));

					if (Stardust3d.DEBUG)
						System.out.println(i + thisChannelDef.getChannelname());
					Stardust3d.channelList.add(thisChannelDef);
					i++;
				}
			}

		} catch (Exception e) {
			System.out.println(LOG_TAG
					+ "Everworld: Error getting chat channel def "
					+ e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public void getShipDefs() {
		// get graphics stuff for ships
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String linebuffer = null;
		stream = null;
		int i = 0;
		String decodedstream = null;
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "8#" + "some random salt";
		String encrypted = "";

		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "shipdef request:" + request
						+ encrypted);
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "shipdef data:"
						+ response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// " Charselect getting data for " + accountName);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}
		// convert response to string
		try {
			try {

				reader = new BufferedReader(new InputStreamReader(is), 2096);

				while ((stream = reader.readLine()) != null) {
					if (stream.trim().length() > 1) {
						stream = new String(mcrypt.decrypt(stream.trim()));
						stream = new String(stream.replace('~', ' '));
						decodedstream = new String(stream.trim());

					}
					i++;
				}
				is.close();
			} catch (Exception e) {
				if (Stardust3d.DEBUG)
					System.out.println(LOG_TAG
							+ "Everworld: Error getting shipdefs "
							+ e.toString());
				e.printStackTrace();
			}
			String currentline;
			if (Stardust3d.DEBUG)
				System.out
						.println("decrypted " + i + " stream" + decodedstream);
			i = 0;
			StringReader stringr = new StringReader(decodedstream);
			BufferedReader reader = new BufferedReader(stringr);

			while ((linebuffer = reader.readLine()) != null) {
				if (Stardust3d.DEBUG)
					System.out.println("decrypted " + i + " line" + linebuffer);
				if (linebuffer.length() > 1) {
					currentline = new String(linebuffer + "");
					String[] RowData = currentline.split("#");
					ShipDef thisShipDef = new ShipDef();

					thisShipDef.setUid(Integer.valueOf(RowData[0]));
					thisShipDef.setItemid(Integer.valueOf(RowData[1]));

					thisShipDef.setJet1x(Float.valueOf(RowData[2]));
					thisShipDef.setJet1y(Float.valueOf(RowData[3]));
					thisShipDef.setJet1z(Float.valueOf(RowData[4]));

					thisShipDef.setJet2x(Float.valueOf(RowData[5]));
					thisShipDef.setJet2y(Float.valueOf(RowData[6]));
					thisShipDef.setJet2z(Float.valueOf(RowData[7]));

					thisShipDef.setJet3x(Float.valueOf(RowData[8]));
					thisShipDef.setJet3y(Float.valueOf(RowData[9]));
					thisShipDef.setJet3z(Float.valueOf(RowData[10]));

					thisShipDef.setJet4x(Float.valueOf(RowData[11]));
					thisShipDef.setJet4y(Float.valueOf(RowData[12]));
					thisShipDef.setJet4z(Float.valueOf(RowData[13]));

					thisShipDef.setJet5x(Float.valueOf(RowData[14]));
					thisShipDef.setJet5y(Float.valueOf(RowData[15]));
					thisShipDef.setJet5z(Float.valueOf(RowData[16]));

					thisShipDef.setMaingunx(Float.valueOf(RowData[17]));
					thisShipDef.setMainguny(Float.valueOf(RowData[18]));
					thisShipDef.setMaingunz(Float.valueOf(RowData[19]));

					thisShipDef.setTurret1x(Float.valueOf(RowData[20]));
					thisShipDef.setTurret1y(Float.valueOf(RowData[21]));
					thisShipDef.setTurret1z(Float.valueOf(RowData[22]));

					thisShipDef.setTurret2x(Float.valueOf(RowData[23]));
					thisShipDef.setTurret2y(Float.valueOf(RowData[24]));
					thisShipDef.setTurret2z(Float.valueOf(RowData[25]));

					thisShipDef.setTurret3x(Float.valueOf(RowData[26]));
					thisShipDef.setTurret3y(Float.valueOf(RowData[27]));
					thisShipDef.setTurret3z(Float.valueOf(RowData[28]));

					thisShipDef.setTurret4x(Float.valueOf(RowData[29]));
					thisShipDef.setTurret4y(Float.valueOf(RowData[30]));
					thisShipDef.setTurret4z(Float.valueOf(RowData[31]));

					thisShipDef.setTurret5x(Float.valueOf(RowData[32]));
					thisShipDef.setTurret5y(Float.valueOf(RowData[33]));
					thisShipDef.setTurret5z(Float.valueOf(RowData[34]));

					thisShipDef.setModel(RowData[35]);
					thisShipDef.setTexture(RowData[36]);

					if (Stardust3d.DEBUG)
						System.out.println(i + thisShipDef.getModel());
					if (thisShipDef.getModel() != null) {
						Stardust3d.shipdefs.add(thisShipDef);

					}

					i++;
				}
			}

		} catch (Exception e) {
			System.out.println(LOG_TAG + "Everworld: Error getting avatardef "
					+ e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public void getRandomHumanFemaleName() {
		// get graphics stuff for ships
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		MCrypt mcrypt = new MCrypt();
		try {

			String request = REQUEST_ENCRYPTED;
			String data = "1#french";
			String encrypted = "";

			/* Encrypt */
			try {
				encrypted = (mcrypt.encrypt(data));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ "female human random name request:" + request
						+ encrypted);
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "name data:"
						+ response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// " Charselect getting data for " + accountName);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 2096);
			line = null;
			int i = 0;

			while ((line = reader.readLine()) != null) {
				if (Stardust3d.DEBUG)
					System.out.println("encrypted " + i + " line" + line);
				if (line.trim().length() > 1) {
					line = new String(mcrypt.decrypt(line.trim()));
					if (Stardust3d.DEBUG)
						System.out.println("decrypted " + i + " line" + line);
					line = line.replace('~', ' ');
					line = line.trim();
					String[] RowData = line.split(",");

					Stardust3d.generatedFirstName = (RowData[0]);
					Stardust3d.generatedSurName = (RowData[1]);

					if (Stardust3d.DEBUG)
						System.out.println("random name "
								+ Stardust3d.generatedFirstName + " "
								+ Stardust3d.generatedSurName);
				}

				i++;
			}
			is.close();
		} catch (Exception e) {
			System.out.println(LOG_TAG
					+ "Everworld: Error getting random human female name "
					+ e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public String hitOpponent(BigInteger charID, int damage, int myID) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + " posting damage: ");
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "16#" + charID + "#" + damage + "#" + myID + "#";
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "do damage plaintext:" + data);
		String encrypted = "";
		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "do damage post request:" + request
					+ encrypted);
		try {
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			// Log.i(LOG_TAG+ "deleteing item: "+ uid+
			if (Stardust3d.DEBUG)
				System.out.println("dbstatus "
						+ response.getStatusLine().toString());
			if ((response.getStatusLine().toString()).equals("")) {
			}
			;
			HttpEntity entity = response.getEntity();
			// consume any lines to release connection
			is = entity.getContent();
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void getMissionDefs() {
		// get graphics stuff for Avatars
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String linebuffer = null;
		stream = null;
		int i = 0;
		String decodedstream = null;
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "24#" + Stardust3d.myCharacter.getUid();
		String encrypted = "";

		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "missiondef request:" + request
						+ encrypted);
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "missiondef data:"
						+ response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// " Charselect getting data for " + accountName);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}
		// convert response to string
		try {
			try {
				reader = new BufferedReader(new InputStreamReader(is), 2096);
				while ((stream = reader.readLine()) != null) {
					if (stream.trim().length() > 1) {
						stream = new String(mcrypt.decrypt(stream.trim()));
						stream = new String(stream.replace('~', ' '));
						decodedstream = new String(stream.trim());
					}
					i++;
				}
				is.close();
			} catch (Exception e) {
				if (Stardust3d.DEBUG)
					System.out.println(LOG_TAG
							+ "Everworld: Error getting missiondefs "
							+ e.toString());
				e.printStackTrace();
			}
			String currentline;
			if (Stardust3d.DEBUG)
				System.out
						.println("decrypted " + i + " stream" + decodedstream);
			i = 0;
			StringReader stringr = new StringReader(decodedstream);
			BufferedReader reader = new BufferedReader(stringr);
			while ((linebuffer = reader.readLine()) != null) {
				if (Stardust3d.DEBUG)
					System.out.println("decrypted " + i + " line" + linebuffer);
				if (linebuffer.length() > 1) {
					currentline = new String(linebuffer + "");
					String[] RowData = currentline.split("#");
					MissionDef thisMissionDef = new MissionDef();
					thisMissionDef.setUid(BigInteger.valueOf(Long
							.valueOf(RowData[0])));
					thisMissionDef.setTitle(RowData[1]);
					thisMissionDef.setText(RowData[2]);
					thisMissionDef.setType(RowData[3]);
					thisMissionDef.setConditions(RowData[4]);
					thisMissionDef.setRewards(RowData[5]);
					thisMissionDef.setGiver(BigInteger.valueOf(Long
							.valueOf(RowData[6])));
					thisMissionDef.setHandin(BigInteger.valueOf(Long
							.valueOf(RowData[7])));
					thisMissionDef.setPrereq(BigInteger.valueOf(Long
							.valueOf(RowData[8])));
					if (Stardust3d.DEBUG)
						System.out.println(i + thisMissionDef.getTitle());
					Stardust3d.missionDefs.add(thisMissionDef);
					i++;
				}
			}

		} catch (Exception e) {
			System.out.println(LOG_TAG + "Everworld: Error getting missiondef "
					+ e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public void getRandomHumanMaleName() {
		// get graphics stuff for ships
		MCrypt mcrypt = new MCrypt();
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		try {

			String request = REQUEST_ENCRYPTED;
			String data = "0#french";
			String encrypted = "";

			/* Encrypt */
			try {
				encrypted = (mcrypt.encrypt(data));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "male human random name request:"
						+ request + encrypted);
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "name data:"
						+ response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// " Charselect getting data for " + accountName);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 2096);
			line = null;
			int i = 0;

			while ((line = reader.readLine()) != null) {
				if (Stardust3d.DEBUG)
					System.out.println("encrypted " + i + " line" + line);
				if (line.trim().length() > 1) {
					line = new String(mcrypt.decrypt(line.trim()));
					if (Stardust3d.DEBUG)
						System.out.println("decrypted " + i + " line" + line);
					line = line.replace('~', ' ');
					line = line.trim();
					String[] RowData = line.split(",");

					Stardust3d.generatedFirstName = (RowData[0]);
					Stardust3d.generatedSurName = (RowData[1]);

					if (Stardust3d.DEBUG)
						System.out.println("random name "
								+ Stardust3d.generatedFirstName + " "
								+ Stardust3d.generatedSurName);
				}

				i++;
			}
			is.close();
		} catch (Exception e) {
			System.out.println(LOG_TAG + "Everworld: Error getting avatardef "
					+ e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public void updateDBShip() {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String encrypted = "";
		if (Stardust3d.DEEPDEBUG)
			System.out.println(LOG_TAG + " updating ship: ");
		String data = "12#" + Stardust3d.myCharacter.getSysx() + "#"
				+ Stardust3d.myCharacter.getSysy() + "#"
				+ Stardust3d.myCharacter.getSysz() + "#"
				+ Stardust3d.myCharacter.getUid() + "#"
				+ Stardust3d.myCharacter.getVelocity() + "#"
				+ Stardust3d.myCharacter.getPitchAngle() + "#"
				+ Stardust3d.myCharacter.getYawAngle() + "#"
				+ Stardust3d.myCharacter.getHitpoints();

		if (Stardust3d.DEEPDEBUG)
			System.out.println(LOG_TAG + "update ship plaintext:" + data);

		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (Stardust3d.DEEPDEBUG)
			System.out.println(LOG_TAG + "update request:" + request
					+ encrypted);

		try {

			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));

			if (Stardust3d.DEEPDEBUG)
				System.out.println("dbstatus "
						+ response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			// consume any lines to release connection
			is = entity.getContent();

		} catch (Exception e) {
			if (Stardust3d.DEEPDEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
			e.printStackTrace();
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 8192);
			line = null;

			if (Stardust3d.DEEPDEBUG)
				System.out.println(LOG_TAG
						+ " *****Everworld updateship data  " + is);
			while ((line = reader.readLine()) != null) {
				// have to use hash as delimeter or description field breaks csv
				// import
				String[] RowData = line.split("#");

				if (Stardust3d.DEEPDEBUG)
					System.out.println(LOG_TAG
							+ "Everworld: update ship data: " + RowData);
			}
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEEPDEBUG)
				System.out.println(LOG_TAG
						+ " Error in http connection when udating ship "
						+ e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public void getchat(int selectedChannel, boolean reset,
			boolean serverMessages) {
		// creat sql date for one week ago
		java.util.Date dt = new Date(System.currentTimeMillis()
				- (604800000*2 ));// 2 weeks in millseconds

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd%20HH:mm:ss");
		String currentTime = sdf.format(dt);
		// get chat
		String latestChat = "";
		if (!serverMessages) {
			// get last chat timestamp
			if (Stardust3d.chatList.size() > 0) {
				latestChat = "" + Stardust3d.chatList.get(0).getTimestamp();
			} else {

				latestChat = currentTime;
				// latestChat = "0000-00-00 00:00:00";
			}
			if (reset) {
				latestChat = currentTime;
				// latestChat = "0000-00-00 00:00:00";
			}
		} else {
			// get last chat timestamp
			if (Stardust3d.serverChatList.size() > 0) {
				latestChat = ""
						+ Stardust3d.serverChatList.get(0).getTimestamp();
			} else {

				latestChat = currentTime;
				// latestChat = "0000-00-00 00:00:00";
			}
			if (reset) {
				latestChat = currentTime;
				// latestChat = "0000-00-00 00:00:00";
			}
		}
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String linebuffer = null;
		stream = null;
		int i = 0;
		String decodedstream = null;
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "14#" + selectedChannel + "#" + latestChat + "#";
		String encrypted = "";

		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "get chat request:" + request
						+ encrypted);
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			if (Stardust3d.DEEPDEBUG)
				System.out.println(LOG_TAG + "get chat data:"
						+ response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// " Charselect getting data for " + accountName);
		} catch (Exception e) {

			System.out.println(LOG_TAG + " Error in http connection "
					+ e.toString());
		}
		// convert response to string
		try {
			try {
				reader = new BufferedReader(new InputStreamReader(is), 8192);
				while ((stream = reader.readLine()) != null) {
					if (stream.trim().length() > 1) {
						stream = new String(mcrypt.decrypt(stream.trim()));
						stream = new String(stream.replace('~', ' '));
						decodedstream = new String(stream.trim());
					}
					i++;
				}

			} catch (Exception e) {

				System.out.println(LOG_TAG + "Everworld: Error getting chat "
						+ e.toString());
				e.printStackTrace();
			}
			String currentline;
			if (Stardust3d.DEEPDEBUG)
				System.out
						.println("decrypted " + i + " stream" + decodedstream);
			i = 0;
			StringReader stringr = new StringReader(decodedstream);
			BufferedReader reader = new BufferedReader(stringr);
			while ((linebuffer = reader.readLine()) != null) {
				if (Stardust3d.DEBUG)
					System.out.println("decrypted " + i + " line" + linebuffer);
				if (linebuffer.length() > 1) {
					currentline = new String(linebuffer + "");
					String[] RowData = currentline.split("#");

					Chatline thischatline = new Chatline();
					thischatline.setName(RowData[0]);
					thischatline.setTimestamp(Timestamp.valueOf(RowData[1]));
					thischatline.setMessage(RowData[2]);
					thischatline.setChannelname(RowData[3]);
					if (reset == false && Stardust3d.chatChannel != 1) {
						checkforPlayerLogins(thischatline);
						checkforLevelUp(thischatline);
					}

					if (Stardust3d.DEEPDEBUG)
						System.out.println(i + thischatline.getName() + " "
								+ thischatline.getMessage());

					if (serverMessages) {
						Stardust3d.serverChatList.add(0, thischatline);
					} else {
						Stardust3d.chatList.add(0, thischatline);
					}
					i++;
				}
			}
			is.close();
		} catch (Exception e) {
			System.out.println(LOG_TAG + "Everworld: Error getting chat "
					+ e.toString());
			e.printStackTrace();
		}

	}

	private void checkforPlayerLogins(Chatline thischatline) {
		// if this is a playerjoin message
		if (thischatline.getChannelname().equals("Server")
				&& thischatline.getMessage().contains("has joined the game")) {
			SoundManager.playPlayerJoined();
		}
		// if this is a playerleave message
		if (thischatline.getChannelname().equals("Server")) {
			if (thischatline.getMessage().contains("has logged out")
					|| thischatline.getMessage().contains("has disconnected")) {
				SoundManager.playPlayerLeft();
			}
		}
	}
	private void checkforLevelUp(Chatline thischatline) {
		// if this is a playerjoin message
		System.out.println("levelcheck "+thischatline.getChannelname()+"  "+thischatline.getMessage());
		if (thischatline.getChannelname().equals("personal")
				&& thischatline.getMessage().contains("attained rank")) {
			Simulation.explosions
			.add(new Explosion(Ship.position,
					Explosion.RANKUP));
			SoundManager.playFanfare();
			
		}
		// if this is a playerleave message
		if (thischatline.getChannelname().equals("personal")) {
			if (thischatline.getMessage().contains("attained level")
					) {
				Simulation.explosions
				.add(new Explosion(Ship.position,
						Explosion.LEVELUP));
				SoundManager.playFanfare();
			}
		}
	}
	@Override
	public void setRunning(boolean b) {
		running = b;

	}

	// send non time sensetive requests to server
	protected void processRequest() {
		// only do if there are some requests in queue
		if (clientRequests.size() > 0) {
			// FIFO list of client requests
			// pop oldest request and process it.
			String currentline = clientRequests.get(0);
			clientRequests.remove(0);
			// decompose request string, delimited with hashes
			String[] RowData = currentline.split("#");
			// get the request type
			String command = RowData[0];

			if (command.equals("hitOpponent")) {
				if (Stardust3d.DEBUG)
					System.out.println("hitOpponent "
							+ BigInteger.valueOf(Long.valueOf(RowData[1]))
							+ " damage " + Integer.valueOf(RowData[2]));
				hitOpponent(BigInteger.valueOf(Long.valueOf(RowData[1])),
						Integer.valueOf(RowData[2]),
						Stardust3d.myCharacter.getUid());
			} else if (command.equals("sale")) {
				if (Stardust3d.DEBUG)
					System.out.println("sale " + " item "
							+ BigInteger.valueOf(Long.valueOf(RowData[1])));
				sell(BigInteger.valueOf(Long.valueOf(RowData[1])),
						BigInteger.valueOf(Long.valueOf(Stardust3d.myCharacter
								.getUid())), BigInteger.valueOf(450));
			} else if (command.equals("purchase")) {
				if (Stardust3d.DEBUG)
					System.out.println("purchase " + " item "
							+ BigInteger.valueOf(Long.valueOf(RowData[1])));
				purchase(BigInteger.valueOf(Long.valueOf(RowData[1])),
						BigInteger.valueOf(Long.valueOf(Stardust3d.myCharacter
								.getUid())), BigInteger.valueOf(450));
			} else if (command.equals("destroy")) {
				if (Stardust3d.DEBUG)
					System.out.println("destroy " + " item "
							+ BigInteger.valueOf(Long.valueOf(RowData[1])));
				inventoryOp(BigInteger.valueOf(Long.valueOf(RowData[1])), 0,
						BigInteger.valueOf(Long.valueOf(Stardust3d.myCharacter
								.getUid())));
			} else if (command.equals("movetowarehouse")) {
				if (Stardust3d.DEBUG)
					System.out.println("movetowarehouse " + " item "
							+ BigInteger.valueOf(Long.valueOf(RowData[1])));
				inventoryOp(BigInteger.valueOf(Long.valueOf(RowData[1])), 1,
						BigInteger.valueOf(Long.valueOf(Stardust3d.myCharacter
								.getUid())));
			} else if (command.equals("movetocargo")) {
				if (Stardust3d.DEBUG)
					System.out.println("movetocargo " + " item "
							+ BigInteger.valueOf(Long.valueOf(RowData[1])));
				inventoryOp(BigInteger.valueOf(Long.valueOf(RowData[1])), 2,
						BigInteger.valueOf(Long.valueOf(Stardust3d.myCharacter
								.getUid())));
			} else if (command.equals("movealltowarehouse")) {
				if (Stardust3d.DEBUG)
					System.out.println("movealltowarehouse ");
				inventoryOp(BigInteger.valueOf(0), 3, BigInteger.valueOf(Long
						.valueOf(Stardust3d.myCharacter.getUid())));
			}
		}
	}

	@Override
	public void newRequest(String requestString) {
		if (Stardust3d.DEBUG)
			System.out.println("new request" + requestString);
		// add client request to request FIFO
		clientRequests.add(requestString);
		// TODO Auto-generated method stub

	}

	@Override
	public String purchase(BigInteger itemID, BigInteger charID,
			BigInteger vendorID) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + " purchase: ");
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "19#" + charID + "#" + itemID + "#" + vendorID + "#";
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "purchase plaintext:" + data);
		String encrypted = "";
		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "purchase post request:" + request
					+ encrypted);
		try {
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));

			if (Stardust3d.DEBUG)
				System.out.println("dbstatus "
						+ response.getStatusLine().toString());
			if ((response.getStatusLine().toString()).equals("")) {
			}
			;
			HttpEntity entity = response.getEntity();
			// consume any lines to release connection
			is = entity.getContent();
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getVendorInventory(int characterId) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		try {

			String request = REQUEST_INVENTORY + characterId;

			HttpResponse response = httpclient.execute(new HttpPost(request));
			// Log.i(LOG_TAG+ " data:"+response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ " Stardust getting vendor inventory data" + is);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 8192);
			line = null;
			int i = 0;

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " getting vendor inventory data"
						+ is);
			while ((line = reader.readLine()) != null) {
				// have to use hash as delimiter or description field breaks csv
				// import
				Inventory thisItem = new Inventory();
				String[] RowData = line.split("#");

				thisItem.setSlot_id(Integer.valueOf(RowData[0]));
				thisItem.setInventoryid(Integer.valueOf(RowData[1]));
				thisItem.setitemUid(Integer.valueOf(RowData[2]));
				thisItem.setCategory(RowData[3]);
				thisItem.setSubcat(RowData[4]);
				thisItem.setItem(RowData[5]);
				thisItem.setMass(Integer.valueOf(RowData[6]));
				thisItem.setStacks(Integer.valueOf(RowData[7]));
				thisItem.setDescription(RowData[8]);
				thisItem.setContraband(Integer.valueOf(RowData[9]));
				thisItem.setRecipe(Integer.valueOf(RowData[10]));
				thisItem.setCapacity(Integer.valueOf(RowData[11]));
				thisItem.setLevel(Integer.valueOf(RowData[12]));
				thisItem.setIcon(RowData[13]);
				thisItem.setQuality(Integer.valueOf(RowData[14]));
				thisItem.setValue(Integer.valueOf(RowData[15]));
				thisItem.setCount(Integer.valueOf(RowData[18]));
				if (Stardust3d.DEBUG)
					System.out.println(i + thisItem.getItem());
				if (thisItem.getItem() != null) {
					Stardust3d.vendorInventory.add(thisItem);
					i++;
				}
			}
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ "Everworld: Error getting inventory for main "
						+ e.toString());
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public String sell(BigInteger itemID, BigInteger charID, BigInteger vendorID) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + " sell: ");
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "18#" + charID + "#" + itemID + "#" + vendorID + "#";
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "sell plaintext:" + data);
		String encrypted = "";
		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "sell post request:" + request
					+ encrypted);
		try {
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));

			if (Stardust3d.DEBUG)
				System.out.println("dbstatus "
						+ response.getStatusLine().toString());
			if ((response.getStatusLine().toString()).equals("")) {
			}
			;
			HttpEntity entity = response.getEntity();
			// consume any lines to release connection
			is = entity.getContent();
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String inventoryOp(BigInteger itemID, int operation,
			BigInteger charID) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		String data = "";

		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		// if operation is delete
		if (operation == 0) {
			data = "20#" + charID + "#" + itemID + "#";
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " delete item: ");
			// if operation is move to warehouse
		} else if (operation == 1) {
			data = "21#" + charID + "#" + itemID + "#";
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " movetowarehouse: ");
			// if operation is move to cargo
		} else if (operation == 2) {
			data = "22#" + charID + "#" + itemID + "#";
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " movetocargo: ");
		} else if (operation == 3) {
			data = "23#" + charID + "#";
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " movealltowarehouse: ");
		}
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "itemop plaintext:" + data);
		String encrypted = "";
		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "inventryop post request:" + request
					+ encrypted);
		try {
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));

			if (Stardust3d.DEBUG)
				System.out.println("dbstatus "
						+ response.getStatusLine().toString());
			if ((response.getStatusLine().toString()).equals("")) {
			}
			;
			HttpEntity entity = response.getEntity();
			// consume any lines to release connection
			is = entity.getContent();
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void getAvatarDefs() {
		// get graphics stuff for Avatars
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String linebuffer = null;
		stream = null;
		int i = 0;
		String decodedstream = null;
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "7#" + "some random salt";
		String encrypted = "";

		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "avatardef request:" + request
						+ encrypted);
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "avatardef data:"
						+ response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// " Charselect getting data for " + accountName);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}
		// convert response to string
		try {
			try {
				reader = new BufferedReader(new InputStreamReader(is), 2096);
				while ((stream = reader.readLine()) != null) {
					if (stream.trim().length() > 1) {
						stream = new String(mcrypt.decrypt(stream.trim()));
						stream = new String(stream.replace('~', ' '));
						decodedstream = new String(stream.trim());
					}
					i++;
				}
				is.close();
			} catch (Exception e) {
				if (Stardust3d.DEBUG)
					System.out.println(LOG_TAG
							+ "Everworld: Error getting avatardefs "
							+ e.toString());
				e.printStackTrace();
			}
			String currentline;
			if (Stardust3d.DEBUG)
				System.out
						.println("decrypted " + i + " stream" + decodedstream);
			i = 0;
			StringReader stringr = new StringReader(decodedstream);
			BufferedReader reader = new BufferedReader(stringr);
			while ((linebuffer = reader.readLine()) != null) {
				if (Stardust3d.DEBUG)
					System.out.println("decrypted " + i + " line" + linebuffer);
				if (linebuffer.length() > 1) {
					currentline = new String(linebuffer + "");
					String[] RowData = currentline.split("#");
					AvatarDef thisAvatarDef = new AvatarDef();
					thisAvatarDef.setUid(Integer.valueOf(RowData[0]));
					thisAvatarDef.setDescription(RowData[1]);
					thisAvatarDef.setImagename(RowData[2]);
					thisAvatarDef.setRace(RowData[3]);
					thisAvatarDef.setSex(RowData[4]);
					if (Stardust3d.DEBUG)
						System.out.println(i + thisAvatarDef.getDescription());
					Stardust3d.avatarList.add(thisAvatarDef);
					i++;
				}
			}

		} catch (Exception e) {
			System.out.println(LOG_TAG + "Everworld: Error getting avatardef "
					+ e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public void acceptMission(BigInteger uid) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "accepting mission: " + uid);
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "25#" + uid + "#" + Stardust3d.myCharacter.getUid();
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "accept mission plaintext:" + data);
		String encrypted = "";
		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "accept mission request:" + request
					+ encrypted);
		try {
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));

			HttpEntity entity = response.getEntity();
			// consume any lines to release connection
			is = entity.getContent();
			// Log.w(LOG_TAG+" *****Everworld NPC getting delete" +is);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
			e.printStackTrace();
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 8192);
			line = null;

			// for some reason first line is blank so skip
			line = reader.readLine();
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " *****Everworld accept mission  "
						+ is);
			while ((line = reader.readLine()) != null) {
				// have to use hash as delimeter or description field breaks csv
				// import
				String[] RowData = line.split("#");

				if (Stardust3d.DEBUG)
					System.out.println(LOG_TAG + "Everworld: accept mission: "
							+ RowData);

			}
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ " Error in http connection when accepting mission"
						+ e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public void getMissionLog(int completed) {
		// get graphics stuff for Avatars
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String linebuffer = null;
		stream = null;
		int i = 0;
		String decodedstream = null;
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "26#" + Stardust3d.myCharacter.getUid() + "#" + completed;
		String encrypted = "";

		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "missionlog request:" + request
						+ encrypted);
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "missionlog data:"
						+ response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			// if(Stardust3d.DEBUG) System.out.println(LOG_TAG+
			// " Charselect getting data for " + accountName);
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
		}
		// convert response to string
		try {
			try {
				reader = new BufferedReader(new InputStreamReader(is), 2096);
				while ((stream = reader.readLine()) != null) {
					if (stream.trim().length() > 1) {
						stream = new String(mcrypt.decrypt(stream.trim()));
						stream = new String(stream.replace('~', ' '));
						decodedstream = new String(stream.trim());
					}
					i++;
				}
				is.close();
			} catch (Exception e) {
				if (Stardust3d.DEBUG)
					System.out.println(LOG_TAG
							+ "Everworld: Error getting missionlog "
							+ e.toString());
				e.printStackTrace();
			}
			String currentline;
			if (Stardust3d.DEBUG)
				System.out
						.println("decrypted " + i + " stream" + decodedstream);
			i = 0;
			StringReader stringr = new StringReader(decodedstream);
			BufferedReader reader = new BufferedReader(stringr);
			while ((linebuffer = reader.readLine()) != null) {
				if (Stardust3d.DEBUG)
					System.out.println("decrypted " + i + " line" + linebuffer);
				if (linebuffer.length() > 1) {
					currentline = new String(linebuffer + "");
					String[] RowData = currentline.split("#");
					MissionLog thisMissionLog = new MissionLog();
					thisMissionLog.setUid(BigInteger.valueOf(Long
							.valueOf(RowData[0])));
					thisMissionLog.setTitle(RowData[1]);
					thisMissionLog.setText(RowData[2]);
					thisMissionLog.setType(RowData[3]);
					thisMissionLog.setConditions(RowData[4]);
					thisMissionLog.setRewards(RowData[5]);
					thisMissionLog.setGiver(BigInteger.valueOf(Long
							.valueOf(RowData[6])));
					thisMissionLog.setHandin(BigInteger.valueOf(Long
							.valueOf(RowData[7])));
					thisMissionLog.setPrereq(BigInteger.valueOf(Long
							.valueOf(RowData[8])));

					thisMissionLog.setCompletetext(RowData[9]);
					thisMissionLog.setFirstname(RowData[10]);
					thisMissionLog.setSurname(RowData[11]);
					thisMissionLog.setFaction(RowData[12]);
					thisMissionLog.setX(Integer.valueOf(RowData[13]));
					thisMissionLog.setY(Integer.valueOf(RowData[14]));
					thisMissionLog.setZ(Integer.valueOf(RowData[15]));
					thisMissionLog.setSysname(RowData[16]);
					thisMissionLog.setInprogress(Integer.valueOf(RowData[17]));
					if (Stardust3d.DEBUG)
						System.out.println(i + thisMissionLog.getTitle());
					Stardust3d.myMissions.add(thisMissionLog);
					i++;
				}
			}

		} catch (Exception e) {
			System.out.println(LOG_TAG + "Everworld: Error getting missionlog "
					+ e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public String logout(int mcharacter) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + " posting logout: ");
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "27#" + mcharacter;
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "new logout plaintext:" + data);
		String encrypted = "";
		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "new logout post request:" + request
					+ encrypted);
		try {
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			// Log.i(LOG_TAG+ "deleteing item: "+ uid+
			if (Stardust3d.DEBUG)
				System.out.println("dbstatus "
						+ response.getStatusLine().toString());
			if ((response.getStatusLine().toString()).equals("")) {
			}
			;
			HttpEntity entity = response.getEntity();
			// consume any lines to release connection
			is = entity.getContent();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
			e.printStackTrace();
		}
		// convert response to string
		try {
			reader = new BufferedReader(new InputStreamReader(is), 8192);
			line = null;
			int i = 0;

			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " logout  " + is);
			while ((line = reader.readLine()) != null) {
				// have to use hash as delimeter or description field breaks csv
				// import
				if (line.trim().length() > 1) {
					line = new String(mcrypt.decrypt(line.trim()));
					if (Stardust3d.DEBUG)
						System.out.println("decrypted " + i + " line" + line);
					line = line.replace('~', ' ');
					line = line.trim();
					String[] RowData = line.split("#");
					result = RowData[0];
				}
				if (Stardust3d.DEBUG)
					System.out.println(LOG_TAG + "logout result: " + result);
				i++;
			}
			is.close();

		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG
						+ " Error in http connection when posting chat "
						+ e.toString());
			e.printStackTrace();
		}

		return "ok";
	}

	@Override
	public void changeShip(int charID, int inventoryID) {
		HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
		InputStream is = null;
		String result = "";
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + " changing ship: ");
		MCrypt mcrypt = new MCrypt();
		String request = REQUEST_ENCRYPTED;
		String data = "28#" + charID + "#" + inventoryID + "#";
		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "changeship plaintext:" + data);
		String encrypted = "";
		/* Encrypt */
		try {
			encrypted = (mcrypt.encrypt(data));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (Stardust3d.DEBUG)
			System.out.println(LOG_TAG + "changeship request:" + request
					+ encrypted);
		try {
			HttpResponse response = httpclient.execute(new HttpPost(request
					+ encrypted));
			// Log.i(LOG_TAG+ "deleteing item: "+ uid+
			if (Stardust3d.DEBUG)
				System.out.println("dbstatus "
						+ response.getStatusLine().toString());
			if ((response.getStatusLine().toString()).equals("")) {
			}
			;
			HttpEntity entity = response.getEntity();
			// consume any lines to release connection
			is = entity.getContent();
			is.close();
		} catch (Exception e) {
			if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + " Error in http connection "
						+ e.toString());
			e.printStackTrace();
		}

	}

}
