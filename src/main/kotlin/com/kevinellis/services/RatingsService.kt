package com.kevinellis.services

import com.kevinellis.dao.EpisodeDao
import com.kevinellis.dao.SeriesDao
import com.kevinellis.models.DbTitleMatch
import com.kevinellis.models.EpisodeDto
import com.kevinellis.models.SeasonDto
import com.kevinellis.models.SeriesResultDto
import com.kevinellis.repositories.EpisodeRepository
import com.kevinellis.repositories.SeriesRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.logging.Level
import java.util.logging.Logger
import java.util.stream.Collectors

@Component
@Service
class RatingsService(
    private var seriesRepository: SeriesRepository,
    private var episodeRepository: EpisodeRepository
) {
    fun getRatingsForSeries(id: String): SeriesResultDto {
        var seriesDao = seriesRepository.getSeriesInfoFromTitleId(id).orElse(SeriesDao())
        var listOfEpisodes = episodeRepository.getAllEpisodesForSeries(id)
        val numberOfSeasons = findNumberOfSeasons(listOfEpisodes)

        return SeriesResultDto(
            seasons = putEpisodesInSeasonDtos(listOfEpisodes, numberOfSeasons),
            seriesName = seriesDao.name,
            numSeasons = numberOfSeasons
        )
    }

    fun searchForSeriesMatches(searchTerm: String): List<DbTitleMatch> {
        return seriesRepository.searchForSeriesMatches(searchTerm)
            .parallelStream()
            .map { seriesDao ->
                Logger.getGlobal().log(Level.SEVERE, "test log")
                DbTitleMatch(id = seriesDao.id, title = seriesDao.name)
            }
            .collect(Collectors.toList())
    }

    fun putEpisodesInSeasonDtos(episodes: List<EpisodeDao>, numberOfSeasons: Int): List<SeasonDto> {
        var listOfSeasons: MutableList<SeasonDto> = mutableListOf()
        var seasonCount: Int = 1
        while (seasonCount <= numberOfSeasons) {
            listOfSeasons.add(getAllEpisodesForSeason(seasonCount, episodes))
            seasonCount++
        }
        return listOfSeasons
    }

    fun findNumberOfSeasons(listOfEpisodeDao: List<EpisodeDao>): Int {
        var numSeasons: Int = 0
        listOfEpisodeDao.map { episodeDao ->
            if (episodeDao.season_number.toInt() > numSeasons) numSeasons = episodeDao.season_number.toInt()
        }
        return numSeasons
    }

    fun getAllEpisodesForSeason(seasonNumber: Int, episodes: List<EpisodeDao>): SeasonDto {
        var runningListOfEpisodesInCurrentSeason: MutableList<EpisodeDto> = mutableListOf()
        episodes.map { episodeDao ->
            if (episodeDao.season_number == seasonNumber) runningListOfEpisodesInCurrentSeason.add(
                EpisodeDto(
                    number = episodeDao.number.toInt(),
                    rating = episodeDao.rating.toDouble(),
                    title = episodeDao.name,
                    id = episodeDao.id
                )
            )
        }
        runningListOfEpisodesInCurrentSeason.sortBy { episodeDto -> episodeDto.number }
        return SeasonDto(seasonNumber = seasonNumber, episodes = runningListOfEpisodesInCurrentSeason)
    }
}