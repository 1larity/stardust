/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.connex;
/*    
 * A* algorithm implementation.
 * Copyright (C) 2007, 2009 Giuseppe Scrivano <gscrivano@gnu.org>

 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
			
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

/*
 * Example.
 */
class PathFinder extends AStar<PathFinder.Node>
{
	
		
		private static byte mendx;
		private static byte mendy;
		private static byte mstartx;
		private static byte mstarty;
		private byte[][] map;

		public static class Node{
				public byte x;
				public byte y;
				Node(byte x, byte y){
						this.x = x; 
						this.y = y;
				}
				public String toString(){
						return "(" + x + ", " + y + ") ";
				} 
		}
		public PathFinder(byte[][] map2){
				this.map = map2;
		}

		protected boolean isGoal(Node node){
				return (node.x == mendx) && (node.y == mendy);
		}

		protected Double g(Node from, Node to){

				if(from.x == to.x && from.y == to.y)
						return 0.0;

				if(map[to.y][to.x] >= 1)
						//return the value of the cell
					return (double) map[to.y][to.x];

				return Double.MAX_VALUE;
		}

		protected Double h(Node from, Node to){
				/* Use the Manhattan distance heuristic.  */
				return new Double(Math.abs(map[0].length - 1 - to.x) + Math.abs(map.length - 1 - to.y));
		}

		protected List<Node> generateSuccessors(Node node){
				List<Node> ret = new LinkedList<Node>();
				byte  x = node.x;
				byte y = node.y;
				if(y < map.length - 1 && map[y+1][x] >=1)
						ret.add(new Node(x, (byte) (y+1)));

				if(x < map[0].length - 1 && map[y][x+1] >=1)
						ret.add(new Node((byte) (x+1), y));

				return ret;
		}
//largest search distance seems to be 32 x32
		public static  int setmap( int userid, byte[][] passedmap,byte startx,byte starty,byte endx, byte endy){
				byte [][] map =passedmap;
				PathFinder pf = new PathFinder(map);
				int routeid;

				System.out.println("Find a path from the top left corner to the right bottom one.");
				/*
				 
				 //print out map
				for(int i = 0; i < map.length; i++){
						for(int j = 0; j < map[0].length; j++)
								System.out.print(map[i][j] + " ");
						System.out.println();
				}
			*/

				long begin = System.currentTimeMillis();
				mendy=endy;
				mendx=endx;
				mstartx=startx;
				mstarty=starty;
				List<Node> nodes = pf.compute(new PathFinder.Node((byte)startx	, (byte)startx));
				
				long end = System.currentTimeMillis();


				System.out.println("Time = " + (end - begin) + " ms" );
				System.out.println("Expanded = " + pf.getExpandedCounter());
				System.out.println("Cost = " + pf.getCost());
				
				if(nodes == null)
						System.out.println("No path");
				else{
					try {
					Statement stmt;
					String query="";
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
					
						System.out.println("insert path into database");
					ResultSet rs;
					
						System.out.print("Path = ");
						for(Node n : nodes){
								System.out.print("x "+n.x+"y "+n.y);
						
						query = "insert into routepaths values ("+userid+","+n.x+","+n.y+")";
						stmt = con.createStatement();
						int result = stmt.executeUpdate(query);
						}
						query = "delete from pathrequest where userid="+userid;
						stmt = con.createStatement();
						int result = stmt.executeUpdate(query);
						con.close();
						System.out.println();
					} catch (MySQLIntegrityConstraintViolationException e) {
						System.out.println("fail");
					} catch (Exception e) {
						e.printStackTrace();
					}// end catch
					
				}
				
				routeid=0;
				return routeid;
		}
}

