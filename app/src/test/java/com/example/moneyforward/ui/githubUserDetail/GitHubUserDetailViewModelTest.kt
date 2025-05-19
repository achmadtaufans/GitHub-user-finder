package com.example.moneyforward.ui.githubUserDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moneyforward.data.GitHubRepos
import com.example.moneyforward.data.GitHubUser
import com.example.moneyforward.domain.GetGitHubUserUseCase
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
import org.junit.Test

class GitHubUserDetailViewModelTest {
 @get:Rule
 val instantTaskExecutorRule = InstantTaskExecutorRule()

 private val useCase: GetGitHubUserUseCase = mockk()
 private lateinit var viewModel: GitHubUserDetailViewModel

 private val testDispatcher = StandardTestDispatcher()

 @Before
 fun setup() {
  Dispatchers.setMain(testDispatcher)
  viewModel = GitHubUserDetailViewModel(useCase)
 }

 @After
 fun tearDown() {
  Dispatchers.resetMain()
 }

 @Test
 fun `fetchGitHubUserAndRepositories success updates user LiveData`() = runTest {
  // Given
  val username = "kenzo"
  val dummyGitHubUser = GitHubUser(
   login = "kenzo",
   id = 1,
   avatar_url = "https://example.com/avatar.png",
   name = "MightyKenzo",
   public_repos = 10,
   followers = 100,
   following = 5
  )

  val dummyListRepos = listOf(
   GitHubRepos(
    name = "Repo1",
    description = "Description1",
    language = "Kotlin",
    stargazers_count = 10,
    fork = false)
  )

  val dummyData = Pair(dummyGitHubUser, dummyListRepos)

  coEvery { useCase(username) } returns Result.success(dummyData)

  // When
  viewModel.fetchGitHubUser(username)
  testDispatcher.scheduler.advanceUntilIdle()

  // Then
  assertEquals(dummyData, Pair(viewModel.user.getOrAwaitValue(), viewModel.userRepoList.getOrAwaitValue()))
  assertNull(viewModel.error.value)
 }

 @Test
 fun `fetchGitHubUserAndRepositories failure updates error LiveData`() = runTest {
  // Given
  val username = "unknown_user"
  val exception = RuntimeException("User not found")

  coEvery { useCase(username) } returns Result.failure(exception)

  // When
  viewModel.fetchGitHubUser(username)
  testDispatcher.scheduler.advanceUntilIdle()

  // Then
  assertEquals("Failed: ${exception.message}", viewModel.error.getOrAwaitValue())
  assertNull(viewModel.user.value)
 }
}