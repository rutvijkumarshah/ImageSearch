/***

The MIT License (MIT)
Copyright © 2014 Rutvijkumar Shah
 
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the “Software”), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
 
The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.
 
THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

***/

package com.github.rutvijkumar.imagesearch.api.types;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public enum ImageColor {

	BLACK,BLUE,BROWN,GRAY,GREEN,ORANGE,PINK,PURPLE,RED,TEAL,WHITE,YELLOW;
	
	/***
	 * Using map over reflection based valueOf Implementation.
	 */
	private static Map<String,ImageColor> valMap=new LinkedHashMap<String,ImageColor>();
	
	static {
		
		valMap.put("ANY",null);
		valMap.put("BLACK",BLACK);
		valMap.put("BLUE",BLUE);
		valMap.put("BROWN",BROWN);
		valMap.put("GRAY",GRAY);
		valMap.put("GREEN",GREEN);
		valMap.put("ORANGE",ORANGE);
		valMap.put("PINK",PINK);
		valMap.put("PURPLE",PURPLE);
		valMap.put("RED",RED);
		valMap.put("TEAL",TEAL);
		valMap.put("WHITE",WHITE);
		valMap.put("YELLOW",YELLOW);
		
	}
	
	public static ImageColor getValueOf(String val) {
		ImageColor color=null;
		if(val!=null) {
			color= valMap.get(val.toUpperCase());
		}
		return color;
		
	}
	
	public static int getPosition(String val) {
		int position=-1;
		if(val!=null) {
			position=new ArrayList<String>(getValues()).indexOf(val.toUpperCase());
		}
		return position;
	}
	public static Set<String> getValues() {
		return valMap.keySet();
	}
	

}
