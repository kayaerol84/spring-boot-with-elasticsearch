package com.ing.elasticsearch.imdbdemo.service;


import com.ing.elasticsearch.imdbdemo.dao.MovieRepository;
import com.ing.elasticsearch.imdbdemo.model.Movie;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService { //implements MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public void setMovieRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void delete(Movie movie) {
        movieRepository.delete(movie);
    }

    public Optional<Movie> findById(UUID id) {
        return movieRepository.findById(id);
    }

    public Iterable<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Page<Movie> findByDirector(String author, PageRequest pageRequest) {
        return movieRepository.findByDirector(author, pageRequest);
    }

    public List<Movie> findByTitle(String title) {
        return movieRepository.findByPrimaryTitle(title);
    }



}

