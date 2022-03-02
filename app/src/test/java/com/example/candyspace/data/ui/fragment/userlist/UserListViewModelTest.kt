package com.example.candyspace.data.ui.fragment.userlist

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.candyspace.data.model.User
import com.example.candyspace.data.repository.UserRepository
import com.example.candyspace.ui.fragment.userlist.UserListViewModel
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

class UserListViewModelTest {

    private val users = mutableListOf(
        User("1", "User 1", 1, 2, 3, 4, "Url1", Date()),
        User("2", "User 2", 10, 20, 30, 40, "Url2", Date()),
    )

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: UserListViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var context: Application

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        userRepository = mockkClass(UserRepository::class)
        context = mockkClass(Application::class)
        viewModel = spyk(UserListViewModel(userRepository, context))

        every { viewModel.showToastMessage(any()) } answers {}
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onSearchUser_whenHasText_shouldReturnUsers() {
        every { runBlocking { userRepository.getUsers(any()) } } returns Result.success(users)

        viewModel.etUserName.value = "Dummy search value"
        viewModel.userList.value = null

        runBlocking {
            viewModel.onSearchUser()
        }

        Assert.assertNotNull(viewModel.userList.value)
        Assert.assertEquals(2, viewModel.userList.value!!.size)
    }

    @Test
    fun onSearchUser_whenHasNoText_shouldReturnUsers() {
        every { runBlocking { userRepository.getUsers(any()) } } returns Result.success(users)

        viewModel.etUserName.value = ""
        viewModel.userList.value = null

        runBlocking {
            viewModel.onSearchUser()
        }

        Assert.assertNotNull(viewModel.userList.value)
        Assert.assertEquals(2, viewModel.userList.value!!.size)
    }

    @Test
    fun onSearchUser_WhenTextHasNotChanged_shouldNotUpdate() {
        every { runBlocking { userRepository.getUsers(any()) } } returns Result.success(users)

        viewModel.etUserName.value = "Dummy search text"
        viewModel.userList.value = null

        runBlocking {
            viewModel.onSearchUser()
        }

        viewModel.etUserName.value = "Dummy search text"

        runBlocking {
            viewModel.onSearchUser()
        }

        verify(exactly = 1) { runBlocking { userRepository.getUsers(any()) } }
        Assert.assertNotNull(viewModel.userList.value)
        Assert.assertEquals(2, viewModel.userList.value!!.size)
    }

    @Test
    fun onSearchUser_WhenResultIsFailure_shouldShowMessage() {
        every { runBlocking { userRepository.getUsers(any()) } } returns Result.failure(Error("Failed"))

        viewModel.etUserName.value = "Dummy search text"

        runBlocking {
            viewModel.onSearchUser()
        }

        verify(exactly = 1) { viewModel.showToastMessage("Failed") }
        Assert.assertNull(viewModel.userList.value)
    }

    @Test
    fun onBinding_whenFetchedUsers_shouldSetVariables() {
        every { runBlocking { userRepository.getUsers(any()) } } returns Result.success(users)

        viewModel.isRvVisible.value = false
        viewModel.userList.value = null

        runBlocking {
            viewModel.onBinding()
        }

        Assert.assertNotNull(viewModel.userList.value)
        Assert.assertEquals(2, viewModel.userList.value!!.size)
        Assert.assertEquals(true, viewModel.isRvVisible.value)
    }

    @Test
    fun onBinding_whenErrorWhileFetchingUsers_shouldShowMessage() {
        every { runBlocking { userRepository.getUsers(any()) } } returns Result.failure(Error("Dummy Failure"))

        viewModel.isRvVisible.value = false
        viewModel.userList.value = null

        runBlocking {
            viewModel.onBinding()
        }

        verify(exactly = 1) { viewModel.showToastMessage("Dummy Failure") }
        Assert.assertNull(viewModel.userList.value)
        Assert.assertEquals(false, viewModel.isRvVisible.value)
    }
}