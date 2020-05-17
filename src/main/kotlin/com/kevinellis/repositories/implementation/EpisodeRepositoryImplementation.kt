package com.kevinellis.repositories.implementation

import com.kevinellis.dao.EpisodeDao
import com.kevinellis.repositories.EpisodeRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

@Repository
@Component
class EpisodeRepositoryImplementation(
    var jdbcTemplate: NamedParameterJdbcTemplate
) : EpisodeRepository {
    override fun getAllEpisodesForSeries(seriesId: String): List<EpisodeDao> {
        var query =
            "SELECT id, name, rating, num_votes, season_number, number from episodes e WHERE e.seriesId = :seriesId"
        var namedParameters = MapSqlParameterSource().addValue("seriesId", seriesId)
        return jdbcTemplate.query(query, namedParameters, EpisodeRowMapper())
    }
}

private class EpisodeRowMapper : RowMapper<EpisodeDao> {
    @Throws(SQLException::class)
    override fun mapRow(resultSet: ResultSet, rowNum: Int): EpisodeDao? {
        return EpisodeDao(
            resultSet.getString("id"),
            resultSet.getString("name"),
            resultSet.getDouble("rating"),
            resultSet.getInt("num_votes"),
            resultSet.getInt("season_number"),
            resultSet.getInt("number")
        )
    }
}