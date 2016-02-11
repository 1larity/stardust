/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.sim;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Planet {
	//if set the ship is docked in this station
	public static String docked ="IN_SPACE";
	public static final float SHIP_RADIUS = 1;
	public static float SHIP_VELOCITY =0f;
	public static float SHIP_MAXVELOCITY =20f;
	public static final float SHIP_ACCELERATION = .1f;
	public Vector3 position = new Vector3(0, 0, 0);
	public int lives = 3;
	public boolean isExploding = false;
	public float explodeTime = 0;
	public final Vector3 direction= new Vector3(0, 0, -1);
	public float yawAngle=0.0f;
	public static Quaternion shipRot = new Quaternion(0,0,0,0);
	public static int SHIP_HEALTH=99;
	public static int SHIP_MAXHEALTH=100;
	
	public float pitchAngle=0.0f;
	//vector rotated to shiprot
	public Vector3 heading;
	public float rollAngle;
	public void update (float delta) {
	
	}
}
