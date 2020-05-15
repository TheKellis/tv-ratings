package com.kevinellis.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Value("\${datasource.driver-class-name}")
lateinit var driverClassName: String

@Value("\${datasource.url}")
lateinit var url: String

@Value("\${datasource.username}")
lateinit var dbUser: String

@Value("\$datasource.password")
lateinit var dbPassword: String

@Bean
fun dataSource(): DataSource {
    val dataSource = DriverManagerDataSource()
    dataSource.setDriverClassName(driverClassName)
    dataSource.url = url
    dataSource.username = dbUser
    dataSource.password = dbPassword
    return dataSource
}