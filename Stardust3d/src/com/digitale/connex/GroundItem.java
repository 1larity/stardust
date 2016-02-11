/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.connex;

public class GroundItem  {
	   private String   _ground;
	   private boolean  _issea;
	   private boolean _isviable;
	   private String      _sound;
	   private String _texture;
	   
	   public GroundItem(  ) {
	   }
	   
	   public GroundItem(String ground, 
	                      boolean issea,
	                      boolean isviable,
	                      String sound,
	                      String texture) {
	      set_ground(ground);
	      set_issea(issea);
	      set_isviable(isviable);
	      set_sound(sound);
	    set_texture(texture);  
	   }

	public void set_ground(String _ground) {
		this._ground = _ground;
	}

	public String get_ground() {
		return _ground;
	}

	public void set_issea(boolean _issea) {
		this._issea = _issea;
	}

	public boolean is_issea() {
		return _issea;
	}

	public void set_isviable(boolean _isviable) {
		this._isviable = _isviable;
	}

	public boolean is_isviable() {
		return _isviable;
	}

	public void set_sound(String _sound) {
		this._sound = _sound;
	}

	public String get_sound() {
		return _sound;
	}

	public void set_texture(String _texture) {
		this._texture = _texture;
	}

	public String get_texture() {
		return _texture;
	}
	   
	   
	   
	}