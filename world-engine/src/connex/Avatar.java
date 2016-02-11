/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package connex;



public class Avatar
{
	int uid;
	private String firstname;
	private String surname;
	private int stamina;
	private int intelligence;
	private int social;
	private int dexterity;
	private int leadership;
	private int recuperation;
	private int exp;
	private int x;
	private int y;
	private int z;
	private String system;
	private int shipid;
	private String shipname;
	private int hitpoints;
	private int shield;
	private int power;
	private int cpu;
	private String shipicon;
	private int sysx;
	private int sysy;
	private int sysz;
	private String status;
	public boolean isItemSelected;
	
	public Avatar(int uid,String firstname, String surname, int stamina,
	int intelligence,
	 int social,
	 int dexterity,
	 int leadership,
	 int recuperation,
	int exp,
	int x,
	int y,
	int z,
	String system,
	int shipid,
	String shipname,
	 int hitpoints,
	 int shield,
	 int power,
	 int cpu,
	 String shipicon,
	 int sysx,
	 int sysy,
	 int sysz,
	 String status) {
		this.uid=uid;
		this.firstname = firstname;
		this.surname=surname;
		this.stamina=stamina;
		this.intelligence=intelligence;
		this.social=social;
		this.dexterity=dexterity;
		this.leadership=leadership;
		this.recuperation=recuperation;
		this.exp=exp;
		this.x=x;
		this.y=y;
		this.z=z;
		this.system=system;
		this.shipname=shipname;
		this.hitpoints=hitpoints;
		this.shield=shield;
		this.power=power;
		this.cpu=cpu;
		this.isItemSelected = false;
		this.shipicon=shipicon;
		this.shipid=shipid;
		this.setSysx(sysx);
		this.setSysy(sysy);
		this.setSysz(sysz);
		this.setStatus(status);
	}
	
	public Avatar() {
		// TODO Auto-generated constructor stub
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getSurname() {
		return surname;
	}
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	public int getStamina() {
		return stamina;
	}
	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}
	public int getIntelligence() {
		return intelligence;
	}
	public void setSocial(int social) {
		this.social = social;
	}
	public int getSocial() {
		return social;
	}
	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}
	public int getDexterity() {
		return dexterity;
	}
	public void setLeadership(int leadership) {
		this.leadership = leadership;
	}
	public int getLeadership() {
		return leadership;
	}
	public void setRecuperation(int recuperation) {
		this.recuperation = recuperation;
	}
	public int getRecuperation() {
		return recuperation;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getExp() {
		return exp;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getZ() {
		return z;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSystem() {
		return system;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getUid() {
		return uid;
	}

	public void setShipname(String shipname) {
		this.shipname = shipname;
	}

	public String getShipname() {
		return shipname;
	}

	public void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}

	public int getHitpoints() {
		return hitpoints;
	}

	public void setShield(int shield) {
		this.shield = shield;
	}

	public int getShield() {
		return shield;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getPower() {
		return power;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}
 
	public int getCpu() {
		return cpu;
	}

	public void setShipicon(String shipicon) {
		this.shipicon = shipicon;
	}

	public String getShipicon() {
		return shipicon;
	}

	public void setShipid(int shipid) {
		this.shipid = shipid;
	}

	public int getShipid() {
		return shipid;
	}

	public int getSysx() {
		return sysx;
	}

	public void setSysx(int sysx) {
		this.sysx = sysx;
	}

	public int getSysy() {
		return sysy;
	}

	public void setSysy(int sysy) {
		this.sysy = sysy;
	}

	public int getSysz() {
		return sysz;
	}

	public void setSysz(int sysz) {
		this.sysz = sysz;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	




		
}