package com.ing.elasticsearch.imdbdemo;

import com.ing.elasticsearch.imdbdemo.model.Movie;
import com.ing.elasticsearch.imdbdemo.service.MovieService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;


@SpringBootApplication
public class ImdbDemoApplication implements CommandLineRunner {

    @Autowired
    private ElasticsearchOperations es;

    @Autowired
    private MovieService movieService;

    public static void main(String args[]) {

        SpringApplication.run(ImdbDemoApplication.class, args);
    }


    @Override
    public void run(String... args) {

        printElasticSearchInfo();

        //fuzzy search
        Page<Movie> moviesByDirector = movieService.findByDirector("James Cameron", PageRequest.of(0, 10));
        List<Movie> moviesByTitle = movieService.findByTitle("Circle");

        System.out.println("**************Found movies**************");
        moviesByTitle.stream().map(Movie::getPrimaryTitle).forEach(System.out::println);
        System.out.println("**************Found movies over**************");

        /*Iterable<Movie> allMovies = movieService.findAll();
        allMovies.iterator().forEachRemaining(m -> movieService.delete(m));*/
    }

    //useful for debug, print elastic search details
    private void printElasticSearchInfo() {

        System.out.println("--ElasticSearch--");
        Client client = es.getClient();
        Map<String, String> asMap = client.settings().getAsMap();

        asMap.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
        System.out.println("--ElasticSearch--");
    }

    public List<Movie> searchMovies(){
        Client client = es.getClient();

        SearchResponse response = client.prepareSearch("index1", "index2")
                .setTypes("type1", "type2")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery("multi", "test"))                 // Query
                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                .setFrom(0).setSize(60).setExplain(true)
                .get();

        response.getAggregations().asList()
                .forEach(System.out::println);
        return null;

    }

}
