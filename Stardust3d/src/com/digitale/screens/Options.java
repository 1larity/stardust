/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.digitale.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.ValueChangedListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.Stardust3d;
import com.digitale.utils.DataOp;
import com.digitale.utils.DataOpImpl;


public class Options extends StardustScreen {
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };
	public DataOp MyDataOp = new DataOpImpl();
	Skin skin;

	Stage stage;
	SpriteBatch batch;
	Texture texture1;
	Texture texture2;
	Texture background;
	Actor root;
	protected boolean doneflag;
	private float originalsfxVolume;
	private float originalMusicVolume;

	public Options() {
		originalsfxVolume=Stardust3d.sfxVolume;
		originalMusicVolume=Stardust3d.musicVolume;

		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));
		background = new Texture(Gdx.files.internal("data/bgpland.jpg"));

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);
		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;

		final Button buttonClose = new TextButton("Close",
				skin.getStyle(TextButtonStyle.class), "buttonClose");
		final Button buttonCancel = new TextButton("Cancel",
				skin.getStyle(TextButtonStyle.class), "buttonCancel");
;
		final CheckBox checkBoxSFX = new CheckBox("Sound Effects",
				skin.getStyle(CheckBoxStyle.class), "checkbox");
		final CheckBox checkBoxFontsize = new CheckBox("Use Small Font",
				skin.getStyle(CheckBoxStyle.class), "checkboxfont");
		 final Slider slidersfx = new Slider(0, 1, .01f, skin.getStyle(SliderStyle.class), "slidersfx");
		 final Slider slidermusic = new Slider(0, 1, .01f, skin.getStyle(SliderStyle.class), "slidersmusic");
		 final Slider slidergfxquality = new Slider(0, 10, 1, skin.getStyle(SliderStyle.class), "slidergfx");
		 slidersfx.setValue(Stardust3d.sfxVolume);
		 slidermusic.setValue(Stardust3d.musicVolume);
		final Label lablesfx = new Label("Sound effect volume",skin.getStyle(LabelStyle.class), "lablesfx");
		final Label lablemusic = new Label("Music volume",skin.getStyle(LabelStyle.class),"lablemusic");
		final Label lablegfx = new Label("Graphics quality",skin.getStyle(LabelStyle.class), "lablegfx");
		
		final Preferences prefs = Gdx.app.getPreferences("stardustpreferences");
		
		
		Window window = new Window("Options Screen",
		skin.getStyle(WindowStyle.class), "window");
		if(Stardust3d.DEBUG)window.debug();
		window.x = window.y = 0;
		window.defaults().pad(5);
		window.setFillParent(true);
		window.setMovable(false);
		//build table
		window.row().expandX().fill();
		window.add();
		window.add();
		window.add(buttonCancel).colspan(1);
		window.add(buttonClose).colspan(1);
		//audio slider lables
		window.row();
		window.add(checkBoxFontsize);
		window.row();
		window.row();
		window.row();
		window.row();
		window.add (lablesfx).minWidth(100).colspan(2);
		window.add (lablemusic).minWidth(100).colspan(2);
		// audio sliders
		window.row().expand();
		window.add(slidersfx).minWidth(100).maxWidth(350). fillX().colspan(2);
		window.add(slidermusic).minWidth(100).maxWidth(350).fillX().colspan(2);
	
		window.row();
	

		window.pack();
		stage.addActor(window);



	 	buttonClose.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("Options Close");
				SoundManager.playuiclick();
				prefs.putFloat("musicvolume",Stardust3d.musicVolume);
                prefs.putFloat("sfxvolume",Stardust3d.sfxVolume);
                prefs.flush();
				if (Stardust3d.mloginOK){
					Stardust3d.gameMode=4;
					 
					doneflag=true;
				}else{
					Stardust3d.gameMode=2;
					doneflag=true;
				}
			}
		});
	 	checkBoxFontsize.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				SoundManager.playuiclick();
				if(Stardust3d.DEBUG) System.out.println("smallfont");
				Stardust3d.tinyfont = checkBoxFontsize.isChecked();
				//if user wants to change fontsize
				prefs.putBoolean("fontsize", Stardust3d.tinyfont);
	            prefs.flush();			
			}
		});
	 	buttonCancel.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("Options Close");
				SoundManager.playuiclick();
				if (Stardust3d.mloginOK){
					Stardust3d.gameMode=4;
					resetSettings();
					doneflag=true;
				}else{
					Stardust3d.gameMode=2;
					doneflag=true;
				}
			}
		});
	 	slidersfx.setValueChangedListener(new ValueChangedListener() {
            public void changed (Slider slider, float value) {
            		if (value==0) value=0.001f;
            		Stardust3d.sfxVolume=value;
                    Gdx.app.log("Options", "sfxslider: " + value);              
                    SoundManager.setSFXVolume();
                    SoundManager.playconfirm();
                               }
    });
	 	slidermusic.setValueChangedListener(new ValueChangedListener() {
            public void changed (Slider slider, float value) {
            	if (value==0) value=0.001f;
            	Stardust3d.musicVolume=value;
                    Gdx.app.log("Options", "musicslider: " + value);
                    SoundManager.setMusicVolume();
                   
            }
    });
	}

	protected void resetSettings() {
		Stardust3d.sfxVolume=originalsfxVolume;
		Stardust3d.musicVolume=originalMusicVolume;
		 SoundManager.setMusicVolume();
		
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		batch.setColor(Color.WHITE);
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(), 0, 0, 1024, 512, false, false);
		batch.end();
		((CheckBox)stage.findActor("checkboxfont")).setChecked( Stardust3d.tinyfont);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();

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
		background.dispose();

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
