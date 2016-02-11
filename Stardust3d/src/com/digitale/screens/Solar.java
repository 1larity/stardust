/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.digitale.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.digitale.utils.Util;
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.Stardust3d;


public class Solar extends StardustScreen {
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };

	Skin skin;
	Stage stage;
	SpriteBatch batch;
	Texture texture1;
	Texture texture2;
	Actor root;

	protected boolean doneflag;

	protected String selectedCharacter;

	public Solar() {
		for (int i = 0; i < Stardust3d.charList.length; i++) {
			listEntries[i] = (Stardust3d.charList[i].getFirstname() + " \n"
					+ Stardust3d.charList[i].getSurname() + " \n " + "10000c ");
			listEntries[i] = listEntries[i]
					+ ("System " + Stardust3d.charList[i].getSystem()
							+ " \nSta " + Stardust3d.charList[i].getStamina()
							+ " Int "
							+ Stardust3d.charList[i].getIntelligence()
							+ " Soc " + Stardust3d.charList[i].getSocial()
							+ " Dex " + Stardust3d.charList[i].getDexterity()
							+ " Led " + Stardust3d.charList[i].getLeadership()
							+ " Rec "
							+ Stardust3d.charList[i].getRecuperation()
							+ " \nFlying: " + Util
							.asCapFirstChar(Stardust3d.charList[i]
									.getShipname()));
		}
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));
		texture1 = new Texture(Gdx.files.internal("data/badlogicsmall.jpg"));
		texture2 = new Texture(Gdx.files.internal("data/badlogic.jpg"));
		TextureRegion image = new TextureRegion(texture1);
		TextureRegion image2 = new TextureRegion(texture2);
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);
		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;
		final Label nameLabel = new Label(Stardust3d.myCharacter.getFirstname()
				+ " \n" + Stardust3d.myCharacter.getSurname(),
				skin.getStyle(LabelStyle.class), "namelable");
		final Label IntelligenceLabel = new Label("Inteligence "
				+ Stardust3d.myCharacter.getIntelligence(),
				skin.getStyle(LabelStyle.class), "intelligencelable");

		final Button buttonEnterGame = new TextButton("Enter Game",
				skin.getStyle(TextButtonStyle.class), "button-enter-game");
		final Button buttonNewCharacter = new TextButton("New Character",
				skin.getStyle(TextButtonStyle.class), "button-enter-game");
		final Button buttonDeleteCharacter = new TextButton("Delete Character",
				skin.getStyle(TextButtonStyle.class), "button-enter-game");
		final TextField textfield = new TextField("", "Click here!",
				skin.getStyle(TextFieldStyle.class), "textfield");
		final SelectBox dropdown = new SelectBox(listEntries,
				skin.getStyle(SelectBoxStyle.class), "combo");
		final Image imageActor = new Image(image2);
		final FlickScrollPane scrollPane = new FlickScrollPane(imageActor,
				"flickscroll");
		final List list = new List(listEntries, skin.getStyle(ListStyle.class),
				"list");
		final ScrollPane scrollPane2 = new ScrollPane(list,
				skin.getStyle(ScrollPaneStyle.class), "scroll");
		scrollPane2.setWidget(list);
		final SplitPane splitPane = new SplitPane(scrollPane, scrollPane2,
				false,
				skin.getStyle("default-horizontal", SplitPaneStyle.class),
				"split");

		final Label fpsLabel = new Label("fps:",
				skin.getStyle(LabelStyle.class), "label");

		selectedCharacter = ("Character: "
				+ Stardust3d.charList[0].getFirstname() + " \n" + Stardust3d.charList[0]
				.getSurname());

		// window.debug();
		Window window = new Window("Solar Info Screen",
				skin.getStyle(WindowStyle.class), "window");
		window.x = window.y = 0;
		window.setFillParent(true);
		window.setMovable(false);
		window.defaults().pad(5);
		window.defaults().spaceBottom(5);
		window.row().fill().expandX();
		window.add(buttonNewCharacter);
		window.add(buttonDeleteCharacter);
		window.add(buttonEnterGame);
		window.row();
		window.add(nameLabel);
		window.row();
		window.add(IntelligenceLabel);
		window.add(IntelligenceLabel);
		window.add(IntelligenceLabel);
		window.add(IntelligenceLabel);
		window.add(IntelligenceLabel);
		window.row();
		// window.add(setWidget(list));
		window.row();
		window.add(splitPane).colspan(4);
		window.row();
		window.row();
		window.add(fpsLabel).colspan(4);
		window.pack();

		// stage.addActor(new Button("Behind Window", skin));
		stage.addActor(window);

		textfield.setTextFieldListener(new TextFieldListener() {
			public void keyTyped(TextField textField, char key) {
				if (key == '\n')
					textField.getOnscreenKeyboard().show(false);
			}
		});

		dropdown.setSelectionListener(new SelectionListener() {
			@Override
			public void selected(Actor actor, int index, String value) {
				SoundManager.playuiclick();
				selectedCharacter = ("Character: "
						+ Stardust3d.charList[index].getFirstname() + " \n" + Stardust3d.charList[index]
						.getSurname());
				Stardust3d.currencharacteruid = "" + Stardust3d.charList[index].getUid();

			}
		});

		buttonEnterGame.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("Charpicker Close");
				SoundManager.playuiclick();
				// populate game from db, based on this char
				Stardust3d.gameMode = 4;
				// Stardust3d.character="35";
			/*	Stardust3d.MyDataOp.get3dChar(Integer
						.valueOf(Stardust3d.currencharacteruid));
				Stardust3d.MyDataOp.getInventory(Integer
						.valueOf(Stardust3d.currencharacteruid));
				Stardust3d.MyDataOp.getSolarSystem(
						Stardust3d.myCharacter.getX(),
						Stardust3d.myCharacter.getY(),
						Stardust3d.myCharacter.getZ());*/

				doneflag = true;

			}
		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		((Label) stage.findActor("label")).setText("Selected Character: "
				+ selectedCharacter);

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
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
