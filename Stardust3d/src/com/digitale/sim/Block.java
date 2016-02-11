/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.sim;

import com.badlogic.gdx.math.Vector3;

public class Block {
	public final static float BLOCK_RADIUS = 0.5f;

	public Vector3 position = new Vector3();

	public Block (Vector3 position) {
		this.position.set(position);
	}
}
