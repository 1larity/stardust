/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package connex;
import java.math.BigInteger;
//
//type  for basic NPC data
//

public class NPC  {
	 private BigInteger  _uid;
	   private String   _race;
	   private byte _x;
	   private byte  _y  ;
	   private byte  _z  ;
	   private boolean _is_static;
	   private String _firstname;
	   private String  _surname;
	   
	   public NPC(  ) {
	   }
	   
	   public NPC(  BigInteger  uid,
	    String   race,
	    byte x,
	    byte  y ,
	    byte  z,
	    boolean is_static,
	    String firstname,
	    String  surname  ) {
		   set_uid(uid);
		   set_race(race);
		    set_x(x);
		    set_y(y);
		    set_z(z);
		   set_is_static(is_static);
		    set_firstname(firstname);
		    set_surname(surname);
	   }

	public void set_uid(BigInteger _uid) {
		this._uid = _uid;
	}

	public BigInteger get_uid() {
		return _uid;
	}

	public void set_race(String _race) {
		this._race = _race;
	}

	public String get_race() {
		return _race;
	}

	public void set_x(byte _x) {
		this._x = _x;
	}

	public int get_x() {
		return _x;
	}

	public void set_y(byte _y) {
		this._y = _y;
	}

	public int get_y() {
		return _y;
	}

	public void set_z(byte _z) {
		this._z = _z;
	}

	public int get_z() {
		return _z;
	}

	public void set_is_static(boolean _is_static) {
		this._is_static = _is_static;
	}

	public boolean is_is_static() {
		return _is_static;
	}

	public void set_firstname(String _firstname) {
		this._firstname = _firstname;
	}

	public String get_firstname() {
		return _firstname;
	}

	public void set_surname(String _surname) {
		this._surname = _surname;
	}

	public String get_surname() {
		return _surname;
	}

	
	   
	   
	}