/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */package com.digitale.connex;



public class celestial
{
	
	private int x;
	private int y;
	private int z;
	private int sysx;
	private int sysy;
	private int sysz;
	private int modelScale;
	private int activationRadius;
	private String name;
	private String structname;

	
	public celestial(int uid,String firstname, String surname, int stamina,
	int x,
	 int y,
	 int z,
	 int sysx,
	 int sysy,
	int sysz,
	int modelScale,
	int activationRadius,
	String name,
	String structname
	) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setSysx(sysx);
		this.setSysy(sysy);
		this.setSysz(sysz);
		this.setModelScale(modelScale);
		this.setActivationRadius(activationRadius);
		this.setName(name);
		this.setStructname(structname);
	}
	
	public celestial() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param z the z to set
	 */
	public void setZ(int z) {
		this.z = z;
	}

	/**
	 * @return the z
	 */
	public int getZ() {
		return z;
	}

	/**
	 * @param sysx the sysx to set
	 */
	public void setSysx(int sysx) {
		this.sysx = sysx;
	}

	/**
	 * @return the sysx
	 */
	public int getSysx() {
		return sysx;
	}

	/**
	 * @param sysy the sysy to set
	 */
	public void setSysy(int sysy) {
		this.sysy = sysy;
	}

	/**
	 * @return the sysy
	 */
	public int getSysy() {
		return sysy;
	}

	/**
	 * @param sysz the sysz to set
	 */
	public void setSysz(int sysz) {
		this.sysz = sysz;
	}

	/**
	 * @return the sysz
	 */
	public int getSysz() {
		return sysz;
	}

	/**
	 * @param modelScale the modelScale to set
	 */
	public void setModelScale(int modelScale) {
		this.modelScale = modelScale;
	}

	/**
	 * @return the modelScale
	 */
	public int getModelScale() {
		return modelScale;
	}

	/**
	 * @param activationRadius the activationRadius to set
	 */
	public void setActivationRadius(int activationRadius) {
		this.activationRadius = activationRadius;
	}

	/**
	 * @return the activationRadius
	 */
	public int getActivationRadius() {
		return activationRadius;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param structname the structname to set
	 */
	public void setStructname(String structname) {
		this.structname = structname;
	}

	/**
	 * @return the structname
	 */
	public String getStructname() {
		return structname;
	}

}