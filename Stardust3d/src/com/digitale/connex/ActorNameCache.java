/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.connex;

import com.badlogic.gdx.graphics.g2d.BitmapFontCache;

public class ActorNameCache  {
	  
	public  BitmapFontCache cache;
	   private String name;
	   
	
	   
	   public ActorNameCache(  ) {
	   }
	   
	   public ActorNameCache(BitmapFontCache cache, 
	              String  name)
	              {
	      setCache(cache);
	      setName(name);
	   }

	/**
	 * @return the cache
	 */
	public BitmapFontCache getCache() {
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(BitmapFontCache cache) {
		this.cache = cache;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	}