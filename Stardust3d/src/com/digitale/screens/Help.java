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
public class Help extends StardustScreen {
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };
	public static boolean fadeOut;
	public static boolean fadeIn;
	public static float fadeTimer;
	public static int i=0;
	private Timer timer = new Timer();
	Skin skin;

	Stage stage;
	SpriteBatch batch;
	/** the cross fade texture **/
	private Texture xfadeTexture;
	Texture background;
	Actor root;
	private String ac;
	protected boolean doneflag=false;

	
	
	public Help(String newslines) {
		OrthographicCamera camera;
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));
		xfadeTexture = new Texture(Gdx.files.internal("data/blackpixel.png"), Format.RGB565, true);
		xfadeTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
		
		background = new Texture(Gdx.files.internal("data/bgpland.jpg"));
		
		camera = new OrthographicCamera();
		
		camera.setToOrtho(false,Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());	
		
		fadeIn();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);

		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;

		final Button buttonLogin = new TextButton("Close",
				skin.getStyle(TextButtonStyle.class), "buttonLogin");
		final Label labelStories = new Label(newslines, skin);
		labelStories.setWrap(true);
		final ScrollPane scrollPane2 = new ScrollPane(labelStories,
				skin.getStyle(ScrollPaneStyle.class), "scroll");

		// configures an example of a TextField in password mode.
		final Label lableNews = new Label("Basic Help: ", skin);
		
		
		Window window = new Window("Help Screen",
				skin.getStyle(WindowStyle.class), "window");
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
		window.add(lableNews).align("left");
		window.row();
		window.add(scrollPane2).fill().expand().colspan(4);
		window.row();
		window.pack();
		stage.addActor(window);

	

		buttonLogin.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("help Close pressed");
				SoundManager.playuiclick();
				if (Stardust3d.mloginOK){
					Stardust3d.gameMode=4;
					 
					doneflag=true;
				}else{
					Stardust3d.gameMode=2;
					doneflag=true;
				}
			}
		});


	}

	/**
	 * 
	 */
	private void fadeIn() {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() { 
				i++;
				
				//detect and set white fade in
				if (i<10 && fadeIn==false ){
						fadeIn=false;
						fadeTimer=1;
				}
				if (fadeIn){
						fadeTimer=fadeTimer-0.05f; 
						System.out.println("fade "+fadeTimer);
					}
				if (fadeIn && fadeTimer>10){
					fadeIn=false;
					fadeTimer=0;
				}
			
				if (fadeTimer<0){
					fadeTimer=0;
					timer.cancel();
				}
				
				}	
		}, 0, 100);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		int width=Gdx.app.getGraphics().getWidth();
		int height=Gdx.app.getGraphics().getHeight();
		
		batch.begin();
		batch.setColor(Color.WHITE);
		batch.draw(background, 0, 0, width,
				height, 0, 0, 1024, 512, false, false);
		batch.end();
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		
		
		if (fadeIn ){
			batch.enableBlending();
			batch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			batch.begin();
			batch.setColor(1,1, 1, fadeTimer);
			batch.draw(xfadeTexture, -100,-100, width+200, height+200);
			batch.end();
			}
		
		
		if(Stardust3d.DEBUG)Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		batch.dispose();
		background.dispose();
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
