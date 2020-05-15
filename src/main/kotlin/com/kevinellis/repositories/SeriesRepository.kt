package com.kevinellis.repositories

import com.kevinellis.dao.SeriesDao
import java.util.*

interface SeriesRepository{
    fun getSeriesInfoFromTitleId(inputId: String): Optional<SeriesDao>
}