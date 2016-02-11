/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.digitale.screens;

import com.digitale.utils.Actors;
import com.digitale.utils.Actors.DialogListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.digitale.connex.Inventory;
import com.digitale.mygdxgame.Renderer;
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.Stardust3d;
import com.digitale.sim.Explosion;
import com.digitale.sim.Ship;
import com.digitale.sim.Simulation;
import com.digitale.sim.SimulationListener;

public class GameLoop extends StardustScreen implements SimulationListener {
	public static OrthographicCamera camera;
	Skin skin;
	Actor dialog;
	/** the simulation **/
	private final Simulation simulation;
	float touch_x, touch_y;
	/** the renderer **/
	public final Renderer renderer;
	private boolean menuSelected;
	public static int androidVersion;
	public static float screenScaleX;
	public static  float screenScaleY;

	public static com.badlogic.gdx.math.Vector3 touchPoint = new com.badlogic.gdx.math.Vector3();

	public static int cameraHorizAngle = 0;
	public static int cameraVertAngle = 10;

	public static int droneOrbitAngle = 0;

	public GameLoop() {
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		simulation = new Simulation();
		simulation.listener = this;
		renderer = new Renderer(Gdx.app);
	
	}

	@Override
	public void dispose() {
		renderer.dispose();

	}

	@Override
	public boolean isDone() {
		// if (simulation.ship.lives == 0)
		// return true;
		return false;
	}

	@Override
	public void render(float delta) {
		Gdx.app.getGraphics().getGL10()
				.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		renderer.render(Gdx.app, simulation);
	}

	@Override
	public void update(float delta) {
		simulation.update(delta);
		droneOrbitAngle = droneOrbitAngle + 1;
		// detect and set black fade in
		// cameraHorizAngle= (int) (Ship.yawAngle/Math.PI);

		Input input = Gdx.app.getInput();
		 androidVersion = Gdx.app.getVersion();
		float turnSpeed = .8f * simulation.gameSpeed;
		// System.out.println("accelx " + input.getAccelerometerX() + " accly "
		// + input.getAccelerometerY());
		if (Ship.STATUS == 1 && Stardust3d.stationScreen == 0) {
			// only check accelerometer on android platforms
			if (androidVersion != 0) {
				accellerometer(input);
			}

			checkShipContols(input, turnSpeed,delta);
			// check rotations haven't gone too far
			rectifyShipAngles();
			// set ship rotation (Quat) yaw and pitch from Euler
			Ship.shipRot.setEulerAngles(Ship.yawAngle, Ship.pitchAngle, 0);
			Vector3 heading = new Vector3(0, 0, -1);

			Ship.shipRot.transform(heading);
			Simulation.ship.heading = heading;
			// add heading vector to ship position
			Ship.position.add(heading.mul(Ship.SHIP_VELOCITY * delta));

			rectifyTouchCoords(input);

			if (input.isTouched()) {
				menuSelected=false;
				if (Stardust3d.DEEPDEBUG)
					System.out.println("x=" + touch_x + " y=" + touch_y);
			//	thumbcontrol();
				rightmenu();
				throttle(delta);
				leftmenu();
				if (!menuSelected){
				checkForSelection();
				}
			}
			
			
		}
		if (Ship.STATUS == 0 && Stardust3d.stationScreen == 0) {
			rectifyTouchCoords(input);
			if (input.isTouched()) {

				if (Stardust3d.DEBUG)
					System.out.println("x=" + touch_x + " y=" + touch_y);
				thumbcontrol();
				leftmenu();
				dockedrightmenu();
			}

		}

	}
private void checkForSelection(){
	boolean locked=false;
	for (int index=0; index < Stardust3d.actorsList.size();index++){
	
		com.digitale.connex.Actor actor = Stardust3d.actorsList.get(index);
		actor.locked=false;
		if (actor.visible && actor.reticulepos!=null && locked==false && actor.position.dst(Ship.position) < 5000){
	if (480-touch_y > ((actor.reticulepos.y+16)-32) && 480-touch_y < (actor.reticulepos.y+16+32)){
		if (touch_x > (actor.reticulepos.x+16)-32 && touch_x < (actor.reticulepos.x+16+32)){	
			if (Stardust3d.DEBUG)
				System.out.println("touching actorx="+ actor.get_uid()+" x " + touch_x + " y=" + touch_y);
		actor.locked=true;
		locked=true;
		}}
	else{
		actor.locked=false;
	}
		}else{
			actor.locked=false;
		}
	}
}
	/**
	 * @param input
	 */
	public void accellerometer(Input input) {
		// accelerometer
		if (input.getAccelerometerY() < -Stardust3d.deadzoneX || input.getAccelerometerY() > Stardust3d.deadzoneX)
			Ship.yawAngle -= (input.getAccelerometerY() * Stardust3d.accelXsensitivity);

		if (input.getAccelerometerX() < 4-Stardust3d.deadzoneY) {
			Ship.pitchAngle -= Math.abs(input.getAccelerometerX() - (4-Stardust3d.deadzoneY)) * Stardust3d.accelYsensitivity;
		} else if (input.getAccelerometerX() > 5+Stardust3d.deadzoneY) {
			Ship.pitchAngle += Math.abs(input.getAccelerometerX() - ( 5+Stardust3d.deadzoneY)) * Stardust3d.accelYsensitivity;
		}
	}

