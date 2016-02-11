/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.digitale.screens;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.digitale.mygdxgame.SplashRenderer;
import com.digitale.mygdxgame.Stardust3d;


public class Splash extends StardustScreen{
	OrthographicCamera camera;
	int loadstate=1;
	/** the renderer **/
	private final SplashRenderer renderer;
	
	public static int i=0;
	public static com.badlogic.gdx.math.Vector3 touchPoint = new com.badlogic.gdx.math.Vector3();

	public static float cameraHorizAngle =-180;
	public static int cameraVertAngle = 15;
	private Timer timer = new Timer();
	private float planetspeed=125;
	private float rotationspeed=20.0f;
	public static boolean fadeOut;
	public static boolean fadeIn;
	public static float fadeTimer;
	public static float planetmove;
	
	public Splash() {
		
		Gdx.input.getInputProcessor();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		renderer =  new SplashRenderer(Gdx.app);
		
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() { 
				i++;
			
				
				//detect and set black fade in
				if (i<10 && fadeIn==false ){
						fadeIn=true;
						fadeTimer=1;
				}
				if (fadeIn){
						fadeTimer=fadeTimer-0.1f; 
						if(Stardust3d.DEBUG) System.out.println("fade "+fadeTimer);
					}
				if (fadeIn && fadeTimer<0){
					fadeIn=false;
					fadeTimer=0;
				}
				//detect and set white fade in
				if (i>90 && fadeOut==false){
					fadeTimer = 0;
					fadeOut=true;
				}
				if (fadeOut){
					fadeTimer=fadeTimer+0.1f; 
					if(Stardust3d.DEBUG) System.out.println("fade "+fadeTimer);
				}
				if (i>100){
					fadeTimer=1;
				}
				
				
				if (i %10==0 && loadstate==(int) i/10 && Stardust3d.Error==Stardust3d.ALL_OK){
					if(Stardust3d.DEBUG) System.out.println("loading stage "+(int) i/10);
			incrementalloader((int) i/10);
			
				}
				if (i==1){
					//SoundManager.playmusic();
				}
				if(Stardust3d.DEBUG) System.out.println("timer "+i%10+"  div"+(int) i/10+ " loadstate "+loadstate );
				}	
		}, 0, 100);
	
	
	}

	@Override
	public void dispose() {
		renderer.dispose();
	

	}

	@Override
	public boolean isDone() {
		if (i>100 && Stardust3d.manager.update()){
			timer.cancel();
			Stardust3d.gameMode=2;
			return true;
			
		}
			
		return false;
	}

	@Override
	public void render(float delta) {
		
		
		renderer.render(Gdx.app);
	}
private void incrementalloader(int time){
	switch (time){
	case 1:
		//load some stuff
		Stardust3d.loadstage01();
		//increment loaded flag
		loadstate=2;
		break;
	case 2:
	//load some stuff
		Stardust3d.loadstage02();
			//increment loaded flag
	loadstate=3;
			break;
	case 3:
	//load some stuff
		Stardust3d.loadstage03();
			//increment loaded flag
	loadstate=4;
			break;
	case 4:
	//load some stuff
		Stardust3d.loadstage04();
	//increment loaded flag
	loadstate=5;
			break;
			
	case 5:
	//load some stuff
		Stardust3d.loadstage05();
		//increment loaded flag
		loadstate=6;
				break;
	case 6:
	//load some stuff
		Stardust3d.loadstage06();
		//increment loaded flag
		loadstate=7;
				break;
	case 7:
	//load some stuff
		Stardust3d.loadstage07();
		//increment loaded flag
		loadstate=8;
				break;
	case 8:
	//load some stuff
		Stardust3d.loadstage08();
		//increment loaded flag
		loadstate=9;
				break;
	case 9:
	//load some stuff
		Stardust3d.loadstage09();
		//increment loaded flag
		loadstate=10;
				break;
	case 10:
	//load some stuff
		Stardust3d.loadstage10();
		//increment loaded flag
		loadstate=11;
				break;
	}
	
		
	
}
	@Override
	public void update(float delta) {
	
		
		cameraHorizAngle=cameraHorizAngle+(rotationspeed*delta);
		planetmove=planetmove+(delta*planetspeed);
		//System.out.println("planet x="+planetmove);
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
