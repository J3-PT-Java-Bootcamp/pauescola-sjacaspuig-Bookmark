package com.ironhack.bookmark_app.services;

import com.google.gson.Gson;
import com.ironhack.bookmark_app.model.Book;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Service
public class ApiService {

    private WebClient client = WebClient.create("https://openlibrary.org");

    public ArrayList<Book> findByTitle(String title, int limit) throws Exception {
        String titleFixed = title.trim().replace(" ", "+");
        var bookResults = client.get()
                .uri("/search.json?title=" + titleFixed + "&limit=" + limit)
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        return convertDataToResults(bookResults);
    }

    public static ArrayList<Book> convertDataToResults(Object results) throws Exception {
        Gson booksToShow = new Gson();
        /*var writer = new FileWriter("BooksToShow.json", false);
        writer.write(booksToShow.toJson(results));
        writer.close();*/
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(booksToShow.toJson(results));
        JSONArray jsonArray = (JSONArray) jsonObject.get("docs");
        Object[] array = jsonArray.toArray();
        var booksFound = new ArrayList<Book>();
        for (int i = 0; i < array.length; i++) {
            JSONObject jsonObject1 = (JSONObject) jsonParser.parse(booksToShow.toJson(array[i]));
            var book = new Book(jsonObject1.get("key").toString(), jsonObject1.get("title").toString(), jsonObject1.get("author_name").toString());
            booksFound.add(book);
        }

        if (booksToShow == null) throw new Exception("No results found");
        return booksFound;
    }

}

