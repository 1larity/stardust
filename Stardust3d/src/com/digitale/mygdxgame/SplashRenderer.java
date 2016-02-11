/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.digitale.mygdxgame;
import java.io.InputStream;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.digitale.screens.Splash;
import com.digitale.utils.Actors;

/** The renderer receives a simulation and renders it.
 * @author rbeech */
public class SplashRenderer {
	private BitmapFont font;
	/** set scale factor for world **/
	private static final float worldscale = .001f;
	/** sprite batch to draw text **/
	private SpriteBatch spriteBatch;
	/** the ship mesh **/
	private Mesh shipMesh;
	/** the ship texture **/
	private Texture shipTexture;
	/** the planet mesh **/
	private Mesh planetMesh02;
	/** the planet mesh **/
	private Mesh planetMesh;
	/** the planet texture **/
	private Texture planetTexture;
	/** the ship mesh **/
	private Mesh skyMesh;
	/** the ship texture **/
	private Texture skyTexture;
	/** the jet mesh **/
	private Mesh jetMesh;
	/** the jet texture **/
	private Texture jetTexture;
	private Texture progressBarTexture;
	/** the cross fade texture **/
	private Texture xfadeTexture;
	/** the sun texture **/
	private Texture sunTexture;
	/** the sun mesh **/
	private Mesh sunMesh;
	Skin skin;
	Stage stage;
	private boolean musicPlaying=false;
	Image img;

	private boolean netErrorShown=false;
	/** perspective camera **/
	PerspectiveCamera camera;
	private float progress;
	private boolean texturesUnbound=false;
	

