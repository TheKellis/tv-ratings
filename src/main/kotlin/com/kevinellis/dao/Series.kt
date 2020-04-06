package com.kevinellis.dao

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Series(
    @Id
    var id: Long = 0,
    var numberOfSeasons: Int = 0,
    var name: String = "Default Series Name",
    var webLink: String = "www.imdb.com"
)