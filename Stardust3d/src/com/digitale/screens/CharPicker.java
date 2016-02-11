/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.screens;

import java.io.IOException;
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
import com.digitale.utils.Util;
import com.digitale.utils.Actors.DialogListener;
import com.digitale.mygdxgame.Renderer;
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.Stardust3d;


public class CharPicker extends StardustScreen {
	   String[] tempEntries={null,null,null,null,null,null,null,null,null,null};
	   String[] listEntries;
	   public static boolean fadeOut;
		public static boolean fadeIn;
		public static float fadeTimer;
		public static int i=0;
		private Timer timer = new Timer();
       Skin skin;
       Stage stage;
       Actor  dialog;
       SpriteBatch batch;
       Texture texture0= new Texture(Gdx.files.internal("data/avatar00.jpg"));
       Texture texture1= new Texture(Gdx.files.internal("data/avatar01.jpg"));
       Texture texture2= new Texture(Gdx.files.internal("data/avatar02.jpg"));
       Texture texture3= new Texture(Gdx.files.internal("data/avatar03.jpg"));
       Texture texture4= new Texture(Gdx.files.internal("data/avatar04.jpg"));
       Texture texture5= new Texture(Gdx.files.internal("data/avatar05.jpg"));
       Texture texture6= new Texture(Gdx.files.internal("data/avatar06.jpg"));
       Texture texture7= new Texture(Gdx.files.internal("data/avatar07.jpg"));
       Texture texture8= new Texture(Gdx.files.internal("data/avatar08.jpg"));
       Texture texture9= new Texture(Gdx.files.internal("data/avatar09.jpg"));
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

	protected String selectedCharacter;

	protected String currentAvatar;
	private String dialogTexts;
	
