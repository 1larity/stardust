/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.sim;

import com.badlogic.gdx.math.Vector3;
import com.digitale.utils.RotationMatrixFull;

public class Drone {
	public static float DRONE_RADIUS = 0.75f;
	public static float DRONE_VELOCITY = 1;
	public static int DRONE_POINTS = 40;
	public final static int STATE_ORBIT = 0;
	public final static int STATE_MOVE_DOWN = 1;
	public final static int STATE_MOVE_RIGHT = 2;

	public final Vector3 position = new Vector3();
	public int state = STATE_ORBIT;
	public boolean wasLastStateLeft = true;
	public float movedDistance = Simulation.PLAYFIELD_MAX_X / 2;
	public Vector3 direction= new Vector3();

	public Drone (Vector3 position) {
		this.position.set(position);
	}

	public void update (float delta, float speedMultiplier,Ship ship) {
		movedDistance += delta * DRONE_VELOCITY * speedMultiplier;
		if (state == STATE_ORBIT) {
			//convert 5 degrees to radians
			double theta = 1*(3.1415926535/180);
			float axisX,axisY,axisZ;
			axisX=Ship.position.x;
			axisY=Ship.position.y;
			axisZ=Ship.position.z;
			 double[] p =new RotationMatrixFull().rotPointFromFormula(
					 //axis to rotate drones around ship
					 axisX, axisY,axisZ-6,
					 axisX, axisY,axisZ+6,
					// drone position
					 position.x,position.y,position.z,
					 theta);
			 position.x=(float) p[0];
			 position.y=(float) p[1];
			 position.z=(float) p[2];
		}
		if (state == STATE_MOVE_RIGHT) {/*
			position.x += delta * DRONE_VELOCITY * speedMultiplier;
			if (movedDistance > Simulation.PLAYFIELD_MAX_X) {
				state = STATE_MOVE_DOWN;
				movedDistance = 0;
				wasLastStateLeft = false;
				
			}
		*/}
		if (state == STATE_MOVE_DOWN) {/*
			position.z += delta * DRONE_VELOCITY * speedMultiplier;
			if (movedDistance > 1) {
				if (wasLastStateLeft)
					state = STATE_MOVE_RIGHT;
				else
					state = STATE_ORBIT;
				movedDistance = 0;
			}
		*/}
	}


	}

