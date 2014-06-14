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

package com.github.rutvijkumar.imagesearch.api;

import com.github.rutvijkumar.imagesearch.api.google.GoogleImageProvider;

public class ImageProviders {

	public static final int GOOGLE_IMAGE_PROVIDER=1; 
	private static GoogleImageProvider googleProvider=new GoogleImageProvider(new SearchFilter());
	
	
	public static ImageProvider getProvider(int providerId,SearchFilter filter) {
		ImageProvider provider=null;
		if(providerId == GOOGLE_IMAGE_PROVIDER) {
			provider= googleProvider;
			provider.updateSearchFilter(filter);
		}
		return provider;
	}
	
	public static ImageProvider getDefaultImageProvider(SearchFilter filter) {
		googleProvider.updateSearchFilter(filter);
		return googleProvider;
	}
}
