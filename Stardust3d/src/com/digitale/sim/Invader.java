/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.sim;

import java.math.BigInteger;

import com.badlogic.gdx.math.Vector3;

public class Invader {
	public static float INVADER_RADIUS = 20f;
	public static float INVADER_VELOCITY = 1;
	public static int INVADER_POINTS = 40;
	public final static int STATE_MOVE_LEFT = 0;
	public final static int STATE_MOVE_DOWN = 1;
	public final static int STATE_MOVE_RIGHT = 2;
	public String FIRST_NAME = "";
	public String SURNAME = "";
	public int HITPOINTS = 0;
	public BigInteger UID;
	public final Vector3 position = new Vector3();
	public int state = STATE_MOVE_LEFT;
	public boolean wasLastStateLeft = true;
	public float movedDistance = Simulation.PLAYFIELD_MAX_X / 2;

	public Invader (Vector3 position,
			String firstname,
			String surname,
			int hitpoints) {
		this.position.set(position);
		FIRST_NAME=firstname;
		SURNAME=surname;
		HITPOINTS=hitpoints;
		//UID=uid;
	}

	public void update (float delta, float speedMultiplier) {
		
	
	}
}

