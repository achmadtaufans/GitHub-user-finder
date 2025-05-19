package com.example.moneyforward.ui.githubUserList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moneyforward.data.GitHubList
import com.example.moneyforward.data.GitHubUser
import com.example.moneyforward.domain.GetGitHubUserListUseCase
import com.example.moneyforward.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test

class GitHubUserListViewModelTest {
  // Rule to allow LiveData to execute instantly
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private val useCase: GetGitHubUserListUseCase = mockk()
  private lateinit var viewModel: GitHubUserListViewModel

  private val testDispatcher = StandardTestDispatcher()

  @Before
  fun setup() {
   Dispatchers.setMain(testDispatcher)
   viewModel = GitHubUserListViewModel(useCase)
  }

  @After
  fun tearDown() {
   Dispatchers.resetMain()
  }

  @Test
  fun `fetchGitHubUser success updates user LiveData`() = runTest {
   // Given
   val username = "kenzo"
   val fakeUser = GitHubList(
       10,
       false,
       listOf(
           GitHubUser(
            login = "kenzo",
            id = 1,
            avatar_url = "https://example.com/avatar.png",
            name = "MightyKenzo",
            public_repos = 10,
            followers = 100,
            following = 5
           )
       )
   )

   coEvery { useCase(username) } returns Result.success(fakeUser)

   // When
   viewModel.getGitHubUserList(username)
   testDispatcher.scheduler.advanceUntilIdle()

   // Then
   assertEquals(fakeUser, viewModel.userList.getOrAwaitValue())
   assertNull(viewModel.error.value)
  }

  @Test
  fun `fetchGitHubUser failure updates error LiveData`() = runTest {
   // Given
   val username = "unknown_user"
   val exception = RuntimeException("User not found")

   coEvery { useCase(username) } returns Result.failure(exception)

   // When
   viewModel.getGitHubUserList(username)
   testDispatcher.scheduler.advanceUntilIdle()

   // Then
   assertEquals("Failed: ${exception.message}", viewModel.error.getOrAwaitValue())
   assertNull(viewModel.userList.value)
  }
}