package com.ironhack.bookmark_app.services;

import com.google.gson.Gson;
import com.ironhack.bookmark_app.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class ApiService {

    private WebClient client = WebClient.create("https://openlibrary.org");

    public Book findByTitle(String title) throws Exception {
        var bookResults = client.get()
                .uri("/search.json?title="+ title+ "&limit=5")
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        return convertDataToBook(bookResults) ;
    }

    public static Book convertDataToBook(Object results) throws Exception {
        Gson booksToShow = new Gson();
        var writer = new FileWriter("BooksToShow.json", false);
        writer.write(booksToShow.toJson(results));
        writer.close();
        Reader reader = Files.newBufferedReader(Paths.get("BooksToShow.json"));
        Book books= booksToShow.fromJson(reader, Book.class);
        if (booksToShow==null) throw new Exception();
        return books;
    }

    }

