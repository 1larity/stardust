/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.connex;

public class Location  {
	   private String   _ground;
	   private byte  _x;
	   private byte _y;
	   private byte      _z;
	   private String  _sysname;
	   private int level;
	   private byte leftlink;
	   private byte downlink;
	 
	   
	   public Location(  ) {
	   }
	   
	   public Location(String ground, 
	                      byte  x,
	                      byte y,
	                      byte z,
	                      String sysname,
	                      byte level,
	                      byte leftlink,
	                      byte downlink) {
	      set_ground(ground);
	      set_x(x);
	      set_y(y);
	      set_z(z);
	      set_sysname(sysname);
	    set_level(level);
	    setLeftlink(leftlink);
	    setDownlink(downlink);
	   }

	public void set_ground(String _ground) {
		this._ground = _ground;
	}

	public String get_ground() {
		return _ground;
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

	public void set_sysname(String _sysname) {
		this._sysname= _sysname;
		
	}

	public String get_sysname() {
		return _sysname;
	}

	public void set_level(byte level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLeftlink(byte leftlink) {
		this.leftlink = leftlink;
	}

	public int getLeftlink() {
		return leftlink;
	}

	public void setDownlink(byte downlink) {
		this.downlink = downlink;
	}

	public int getDownlink() {
		return downlink;
	}
	}