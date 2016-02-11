/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.digitale.screens;

import com.badlogic.gdx.Screen;

/** Interface for a game screen, e.g. main menu, game loop, game over screen and so on.
 * @author R Beech */
public abstract class StardustScreen implements Screen {
	/** Called when the screen should update itself, e.g. continue a simulation etc.
	 * 
	 * @param delta the Application */
	public abstract void update (float delta);
	/** Called when a screen should render itself
	 */
	public abstract void draw (float delta);
	
	/** Called by Stardust to check whether the screen is done.
	 * 
	 * @return true when the screen is done, false otherwise 
	 * Int regarding */
	public abstract boolean isDone ();

	@Override
	public  void render (float delta){
	update(delta);
	draw(delta);
	}
	@Override
	public void resize(int width, int height){}
	

	@Override
	public void show () {
	}

	@Override
	public void hide () {
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	/** Cleans up all resources of the screen, e.g. meshes, textures etc. */
	public abstract void dispose ();


}
