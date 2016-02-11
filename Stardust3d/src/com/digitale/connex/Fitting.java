/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */package com.digitale.connex;

import java.util.ArrayList;
import java.util.List;

public class Fitting  {
	
	public static List<Integer> defList = new ArrayList <Integer>();
	public static List<Integer> offList = new ArrayList <Integer>();
	public static List<Integer> augList = new ArrayList <Integer>();
	  
	public Fitting(){
		for (int i=0;i<5;i++){
			defList.add(0);
			offList.add(0);
			augList.add(0);
		}
	}

	}