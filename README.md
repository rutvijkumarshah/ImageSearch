ImageCandy
===========
ImageCandy is an android app to search images from various search engines, as of now google is only provider supported.
Application's backend supports easy to hook any other search engine/image provider.

Time spent: 40 hours spent in total.

Completed user stories:

 * [x] Required: User can enter a search query that will display a grid of image results from the Google Image API.
 * [x] Required: User can click on "settings" which allows selection of advanced search options to filter results
 * [x] Required: User can configure advanced search filters such as:
          Size (small, medium, large, extra-large)
          Color filter (black, blue, brown, gray, green, etc...)
          Type (faces, photo, clip art, line art)
          Site (espn.com)
          [ Additional ] File Type ( jpg,bmp,gif,png )

* [x] Required: Subsequent searches will have any filters applied to the search results
* [x] Required: User can tap on any image in results to see the image full-screen
* [x] Required: User can scroll down “infinitely” to continue loading more image results (up to 8 pages)
 

The following advanced user stories are optional and completed:

* [x] Advanced: Robust error handling, check if internet is available, handle error cases, network failures
* [x] Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
* [x] Advanced: User can share an image to their friends or email it to themselves
* [x] Advanced: Replace Filter Settings Activity with a lightweight modal overlay
* [x] Advanced: Improve the user interface and experiment with image assets and/or styling and coloring

* [x] Bonus: Use the StaggeredGridView to display visually interesting image results
* [x] Bonus: User can zoom or pan images displayed in full-screen detail view
