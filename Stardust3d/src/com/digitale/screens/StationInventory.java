/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.screens;

import com.badlogic.gdx.Gdx;
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
import com.digitale.utils.Actors;
import com.digitale.utils.Actors.DialogListener;
import com.digitale.utils.InventoryIcon;
import com.digitale.utils.InventoryUtils;

/**
 * inventory screen needs 2 forms one for when ship is docked that allows
 * trading of items and one for when ship is in space that only displays ship
 * cargo
 * 
 * @author rbeech
 */
public class StationInventory extends StardustScreen {
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };

	Skin skin;
	Actor dialog;
	Actor root;
	
	/** the cross fade texture **/

	protected boolean doneflag;

	protected String selectedCharacter;

	private boolean iscurrentship;

	public StationInventory(Stage stage) {

		Stardust3d.myInventory.clear();
		Stardust3d.MyDataOp.get3dChar(Integer
				.valueOf(Stardust3d.currencharacteruid));
		Stardust3d.MyDataOp.getInventory(Integer
				.valueOf(Stardust3d.currencharacteruid),false);
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));
		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;
		final Label warehouseLabel = new Label("Warehouse",
				skin.getStyle(LabelStyle.class), "warehouselable");
		final Label cargoLabel = new Label("Cargo",
				skin.getStyle(LabelStyle.class), "cargolable");
		final Button buttonClose = new TextButton("Close",
				skin.getStyle(TextButtonStyle.class), "button-close");
		final Button buttonAllToWarehouse = new TextButton("Cargo > Warehouse",
				skin.getStyle(TextButtonStyle.class), "buttoncargotowarehouse");
		final Label balance = new Label(Stardust3d.myCharacter.getCredits()
				+ " $D", skin.getStyle(LabelStyle.class), "balancelable");
		final Table gridCargo = initGridCargo();

		final Table gridWarehouse = initGridWarehouse();
		final ScrollPane paneCargo = new ScrollPane(gridCargo,
				skin.getStyle(ScrollPaneStyle.class), "cargo");
		final ScrollPane paneWarehouse = new ScrollPane(gridWarehouse,
				skin.getStyle(ScrollPaneStyle.class), "warehouse");
		final SplitPane splitPane = new SplitPane(paneCargo, paneWarehouse,
				false,
				skin.getStyle("default-horizontal", SplitPaneStyle.class),
				"split");

		Window window = new Window("Station Inventory Screen",
				skin.getStyle(WindowStyle.class), "stationInventoryWindow");
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
		window.add(buttonAllToWarehouse).minWidth(150);
		window.add(buttonClose).minWidth(150);
		window.row();
		window.add(cargoLabel).colspan(2);
		window.add(warehouseLabel).colspan(2);
		window.row().fill().expandY();
		window.add(splitPane).colspan(4);
		window.pack();

		stage.addActor(window);
		buttonClose.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("inventory Close");
				SoundManager.playuiclick();
				Stardust3d.MyDataOp.getInventory(Integer
						.valueOf(Stardust3d.currencharacteruid),false);	
				Stardust3d.stationScreen = 102;
				Renderer.stage.clear();
			}
		});
		buttonAllToWarehouse.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("dump cargo to warehouse");
				SoundManager.playuiclick();
				Stardust3d.MyDataOp.newRequest("movealltowarehouse#");

				for (int i = 0; i < Stardust3d.myInventory.size(); i++) {
					Inventory item = Stardust3d.myInventory.get(i);
					if (item.getSlot_id() == 0) {
if (Renderer.stage.findActor("" + item.getInventoryid()) !=null){
						Renderer.stage.findActor("" + item.getInventoryid())
								.remove();
					}
				}}
			}
		});
	}

	/**
	 * @return
	 */
	private Table initGridCargo() {
		final Table gridCargo = new Table("gridcargo");
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
			if (item.getSlot_id() == 0) {

				String uidString = "" + item.getInventoryid();
				final Image newItem = new Image(new TextureRegion(
						InventoryIcon.generateDynamicTexture(itemIcon, item.getCategory(),
								item.getQuality(),false,item.getCount())), Scaling.none,
						Align.CENTER, uidString);
				newItem.setClickListener(cargoClickListener(item, newItem));
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

	/**
	 * @param item
	 * @param newItem
	 * @return
	 */
	private ClickListener cargoClickListener(final Inventory item,
			final Image newItem) {
		return new ClickListener() {
			public void click(Actor actor, float x, float y) {
				cargoItemClick(item, newItem);

			}

			/**
			 * @param item
			 * @param newItem
			 */
			private void cargoItemClick(final Inventory item,
					final Image newItem) {
				SoundManager.playuiclick();
				if (Stardust3d.DEBUG) {
					System.out.println("cargo pick" + newItem.name);

				}

				String dialogTitle = (item.getItem());
				String dialogTexts = InventoryUtils.makeItemInfo(item);

				DialogListener dialogListener = (new DialogListener() {
					@Override
					public void optionSelected(int option) {
						SoundManager.playuiclick();
						System.out.println("option " + option);
						// if close
						if (option == 1) {
							Renderer.stage.removeActor(dialog);
						}
						// if sell
						if (option == 0) {
							SoundManager.playCoins();
							System.out.println("movetowarehouse "
									+ newItem.name);
							Stardust3d.MyDataOp
									.newRequest("movetowarehouse#"
											+ newItem.name);
							
							// need to work out how to add item to other
							// grid
							Renderer.stage.removeActor(newItem);
							Renderer.stage.removeActor(dialog);
							 ((Table)Renderer.stage.findActor("gridwarehouse")).add(newItem);
							 newItem.setClickListener(warehouseClickListener(item, newItem));
							 ((Table)Renderer.stage.findActor("gridwarehouse")).layout();
							
							;
						}
					}

				});

				dialog = (Actors.twoOptionsDialog(dialogTexts,
						dialogListener, dialogTitle, "To Warehouse",
						"Close", skin));
				Renderer.stage.addActor(dialog);
				SoundManager.playuiclick();
			}

		};
	}

	private Table initGridWarehouse() {
		System.out.println("init warehouse ");
		final Table gridWarehouse = new Table("gridwarehouse");
		if (Stardust3d.DEBUG)
			gridWarehouse.debug();
		String itemIcon;

		int itemcounter = 0;
		gridWarehouse.defaults().height(64).width(64).align("topleft");
		for (int x = 0; x < Stardust3d.myInventory.size(); x++) {
			System.out.println("init vendor id " + x);
			final Inventory item = Stardust3d.myInventory.get(x);
			itemIcon = "data/" + item.getIcon() + ".png";

			if (Stardust3d.DEBUG) {
				System.out.println("warehouse icon " + itemIcon);
			}
			if (item.getSlot_id() == 1) {
				if (item.getInventoryid() == Stardust3d.myCharacter
						.getShipid()){
					iscurrentship=true;
				}else{
					iscurrentship=false;
				}
				String uidString = "" + item.getInventoryid();
				final Image newItem = new Image(new TextureRegion(
						InventoryIcon.generateDynamicTexture(itemIcon, item.getCategory(),
								item.getQuality(), iscurrentship,item.getCount())), Scaling.none,
						Align.CENTER, uidString);
				newItem.setClickListener(warehouseClickListener(item, newItem));
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

	/**
	 * @param item
	 * @param newItem
	 * @return
	 */
	private ClickListener warehouseClickListener(final Inventory item,
			final Image newItem) {
		return new ClickListener() {
			public void click(Actor actor, float x, float y) {
				// if the current item is a ship
				if (item.getCategory().equals("ship")) {
					// if the ship is not the players current ship
					if (Stardust3d.DEBUG) {
						System.out
								.println("check for current ship: item id="
										+ item.getInventoryid()
										+ "char ship="
										+ Stardust3d.myCharacter
												.getShipid());
					}
					if (item.getInventoryid() != Stardust3d.myCharacter
							.getShipid()) {
						// add dialog with board ship option
						shipItemDialog(item, newItem);
					} else {
						regularItemDialog(item, newItem);
					}
				} else {

					regularItemDialog(item, newItem);
				}
			}

			/**
			 * @param item
			 * @param newItem
			 */
			private void regularItemDialog(final Inventory item,
					final Image newItem) {
				SoundManager.playuiclick();
				if (Stardust3d.DEBUG) {
					System.out.println("warehouse pick" + newItem.name);

				}

				String dialogTitle = (item.getItem());
				String dialogTexts = InventoryUtils.makeItemInfo(item);

				DialogListener dialogListener = (new DialogListener() {
					@Override
					public void optionSelected(int option) {
						SoundManager.playuiclick();
						System.out.println("option " + option);
						// if close
						if (option == 1) {
							Renderer.stage.removeActor(dialog);
						}
						// if sell
						if (option == 0) {
							SoundManager.playCoins();
							System.out.println("to cargo "
									+ newItem.name);
							Stardust3d.MyDataOp
									.newRequest("movetocargo#"
											+ newItem.name);

							// need to work out how to add item to other
							// grid
							Renderer.stage.removeActor(newItem);
							Renderer.stage.removeActor(dialog);
							 ((Table)Renderer.stage.findActor("gridcargo")).add(newItem);
							 newItem.setClickListener(cargoClickListener(item, newItem));
							 ((Table)Renderer.stage.findActor("gridcargo")).layout();
							
						}
					}
				});

				dialog = (Actors.twoOptionsDialog(dialogTexts,
						dialogListener, dialogTitle, "To Cargo",
						"Close", skin));
				Renderer.stage.addActor(dialog);
				SoundManager.playuiclick();
			}

			/**
			 * @param item
			 * @param newItem
			 */
			private void shipItemDialog(final Inventory item,
					final Image newItem) {
				SoundManager.playuiclick();
				if (Stardust3d.DEBUG) {
					System.out.println("ship pick" + newItem.name);

				}

				String dialogTitle = (item.getItem());
				String dialogTexts = InventoryUtils.makeItemInfo(item);

				DialogListener dialogListener = (new DialogListener() {
					@Override
					public void optionSelected(int option) {
						SoundManager.playuiclick();
						System.out.println("option " + option);
						// if board ship
						if (option == 0) {
							Renderer.stage.removeActor(dialog);
							Stardust3d.myCharacter.setShipid(item.getInventoryid());
							Stardust3d.myCharacter.setShipname(item.getItem());
							Stardust3d.MyDataOp.changeShip(Stardust3d.myCharacter.getUid(),
									item.getInventoryid());
							
						}
						// if close
						if (option == 1) {
							Renderer.stage.removeActor(dialog);
						}
						// if sell
						
					}
				});

				dialog = (Actors.twoOptionsDialog(dialogTexts,
						dialogListener, dialogTitle,
						"Board", "Close", skin));
				Renderer.stage.addActor(dialog);
				SoundManager.playuiclick();
			}

			/**
			 * @param item
			 * @return
			 */
		

		};
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

}
