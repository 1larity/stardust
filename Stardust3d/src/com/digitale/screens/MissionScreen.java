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
import com.digitale.utils.Actors.DialogListener;


public class MissionScreen extends StardustScreen {
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };

	Skin skin;
	Actor dialog;
	Actor root;
	public int currentMission=0;
	/** the cross fade texture **/

	protected boolean doneflag;

	protected String selectedCharacter;

	public MissionScreen(Stage stage) {
		
		Stardust3d.missionDefs.clear();
		Stardust3d.myMissions.clear();
		Stardust3d.MyDataOp.getMissionDefs();
				
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));
		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;
		final Label missionTitleLabel = new Label(Stardust3d.missionDefs.get(0).getTitle(),
				skin.getStyle(LabelStyle.class), "missionlable");
		final Label negotiatorLabel = new Label("Negotiator",
				skin.getStyle(LabelStyle.class), "negotiatorlable");
		final Label missionTextLabel = new Label(Stardust3d.missionDefs.get(0).getText()+
				"\n"+Stardust3d.missionDefs.get(0).getConditions()+
				"\n"+Stardust3d.missionDefs.get(0).getRewards(),
				skin.getStyle(LabelStyle.class), "missiontextlable");
		missionTextLabel.setWrap(true);
		final Label negotiatorInfoLabel = new Label("Negotiator Info",
				skin.getStyle(LabelStyle.class), "negotiatorinfolable");
		negotiatorInfoLabel.setWrap(true);
		//buttons
		final Button buttonClose = new TextButton("Close",
				skin.getStyle(TextButtonStyle.class), "button-close");
		final Button buttonPrev = new TextButton("Previous",
				skin.getStyle(TextButtonStyle.class), "buttonprevious");
		final Button buttonNext = new TextButton("Next",
				skin.getStyle(TextButtonStyle.class), "buttonnext");
		final Button buttonAccept = new TextButton("Accept",
				skin.getStyle(TextButtonStyle.class), "buttonpaccept");
		final Button buttonComplete = new TextButton("Complete",
				skin.getStyle(TextButtonStyle.class), "buttoncomplete");
		
		String uidString="bob";
		final Image newItem = new Image(new TextureRegion(new Texture(Gdx.files.internal("data/avatar02.jpg"))), Scaling.none,
				Align.CENTER, uidString);
		
		final ScrollPane paneCargo = new ScrollPane(newItem,
				skin.getStyle(ScrollPaneStyle.class), "cargo");
		//paneCargo.addActor(negotiatorInfoLabel);
		final ScrollPane paneMission = new ScrollPane(missionTextLabel,
				skin.getStyle(ScrollPaneStyle.class), "warehouse");
		final SplitPane splitPane = new SplitPane(paneCargo, paneMission,
				false,
				skin.getStyle("default-horizontal", SplitPaneStyle.class),
				"split");
		
		Window window = new Window("Mission Screen",
				skin.getStyle(WindowStyle.class), "missionWindow");
		if (Stardust3d.DEBUG)
			window.debug();
		window.x = window.y = 0;
		window.setFillParent(true);
		window.setMovable(false);
		window.defaults().pad(5);
		window.defaults().spaceBottom(5);
		window.row().fill().expandX();
		window.add(buttonPrev).minWidth(150);
		window.add(buttonNext).minWidth(150);
		window.add(buttonAccept).minWidth(150);
		window.add(buttonClose).minWidth(150);
		window.row();
		window.add(negotiatorLabel).colspan(2);
		window.add(missionTitleLabel).colspan(2);
		window.row().fill().expandY();
		window.add(splitPane).colspan(4);
		window.pack();

		stage.addActor(window);
		buttonClose.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("mission Close");
				SoundManager.playuiclick();
				Stardust3d.stationScreen=110;
				//Renderer.stage.clear();
			}
		});
		buttonPrev.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("show previous mission");
				SoundManager.playuiclick();
				if (currentMission>0){
					currentMission=currentMission-1;
					System.out.println("missionID"+currentMission);
				}
				
			}
		});
		buttonNext.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("show next mission");
				if (currentMission<Stardust3d.missionDefs.size()-1){
					currentMission=currentMission+1;
					System.out.println("missionID"+currentMission);
				}
				SoundManager.playuiclick();
				
				
			}
		});
		buttonAccept.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("accept mission");
				// check mission is not already accepted.
			/*	for (int i = 0; i < Stardust3d.myMissions.size(); i++) {
				//if this mission already exists in char mission list and it is not repeatable
					if((Stardust3d.myMissions.get(i).getMissionUid()==
						Stardust3d.missionDefs.get(currentMission).getUid()) &&
						Stardust3d.missionDefs.get(currentMission).get()){
					
				}
				}*/
				//flag mission as accepted
				Stardust3d.MyDataOp.acceptMission(Stardust3d.missionDefs.get(currentMission).getUid());
				SoundManager.playuiclick();
				
				
			}
		});
		buttonComplete.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				System.out.println("complete mission");
				//check requsites have been fulfilled
				//flag mission as completed
				//award rewards
				SoundManager.playuiclick();
				
				
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
			if (item.getSlot_id() == 0) {

				String uidString =""+ item.getInventoryid();
				final Image newItem = new Image(new TextureRegion(
						generateDynamicTexture(itemIcon, item.getCategory(),
								item.getQuality())), Scaling.none,
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
								//if sell
								if (option == 0) {
									SoundManager.playCoins();
									System.out.println("movetowarehouse "
											+ newItem.name );
									Stardust3d.MyDataOp.newRequest("movetowarehouse#"
											+newItem.name);
									//need to work out how to add item to other grid
									Renderer.stage.removeActor(newItem);
									Renderer.stage.removeActor(dialog);
									;
								}
							}

							
						});

						dialog = (Actors.twoOptionsDialog(dialogTexts,
								dialogListener, dialogTitle, "To Warehouse", "Close",
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

	private Table initGridWarehouse() {
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
			if (item.getSlot_id() == 1) {

				String uidString =""+ item.getInventoryid();
				final Image newItem = new Image(new TextureRegion(
						generateDynamicTexture(itemIcon, item.getCategory(),
								item.getQuality())), Scaling.none,
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
									SoundManager.playCoins();
									System.out.println("to cargo "
											+ newItem.name );
									Stardust3d.MyDataOp.newRequest("movetocargo#"
											+newItem.name);
									
									//need to work out how to add item to other grid
									Renderer.stage.removeActor(newItem);
									Renderer.stage.removeActor(dialog);
									
								}
							}
						});

						dialog = (Actors.twoOptionsDialog(dialogTexts,
								dialogListener, dialogTitle, "To Cargo", "Close",
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

	public Texture generateDynamicTexture(String iconFilename, String category,
			int quality) {
		Pixmap baseImage;
		if (category.equals("blueprint")) {
			FileHandle baseFile = Gdx.files.internal("data/blueprint.png");
			baseImage = new Pixmap(baseFile);
		} else {
			baseImage = new Pixmap(64, 64, Format.RGBA8888);
		}
		FileHandle iconFile = Gdx.files.internal(iconFilename);
		FileHandle iconGlowFile = Gdx.files.internal(iconFilename);

		if (Stardust3d.DEEPDEBUG)
			System.out.println("base format" + baseImage.getFormat());
		Pixmap imgA = new Pixmap(iconGlowFile);
		if (Stardust3d.DEEPDEBUG)
			System.out.println("a format" + imgA.getFormat());
		Pixmap imgB = new Pixmap(iconFile);

		if (Stardust3d.DEEPDEBUG)
			System.out.println("b " + iconFilename + " format"
					+ imgB.getFormat());
		// overdraw rectangle to tint icon where needed
		// if(tintcolour !null){imgB.SetColor(tintcolour);

		imgB.fillRectangle(0, 0, 64, 64);

		// set colour for quality marker
		switch (quality) {
		case 0:
			imgB.setColor(.5f, .5f, .5f, 1f);
			break;
		case 1:
			imgB.setColor(.75f, .75f, .75f, 1f);
			break;
		case 2:
			imgB.setColor(1f, 1f, 1f, 1f);
			break;
		case 3:
			imgB.setColor(0f, .5f, 0f, 1f);
			break;
		case 4:
			imgB.setColor(0f, 1f, 0f, 1f);
			break;
		case 5:
			imgB.setColor(0f, 0f, 1f, 1f);
			break;
		case 6:
			imgB.setColor(0f, 0f, .5f, 1f);
			break;
		case 7:
			imgB.setColor(.5f, 0f, 1f, 1f);
			break;
		case 8:
			imgB.setColor(1f, .5f, 0f, 1f);
			break;
		case 9:
			imgB.setColor(1f, 0.5f, 0f, 1f);
			break;
		}
		// draw quality indicator
		imgB.fillRectangle(0, 54, 10, 10);
		baseImage.setColor(0f, 1f, 0f, .2f);
		// baseImage.drawPixmap(imgA, 0, 0, 64, 64, 0, 0, 64, 64);
		// superimpose item's icon
		baseImage.drawPixmap(imgB, 0, 0, 64, 64, 0, 0, 64, 64);
		Texture dynamicTexture = new Texture(baseImage); 

		baseImage.dispose();
		imgA.dispose();
		imgB.dispose();

		return dynamicTexture;
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
