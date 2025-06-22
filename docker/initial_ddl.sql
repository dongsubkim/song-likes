CREATE TABLE songs
(
    id                            BIGSERIAL PRIMARY KEY,

    artist                        TEXT NOT NULL,
    title                         TEXT NOT NULL,
    "text"                        TEXT,
    length                        INTERVAL,
    emotion                       TEXT,
    genre                         TEXT,
    album                         TEXT NOT NULL,
    release_date                  DATE,

    "key"                         TEXT,
    tempo                         DOUBLE PRECISION,
    loudness                      DOUBLE PRECISION,
    time_signature                TEXT,
    is_explicit                   BOOLEAN,
    popularity                    INTEGER,

    energy                        INTEGER,
    danceability                  INTEGER,
    positiveness                  INTEGER,
    speechiness                   INTEGER,
    liveness                      INTEGER,
    acousticness                  INTEGER,
    instrumentalness              INTEGER,

    is_good_for_party             BOOLEAN,
    is_good_for_work_study        BOOLEAN,
    is_good_for_relaxation_med    BOOLEAN,
    is_good_for_exercise          BOOLEAN,
    is_good_for_running           BOOLEAN,
    is_good_for_yoga_stretching   BOOLEAN,
    is_good_for_driving           BOOLEAN,
    is_good_for_social_gatherings BOOLEAN,
    is_good_for_morning_routine   BOOLEAN,

    similar_artist1               TEXT,
    similar_song1                 TEXT,
    similarity_score1             DOUBLE PRECISION,
    similar_artist2               TEXT,
    similar_song2                 TEXT,
    similarity_score2             DOUBLE PRECISION,
    similar_artist3               TEXT,
    similar_song3                 TEXT,
    similarity_score3             DOUBLE PRECISION
);

CREATE INDEX idx_songs_artist_year_inc_album
    ON songs (artist, date_trunc('year', release_date))
    INCLUDE (album);
