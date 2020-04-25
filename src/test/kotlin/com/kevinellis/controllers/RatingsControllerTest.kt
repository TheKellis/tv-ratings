package com.kevinellis.controllers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest()
class RatingsControllerTest {
    private var RatingsController = RatingsController()

    @Test
    fun testGetAllRatingsForSeries() {
        Assertions.assertEquals(1,1)
    }
}