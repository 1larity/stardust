/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package connex;

public class News  {
	   private String   _title;
	   private String  _text;
	   private String _date;
	
	   
	   public News(  ) {
	   }
	   
	   public News(String title, 
	              String  text,
	              String date)
	              {
	      set_title(title);
	      set_text(text);
	      set_date(date);
	   }

	public void set_title(String _title) {
		this._title = _title;
	}

	public String get_title() {
		return _title;
	}

	public void set_text(String _text) {
		this._text = _text;
	}

	public String get_text() {
		return _text;
	}

	public void set_date(String _date) {
		this._date = _date;
	}

	public String get_date() {
		return _date;
	}

	}