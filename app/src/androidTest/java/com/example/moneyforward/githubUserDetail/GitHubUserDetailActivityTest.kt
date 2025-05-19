package com.example.moneyforward.githubUserDetail

import androidx.test.ext.junit.runners.AndroidJUnit4
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.example.moneyforward.R
import com.example.moneyforward.ui.RepoWebViewActivity
import com.example.moneyforward.ui.githubUserDetail.GitHubUserDetailActivity
import com.example.moneyforward.ui.githubUserList.GitHubUserListActivity.Companion.TAG
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GitHubUserDetailActivityTest {

    @Before
    fun setup() {
        init()
    }

    @After
    fun tearDown() {
        release()
    }

    @Test
    fun testUserInfoDisplayedCorrectly() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(context, GitHubUserDetailActivity::class.java).apply {
            putExtra(TAG, "octocat") // Provide test GitHub user login
        }

        ActivityScenario.launch<GitHubUserDetailActivity>(intent)

        Thread.sleep(2000)

        onView(withText("Personal Info")).check(matches(isDisplayed()))

        onView(withId(R.id.tv_login_user)).check(matches(withText("octocat")))

        onView(withId(R.id.img_photo)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_following_value)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_followers_value)).check(matches(isDisplayed()))
    }

    @Test
    fun testRepoClickOpensWebView() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(context, GitHubUserDetailActivity::class.java).apply {
            putExtra(TAG, "octocat")
        }

       ActivityScenario.launch<GitHubUserDetailActivity>(intent)

        Thread.sleep(3000)

        onView(withId(R.id.recyclerViewRepos))
            .perform(
                androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition<androidx.recyclerview.widget.RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        intended(hasComponent(RepoWebViewActivity::class.java.name)) // Validate intent
    }
}
