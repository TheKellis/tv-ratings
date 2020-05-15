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

@Repository
@Component
class SeriesRepositoryImplementation(
    var jdbcTemplate: NamedParameterJdbcTemplate
): SeriesRepository {
    override fun getSeriesInfoFromTitleId(inputId: String): Optional<SeriesDao> {
        var query =
                "SELECT id, name from series s WHERE s.id = :inputId limit 1";
        var namedParameters = MapSqlParameterSource().addValue("inputId", inputId);
        return jdbcTemplate
                .query(query, namedParameters,  ComplianceRowMapper())
                .stream()
                .findFirst();
    }
}

private class SeriesRowMapper : RowMapper<SeriesDao?> {
    @Throws(SQLException::class)
    override fun mapRow(resultSet: ResultSet, i: Int): ComplianceStatus? {
        return ComplianceStatus(
                resultSet.getString("case_step"), resultSet.getBoolean("complete"))
    }
}