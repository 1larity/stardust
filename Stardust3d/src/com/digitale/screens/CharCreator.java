/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.screens;

import java.util.Timer;
import java.util.TimerTask;

import com.digitale.utils.Actors;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.FlickScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectionListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane.SplitPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.digitale.utils.Actors.DialogListener;
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.Stardust3d;

/**
 * Screen for creating new player character
 * 
 * @author R Beech
 */
public class CharCreator extends StardustScreen {
	protected static final String LOG_TAG = "character creator ";
	String[] listEntries = { "", "", "", "", "", " ", "", "", "", "" };
	String[] listsexEntries = { "Male", "Female" };
	String[] listraceEntries = { "Human", "Jelkek", "Orinian", "Gulhurg" };
	public static boolean fadeOut;
	public static boolean fadeIn;
	public static float fadeTimer;
	public static int i = 0;
	private Timer timer = new Timer();
	Skin skin;
	Stage stage;
	Actor dialog;
	SpriteBatch batch;
	Texture texture0 = new Texture(Gdx.files.internal("data/avatar00.jpg"));
	Texture texture1 = new Texture(Gdx.files.internal("data/avatar01.jpg"));
	Texture texture2 = new Texture(Gdx.files.internal("data/avatar02.jpg"));
	Texture texture3 = new Texture(Gdx.files.internal("data/avatar03.jpg"));
	Texture texture4 = new Texture(Gdx.files.internal("data/avatar04.jpg"));
	Texture texture5 = new Texture(Gdx.files.internal("data/avatar05.jpg"));
	Texture texture6 = new Texture(Gdx.files.internal("data/avatar06.jpg"));
	Texture texture7 = new Texture(Gdx.files.internal("data/avatar07.jpg"));
	Texture texture8 = new Texture(Gdx.files.internal("data/avatar08.jpg"));
	Texture texture9 = new Texture(Gdx.files.internal("data/avatar09.jpg"));
	Actor root;
	Texture background;
	/** the cross fade texture **/
	private Texture xfadeTexture;
	TextureRegion image0 = new TextureRegion(texture0);
	TextureRegion image1 = new TextureRegion(texture1);
	TextureRegion image2 = new TextureRegion(texture2);
	TextureRegion image3 = new TextureRegion(texture3);
	TextureRegion image4 = new TextureRegion(texture4);
	TextureRegion image5 = new TextureRegion(texture5);
	TextureRegion image6 = new TextureRegion(texture6);
	TextureRegion image7 = new TextureRegion(texture7);
	TextureRegion image8 = new TextureRegion(texture8);
	TextureRegion image9 = new TextureRegion(texture9);
	Image imageActor;
	protected boolean doneflag;
	protected String selectedAvatar;
	protected String racetext;
	protected String selectedSex = "Male";
	protected String selectedRace = "Human";
	protected String PrimaryStat;

