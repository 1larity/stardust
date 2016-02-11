/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.screens;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.Stardust3d;
import com.digitale.utils.Actors;

/**
 * Screen for creating new player account
 * 
 * @author R Beech
 */
public class NewAccount extends StardustScreen {
	protected static final String LOG_TAG = "New Account";
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };
	public static boolean fadeOut;
	public static boolean fadeIn;
	public static float fadeTimer;
	private String ac;
	private String mpw1;
	private String mpw2;
	private String memail;
	public static int i=0;
	private Timer timer = new Timer();
	Skin skin;

	Stage stage;
	SpriteBatch batch;
	/** the cross fade texture **/
	private Texture xfadeTexture;
	Texture background;
	Actor root;

	protected boolean doneflag=false;

	
	
	public NewAccount() {
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
		final Label labelEmail = new Label("Valid email address (Stardust will send an activation mail to this address).", skin);
		final Label labelAccountName = new Label("Account Name (must contain at least 8 letters or numbers).", skin);
		final Label labelPassword1 = new Label("Password (must be at least 8 letters and include a capital letter and a number).", skin);
		final Label labelPassword2 = new Label("Repeat Password", skin);
		
		final Button buttonCreateAccount = new TextButton("Create Account",
				skin.getStyle(TextButtonStyle.class), "buttonCreateAccount");
		final Button buttonCancel = new TextButton("Cancel",
				skin.getStyle(TextButtonStyle.class), "buttonCancel");
		final TextField textfieldEmail = new TextField("", "Email address",
				skin.getStyle(TextFieldStyle.class), "textfield");
		final TextField textfieldUserName = new TextField("", "Account Name",
				skin.getStyle(TextFieldStyle.class), "textfield");
		

		// configure TextField in password mode.
		final Label lableNews = new Label("News: ", skin);
		final TextField passwordTextField = new TextField("", "Password", skin);
		passwordTextField.setPasswordCharacter('*');
		passwordTextField.setPasswordMode(true);
		//password confirmation box
		final TextField password2TextField = new TextField("", "Repeat password", skin);
		password2TextField.setPasswordCharacter('*');
		password2TextField.setPasswordMode(true);
		
		Window window = new Window("New Account Screen",
				skin.getStyle(WindowStyle.class), "window");
		if(Stardust3d.DEBUG) window.debug();
		window.x = window.y = 0;
		window.setFillParent(true);
		window.setMovable(false);
		window.defaults().pad(5).align("left");
		//build table
		window.row().fill().expandX();
		window.add(buttonCancel).colspan(1);
		window.add();
		window.add(buttonCreateAccount).colspan(1);
		window.row();
		window.add(labelEmail).colspan(3);
		window.row();
		window.add(textfieldEmail).minWidth(400).colspan(3);
		window.row();
		window.add(labelAccountName).colspan(3);
		window.row();
		window.add(textfieldUserName).minWidth(400).colspan(3);
		window.row();
		window.add(labelPassword1).minWidth(400).colspan(3);
		window.row();
		window.add(passwordTextField).minWidth(400).colspan(3);
		window.row();
		window.add(labelPassword2).minWidth(400).colspan(3);
		window.row();
		window.add(password2TextField).minWidth(400).colspan(3);
		window.pack();
		stage.addActor(window);

		textfieldUserName.setTextFieldListener(new TextFieldListener() {
			public void keyTyped(TextField textField, char key) {
				if (key == '\n')
					textField.getOnscreenKeyboard().show(false);
			}
		});

		buttonCreateAccount.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("create account button pressed");
				SoundManager.playuiclick();

				 ac=textfieldUserName.getText().toString();
				 mpw1 =passwordTextField.getText().toString();
				 mpw2 =password2TextField.getText().toString();
				 memail=textfieldEmail.getText().toString();
				
				if (checkEmail(memail.toLowerCase())){
					if (checkuser(ac)){
						if(checkpass(mpw1,mpw2)){
							if (checkuserandpassaredifferent(ac,mpw1)){
					 Stardust3d.MyDataOp.getFreeUser(ac);
							if (Stardust3d.muserExists.equals("0")){
								
								SoundManager.playconfirm();
								   if(Stardust3d.DEBUG) System.out.println(LOG_TAG+ " " + mpw1);
							     	
								 stage.addActor(Actors.bottomToast("Your account has been created, please check your EMail.",
											4, skin));
								Stardust3d.MyDataOp.createUser(ac,mpw1,memail);
											
											//TODO need pause in here so toast can display
									Stardust3d.gameMode=2;
									doneflag=true;
							}else{
								//user name is already in database
								SoundManager.playError();
								stage.addActor(Actors.bottomToast("Sorry, this account name is used, please try another.",
										4, skin)); 
							
							}
							}else{
								//the username and password are the same
								SoundManager.playError();
									stage.addActor(Actors.bottomToast("Your username and password cannot be the same.",
											4, skin)); 
							}
						}else{
							//password is not good enough
							SoundManager.playError();
								stage.addActor(Actors.bottomToast("There is a problem with your password, please check it.",
										4, skin)); 
						}
					}else{
						//username is not good enough
						SoundManager.playError();
							stage.addActor(Actors.bottomToast("There is a problem with your username, please check it.",
									4, skin)); 
					}
				}else{
					//email is not valid
					SoundManager.playError();
						stage.addActor(Actors.bottomToast("This does not appear to be a valid Email address.",
								4, skin)); 
				}
			}
		});

		buttonCancel.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("new account cancel button pressed");
				SoundManager.playuiclick();
				Stardust3d.gameMode=2;
				doneflag=true;
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
	public  boolean checkEmail(String email)  {
		
		Pattern pattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$");
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches()){
			SoundManager.playError();
			stage.addActor(Actors.bottomToast("This does not appear to be a valid Email address",
					4, skin));    
		
			 return false;
		}
		return true;
	}
	private boolean checkpass(String mpw1, String mpw2) {
		//pattern defining only letter, numbers and uppercase letters
		Pattern pattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})");
		Matcher matcher = pattern.matcher(mpw1);
		if (!mpw1.equals(mpw2)){
			SoundManager.playError();
			stage.addActor(Actors.bottomToast("UBoth passwords must be the same.",
					4, skin));
		return false;
		}else if(mpw1.length()<8 || mpw2.length()<8){
			SoundManager.playError();
			stage.addActor(Actors.bottomToast("Password must be at least 8 characters long.",
					4, skin));
			 return false;
		}else if (!matcher.matches()){
			SoundManager.playError();
			stage.addActor(Actors.bottomToast("Password must only contain letters and numbers, be 8 to 20 characters long, and contain at least one number and capital letter.",
					4, skin));
			 return false;
		}else if(mpw1.toLowerCase().contains("password")){
			SoundManager.playError();
			stage.addActor(Actors.bottomToast("Seriously!? You are trying to set your password to 'password', do try harder!",
					4, skin));
		 return false;
		}
		return true;
	}
	
	protected boolean checkuserandpassaredifferent(String ac, String mpw) {
		if(ac.toLowerCase().equals(mpw.toLowerCase())){
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
