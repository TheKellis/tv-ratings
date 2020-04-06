#!/bin/bash

# Download the three files
# Grab the three relevant datasets
wget https://datasets.imdbws.com/title.episode.tsv.gz -O episode_bundle.tsv.gz
wget https://datasets.imdbws.com/title.ratings.tsv.gz -O ratings_bundle.tsv.gz
wget https://datasets.imdbws.com/title.basics.tsv.gz -O title_general_info_bundle.tsv.gz


# Unzip the files
gunzip episode_bundle.tsv.gz
gunzip ratings_bundle.tsv.gz
gunzip title_general_info_bundle.tsv.gz

# Pass through title general bundle and populate series and episode tables with inital data
# [tconst -> (series/episode).id, primaryTitle -> (series/episode).name]

# Pass through the episode file and populate most of the episode data 
# [parentConst -> episode.series_id, seasonNumber -> episode.season_numbber, episodeNumber -> episode.number]

# Pass through the ratings file and populate the rating field on episode
# [averageRating -> episode.rating]