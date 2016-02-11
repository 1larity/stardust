/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.connex;
import java.sql.*;

public class Ground {
 public static void loadGround(){
 System.out.println(
 "start load ground types");
 try {
 Statement stmt;
 ResultSet rs;

 //Register the JDBC driver for MySQL.
 Class.forName("com.mysql.jdbc.Driver");

 //Define URL of database server for
 // database named test on the localhost
 // with the default port number 3306.
 String url = "jdbc:mysql://localhost:3306/test";

 //Get a connection to the database for a
 // user named Playeraccount with the password
 // akira01, which is password spelled
 // backwards.
 Connection con = DriverManager.getConnection( url,"root", "universal40");

 //Display URL and connection information
 System.out.println("URL: " + url);
 System.out.println("Connection: " + con);

 //Get a Statement object
 stmt = con.createStatement();
/**
 //As a precaution, delete myTable if it
 // already exists as residue from a
 // previous run. Otherwise, if the table
 // already exists and an attempt is made
 // to create it, an exception will be
 // thrown.
 try{
 stmt.executeUpdate("DROP TABLE myTable");
 }catch(Exception e){
 System.out.print(e);
 System.out.println(
 "No existing table to delete");
 }//end catch

 //Create a table in the database named
 // myTable.
 stmt.executeUpdate(
 "CREATE TABLE myTable(test_id int," +
 "test_val char(15) not null)");

 //Insert some values into the table
 stmt.executeUpdate(
 "INSERT INTO myTable(test_id, " +
 "test_val) VALUES(1,'One')");
 stmt.executeUpdate(
 "INSERT INTO myTable(test_id, " +
 "test_val) VALUES(2,'Two')");
 stmt.executeUpdate(
 "INSERT INTO myTable(test_id, " +
 "test_val) VALUES(3,'Three')");
 stmt.executeUpdate(
 "INSERT INTO myTable(test_id, " +
 "test_val) VALUES(4,'Four')");
 stmt.executeUpdate(
 "INSERT INTO myTable(test_id, " +
 "test_val) VALUES(5,'Five')");
**/
 //Get another statement object initialized
 // as shown.
 stmt = con.createStatement(
 ResultSet.TYPE_SCROLL_INSENSITIVE,
 ResultSet.CONCUR_READ_ONLY);

 //Query the database, storing the result
 // in an object of type ResultSet
 rs = stmt.executeQuery("SELECT * from ground_types ORDER BY ground");

 //Use the methods of class ResultSet in a
 // loop to display all of the data in the
 // database.
 System.out.println("Display all results:");
 int i=0;
 while(rs.next()){
 String theGround= rs.getString("ground");
 boolean isSea = rs.getBoolean("issea");
 boolean isViable = rs.getBoolean("isviable");
 String soundLoc =rs.getString("sound");
 String textureLoc = rs.getString("texture");
 
 
 
 
 System.out.println("\tGround= " + theGround + "\tis sea? = " + isSea+ "\tis viable? = "
		 + isViable + "\tsound = " + soundLoc + "\ttexture"+ textureLoc);
//load data into global array
 Main.Land[i].set_ground(theGround);
 Main.Land[i].set_issea(isSea);
 Main.Land[i].set_isviable(isViable);
 Main.Land[i].set_texture(textureLoc);
 Main.Land[i].set_sound(soundLoc);

 i++;
 }//end while loop

 //Display the data in a specific row using
 // the rs.absolute method.
 /** System.out.println(
 "Display row number 2:");
 if( rs.absolute(2) ){
 int theInt= rs.getInt("test_id");
 String str = rs.getString("test_val");
 System.out.println("\ttest_id= " + theInt
 + "\tstr = " + str);
 }//end if
**/
 //Delete the table and close the connection
 // to the database
 //stmt.executeUpdate("DROP TABLE myTable");
 con.close();
 }catch( Exception e ) {
 e.printStackTrace();
 }//end catch
 }//end main
}//end class Ground