	public SplashRenderer (Application app) {
		
		font=new BitmapFont(Gdx.files.internal("data/default.fnt"),false);
		try {
			spriteBatch = new SpriteBatch();
			skin = new Skin(Gdx.files.internal("data/uiskin.json"),
					Gdx.files.internal("data/uiskin.png"));
			stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
					false);
			InputStream in = Gdx.files.internal("data/ship02.obj").read();
			shipMesh = ObjLoader.loadObj(in,true);
			in.close(); 
			 in = Gdx.files.internal("data/jet.obj").read();
				jetMesh = ObjLoader.loadObj(in,true);
				in.close();
			 in = Gdx.files.internal("data/sun.obj").read();
			sunMesh = ObjLoader.loadObj(in,true);
			in.close(); 

			in = Gdx.files.internal("data/planet.obj").read();
			planetMesh = ObjLoader.loadObj(in,true);
			in.close();
			
			in = Gdx.files.internal("data/planet02.obj").read();
			planetMesh02 = ObjLoader.loadObj(in,true);
			in.close();
			
			in = Gdx.files.internal("data/shot.obj").read();
			ObjLoader.loadObj(in,false);
			in.close();
			//note use of tru to flip texture V co-ords for max OBJ files
			in = Gdx.files.internal("data/sky.obj").read();
			skyMesh = ObjLoader.loadObj(in,true);
			in.close();
			progressBarTexture = new Texture(Gdx.files.internal("data/progbar.png"), Format.RGB565, true);
			progressBarTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
			
			
			camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	public void render (Application app) {
		if(Stardust3d.manager.isLoaded("data/jet.png")){
			//if base asstest for load screen havent been loaded
			if(!texturesUnbound){
				texturesUnbound=true;
				//load them
				bindTextures();
			}
			//render load anim
			renderInsideStation(app); 
		}
			
			  if(Stardust3d.manager.update()) {
			         // we are done loading everything
				  
				  
			      }

			      // display loading information
			       progress = Stardust3d.manager.getProgress();
			       //check if music has loaded
			       if(Stardust3d.manager.isLoaded("sound/movement_proposition.mp3")&& musicPlaying==false) {
			    	   // music is available, let's play it 
			    	   musicPlaying=true;
			    	   SoundManager.music = Stardust3d.manager.get("sound/movement_proposition.mp3", Music.class);
			    	   SoundManager.playmusic();
			    	}
			       
			   }
	
	// load base assets for displaying loading anim
	private void bindTextures() {
		
		Stardust3d.manager.get("data/planet02.jpg", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
		planetTexture = Stardust3d.manager.get("data/planet02.jpg", Texture.class);
		Stardust3d.manager.get("data/sky01.jpg", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
		skyTexture = Stardust3d.manager.get("data/sky01.jpg", Texture.class);
		Stardust3d.manager.get("data/destroyer01.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
		shipTexture = Stardust3d.manager.get("data/destroyer01.png", Texture.class);
		
		xfadeTexture = Stardust3d.manager.get("data/blackpixel.png", Texture.class);
		Stardust3d.manager.get("data/nova.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sunTexture = Stardust3d.manager.get("data/nova.png", Texture.class);
		Stardust3d.manager.get("data/jet.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
		jetTexture = Stardust3d.manager.get("data/jet.png", Texture.class);
		
	}


	/**
	 * @param simulation
	 * @param gl
	 */
	private void renderHud( GL10 gl) {
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		int width=Gdx.app.getGraphics().getWidth();
		int height=Gdx.app.getGraphics().getHeight();
		//spriteBatch.setProjectionMatrix(viewMatrix);
		//spriteBatch.setTransformMatrix(transformMatrix);
		
		spriteBatch.begin();
		spriteBatch.enableBlending();
		spriteBatch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		/*A minimum of dataset
		B maximum of dataset
		a is from where you would like normalised data set to start
		b is where you would like normalised data set to end
		x is the value you are trying to normalise
		a + (x-A)*(b-a)/(B-A) 
		
		*/
		
		float length=0+(progress-0)*((Gdx.graphics.getWidth()/10)*8-0)/(1-0);
		spriteBatch.setColor(0,0, 1, 1);
		 spriteBatch.draw(progressBarTexture,Gdx.graphics.getWidth()/10,110,(Gdx.graphics.getWidth()/10)*8,30);
		 spriteBatch.setColor(0,1, 1, 1);
		 spriteBatch.draw(progressBarTexture,(Gdx.graphics.getWidth()/10)+2,112,length,26);
		 spriteBatch.setColor(1,1, 1, 1);
		 font.draw(spriteBatch, "Version:"+Stardust3d.version, 10, 10);
	font.draw(spriteBatch, "Loading "+String.format("%.0f",progress*100)+"%",100, 100);  
		if (Splash.fadeIn ){
			spriteBatch.setColor(0,0, 0, Splash.fadeTimer);
			spriteBatch.draw(xfadeTexture,0, 0, width, height);
			}
		if (Splash.fadeOut ){
			spriteBatch.setColor(1,1, 1, Splash.fadeTimer);
			spriteBatch.draw(xfadeTexture, 0,0, width, height);
			}
		spriteBatch.setColor(1,1, 1, 1);
		 if (Stardust3d.Error==Stardust3d.NETWORK_DOWN && netErrorShown==false){
				stage.addActor(Actors.bottomToast("Connection to internet failed, Please check your network is working.",
						5, skin));
		
		netErrorShown=true;
		}
			
		spriteBatch.end();
	}
	
	private void renderInsideStation(Application app) {
		GL10 gl = app.getGraphics().getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glViewport(0, 0, app.getGraphics().getWidth(), app.getGraphics().getHeight());
		gl.glEnable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_DITHER);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_CULL_FACE);
		setStaticProjectionAndCamera(app.getGraphics(), app, gl);
		setLighting(gl);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		renderSky(gl);
		renderPlanet(gl,"sun",true,1737f,Splash.planetmove-150, 0, -2500, app);
		renderSky(gl);
		gl.glDisable(GL10.GL_DITHER);
		gl.glDisable(GL10.GL_CULL_FACE);
		//do alpha models after this
		renderStaticShip(gl, app);
		renderSun(gl,70000f,-1600, 0, -4500, app);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		renderHud( gl);
	}
	

	final Vector3 dir = new Vector3(); 

	private void setStaticProjectionAndCamera (Graphics graphics, Application app,GL10 gl) {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		//x=left-right, y=up-down, z=back-forward
		camera.far=1000000;
		camera.position.set(0, 7f+0, 9f+0);
		//camera.direction.set(0, 0, -4f).sub(camera.position).nor();
		camera.fieldOfView=67;
		float orbitRadius = (new Vector3(0,0,0).dst(camera.position));
		camera.position.set(new Vector3(0,0,0));
		
		camera.rotate(Splash.cameraHorizAngle, 0, 1, 0);
		Vector3 orbitReturnVector = new Vector3(0, 0, 0);
		orbitReturnVector.set(camera.direction.tmp().mul(-orbitRadius));
	    camera.translate(orbitReturnVector.x, orbitReturnVector.y, orbitReturnVector.z);
		camera.update();
		camera.apply(gl);
		
		 orbitRadius = (new Vector3(0,0,0)).dst(camera.position);
		camera.position.set(new Vector3(0,0,0));
		camera.rotate(Splash.cameraVertAngle, 1, 0, 0);
		 orbitReturnVector = new Vector3(0, 0, 0);
		orbitReturnVector.set(camera.direction.tmp().mul(-orbitRadius));
	    camera.translate(orbitReturnVector.x, orbitReturnVector.y, orbitReturnVector.z);
		camera.update();
		camera.apply(gl);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		Splash.cameraHorizAngle=0;
		Splash.cameraVertAngle=0;
	}
	float[] direction = {0,0, 3, 1};

	private void setLighting (GL10 gl) {
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		
	
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, new float[]{0.25f, 0.25f, 0.29f, 1f}, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, new float[]{0.99f, 0.99f, 0.79f, 1f}, 0);
		//bright yellow light
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, new float[]{.9f, .9f, 1f, 1f}, 0);
		
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
		
	}

	private void renderStaticShip (GL10 gl, Application app) {
		
		shipTexture.bind();
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0);
		shipMesh.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();
		float noise = (float) Math.random()/2;
		renderJet(gl,2.3f,1f+noise, 1.1f, -.5f, 1.8f, app);
		noise = (float) Math.random()/2;
		renderJet(gl,2.3f,1f+noise, -1.1f, -.5f, 1.8f, app);
		
	}
	private void renderSky (GL10 gl) {
	
		gl.glDisable(GL10.GL_LIGHTING);
		skyTexture.bind();
		gl.glColor4f(1, 1,1, 1);

		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0);
		gl.glScalef(100f,100f,100f);
		skyMesh.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();
		gl.glEnable(GL10.GL_LIGHTING);
	}
	private void renderPlanet (GL10 gl,String texture, boolean uvtype, float radius,float x,float y,float z, Application app) {
		radius=radius*worldscale;
		
		if (texture.equals("earth")){
			//render earth/jupiter  texture
			planetTexture.bind();
		}else if (texture.equals("sun")){
			//render mars/sun  texture
			planetTexture.bind();
		}
		
		gl.glPushMatrix();
		//move away from origin
		gl.glTranslatef(x,y,z);
		//scale to 10% size of earth
		gl.glScalef(radius,radius,radius);
		gl.glRotatef((Splash.planetmove/10)-180, 0, 1, 0);
		if (uvtype==true){
			//render lower planet texture
			planetMesh.render(GL10.GL_TRIANGLES);
		}else{
			//render upper planet texture
			planetMesh02.render(GL10.GL_TRIANGLES);
		}
		gl.glPopMatrix();
	
	}
	private void renderSun (GL10 gl, float radius,float x,float y,float z, Application app) {
		
		gl.glDisable(GL10.GL_LIGHTING);
		radius=radius*worldscale;
		sunTexture.bind();
		gl.glPushMatrix();
		//move away from origin
		gl.glTranslatef(x,y,z);
		//scale to size of earth
		gl.glScalef(radius,radius,radius);
		gl.glDisable(GL10.GL_CULL_FACE);
		
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		gl.glPushMatrix();
		gl.glDisable(GL10.GL_LIGHTING);
		//gl.glRotatef((Splash.planetmove/10), .25f, .75f, .5f);
		sunMesh.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		
	}
	
	  /**
     * render ship jets
     * @param basescale :- scale factor for jet size before applying thrust
     * @param length  :- scale factor for jet length, typically thrust value + random noise
     * @param x The jet's x-coordinate.
     * @param y The jet's  y-coordinate.
     * @param z The jet's  z-coordinate.
     * @return The product, in a vector <#, #, #, 1>, representing the
     * rotated point.
     */
private void renderJet (GL10 gl,float baseScale, float length,float x,float y,float z, Application app) {
		jetTexture.bind();
		gl.glPushMatrix();
		//move away from origin
		gl.glTranslatef(x,y,z);
		//scale to size of earth
		gl.glScalef(baseScale,baseScale,baseScale*length);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		gl.glDisable(GL10.GL_LIGHTING);
		//gl.glRotatef((Splash.planetmove/10), .25f, .75f, .5f);
		jetMesh.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();
		gl.glEnable(GL10.GL_LIGHTING);
		//gl.glEnable(GL10.GL_DEPTH_TEST);
		}


	public void dispose () {
		spriteBatch.dispose();
		
	}
}
