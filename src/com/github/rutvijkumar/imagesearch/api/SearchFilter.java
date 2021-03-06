/***

The MIT License (MIT)
Copyright � 2014 Rutvijkumar Shah
 
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the �Software�), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
 
The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.
 
THE SOFTWARE IS PROVIDED �AS IS�, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

***/

package com.github.rutvijkumar.imagesearch.api;

import java.io.Serializable;

import com.github.rutvijkumar.imagesearch.api.types.ImageColor;
import com.github.rutvijkumar.imagesearch.api.types.ImageFileType;
import com.github.rutvijkumar.imagesearch.api.types.ImageSize;
import com.github.rutvijkumar.imagesearch.api.types.ImageType;

public class SearchFilter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ImageSize imageSize;
	private ImageColor imageColor;
	private ImageType imageType;
	private ImageFileType imageFileType;
	private String site;
	
	public ImageSize getImageSize() {
		return imageSize;
	}
	public ImageColor getImageColor() {
		return imageColor;
	}
	public ImageType getImageType() {
		return imageType;
	}
	public ImageFileType getImageFileType() {
		return imageFileType;
	}
	public String getSite() {
		return site;
	}
	
	public SearchFilter imageSize(ImageSize imageSize) {
		this.imageSize = imageSize;
		return this;
	}
	public SearchFilter imageColor(ImageColor imageColor) {
		this.imageColor = imageColor;
		return this;
	}
	public SearchFilter imageType(ImageType imageType) {
		this.imageType = imageType;
		return this;
	}
	public SearchFilter imageFileType(ImageFileType imageFileType) {
		this.imageFileType = imageFileType;
		return this;
	}
	public SearchFilter site(String siteFilter) {
		this.site = siteFilter;
		return this;
	}
	
}
