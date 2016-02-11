/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package connex;

import java.sql.*;
import java.util.Random;
import java.lang.Math;
import java.math.BigInteger;

import com.badlogic.gdx.math.Vector3;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class NPCtasks {
	static Random rng = new Random();
	private static int affected;

	public static void totallyRandom(int limit) {
		System.out.println("start generate random land");
		try {
			Statement stmt;
			ResultSet rs;

			// Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");

			// Define URL of database server
			String url = Main.MYSQLURL;

			// Get a connection to the database
			Connection con = DriverManager.getConnection(url, "root",
					"universal40");

			// Display URL and connection information
			System.out.println("URL: " + url);
			System.out.println("Connection: " + con);

			// Get a Statement object
			stmt = con.createStatement();

			int x = 1;
			int z = 1;
			String groundtype = "";
			int RandomNumber = rng.nextInt(7);

			System.out.println("inserting groundtypes" + " with " + limit + "x"
					+ limit + "dimensions");

			// Insert sand into limit sized grid

			for (int xread = 0; xread < limit - 1; xread++) {
				for (int yread = 0; yread < limit - 1; yread++) {

					RandomNumber = rng.nextInt(7);
					groundtype = Main.Land[RandomNumber].get_ground();
					System.out
					.printf("x " + xread + " y " + yread + groundtype);
					stmt.executeUpdate("UPDATE landscape SET ground ='"
							+ groundtype + "' where x=" + (xread + 1)
							+ " and y=" + (yread + 1));
				}
				System.out.println();
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}// end catch
	}// end method totallyRandom

	// generate heightmap using Diamond-square fractal algorithm
	public static void heightMap(final int DATA_SIZE) {
		System.out.println("start generate Diamond-square land");
		// DATA_SIZE = size of grid to generate, note this must be a
		// value 2^n+1

		// an initial seed value for the corners of the data
		final double SEED = 1000.0;
		double[][] data = new double[DATA_SIZE][DATA_SIZE];
		// seed the data
		data[0][0] = data[0][DATA_SIZE - 1] = data[DATA_SIZE - 1][0] = data[DATA_SIZE - 1][DATA_SIZE - 1] = SEED;

		double h = 500.0;// the range (-h -> +h) for the average offset
		Random r = new Random();// for the new value in range of h
		// side length is distance of a single square side
		// or distance of diagonal in diamond
		for (int sideLength = DATA_SIZE - 1;
				// side length must be >= 2 so we always have
				// a new value (if its 1 we overwrite existing values
				// on the last iteration)
				sideLength >= 2;
				// each iteration we are looking at smaller squares
				// diamonds, and we decrease the variation of the offset
				sideLength /= 2, h /= 2.0) {
			// half the length of the side of a square
			// or distance from diamond center to one corner
			// (just to make calcs below a little clearer)
			int halfSide = sideLength / 2;

			// generate the new square values
			for (int x = 0; x < DATA_SIZE - 1; x += sideLength) {
				for (int y = 0; y < DATA_SIZE - 1; y += sideLength) {
					// x, y is upper left corner of square
					// calculate average of existing corners
					double avg = data[x][y] + // top left
							data[x + sideLength][y] + // top right
							data[x][y + sideLength] + // lower left
							data[x + sideLength][y + sideLength];// lower right
					avg /= 4.0;

					// center is average plus random offset
					data[x + halfSide][y + halfSide] =
							// We calculate random value in range of 2h
							// and then subtract h so the end value is
							// in the range (-h, +h)
							avg + (r.nextDouble() * 2 * h) - h;
				}
			}

			// generate the diamond values
			// since the diamonds are staggered we only move x
			// by half side
			// NOTE: if the data shouldn't wrap then x < DATA_SIZE
			// to generate the far edge values
			for (int x = 0; x < DATA_SIZE - 1; x += halfSide) {
				// and y is x offset by half a side, but moved by
				// the full side length
				// NOTE: if the data shouldn't wrap then y < DATA_SIZE
				// to generate the far edge values
				for (int y = (x + halfSide) % sideLength; y < DATA_SIZE - 1; y += sideLength) {
					// x, y is center of diamond
					// note we must use mod and add DATA_SIZE for subtraction
					// so that we can wrap around the array to find the corners
					double avg = data[(x - halfSide + DATA_SIZE - 1)
					                  % (DATA_SIZE - 1)][y]
					                		  + // left of center
					                		  data[(x + halfSide) % (DATA_SIZE - 1)][y] + // right
					                		  // of
					                		  // center
					                		  data[x][(y + halfSide) % (DATA_SIZE - 1)] + // below
					                		  // center
					                		  data[x][(y - halfSide + DATA_SIZE - 1)
					                		          % (DATA_SIZE - 1)]; // above center
					avg /= 4.0;

					// new value = average plus random offset
					// We calculate random value in range of 2h
					// and then subtract h so the end value is
					// in the range (-h, +h)
					avg = avg + (r.nextDouble() * 2 * h) - h;
					// update value for center of diamond
					data[x][y] = (avg / 5);

					// wrap values on the edges, remove
					// this and adjust loop condition above
					// for non-wrapping values.
					if (x == 0)
						data[DATA_SIZE - 1][y] = avg;
					if (y == 0)
						data[x][DATA_SIZE - 1] = avg;
				}
			}
		}
		// write generated z to database
		try {
			Statement stmt;
			// Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");

			// Define URL of database server
			String url = Main.MYSQLURL;

			// Get a connection to the database
			Connection con = DriverManager.getConnection(url, "root",
					"universal40");

			// Display URL and connection information
			System.out.println("URL: " + url);
			System.out.println("Connection: " + con);

			// Get a Statement object
			stmt = con.createStatement();
			System.out.println("populating altitude");
			// print out the data
			for (int xread = 0; xread < DATA_SIZE - 1; xread++) {
				for (int yread = 0; yread < DATA_SIZE - 1; yread++) {

					System.out.printf("x " + xread + " y " + yread + "%8.3f ",
							data[xread][yread]);
					stmt.executeUpdate("UPDATE landscape SET z ="
							+ data[xread][yread] + " where x=" + (xread + 1)
							+ " and y=" + (yread + 1));
				}
				System.out.println();
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}// end catch

		System.out.println("generate Diamond-square land");
	}

	// start method Fillblank
	// populates landscape table with empty ground tiles

	public static void Fillblank(int limit) {
		System.out.println("start Fillblank");

		try {
			Statement stmt;
			// Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");

			// Define URL of database server
			String url = Main.MYSQLURL;

			// Get a connection to the database
			Connection con = DriverManager.getConnection(url, "root",
					"universal40");

			// Display URL and connection information
			System.out.println("URL: " + url);
			System.out.println("Connection: " + con);

			// Get a Statement object
			stmt = con.createStatement();
			int x = 1;
			int z = 1;
			System.out.println("creating land with " + limit + "x" + limit
					+ "dimensions");
			do {
				int y = 1;
				do {
					// Insert sand into limit sized grid
					stmt.executeUpdate("INSERT INTO landscape(ground, x, y, z) VALUES('sand',"
							+ x + "," + y + "," + z + ")");
					y++;
				} while (y < limit);
				x++;
			} while (x < limit);

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}// end catch

		System.out.println("end Fillblank");

	}

	public static void walknpcs() {
		System.out.println("updating NPC locations");
		try {
			Statement stmt, updatestmt;

			// Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");

			// Define URL of database server
			String url = Main.MYSQLURL;

			// Get a connection to the database
			Connection con = DriverManager.getConnection(url, "root",
					"universal40");

			// Display URL and connection information
			System.out.println("URL: " + url);
			System.out.println("Connection: " + con);
			String query;
			// Get a Statement object
			updatestmt = con.createStatement();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select UID, x,y  from npc");
			while (rs.next()) {
				int mUID = Integer.valueOf(rs.getString("UID"));
				int mx = Integer.valueOf(rs.getString("x"));
				int my = Integer.valueOf(rs.getString("y"));
				System.out.println("UID" + mUID + " x " + mx + " y " + my);
				double r = Math.random();
				if (r < 0.25 && mx > 1)
					mx--;
				else if (r < 0.50 && mx < 63)
					mx++;
				else if (r < 0.75 && my > 1)
					my--;
				else if (r < 1.00 && my < 63)
					my++;
				query = "UPDATE  npc set  x=" + mx + ", y=" + my
						+ ", z = (SELECT z from landscape where x=" + mx
						+ " and y=" + my + ") WHERE UID=" + mUID;
				System.out.println(query);
				// update npc set npc.x= mx, npc.y= my,npc.z = (SELECT z from
				// landscape where x=1 and y=2) where UID=176
				int affected = updatestmt.executeUpdate(query);

			}

			con.close();
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("fail");
		} catch (Exception e) {
			e.printStackTrace();
		}// end catch
	}// end method totallyRandom

	public static void regenHiveShieldDisruptors() {
		System.out.println("regen hive shield disruptors");
		Main.myLog=Main.myLog+"regen hive shield disruptors\n";
		try {
			Statement stmt, updatestmt, qrystmt;


			String query;
			// Get a Statement objects
			updatestmt = Main.con.createStatement();
			stmt = Main.con.createStatement();
			qrystmt = Main.con.createStatement();
			int lootID; 
					
			query="delete FROM npc where shipname='dead' and lastupdate < (select now() -interval 2 minute from landscape limit 1)";
			System.out.println(query);
			 affected = updatestmt.executeUpdate(query);		
			// find out if any actors are at < 0 hitpoints (have been killed)
			ResultSet rs = stmt
					.executeQuery("select *  from actors where hitpoints<=0 and shipname !='dead'");
			while (rs.next()) {
				if (rs.getString("uid") != null) {
					// find out who the last attacker was
					int mUID = Integer.valueOf(rs.getString("lastattackeruid"));
					// get the name of the actor
					String npcfirstname = rs.getString("firstname");
					String npcsurname = rs.getString("surname");
					String faction = rs.getString("faction");
					// get exp value of actor
					int expValue = Integer.valueOf(rs.getString("expvalue"));
					// get $D value of actor
					int creditValue = Integer.valueOf(rs
							.getString("creditvalue"));
					// get the uid of the actor
					int auid = Integer.valueOf(rs.getString("uid"));
					// convert last attacker uid to name
					ResultSet agressorRS = qrystmt
							.executeQuery("SELECT CONCAT(firstname, ' ', surname)as name FROM actors where uid="
									+ mUID);
					while (agressorRS.next()) {
						String agressor = agressorRS.getString("name");
						// post chat message to solar system to say who killed
						// what
						query = "INSERT INTO chatmessages (`chaituid`, `chatmessage`, `senderid`, `chatchannel`) "
								+ " VALUES (NULL,'"
								+ agressor
								+ " destroyed "
								+ npcfirstname
								+ " "
								+ npcsurname
								+ ".','48','0')";
						System.out.println(query);
						Main.myLog=Main.myLog+query+"\n";
						 affected = updatestmt.executeUpdate(query);

						// if actor is an NPC, destroy record
						
						if (!(faction.equals("player"))) {
							// remove minus from uid
							auid = -auid;
							//query = "DELETE FROM npc where uid=" + auid;
							query = "update npc set shipname='dead' where uid=" + auid;
							System.out.println(query);
							affected = updatestmt.executeUpdate(query);
							awardPlayerForNPCKill(updatestmt, Main.con, mUID,
									npcfirstname, npcsurname, expValue, creditValue);
							
						}else if (faction.equals("player")){
							// if actor is a player, destroy thier ship and send
							// them to nearest station, penalise by deducting credits and cargo
							query = "update player_char set hitpoints=1000, status=0, credits=credits-1000 where uid=" + auid;
							System.out.println(query);
							affected = updatestmt.executeUpdate(query);
							auid = -auid;
							query = "INSERT INTO chatmessages (`chaituid`, `chatmessage`, `senderid`, `chatchannel`) "
									+ " VALUES (NULL,'"
									+ " You lose "
									+ 1000
									+ " $D and all your cargo for dying to an NPC"
									 + ".','48','" + auid + "')";
							System.out.println(query);
							affected = updatestmt.executeUpdate(query);
							// remove minus from uid
							auid = -auid;
							query="delete from inventory where characteruid="+auid+" and slot_id=0";
							System.out.println(query);
							affected = updatestmt.executeUpdate(query);
						}

					}
				}
			}
			respawnShieldDisruptor(stmt, updatestmt);
			respawnScout(stmt, updatestmt);
			stmt.close();
			 updatestmt.close();
				  qrystmt.close();
			
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("fail");
		} catch (Exception e) {
			e.printStackTrace();
		}// end catch
	}// end method regenHiveShieldDisruptors

	/**
	 * @param stmt
	 * @param updatestmt
	 * @throws SQLException
	 */
	private static void respawnShieldDisruptor(Statement stmt,
			Statement updatestmt) throws SQLException {
		String query;
		ResultSet rs;
		// surname id 1 is a shield disruptor hive ai
		rs = stmt
				.executeQuery("SELECT count(*)as count FROM `npc` WHERE (surnames_uid=1)and shipname!='dead'");
		while (rs.next()) {
			int count = Integer.valueOf(rs.getString("count"));

			System.out.println("count " + count);
			if (count <= 20) {
				double s = Math.random();
				double t = Math.random();
				double u = s * Math.PI * 2;
				double z = t * 2 - 1;
				double r = Math.sqrt(1 - z * z);
				double x = r * Math.cos(u);
				double y = r * Math.sin(u);
//point disruptor at station
				Vector3 npcVector = new Vector3((float)(x),(float)(y),(float)(z));
				Vector3 targetVector = new Vector3(0,
						0, 0);
				Vector3 direction = new Vector3(
						targetVector.sub(npcVector));
				double length = direction.len();
				System.out.println(length);
				
				double npcPitch = -Math.toDegrees(Math
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
				npcYaw= Math.toDegrees(npcYaw);
				
				
				query = "INSERT INTO npc (`UID`, `race`, `x`, `y`, `z`, `is_static`, `firstnames_uid`, `surnames_uid`,"
						+ " `sysx`, `sysy`, `sysz`, `hitpoints`, `faction`, `stamina`, `intelligence`, `social`, `dexterity`, `leadership`, `recuperation`, `exp`,"
						+ "`firstattackeruid`,`lastattackeruid`,`expvalue`,`creditvalue`,`status`,`yawangle`,`pitchangle`,`shipname`,`lastupdate`)"
						+ " VALUES (NULL, 'aih', '1', '1', '10', '1', '7168', '1', '"
						+ x
						* 1500
						+ "', '"
						+ y
						* 1500
						+ "', '"
						+ z
						* 1500
						+ "', '500', 'aihive', '1', '1', '1', '1', '1', '1', '0','0','0','100','1000','1','"+npcYaw+"','" +npcPitch+ "','shielddisruptor',(select NOW() from landscape limit 1))";
				System.out.println(query);
				int affected = updatestmt.executeUpdate(query);

			}
		}
	}

	/**
	 * @param updatestmt
	 * @param con
	 * @param mUID
	 * @param npcfirstname
	 * @param npcsurname
	 * @param expValue
	 * @param creditValue
	 * @throws SQLException
	 */
	private static void awardPlayerForNPCKill(Statement updatestmt,
			Connection con, int mUID, String npcfirstname, String npcsurname,
			int expValue, int creditValue) throws SQLException {
		String query;
		int lootID;
		int affected;
		// award aggressor with $D, exp, loot and update kills
		lootID = calculateLoot(con);
		System.out.println("loot id" + lootID);
		query = "INSERT INTO inventory (`slot_id`, `characteruid`, `item_ID`) "
				+ " VALUES (0,'" + mUID + "','" + lootID + "')";
		System.out.println(query);
		affected = updatestmt.executeUpdate(query);

		// add some rng to cash
		creditValue = (int) getGaussian(creditValue,
				creditValue / 10);
		// add some rng to exp
		expValue = (int) getGaussian(expValue, expValue / 10);
		query = "UPDATE player_char set credits=credits+"
				+ creditValue + ",exp=exp+" + expValue
				+ ",kills=kills+1" + " where uid=" + mUID;
		System.out.println(query);
		affected = updatestmt.executeUpdate(query);

		// inform player of gains
		// invert agressor id to get private chat channel id
		mUID = -mUID;
		query = "INSERT INTO chatmessages (`chaituid`, `chatmessage`, `senderid`, `chatchannel`) "
				+ " VALUES (NULL,'"
				+ " You receive "
				+ expValue
				+ " exp and "
				+ creditValue
				+ " $D for destroying "
				+ npcfirstname
				+ " "
				+ npcsurname + ".','48','" + mUID + "')";
		System.out.println(query);
		affected = updatestmt.executeUpdate(query);
	}

	private static int calculateLoot(Connection con) {
		int depth = 0;
		int result=0;
		result = oneintenloot(con, depth, result);
		
		return result;
	}

	/**
	 * @param con
	 * @param depth
	 * @param result
	 * @return
	 */
	private static int oneintenloot(Connection con, int depth, int result) {
		ResultSet itemRS;
		Statement qrystmt;
		System.out.println(depth);
		try {
			qrystmt = con.createStatement();
			if (rng.nextInt(99) < 75) {
				// select uid of random scar

				itemRS = qrystmt
						.executeQuery("SELECT * FROM item where scarcity="+depth+" and nodrop=0 ORDER BY RAND() LIMIT 0,1;");
				while (itemRS.next()) {
					result = itemRS.getInt("uid");

				}
				// got lucky, escalate loot
			} else {
				depth++;
				result=oneintenloot(con, depth, result);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private static double getGaussian(double aMean, double aVariance) {
		return aMean + rng.nextGaussian() * aVariance;
	}

	/**
		 * @param stmt
		 * @param updatestmt
		 * @throws SQLException
		 */
		private static void respawnScout(Statement stmt,
				Statement updatestmt) throws SQLException {
			String query;
			ResultSet rs;
			// surname id 1 is a shield disruptor hive ai
			rs = stmt
					.executeQuery("SELECT count(*)as count FROM `npc` WHERE (surnames_uid=1000000)and shipname!='dead'");
			while (rs.next()) {
				int count = Integer.valueOf(rs.getString("count"));
	
				System.out.println("count " + count);
				if (count <= 5) {
					double s = Math.random();
					double t = Math.random();
					double u = s * Math.PI * 2;
					double z = t * 2 - 1;
					double r = Math.sqrt(1 - z * z);
					double x = r * Math.cos(u);
					double y = r * Math.sin(u);

					query = "INSERT INTO npc (`UID`, `race`, `x`, `y`, `z`, `is_static`, `firstnames_uid`, `surnames_uid`,"
							+ " `sysx`, `sysy`, `sysz`, `hitpoints`, `faction`, `stamina`, `intelligence`, `social`, `dexterity`, `leadership`, `recuperation`, `exp`,"
							+ "`firstattackeruid`,`lastattackeruid`,`expvalue`,`creditvalue`,`status`,`yawangle`,`pitchangle`,`shipname`,`lastupdate`)"
							+ " VALUES (NULL, 'aih', '1', '1', '10', '1', '7168', '1000000', '"
							+ x
							* 10000
							+ "', '"
							+ y
							* 10000
							+ "', '"
							+ z
							* 10000
							+ "', '1000', 'aihive', '1', '1', '1', '1', '1', '1', '0','0','0','500','5000','1','0','0','aiscout',(select NOW() from landscape limit 1))";
					if (Main.DEBUG)System.out.println(query);
					int affected = updatestmt.executeUpdate(query);
	
				}
			}
		}
}// end class NPCtasks