package com.kevinellis.controllers

import com.kevinellis.models.DbTitleMatch
import com.kevinellis.models.EpisodeDto
import com.kevinellis.models.SeasonDto
import com.kevinellis.models.SeriesResultDto
import org.springframework.web.bind.annotation.*

@RequestMapping("/ratings")
@RestController
class RatingsController {

    @GetMapping(path = ["/series/{id}"],
        name = "Retrieve Ratings",
        produces = ["application/json"])
    fun getAllRatingsForSeries(@PathVariable id: String): SeriesResultDto {
        return SeriesResultDto(seasons = arrayOf())
    }

    @GetMapping(path = ["/seriesTest/{id}"],
        name = "Retrieve Ratings",
        produces = ["application/json"])
    fun getAllRatingsForSeriesTest(@PathVariable id: String): SeriesResultDto {
        var testEpisode1 = EpisodeDto(number = 1, rating = 4.4, title = "the fly", id = "tt1011021")
        var testEpisode2 = EpisodeDto(number = 2, rating = 8.4, title = "the people", id = "tt1041021")
        var testSeason1 = SeasonDto(seasonNumber = 1, episodes = arrayOf(testEpisode1, testEpisode2))
        var testSeason2 = SeasonDto(seasonNumber = 2, episodes = arrayOf(testEpisode1, testEpisode2))
        return SeriesResultDto(
            seriesName = "testSeries", numSeasons = 1,seasons = arrayOf(testSeason1, testSeason2)
        )
    }

    @GetMapping(path = ["/search/{searchTerm}"],
        name = "Search for titles",
        produces = ["application/json"])
    fun getPossibleSeriesNamesWithIds(@PathVariable searchTerm: String): Array<DbTitleMatch> {
        var test = DbTitleMatch(1, "one")
        var test2 = DbTitleMatch(2, "two")

        return arrayOf(test, test2)
    }
}