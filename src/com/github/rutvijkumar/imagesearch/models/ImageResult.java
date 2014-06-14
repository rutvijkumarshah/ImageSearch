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

package com.github.rutvijkumar.imagesearch.models;
/***
 * 
 * Image represents image from search result.
 * 
 * @author Rutvijkumar Shah
 *
 */
public class ImageResult {
	
	private String imageUrl ;
	private String thumbUrl;
	private String title;
	
	public ImageResult() {
		
	}
	public ImageResult(String imageUrl, String thumbUrl, String title) {
		super();
		this.imageUrl = imageUrl;
		this.thumbUrl = thumbUrl;
		this.title = title;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Image [imageUrl=" + imageUrl + ", thumbUrl=" + thumbUrl
				+ ", title=" + title + ", getImageUrl()=" + getImageUrl()
				+ ", getThumbUrl()=" + getThumbUrl() + ", getTitle()="
				+ getTitle() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result
				+ ((thumbUrl == null) ? 0 : thumbUrl.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageResult other = (ImageResult) obj;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (thumbUrl == null) {
			if (other.thumbUrl != null)
				return false;
		} else if (!thumbUrl.equals(other.thumbUrl))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
	
}
