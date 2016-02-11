/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.digitale.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane.SplitPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.utils.Scaling;
import com.digitale.connex.Inventory;
import com.digitale.mygdxgame.Renderer;
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.Stardust3d;
import com.digitale.sim.Ship;
import com.digitale.utils.Actors;
import com.digitale.utils.InventoryIcon;
import com.digitale.utils.Actors.DialogListener;

/**
 * factory screen
 * 
 * 
 * 
 * @author rbeech
 */
public class FactoryScreen extends StardustScreen {
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };

	Skin skin;
	Actor dialog;
	Actor root;
	
	/** the cross fade texture **/

	protected boolean doneflag;

	protected String selectedCharacter;

	public FactoryScreen(Stage stage) {
		
		Stardust3d.myInventory.clear();
		Stardust3d.MyDataOp.get3dChar(Integer.valueOf(Stardust3d.currencharacteruid));
		Stardust3d.MyDataOp.getInventory(Integer
				.valueOf(Stardust3d.currencharacteruid),false);
		
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));
		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;
		final Label tradegoodsLabel = new Label("Your Tradegoods",
				skin.getStyle(LabelStyle.class), "tradegoodslable");
		final Label blueprintsLabel = new Label("Your Blueprints",
				skin.getStyle(LabelStyle.class), "blueprintslable");
		final Button buttonClose = new TextButton("Close",
				skin.getStyle(TextButtonStyle.class), "button-close");
		final Button buttonBuild = new TextButton("Build",
				skin.getStyle(TextButtonStyle.class), "buttonbuild");
		final Label balance = new Label(Stardust3d.myCharacter.getCredits()+" $D",
				skin.getStyle(LabelStyle.class), "balancelable");
		final Table gridCargo = initBlueprints();
		
		final Table gridWarehouse = initTradegoods();
		final ScrollPane paneCargo = new ScrollPane(gridCargo,
				skin.getStyle(ScrollPaneStyle.class), "blueprints");
		final ScrollPane paneWarehouse = new ScrollPane(gridWarehouse,
				skin.getStyle(ScrollPaneStyle.class), "tradegoods");
		final SplitPane splitPane = new SplitPane(paneCargo, paneWarehouse,
				false,
				skin.getStyle("default-horizontal", SplitPaneStyle.class),
				"split");

		Window window = new Window("Factory Screen",
				skin.getStyle(WindowStyle.class), "factoryWindow");
		if (Stardust3d.DEBUG)
			window.debug();
		window.x = window.y = 0;
		window.setFillParent(true);
		window.setMovable(false);
		window.defaults().pad(5);
		window.defaults().spaceBottom(5);
		window.row().fill().expandX();
		window.add(balance);
		window.add();
		window.add(buttonBuild).minWidth(150);
		window.add(buttonClose).minWidth(150);
		window.row();
		window.add(blueprintsLabel).colspan(2);
		window.add(tradegoodsLabel).colspan(2);
		window.row().fill().expandY();
		window.add(splitPane).colspan(4);
		window.pack();

		stage.addActor(window);
		buttonClose.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("factory Close");
				SoundManager.playuiclick();
				Stardust3d.MyDataOp.getInventory(Integer
						.valueOf(Stardust3d.currencharacteruid),false);	
				Stardust3d.stationScreen=111;
				//Renderer.stage.clear();
			}
		});
		buttonBuild.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("build item");
				SoundManager.playuiclick();
				//Stardust3d.MyDataOp.newRequest("movealltowarehouse#");
				for (int i = 0; i < Stardust3d.myInventory.size(); i++) {
					Inventory item = Stardust3d.myInventory.get(i);
					if (item.getSlot_id() == 0) {
						
						//Renderer.stage.findActor(""+item.getInventoryid()).remove();
					}
				}
			}
		});
	}

	/**
	 * @return
	 */
	private Table initBlueprints() {
		final Table gridCargo = new Table();
		if (Stardust3d.DEBUG)
			gridCargo.debug();
		String itemIcon;
		int itemcounter = 0;
		gridCargo.defaults().height(64).width(64).align("topleft");
		for (int x = 0; x < Stardust3d.myInventory.size(); x++) {

			final Inventory item = Stardust3d.myInventory.get(x);
			itemIcon = "data/" + item.getIcon() + ".png";

			if (Stardust3d.DEBUG) {
				System.out.println("icon " + itemIcon);
			}
			if (item.getCategory().equals("blueprint") ){

				String uidString =""+ item.getInventoryid();
				final Image newItem = new Image(new TextureRegion(
						InventoryIcon.generateDynamicTexture(itemIcon, item.getCategory(),
								item.getQuality(),false,item.getCount())), Scaling.none,
						Align.CENTER, uidString);
				newItem.setClickListener(new ClickListener() {
					public void click(Actor actor, float x, float y) {
						SoundManager.playuiclick();
						if (Stardust3d.DEBUG) {
							System.out.println("cargo pick" + newItem.name);
							
						}
						
						String dialogTitle = (item.getItem());
						String dialogTexts = ("\nCategory: "
								+ item.getCategory() + " " + "  Type:- "
								+ item.getSubcat() + "\n\n"
								+ item.getDescription() + "\n\n" + "Quality:"
								+ decodeQuality(item.getQuality()) + "   Lvl:"
								+ item.getLevel() + "  Mass:" + item.getMass()
								+ "kg\n" + "Value:" + item.getValue() + "$D"
								+ "   Contraband:" + item.getContraband() + "\n"
								+ "Effect:" + item.getEffect()
								+ "   Bind: "+decodeBind(item.getBind()));

						DialogListener dialogListener = (new DialogListener() {
							@Override
							public void optionSelected(int option) {
								SoundManager.playuiclick();
								System.out.println("option " + option);
								//if close
								if (option == 1) {
								Renderer.stage.removeActor(dialog);
								}
								//if build
								if (option == 0) {
									SoundManager.playCoins();
									//check funds
									//check materials
									//create item in warehouse
									//delete materials
									
									
									//Renderer.stage.removeActor(newItem);
									Renderer.stage.removeActor(dialog);
									;
								}
							}

							
						});

						dialog = (Actors.twoOptionsDialog(dialogTexts,
								dialogListener, dialogTitle, "Build", "Close",
								skin));
						Renderer.stage.addActor(dialog);
						SoundManager.playuiclick();

					}

				});
				gridCargo.add(newItem);
				itemcounter++;
			}
			if (itemcounter % 10 == 9) {
				gridCargo.row();
			}
		}
		gridCargo.pack();
		return gridCargo;
	}
	private void refreshVendorGrid() {
		// TODO Auto-generated method stub
		
	}
	private String decodeQuality(int quality) {
		switch (quality) {
		case 0:
			return "Commodity";
		case 1:
			return "Mundane";
		case 2:
			return "Commmon";
		case 3:
			return "Plain";
		case 4:
			return "Uncommon";
		case 5:
			return "Rare";
		case 6:
			return "Grand";
		case 7:
			return "Epic";
		case 8:
			return "Legendary";
		case 9:
			return "Mythical";

		}
		return null;
	}

	private Table initTradegoods() {
		System.out.println("init warehouse " );
		final Table gridWarehouse = new Table();
		if (Stardust3d.DEBUG)
			gridWarehouse.debug();
		String itemIcon;
		
		int itemcounter = 0;
		gridWarehouse.defaults().height(64).width(64).align("topleft");
		for (int x = 0; x < Stardust3d.myInventory.size(); x++) {
			System.out.println("init vendor id "+x );
			final Inventory item = Stardust3d.myInventory.get(x);
			itemIcon = "data/" + item.getIcon() + ".png";

			if (Stardust3d.DEBUG) {
				System.out.println("vendor icon " + itemIcon);
			}
			if (item.getCategory().equals("tradegoods")) {

				String uidString =""+ item.getInventoryid();
				final Image newItem = new Image(new TextureRegion(
						InventoryIcon.generateDynamicTexture(itemIcon, item.getCategory(),
								item.getQuality(),false,item.getCount())), Scaling.none,
						Align.CENTER, uidString);
				newItem.setClickListener(new ClickListener() {
					public void click(Actor actor, float x, float y) {
						SoundManager.playuiclick();
						if (Stardust3d.DEBUG) {
							System.out.println("warehouse pick" + newItem.name);
						
						}
						
						String dialogTitle = (item.getItem());
						String dialogTexts = ("\nCategory: "
								+ item.getCategory() + " " + "  Type:- "
								+ item.getSubcat() + "\n\n"
								+ item.getDescription() + "\n\n" + "Quality:"
								+ decodeQuality(item.getQuality()) + "   Lvl:"
								+ item.getLevel() + "  Mass:" + item.getMass()
								+ "kg\n" + "Value:" + item.getValue() + "$D"
								+ "   Contraband:" + item.getContraband() + "\n"
								+ "Effect:" + item.getEffect()
								+ "   Bind: "+decodeBind(item.getBind()));

						DialogListener dialogListener = (new DialogListener() {
							@Override
							public void optionSelected(int option) {
								SoundManager.playuiclick();
								System.out.println("option " + option);
								//if close
								if (option == 1) {
								Renderer.stage.removeActor(dialog);
								}
								//if sell
								if (option == 0) {
									//SoundManager.playCoins();
									//System.out.println("to cargo "
									//		+ newItem.name );
									//Stardust3d.MyDataOp.newRequest("movetocargo#"
									//		+newItem.name);
									
									//need to work out how to add item to other grid
									//Renderer.stage.removeActor(newItem);
									Renderer.stage.removeActor(dialog);
									
								}
							}
						});

						dialog = (Actors.twoOptionsDialog(dialogTexts,
								dialogListener, dialogTitle, "Nothing", "Close",
								skin));
						Renderer.stage.addActor(dialog);
						SoundManager.playuiclick();

					}

				});
				gridWarehouse.add(newItem);
				itemcounter++;
			}
			if (itemcounter % 10 == 9) {
				gridWarehouse.row();
			}
		}
		gridWarehouse.pack();
		return gridWarehouse;
	}

	@Override
	public void render(float delta) {
	
		Table.drawDebug(Renderer.stage);
	}

	
	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void dispose() {
		
		skin.dispose();
		

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

	private String decodeBind(int bind) {
		switch (bind) {
		case 0:
			return "No binding";
		case 1:
			return "Bind on use";
		case 2:
			return "Character Bound";
		
	
		}
		return null;
	}

}
