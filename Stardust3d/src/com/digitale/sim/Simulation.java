/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.sim;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.math.Vector3;
import com.digitale.connex.Actor;
import com.digitale.connex.ActorNameCache;
import com.digitale.connex.Inventory;
import com.digitale.mygdxgame.Renderer;
import com.digitale.mygdxgame.SoundManager;
import com.digitale.mygdxgame.Stardust3d;
import com.digitale.screens.GameLoop;

public class Simulation {
	static final int DELAY = 20; // Milliseconds between screen and
	static final int FPS = // the resulting frame rate.
	Math.round(1000 / DELAY);
	static final double SHIP_ANGLE_STEP = Math.PI / FPS;
	static final double SHIP_SPEED_STEP = 15.0 / FPS;
	static final double MAX_SHIP_SPEED = 100;
	public final static float PLAYFIELD_MIN_X = -100000;
	public final static float PLAYFIELD_MAX_X = 100000;
	public final static float PLAYFIELD_MIN_Z = -100000;
	public final static float PLAYFIELD_MAX_Z = 100000;
	public final static float PLAYFIELD_MIN_Y = -100000;
	public final static float PLAYFIELD_MAX_Y = 100000;
	protected static final String LOG_TAG = "Simultion";

	public boolean docking = false;
	// public ArrayList<Invader> invaders = new ArrayList<Invader>();
	public ArrayList<Block> blocks = new ArrayList<Block>();
	public ArrayList<Shot> shots = new ArrayList<Shot>();
	public ArrayList<Missile> missiles = new ArrayList<Missile>();
	public ArrayList<Trail> trails = new ArrayList<Trail>();
	public ArrayList<Drone> drones = new ArrayList<Drone>();
	public static ArrayList<Dust> dusts = new ArrayList<Dust>();
	public static ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	public static Ship ship;
	public Shot shipShot = null;
	public Missile shipMissile = null;
	public Trail shipTrail = null;
	public Station station;
	public transient static SimulationListener listener;
	public float multiplier = 1;
	public int score;
	public int wave = 1;
	public float gameSpeed = 1.0f;
	private ArrayList<Trail> removedTrails = new ArrayList<Trail>();
	private ArrayList<Shot> removedShots = new ArrayList<Shot>();
	private ArrayList<Explosion> removedExplosions = new ArrayList<Explosion>();
	// slide out menu vars
	public boolean pullout = false;
	public int slideOut = 0;
	public int iconWidth = 64;
	public int numMenuItems = 9;
	public static int docktick = 0;
	private long dockstart;
	public static Timer timer = new Timer();
	private static Timer dockTimer = new Timer();
	public static float hurtflash = 0f;
	int attackTick = 0;
	int ticks = 0;
	private float enginePitch;
	private float currentLevelSize = 1000000;
	public static float healthGauge;
	public static float powerGauge;
	public static float speedGauge;
	public static Vector3 camera = new Vector3(0, 0, 0);
	public static float exp;

