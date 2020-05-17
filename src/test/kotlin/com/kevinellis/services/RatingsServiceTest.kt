package com.kevinellis.services

import com.kevinellis.controllers.RatingsController
import com.kevinellis.dao.EpisodeDao
import com.kevinellis.dao.SeriesDao
import com.kevinellis.models.SeasonDto
import com.kevinellis.models.SeriesResultDto
import com.kevinellis.repositories.EpisodeRepository
import com.kevinellis.repositories.SeriesRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RatingsServiceTest {
    @MockBean
    lateinit var seriesRepository: SeriesRepository
    @MockBean
    lateinit var episodeRepository: EpisodeRepository

    @Autowired
    private lateinit var ratingsService: RatingsService

    @Test
    fun test(){
        // Arrange
        val testId = "tt0039125"
        val testName = "testName"
        val testSeriesDao = Optional.of(SeriesDao(id = testId, name = testName))
        val testEpisodeDao = EpisodeDao(id = "tt000000", name = "testEpisode1", rating = 9.2, num_votes = 10000, season_number = 1, number = 1)
        val testEpisode2Dao = EpisodeDao(id = "tt000001", name = "testEpisode2", rating = 9.1, num_votes = 9999, season_number = 2, number = 1)
        val listOfEpisodes = listOf(testEpisodeDao, testEpisode2Dao)
        val expectedSeason1Dto = SeasonDto(seasonNumber = 1, episodes = listOf())
        val expectedSeason2Dto = SeasonDto(seasonNumber = 2, episodes = listOf())
        val expectedListOfSeasons = listOf(expectedSeason1Dto, expectedSeason2Dto)
        Mockito.`when`(seriesRepository.getSeriesInfoFromTitleId(testId)).thenReturn(testSeriesDao)
        Mockito.`when`(episodeRepository.getAllEpisodesForSeries(testId)).thenReturn(listOfEpisodes)

        //Act
        var result = ratingsService.getRatingsForSeries(testId)

        //Assert
        var expectedSeriesResultDto: SeriesResultDto = SeriesResultDto(seriesName = testName, numSeasons = 2, seasons = expectedListOfSeasons)
        org.assertj.core.api.Assertions.assertThat(result).isEqualToComparingFieldByField(expectedSeriesResultDto)
    }
}
