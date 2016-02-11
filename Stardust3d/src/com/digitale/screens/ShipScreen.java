/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.digitale.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
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
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.utils.Scaling;
import com.digitale.connex.Inventory;
import com.digitale.mygdxgame.Renderer;
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.Stardust3d;
import com.digitale.utils.Actors;
import com.digitale.utils.InventoryIcon;
import com.digitale.utils.InventoryUtils;
import com.digitale.utils.Util;
import com.digitale.utils.Actors.DialogListener;

/**
 * the ship equipment screen
 * 
 * @author rbeech
 */
public class ShipScreen extends StardustScreen {
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };
	Actor dialog;
	Skin skin;
	Pixmap sticklebackTexture;
	Pixmap salxTexture;
	Pixmap cynomysTexture;
	Pixmap squirrelTexture;
	Actor root;
	ArrayList<Image> defImageList = new ArrayList <Image>();
	ArrayList<Image> offImageList = new ArrayList <Image>();
	ArrayList<Image> augImageList = new ArrayList <Image>();
	protected boolean doneflag;

	protected String selectedCharacter;

	public ShipScreen(Stage stage) {
		
		Stardust3d.MyDataOp.getShipFitting(Stardust3d.myCharacter.getUid());
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
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));
		sticklebackTexture = Stardust3d.manager.get("data/stickleback.png",Pixmap.class);
		salxTexture = Stardust3d.manager.get("data/salx.png",Pixmap.class);
		cynomysTexture = Stardust3d.manager.get("data/cynomys.png",Pixmap.class);
		squirrelTexture = Stardust3d.manager.get("data/squirrelicon.png",Pixmap.class);
		Texture shipimage;
		
		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;
		final Label shipNameLabel = new Label(Stardust3d.myCharacter.getShipname()
				 ,skin.getStyle(LabelStyle.class), "shipnamelable");
		
		final Button buttonClose = new TextButton("Close",
				skin.getStyle(TextButtonStyle.class), "button-close");
		
		final SelectBox dropdown = new SelectBox(listEntries,
				skin.getStyle(SelectBoxStyle.class), "combo");
		
		final Table gridEquipment = initGridEquipment();
		final List list = new List(listEntries, skin.getStyle(ListStyle.class),
				"list");
		final ScrollPane scrollPane2 = new ScrollPane(gridEquipment,
				skin.getStyle(ScrollPaneStyle.class), "scroll");
		//scrollPane2.setWidget(list);
		

		final Label statsLabel = new Label("Stats",
				skin.getStyle(LabelStyle.class), "statslabel");
		final Label equipLabel = new Label("Equipment",
				skin.getStyle(LabelStyle.class), "equiplabel");
		if (Stardust3d.myCharacter.getShipname().equals("salx")){
			shipimage = new Texture(salxTexture);
		}else if(Stardust3d.myCharacter.getShipname().equals("stickleback")){
			shipimage = new Texture(sticklebackTexture);
		}else if(Stardust3d.myCharacter.getShipname().equals("squirrel")){
			shipimage = new Texture(squirrelTexture);
		}else {
			shipimage = new Texture(cynomysTexture);
		}
		final Image imageActor = new Image(shipimage);

		selectedCharacter = ("Character: "
				+ Stardust3d.charList[0].getFirstname() + " \n" + Stardust3d.charList[0]
				.getSurname());

		
		Window window = new Window("Ship Screen",
				skin.getStyle(WindowStyle.class), "shipWindow");
		if(Stardust3d.DEBUG) window.debug();
		window.x = window.y = 0;
		window.setFillParent(true);
		window.setMovable(false);
		window.defaults().pad(5);
		window.defaults().spaceBottom(5);
		window.row().fill().expandX().height(32);
		window.add();
		window.add();
		window.add();
		window.add(buttonClose).height(32);
		window.row();
		window.add(shipNameLabel);
		window.add(statsLabel);
		window.add(equipLabel);
		window.row();
		window.add(imageActor).size(128, 128);
		window.add();
		window.add(scrollPane2).colspan(2).fill().expandY();
		window.row();
		// 
		window.row();
		//window.add(splitPane).colspan(4);
		window.row();
		window.row();
	
		window.pack();

		// stage.addActor(new Button("Behind Window", skin));
		stage.addActor(window);

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

		buttonClose.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				System.out.println("Shipscreen Close");
				SoundManager.playuiclick();
				Stardust3d.stationScreen = 106;
				doneflag = true;

			}
		});
	}

	@Override
	public void render(float delta) {	
		Table.drawDebug(Renderer.stage);
	}

	@Override
	public void resize(int width, int height) {
		
	}
	private Table initGridEquipment() {
	
		System.out.println("init equip ");
		final Table gridEquip = new Table("gridequipment");
		if (Stardust3d.DEBUG)
			gridEquip.debug();
		String itemIcon;
		
		
		for (int x = 0; x < Stardust3d.myInventory.size(); x++) {
			System.out.println("init equip id " + x +"item id "+ Stardust3d.myInventory.get(x).getInventoryid());
			final Inventory item = Stardust3d.myInventory.get(x);
			itemIcon = "data/" + item.getIcon() + ".png";

			for (int offSlots=0; offSlots <5; offSlots++){
			if (item.getInventoryid() ==Stardust3d.myFitting.offList.get(offSlots)) {
				if (Stardust3d.DEBUG) {
					System.out.println("equip icon " + itemIcon);
				}
				String uidString = "" + item.getInventoryid();
				final Image newItem = new Image(new TextureRegion(
						InventoryIcon.generateDynamicTexture(itemIcon, item.getCategory(),
								item.getQuality(), false,item.getCount())), Scaling.none,
						Align.CENTER, uidString);
				newItem.setClickListener(equipClickListener(item, newItem));
				offImageList.add(newItem);
		
			}
	
		
					}
		
		}
		for (int offSlots=offImageList.size(); offSlots <Stardust3d.myFitting.offList.size(); offSlots++){
			final Inventory item = new Inventory();
			 String uidString = "notset";
				item.setCategory("offslot"+offSlots);
				item.setitemUid(-offSlots);
				final Image newItem = new Image(new TextureRegion(
						InventoryIcon.generateDynamicTexture("data/notset.png", "offslot"+offSlots,
								1, false,0)), Scaling.none,
						Align.CENTER, uidString);
				newItem.setClickListener(equipClickListener(item, newItem));
				offImageList.add(newItem);
		 }
		for (int augSlots=augImageList.size(); augSlots <Stardust3d.myFitting.augList.size(); augSlots++){
			final Inventory item = new Inventory();
			 String uidString = "notset";
				item.setCategory("augslot"+augSlots);
				item.setitemUid(-augSlots);
				final Image newItem = new Image(new TextureRegion(
						InventoryIcon.generateDynamicTexture("data/notset.png", "augslot"+augSlots,
								1, false,0)), Scaling.none,
						Align.CENTER, uidString);
				newItem.setClickListener(equipClickListener(item, newItem));
				augImageList.add(newItem);
		 }
		
		for (int defSlots=defImageList.size(); defSlots <Stardust3d.myFitting.defList.size(); defSlots++){
			final Inventory item = new Inventory();
			 String uidString = "notset";
				item.setCategory("defslot"+defSlots);
				item.setitemUid(-defSlots);
				final Image newItem = new Image(new TextureRegion(
						InventoryIcon.generateDynamicTexture("data/notset.png", "defslot"+defSlots,
								1, false,0)), Scaling.none,
						Align.CENTER, uidString);
				newItem.setClickListener(equipClickListener(item, newItem));
				defImageList.add(newItem);
		 }
		gridEquip.defaults().height(30).width(64).align("topleft");
		gridEquip.add(new Label("Off 1", skin));
		gridEquip.add(new Label("Off 2", skin));
		gridEquip.add(new Label("Off 3", skin));
		gridEquip.add(new Label("Off 4", skin));
		gridEquip.add(new Label("Off 5", skin));
		gridEquip.row();
		gridEquip.defaults().height(64).width(64);
		for (int offSlots=0; offSlots < offImageList.size(); offSlots++){
			if (offImageList.get(offSlots)!=null){
		gridEquip.add(offImageList.get(offSlots));
			}	
		}
		gridEquip.row();
		
		gridEquip.defaults().height(30).width(64).align("topleft");
		gridEquip.add(new Label("Def 1", skin));
		gridEquip.add(new Label("Def 2", skin));
		gridEquip.add(new Label("Def 3", skin));
		gridEquip.add(new Label("Def 4", skin));
		gridEquip.add(new Label("Def 5", skin));
		gridEquip.row();
		gridEquip.defaults().height(64).width(64);
		for (int defSlots=0; defSlots < defImageList.size(); defSlots++){
			if (defImageList.get(defSlots)!=null){
		gridEquip.add(defImageList.get(defSlots));
			}
		}
		gridEquip.row();
		
		gridEquip.defaults().height(30).width(64).align("topleft");
		gridEquip.add(new Label("Aug 1", skin));
		gridEquip.add(new Label("Aug 2", skin));
		gridEquip.add(new Label("Aug 3", skin));
		gridEquip.add(new Label("Aug 4", skin));
		gridEquip.add(new Label("Aug 5", skin));
		gridEquip.row();
		gridEquip.defaults().height(64).width(64);
		for (int augSlots=0; augSlots < augImageList.size(); augSlots++){
			if (augImageList.get(augSlots)!=null){
		gridEquip.add(augImageList.get(augSlots));
			}
			
		}
		gridEquip.pack();
		return gridEquip;
	}
	@Override
	public void dispose() {
		for (int offSlots=0; offSlots < offImageList.size(); offSlots++){
			//offImageList.get(offSlots).dispose();
		}
		skin.dispose();
		sticklebackTexture.dispose();
		salxTexture.dispose();
		
	}
	/**
	 * @param item
	 * @param newItem
	 * @return
	 */
	private ClickListener equipClickListener(final Inventory item,
			final Image newItem) {
		return new ClickListener() {
			public void click(Actor actor, float x, float y) {
				// if the current item is a blank offence slot
				if (item.getCategory().contains("off")) {					
						// add dialog with pick offence mod
						//offModDialog(item, newItem);
					// if the current item is a blank defence slot
					} else if(item.getCategory().contains("def")) {
						// add dialog with pick defence mod
						//defModDialog(item, newItem);
					}else if(item.getCategory().contains("aug")) {
						// add dialog with pick aug mod
						//augModDialog(item, newItem);
					
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
					System.out.println("equip pick" + newItem.name);

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
							System.out.println("unequip "
									+ newItem.name);
							Stardust3d.MyDataOp
									.newRequest("movetocargo#"
											+ newItem.name);

							// need to work out how to add item to other
							// grid
						//	Renderer.stage.removeActor(newItem);
							Renderer.stage.removeActor(dialog);
							// ((Table)Renderer.stage.findActor("gridcargo")).add(newItem);
							// newItem.setClickListener(equipClickListener(item, newItem));
							// ((Table)Renderer.stage.findActor("gridcargo")).layout();
						}
					}
				});

				dialog = (Actors.twoOptionsDialog(dialogTexts,
						dialogListener, dialogTitle, "Unequip",
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
