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
        init() // Initialize Espresso Intents
    }

    @After
    fun tearDown() {
        release() // Clean up Intents
    }

    @Test
    fun testUserInfoDisplayedCorrectly() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(context, GitHubUserDetailActivity::class.java).apply {
            putExtra(TAG, "octocat") // Provide test GitHub user login
        }

        ActivityScenario.launch<GitHubUserDetailActivity>(intent)

        // Wait a bit for data to load (in a real test use IdlingResource or Espresso-contrib for recycler)
        Thread.sleep(2000)

        // Check toolbar title
        onView(withText("Personal Info")).check(matches(isDisplayed()))

        // Check login is displayed
        onView(withId(R.id.tv_login_user)).check(matches(withText("octocat")))

        // Check image is loaded (just test visibility)
        onView(withId(R.id.img_photo)).check(matches(isDisplayed()))

        // Check following/followers views are visible
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

        // Wait for repos to load
        Thread.sleep(3000)

        // Simulate clicking on first repo item
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
