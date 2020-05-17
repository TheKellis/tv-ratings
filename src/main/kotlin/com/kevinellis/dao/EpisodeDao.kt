package com.kevinellis.dao

import lombok.Value

@Value
class EpisodeDao(
        var id: String = "",
        var name: String = "default name",
        var rating: Number = 0,
        var num_votes: Number = 0,
        var season_number: Number = 0,
        var number: Number = 0
)