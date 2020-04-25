package com.kevinellis.services

import com.kevinellis.models.SeriesResultDto
import org.springframework.stereotype.Service

@Service
class RatingsService {
    fun getRatingsForSeries(id: String): SeriesResultDto {
        return SeriesResultDto(seasons = arrayOf())
    }
}