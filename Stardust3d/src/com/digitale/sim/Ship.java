/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.sim;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.digitale.mygdxgame.Stardust3d;

public class Ship {
	//if set the ship is docked in this station
	public static int STATUS =0;
	public static final float SHIP_RADIUS = 10;
	public static float SHIP_POWER = 0.01f;
	public static final float SHIP_MAXACCELERATION = 2.5f;
	public static float SHIP_VELOCITY =0f;
	public static int mass=1;
	public static float SHIP_MAXVELOCITY =200f;
	public static float SHIP_ACCELERATION = 0f;
	public static Vector3 position ;//= new Vector3(0, 0, 0);
	public int lives = 3;
	public boolean isExploding = false;
	public float explodeTime = 0;
	public final Vector3 direction= new Vector3(0, 0, -1);
	public static float yawAngle=0.0f;
	public static Quaternion shipRot = new Quaternion(0,0,0,0);
	public static float SHIP_HEALTH=0;
	public static int SHIP_LASTHEALTH=0;
	public static int SHIP_MAXHEALTH=1000;
	public boolean attacked;
	
	public static float pitchAngle=0.0f;
	//vector rotated to shiprot
	public Vector3 heading;
	public float rollAngle;
	public int mainGunCD;
	public int repairCD;
	public int missileCD;
	public int turretCD;
	
	public static boolean canDock=false;
	public static boolean docking=false;
	public static boolean undocking=false;
	public static float drag;
	public static int bounce;
	
	public Ship(float sysx, float sysy, float sysz, String status) {
		position=new Vector3(sysx,sysy,sysz);
		if (status.equals("0"))
			STATUS=0;
		if(status.equals("1"))
			STATUS=1;
		SHIP_HEALTH=Stardust3d.myCharacter.getHitpoints();
		SHIP_MAXHEALTH=1000;
		attacked=false;
	}
	
	public void update (float delta) {
		Stardust3d.myCharacter.setSysx(position.x);
		Stardust3d.myCharacter.setSysy(position.y);
		Stardust3d.myCharacter.setSysz(position.z);
	//	if (bounce>0){shipBounce(delta);}
		SHIP_HEALTH=Stardust3d.myCharacter.getHitpoints();
		Stardust3d.myCharacter.setPitchAngle(pitchAngle);
		Stardust3d.myCharacter.setYawAngle(yawAngle);
		Stardust3d.myCharacter.setVelocity(SHIP_VELOCITY);
		SHIP_VELOCITY =SHIP_VELOCITY+(SHIP_ACCELERATION - drag *SHIP_VELOCITY);
	
		STATUS=Integer.valueOf(Stardust3d.myCharacter.getStatus());
		if (SHIP_HEALTH < SHIP_LASTHEALTH){
			attacked=true;
			}
		SHIP_LASTHEALTH=(int) SHIP_HEALTH;
		if (isExploding) {
			explodeTime += delta;
			if (explodeTime > 3) {
				isExploding = false;
				explodeTime = 0;
			}
		}
	}

	private void shipBounce(float delta) {
		
		//yawAngle=yawAngle-(bounce*delta);
		pitchAngle=pitchAngle-(bounce*5*delta);
		Stardust3d.myCharacter.setPitchAngle(pitchAngle);
		//Stardust3d.myCharacter.setYawAngle(yawAngle);
		
		if (bounce >= 180){
			bounce=0;
		}
	}
}
