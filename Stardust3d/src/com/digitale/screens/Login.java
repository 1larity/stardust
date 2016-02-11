/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.screens;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.digitale.utils.Actors;
import com.digitale.utils.MCrypt;
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
public class Login extends StardustScreen {
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };
	public static boolean fadeOut;
	public static boolean fadeIn;
	public static float fadeTimer;
	public static int i=0;
	private Timer timer = new Timer();
	Skin skin;
	MCrypt mcrypt = new MCrypt();
	Stage stage;
	SpriteBatch batch;
	/** the cross fade texture **/
	private Texture xfadeTexture;
	Texture background;
	Actor root;
	private String ac;
	protected boolean doneflag=false;
	Preferences prefs = Gdx.app.getPreferences("stardustpreferences");
	
	
	public Login(String newslines) {
		//all sounds loaded, now bind them 
		
		try {
			Renderer.bindMeshes(0);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
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

		final Button buttonLogin = new TextButton("Log in",
				skin.getStyle(TextButtonStyle.class), "buttonLogin");
		final Button buttonHelp = new TextButton("Quick Help",
				skin.getStyle(TextButtonStyle.class), "buttonHelp");
		final Button buttonNewAccount = new TextButton("New Account",
				skin.getStyle(TextButtonStyle.class), "buttonNewAccount");
		final Button buttonOptions = new TextButton("Options",
				skin.getStyle(TextButtonStyle.class), "buttonOptions");
		final CheckBox checkBox = new CheckBox("Remember account name and password.",
				skin.getStyle(CheckBoxStyle.class), "checkbox");
		final TextField textfieldUserName = new TextField("", "Account Name",
				skin.getStyle(TextFieldStyle.class), "textfield");
		// final List list = new List(listEntries,
		// skin.getStyle(ListStyle.class), "list");
		final Label labelStories = new Label(newslines, skin);
		labelStories.setWrap(true);
		final ScrollPane scrollPane2 = new ScrollPane(labelStories,
				skin.getStyle(ScrollPaneStyle.class), "scroll");
		
		// configures an example of a TextField in password mode.
		final Label lableNews = new Label("News: ", skin);
		final TextField passwordTextField = new TextField("", "password", skin);
		passwordTextField.setPasswordCharacter('*');
		passwordTextField.setPasswordMode(true);
		textfieldUserName.getOnscreenKeyboard().show(true);
		
		Boolean booltemp=prefs.getBoolean("nopoly",false);
		//is save account checked in prefs
		if (booltemp){
			//if preferences account is too short to have been set to a valid account name
			checkBox.setChecked(true);
			String temp=prefs.getString("un","");
			ac=temp;
			textfieldUserName.setText(ac);
			 temp=prefs.getString("cullingtree","");
			String pw = null;
			try {
				pw = new String(mcrypt.decrypt(temp.trim()));
				pw =pw.replace('~', ' ').trim();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			passwordTextField.setText(pw);
		}
		Window window = new Window("Login Screen",
				skin.getStyle(WindowStyle.class), "window");
		if(Stardust3d.DEBUG) window.debug();
		window.x = window.y = 0;
		window.setFillParent(true);
		window.setMovable(false);
		window.defaults().pad(5);
		//build table
		window.row().fill().expandX();
		window.add(buttonNewAccount).colspan(1).align("Centre");
		window.add(buttonOptions).colspan(1).align("center");
		window.add(buttonHelp).colspan(1).align("center");
		window.add(buttonLogin).colspan(1).align("right");
		window.row();
		
		window.add(textfieldUserName).minWidth(100).expandX().fillX()
				.colspan(2);
		window.add(passwordTextField).minWidth(100).expandX().fillX()
				.colspan(2);
		window.row();
		window.add(lableNews).align("left");
		window.row();
		window.add(scrollPane2).fill().expand().colspan(4);
		window.row();
		window.add(checkBox).colspan(4);
		window.row();
		window.pack();
		stage.addActor(window);
		SoundManager.init();
		try {
			Renderer.bindMeshes(1);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		textfieldUserName.setTextFieldListener(new TextFieldListener() {
			
			public void keyTyped(TextField textField, char key) {
				if (key == '\n')
					textField.getOnscreenKeyboard().show(false);
			}
		
		});
 
 
		buttonLogin.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				try {
					Renderer.bindMeshes(2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(Stardust3d.DEBUG) System.out.println("login button pressed");
				SoundManager.playuiclick();

				ac = textfieldUserName.getText().toString();
				String pw = passwordTextField.getText().toString();
				
				if (checkpass(pw, pw) && checkuser(ac)) {
					
					String passed = Stardust3d.MyDataOp.login(ac, pw);
					if(Stardust3d.DEBUG) System.out.println("passed "+passed);
					if (passed.equals("true")) {
						SoundManager.playconfirm();
						stage.addActor(Actors.bottomToast("Login sucessful",
    							4, skin));
					
						Stardust3d.mloginOK=true;
						Stardust3d.gameMode=3;
						//get players characters
						Stardust3d.populateCharacterlist(Stardust3d.muser);
					
						doneflag=true;
					  isDone();
					} else if (passed.equals("NOT_ACTIVE")) {
						SoundManager.playError();
						stage.addActor(Actors.bottomToast("Login failed, this account is not active, check your Email.",
    							4, skin));
						

					} else if (passed.equals("false")) {
						SoundManager.playError();
						stage.addActor(Actors.bottomToast("Login failed, please check your account name and password.",
    							4, skin));
						}
				}

			}
		});

		buttonOptions.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				if(Stardust3d.DEBUG) System.out.println("options button pressed");
				SoundManager.playuiclick();
				Stardust3d.gameMode=6;
				doneflag=true;
			}
		});
		buttonNewAccount.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				if(Stardust3d.DEBUG) System.out.println("New account button pressed");
				SoundManager.playuiclick();
				Stardust3d.gameMode=14;
				doneflag=true;
			}
		});
		buttonHelp.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				if(Stardust3d.DEBUG) System.out.println("help button pressed");
				SoundManager.playuiclick();
				Stardust3d.gameMode=9;
				doneflag=true;
			}
		});
		checkBox.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				SoundManager.playuiclick();
				String encrypted = null;
				if(Stardust3d.DEBUG) System.out.println("remeber account name pressed");
				Stardust3d.mremeberAccountname = checkBox.isChecked();
				//if user wants to save login
				if(Stardust3d.mremeberAccountname){
					String pw = passwordTextField.getText().toString();
					String acc = textfieldUserName.getText().toString();
					//check user has entered account details before saving
					if (pw.length()>8 && acc.length()>8){
					
					
					
					/* Encrypt */
					try {
						 encrypted = (mcrypt.encrypt(pw));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					prefs.putBoolean("nopoly", Stardust3d.mremeberAccountname);
	                prefs.putString("un",acc);
					prefs.putString("cullingtree",encrypted);
	                prefs.flush();
					}else{
						stage.addActor(Actors.bottomToast(
								"You need to enter your account name and password before trying to save them.", 4, skin));
						checkBox.setChecked(false);
					}
				}else{
					stage.addActor(Actors.bottomToast(
							"Saved account details cleared.", 4, skin));
					//clear saved account details
					prefs.putBoolean("nopoly", Stardust3d.mremeberAccountname);
	                prefs.putString("un","");
					prefs.putString("cullingtree","");
	                prefs.flush();
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
						if(Stardust3d.DEBUG) System.out.println("fade "+fadeTimer);
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

	private boolean checkpass(String mpw1, String mpw2) {
		// pattern defining only letter, numbers and uppercase letters
		Pattern pattern = Pattern
				.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})");
		Matcher matcher = pattern.matcher(mpw1);
		if (!mpw1.equals(mpw2)) {
			SoundManager.playError();
			stage.addActor(Actors.bottomToast("Both passwords must be the same.",
					4, skin));
			return false;
		} else if (mpw1.length() < 8 || mpw2.length() < 8) {
			SoundManager.playError();
			stage.addActor(Actors.bottomToast("Password must be at least 8 characters long.",
					4, skin));
		
			return false;
		} else if (!matcher.matches()) {
			SoundManager.playError();
			stage.addActor(Actors.bottomToast("Password must only contain letters and numbers, be 8 to 20\n" +
					"characters long, and contain at least one number and capital letter.",
					4, skin));
			return false;
		}
		return true;
	}

	private boolean checkuser(String ac) {
		// pattern defining only letter, numbers and uppercase letters
		Pattern pattern = Pattern.compile("^([A-Za-z0-9])+$");
		Matcher matcher = pattern.matcher(ac);
		if (ac.length() < 8) {
			SoundManager.playError();
			stage.addActor(Actors.bottomToast("Username must be at least 8 characters long.",
					4, skin));
			return false;
		} else if (!matcher.matches()) {
			SoundManager.playError();
			stage.addActor(Actors.bottomToast("Username must only contain letters and numbers.",
					4, skin));
			return false;
		}
		return true;
	}

	public void callOptions() {
		// TODO

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
	/**
	 * Get input multiplexer.
	 *
	 * @return The input multiplexer.
	 */

}