	/**
	 * if ship pitch/yaw angles have gone beyond 0-360
	 */
	private void rectifyShipAngles() {
		if (Ship.pitchAngle < 0)
			Ship.pitchAngle = (360 - Ship.pitchAngle);
		else if (Ship.pitchAngle > 360)
			Ship.pitchAngle = (Ship.pitchAngle - 360);
		if (Simulation.ship.rollAngle < 0)
			Simulation.ship.rollAngle = (360 - Simulation.ship.rollAngle);
		else if (Simulation.ship.rollAngle > 360)
			Simulation.ship.rollAngle = (float) (Simulation.ship.rollAngle - 360);
		if (Ship.yawAngle < 0)
			Ship.yawAngle = (360 - Ship.yawAngle);
		else if (Ship.yawAngle > 360)
			Ship.yawAngle = (float) (Ship.yawAngle - 360);
	}

	/**
	 * @param input
	 *            set touch co-ords so they match screen co-ords
	 */
	private void rectifyTouchCoords(Input input) {
		screenScaleX=Gdx.app.getGraphics().getWidth()/800f;
		screenScaleY=Gdx.app.getGraphics().getHeight()/480f;
		camera.unproject(GameLoop.touchPoint.set(Gdx.input.getX(),
				Gdx.input.getY(), 0));
		int x = input.getX();
		int y = input.getY();
		if (input.justTouched()) {
			touch_x = input.getX();
			touch_y = input.getY();
		}

		if (input.isTouched()) {
			x += input.getX() - touch_x;
			y += touch_y - input.getY();
			touch_x = input.getX(); // x;
			touch_y = input.getY(); // y;
		}
	
		touch_x=touch_x/screenScaleX;
		touch_y=touch_y/screenScaleY;
		if (Stardust3d.DEEPDEBUG)
			System.out.println("Width:"+Gdx.app.getGraphics().getWidth()+
					"Height:"+Gdx.app.getGraphics().getHeight()+
					"xscale:" + screenScaleX + " yscale:"+screenScaleY);
	}

