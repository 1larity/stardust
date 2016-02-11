/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
 
package com.digitale.sim;

import com.badlogic.gdx.math.Vector3;

public class Trail {
	public static float TRAIL_VELOCITY = 0f;
	public static float TRAIL_LIFE=10;//2.5sec
	public final Vector3 position = new Vector3();
	public Vector3 heading = new Vector3();

	public boolean hasLeftField = false;
	public int life=0;
	public float pitchAngle;
	public float yawAngle;

	public Trail (Vector3 position, float yawAngle, float pitchAngle, boolean isInvaderShot) {
		this.position.set(position);
		heading = new Vector3(0, 0, -1);
		this.yawAngle=yawAngle;
		this.pitchAngle=pitchAngle;
		Ship.shipRot.transform(heading);
		heading=heading.mul(TRAIL_VELOCITY);
	
	}

	public void update (float delta) {
		
		
		life++;
		position.x +=heading.x*delta;
		position.y +=heading.y*delta;
		position.z +=heading.z*delta;
//probably should put in some lifetime of shot code here
		if (life>=TRAIL_LIFE) hasLeftField = true;
		
	}
}
