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
import java.sql.SQLException;
import java.sql.Statement;

import com.badlogic.gdx.math.Vector3;

public class VendorTasks {
	/**
	 * @param stmt
	 * @param updatestmt
	 * @throws SQLException
	 */
	
	static void restock() throws SQLException {

		int vendorid ;
		

		String query;
		// Get a Statement objects
		Statement updatestmt = Main.con.createStatement();
		Statement stmt = Main.con.createStatement();
		Statement qrystmt = Main.con.createStatement();
		
		ResultSet rs;
		// get list of vendors
		query="SELECT * FROM `npc`,vendorstocklist WHERE (shipname='vendor')and npc.uid=vendorstocklist.vendorid";
		System.out.println(query);
		int affected;
		rs = stmt
				.executeQuery(query);
		while (rs.next()) {
			 vendorid = Integer.valueOf(rs.getString("uid"));
			
			int itemid= Integer.valueOf(rs.getString("itemid"));
			int stocklevel= Integer.valueOf(rs.getString("stocklevel"));
			System.out.println("vendorid " + vendorid +" itemid "+itemid+" stocklevel "+stocklevel);
			
			String stockcountquery="SELECT count(*) as currentstock FROM `inventory` WHERE (characteruid=-'"+vendorid+"')and item_ID='"+itemid+"'" ;
			System.out.println(query);
			ResultSet stockcount = qrystmt
					.executeQuery(stockcountquery);
			while (stockcount.next()) {
				int currentStocklevel= Integer.valueOf(stockcount.getString("currentstock"));
				if (currentStocklevel<stocklevel){
				for(int i=currentStocklevel;i<stocklevel;i++){
				query = "INSERT INTO inventory (`slot_id`, `characteruid`, `item_ID`, `uid`)"
						+ " VALUES ('0', '-"+vendorid+"', '"+itemid+"',NULL)";
				System.out.println(query);
				 affected = updatestmt.executeUpdate(query);
				}
				}
			}
		}
		
		// trash unwanted items
		query="SELECT * FROM `npc` WHERE (shipname='vendor')";
		System.out.println(query);
		
		rs = stmt
				.executeQuery(query);
		while (rs.next()) {
			vendorid = Integer.valueOf(rs.getString("uid"));
		
		query = "select* from vendortrash where characteruid='-"+vendorid+"'";
		System.out.println(query);
		ResultSet deleteresults=qrystmt
				.executeQuery(query);
		
		while (deleteresults.next()) {
			int inventoryid= Integer.valueOf(deleteresults.getString("uid"));
			query = "delete from inventory where uid='"+inventoryid+"'";
			System.out.println(query);
			// affected = updatestmt.executeUpdate(query);
		}
		}
		 updatestmt.close();
		 stmt.close();
		 qrystmt.close();
		
	}
}
