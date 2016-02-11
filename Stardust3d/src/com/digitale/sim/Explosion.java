/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.sim;

import com.badlogic.gdx.math.Vector3;
import com.digitale.utils.Util;

public class Explosion {
	public   float explosionLiveTime = 1;
	
	public final static int NORMAL=1;
	public final static int SWANSONG=0;
	public final static int RANKUP=2;
	public final static int LEVELUP=3;
	public int type=NORMAL;
	public float aliveTime = 0;
	public float scale=0;
	public final Vector3 position = new Vector3();
	private Vector3 looking;
	public float yawAngle;
	public float pitchAngle;
	public Explosion (Vector3 position) {
		this.position.set(position);
	}
	public Explosion (Vector3 position, int type) {
		this.type=type;
		this.position.set(position);
		if (type==SWANSONG ||type==RANKUP ||type==LEVELUP)explosionLiveTime=2;
	}
	public void update (float delta) {
		aliveTime += delta;
		looking=Util.lookAt(Simulation.camera,position);
		pitchAngle=-looking.x;
		yawAngle=looking.y;
		scale = scale + .1f;
		if (type==RANKUP || type==LEVELUP){
			position.set(Ship.position);
		}
	}
	public void setScale(float scale){
		this.scale=scale;
	}
	public float getScale(){
		return this.scale;
	}
}
