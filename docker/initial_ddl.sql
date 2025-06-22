CREATE TABLE songs
(
    id                                BIGSERIAL PRIMARY KEY,

    artist                            TEXT,
    title                             TEXT,
    "text"                            TEXT,
    length                            TEXT,
    emotion                           TEXT,
    genre                             TEXT,
    album                             TEXT,
    release_date                      DATE,

    "key"                             TEXT,
    tempo                             DOUBLE PRECISION,
    loudness                          DOUBLE PRECISION,
    time_signature                    TEXT,
    is_explicit                       BOOLEAN,
    popularity                        INTEGER,

    energy                            INTEGER,
    danceability                      INTEGER,
    positiveness                      INTEGER,
    speechiness                       INTEGER,
    liveness                          INTEGER,
    acousticness                      INTEGER,
    instrumentalness                  INTEGER,

    is_good_for_party                 BOOLEAN,
    is_good_for_work_study            BOOLEAN,
    is_good_for_relaxation_meditation BOOLEAN,
    is_good_for_exercise              BOOLEAN,
    is_good_for_running               BOOLEAN,
    is_good_for_yoga_stretching       BOOLEAN,
    is_good_for_driving               BOOLEAN,
    is_good_for_social_gatherings     BOOLEAN,
    is_good_for_morning_routine       BOOLEAN,

    similar_artist1                   TEXT,
    similar_song1                     TEXT,
    similarity_score1                 DOUBLE PRECISION,
    similar_artist2                   TEXT,
    similar_song2                     TEXT,
    similarity_score2                 DOUBLE PRECISION,
    similar_artist3                   TEXT,
    similar_song3                     TEXT,
    similarity_score3                 DOUBLE PRECISION
);
-- release_year 을 생성하는 컬럼을 추가합니다. index 를 생성하려면 Immutable 컬럼으로 생성해야 합니다.
ALTER TABLE songs
    ADD COLUMN release_year int GENERATED ALWAYS AS (EXTRACT(year FROM release_date)) STORED;

CREATE INDEX idx_songs_artist_year_inc_album
    ON songs (artist, release_year)
    INCLUDE (album);
