/*
 * Copyright 2012 Richard Beech rp.Beech@gmail.com
 * 
 */

package com.digitale.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.Stardust3d;
import com.digitale.utils.Util;
import com.digitale.mygdxgame.Renderer;

/**
 * display character stats screen
 * 
 * @author rbeech
 */
public class Char extends StardustScreen {
	String[] listEntries = { null, null, null, null, null, null, null, null,
			null, null };
	String PrimaryStat;
	String BonusStat;
	Skin skin;
	Texture texture1;
	Texture texture2;
	Actor root;

	protected boolean doneflag;

	protected String selectedCharacter;

	public Char(Stage stage) {
		
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
		texture1 = new Texture(Gdx.files.internal("data/fempaperdoll.png"));
		texture2 = new Texture(Gdx.files.internal("data/avatar02.jpg"));
		TextureRegion image = new TextureRegion(texture1);
		TextureRegion image2 = new TextureRegion(texture2);
		Gdx.input.setInputProcessor(stage);

		// Group.debug = true;
		final Label nameLabel = new Label(Stardust3d.myCharacter.getFirstname()
				 + Stardust3d.myCharacter.getSurname(),
				skin.getStyle(LabelStyle.class), "namelable");
		//intel
		final Label PrimaryStatLabel = new Label("twat"
				+ Stardust3d.myCharacter.getIntelligence(),
				skin.getStyle(LabelStyle.class), "primary-stat");
		final Label BonusStatLabel = new Label(PrimaryStat
				+ Stardust3d.myCharacter.getIntelligence(),
				skin.getStyle(LabelStyle.class), "bonus-stat");
	
		final Button buttonClose = new TextButton("Close",
				skin.getStyle(TextButtonStyle.class), "button-close");
		
		
		final SelectBox dropdown = new SelectBox(listEntries,
				skin.getStyle(SelectBoxStyle.class), "combo");
		final Image imageActor2 = new Image(image2);
		final Image imageActor = new Image(image);
		final FlickScrollPane scrollPane = new FlickScrollPane(imageActor,
				"flickscroll");
		final FlickScrollPane scrollPane2 = new FlickScrollPane(imageActor2,
				"flickscroll2");
		
		final SplitPane splitPane = new SplitPane(scrollPane2, scrollPane,
				false,
				skin.getStyle("default-horizontal", SplitPaneStyle.class),
				"split");

		selectedCharacter = ("Character: "
				+ Stardust3d.charList[0].getFirstname() + " " + Stardust3d.charList[0]
				.getSurname());

		
		Window window = new Window("Character Screen",
				skin.getStyle(WindowStyle.class), "charWindow");
		if(Stardust3d.DEBUG) window.debug();
		window.x = window.y = 0;
		window.setFillParent(true);
		window.setMovable(false);
		window.defaults().pad(5);
		window.defaults().spaceBottom(5);
		window.row().fill();
		window.add(nameLabel).colspan(2);
		window.add(buttonClose).colspan(1);
		window.row();
		window.add(splitPane).colspan(1).expandY().maxWidth(400);
		window.add(PrimaryStatLabel).colspan(1);
		window.add(BonusStatLabel).colspan(1);
		
		window.row();
		window.pack();

		
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
				System.out.println("Char Close");
				SoundManager.playuiclick();
				Stardust3d.stationScreen = 105;
				doneflag = true;
			}
		});
	}

	@Override
	public void render(float delta) {
		
		if (Stardust3d.DEBUG)
			Table.drawDebug(Renderer.stage);
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void dispose() {
		
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
