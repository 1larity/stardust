/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.sim;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Station {
		public static final float STATION_RADIUS = 1500;
	public static float STATION_VELOCITY =0f;
	public static float STATION_MAXVELOCITY =20f;
	public static final float STATION_ACCELERATION = .1f;
	public Vector3 position = new Vector3(0, 0, 0);
	public int lives = 3;
	public boolean isExploding = false;
	public float explodeTime = 0;
	public final Vector3 direction= new Vector3(0, 0, -1);
	public static float yawAngle=0.0f;
	public static Quaternion STATIONRot = new Quaternion(0,0,0,0);
	public static int STATION_HEALTH=99;
	public static int STATION_MAXHEALTH=100;
	
	public static float pitchAngle=0.0f;
	public static float Rotspeedy;
	//vector rotated to STATIONrot 
	public Vector3 heading;
	public float rollAngle;
	public Station(int x, int y, int z, float rotspeedy) {
		position=new Vector3(x,y,z);
		Rotspeedy=rotspeedy;
	}
	public void update (float delta) {
		if (isExploding) {
			explodeTime += delta;
			if (explodeTime > 3) {
				isExploding = false;
				explodeTime = 0;
			}
		}
	}
}
