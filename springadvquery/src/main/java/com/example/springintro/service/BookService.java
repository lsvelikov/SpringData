package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllBookTitlesWithAgeRestriction(AgeRestriction ageRestriction);

    List<String> findAllGoldenBookTitlesWithCopiesLessThan();

    List<String> findAllTitlesAndPricesWithPriceLessThanAndMoreThan();

    List<String> findAllTitlesThatNotReleasedInYear(int year);

    List<String> findAllBooksBeforeDate(LocalDate localDate);

    List<String> findAllBooksWhichContainGivenString(String substring);

    List<String> findAllBooksTitlesWrittenByAuthorsWithLastName(String lastNameStarts);

    int numberOfBooksWithTitleLengthLongerThan(int length);

    List<String> findBookInfo(String title);
}
