package com.geekmk.newsapp.utils

import com.geekmk.newsapp.data.model.NewsArticle
import com.geekmk.newsapp.data.model.TopNewsArticlesAPIResponse


class MockTestUtil {
  companion object {
    fun mockArticleList() = mutableListOf(getArticle())

    fun getArticle() = NewsArticle("Google quietly shelves custom Pixel phone cases - Engadget",
      "Google has dropped its personalized cases for Pixel phones.",
      "https://www.engadget.com/2019/05/19/google-drops-pixel-my-case/",
      "https://o.aolcdn.com/images/dims?thumbnail=1200%2C630&quality=80&image_uri=https%3A%2F%2Fo.aolcdn.com%2Fimages%2Fdims%3Fcrop%3D1600%252C1067%252C0%252C0%26quality%3D85%26format%3Djpg%26resize%3D1600%252C1067%26image_uri%3Dhttps%253A%252F%252Fs.yimg.com%252Fos%252Fcreatr-uploaded-images%252F2019-05%252F72726850-7a7a-11e9-9fbf-c21d0bb6ee24%26client%3Da1acac3e1b3290917d92%26signature%3D0f5c55fda421dae1f7da02882a17e889a1da1684&client=amp-blogside-v2&signature=97457d492d5c9b68c0f5d7c42db9ad702f4cfdd8",
      "The company introduced Live Cases back in early 2016 for the Nexus 5X, 6 and 6P, using a clever NFC feature to provide wallpaper that matched the shell on your phone. Google dropped NFC when the Pixel 2 rolled around, and switched to its photo-based My Cases … [+418 chars]",
      "2019-05-20T03:51:25Z")

    fun mockAPIResult() = TopNewsArticlesAPIResponse("ok",1, mockArticleList())

  }
}
