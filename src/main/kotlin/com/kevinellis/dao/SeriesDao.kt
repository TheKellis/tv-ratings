package com.kevinellis.dao

import lombok.Value

@Value
class SeriesDao(
    var id: String = "",
    var name: String = "Default Series Name"
)