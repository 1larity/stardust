/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.connex;

import java.math.BigInteger;

import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.digitale.mygdxgame.Renderer;
import com.digitale.sim.Ship;
import com.digitale.sim.Simulation;

public class Actor {
	private BigInteger _uid;
	private String _race;
	private int _x;
	private int _y;
	private int _z;
	public boolean exploding = false;
	private boolean _is_static;
	private String _firstname;
	private String _surname;
	public Vector3 position;
	private float sysx;
	private float sysy;
	private float sysz;
	private int status;
	private int hitpoints;
	public static float INVADER_RADIUS = 10f;
	public static float INVADER_VELOCITY = 1;
	public static int INVADER_POINTS = 40;
	public final static int STATE_MOVE_LEFT = 0;
	public final static int STATE_MOVE_DOWN = 1;
	public final static int STATE_MOVE_RIGHT = 2;
	public int state = STATE_MOVE_LEFT;
	public boolean wasLastStateLeft = true;
	public float movedDistance = Simulation.PLAYFIELD_MAX_X / 2;
	public String shipname = "cynomys";
	public float velocity;
	public int healthBar;
	public float distance;
	public String distanceString;
	public BitmapFontCache cache;
	public boolean visible;
	public boolean locked;

	public int getHealthBar() {
		return healthBar;
	}

	public void setHealthBar(int healthBar) {
		this.healthBar = healthBar;
	}

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public String getShipname() {
		return shipname;
	}

	public void setShipname(String shipname) {
		this.shipname = shipname;
	}

	public float pitchangle;

	public float getPitchangle() {
		return pitchangle;
	}

	public void setPitchangle(float pitchangle) {
		this.pitchangle = pitchangle;
	}

	public float getYawangle() {
		return yawangle;
	}

	public void setYawangle(float yawangle) {
		this.yawangle = yawangle;
	}

	public float yawangle;
	private Quaternion shipRot = new Quaternion(0, 0, 0, 0);
	public Vector3 heading;
	public Vector3 reticulepos;
	public int mass = 1;

	public Actor() {
	}

	public void update(float delta) {
		this.shipRot.setEulerAngles(this.yawangle, this.pitchangle, 0);
		Vector3 heading = new Vector3(0, 0, -1);
		this.shipRot.transform(heading);
		this.heading = heading;
		// add heading vector to ship position

		this.position.add(heading.mul(this.velocity * 1.01f * delta));

		this.healthBar = 0 + (hitpoints - 0) * (32 - 0) / (1000 - 0);
		/*
		 * A minimum of dataset B maximum of dataset a is from where you would
		 * like normalised data set to start b is where you would like
		 * normalised data set to end x is the value you are trying to normalise
		 * a + (x-A)*(b-a)/(B-A)
		 */
		visible = false;
		if (distance < 5000) {
			if (Renderer.camera.frustum.pointInFrustum(position)) {
				if (status == 1) {
					if (hitpoints > 0) {
						visible = true;
					} else {
						visible = false;
					}
				}
			}
		}
	}

	public Actor(BigInteger uid, String race, int x, int y, int z,
			boolean is_static, String firstname, String surname,
			Vector3 position, int hitpoints, String shipname, float velocity) {
		set_uid(uid);
		set_race(race);
		set_x(x);
		set_y(y);
		set_z(z);
		set_position(position);
		set_is_static(is_static);
		set_firstname(firstname);
		set_surname(surname);
		setHitpoints(hitpoints);
		setSysx(sysx);
		setSysy(sysy);
		setSysz(sysz);
		setShipname(shipname);
		setVelocity(velocity);
		reticulepos = new Vector3(0, 0, 0);
		distanceString = "";
		cache = new BitmapFontCache(Renderer.fontsmall);
	}

	public float getSysx() {
		return sysx;
	}

	public void setSysx(float sysx) {
		this.sysx = sysx;
	}

	public float getSysy() {
		return sysy;
	}

	public void setSysy(float sysy) {
		this.sysy = sysy;
	}

	public float getSysz() {
		return sysz;
	}

	public void setSysz(float sysz) {
		this.sysz = sysz;
	}

	public int getHitpoints() {
		return hitpoints;
	}

	public void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
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

	public void set_x(int _x) {
		this._x = _x;
	}

	public int get_x() {
		return _x;
	}

	public void set_y(int _y) {
		this._y = _y;
	}

	public int get_y() {
		return _y;
	}

	public void set_z(int _z) {
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

	public Vector3 get_position() {
		return position;
	}

	public void set_position(Vector3 _position) {
		this.position = _position;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}