	/**
	 * @param input
	 * @param turnSpeed
	 */
	private void checkShipContols(Input input, float turnSpeed,float delta) {
		/*
		 * A minimum of dataset B maximum of dataset a is from where you would
		 * like normalised data set to start b is where you would like
		 * normalised data set to end x is the value you are trying to normalise
		 * a + (x-A)*(b-a)/(B-A)
		 */
		float pitch = 0.5f + (Ship.SHIP_VELOCITY - 0) * (2.0f - 0.5f)
				/ (Ship.SHIP_MAXVELOCITY - 0);
		if (Stardust3d.DEEPDEBUG)
			System.out.println("enginepitch " + pitch);
		SoundManager.changeEnginePitch(pitch);
		if (input.isKeyPressed(Keys.DPAD_LEFT) ||input.isKeyPressed(Keys.A) )
		
			Ship.yawAngle += turnSpeed;
			
			if (input.isKeyPressed(Keys.DPAD_RIGHT) ||input.isKeyPressed(Keys.D))
		
			Ship.yawAngle -= turnSpeed;
	
		if (input.isKeyPressed(Keys.DPAD_UP) ||input.isKeyPressed(Keys.W))

			Ship.pitchAngle += turnSpeed;

		if (input.isKeyPressed(Keys.DPAD_DOWN) ||input.isKeyPressed(Keys.S))
			Ship.pitchAngle -= turnSpeed;
		if (input.isKeyPressed(Keys.SPACE))
			simulation.shot();
		if (input.isKeyPressed(Keys.Q))
			simulation.missile();
		if (input.isKeyPressed(Keys.E))
			simulation.repair();
		/*
		 * if (input.isKeyPressed(Keys.A)) Ship.SHIP_VELOCITY +=
		 * Ship.SHIP_ACCELERATION; if (Ship.SHIP_VELOCITY >
		 * Ship.SHIP_MAXVELOCITY) Ship.SHIP_VELOCITY = Ship.SHIP_MAXVELOCITY; if
		 * (input.isKeyPressed(Keys.Z)) Ship.SHIP_VELOCITY -=
		 * Ship.SHIP_ACCELERATION; if (Ship.SHIP_VELOCITY < 0)
		 * Ship.SHIP_VELOCITY = 0; if (input.isKeyPressed(Keys.S))
		 * Ship.SHIP_VELOCITY = 0;
		 */
		if (input.isKeyPressed(Keys.R)) {
			Ship.SHIP_POWER = 1f*delta;
		} else if (input.isKeyPressed(Keys.F)) {
			Ship.SHIP_POWER = -1f*delta;

		} else {

			Ship.SHIP_POWER = 0;
			Ship.SHIP_ACCELERATION = 0;
		}
		Ship.SHIP_ACCELERATION += Ship.SHIP_POWER;
		if (Ship.SHIP_ACCELERATION > Ship.SHIP_MAXACCELERATION)
			Ship.SHIP_ACCELERATION = Ship.SHIP_MAXACCELERATION;

		if (Ship.SHIP_VELOCITY < 0)
			Ship.SHIP_VELOCITY = 0;
		if (Ship.SHIP_VELOCITY > Ship.SHIP_MAXVELOCITY)
			Ship.SHIP_VELOCITY = Ship.SHIP_MAXVELOCITY;

	}

	/**
	 * 
	 */
	private void throttle(float delta) {

		if ((touch_x > 0) && (touch_x < 64)) {
			if ((touch_y > 128) && (touch_y < 352)) {
				menuSelected=true;
				if (touch_y < 240) {
					Ship.SHIP_POWER = 75f*delta;
				} else if (touch_y > 240) {
					Ship.SHIP_POWER = -75f*delta;
				}
			} else {
				Ship.SHIP_POWER = 0;
				Ship.SHIP_ACCELERATION = 0;
			}
		}
		Ship.SHIP_ACCELERATION += Ship.SHIP_POWER;
		if (Ship.SHIP_ACCELERATION > Ship.SHIP_MAXACCELERATION)
			Ship.SHIP_ACCELERATION = Ship.SHIP_MAXACCELERATION;

		if (Ship.SHIP_VELOCITY < 0)
			Ship.SHIP_VELOCITY = 0;
		if (Ship.SHIP_VELOCITY > Ship.SHIP_MAXVELOCITY)
			Ship.SHIP_VELOCITY = Ship.SHIP_MAXVELOCITY;
	}

	private void thumbcontrol() {
		if ((touch_y > 352) && (touch_y < 480) && (touch_x > 0)
				&& (touch_x < 128)) {
			if (Stardust3d.DEBUG)
				System.out.println("thumbcontrol");
			menuSelected=true;
			if ((touch_y > 400) && (touch_y < 432)) {
				if ((touch_x > 0) && (touch_x < 48)) {

					cameraHorizAngle = cameraHorizAngle + 1;
					// System.out.println("cam rot left");
				}
				if ((touch_x > 80) && (touch_x < 128)) {
					cameraHorizAngle = cameraHorizAngle - 1;
					// System.out.println("cam rot right");
				}

			}
			if ((touch_x > 48) && (touch_x < 80) && Ship.STATUS == 0) {
				if ((touch_y > 432) && (touch_y < 480)) {
					cameraVertAngle = cameraVertAngle + 1;
					if (cameraVertAngle > 100)
						cameraVertAngle = 100;
					// System.out.println("cam rot down");
				}
				if ((touch_y < 400) && (touch_y > 352)) {
					cameraVertAngle = cameraVertAngle - 1;
					if (cameraVertAngle < -30)
						cameraVertAngle = -30;
					// System.out.println("cam rot up");
				}
				/*
				 * if ((touch_y < 432) && (touch_y > 352)) { cameraHorizAngle =
				 * 0; cameraVertAngle=10; // System.out.println("cam rot up"); }
				 */
			}
			if ((touch_x > 48) && (touch_x < 80) && Ship.STATUS == 1) {
				if ((touch_y > 432) && (touch_y < 480)) {
					cameraVertAngle = cameraVertAngle + 1;
					if (cameraVertAngle > 30)
						cameraVertAngle = 30;
					// System.out.println("cam rot down");
				}
				if ((touch_y < 400) && (touch_y > 352)) {
					cameraVertAngle = cameraVertAngle - 1;
					if (cameraVertAngle < -30)
						cameraVertAngle = -30;
					// System.out.println("cam rot up");
				}
				/*
				 * if ((touch_y < 432) && (touch_y > 352)) { cameraHorizAngle =
				 * 0; cameraVertAngle=10; // System.out.println("cam rot up"); }
				 */
			}

		}
	}

