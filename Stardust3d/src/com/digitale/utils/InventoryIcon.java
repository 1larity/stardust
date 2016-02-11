package com.digitale.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.digitale.mygdxgame.Stardust3d;

public class InventoryIcon {
	FileHandle iconFile;
	static FileHandle iconGlowFile = Gdx.files.internal("data/iconship.png");
	static FileHandle baseFile = Gdx.files.internal("data/blueprint.png");
	static Pixmap imgZero = (Stardust3d.manager.get("data/0.png", Pixmap.class));
	static Pixmap imgOne = (Stardust3d.manager.get("data/1.png", Pixmap.class));
	static Pixmap imgTwo = (Stardust3d.manager.get("data/2.png", Pixmap.class));
	static Pixmap imgThree =(Stardust3d.manager.get("data/3.png", Pixmap.class));
	static Pixmap imgFour = (Stardust3d.manager.get("data/4.png", Pixmap.class));
	static Pixmap imgFive = (Stardust3d.manager.get("data/5.png", Pixmap.class));
	static Pixmap imgSix = (Stardust3d.manager.get("data/6.png", Pixmap.class));
	static Pixmap imgSeven = (Stardust3d.manager.get("data/7.png", Pixmap.class));
	static Pixmap imgEight = (Stardust3d.manager.get("data/8.png", Pixmap.class));
	static Pixmap imgNine = (Stardust3d.manager.get("data/9.png", Pixmap.class));
	
	public static Texture generateDynamicTexture(String iconFilename, String category,
			int quality, boolean ismyship, int quantity) {
		
		Pixmap baseImage;
		if (category.equals("blueprint")) {
			
			baseImage = new Pixmap(baseFile);
		} else {
			baseImage = new Pixmap(64, 64, Format.RGBA8888);
		}
		

		if (Stardust3d.DEEPDEBUG)
			System.out.println("base format" + baseImage.getFormat());
		Pixmap imgA = new Pixmap(iconGlowFile);
		if (Stardust3d.DEEPDEBUG)
			System.out.println("a format" + imgA.getFormat());
		Pixmap imgB =(Stardust3d.manager.get(iconFilename, Pixmap.class));

		if (Stardust3d.DEEPDEBUG)
			System.out.println("b " + iconFilename + " format"
					+ imgB.getFormat());
		// overdraw rectangle to tint icon where needed
		// if(tintcolour !null){imgB.SetColor(tintcolour);

	//	imgB.fillRectangle(0, 0, 64, 64);

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
		if(ismyship){
			baseImage.drawPixmap(imgA, 0, 0, 64, 64, 0, 0, 64, 64);
		}
		
		baseImage.drawPixmap(imgB, 0, 0, 64, 64, 0, 0, 64, 64);
		addStackNumber(quantity, baseImage);
		Texture dynamicTexture = new Texture(baseImage);

		baseImage.dispose();
		imgA.dispose();
		return dynamicTexture;
	}

	/**
	 * @param quantity
	 * @param baseImage
	 */
	private static void addStackNumber(int quantity, Pixmap baseImage) {
		if(quantity>1){
			int ones=quantity % 10;
			drawnumber(ones,64-10,baseImage);
			
		}
		if(quantity>9){
			int tens=(quantity % 100)/10;
			drawnumber(tens,64-20,baseImage);
		}
		if(quantity>99){
			int hundreds=(quantity % 1000)/100;
			drawnumber(hundreds,64-30,baseImage);
		}
		if(quantity>999){
			int thousands=(quantity % 10000)/1000;
			drawnumber(thousands,64-40,baseImage);
		}
		if(quantity>9999){
			int tenthousands=(quantity % 100000)/10000;
			drawnumber(tenthousands,64-50,baseImage);
		}
		if(quantity>99999){
			int hundredthousands=(quantity % 1000000)/100000;
			drawnumber(hundredthousands,64-60,baseImage);
		}
	}

	private static void drawnumber(int number, int position,Pixmap baseImage) {
		switch (number){
		case 0:
			baseImage.drawPixmap(imgZero, 0, 0, 64, 64, position, 0, 64, 64);
			break;
		case 1:
			baseImage.drawPixmap(imgOne, 0, 0, 64, 64, position, 0, 64, 64);
			break;
		case 2:
			baseImage.drawPixmap(imgTwo, 0, 0, 64, 64, position, 0, 64, 64);
			break;
		case 3:
			baseImage.drawPixmap(imgThree, 0, 0, 64, 64, position, 0, 64, 64);
			break;
		case 4:
			baseImage.drawPixmap(imgFour, 0, 0, 64, 64, position, 0, 64, 64);
			break;
		case 5:
			baseImage.drawPixmap(imgFive, 0, 0, 64, 64, position, 0, 64, 64);
			break;
		case 6:
			baseImage.drawPixmap(imgSix, 0, 0, 64, 64, position, 0, 64, 64);
			break;
		case 7:
			baseImage.drawPixmap(imgSeven, 0, 0, 64, 64, position, 0, 64, 64);
			break;
		case 8:
			baseImage.drawPixmap(imgEight, 0, 0, 64, 64, position, 0, 64, 64);
			break;
		case 9:
			baseImage.drawPixmap(imgNine, 0, 0, 64, 64, position, 0, 64, 64);
			break;
			
		}
			
	}
}
