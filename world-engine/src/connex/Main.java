/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package connex;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import com.badlogic.gdx.math.Vector3;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class Main {
	public static final String MYSQLURL = "jdbc:mysql://127.0.0.1:3306/test";
	public static final String serviceUser = "stardustagent";
	public static final String servicePass = "observe4024crisis";
	public static final boolean DEBUG = true;
	static GroundItem Land[] = new GroundItem[20];
	public static byte[][] routeMap = new byte[129][129];
public static List <levelDef> levelslist=new ArrayList<levelDef>();
	static int landSize = 64;
	static Location[][] LandScape = new Location[64][64];
	static Connection con;
	static Timer aitimer;
	static UserInterface myWindow;
	static String myLog; 
	
	public static void start(final UserInterface window){
		myWindow=window;
		System.out.println("start main");
		//UI.createAndShowGUI();
		// Register the JDBC driver for MySQL.
					try {
						Class.forName("com.mysql.jdbc.Driver");
						// Define URL of database server
										
						// Get a connection to the database
						con = DriverManager.getConnection(MYSQLURL, serviceUser,
								servicePass);
				
						// Display URL and connection information
						System.out.println("URL: " + MYSQLURL);
						System.out.println("Connection: " + con);
				
						// Get a Statement object
					} catch (MySQLIntegrityConstraintViolationException e) {
						System.out.println("fail");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
					
		populateLevelist();
		// loop forever every .5 secs running NPC moves
		Timer sectimer = new Timer();
		sectimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				
				window.setLogString(myLog);
				System.out.println("start NPC tasks");

				NPCtasks.regenHiveShieldDisruptors();
				PlayerTasks.checkDisconnects();
				try {
					VendorTasks.restock();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 0, 1000);

		// loop forever every.5secs check for new routepath
		 aitimer = new Timer();
		aitask(aitimer);	
	}
	


	/**
	 * @param timer
	 */
	private static void aitask(Timer timer) {
						

			timer.scheduleAtFixedRate(new TimerTask() {
				private int ticks;

				public void run() {
					
					try {
						//Class.forName("com.mysql.jdbc.Driver");
					
					System.out.println("start NPC ai");
						
						final Statement stmt;
						final Statement updatestmt;
						final Statement qrystmt;
						String query;
						
						//con = DriverManager.getConnection(MYSQLURL, serviceUser,
						//		servicePass);
						updatestmt = con.createStatement();
						stmt = con.createStatement();
						qrystmt = con.createStatement();		
						// surnameUid=1000000 =hive ai scout
						ResultSet rs = stmt
								.executeQuery("SELECT * FROM `npc` WHERE (surnames_uid=1000000)and hitpoints >0");
						// for each Hive ai scout
						while (rs.next()) {
							
							if (rs.getString("uid") != null) {
								float npcx = rs.getFloat("sysx");
								float npcy = rs.getFloat("sysy");
								float npcz = rs.getFloat("sysz");
								BigInteger npcUID = BigInteger.valueOf(Long
										.valueOf(rs.getString("uid")));
								int locx = rs.getInt("x");
								int locy = rs.getInt("y");
								int locz = rs.getInt("z");
								// find nearest player in range
								query = "select uid,firstname, surname, sysx, sysy, sysz,"
										+ " MIN(ABS(sysx-"
										+ npcx
										+ ")) +MIN(ABS(sysy-"
										+ npcy
										+ ")) +MIN(ABS(sysz-"
										+ npcz
										+ ")) as dist"
										+ " from player_char"
										+ " where (x="
										+ locx
										+ " and y="
										+ locy
										+ " and z="
										+ locz
										+ " and status=1"
										+ ")"
										+ " group by sysx,sysy,sysz"
										+ " order by dist limit 1";

								//if (DEBUG)	System.out.println(query);
								ResultSet playerdata = qrystmt
										.executeQuery(query);

								Vector3 npcVector = new Vector3(npcx, npcy,
										npcz);
								while (playerdata.next()) {
									float playerx = playerdata.getFloat("sysx");
									float playery = playerdata.getFloat("sysy");
									float playerz = playerdata.getFloat("sysz");
									int playerUID = playerdata.getInt("uid");
									Vector3 playerVector = new Vector3(playerx,
											playery, playerz);
									Vector3 direction = new Vector3(
											playerVector.sub(npcVector));
									double length = direction.len();
									if (DEBUG)
										System.out.println(length);
									if (length < 2000) {
										if (DEBUG)
											System.out
													.println("moving to player");
										double npcPitch = Math
												.toDegrees(Math
														.asin((double) (direction.y / length)));
										double npcYaw;
										if (Math.abs(direction.z) < 0.00001) {
											// special case
											if (direction.x > 0) {
												npcYaw = Math.PI / 2.0;
											} else if (direction.x < 0) {
												npcYaw = -Math.PI / 2.0;
											} else {
												npcYaw = 0.0;
											}
										} else {
											npcYaw = Math.atan2(direction.x,
													direction.z);
										}

										npcYaw = Math.toDegrees(npcYaw);
										direction = (direction.nor().mul(10f));
										query = "update npc set pitchangle="
												+ -npcPitch
												+ ", yawangle="
												+ npcYaw
												+ ",lastupdate=(select Now() from landscape limit 1)"
												+ ",sysx=sysx+" + direction.x
												+ ",sysy=sysy+" + direction.y
												+ ",sysz=sysz+" + direction.z
												+ "where uid=" + npcUID;
										if (DEBUG)
											System.out.println(query);
										int affected = updatestmt
												.executeUpdate(query);
										//attack players in range
										if (length < 200 && ticks == 1) {
											query = "update player_char set hitpoints=hitpoints-50, lastattackeruid=-"
													+ npcUID
													+ ",lastupdate=(select Now() from landscape limit 1) where uid="
													+ playerUID;
											if (DEBUG)
												System.out.println(query);
											int newaffected = updatestmt
													.executeUpdate(query);
										}
									}
									
								}

							}
						}

					
					ticks++;
					if (ticks == 10)
						ticks = 0;
				//	con.close();
					stmt.close();
					 updatestmt.close();
						  qrystmt.close();
					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}, 0, 200);

			 
		
	}

	private static int findroute(int userid, byte sx, byte sy, byte ex, byte ey) {
		//
		int routeid;
		// set up search area slightly bigger than area bounded by start and
		// finish
		// just in case we have to go away from endpoint before we can find a
		// path
		int lowerx = sx - 2;
		int topx = ex + 2;
		int lowery = sy - 2;
		int topy = ey + 2;
		// make sure search area is not off map
		if (lowerx < 0)
			lowerx = 0;
		if (lowery < 0)
			lowery = 0;
		if (topx > 64)
			lowerx = 64;
		if (topy > 64)
			lowerx = 64;
		for (int x = lowerx; x < topy; x++) {

			for (int y = lowery; y < topy; y++) {
				// System.out.println("set routemap x= " + x + "y= " + y);
				// mark up links
				// set each system to 1
				routeMap[x * 2][y * 2] = 2; // systems LandScape[x][y]
				if (LandScape[x][y].getLeftlink() == 1)
					routeMap[x * 2][y * 2 + 1] = 1;
				if (LandScape[x][y].getDownlink() == 1)
					routeMap[x * 2 + 1][y * 2] = 1;
			}
		}
		drawmap(sx, sy, ex, ey);
		System.out.println("find path");
		// request route with co-ords doubled to account for system links
		routeid = PathFinder.setmap(userid, routeMap, (byte) (sx * 2),
				(byte) (sy * 2), (byte) (ex * 2), (byte) (ey * 2));

		return routeid;
	}

	private static void initlandscape() {
		try {
			Statement stmt;
			String query = "select x,y,z,leftlink,downlink from systems";
			// Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");
			// Define URL of database server
			String url = "jdbc:mysql://localhost:3306/test";
			// Get a connection to the database
			Connection con = DriverManager.getConnection(url, "root",
					"universal40");
			// Display URL and connection information
			System.out.println("URL: " + url);
			System.out.println("Connection: " + con);
			// Get a Statement object
			stmt = con.createStatement();
			// System.out.println("inserting "+limit+ " NPCs" );
			System.out.println("build map");
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				// System.out.println("x " + rs.getInt("x") + " y "
				// + rs.getInt("y") + " LL " + rs.getInt("leftlink")
				// + " dl  " + rs.getInt("downlink"));
				LandScape[rs.getInt("x") - 1][rs.getInt("y") - 1]
						.setLeftlink((byte) rs.getInt("leftlink"));
				LandScape[rs.getInt("x") - 1][rs.getInt("y") - 1]
						.setDownlink((byte) rs.getInt("downlink"));

			}
			con.close();

			// print out landscape
			/*
			 * for(int i = 0; i < LandScape.length; i++){ for(int j = 0; j <
			 * LandScape[0].length; j++) System.out.print(LandScape[i][j] +
			 * " "); System.out.println(); }
			 */

		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("fail");
		} catch (Exception e) {
			e.printStackTrace();
		}// end catch
	}

	private static void drawmap(byte sx, byte sy, byte ex, byte ey) {
		// draw map with star and end points
		for (int i = 0; i < routeMap.length; i++) {
			for (int j = 0; j < routeMap.length; j++)
				if (i == sx * 2 && j == sy * 2) {
					System.out.print("S ");
				} else if (i == ex * 2 && j == ey * 2) {
					System.out.print("E ");

				} else {
					System.out.print(routeMap[i][j] + " ");
				}
			System.out.println();
		}
	}

	private static void checkrequests() {
		try {
			Statement stmt;
			String query = "select * from pathrequest where inprogress = 0 limit 1";
			// Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");
			// Define URL of database server
			String url = "jdbc:mysql://localhost:3306/test";
			// Get a connection to the database
			Connection con = DriverManager.getConnection(url, "root",
					"universal40");
			// Display URL and connection information
			System.out.println("URL: " + url);
			System.out.println("Connection: " + con);
			// Get a Statement object
			stmt = con.createStatement();
			System.out.println("check routerequests");
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				byte startx = (byte) rs.getInt("startx");
				byte starty = (byte) rs.getInt("starty");
				byte endx = (byte) rs.getInt("endx");
				byte endy = (byte) rs.getInt("endy");
				int uid = rs.getInt("uid");
				int userid = rs.getInt("userid");
				query = "update pathrequest set inprogress= 1 where uid ="
						+ uid;
				int result = stmt.executeUpdate(query);

				int routeid = findroute(userid, startx, starty, endx, endy);

			}
			
			con.close();
			

		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("fail");
		} catch (Exception e) {
			e.printStackTrace();
		}// end catch
	}

	public static void populateLevelist() {
		myLog=myLog +(" populating level list /n");
		
		try {
			Statement stmt;
	
			
			
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select *  from levels order by uid");
			
			while (rs.next()) {
				levelDef thisLevelDef=new levelDef();
				thisLevelDef.rank = Integer.valueOf(rs.getString("rank"));
				thisLevelDef.level = Integer.valueOf(rs.getString("level"));
				thisLevelDef.exp = Integer.valueOf(rs.getString("exp"));
					levelslist.add(thisLevelDef);
			}
			for (int i=0; i<Main.levelslist.size();i++)
			{
				System.out.println(Main.levelslist.get(i).rank+
						" "+Main.levelslist.get(i).level+
						" "+Main.levelslist.get(i).exp);
			}
		
	
			//con.close();
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("fail");
		} catch (Exception e) {
			e.printStackTrace();
		}// end catch
	}// end method totallyRandom



	public static void stop() {
		// TODO Auto-generated method stub
		aitimer.cancel();
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}