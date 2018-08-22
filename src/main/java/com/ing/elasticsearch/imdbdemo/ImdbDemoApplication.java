package com.ing.elasticsearch.imdbdemo;

import com.ing.elasticsearch.imdbdemo.model.Movie;
import com.ing.elasticsearch.imdbdemo.service.MovieService;
import org.elasticsearch.client.Client;
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
	public void run(String... args)  {

		printElasticSearchInfo();

		movieService.save(new Movie(UUID.randomUUID(), "Terminator", "James Cameron", 1992, Arrays.asList("Arnold Schwarzzeneger", "Linda Hamilton", "Edward Furlong")));
		movieService.save(new Movie(UUID.randomUUID(), "Fight Club", "David Fincher", 1998, Arrays.asList("Brad Pitt", "Edward Norten", "Helena Bonhem Carter", "Jared Leto")));
		movieService.save(new Movie(UUID.randomUUID(), "Back To the Future", "Robert Zemeckis", 1985, Arrays.asList("Michael J. Fox", "Christopher Lloyd")));

		//fuzzey search
		Page<Movie> movies = movieService.findByDirector("James Cameron", new PageRequest(0, 10));

		//List<Movie> movies = movieService.findByTitle("Fight");

        System.out.println("**************Found movies**************");
		movies.stream().map(Movie::getTitle).forEach(System.out::println);

        Iterable<Movie> allMovies = movieService.findAll();
		allMovies.iterator().forEachRemaining(m -> movieService.delete(m));
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

}
