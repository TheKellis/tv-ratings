package com.kevinellis.repositories

import com.kevinellis.dao.EpisodeDao
import java.util.*

interface EpisodeRepository {
    fun getAllEpisodesForSeries(seriesId: String): List<EpisodeDao>
}