	/**
	 * 
	 */
	private void rightmenu() {
		if ((touch_x > 800 - 64) && (touch_x < 800)) {

			if ((touch_y > 0) && (touch_y < 64)) {
				// Stardust3d.showmap();
				menuSelected=true;
				if (Stardust3d.DEBUG)
					System.out.println("inspace showmap clicked ");
				SoundManager.playFanfare();
				Simulation.explosions
				.add(new Explosion(Ship.position,
						Explosion.LEVELUP));
				Renderer.toastShown = false;
				Stardust3d.Error = Stardust3d.WIP_ERROR;
			} else if ((touch_y > 64) && (touch_y < 128)) {
				menuSelected=true;
				if (Stardust3d.DEBUG)
					System.out.println("dock clicked ");
				if (Ship.canDock == true && Ship.docking == false) {

					Ship.docking = true;
				}
			} else if ((touch_y > 480 - 128 - 64) && (touch_y < 480 - 128)) {
				menuSelected=true;
				simulation.repair();

			} else if ((touch_y > 480 - 128) && (touch_y < 480 - 64)) {
				simulation.missile();
				menuSelected=true;
			} else if ((touch_y > 480 - 64) && (touch_y < 480)) {
				simulation.shot();
				menuSelected=true;
				if (Stardust3d.DEBUG)
					System.out.println("shoot clicked ");
			}
		}
	}

	/**
	 * 
	 */

	// check right menu when docked.
	private void dockedrightmenu() {
		if ((touch_x > 800 - 64) && (touch_x < 800)) {

			if ((touch_y > 0) && (touch_y < 64)) {
				// Stardust3d.showmap();
				Renderer.toastShown = false;
				SoundManager.playuiclick();
				Stardust3d.stationScreen = 12;
				if (Stardust3d.DEBUG)
					System.out.println("showmap clicked ");
			} else if ((touch_y > 64) && (touch_y < 128)
					&& Ship.docking == false) {
				SoundManager.playuiclick();
				if (Stardust3d.DEBUG)
					System.out.println("undock clicked ");
				Ship.undocking = true;

			} else if ((touch_y > 480 - 128 - 64) && (touch_y < 480 - 128)) {
				SoundManager.playuiclick();
				Stardust3d.stationScreen = 10;
				if (Stardust3d.DEBUG)
					System.out.println("mission clicked ");
			} else if ((touch_y > 480 - 128) && (touch_y < 480 - 64)) {
				if (Stardust3d.DEBUG)
					System.out.println("vendor clicked ");
				SoundManager.playuiclick();
				Stardust3d.stationScreen = 1;
			} else if ((touch_y > 480 - 64) && (touch_y < 480)
					&& Renderer.toastShown == false) {
				SoundManager.playuiclick();
				Stardust3d.stationScreen = 11;
				if (Stardust3d.DEBUG)
					System.out.println("build clicked ");
			}
		}
	}

	private void undockShip() {
		Ship.STATUS = 1;
		// update DB

	}