	public Simulation() {

		populate();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {

				if (Stardust3d.DEEPDEBUG)
					System.out.println("hurtflash " + hurtflash
							+ " attackTick " + attackTick);
				if (Stardust3d.DEEPDEBUG)
					System.out.println(ticks);
				if (Ship.docking)
					docktick++;
				if (Ship.undocking)
					docktick++;

				if (ship.mainGunCD > 0) {
					ship.mainGunCD--;
				}
				if (Ship.bounce>0){
					Ship.bounce++;
				}
				if (ship.repairCD > 0) {
					ship.repairCD--;
				}
				if (ship.missileCD > 0) {
					ship.missileCD--;
				}
				if (ship.attacked) {
					if (attackTick == 1)
						SoundManager.playAlarm();
					if (attackTick <= 5) {
						attackTick++;
						hurtflash = (float) (attackTick) / 5;
					} else if (attackTick > 5) {
						attackTick++;
						hurtflash = (1 - ((float) (attackTick) / 5));
						if (hurtflash <= 0) {
							ship.attacked = false;
							attackTick = 0;
						}
					}
					if (Stardust3d.DEEPDEBUG)
						System.out.println("hurtflash " + hurtflash
								+ " attackTick " + attackTick);
				}

				// trail();
				ticks++;
				if (ticks == 10)

					ticks = 0;
			}
		}, 0, 100);
	}

	private void populate() {
		// make a new player ship with position from the database
		if (Stardust3d.DEBUG)
			System.out.println("create ship at"
					+ Stardust3d.myCharacter.getFirstname() + "sysx "
					+ Stardust3d.myCharacter.getSysx() + "sysy "
					+ Stardust3d.myCharacter.getSysy() + "sysz "
					+ Stardust3d.myCharacter.getSysz());
		ship = new Ship(Stardust3d.myCharacter.getSysx(),
				Stardust3d.myCharacter.getSysy(),
				Stardust3d.myCharacter.getSysz(),
				Stardust3d.myCharacter.getStatus());

		station = new Station(0, 0, 0, 1);
		if (Ship.STATUS == 1 && Stardust3d.manager.isLoaded("sound/engine.mp3")) {
			enginePitch = 0.5f + (Ship.SHIP_VELOCITY - 0) * (2.0f - 0.5f)
					/ (Ship.SHIP_MAXVELOCITY - 0);
			SoundManager.playEngine(enginePitch);
		}
		/*
		 * drones.add(new Drone(new Vector3(Ship.position.x + 6,
		 * Ship.position.y, Ship.position.z))); drones.add(new Drone(new
		 * Vector3(Ship.position.x - 6, Ship.position.y, Ship.position.z)));
		 * drones.add(new Drone(new Vector3(Ship.position.x, Ship.position.y +
		 * 6, Ship.position.z))); drones.add(new Drone(new
		 * Vector3(Ship.position.x, Ship.position.y - 6, Ship.position.z)));
		 * 
		 * for (int shield = 0; shield < 1; shield++) { blocks.add(new Block(new
		 * Vector3(-10 + shield * 10 - 1, 0, -2))); blocks.add(new Block(new
		 * Vector3(-10 + shield * 10 - 1, 0, -3))); blocks.add(new Block(new
		 * Vector3(-10 + shield * 10 + 0, 0, -3))); blocks.add(new Block(new
		 * Vector3(-10 + shield * 10 + 1, 0, -3))); blocks.add(new Block(new
		 * Vector3(-10 + shield * 10 + 1, 0, -2))); }
		 * 
		 * }
		 */
		populateDust();
	}

	/**
	 * 
	 */
	public static void populateDust() {
		dusts.clear();
		for (int dust = 0; dust < Stardust3d.dustsize; dust++) {
			dusts.add(new Dust(new Vector3(((float) (Math.random() * 512))
					- 256 + Ship.position.x, ((float) (Math.random() * 512))
					- 256 + Ship.position.y, ((float) (Math.random() * 512))
					- 256 + Ship.position.z)));
		}
	}

	public void update(float delta) {
		// if(Stardust3d.DEBUG) System.out.printf (LOG_TAG,
		// "timer:  "+System.currentTimeMillis());
		updatePullOut(delta);

		for (int i = 0; i < Stardust3d.actorsList.size(); i++) {
			Stardust3d.actorsList.get(i).update(delta);
		}
		if (Ship.STATUS == 1) {

			updatedusts();
			gaugesUpdate();
			updateReticules();
			updateShots(delta);
			updateMissiles(delta);
		}
		ship.update(delta);
		//calculate expbar
		exp = 0 + (Stardust3d.myCharacter.getExp() - 0) * (576 - 0)
				/ (currentLevelSize - 0);
		if (Stardust3d.DEEPDEBUG)
			System.out.println("exp" + exp);
		updateStation(delta);
		if (Ship.docking == true)
			updateDock();
		if (Ship.undocking == true)
			updateUndock();
		updateExplosions(delta);
		checkShipCollision(delta);
		updateStatusline();
		checkInvaderCollision();
		Renderer.stationAngle += delta * 1;
		if (Renderer.stationAngle > 360)
			Renderer.stationAngle -= 360;

		Renderer.invaderAngle += delta * 10;
		if (Renderer.invaderAngle > 360)
			Renderer.invaderAngle -= 360;
	}

	public static void updateReticules() {
		Vector3 tmp;
		// for every actor
		for (int i = 0; i < Stardust3d.actorsList.size(); i++) {
			// is it renderable?
			Actor actor = Stardust3d.actorsList.get(i);
			if (actor != null && !(actor.shipname.equals("dead"))
					&& actor.getHitpoints() > 0) {
				// only do if actor is in space
				tmp = new Vector3((actor.position));

				Renderer.camera.project(tmp);

				tmp.x = (tmp.x / GameLoop.screenScaleX) - 16;
				tmp.y = (tmp.y / GameLoop.screenScaleY) - 16;
				actor.reticulepos = tmp;
				if (Stardust3d.DEEPDEBUG)
					System.out.println("reticule " + tmp.x + ":" + tmp.y + ":"
							+ tmp.z);
				actor.distance = actor.position.dst(Ship.position);
				actor.distanceString =Integer.toString(Math.round(actor.distance / 10));
				actor.distanceString = actor.distanceString + "km";
			}
		}
	}

	private void updatedusts() {
		for (int i = 0; i < dusts.size(); i++) {
			Dust dust = dusts.get(i);
			dust.Update();
		}

	}

	private void updateMissiles(float delta) {
		for (int i = 0; i < missiles.size(); i++) {
			Missile missile = missiles.get(i);
			missile.update(delta);

			if (missile.hasLeftField)
				missiles.remove(i);
		}

	}

	private void updateStatusline() {
		DecimalFormat df2 = new DecimalFormat("000000000.00");
		String xprint = String.format("%05.0f", Ship.position.x);
		String yprint = String.format("%05.0f", Ship.position.y);
		String zprint = String.format("%05.0f", Ship.position.z);
		String rollprint = String.format("%03.0f", Ship.yawAngle);
		String pitchprint = String.format("%03.0f", Ship.pitchAngle);
		Renderer.status = "x:" + xprint + " y:" + yprint + " z:" + zprint
				+ " roll:" + rollprint + " pitch:" + pitchprint + " thrust:"
				+ Ship.SHIP_VELOCITY + " hp:"
				+ String.format("%05.0f", Ship.SHIP_HEALTH) + " GameTime:"
				+ Stardust3d.mserverTime + " Ping:"
				+ String.format("%05d", Stardust3d.mnetworkPing) + " FPS:"
				+ Gdx.graphics.getFramesPerSecond();

	}

	private void gaugesUpdate() {
		/*
		 * A minimum of dataset B maximum of dataset a is from where you would
		 * like normalised data set to start b is where you would like
		 * normalised data set to end x is the value you are trying to normalise
		 * a + (x-A)*(b-a)/(B-A)
		 */
		healthGauge = 0 + (Ship.SHIP_HEALTH - 0) * (1 - 0)
				/ (Ship.SHIP_MAXHEALTH - 0);
		float power = 100 - (Ship.SHIP_VELOCITY / 10);
		powerGauge = 0 + (power - 0) * (1 - 0) / (100 - 0);

		speedGauge = 0 + (Ship.SHIP_VELOCITY - 0) * (1 - 0)
				/ (Ship.SHIP_MAXVELOCITY - 0);

	}

	// manage pull-out menu animations
	private void updatePullOut(float delta) {
		if (pullout) {
			if (Stardust3d.DEBUG)
				System.out.println("pullout detected");
			if (slideOut <= 0) {
				slideOut = 1;
				SoundManager.playwhoosh();
			}
			if ((slideOut <= iconWidth * numMenuItems) && (slideOut >= 1)) {
				slideOut = (int) (slideOut + (2000 * delta));
				if (Stardust3d.DEBUG)
					System.out.println("slide " + slideOut);
			}
			if (slideOut >= iconWidth * numMenuItems) {

				slideOut = iconWidth * numMenuItems;
			}
		} else if (slideOut >= 1) {
			if (slideOut >= iconWidth * numMenuItems) {
				slideOut = (iconWidth * numMenuItems) - 1;
				SoundManager.playwhoosh();
			}
			if ((slideOut <= (iconWidth * numMenuItems) - 1) && (slideOut >= 1)) {
				slideOut = (int) (slideOut - (2000 * delta));
				if (Stardust3d.DEBUG)
					System.out.println("slide " + slideOut);
			}
			if (slideOut <= 0) {
				slideOut = 0;
			}
		}
	}

	private void updateDrones(float delta) {
		for (int i = 0; i < drones.size(); i++) {
			Drone drone = drones.get(i);
			drone.update(delta, multiplier, ship);
		}
	}

	private void updateStation(float delta) {
		Station.yawAngle = Station.yawAngle + Station.Rotspeedy * delta;
	}

	private void updateInvaders(float delta) {
		// for (int i = 0; i < Stardust3d.actorsList.size(); i++) {
		// Actor actor = Stardust3d.actorsList.get(i);
		// actor.position.set(Stardust3d.get_position(),
		// Stardust3d.localActors[i].getSysy(),
		// Stardust3d.localActors[i].getSysz());
		// invader.update(delta, multiplier);
		// }
	}

	private void updateShots(float delta) {
		removedShots.clear();// clear removed shots list
		for (int i = 0; i < shots.size(); i++) { // for all the shots
			Shot shot = shots.get(i);// get shot info
			shot.update(delta);// update shot vs time
			if (shot.hasLeftField)
				removedShots.add(shot); // if shot has left play field remove
										// from list of shots
		}

		for (int i = 0; i < removedShots.size(); i++)
			// for all the removed shots
			shots.remove(removedShots.get(i));// cleanup

		if (shipShot != null && shipShot.hasLeftField)
			shipShot = null;// if play shot exists and has left playfield kill
							// it

		/*
		 * if random is 1 in 100and there is an invader left if (Math.random() <
		 * 0.01 * multiplier && invaders.size() > 0) { // find a random invader
		 * int index = (int) (Math.random() * (invaders.size() - 1)); // add
		 * shot at that invaders position Shot shot = new
		 * Shot(invaders.get(index).position, true); shots.add(shot);// add that
		 * shot to the shot list if (listener != null) listener.shot(); // tell
		 * the renderer about it }
		 */
	}

	private void updateTrails(float delta) {
		removedTrails.clear();// clear removed shots list
		for (int i = 0; i < trails.size(); i++) { // for all the shots
			Trail trail = trails.get(i);// get shot info
			if (trail != null) {
				trail.update(delta);// update shot vs time
				if (trail.hasLeftField)
					removedTrails.add(trail); // if shot has left play field
												// remove
												// from list of shots
			}
		}
		for (int i = 0; i < removedTrails.size(); i++)
			// for all the removed shots
			trails.remove(removedTrails.get(i));// cleanup

		if (shipTrail != null && shipTrail.hasLeftField)
			shipTrail = null;// if play shot exists and has left playfield kill
								// it

	}

	public void updateExplosions(float delta) {
		removedExplosions.clear(); // get rid of all removed explosions
		for (int i = 0; i < explosions.size(); i++) {// for all explosions
			Explosion explosion = explosions.get(i);// get explosion object
			explosion.update(delta);// update that explosion due to time
			// if explosion has existed beyond predefined lifetime add to
			// removal list
			if (explosion.aliveTime > explosion.explosionLiveTime)
				removedExplosions.add(explosion);
		}

		for (int i = 0; i < removedExplosions.size(); i++)
			// for all the removed explosions
			explosions.remove(removedExplosions.get(i)); // delete their objects
	}

	private void checkInvaderCollision() {
		if (shipShot != null) {
			try {
				for (int j = 0; j < Stardust3d.actorsList.size(); j++) {
					Actor actor = Stardust3d.actorsList.get(j); // for every
																// invader
					// for all the shots
					BigInteger zero = BigInteger.valueOf(0l);
					for (int i = 0; i < shots.size(); i++) {
						Shot currentShot = shots.get(i);
						// if actor is an NPC
						if (actor.get_uid().compareTo(zero) == -1
								&& (!actor.getShipname().equals("dead"))) {
							// if shipshot is within enemy radius
							if (actor.position.dst(currentShot.position) < Invader.INVADER_RADIUS) {

								Stardust3d.MyDataOp.newRequest("hitOpponent#"
										+ actor.get_uid() + "#" + 30);

								if (actor.getHitpoints() > 0) {
									actor.setHitpoints(actor.getHitpoints() - 30);
									Stardust3d.actorsList.set(j, actor);
									explosions.add(new Explosion(
											currentShot.position));
								}
								if (actor.getHitpoints() <= 0
										&& !actor.exploding) {
									actor.exploding = true;
									actor.locked = false;
									if (listener != null)
										listener.boom();
								/*	explosions
											.add(new Explosion(actor.position,
													Explosion.SWANSONG));*/
									
								}

								if (listener != null)
									listener.explosion();
								shots.remove(shots.get(i));// kill that shot

								break;

							}
						} else {
							if (Stardust3d.DEBUG) {
								// System.out.println("hit player");

							}
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (shipMissile != null) {
			try {
				for (int j = 0; j < Stardust3d.actorsList.size(); j++) {
					Actor actor = Stardust3d.actorsList.get(j); // for every
																// invader
					// for all the shots
					BigInteger zero = BigInteger.valueOf(0l);
					for (int i = 0; i < missiles.size(); i++) {
						Missile currentShot = missiles.get(i);
						// if actor is an NPC
						if (actor.get_uid().compareTo(zero) == -1
								&& (!actor.getShipname().equals("dead"))) {
							// if shipshot is within enemy radius
							if (actor.position.dst(currentShot.position) < Invader.INVADER_RADIUS) {

								Stardust3d.MyDataOp.newRequest("hitOpponent#"
										+ actor.get_uid() + "#" + 100);

								if (actor.getHitpoints() > 0) {
									actor.setHitpoints(actor.getHitpoints() - 100);
									Stardust3d.actorsList.set(j, actor);

									// invaders.remove(invader);// kill that
									// invader
									// spawn explosion at enemy pos
									explosions.add(new Explosion(
											currentShot.position));
								}
								if (actor.getHitpoints() <= 0
										&& !actor.exploding) {
									actor.exploding = true;
									if (listener != null)
										listener.boom();
									explosions
											.add(new Explosion(actor.position,
													Explosion.SWANSONG));
									Stardust3d.actorsList.remove(j);
								}

								if (listener != null)
									listener.explosion();
								missiles.remove(missiles.get(i));// kill that
																	// shot

								break;

							}
						} else {
							if (Stardust3d.DEBUG) {
								// System.out.println("hit player");

							}
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void checkShipCollision(float delta) {
		removedShots.clear();
		// check invader / ship collision
		if (Ship.STATUS == 1) {
			for (int i = 0; i < Stardust3d.actorsList.size(); i++) {
				Actor actor = Stardust3d.actorsList.get(i);
				if (ship.position.dst(actor.position)< (ship.SHIP_RADIUS/2)+(actor.INVADER_RADIUS/2)){
						// if invader collides with ship
				//Collide(delta, ship, actor);
				}
				}
			}
		
		// check if ship can dock
		// TODO
		// should be some kind of "if solarsystem contains station" here
		if (station.position.dst(Ship.position) < Station.STATION_RADIUS
				&& Ship.canDock == false) {// if invader shot is within ship
											// radius
			Ship.canDock = true; // set ship status to dockable
			if (Stardust3d.DEBUG)
				System.out.println("dockable");
			SoundManager.playconfirm();
		} else if (station.position.dst(Ship.position) > Station.STATION_RADIUS) {
			Ship.canDock = false;// set ship status to dockable

		}

		// check if ship / shot collision
		if (Ship.STATUS == 0) {
			if (!ship.isExploding) {
				for (int i = 0; i < shots.size(); i++) {
					Shot shot = shots.get(i);
					if (!shot.isInvaderShot)
						continue;
					// if invader shot is within ship radius
					if (Ship.position.dst(shot.position) < Ship.SHIP_RADIUS) {
						removedShots.add(shot); // add to removed shots array
						shot.hasLeftField = true; // set shot status to has left
													// the playfield
						ship.lives--;// decrement ship lives
						ship.isExploding = true; // set ship status to exploding
						// add new explosion to explosions list
						explosions.add(new Explosion(Ship.position));
						if (listener != null)
							listener.explosion(); // ?? if simulation listener
													// is set add explosion to
													// event queue
						break;
					}
				}

				for (int i = 0; i < removedShots.size(); i++)
					shots.remove(removedShots.get(i));
			}
		}

	

	}

	public void moveShipLeft(float delta, float scale) {
		if (ship.isExploding)
			return; // if ship is exploding don't bother moving

		Ship.position.x -= delta * Ship.SHIP_VELOCITY * scale;
		if (Ship.position.x < PLAYFIELD_MIN_X)
			Ship.position.x = PLAYFIELD_MIN_X;
	}

	public void rollShip(float delta, float value) {
		if (ship.isExploding)
			return; // if ship is exploding don't bother moving
		Ship.yawAngle = Ship.yawAngle + (value * delta);

	}

	public void pitchShip(float delta, float value) {
		if (ship.isExploding)
			return; // if ship is exploding don't bother moving
		Ship.pitchAngle = Ship.pitchAngle + (value * delta);

	}

	public void moveShipRight(float delta, float scale) {
		if (ship.isExploding)
			return; // if ship is exploding don't bother moving

		Ship.position.x += delta * Ship.SHIP_VELOCITY * scale;
		if (Ship.position.x > PLAYFIELD_MAX_X)
			Ship.position.x = PLAYFIELD_MAX_X;
	}

	public void moveShipForwards(float delta, float scale) {
		if (ship.isExploding)
			return; // if ship is exploding don't bother moving

		Ship.position.z -= delta * Ship.SHIP_VELOCITY * scale;
		if (Ship.position.z < PLAYFIELD_MIN_Z)
			Ship.position.z = PLAYFIELD_MIN_Z;
	}

	public void moveShipBack(float delta, float scale) {
		if (ship.isExploding)
			return; // if ship is exploding don't bother moving

		Ship.position.y += delta * Ship.SHIP_VELOCITY * scale;
		if (Ship.position.y > PLAYFIELD_MAX_Y)
			Ship.position.y = PLAYFIELD_MAX_Y;
	}

	public void moveShipUp(float delta, float scale) {
		if (ship.isExploding)
			return; // if ship is exploding don't bother moving

		Ship.position.y -= delta * Ship.SHIP_VELOCITY * scale;
		if (Ship.position.y < PLAYFIELD_MIN_Y)
			Ship.position.y = PLAYFIELD_MIN_Y;
	}

	public void moveShipDown(float delta, float scale) {
		if (ship.isExploding)
			return; // if ship is exploding don't bother moving

		Ship.position.y += delta * Ship.SHIP_VELOCITY * scale;
		if (Ship.position.y > PLAYFIELD_MAX_Y)
			Ship.position.y = PLAYFIELD_MAX_Y;
	}

	public void repair() {
		if (ship.repairCD == 0) {
			Inventory usableItem = null;
			// check we actually can repair hp< max hp
			if (Ship.SHIP_HEALTH < Ship.SHIP_MAXHEALTH) {
				for (int i = 0; i < Stardust3d.myInventory.size(); i++) {
					System.out.println("repair clicked ");
					Inventory currentItem = Stardust3d.myInventory.get(i);
					// check repair goop in hold
					if (currentItem.getSlot_id() == 0) {
						// If the current item is a repair pack
						if (currentItem.getSubcat().equals("repair packs")) {
							// if we havent found any repairpacks, or this
							// repairpack is the smallest found so far
							if (usableItem == null) {
								usableItem = currentItem;
							} else if (currentItem != null) {

								if (Integer.valueOf(currentItem.getEffect()) < Integer
										.valueOf(usableItem.getEffect())) {

									usableItem = currentItem;
								}
							}
						}

					}
				}
				// if we found no repair packs, tell the user
				if (usableItem == null) {
					Renderer.toastShown = false;
					Stardust3d.Error = Stardust3d.REPAIR_ERROR;
				} else {// there is some goop, repair ship
					// get latest from db
					Stardust3d.MyDataOp.get3dChar(Integer
							.valueOf(Stardust3d.currencharacteruid));
					Simulation.ship.update(0);
					// increase hitpoints
					Ship.SHIP_HEALTH = Ship.SHIP_HEALTH
							+ Integer.valueOf(usableItem.getEffect());
					if (Ship.SHIP_HEALTH > Ship.SHIP_MAXHEALTH) {
						Ship.SHIP_HEALTH = Ship.SHIP_MAXHEALTH;
					}
					Stardust3d.myCharacter.setHitpoints((int) Ship.SHIP_HEALTH);
					Stardust3d.MyDataOp.updateDBShip();
					// remove repair goop from inventory
					// put repair timer on cool down
					// reset repair timer
					ship.repairCD = 50;
					if (listener != null)
						listener.repair();
				}
			} else {
				if (Renderer.toastShown){
				Renderer.toastShown = false;
				Stardust3d.Error = Stardust3d.FULLHP_ERROR;
				}
			}
		}
	}

	public void missile() {
		if (ship.missileCD == 0) {
			Inventory usableItem = null;
			// check we actually can fire missile 
			for (int i = 0; i < Stardust3d.myInventory.size(); i++) {
				System.out.println("missile clicked ");
				Inventory currentItem = Stardust3d.myInventory.get(i);
				// check missile in hold
				if (currentItem.getSlot_id() == 0) {
					// If the current item is a missile
					if (currentItem.getSubcat().equals("missile")) {
						// if we haven't found any missiles, or this
						// missile is the biggest found so far
						if (usableItem == null) {
							usableItem = currentItem;
						} else if (currentItem != null) {

							if (Integer.valueOf(currentItem.getEffect()) > Integer
									.valueOf(usableItem.getEffect())) {

								usableItem = currentItem;
							}
						}
					}

				}
			}
			// if we found no missile, tell the user
			if (usableItem == null) {
				Stardust3d.Error = Stardust3d.MISSILE_ERROR;
				Renderer.toastShown = false;
			} else {// there is a missile, try launch it
				// check for target lock
				boolean lockFound = false;
				for (int index = 0; index < Stardust3d.actorsList.size(); index++) {
					com.digitale.connex.Actor target = Stardust3d.actorsList
							.get(index);
					if (target.locked) {
						lockFound = true;
						// damage target.

						// remove missile from inventory
						// put missile timer on cool down
						// reset missile timer
						if (Ship.position.dst(target.position) < 5000) {
							ship.missileCD = 50;
							if (listener != null)
								listener.missile();
							shipMissile = new Missile(Ship.position,
									Ship.yawAngle, Ship.pitchAngle, false);// create
																			// a
																			// new
																			// missile
							// reset gun timer
							shipMissile.target = target;
							shipMissile.damage = Float.valueOf(usableItem
									.getEffect());
							missiles.add(shipMissile);
						} else {
							Stardust3d.Error = Stardust3d.RANGE_ERROR;
							Renderer.toastShown = false;
						}
					}

				}
				if (!lockFound) {
					Stardust3d.Error = Stardust3d.MISSILE_LOCK_ERROR;
					Renderer.toastShown = false;
				}

			}
		}
	}

	public void shot() {
		if (ship.mainGunCD == 0) {
			if (!ship.isExploding) { // if a player?npc shot is not in flight
										// and
										// player?npc ship is not exploding

				shipShot = new Shot(Ship.position, Ship.yawAngle,
						Ship.pitchAngle, false);// create a new shot
				// reset gun timer
				ship.mainGunCD = 5;
				shots.add(shipShot);
				if (listener != null)
					listener.shot();
			}
		}
	}

	public void trail() {

		String shipname = Stardust3d.myCharacter.getShipname();
		// is the ship alive and moving?
		if (!ship.isExploding && Ship.SHIP_VELOCITY > 10) {
			// look thru' the shipdefs
			for (int i = 0; i < Stardust3d.shipdefs.size(); i++) {
				String currentShipdef = Stardust3d.shipdefs
						.get(i)
						.getModel()
						.substring(
								0,
								Stardust3d.shipdefs.get(i).getModel().length() - 4);
				// if the shipdef matches my ship
				if (currentShipdef.equals(shipname)) {
					// if the current jet is not unset (99999)

					if (Stardust3d.shipdefs.get(i).getJet1x() != 99999) {

						shipTrail = new Trail(
								new Vector3(
										Ship.position.x
												+ Stardust3d.shipdefs.get(i)
														.getJet1x(),
										Ship.position.y
												+ Stardust3d.shipdefs.get(i)
														.getJet1y(),
										Ship.position.z
												+ Stardust3d.shipdefs.get(i)
														.getJet1z()),
								Ship.yawAngle, Ship.pitchAngle, false);
						// create a new Trail
						trails.add(shipTrail);
					}

					if (Stardust3d.shipdefs.get(i).getJet2x() != 99999) {
						shipTrail = new Trail(
								new Vector3(
										Ship.position.x
												+ Stardust3d.shipdefs.get(i)
														.getJet2x(),
										Ship.position.y
												+ Stardust3d.shipdefs.get(i)
														.getJet2y(),
										Ship.position.z
												+ Stardust3d.shipdefs.get(i)
														.getJet2z()),
								Ship.yawAngle, Ship.pitchAngle, false);
						// create a new Trail
						trails.add(shipTrail);
					}

					if (Stardust3d.shipdefs.get(i).getJet3x() != 99999) {
						shipTrail = new Trail(
								new Vector3(
										Ship.position.x
												+ Stardust3d.shipdefs.get(i)
														.getJet3x(),
										Ship.position.y
												+ Stardust3d.shipdefs.get(i)
														.getJet3y(),
										Ship.position.z
												+ Stardust3d.shipdefs.get(i)
														.getJet3z()),
								Ship.yawAngle, Ship.pitchAngle, false);
						// create a new Trail
						trails.add(shipTrail);
					}

					if (Stardust3d.shipdefs.get(i).getJet1x() != 99999) {
						shipTrail = new Trail(
								new Vector3(
										Ship.position.x
												+ Stardust3d.shipdefs.get(i)
														.getJet4x(),
										Ship.position.y
												+ Stardust3d.shipdefs.get(i)
														.getJet4y(),
										Ship.position.z
												+ Stardust3d.shipdefs.get(i)
														.getJet4z()),
								Ship.yawAngle, Ship.pitchAngle, false);
						// create a new Trail
						trails.add(shipTrail);
					}

					if (Stardust3d.shipdefs.get(i).getJet5x() != 99999) {
						shipTrail = new Trail(
								new Vector3(
										Ship.position.x
												+ Stardust3d.shipdefs.get(i)
														.getJet5x(),
										Ship.position.y
												+ Stardust3d.shipdefs.get(i)
														.getJet5y(),
										Ship.position.z
												+ Stardust3d.shipdefs.get(i)
														.getJet5z()),
								Ship.yawAngle, Ship.pitchAngle, false);
						// create a new Trail
						trails.add(shipTrail);
					}
				}
			}
		}

	}

	public void updateDock() {

		// if(Stardust3d.DEBUG) System.out.println("docktick "+docktick);
		if (docktick >= 1 && docktick <= 4) {
			SoundManager.playDockRequest();

			docktick = 5;
		}
		if (docktick >= 40 && docktick <= 44) {
			SoundManager.playDockOK();
			docktick = 45;
		}
		if (docktick >= 60) {
			Ship.STATUS = 0;
			Ship.docking = false;
			GameLoop.cameraHorizAngle = 0;
			GameLoop.cameraVertAngle = 10;
			SoundManager.stopEngine();
			docktick = 0;
			// update db
			Stardust3d.MyDataOp.dockship(Stardust3d.myCharacter.getUid());
		}

	}

	public void updateUndock() {

		if (Stardust3d.DEEPDEBUG)
			System.out.println("docktick " + docktick);
		if (docktick >= 1 && docktick <= 4) {
			SoundManager.playUndock();

			docktick = 5;
		}

		if (docktick >= 40) {
			Ship.STATUS = 1;

			Ship.undocking = false;
			Ship.SHIP_VELOCITY = 0;
			// undock ship to random co-ords in station radius
			float rand = (float) (Math.random() * Station.STATION_RADIUS);
			Ship.position.x = rand;
			rand = (float) (Math.random() * Station.STATION_RADIUS);
			Ship.position.y = rand;
			rand = (float) (Math.random() * Station.STATION_RADIUS);
			Ship.position.z = rand;
			populateDust();
			docktick = 0;
			GameLoop.cameraHorizAngle = 0;
			GameLoop.cameraVertAngle = 10;
			enginePitch = 0.5f + (Ship.SHIP_VELOCITY - 0) * (2.0f - 0.5f)
					/ (Ship.SHIP_MAXVELOCITY - 0);
			SoundManager.playEngine(enginePitch);
			Stardust3d.MyDataOp.undockship(Stardust3d.myCharacter.getUid());
			ship.update(0);
			Stardust3d.MyDataOp.getLocalPlayers(Stardust3d.myCharacter.getX(),
					Stardust3d.myCharacter.getY(),
					Stardust3d.myCharacter.getZ(), true);
			Stardust3d.MyDataOp.updateDBShip();
		}

	}

	public void swansongExplosion(float sysx, float sysy, float sysz) {
		if (listener != null)
			listener.boom();
		Vector3 position = new Vector3(sysx, sysy, sysz);
		explosions.add(new Explosion(position, Explosion.SWANSONG));

	}

	public static void updateActorFontCache() {
		String currentName;
		// Renderer.actorNames.clear();
		// for every actor
		for (int i = 0; i < Stardust3d.actorsList.size(); i++) {
			// is it renderable?
			Actor actor = Stardust3d.actorsList.get(i);
			if (actor != null && !(actor.shipname.equals("dead"))
					&& actor.getHitpoints() > 0) {
				// only do if actor is in space
				currentName = actor.get_firstname() + "\n"
						+ actor.get_surname();
				if (actor.visible) {
					actor.cache = new BitmapFontCache(Renderer.fontsmall);
					actor.cache.setText(actor.distanceString, 0, 0);
					boolean found = false;

					for (int j = 0; j < Renderer.actorNames.size(); j++) {
						ActorNameCache actorName = Renderer.actorNames.get(j);
						if ((actorName.getName().equals(currentName))) {
							found = true;
						} else {
						}
					}
					if (found == false) {
						BitmapFontCache tempBMFC = new BitmapFontCache(
								Renderer.fontsmall);
						tempBMFC.setMultiLineText(actor.get_firstname() + "\n"
								+ actor.get_surname(), 0, 0);
						tempBMFC.setColor(0, 1, 0, 1);
						if (actor.getShipname().equals("aiscout")) {
							tempBMFC.setColor(1, 0, 0, 1);
						} else if (actor.getShipname()
								.equals("shielddisruptor")) {
							tempBMFC.setColor(1, .75f, 0, 1);
						}
						Renderer.actorNames.add(new ActorNameCache(tempBMFC,
								currentName));

					}
				}
			}
		}
	}
	
	
	public static void Collide(float frameDuration, Ship object1, Actor object2)
	{
	    // Calculate the difference between the two objects.
	    Vector3 difference = object1.position.sub(object2.position);
	    float distanceAtFrameEnd = difference.len();
	    frameDuration=frameDuration*100000;
	    // Calculate the distance that a collision would occur at.
	    float collisionDistance = (object1.SHIP_RADIUS / 2f) + (object2.INVADER_RADIUS / 2f);

	    // Check of the objects are closer that the collision distance.
	    if (distanceAtFrameEnd < collisionDistance)
	    {
	        // Move both objects back to the exact point of collision.
	        float millisecondsAfterCollision = MoveBackToCollisionPoint(frameDuration, object1, object2, distanceAtFrameEnd, collisionDistance);
	        if (Stardust3d.DEBUG)
				System.out.println(LOG_TAG + "milli after coll: " + millisecondsAfterCollision);
	        // Calculate the normal of the collision plane.
	        Vector3 normalPlane = difference;
	        normalPlane.nor();

	        // Calculate the collision plane.
	        Vector3 collisionPlane = new Vector3(-normalPlane.x, normalPlane.y,normalPlane.z);

	        // Calculate prior velocities relative the the collision plane and normal.
	        float n_vel1 = (normalPlane.dot( object1.heading));
	        float c_vel1 =(collisionPlane.dot(object1.heading));
	        float n_vel2 = (normalPlane.dot( object2.heading));
	        float c_vel2 = (collisionPlane.dot(object2.heading));

	        // Calculate the scaler velocities of each object after the collision.
	        float n_vel1_after = ((n_vel1 * (object1.mass - object2.mass)) + (2 * object2.mass * n_vel2)) / (object2.mass + object1.mass);
	        float n_vel2_after = ((n_vel2 * (object2.mass - object1.mass)) + (2 * object1.mass * n_vel1)) / (object2.mass + object1.mass);
	        //float velObject2Tangent_After = c_vel2;
	        //float velObject1Tangent_After = c_vel1;

	        // Convert the scalers to vectors by multiplying by the normalised plane vectors.
	        Vector3 vec_n_vel2_after =  normalPlane.mul(n_vel2_after );
	        Vector3 vec_c_vel2 =  collisionPlane.mul(c_vel2);
	        Vector3 vec_n_vel1_after =  normalPlane.mul(n_vel1_after);
	        Vector3 vec_c_vel1 =  collisionPlane.mul(c_vel1);

	        // Combine the vectors back into a single vector in world space.
	        Vector3 vel1_after = vec_n_vel1_after.add(vec_c_vel1);
	        Vector3 vel2_after = vec_n_vel2_after.add(vec_c_vel2);

	        // Reapply the move-back from before the collision (using the post collision velocity)
	        Vector3 object1AdjustedPositionAfterCollision = object1.position.add(vel1_after.mul( millisecondsAfterCollision));
	        Vector3 object2AdjustedPositionAfterCollision = object2.position.add(vel2_after.mul(millisecondsAfterCollision));

	        // Set the objects new positions and velocities.
	        object1.position=(object1AdjustedPositionAfterCollision);
	        object1.heading= vel1_after;
	        object2.position=object2AdjustedPositionAfterCollision;
	        object2.heading= vel2_after;
	    }
	}

	private static float MoveBackToCollisionPoint(float frameDuration, Ship object1, Actor object2, float distanceAtFrameEnd, float collisionDistance)
	{
	    // Calculate the position of each object at the start of the frame.
	    float object1PosAtFrameStart_X = (float)(object1.position.x - object1.heading.x * frameDuration);
	    float object1PosAtFrameStart_Y = (float)(object1.position.y - object1.heading.y * frameDuration);
	    float object1PosAtFrameStart_Z = (float)(object1.position.z - object1.heading.z * frameDuration);
	    Vector3 object1PosAtFrameStart = new Vector3(object1PosAtFrameStart_X, object1PosAtFrameStart_Y, object1PosAtFrameStart_Z);

	    float object2PosAtFrameStart_X = (float)(object2.position.x - object2.heading.x * frameDuration);
	    float object2PosAtFrameStart_Y = (float)(object2.position.y - object2.heading.y * frameDuration);
	    float object2PosAtFrameStart_Z = (float)(object2.position.z - object2.heading.z * frameDuration);
	    Vector3 object2PosAtFrameStart = new Vector3(object2PosAtFrameStart_X, object2PosAtFrameStart_Y, object2PosAtFrameStart_Z);

	    // Calculate the distance between the objects at the start of the frame.
	    Vector3 differenceAtFrameStart = object2PosAtFrameStart.sub(object1PosAtFrameStart);
	    float distanceAtFrameStart = differenceAtFrameStart.len();

	    // Calculate the total change in distance during the frame, and the required change to reach the collision.
	    float distanceTotalDelta = distanceAtFrameEnd - distanceAtFrameStart;
	    float distanceDeltaToCollision = collisionDistance - distanceAtFrameStart;

	    // Calculate the percentage change to the collision and after the collision.
	    float percentageDeltaToCollision = distanceDeltaToCollision / distanceTotalDelta;
	    float percentageDeltaAfterCollision = 1 - percentageDeltaToCollision;

	    // Calculte the time before and after the collision in the frame.
	    double millisecondsToCollision = frameDuration * percentageDeltaToCollision;
	    float millisecondsAfterCollision = (float)(frameDuration * percentageDeltaAfterCollision);

	    // Calculate and move the objects to their positions at the point of collision.
	    float object1PosAtCollision_X = (float)(object1PosAtFrameStart_X + object1.heading.x * millisecondsToCollision);
	    float object1PosAtCollision_Y = (float)(object1PosAtFrameStart_Y + object1.heading.y * millisecondsToCollision);
	    float object1PosAtCollision_Z = (float)(object1PosAtFrameStart_Z + object1.heading.z * millisecondsToCollision);
	    Vector3 object1PosAtCollision = new Vector3(object1PosAtCollision_X, object1PosAtCollision_Y, object1PosAtCollision_Z);
	    object1.position=(object1PosAtCollision);

	    float object2PosAtCollision_X = (float)(object2PosAtFrameStart_X + object2.heading.x * millisecondsToCollision);
	    float object2PosAtCollision_Y = (float)(object2PosAtFrameStart_Y + object2.heading.y * millisecondsToCollision);
	    float object2PosAtCollision_Z = (float)(object2PosAtFrameStart_Y + object2.heading.z * millisecondsToCollision);
	    Vector3 object2PosAtCollision = new Vector3(object2PosAtCollision_X, object2PosAtCollision_Y, object2PosAtCollision_Z);
	    object2.position=(object2PosAtCollision);

	    return millisecondsAfterCollision;
	}
}
