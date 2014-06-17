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

package com.github.rutvijkumar.imagesearch.util;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {

	public static Boolean isNetworkAvailable(Activity activity) {
		ConnectivityManager connectivityManager 
        = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		boolean isNetworkAvailable=true;
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    isNetworkAvailable=( activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting());
	    return isNetworkAvailable;
	}
	
	public static String getMD5Sum(ByteArrayOutputStream byteOS) {
		String md5Sum = null;
		try {
			byte[] byteArray = byteOS.toByteArray();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteArray, 0, byteArray.length);
			md5Sum = new BigInteger(1, md5.digest()).toString(16);
			while (md5Sum.length() < 32) {
				md5Sum = "0" + md5Sum;
			}
		} catch (NoSuchAlgorithmException e) {

		}
		return md5Sum;
	}
	
	public static String getFileSizeString(long fileSize) {
		 StringBuilder sb=new StringBuilder();
		 String unit="KB";
		 long size=(fileSize / 1024);
		 if(size > 1024) { 
			 //more than 1024 KB
			 size= (size /1024 );
			 if(size > 1024) {
				 unit="GB";
			 }
			 unit="MB";
		 }
		 sb.append(size);
		 sb.append(" ");
		 sb.append(unit);
		 
		 return sb.toString();
	 
		
	}
}