	private void leftmenu() {
		// is touch in the pulled out menu's horizontal area, is menu already pulled
		if ((touch_x > 0)
				&& (touch_x < simulation.iconWidth * simulation.numMenuItems)&& simulation.pullout ) {
			
				// is touch in the pull out menu's vertical area?
				if ((touch_y > 32) && (touch_y < 96)) {
					// check which menu item is touched
					menuSelected=true;
					pulloutMenu();
				} else {// touch was not inside vertical area
					simulation.pullout = false;
					// if menu is not already pulled
						// is touch in the pull out menu's vertical area?
				}
			} else if((touch_x > 0)	&& (touch_x < 64 )){
				if ((touch_y > 32) && (touch_y < 96)) {
			
					menuSelected=true;
					// the user is touching on the pull out arrow
					simulation.pullout = true;
					if (Stardust3d.DEBUG)
						System.out.println("pullout clicked ");
				}
		} else {// if touch is outside pulled out menu's horizontal area, cancel
			simulation.pullout = false;
		}
		if((touch_x > 0)	&& (touch_x < 64 )){
			if ((touch_y > 0) && (touch_y < 64)) {
			
			}
			}
	}

	private void pulloutMenu() {
		// the menu has been pulled out
		// dont do while still animating pull out
		if ((simulation.slideOut == simulation.iconWidth
				* simulation.numMenuItems)) {
			// what is the user touching
			for (int i = 0; i < simulation.numMenuItems; i++) {
				// detect which icon finger is over
				if ((int) touch_x > i * 64 && (int) touch_x < (i * 64) + 64) {
					if (Stardust3d.DEBUG)
						System.out.printf("finger is in " + i);
					int mcurrentMenuItem = i;
					SoundManager.playuiclick();
					switch (mcurrentMenuItem) {
					case 0: {
						// chat
						simulation.pullout = false;
						Stardust3d.refreshChat(Stardust3d.chatChannel, false);
						Stardust3d.stationScreen = 4;
						break;
					}
					case 1: {
						// inventory
						simulation.pullout = false;
						// if docked show cargo and warehouse inventory
						if (Ship.STATUS == 0) {
							Stardust3d.stationScreen = 2;
						} else {
							// we are in space
							// display cargo only
							Stardust3d.stationScreen = 3;
						}
						break;
					}
					case 2: {
						// ship
						Stardust3d.stationScreen = 6;
						simulation.pullout = false;
						break;
					}
					case 3: {
						// char
						Stardust3d.stationScreen = 5;
						simulation.pullout = false;
						break;
					}
					case 4: {
						// market
						Stardust3d.stationScreen = 9;
						simulation.pullout = false;

						// Stardust3d.gameMode = 13;
						// tell stardust we need a new screen
						// Stardust3d.selectScreen = true;

						break;
					}
					case 5: {
						// help
						Stardust3d.stationScreen = 8;
						simulation.pullout = false;
						break;
					}
					case 6: {
						// options
						simulation.pullout = false;
						Stardust3d.stationScreen = 7;
						break;
					}
					case 7: {
						// exit
						simulation.pullout = false;
						Stardust3d.stationScreen = -1;
						DialogListener dialogListener = (new DialogListener() {
							@Override
							public void optionSelected(int option) {
								SoundManager.playuiclick();
								System.out.println("option " + option);
								// if close
								if (option == 1) {
									Renderer.stage.clear();
									Stardust3d.stationScreen = 0;
								}
								// if exit
								if (option == 0) {
									String passed = Stardust3d.MyDataOp
											.postChat(
													Stardust3d.myCharacter
															.getFirstname()
															+ " "
															+ Stardust3d.myCharacter
																	.getSurname()
															+ " has logged out.",
													1, 48);
									passed = Stardust3d.MyDataOp
											.logout(Stardust3d.myCharacter
													.getUid());
									Gdx.app.exit();
								}
							}

						});

						dialog = (Actors.twoOptionsDialog(
								"Are you sure you want to quit the game?",
								dialogListener, "Exit Game", "Exit", "Cancel",
								skin));
						Renderer.stage.addActor(dialog);

						break;
					}
					case 8: {
						// options
						simulation.pullout = false;
						Stardust3d.stationScreen = 13;
						break;
					}

					}
				}
			}
		}
	}

	@Override
	public void explosion() {
		SoundManager.playexplosion();
	}

	@Override
	public void boom() {
		SoundManager.playboom();
	}

	@Override
	public void shot() {
		SoundManager.playshot();
	}

	@Override
	public void missile() {
		SoundManager.playmissile();
	}

	@Override
	public void repair() {
		SoundManager.playRepair();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		Renderer.camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Renderer.camera.update();
		
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
