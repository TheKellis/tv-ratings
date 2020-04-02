package com.kevinellis.repositories

import com.kevinellis.dao.Series
import org.springframework.data.repository.CrudRepository

interface SeriesRepository : CrudRepository<Series, Long>