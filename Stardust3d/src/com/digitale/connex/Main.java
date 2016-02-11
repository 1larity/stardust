/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.connex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class Main {
	static GroundItem Land[] = new GroundItem[20];
	public static byte[][] routeMap = new byte[129][129];

	static int landSize = 64;
	static Location[][] LandScape = new Location[64][64];

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("start main");

		// load ground tiles

		// initialize every entry in the array
		for (int x = 0; x < LandScape.length; x++) {
			for (int y = 0; y < LandScape.length; y++) {
				LandScape[x][y] = new Location();
			}

			for (int i = 0; i < Land.length; i++) {

				Land[i] = new GroundItem();
			}
		}
		Ground.loadGround();
		System.out.println("checking groundtypes");
		for (int i = 0; i < Land.length; i++) {
			System.out.println(i + "    " + Land[i].get_ground());

		}
		// generate 100 square landscape full of sand, just use to init table
		// RandomLand.Fillblank(landSize+1);
		// set ground types
		// RandomLand.totallyRandom(landSize+1);
		// generate z co-ords
		// RandomLand.heightMap(landSize+1);

		/*
		 * move npcs around 
		 * boolean bob=false;
		 *  Timer timer = new Timer();
		 * timer.scheduleAtFixedRate(new TimerTask() { public void run() {
		 * NPCtasks.walknpcs(); } }, 0, 500);
		 * 
		 * System.out.println( "end main"); }
		 */
		// try {
		// RandomLand.readpvp(landSize);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// RandomLand.randomlinks(landSize+1);
		initlandscape();//only do once!
		
		//loop forever every .5 secs running NPC moves
		/*  Timer timer = new Timer();
		 timer.scheduleAtFixedRate(new TimerTask() { public void run() {
			 checkrequests();
			 NPCtasks.walknpcs();
			 } }, 0, 500);
		*/
		//loop forever every.5secs check for new routepath
		  Timer timer2 = new Timer();
			 timer2.scheduleAtFixedRate(new TimerTask() { public void run() {
				 checkrequests();
				
				 } }, 0, 500);
			
		
	}

	private static int   findroute(int userid, byte sx ,byte sy ,byte ex ,byte ey ) {
		//
		int routeid;
			//set up search area slightly bigger than area bounded by start and finish
			// just in case we have to go away from endpoint before we can find a path 
			int lowerx = sx-2;
			int topx = ex +2;
			int lowery = sy-2;
			int topy = ey+2;
			// make sure search area is not off map
			if (lowerx<0) lowerx=0;
			if (lowery<0) lowery=0;
			if (topx>64) lowerx=64;
			if (topy>64) lowerx=64;
			for (int x = lowerx; x < topy; x++) {

				for (int y = lowery; y < topy; y++) {
					//System.out.println("set routemap x= " + x + "y= " + y);
					// mark up links
					//set each system to 1
					routeMap[x * 2][y * 2] = 2; // systems LandScape[x][y]
					if (LandScape[x ][y].getLeftlink() == 1 )
						routeMap[x * 2 ][y * 2+1] = 1;
					if (LandScape[x][y].getDownlink() == 1 )
						routeMap[x * 2+1][y * 2 ] = 1;
				}
			}
			drawmap(sx, sy, ex, ey);
			System.out.println("find path");
			//request route with co-ords doubled to account for system links
			 routeid=PathFinder.setmap(userid,routeMap, (byte)(sx*2), (byte)(sy*2),(byte) (ex*2),(byte)( ey*2));

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
				//System.out.println("x " + rs.getInt("x") + " y "
				//		+ rs.getInt("y") + " LL " + rs.getInt("leftlink")
				//		+ " dl  " + rs.getInt("downlink"));
				LandScape[rs.getInt("x")-1][rs.getInt("y")-1].setLeftlink((byte) rs
						.getInt("leftlink"));
				LandScape[rs.getInt("x")-1][rs.getInt("y")-1].setDownlink((byte) rs
						.getInt("downlink"));
		
			}			con.close();
			
			//print out landscape
			/*
			for(int i = 0; i < LandScape.length; i++){
				for(int j = 0; j < LandScape[0].length; j++)
						System.out.print(LandScape[i][j] + " ");
				System.out.println();
		}
		*/

		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("fail");
		} catch (Exception e) {
			e.printStackTrace();
		}// end catch
	}

	private static void drawmap(byte sx, byte sy, byte ex, byte ey) {
		//draw map with star and end points
		for(int i = 0; i < routeMap.length; i++){
			for(int j = 0; j < routeMap.length; j++)
				if(i==sx*2 && j ==sy*2){
					System.out.print("S ");
				}else if (i==ex*2 && j ==ey*2){
						System.out.print("E ");
					
				}else{	
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
				int uid= rs.getInt("uid");
				int userid=rs.getInt("userid");
				query = "update pathrequest set inprogress= 1 where uid ="+uid ;
				int result = stmt.executeUpdate(query);
				
				int routeid = findroute(userid,startx,starty,endx,endy);
				
				
			}			con.close();
			
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("fail");
		} catch (Exception e) {
			e.printStackTrace();
		}// end catch
	}
}