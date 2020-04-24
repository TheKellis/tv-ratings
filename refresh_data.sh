#!/bin/bash
echo "Beginning at `date`"
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
INPUT_EPISODE_FILE=episode_bundle.tsv

# # Remove the current table
rm tv_info
rm general.sql
rm episodes.sql
rm ratings.sql

# # Recreate the tv_ratings
sqlite3 tv_info "create table series(id TEXT PRIMARY KEY, name TEXT);"
sqlite3 tv_info "create table episodes(id TEXT PRIMARY KEY, name TEXT, rating REAL, num_votes INTEGER, season_number INTEGER, number INTEGER, series_id TEXT,FOREIGN KEY(series_id) REFERENCES series(id));"

echo "Beginning general parsing"

IFS=$'\t'
while read tconst titleType primaryTitle originalTitle isAdult startYear endYear runtimeMinutes genres
do
	if [[ $titleType == "tvSeries" ]]; then
		echo "insert into series(id, name) values('$tconst', '`echo $primaryTitle | tr -d \'`');" >> general.sql
	elif [[ $titleType == "tvEpisode" ]]; then
		echo "insert into episodes(id, name) values('$tconst', '`echo $primaryTitle | tr -d \'`');" >> general.sql
	fi
done < $INPUT_TITLE_GENERAL_FILE

echo "Finished parsing"
echo "Running general.sql script"

sqlite3 tv_info < general.sql 
echo "finished running script"
# Pass through the episode file and populate most of the episode data 
# [parentConst -> episode.series_id, seasonNumber -> episode.season_numbber, episodeNumber -> episode.number]
echo "Beginning episode parsing"
IFS=$'\t'
while read tconst parentTconst seasonNumber episodeNumber
do
	if [[ $tconst != "tconst" ]] && [[ $seasonNumber != 'N' ]] && [[ $episodeNumber != 'N' ]]; then
	  echo "update episodes set series_id = '$parentTconst', season_number = $seasonNumber, number = $episodeNumber WHERE id = '$tconst';" >> episodes.sql
	fi
	# Update episode records in episode table by adding the parent (foreign) key and the season numbers and episode numbers

done < $INPUT_EPISODE_FILE

echo "Finished episode parsing"
echo "Running episodes.sql script"

sqlite3 tv_info < episodes.sql 

# Pass through the ratings file and populate the rating field on episode
# [averageRating -> episode.rating]
IFS=$'\t'
while read tconst averageRating numVotes
do
	if [[ $tconst != "tconst" ]]; then
	  echo "update episodes set rating = '$averageRating', num_votes = $numVotes WHERE id = '$tconst';" >> ratings.sql
	fi
done < $INPUT_RATINGS_FILE

echo "Finished parsing"
echo "Running ratings.sql script"

sqlite3 tv_info < ratings.sql 
echo "All Done! Finished up at `date`"