/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.sim;

import com.badlogic.gdx.math.Vector3;
import com.digitale.connex.Actor;

public class Missile {
	public static float SHOT_VELOCITY = 500f;
	public static float SHOT_RANGE=500000;//50km
	private long lifeTime=4000;
	public final Vector3 position = new Vector3();
	public float yawAngle;
	public int shotLife;
	public float damage;
	public float pitchAngle;
	public Vector3 heading = new Vector3();
	public boolean isInvaderShot;
	public boolean hasLeftField = false;
	private long shotTime;
	public Actor target;
	Vector3 destination;

	public Missile (Vector3 position, float yawAngle, float pitchAngle, boolean isInvaderShot) {
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
		
		 destination= new Vector3(target.position);
			Vector3 direction = new Vector3(
					destination.sub(position));
			double length = direction.len();
			double npcPitch = Math
						.toDegrees(Math
								.asin((double) (direction.y / length)));
				double npcYaw;
				if (Math.abs(direction.z) < 0.00001) {
					// special case
					if (direction.x > 0) {
						npcYaw = Math.PI / 2.0;
					} else if (direction.x < 0) {
						npcYaw = -Math.PI / 2.0;
					} else {
						npcYaw = 0.0;
					}
				} else {
					npcYaw = Math.atan2(direction.x,
							direction.z);
				}

				yawAngle = (float) Math.toDegrees(npcYaw);
				pitchAngle =-(float)npcPitch;
				position.set(position.add(direction.nor().mul(20f)));
			
		
		if (shotTime+lifeTime < System.currentTimeMillis())hasLeftField = true;
	}
}
