package com.ing.elasticsearch.imdbdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Arrays;
import java.util.List;

@Document(indexName = "imdb", type = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    //private UUID id;
    private String tconst;
    private String primaryTitle;
    private String director;
    private int startYear;
    private String runtimeMinutes;
    private List<String> genres;

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + tconst + '\'' +
                ", title='" + primaryTitle + '\'' +
                ", director='" + director + '\'' +
                ", startYear='" + startYear + '\'' +
                ", minutes='" + runtimeMinutes + '\'' +
                ", genres='" + Arrays.toString(genres.toArray())+ '\'' +
                '}';
    }
}
