package com.example.moneyforward.githubUserList

import android.content.Intent
import android.provider.Settings
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.init
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.moneyforward.R
import com.example.moneyforward.ui.githubUserDetail.GitHubUserDetailActivity
import com.example.moneyforward.ui.githubUserList.GitHubUserListActivity
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Assume
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GitHubUserListActivityTest {
    @Before
    fun setUp() {
        init() // required!
    }
    @Before
    fun assertAnimationsDisabled() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val resolver = context.contentResolver

        val window = Settings.Global.getFloat(resolver, Settings.Global.WINDOW_ANIMATION_SCALE, 1f)
        val transition = Settings.Global.getFloat(resolver, Settings.Global.TRANSITION_ANIMATION_SCALE, 1f)
        val animator = Settings.Global.getFloat(resolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f)

        if (window != 0f || transition != 0f || animator != 0f) {
            println("⚠️ System animations are enabled. Skipping test to prevent failures.")
            Assume.assumeTrue(false)
        }

        assertEquals("Disable window animations", 0f, window, 0f)
        assertEquals("Disable transition animations", 0f, transition, 0f)
        assertEquals("Disable animator duration", 0f, animator, 0f)
    }

    @Test
    fun testSearchAndNavigateToDetail() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(context, GitHubUserListActivity::class.java)
        ActivityScenario.launch<GitHubUserListActivity>(intent)

        onView(withId(R.id.tv_username)).perform(typeText("Kenzo"), closeSoftKeyboard())
        onView(withId(R.id.buttonSearch)).perform(click())

        Thread.sleep(3000)

        onView(withId(R.id.recyclerViewUsers))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));

        intended(allOf(hasComponent(GitHubUserDetailActivity::class.java.name)))
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}
