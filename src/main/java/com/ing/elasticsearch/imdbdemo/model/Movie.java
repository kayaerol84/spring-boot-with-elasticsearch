package com.ing.elasticsearch.imdbdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Document(indexName = "imdb", type = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    private UUID id;
    private String title;
    private String director;
    private int releaseYear;
    private List<String> actors;

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id.toString() + '\'' +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", actors='" + Arrays.toString(actors.toArray())+ '\'' +
                '}';
    }
}
