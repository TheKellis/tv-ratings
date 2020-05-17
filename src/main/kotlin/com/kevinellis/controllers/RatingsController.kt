package com.kevinellis.controllers

import com.kevinellis.dao.SeriesDao
import com.kevinellis.models.DbTitleMatch
import com.kevinellis.models.EpisodeDto
import com.kevinellis.models.SeasonDto
import com.kevinellis.models.SeriesResultDto
import com.kevinellis.repositories.SeriesRepository
import com.kevinellis.services.RatingsService
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*

@RequestMapping("/ratings")
@RestController
@Component
class RatingsController(
    private val ratingsService: RatingsService
) {

    @GetMapping(path = ["/series/{id}"],
        name = "Retrieve Ratings",
        produces = ["application/json"])
    fun getAllRatingsForSeries(@PathVariable id: String): SeriesResultDto {
        return ratingsService.getRatingsForSeries(id)
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