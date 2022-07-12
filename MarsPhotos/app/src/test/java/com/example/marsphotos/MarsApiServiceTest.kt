package com.example.marsphotos

import com.example.marsphotos.network.MarsApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MarsApiServiceTest : BaseTest() {
    private lateinit var service: MarsApiService

    @Before
    fun setup() {
        val url = mockWebServer.url("/")

        service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build()
            .create(MarsApiService::class.java)
    }

    @Test
    fun apiService() {
        enqueue("mars_photos.json")

        runBlocking {
            val apiResponse = service.getPhotos()
            assertNotNull(apiResponse)
            assertTrue("The list was empty", apiResponse.isNotEmpty())
            assertEquals("The IDs did mot match", "424905", apiResponse[0].id)
        }
    }
}
