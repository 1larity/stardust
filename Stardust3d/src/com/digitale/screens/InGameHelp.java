/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.screens;

import java.util.Timer;
import java.util.TimerTask;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;

import com.digitale.mygdxgame.Renderer;
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.Stardust3d;

/**
 * The main menu screen showing a background, the logo of the game and a label
 * telling the user to touch the screen to start the game. Waits for the touch
 * and returns isDone() == true when it's done so that the ochestrating
 * GdxInvaders class can switch to the next screen.
 * 
 * @author mzechner
 */
public class InGameHelp extends StardustScreen {
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };
	Skin skin;
	Actor root;
	protected boolean doneflag=false;
	
	public InGameHelp(Stage stage, String newslines) {
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));

		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;

		final Button buttonLogin = new TextButton("Close",
				skin.getStyle(TextButtonStyle.class), "buttonLogin");
		final Label labelHelpText = new Label(newslines, skin);
		labelHelpText.setWrap(true);
		final ScrollPane scrollPane2 = new ScrollPane(labelHelpText,
				skin.getStyle(ScrollPaneStyle.class), "scroll");

		// configures an example of a TextField in password mode.
		final Label lableHelpTitle = new Label("Basic Help: ", skin);
		
		
		Window window = new Window("Help Screen",
				skin.getStyle(WindowStyle.class), "helpWindow");
		if(Stardust3d.DEBUG) window.debug();
		window.x = window.y = 0;
		window.setFillParent(true);
		window.setMovable(false);
		window.defaults().pad(5);
		//build table
		window.row().fill().expandX();
		window.add();
		window.add();
		window.add();
		window.add(buttonLogin).colspan(1).align("right");
		window.row();
		window.add(lableHelpTitle).align("left");
		window.row();
		window.add(scrollPane2).fill().expand().colspan(4);
		window.row();
		window.pack();
		stage.addActor(window);

	

		buttonLogin.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("help Close pressed");
				SoundManager.playuiclick();
				Stardust3d.stationScreen = 108;
			}
		});
	}

	
	@Override
	public void render(float delta) {

		
		if(Stardust3d.DEBUG)Table.drawDebug(Renderer.stage);
	}

	@Override
	public void resize(int width, int height) {
	
	}

	@Override
	public void dispose() {
		skin.dispose();
		//render_toast.trash_all();

	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDone() {
		return doneflag;
		
		}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(float delta) {
		// TODO Auto-generated method stub
		
	}
	

}
