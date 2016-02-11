/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.sim;

import com.badlogic.gdx.math.Vector3;

public class Shot {
	public static float SHOT_VELOCITY = 1000f;
	public static float SHOT_RANGE=500000;//50km
	private long lifeTime=4000;
	public final Vector3 position = new Vector3();
	public float yawAngle;
	public int shotLife;
	public float pitchAngle;
	public Vector3 heading = new Vector3();
	public boolean isInvaderShot;
	public boolean hasLeftField = false;
	private long shotTime;

	public Shot (Vector3 position, float yawAngle, float pitchAngle, boolean isInvaderShot) {
		this.position.set(position);
		heading = new Vector3(0, 0, -1);
		this.yawAngle=yawAngle;
		this.pitchAngle=pitchAngle;
		Ship.shipRot.transform(heading);
		heading=heading.mul(SHOT_VELOCITY);
		this.isInvaderShot = isInvaderShot;
		this.shotTime=System.currentTimeMillis();
	}

	public void update (float delta) {
		shotLife++;
		if (isInvaderShot){
			position.x +=heading.x*delta;
		position.y +=heading.y*delta;
		position.z +=heading.z*delta;
		}else{
			
		position.x +=heading.x*delta;
		position.y +=heading.y*delta;
		position.z +=heading.z*delta;
		}
		//probably should put in some lifetime of shot code here
		
		if (shotTime+lifeTime < System.currentTimeMillis())hasLeftField = true;
	}
}
