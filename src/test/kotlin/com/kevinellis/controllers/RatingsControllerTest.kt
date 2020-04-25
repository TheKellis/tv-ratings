package com.kevinellis.controllers

import com.kevinellis.models.EpisodeDto
import com.kevinellis.models.SeasonDto
import com.kevinellis.models.SeriesResultDto
import com.kevinellis.services.RatingsService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RatingsControllerTest {
    val ratingsService = Mockito.mock(RatingsService::class.java)

    private var RatingsController = RatingsController(ratingsService = ratingsService)

    lateinit var sampleSeries: SeriesResultDto

    @BeforeAll
    fun setup() {
        sampleSeries = SeriesResultDto(
            seasons = arrayOf(
                SeasonDto(
                    seasonNumber = 1,
                    episodes = arrayOf(
                        EpisodeDto(number = 0),
                        EpisodeDto(number = 1)
                    )
                )
            )
        )
    }

    @Test
    fun testGetAllRatingsForSeries() {
        var input = "testId"
        Mockito.`when`(ratingsService.getRatingsForSeries(input)).thenReturn(sampleSeries)

        Assertions.assertEquals(sampleSeries, ratingsService.getRatingsForSeries(input))
    }
}