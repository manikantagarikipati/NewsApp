package com.geekmk.newsapp.db

import androidx.test.runner.AndroidJUnit4
import com.geekmk.newsapp.MockTestUtil
import com.geekmk.newsapp.data.model.NewsArticle
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NewsArticleDaoTest : DbTest() {

  @Test
  fun insertAndReadTest() {
    val articleList = ArrayList<NewsArticle>()
    val article = MockTestUtil.getArticle()
    articleList.add(article)

    db.newsArticlesDao().insertAllNewsArticles(articleList)
    db.newsArticlesDao().queryNewsArticles().toObservable().test().assertNoErrors().assertValue{
     !it.isNullOrEmpty() && it[0].title == "Google quietly shelves custom Pixel phone cases - Engadget"
    }
  }

}
