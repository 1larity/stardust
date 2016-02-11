/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.connex;


public class Agent
{
	public String name;
	public boolean type;
	public String race;



	public Agent(String name, boolean type, String race)
		{
			this.name = name;
			this.type = type;
			this.race= race;

		}

	@Override
	public String toString()
		{
			return this.name;
		}
}