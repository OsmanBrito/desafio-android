package com.osman.bestjava

import com.osman.bestjava.api.RetrofitInitializer
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.IOException

class NetworkUnitTest {

    @Test
    fun callSuccess() {
        val apiCall = RetrofitInitializer.githubApi
        try {
            runBlocking {
                val response = apiCall.repositories("language:kotlin", "stars", 1)
                assertTrue(response.isSuccessful)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}