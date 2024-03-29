package dmit2015.persistence;

import common.config.ApplicationConfig;
import dmit2015.entity.Movie;
import dmit2015.listener.MoviesApplicationStartupListener;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ArquillianExtension.class)
public class MovieArquillianIT { // The class must be declared as public

    @Inject
    private MovieRepository _movieRepository;

    @Resource
    private UserTransaction _beanManagedTransaction;

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addAsLibraries(pomFile.resolve("com.h2database:h2:2.1.214").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("com.microsoft.sqlserver:mssql-jdbc:11.2.3.jre17").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("com.oracle.database.jdbc:ojdbc11:21.8.0.0").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hibernate.orm:hibernate-spatial:6.1.7.Final").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hamcrest:hamcrest:2.2").withTransitivity().asFile())
                .addClass(ApplicationConfig.class)
                .addClasses(MoviesApplicationStartupListener.class)
                .addClasses(Movie.class, MovieRepository.class)
                .addAsResource("data/csv/movies.csv")
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/beans.xml");
    }

    @Order(3)
    @Test
    void shouldCreate() throws SystemException, NotSupportedException {
        _beanManagedTransaction.begin();
        // Arrange
        Movie newMovie = new Movie();
        newMovie.setGenre("Horror");
        newMovie.setPrice(BigDecimal.valueOf(19.99));
        newMovie.setRating("NC-17");
        newMovie.setTitle("The Return of the Java Master");
        newMovie.setReleaseDate(LocalDate.parse("2021-01-21"));
        // Act
        _movieRepository.add(newMovie);
        // Assert
        Optional<Movie> optionalMovie = _movieRepository.findById(newMovie.getId());
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.orElseThrow();
        assertNotNull(existingMovie);
        assertEquals(newMovie.getTitle(), existingMovie.getTitle());
        assertEquals(newMovie.getGenre(), existingMovie.getGenre());
        assertEquals(newMovie.getPrice(), existingMovie.getPrice());
        assertEquals(newMovie.getRating(), existingMovie.getRating());
        assertEquals(newMovie.getReleaseDate(), existingMovie.getReleaseDate());

        long createTimeDifference = newMovie.getCreateTime().until(LocalDateTime.now(), ChronoUnit.MINUTES);
        assertEquals(0, createTimeDifference);

        assertNull(newMovie.getUpdateTime());

        _beanManagedTransaction.rollback();
    }

    @Order(2)
    @Test
    void shouldFindOne() {
        // Arrange
        final Long editId = 3L;  // for Ghostbusters 2
        // Act
        Optional<Movie> optionalMovie = _movieRepository.findById(editId);
        // Assert
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.orElseThrow();
        assertNotNull(existingMovie);
        assertEquals("Comedy", existingMovie.getGenre());
        assertEquals(9.99, existingMovie.getPrice().doubleValue());
        assertEquals("PG", existingMovie.getRating());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
        assertEquals(LocalDate.parse("1986-2-23", formatter).toString(), existingMovie.getReleaseDate().toString());
        assertNotNull(existingMovie.getCreateTime());
    }

    @Order(1)
    @Test
    void shouldFindAll() {
        // Arrange and Act
        List<Movie> queryResultList = _movieRepository.findAll();
        // Assert
        assertEquals(4, queryResultList.size());

        Movie firstMovie = queryResultList.get(0);
        assertEquals("When Harry Met Sally", firstMovie.getTitle());
        assertEquals("Romantic Comedy", firstMovie.getGenre());
        assertEquals(7.99, firstMovie.getPrice().doubleValue());
        assertEquals("G", firstMovie.getRating());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
        assertEquals(LocalDate.parse("1989-02-12", formatter).toString(), firstMovie.getReleaseDate().toString());

        Movie lastMovie = queryResultList.get(queryResultList.size() - 1);
        assertEquals("Rio Bravo", lastMovie.getTitle());
        assertEquals("Western", lastMovie.getGenre());
        assertEquals(7.99, lastMovie.getPrice().doubleValue());
        assertEquals("PG-13", lastMovie.getRating());
        assertEquals(LocalDate.parse("1959-04-15", formatter).toString(), lastMovie.getReleaseDate().toString());
    }

    @Order(4)
    @Test
    void shouldUpdate() throws SystemException, NotSupportedException {
        // Arrange - Create a new Movie then update the same Movie
        Movie newMovie = new Movie();
        newMovie.setGenre("Adventure");
        newMovie.setPrice(BigDecimal.valueOf(29.99));
        newMovie.setRating("PG");
        newMovie.setTitle("JDK 17 Release Party");
        newMovie.setReleaseDate(LocalDate.parse("2021-09-14"));
        _movieRepository.add(newMovie);

        final Long editId = newMovie.getId();
        Optional<Movie> optionalMovie = _movieRepository.findById(editId);
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.orElseThrow();
        assertNotNull(existingMovie);

        // Act - change the genre, title, rating, price, and release date
        existingMovie.setGenre("Action");
        existingMovie.setTitle("JDK 18 Release Party");
        existingMovie.setRating("PG-13");
        existingMovie.setPrice(BigDecimal.valueOf(19.99));
        existingMovie.setReleaseDate(LocalDate.parse("2022-03-22"));
        _movieRepository.update(editId, existingMovie);

        // Assert
        Optional<Movie> optionalUpdatedMovie = _movieRepository.findById(existingMovie.getId());
        assertTrue(optionalUpdatedMovie.isPresent());
        Movie updatedMovie = optionalUpdatedMovie.orElseThrow();
        System.out.println("Updated movie: "  + updatedMovie);
        assertNotNull(updatedMovie);
        assertEquals(existingMovie.getTitle(), updatedMovie.getTitle());
        assertEquals(existingMovie.getGenre(), updatedMovie.getGenre());
        assertEquals(existingMovie.getPrice().doubleValue(), updatedMovie.getPrice().doubleValue());
        assertEquals(existingMovie.getRating(), updatedMovie.getRating());
        assertEquals(existingMovie.getReleaseDate(), updatedMovie.getReleaseDate());

        assertNotNull(updatedMovie.getUpdateTime());
        long updateTimeDifference = updatedMovie.getUpdateTime().until(LocalDateTime.now(), ChronoUnit.MINUTES);
        assertEquals(0, updateTimeDifference);

        // Delete the entity that was added to test the update method
        _movieRepository.delete(updatedMovie);
    }

    @Order(5)
    @Test
    void shouldDelete() throws SystemException, NotSupportedException {
        _beanManagedTransaction.begin();
        // Arrange
        final Long editId = 3L;  // for Ghostbusters 2
        Optional<Movie> optionalMovie = _movieRepository.findById(editId);
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.orElseThrow();
        assertNotNull(existingMovie);
        // Act
        _movieRepository.delete(existingMovie);
        optionalMovie = _movieRepository.findById(editId);
        // Assert
        assertTrue(optionalMovie.isEmpty());

        _beanManagedTransaction.rollback();
    }

    @Order(6)
    @ParameterizedTest
    @CsvSource({
            "Action",
            ""
    })
    void shouldSelectGenre() throws SystemException, NotSupportedException {

    }
}