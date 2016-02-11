/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.utils;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;


public class Util {
//
	/** the logo texture **/
	private Texture logo;
	/** view & transform matrix **/
	private final Matrix4 viewMatrix = new Matrix4();
	private final Matrix4 transformMatrix = new Matrix4();
	/** the SpriteBatch used to draw the background, logo and text **/
	private SpriteBatch spriteBatch;
	/** the font **/
	private BitmapFont font;
	/** lower margin for text **/
	private int lowerMargin = 64;
	public void doCopyright(String copyRightMessage) {
	spriteBatch = new SpriteBatch();
	font = new BitmapFont(Gdx.files.internal("data/font16.fnt"), Gdx.files.internal("data/font16.png"), false);//set up font hilight
		viewMatrix.setToOrtho2D(0, 0, 480, 320);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.setTransformMatrix(transformMatrix);
		spriteBatch.begin();
		
		logo = new Texture(Gdx.files.internal("data/title.png")); // OVERLAY 
		logo.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		String text = copyRightMessage; //create and assign 1st line
		float width = font.getBounds(text).width; // calculate text width for centering
		font.draw(spriteBatch, text, 240 - width / 2, lowerMargin-40); // 64 bottom edge of menu graphic, text width/2 for centre justified
		spriteBatch.end();
		
	
}
	public final static String asCapFirstChar(final String target) {

	    if ((target == null) || (target.length() == 0)) {
	        return target; // You could omit this check and simply live with an
	                       // exception if you like
	    }
	    return Character.toUpperCase(target.charAt(0))
	            + (target.length() > 1 ? target.substring(1) : "");
	}
public static   Vector3 lookAt(Vector3 target, Vector3 observer){
	float playerx = target.x;
	float playery = target.y;
	float playerz = target.z;
	
	Vector3 playerVector = new Vector3(playerx,
			playery, playerz);
	Vector3 direction = new Vector3(
			playerVector.sub(observer));
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

		npcYaw = Math.toDegrees(npcYaw);
		return new Vector3((float)(npcPitch),(float)(npcYaw),0);
}
}

