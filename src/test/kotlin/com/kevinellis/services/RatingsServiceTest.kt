package com.kevinellis.services

import com.kevinellis.dao.EpisodeDao
import com.kevinellis.dao.SeriesDao
import com.kevinellis.models.EpisodeDto
import com.kevinellis.models.SeasonDto
import com.kevinellis.models.SeriesResultDto
import com.kevinellis.repositories.EpisodeRepository
import com.kevinellis.repositories.SeriesRepository
import org.assertj.core.api.Assertions.assertThat
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
    fun testGetRatingsForSeries() {
        // Arrange
        val testId = "tt0039125"
        val testSeriesName = "testName"
        val testEpisode1Name = "testEpisode1Name"
        val testEpisode2Name = "testEpisode2Name"
        val testRating = 9.2
        val testRating2 = 8.4
        val testEpisodeId = "tt000000"
        val testNumVotes = 1000
        val testSeriesDao = Optional.of(SeriesDao(id = testId, name = testSeriesName))
        val testEpisodeDao = EpisodeDao(
            id = testEpisodeId,
            name = testEpisode1Name,
            rating = testRating,
            num_votes = testNumVotes,
            season_number = 1,
            number = 1
        )
        val testEpisode2Dao = EpisodeDao(
            id = testEpisodeId,
            name = testEpisode2Name,
            rating = testRating2,
            num_votes = testNumVotes,
            season_number = 2,
            number = 1
        )
        val listOfEpisodes = listOf(testEpisodeDao, testEpisode2Dao)
        val expectedListOfSeasons = ratingsService.putEpisodesInSeasonDtos(listOf(testEpisodeDao, testEpisode2Dao), 2)
        Mockito.`when`(seriesRepository.getSeriesInfoFromTitleId(testId)).thenReturn(testSeriesDao)
        Mockito.`when`(episodeRepository.getAllEpisodesForSeries(testId)).thenReturn(listOfEpisodes)
        val expectedSeriesResultDto: SeriesResultDto =
            SeriesResultDto(seriesName = testSeriesName, numSeasons = 2, seasons = expectedListOfSeasons)

        // Act
        var result = ratingsService.getRatingsForSeries(testId)

        // Assert
        assertThat(result.numSeasons).isEqualTo(expectedSeriesResultDto.numSeasons)
        assertThat(result.seriesName).isEqualTo(expectedSeriesResultDto.seriesName)
        assertThat(result.seasons.size).isEqualTo(expectedSeriesResultDto.seasons.size)
        assertThat(result.seasons.get(0).episodes.size).isEqualTo(expectedSeriesResultDto.seasons.get(0).episodes.size)
        assertThat(result.seasons.get(0).episodes.get(0)).isEqualToComparingFieldByField(
            expectedSeriesResultDto.seasons.get(
                0
            ).episodes.get(0)
        )
    }

    @Test
    fun testFindNumberOfSeasons() {
        val testId = "tt0039125"
        val testName = "testName"
        val testEpisodeDao =
            EpisodeDao(id = testId, name = testName, rating = 9.2, num_votes = 10000, season_number = 1, number = 1)
        val testEpisode2Dao =
            EpisodeDao(id = testId, name = testName, rating = 9.1, num_votes = 9999, season_number = 2, number = 1)
        val listOfEpisodes = listOf(testEpisodeDao, testEpisode2Dao)
        val expectedSeasonCount: Int = 2

        var result = ratingsService.findNumberOfSeasons(listOfEpisodes)
        assertThat(result).isEqualTo(expectedSeasonCount)
    }

    @Test
    fun testGetAllEpisodesForSeason() {
        val testId = "tt0039125"
        val testName = "testName"
        val testEpisodeDao =
            EpisodeDao(id = testId, name = testName, rating = 9.2, num_votes = 10000, season_number = 1, number = 1)
        val testEpisode2Dao =
            EpisodeDao(id = testId, name = testName, rating = 9.1, num_votes = 9999, season_number = 2, number = 1)
        val listOfEpisodes = listOf(testEpisodeDao, testEpisode2Dao)
        val expectedListOfEpisodes: List<EpisodeDto> = listOf(
            EpisodeDto(
                number = testEpisodeDao.number.toInt(),
                rating = testEpisodeDao.rating.toDouble(),
                title = testEpisodeDao.name,
                id = testEpisodeDao.id
            )
        )
        val expectedSeasonDto: SeasonDto = SeasonDto(seasonNumber = 1, episodes = expectedListOfEpisodes)

        val result = ratingsService.getAllEpisodesForSeason(1, listOfEpisodes)

        assertThat(result.episodes.get(0)).isEqualToComparingFieldByField(expectedSeasonDto.episodes.get(0))
        assertThat(result.episodes.size).isEqualTo(expectedSeasonDto.episodes.size)
    }
}
