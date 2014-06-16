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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;

import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageTask;

public class SimpleSmartImageLoader {

	private static final int LOADING_THREADS = 4;
	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(LOADING_THREADS);

	 private SmartImageTask currentTask;
	
	public void loadImage(Context context,final SmartImage image, final SmartImageTask.OnCompleteListener completeListener) {

        // Cancel any existing tasks for this image view
        if(currentTask != null) {
            currentTask.cancel();
            currentTask = null;
        }

        // Set up the new task
        currentTask = new SmartImageTask(context, image);
        currentTask.setOnCompleteHandler(new SmartImageTask.OnCompleteHandler() {
        	@Override
        	public void onComplete(Bitmap bitmap) {
        		// TODO Auto-generated method stub
        		if(completeListener != null){
                  completeListener.onComplete(bitmap);
              }
        	}
        });
        threadPool.execute(currentTask);
        
	}
	public static void cancelAllTasks() {
		threadPool.shutdownNow();
		threadPool = Executors.newFixedThreadPool(LOADING_THREADS);
	}
}
