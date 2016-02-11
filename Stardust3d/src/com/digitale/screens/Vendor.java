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
public class Vendor extends StardustScreen {
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };

	Skin skin;
	Actor dialog;
	Actor root;
	
	/** the cross fade texture **/

	protected boolean doneflag;

	protected String selectedCharacter;

	public Vendor(Stage stage) {

		Stardust3d.myInventory.clear();
		Stardust3d.MyDataOp.get3dChar(Integer
				.valueOf(Stardust3d.currencharacteruid));
		Stardust3d.MyDataOp.getInventory(Integer
				.valueOf(Stardust3d.currencharacteruid),false);
		Stardust3d.vendorInventory.clear();
		Stardust3d.MyDataOp.getVendorInventory(Integer.valueOf("-450"));

		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));
		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;
		final Label warehouseLabel = new Label("Vendor",
				skin.getStyle(LabelStyle.class), "vendorlable");
		final Label cargoLabel = new Label("Warehouse",
				skin.getStyle(LabelStyle.class), "warehouselable");
		final Button buttonClose = new TextButton("Close",
				skin.getStyle(TextButtonStyle.class), "button-close");
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

		Window window = new Window("Vendor Screen",
				skin.getStyle(WindowStyle.class), "vendorwindow");
		if (Stardust3d.DEBUG)
			window.debug();
		window.x = window.y = 0;
		window.setFillParent(true);
		window.setMovable(false);
		window.defaults().pad(5);
		window.defaults().spaceBottom(5);
		window.row().fill().expandX();
		window.add(balance);
		window.add(buttonClose);
		window.row();
		window.add(cargoLabel);
		window.add(warehouseLabel);
		window.row().fill().expandY();
		window.add(splitPane).colspan(2);
		window.pack();

		stage.addActor(window);
		buttonClose.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("inventory Close");
				SoundManager.playuiclick();
				Stardust3d.MyDataOp.getInventory(Integer
						.valueOf(Stardust3d.currencharacteruid),false);	
				Stardust3d.stationScreen = 101;
				Renderer.stage.clear();
			}
		});
	}

	/**
	 * @return
	 */
	private Table initGridCargo() {
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
			if (item.getSlot_id() == 1) {

				String uidString = "" + item.getInventoryid();
				final Image newItem = new Image(new TextureRegion(
						InventoryIcon.generateDynamicTexture(itemIcon, item.getCategory(),
								item.getQuality(),false,item.getCount())), Scaling.none,
						Align.CENTER, uidString);
				newItem.setClickListener(new ClickListener() {
					public void click(Actor actor, float x, float y) {
						SoundManager.playuiclick();
						if (Stardust3d.DEBUG) {
							System.out.println("inventory pick" + newItem.name);

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
									if (item.getBind() != 2) {
										SoundManager.playCoins();
										System.out.println("Sold "
												+ newItem.name);
										Stardust3d.MyDataOp.newRequest("sale#"
												+ newItem.name);
										// need to work out how to add item to
										// other grid
										Renderer.stage.removeActor(newItem);
										Renderer.stage.removeActor(dialog);
										Stardust3d.MyDataOp.get3dChar(Integer
												.valueOf(Stardust3d.currencharacteruid));
									} else {
										Renderer.stage.addActor(Actors
												.bottomToast(
														"You cannot sell this item.",
														3, skin));
									}
								}
							}

						});

						dialog = (Actors.twoOptionsDialog(dialogTexts,
								dialogListener, dialogTitle, "Sell", "Close",
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

	private Table initGridWarehouse() {
		System.out.println("init vendor ");
		final Table gridWarehouse = new Table();
		if (Stardust3d.DEBUG)
			gridWarehouse.debug();
		String itemIcon;

		int itemcounter = 0;
		gridWarehouse.defaults().height(64).width(64).align("topleft");
		for (int x = 0; x < Stardust3d.vendorInventory.size(); x++) {
			System.out.println("init vendor id " + x);
			final Inventory item = Stardust3d.vendorInventory.get(x);
			itemIcon = "data/" + item.getIcon() + ".png";

			if (Stardust3d.DEBUG) {
				System.out.println("vendor icon " + itemIcon);
			}
			

				String uidString = "" + item.getInventoryid();
				final Image newItem = new Image(new TextureRegion(
						InventoryIcon.generateDynamicTexture(itemIcon, item.getCategory(),
								item.getQuality(),false,item.getCount())), Scaling.none,
						Align.CENTER, uidString);
				newItem.setClickListener(new ClickListener() {
					public void click(Actor actor, float x, float y) {
						SoundManager.playuiclick();
						if (Stardust3d.DEBUG) {
							System.out.println("vendor pick" + newItem.name);

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
									//if player can afford item
									if(item.getValue()<Stardust3d.myCharacter.getCredits()){
									SoundManager.playCoins();
									System.out.println("purchased "
											+ newItem.name);
									Stardust3d.MyDataOp.newRequest("purchase#"
											+ newItem.name);

									// need to work out how to add item to other
									// grid
									Renderer.stage.removeActor(newItem);
									Renderer.stage.removeActor(dialog);
									Stardust3d.MyDataOp.get3dChar(Integer
											.valueOf(Stardust3d.currencharacteruid));
								}else{//if player cannot afford item
									Renderer.stage.addActor(Actors
											.bottomToast(
													"You cannot afford this item.",
													3, skin));
								}
							}
							}
						});

						dialog = (Actors.twoOptionsDialog(dialogTexts,
								dialogListener, dialogTitle, "Buy", "Close",
								skin));
						Renderer.stage.addActor(dialog);
						SoundManager.playuiclick();

					}

				});
				gridWarehouse.add(newItem);
				itemcounter++;
			
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

}
