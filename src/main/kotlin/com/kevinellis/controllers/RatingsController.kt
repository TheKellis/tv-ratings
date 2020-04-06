package com.kevinellis.controllers

import com.kevinellis.models.DbTitleMatch
import org.springframework.web.bind.annotation.*

@RequestMapping("/ratings")
@RestController
class RatingsController {

    @GetMapping(path = ["/series/{id}"],
        name = "Retrieve Ratings",
        produces = ["application/json"])
    fun getAllRatingsForSeries(@PathVariable id: String): String {
        return "Hello World! $id"
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