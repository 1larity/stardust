/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package connex;



public class Inventory
{
	private int itemUid;
	private int slot_id;
	private int inventoryid;
	private String category;
	private String subcat;
	private String item;
	private int mass;
	private int stacks;
	private String description;
	private int contraband;
	private int recipe;
	private int capacity;
	private int level;
	private String icon;
	private int quality;
	
	
	public Inventory(
			int itemUid,
			 int slot_id,
			 int inventoryid,
			 String category,
			 String subcat,
			 String item,
			 int mass,
			 int stacks,
			 String description,
			 int contraband,
			 int recipe,
			 int capacity,
			 int level,
			 String icon,
			 int quality) {
		this.itemUid=itemUid;
		this.slot_id=slot_id;
		this.inventoryid=inventoryid;
		this.category=category;
		this.subcat=subcat;
		this.item=item;
		this.mass=mass;
		this.stacks=stacks;
		this.description=description;
		this.contraband=contraband;
		this.recipe=recipe;
		this.capacity=capacity;
		this.level=level;
		this.icon=icon;
		this.quality=quality;
			}
	
	public Inventory() {
		// TODO Auto-generated constructor stub
	}

	public void setitemUid(int itemUid) {
		this.itemUid = itemUid;
	}

	public int getitemUid() {
		return itemUid;
	}
	public void setSlot_id(int slot_id) {
		this.slot_id = slot_id;
	}

	public int getSlot_id() {
		return slot_id;
	}

	public void setInventoryid(int inventoryid) {
		this.inventoryid = inventoryid;
	}

	public int getInventoryid() {
		return inventoryid;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setSubcat(String subcat) {
		this.subcat = subcat;
	}

	public String getSubcat() {
		return subcat;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}

	public int getMass() {
		return mass;
	}

	public void setStacks(int stacks) {
		this.stacks = stacks;
	}

	public int getStacks() {
		return stacks;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setContraband(int contraband) {
		this.contraband = contraband;
	}

	public int getContraband() {
		return contraband;
	}

	public void setRecipe(int recipe) {
		this.recipe = recipe;
	}

	public int getRecipe() {
		return recipe;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}


	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getQuality() {
		return quality;
	}

	
	}

	




		
