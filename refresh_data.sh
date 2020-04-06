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
INPUT_TITLE_GENERAL_FILE=title_general_info_bundle.tsv
INPUT_RATINGS_FILE=ratings_bundle.tsv
INPUT_EPISODE_FILE=sepisode_bundle.tsv

IFS=$'\t'
while read tconst titleType primaryTitle originalTitle isAdult startYear endYear runtimeMinutes genres
do
	echo "tconst : $tconst"
	echo "titleType : $titleType"
	echo "primaryTitle : $primaryTitle"
	echo "originalTitle : $originalTitle"
	echo "isAdult : $isAdult"
	echo "startYear : $startYear"
	echo "endYear : $endYear"
	echo "runtimeMinutes : $runtimeMinutes"
	echo "genres : $genres"
	echo ""

done < $INPUT_TITLE_GENERAL_FILE

# Pass through the episode file and populate most of the episode data 
# [parentConst -> episode.series_id, seasonNumber -> episode.season_numbber, episodeNumber -> episode.number]


IFS=$'\t'
while read tconst parentTconst seasonNumber episodeNumber
do
	echo "tconst : $tconst"
	echo "parentTconst : $parentTconst"
	echo "seasonNumber : $seasonNumber"
	echo "episodeNumber : $episodeNumber"
	echo ""

done < $INPUT_EPISODE_FILE


# Pass through the ratings file and populate the rating field on episode
# [averageRating -> episode.rating]

IFS=$'\t'
while read tconst averageRating numVotes
do
	echo "tconst : $tconst"
	echo "averageRating : $averageRating"
	echo "numVotes : $numVotes"
	echo ""

done < $INPUT_RATINGS_FILE
