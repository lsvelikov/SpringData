package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader bufferedReader;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, BufferedReader bufferedReader) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        //printAllBooksAfterYear(2000);
        //printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
        //printAllAuthorsAndNumberOfTheirBooks();
        //printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

        System.out.println("Please select exercise:");
        int exeNumber = Integer.parseInt(bufferedReader.readLine());

        switch (exeNumber) {
            case 1 -> booksTitleByAgeRestriction();
            case 2 -> goldenBooks();
            case 3 -> bookByPrice();
            case 4 -> bookTitlesNotReleasedInGivenYear();
            case 5 -> booksReleasedBeforeDate();
            case 6 -> authorsSearch();
            case 7 -> bookSearch();
            case 8 -> bookTitleSearch();
            case 9 -> countBooks();
            case 10 -> totalBookCopies();
            case 11 -> reducedBook();
        }

    }

    private void reducedBook() throws IOException {
        System.out.println("Please enter a book title:");
        String title = bufferedReader.readLine();

        bookService.findBookInfo(title)
                .forEach(System.out::println);
    }

    private void totalBookCopies() {

        authorService.findTotalBookCopiesByAuthorDesc()
                .forEach(System.out::println);
    }

    private void countBooks() throws IOException {
        System.out.println("Please enter title length:");
        int length = Integer.parseInt(bufferedReader.readLine());

        System.out.println(bookService.numberOfBooksWithTitleLengthLongerThan(length));
    }

    private void bookTitleSearch() throws IOException {
        System.out.println("Please enter how the author last name starts:");
        String lastNameStarts = bufferedReader.readLine();

        bookService.findAllBooksTitlesWrittenByAuthorsWithLastName(lastNameStarts)
                .forEach(System.out::println);

    }

    private void bookSearch() throws IOException {
        System.out.println("Please enter a substring from a book title:");
        String substring = bufferedReader.readLine();

        bookService.findAllBooksWhichContainGivenString(substring)
                .forEach(System.out::println);
    }

    private void authorsSearch() throws IOException {
        System.out.println("Please enter the end letters from author first name:");
        String endFirstName = bufferedReader.readLine();

        authorService.authorsWithFirstNameEndingWith(endFirstName)
                .forEach(System.out::println);
    }

    private void booksReleasedBeforeDate() throws IOException {
        System.out.println("Please enter year in format dd-MM-yyyy:");
        LocalDate localDate = LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        bookService.findAllBooksBeforeDate(localDate)
                .forEach(System.out::println);
    }

    private void bookTitlesNotReleasedInGivenYear() throws IOException {
        System.out.println("Please enter year:");
        int year = Integer.parseInt(bufferedReader.readLine());

        bookService.findAllTitlesThatNotReleasedInYear(year)
                .forEach(System.out::println);
    }

    private void bookByPrice() {
        bookService.findAllTitlesAndPricesWithPriceLessThanAndMoreThan()
                .forEach(System.out::println);
    }

    private void goldenBooks() {
        bookService.findAllGoldenBookTitlesWithCopiesLessThan()
                .forEach(System.out::println);
    }

    private void booksTitleByAgeRestriction() throws IOException {
        System.out.println("Please enter age restriction:");
        AgeRestriction ageRestriction = AgeRestriction.valueOf(bufferedReader.readLine().toUpperCase());

        bookService.findAllBookTitlesWithAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
