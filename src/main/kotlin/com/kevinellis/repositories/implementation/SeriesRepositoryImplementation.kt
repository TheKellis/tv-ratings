package com.kevinellis.repositories.implementation

import com.kevinellis.dao.SeriesDao
import com.kevinellis.repositories.SeriesRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import java.util.stream.Collectors

@Repository
@Component
class SeriesRepositoryImplementation(
    var jdbcTemplate: NamedParameterJdbcTemplate
) : SeriesRepository {
    override fun getSeriesInfoFromTitleId(inputId: String): Optional<SeriesDao> {
        var query =
            "SELECT id, name from series s WHERE s.id = :inputId limit 1"
        var namedParameters = MapSqlParameterSource().addValue("inputId", inputId)
        return jdbcTemplate
            .query(query, namedParameters, SeriesRowMapper())
            .stream()
            .findFirst()
    }

    override fun searchForSeriesMatches(inputSearchTerm: String): List<SeriesDao> {
        var query =
            "SELECT id, name from series s WHERE s.name like :finalSearchTerm limit 10"
        val finalSearchTerm = "%" + inputSearchTerm.toLowerCase().trim().toString() + "%"
        var namedParameters = MapSqlParameterSource().addValue("finalSearchTerm", finalSearchTerm)
        var result = jdbcTemplate
            .query(query, namedParameters, SeriesRowMapper())
        Logger.getGlobal().log(Level.SEVERE, "Result size: " + result.size)
        Logger.getGlobal().log(Level.SEVERE, "input term: " + finalSearchTerm)
        return jdbcTemplate
            .query(query, namedParameters, SeriesRowMapper())
            .parallelStream()
            .limit(10)
            .collect(Collectors.toList())
    }
}

private class SeriesRowMapper : RowMapper<SeriesDao> {
    @Throws(SQLException::class)
    override fun mapRow(resultSet: ResultSet, i: Int): SeriesDao {
        return SeriesDao(
            resultSet.getString("id"), resultSet.getString("name")
        )
    }
}