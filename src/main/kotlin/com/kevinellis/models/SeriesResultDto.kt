package com.kevinellis.models

class SeriesResultDto(
    var seriesName: String = "",
    var numSeasons: Int = 0,
    var seasons: List<SeasonDto>
)