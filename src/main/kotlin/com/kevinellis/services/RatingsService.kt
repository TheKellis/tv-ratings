package com.kevinellis.services

import com.kevinellis.dao.EpisodeDao
import com.kevinellis.dao.SeriesDao
import com.kevinellis.models.EpisodeDto
import com.kevinellis.models.SeasonDto
import com.kevinellis.models.SeriesResultDto
import com.kevinellis.repositories.EpisodeRepository
import com.kevinellis.repositories.SeriesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
@Service
class RatingsService(
    private var seriesRepository: SeriesRepository,
    private var episodeRepository: EpisodeRepository
) {
    fun getRatingsForSeries(id: String): SeriesResultDto {
        var seriesDao = seriesRepository.getSeriesInfoFromTitleId(id).orElse(SeriesDao())
        var listOfEpisodes = episodeRepository.getAllEpisodesForSeries(id)
        var listOfEpisodeDto = listOfEpisodes.map { episode ->
            EpisodeDto(
                number = episode.number.toInt(),
                rating = episode.rating.toDouble(),
                title = episode.name,
                id = episode.id
            )
        }
        //TODO take list of episodes and make an array of seasondtos from it
        var season1Dto = SeasonDto(seasonNumber = 1, episodes = listOfEpisodeDto)
        return SeriesResultDto(seasons = listOf(), seriesName = seriesDao.name, numSeasons = 0)
    }

    fun putEpisodesInSeasonDtos(episodes: List<EpisodeDao>): List<SeasonDto> {
        return episodes.map { episodeDao ->  }
        return listOf()
    }
}