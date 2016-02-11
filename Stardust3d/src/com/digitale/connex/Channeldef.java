/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.connex;

import java.sql.Timestamp;



public class Channeldef
{
	private int uid;
	private String channelname;
	private int channeltype;

	
	public Channeldef(
			int uid,
			String channelname, 
			int channeltype,
	Timestamp timestamp) {
		this.setUid(uid);
		this.setChannelname(channelname);
		this.setChanneltype(channeltype);
		
			}
	


	public Channeldef() {
		// TODO Auto-generated constructor stub
	}



	public int getUid() {
		return uid;
	}



	public void setUid(int uid) {
		this.uid = uid;
	}



	public String getChannelname() {
		return channelname;
	}



	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}



	public int getChanneltype() {
		return channeltype;
	}



	public void setChanneltype(int channeltype) {
		this.channeltype = channeltype;
	}



	
}