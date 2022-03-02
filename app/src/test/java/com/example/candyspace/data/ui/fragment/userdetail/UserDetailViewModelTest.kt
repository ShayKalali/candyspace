package com.example.candyspace.data.ui.fragment.userdetail

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.candyspace.data.model.User
import com.example.candyspace.data.repository.UserRepository
import com.example.candyspace.ui.fragment.userdetail.UserDetailViewModel
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import java.util.*

class UserDetailViewModelTest {

    private val user = User("1", "User 1", 1, 2, 3, 4, "Url1", Date())
    private val userTopTags = mutableListOf("Tag1", "Tag2", "Tag3")

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: UserDetailViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var context: Application

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        userRepository = mockkClass(UserRepository::class)
        context = mockkClass(Application::class)
        viewModel = spyk(UserDetailViewModel(userRepository, context))

        every { viewModel.showToastMessage(any()) } answers {}
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun setUserDetail_shouldSetUserFields() {
        every { runBlocking { userRepository.getUserTopTags(any()) } } returns Result.success(
            userTopTags
        )

        runBlocking {
            viewModel.setUserDetail(user)
        }

        Assert.assertEquals(user.profileImage, viewModel.userAvatarUrl.value)
        Assert.assertEquals(user.name, viewModel.userName.value)
        Assert.assertEquals(user.reputation.toString(), viewModel.reputation.value)
        Assert.assertEquals(user.goldBadgeCounts.toString(), viewModel.goldBadge.value)
        Assert.assertEquals(user.silverBadgeCounts.toString(), viewModel.silverBadge.value)
        Assert.assertEquals(user.bronzeBadgeCounts.toString(), viewModel.bronzeBadge.value)
        Assert.assertEquals(user.location, viewModel.location.value)
        Assert.assertEquals(user.creationDate.toString(), viewModel.creationDate.value)
        Assert.assertNotNull(viewModel.topTags.value)
        Assert.assertTrue(viewModel.topTags.value!!.contains("Tag1"))
        Assert.assertTrue(viewModel.topTags.value!!.contains("Tag2"))
        Assert.assertTrue(viewModel.topTags.value!!.contains("Tag3"))
    }

    @Test
    fun setUserDetail_whenErrorWhileFetchingTags_shouldShowMessage() {
        every { runBlocking { userRepository.getUserTopTags(any()) } } returns Result.failure(
            Error(
                "Failure"
            )
        )

        runBlocking {
            viewModel.setUserDetail(user)
        }

        Assert.assertEquals(user.profileImage, viewModel.userAvatarUrl.value)
        Assert.assertEquals(user.name, viewModel.userName.value)
        Assert.assertEquals(user.reputation.toString(), viewModel.reputation.value)
        Assert.assertEquals(user.goldBadgeCounts.toString(), viewModel.goldBadge.value)
        Assert.assertEquals(user.silverBadgeCounts.toString(), viewModel.silverBadge.value)
        Assert.assertEquals(user.bronzeBadgeCounts.toString(), viewModel.bronzeBadge.value)
        Assert.assertEquals(user.location, viewModel.location.value)
        Assert.assertEquals(user.creationDate.toString(), viewModel.creationDate.value)
        Assert.assertTrue(viewModel.topTags.value?.isEmpty() == true)
        verify(exactly = 1) { viewModel.showToastMessage("Failure") }
    }

    @Test
    fun clearData_shouldEmptyUserFields() {
        viewModel.userAvatarUrl.value = "Dummy value"
        viewModel.userName.value = "Dummy value"
        viewModel.reputation.value = "Dummy value"
        viewModel.goldBadge.value = "Dummy value"
        viewModel.silverBadge.value = "Dummy value"
        viewModel.bronzeBadge.value = "Dummy value"
        viewModel.location.value = "Dummy value"
        viewModel.creationDate.value = "Dummy value"
        viewModel.topTags.value = userTopTags

        viewModel.clearData()

        Assert.assertNull(viewModel.userAvatarUrl.value)
        Assert.assertNull(viewModel.userName.value)
        Assert.assertNull(viewModel.reputation.value)
        Assert.assertNull(viewModel.goldBadge.value)
        Assert.assertNull(viewModel.silverBadge.value)
        Assert.assertNull(viewModel.bronzeBadge.value)
        Assert.assertNull(viewModel.location.value)
        Assert.assertNull(viewModel.creationDate.value)
        Assert.assertTrue(viewModel.topTags.value?.isEmpty() == true)
    }
}