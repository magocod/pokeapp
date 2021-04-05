package com.example.pokeapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokeapp.data.model.LoggedInUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

private const val VALID_USERNAME = "basic_user"
private const val VALID_PASSWORD = "123"
private const val VALID_NAME = "trainer"

class LoginDataSourceTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Test
    fun login() = runBlockingTest {
        val dataSource = LoginDataSource()
        val result: Result<LoggedInUser> = dataSource.login(VALID_USERNAME, VALID_PASSWORD)

        var data = LoggedInUser("0","none","none")
        if (result is Result.Success) {
            data = result.data
        }
//        println(result is Result.Error)

//        Assert.assertEquals(true, result is Result.Success)
        Assert.assertEquals("trainer", data.displayName)
    }
}