       public CharPicker() {
    	   try {
			Renderer.bindMeshes(3);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	   Stardust3d.currencharacteruid=""+Stardust3d.charList[0].getUid();
    	   int charListLength=0;
    	   for (int i = 0; i < Stardust3d.charList.length; i++) {
    		   
    		   if (Stardust3d.charList[i].getFirstname()!=null){
    			   tempEntries[i]=new String(""); 
    		   tempEntries[i]=(Stardust3d.charList[i].getFirstname() + " "
    					+ Stardust3d.charList[i].getSurname());
    		   tempEntries[i]= tempEntries[i]+("  System " + Stardust3d.charList[i].getSystem()// + " Sta "
    					//+ Stardust3d.charList[i].getStamina() + " Int "
    					//+ Stardust3d.charList[i].getIntelligence() + " Soc " + Stardust3d.charList[i].getSocial()
    					//+ " Dex " + Stardust3d.charList[i].getDexterity() + " Led "
    					//+ Stardust3d.charList[i].getLeadership() + " Rec "
    					//+ Stardust3d.charList[i].getRecuperation()
    					+ ".  Flying: "
    					+ Util.asCapFirstChar(Stardust3d.charList[i].getShipname()));
    		   charListLength++;
   		}else{
    	   tempEntries[i]="";
       }
    		   listEntries = new String[charListLength];
				for (int j = 0; j < listEntries.length; j++) {
					listEntries[j] = tempEntries[j];
				}   
    	   }
    	   
    	   currentAvatar=Stardust3d.charList[0].getAvatar();
    	   OrthographicCamera camera;
               batch = new SpriteBatch();
               skin = new Skin(Gdx.files.internal("data/uiskin.json"), Gdx.files.internal("data/uiskin.png"));             
               xfadeTexture = new Texture(Gdx.files.internal("data/blackpixel.png"), Format.RGB565, true);
       		xfadeTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
       		background = new Texture(Gdx.files.internal("data/bgpland.jpg"));		
       		camera = new OrthographicCamera();
       		camera.setToOrtho(false,Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());	
       		
       		fadeIn();
               stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
               Gdx.input.setInputProcessor(stage);
               final Button buttonEnterGame = new TextButton("Enter Game", skin.getStyle(TextButtonStyle.class), "button-enter-game");
               final Button buttonNewCharacter = new TextButton("New Character", skin.getStyle(TextButtonStyle.class), "button-enter-game");
               final Button buttonDeleteCharacter = new TextButton("Delete Character", skin.getStyle(TextButtonStyle.class), "button-enter-game");
              final SelectBox dropdown = new SelectBox(listEntries,skin.getStyle(SelectBoxStyle.class), "combo");
              final List list = new List(listEntries, skin.getStyle(ListStyle.class), "list");    
               setAvatarPicture();
              if(Stardust3d.charList[0].getFirstname()==null){
            	  selectedCharacter="    ";
              }else{
               selectedCharacter=(Stardust3d.charList[0].getFirstname() + " "
   					+ Stardust3d.charList[0].getSurname() );
              }
              final Label fpsLabel = new Label(selectedCharacter, skin.getStyle(LabelStyle.class), "label");
              
              
               Window window = new Window("Choose Character", skin.getStyle(WindowStyle.class), "window");
               if(Stardust3d.DEBUG) window.debug(); 
               window.x = window.y = 0;
               window.setFillParent(true);
       		window.setMovable(false);
       		window.align(Align.TOP);
       		window.defaults().pad(5);
               window.defaults().spaceBottom(5).align("top");
               window.row().fill().expandX().maxHeight(28);
               window.add(buttonNewCharacter);
               window.add(buttonDeleteCharacter);
               window.add(buttonEnterGame);
               window.row();
               
               window.add(dropdown).colspan(3);
               window.row();
               window.add(imageActor).size(128, 128);
               window.add(fpsLabel).colspan(2).align("centre");
               window.add();
               window.pack();

               stage.addActor(window);
          
               dropdown.setSelectionListener(new SelectionListener() {               
				@Override
				public void selected(Actor actor, int index, String value) {
					SoundManager.playuiclick();
					 selectedCharacter=( Stardust3d.charList[index].getFirstname() 
		    					+" "+ Stardust3d.charList[index].getSurname() );
					 Stardust3d.currencharacteruid=""+Stardust3d.charList[index].getUid();
					 currentAvatar=Stardust3d.charList[index].getAvatar();
					 setAvatarPicture();
					   ((Label)stage.findActor("label")).setText( selectedCharacter);
					  
			            
					 
				}
           });
                 
           	buttonEnterGame.setClickListener(new ClickListener() {

    			public void click(Actor actor, float x, float y) {
    				if(Stardust3d.DEBUG)System.out.println("Charpicker Close");
    				SoundManager.playuiclick();
    				if(Stardust3d.DEBUG)System.out.println("character ID"+Stardust3d.currencharacteruid);
    				if (Integer.valueOf(Stardust3d.currencharacteruid)!=0){
    				//populate game from db, based on this char
    					Stardust3d.gameMode=4;
    					//init character
    					Stardust3d.MyDataOp.get3dChar(Integer.valueOf(Stardust3d.currencharacteruid));
    					//
    					if (!Stardust3d.DEBUG){
    					String passed = Stardust3d.MyDataOp.postChat(Stardust3d.myCharacter.getFirstname()+
    							" "+Stardust3d.myCharacter.getSurname()+" has joined the game.",
    							1, 48);
    					}
    					Stardust3d. MyDataOp.getInventory(Integer.valueOf(Stardust3d.currencharacteruid),true);
    					Stardust3d. MyDataOp.getSolarSystem(Stardust3d.myCharacter.getX(),
    							Stardust3d.myCharacter.getX(),
    							Stardust3d.myCharacter.getZ());
    					Stardust3d. MyDataOp.getLocalPlayers(Stardust3d.myCharacter.getX(),
    							Stardust3d.myCharacter.getY(),
    							Stardust3d.myCharacter.getZ(),true);
    					Stardust3d.MyDataOp.start();
    					
    					try {
							Renderer.bindMeshes(4);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					doneflag=true;
    				}else{
    					stage.addActor(Actors.bottomToast("Please select a character before entering the game.",
    							4, skin));    	
    				}
    			}
    		});
           	buttonNewCharacter.setClickListener(new ClickListener() {

    			public void click(Actor actor, float x, float y) {

    				SoundManager.playuiclick();				
    				if (Stardust3d.numberOfCharacters >= 10) {
    					SoundManager.playError();
    					stage.addActor(Actors.bottomToast("You already have 10 characters, you must delete one first, before you can create a new one.",
    							4, skin));    				
    				} else {
    					// startNewCharActivity();
    					Stardust3d.gameMode=15;
    					stage.addActor(Actors.bottomToast("Creating new character",
    							4, skin));
    					doneflag=true;
    				}
     			}
    		});
           	
        	 DialogListener dialogListener=(new DialogListener(){

				@Override
				public void optionSelected(int option) {
					System.out.println("option "+option);
					if (option==1){
						System.out.println("no");
						stage.removeActor(dialog);
					}
					if (option==0){
						System.out.println("yes");
						System.out.println("DELETED"+selectedCharacter+" "+Stardust3d.currencharacteruid);
						stage.removeActor(dialog);
					}
					
				}
				
			});
        	 dialogTexts =( "Are you sure you wish to delete "+selectedCharacter
					 +", and all the contents of thier inventory?");
           dialog=  (Actors.twoOptionsDialog( selectedCharacter, 
					 dialogListener, "Delete Character",
						"Yes", "No", skin));
        	buttonDeleteCharacter.setClickListener(new ClickListener() {

    			public void click(Actor actor, float x, float y) {

    				SoundManager.playuiclick();	
    				
    			
					stage.addActor(dialog);
    				
     			}
    		});
       }
	/**
	 * 
	 */
	public void setAvatarPicture() {
		if (currentAvatar==null||currentAvatar.equals ("avatar00.jpg")){
			   imageActor=new Image(image0,Scaling.fit,
						Align.CENTER, "avatar");
		   }else{
		   if (currentAvatar.equals("avatar01.jpg")){
			   imageActor = new Image(image1,Scaling.fit,
						Align.CENTER, "avatar");
		   }
		   if (currentAvatar.equals("avatar02.jpg")){
			   imageActor = new Image(image2,Scaling.fit,
						Align.CENTER, "avatar");
		   }
		   if (currentAvatar.equals("avatar03.jpg")){
			   imageActor = new Image(image3,Scaling.fit,
						Align.CENTER, "avatar");
		   }
		   if (currentAvatar.equals("avatar04.jpg")){
			   imageActor = new Image(image4,Scaling.fit,
						Align.CENTER, "avatar");
		   }
		   if (currentAvatar.equals("avatar05.jpg")){
			   imageActor = new Image(image5,Scaling.fit,
						Align.CENTER, "avatar");
		   }
		   if (currentAvatar.equals("avatar06.jpg")){
			   imageActor = new Image(image6,Scaling.fit,
						Align.CENTER, "avatar");
		   }
		   if (currentAvatar.equals("avatar07.jpg")){
			   imageActor = new Image(image7,Scaling.fit,
						Align.CENTER, "avatar");
		   }
		   if (currentAvatar.equals("avatar08.jpg")){
			   imageActor = new Image(image8,Scaling.fit,
						Align.CENTER, "avatar");
		   }
		   
		   if (currentAvatar.equals("avatar09.jpg")){
			   imageActor = new Image(image9,Scaling.fit,
						Align.CENTER, "avatar");
		   }
		   
		   
		   }
		imageActor.invalidate();
	}
       private void fadeIn() {
   		timer.scheduleAtFixedRate(new TimerTask() {
   			public void run() { 
   				i++;
   				
   				//detect and set white fade in
   				if (i<10 && fadeIn==false ){
   						fadeIn=false;
   						fadeTimer=1;
   				}
   				if (fadeIn){
   						fadeTimer=fadeTimer-0.05f; 
   						System.out.println("fade "+fadeTimer);
   					}
   				if (fadeIn && fadeTimer>10){
   					fadeIn=false;
   					fadeTimer=0;
   				}
   			
   				if (fadeTimer<0){
   					fadeTimer=0;
   					timer.cancel();
   				}
   				
   				}	
   		}, 0, 100);
   	}
       @Override
       public void render (float delta) {
               Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
               Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
               int width=Gdx.app.getGraphics().getWidth();
       		int height=Gdx.app.getGraphics().getHeight();
       		batch.begin();
    		batch.setColor(Color.WHITE);
    		batch.draw(background, 0, 0, width,
    				height, 0, 0, 1024, 512, false, false);
    		batch.end();
    	
    		imageActor.height=128;
               stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
               stage.draw();
               if (fadeIn ){
       			batch.enableBlending();
       			batch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
       			batch.begin();
       			batch.setColor(1,1, 1, fadeTimer);
       			batch.draw(xfadeTexture, -100,-100, width+200, height+200);
       			batch.end();
       			}
              
               if(Stardust3d.DEBUG) Table.drawDebug(stage);
       }

       @Override
       public void resize (int width, int height) {
               stage.setViewport(width, height, false);
       }

       @Override
       public void dispose () {
               stage.dispose();
               skin.dispose();
               texture1.dispose();
               texture2.dispose();
               texture3.dispose();
               texture4.dispose();
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
