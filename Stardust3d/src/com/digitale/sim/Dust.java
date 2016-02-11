/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.sim;

import com.badlogic.gdx.math.Vector3;
import com.digitale.utils.Util;

public class Dust {
	public   float size = 0.5f;
	public float yawAngle;
	public float pitchAngle;
	public Vector3 position = new Vector3();
	private Vector3 looking;
	public float colour;

	public Dust (Vector3 position) {
		this.position.set(position);
		this.size=(float) (Math.random() *.2);
		this.colour=(float) ((Math.random()/.75)+.25 );
	}
	public void Update(){
		if (position.x>Ship.position.x+256)
			position.x=Ship.position.x-256;
		if (position.x<Ship.position.x-256)
			position.x=Ship.position.x+256;
		
		if (position.y>Ship.position.y+256)
			position.y=Ship.position.y-256;
		if (position.y<Ship.position.y-256)
			position.y=Ship.position.y+256;
		
		if (position.z>Ship.position.z+256)
			position.z=Ship.position.z-256;
		if (position.z<Ship.position.z-256)
			position.z=Ship.position.z+256;
		looking=Util.lookAt(Simulation.camera,position);
		pitchAngle=looking.x;
		yawAngle=looking.y;
	}
}
