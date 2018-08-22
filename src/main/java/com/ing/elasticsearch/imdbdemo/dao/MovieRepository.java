package com.ing.elasticsearch.imdbdemo.dao;

import com.ing.elasticsearch.imdbdemo.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.UUID;

public interface MovieRepository extends ElasticsearchRepository<Movie, UUID> {

    Page<Movie> findByDirector(String director, Pageable pageable);

    List<Movie> findByTitle(String title);

}

