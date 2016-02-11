/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package connex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class PlayerTasks {

	public static void checkDisconnects() {
		System.out.println("check for disconnects");
		try {
			Statement stmt, updatestmt, qrystmt;

			// Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");

			// Define URL of database server
			String url = Main.MYSQLURL;

			// Get a connection to the database
			Connection con = DriverManager.getConnection(url, Main.serviceUser,
					Main.servicePass);

			String query = null;
			// Get a Statement objects
			updatestmt = con.createStatement();
			stmt = con.createStatement();
			qrystmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM player_char where (lastupdate < (select now() -interval 2 minute from landscape limit 1)) and lastupdate !='0000-00-00 00:00:00'");
			while (rs.next()) {
				if (rs.getString("uid") != null) {

					// get the name of the player
					String npcfirstname = rs.getString("firstname");
					String npcsurname = rs.getString("surname");
					// get the uid of the player
					int auid = Integer.valueOf(rs.getString("uid"));
					// convert last attacker uid to name
					query = ("update player_char set lastupdate='0000-00-00 00:00:00', velocity=0 where uid=" + auid);
					System.out.println(query);
					int affected = updatestmt.executeUpdate(query);

					query = "INSERT INTO chatmessages (`chaituid`, `chatmessage`, `senderid`, `chatchannel`) "
							+ " VALUES (NULL,'"
							+ npcfirstname
							+ " "
							+ npcsurname + " has disconnected.','48','1')";
					System.out.println(query);
					affected = updatestmt.executeUpdate(query);

				}
			}
			System.out.println("check levels");
			rs = stmt
					.executeQuery("SELECT * FROM player_char where (lastupdate > (select now() -interval 1 minute from landscape limit 1)) and lastupdate !='0000-00-00 00:00:00'");
			// for each player that's been recently updated
			while (rs.next()) {
				if (rs.getString("uid") != null) {
					String name = rs.getString("firstname") + " "
							+ rs.getString("surname");
					String lastRank = rs.getString("lastlevelnotification");
					// for each leveldef
				leveloop:	for (int i = 0; i < Main.levelslist.size(); i++)

					{
						levelDef thislevel = Main.levelslist.get(i);
						int exp = Integer.valueOf(rs.getString("exp"));
						System.out.println("check name " + name + "i " + i
								+ " target exp" + exp + "current lvlcheck exp"
								+ thislevel.exp);
						// if player has less exp than this leveldef, and more
						// than the previous leveldef
						if (i > 0 && exp < Main.levelslist.get(i).exp
								&& exp >= Main.levelslist.get(i - 1).exp) {
							int rank = Main.levelslist.get(i - 1).rank;
							int level = Main.levelslist.get(i - 1).level;
							// get the uid of the player
							int auid = Integer.valueOf(rs.getString("uid"));
							String rankstring = "L" + level + "R" + rank;
							System.out.println("Player name " + name
									+ " rankstring " + rankstring);
							if (!lastRank.equals(rankstring)) {
								int rankDelimiter=lastRank.indexOf("R");
								int currentLevel=Integer.valueOf(lastRank.substring(1,rankDelimiter));
								int currentrank=Integer.valueOf(lastRank.substring(rankDelimiter+1));
								if (rank > currentrank ){
								query = "INSERT INTO chatmessages (`chaituid`, `chatmessage`, `senderid`, `chatchannel`) "
										+ " VALUES (NULL,'"
										+ " Congratulations! You have attained rank "
										+ rank
										+ " level "
										+ level
										+ ". You have gained a skillpoint.','48','"
										+ -auid + "')";
								}else if (level>currentLevel){
									query = "INSERT INTO chatmessages (`chaituid`, `chatmessage`, `senderid`, `chatchannel`) "
											+ " VALUES (NULL,'"
											+ " Congratulations! You have attained level "
											+ level
											+ ". You have gained a skillpoint, your stats have increased.','48','"
											+ -auid + "')";
								}
								System.out.println(query);
								int affected = updatestmt.executeUpdate(query);
								query = "update player_char set skillpoints=skillpoints+1, lastlevelnotification='"
										+ rankstring + "' where uid="+auid;
								System.out.println(query);
								affected = updatestmt.executeUpdate(query);
							}
							break leveloop;
						}
						
						
					}
					
				}
			}

			con.close();
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("fail");
		} catch (Exception e) {
			e.printStackTrace();
		}// end catch
	}// end method checkdisconnects

}