	public CharCreator(final Integer muser) {
		Stardust3d.currencharacteruid = "" + Stardust3d.charList[0].getUid();
		// populate avatardropdown
		for (int i = 0; i < Stardust3d.avatarList.size(); i++) {
			listEntries[i] = Stardust3d.avatarList.get(i).getDescription();
			// do avatar dropdown population from db here
		}
		selectedAvatar = ("avatar00.jpg");
		OrthographicCamera camera;
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));
		xfadeTexture = new Texture(Gdx.files.internal("data/blackpixel.png"),
				Format.RGB565, true);
		xfadeTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
		background = new Texture(Gdx.files.internal("data/bgpland.jpg"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.app.getGraphics().getWidth(), Gdx.app
				.getGraphics().getHeight());

		fadeIn();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);
		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;

		final Button buttonSaveCharacter = new TextButton("Save Character",
				skin.getStyle(TextButtonStyle.class), "button-save-char");
		final Button buttonCancel = new TextButton("Cancel",
				skin.getStyle(TextButtonStyle.class), "button-cancel");
		final Button buttonRandName = new TextButton("Generate Random Name",
				skin.getStyle(TextButtonStyle.class), "button-random-name");
		final SelectBox dropdown = new SelectBox(listEntries,
				skin.getStyle(SelectBoxStyle.class), "combo");
		final SelectBox dropdownsex = new SelectBox(listsexEntries,
				skin.getStyle(SelectBoxStyle.class), "combosex");
		final SelectBox dropdownrace = new SelectBox(listraceEntries,
				skin.getStyle(SelectBoxStyle.class), "comborace");
		final TextField textfieldFirstName = new TextField("", "Firstname",
				skin.getStyle(TextFieldStyle.class), "textfieldfirstname");
		final TextField textfieldSurName = new TextField("", "Surname",
				skin.getStyle(TextFieldStyle.class), "textfieldsurname");
		final Label lableracepicker = new Label("Race",
				skin.getStyle(LabelStyle.class), "label-race-picker");
		final Label lablesexpicker = new Label("Sex",
				skin.getStyle(LabelStyle.class), "label-sex-picker");
		final Label lableportraitpicker = new Label("Portrait",
				skin.getStyle(LabelStyle.class), "label-portrait-picker");
		final Label lablefirstname = new Label("First name",
				skin.getStyle(LabelStyle.class), "label-firstname");
		final Label lablesurname = new Label("Surname",
				skin.getStyle(LabelStyle.class), "label-surname");

		imageActor = new Image(image0);

		final FlickScrollPane scrollPane = new FlickScrollPane(imageActor,
				"flickscroll");
		final List list = new List(listEntries, skin.getStyle(ListStyle.class),
				"list");
		final ScrollPane scrollPane2 = new ScrollPane(list,
				skin.getStyle(ScrollPaneStyle.class), "scroll");
		scrollPane2.setWidget(list);
		final SplitPane splitPane = new SplitPane(scrollPane, scrollPane,
				false,
				skin.getStyle("default-horizontal", SplitPaneStyle.class),
				"split");

		final Label lablerace = new Label(racetext,
				skin.getStyle(LabelStyle.class), "label-race");
		lablerace.setWrap(true);
		final ScrollPane scrollPanerace = new ScrollPane(lablerace,
				skin.getStyle(ScrollPaneStyle.class), "scroll");
		// set defaults
		selectedAvatar = (Stardust3d.avatarList.get(0).getImagename());

		racetext = "Humans small size compared to other races, nimble hands and excellent sight allows them to handle thier ships with more finesse than other races.";
		racetext = racetext + "\n\n Racial Bonus:- +1 to dexterity.";
		racetext = racetext + "\n Skilled with Human ships and weaponry.";
		PrimaryStat = "Stamina " + 1 + "\nIntelligence " + 1 + "\nSocial " + 1
				+ "\nDexterity " + 2 + "\nLeadership " + 1 + "\nRecuperation "
				+ 1 + "";
		final Label PrimaryStatLabel = new Label(PrimaryStat
				+ Stardust3d.myCharacter.getIntelligence(),
				skin.getStyle(LabelStyle.class), "primary-stat");
		Window window = new Window("Create Character",
				skin.getStyle(WindowStyle.class), "window");
		if (Stardust3d.DEBUG)
			window.debug();
		window.x = window.y = 0;
		window.setFillParent(true);
		window.setMovable(false);
		window.defaults().pad(5);
		window.defaults().spaceBottom(5).align("top");
		window.row().fill().expandX();
		window.add(buttonCancel).colspan(2);
		window.add().colspan(2).maxWidth(200);
		window.add(buttonSaveCharacter).colspan(2);
		window.row();
		window.add(lableracepicker).align("middleleft");
		window.add(dropdownrace).colspan(1).maxHeight(32);
		window.add(lableportraitpicker).align("middleleft");
		window.add(dropdown).colspan(1).maxHeight(32);
		window.add(lablesexpicker).align("middleleft");
		window.add(dropdownsex).colspan(1).maxHeight(32);
		window.row();
		window.add(scrollPanerace).fill().expand().colspan(2).align("topleft");
		window.add(PrimaryStatLabel).colspan(2);
		window.add().minHeight(256).colspan(2);
		window.row();
		window.add(lablefirstname).align("middleleft");
		window.add(textfieldFirstName);
		window.add(lablesurname).align("middleleft");
		window.add(textfieldSurName);
		window.add(buttonRandName).colspan(2);

		window.pack();

		stage.addActor(window);

		dropdown.setSelectionListener(new SelectionListener() {
			@Override
			public void selected(Actor actor, int index, String value) {
				SoundManager.playuiclick();
				selectedAvatar = (Stardust3d.avatarList.get(index)
						.getImagename());

			}
		});
		dropdownsex.setSelectionListener(new SelectionListener() {
			@Override
			public void selected(Actor actor, int index, String value) {
				SoundManager.playuiclick();
				selectedSex = value;

			}
		});
		dropdownrace.setSelectionListener(new SelectionListener() {
			@Override
			public void selected(Actor actor, int index, String value) {
				SoundManager.playuiclick();
				switch (index) {
				case 0:
					racetext = "Humans small size compared to other races, nimble hands and excellent sight allows them to handle thier ships with more finesse than other races.";
					racetext = racetext
							+ "\n\n Racial Bonus:- +1 to dexterity.";
					racetext = racetext
							+ "\n Skilled with Human ships and weaponry.";
					selectedRace = "Human";

					PrimaryStat = "Stamina " + 1 + "\nIntelligence " + 1
							+ "\nSocial " + 1 + "\nDexterity " + 2
							+ "\nLeadership " + 1 + "\nRecuperation " + 1;
					break;
				case 1:
					racetext = "Jelkek are a war-like race, for generations they have subjugated the Orinians to further their technological advancement. Fearless and bloodthirsty tendencies make their combat pilots unequalled.";
					racetext = racetext
							+ "\n\n Racial Bonus:- +1 to recuperation.";
					racetext = racetext
							+ "\n Skilled with Jelkek ships and weaponry.";
					selectedRace = "Jelkek";
					PrimaryStat = "Stamina " + 1 + "\nIntelligence " + 1
							+ "\nSocial " + 1 + "\nDexterity " + 1
							+ "\nLeadership " + 1 + "\nRecuperation " + 2;
					break;
				case 2:
					racetext = "Orinians are masters of biotechnology, their skills make them the foremost manufacturers in the universe.";
					racetext = racetext
							+ "\n\n Racial Bonus:- +1 to intelligence.";
					racetext = racetext
							+ "\n Skilled with Orinian ships and weaponry.";
					selectedRace = "Orinian";
					PrimaryStat = "Stamina " + 1 + "\nIntelligence " + 2
							+ "\nSocial " + 1 + "\nDexterity " + 1
							+ "\nLeadership " + 1 + "\nRecuperation " + 1;
					break;
				case 3:
					racetext = "Gulhurg colonies are unarguably the most complex systems known to science. Their hive-like social structure gives them an unrivalled empathy with others, making them excellent traders.";
					racetext = racetext + "\n\n Racial Bonus:- +1 to social.";
					racetext = racetext
							+ "\n Skilled with Gulhurg ships and weaponry.";
					selectedRace = "Gulhurg";
					PrimaryStat = "Stamina " + 1 + "\nIntelligence " + 1
							+ "\nSocial " + 2 + "\nDexterity " + 1
							+ "\nLeadership " + 1 + "\nRecuperation " + 1;
					break;
				}
			}
		});
		buttonSaveCharacter.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				if (Stardust3d.DEBUG)
					System.out.println("Charmaker Complete");

				if (textfieldFirstName.getText().length() < 1) {
					SoundManager.playError();
					stage.addActor(Actors.bottomToast(
							"Character first name cannot be blank.", 4, skin));
				} else {
					SoundManager.playuiclick();
					// populate game from db, based on this char
					// Stardust3d.gameMode = 4;

					String result = Stardust3d.MyDataOp.makeAvatar(
							selectedAvatar, selectedRace, selectedSex,
							textfieldFirstName.getText(),
							textfieldSurName.getText(), muser);
					if (Stardust3d.DEBUG)
						System.out.println(LOG_TAG
								+ "Everworld: char creation result: " + result);
					if (result.trim().equals("ok")) {
						Stardust3d.gameMode = 3;
						// repopulate players characters
						Stardust3d.populateCharacterlist(Stardust3d.muser);

						doneflag = true;
					} else {
						SoundManager.playError();
						stage.addActor(Actors
								.bottomToast(
										"This character name is taken, please choose another.",
										4, skin));
					}
				}

			}
		});
		buttonCancel.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {

				SoundManager.playuiclick();

				stage.addActor(Actors.bottomToast(
						"Cancelling character creation", 4, skin));
				doneflag = true;
				Stardust3d.gameMode = 3;
			}

		});
		buttonRandName.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {

				SoundManager.playuiclick();
				// generate human female name
				if (selectedSex.equals("Female")
						&& selectedRace.equals("Human")) {
					Stardust3d.MyDataOp.getRandomHumanFemaleName();
					textfieldFirstName.setText(Stardust3d.generatedFirstName);
					textfieldSurName.setText(Stardust3d.generatedSurName);
					// generate human male name
				} else if (selectedSex.equals("Male")
						&& selectedRace.equals("Human")) {
					Stardust3d.MyDataOp.getRandomHumanMaleName();
					textfieldFirstName.setText(Stardust3d.generatedFirstName);
					textfieldSurName.setText(Stardust3d.generatedSurName);

					// generate Jelkek female name
				} else if (selectedSex.equals("Female")
						&& selectedRace.equals("Jelkek")) {
					Stardust3d.MyDataOp.getRandomHumanMaleName();
					textfieldFirstName.setText(Stardust3d.generatedFirstName);
					textfieldSurName.setText(Stardust3d.generatedSurName);
					// generate Jelkek male name
				} else if (selectedSex.equals("Male")
						&& selectedRace.equals("Jelkek")) {
					Stardust3d.MyDataOp.getRandomHumanMaleName();
					textfieldFirstName.setText(Stardust3d.generatedFirstName);
					textfieldSurName.setText(Stardust3d.generatedSurName);

					// generate Orinian female name
				} else if (selectedSex.equals("Female")
						&& selectedRace.equals("Orinian")) {
					Stardust3d.MyDataOp.getRandomHumanMaleName();
					textfieldFirstName.setText(Stardust3d.generatedFirstName);
					textfieldSurName.setText(Stardust3d.generatedSurName);
					// generate Orinain male name
				} else if (selectedSex.equals("Male")
						&& selectedRace.equals("Orinain")) {
					Stardust3d.MyDataOp.getRandomHumanMaleName();
					textfieldFirstName.setText(Stardust3d.generatedFirstName);
					textfieldSurName.setText(Stardust3d.generatedSurName);

					// generate Gulhurg female name
				} else if (selectedSex.equals("Female")
						&& selectedRace.equals("Gulhurg")) {
					Stardust3d.MyDataOp.getRandomHumanMaleName();
					textfieldFirstName.setText(Stardust3d.generatedFirstName);
					textfieldSurName.setText(Stardust3d.generatedSurName);
					// generate Gulhurg male name
				} else if (selectedSex.equals("Male")
						&& selectedRace.equals("Gulhurg")) {
					Stardust3d.MyDataOp.getRandomHumanMaleName();
					textfieldFirstName.setText(Stardust3d.generatedFirstName);
					textfieldSurName.setText(Stardust3d.generatedSurName);
				}
			}

		});
		DialogListener dialogListener = (new DialogListener() {

			@Override
			public void optionSelected(int option) {
				if (Stardust3d.DEBUG)
					System.out.println("option " + option);
				if (option == 1) {
					if (Stardust3d.DEBUG)
						System.out.println("no");
					stage.removeActor(dialog);
				}
				if (option == 0) {
					if (Stardust3d.DEBUG)
						System.out.println("yes");
					stage.removeActor(dialog);
				}

			}

		});

	}

	private void fadeIn() {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				i++;

				// detect and set white fade in
				if (i < 10 && fadeIn == false) {
					fadeIn = false;
					fadeTimer = 1;
				}
				if (fadeIn) {
					fadeTimer = fadeTimer - 0.05f;
					if (Stardust3d.DEBUG)
						System.out.println("fade " + fadeTimer);
				}
				if (fadeIn && fadeTimer > 10) {
					fadeIn = false;
					fadeTimer = 0;
				}

				if (fadeTimer < 0) {
					fadeTimer = 0;
					timer.cancel();
				}

			}
		}, 0, 100);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		int width = Gdx.app.getGraphics().getWidth();
		int height = Gdx.app.getGraphics().getHeight();
		batch.begin();
		batch.setColor(Color.WHITE);
		batch.draw(background, 0, 0, width, height, 0, 0, 1024, 512, false,
				false);
		batch.end();
		((Label) stage.findActor("label-race")).setText(racetext);
		((Label) stage.findActor("primary-stat")).setText(PrimaryStat);

		if (selectedAvatar.equals("avatar00.jpg")) {
			imageActor = new Image(image0);
		}
		if (selectedAvatar.equals("avatar01.jpg")) {
			imageActor = new Image(image1);
		}
		if (selectedAvatar.equals("avatar02.jpg")) {
			imageActor = new Image(image2);
		}
		if (selectedAvatar.equals("avatar03.jpg")) {
			imageActor = new Image(image3);
		}
		if (selectedAvatar.equals("avatar04.jpg")) {
			imageActor = new Image(image4);
		}
		if (selectedAvatar.equals("avatar05.jpg")) {
			imageActor = new Image(image5);
		}
		if (selectedAvatar.equals("avatar06.jpg")) {
			imageActor = new Image(image6);
		}
		if (selectedAvatar.equals("avatar07.jpg")) {
			imageActor = new Image(image7);
		}
		if (selectedAvatar.equals("avatar08.jpg")) {
			imageActor = new Image(image8);
		}
		if (selectedAvatar.equals("avatar09.jpg")) {
			imageActor = new Image(image9);
		}
		imageActor.x = width - 210;
		imageActor.y = 100;
		imageActor.scaleX = 3;
		imageActor.scaleY = 3;
		stage.addActor(imageActor);

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		if (fadeIn) {
			batch.enableBlending();
			batch.setBlendFunction(GL10.GL_SRC_ALPHA,
					GL10.GL_ONE_MINUS_SRC_ALPHA);
			batch.begin();
			batch.setColor(1, 1, 1, fadeTimer);
			batch.draw(xfadeTexture, -100, -100, width + 200, height + 200);
			batch.end();
		}

		if (Stardust3d.DEBUG)
			Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		texture1.dispose();
		texture2.dispose();
		texture3.dispose();
		texture4.dispose();
		texture5.dispose();
		texture6.dispose();
		texture7.dispose();
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
