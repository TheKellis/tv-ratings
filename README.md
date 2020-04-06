This is a springboot application written in Kotlin to return the ratings for tv shows.

It connects to a relational database that can be swapped out with whatever implementation is needed.

### Setting up the data
Running the refresh_data script will download the datasets, unzip them, parse them, and insert them into a database.

# Environment variables

You'll need to define the endpoints for the database that we'll be inserting into

```sh
$ . ./refresh_data.sh
```