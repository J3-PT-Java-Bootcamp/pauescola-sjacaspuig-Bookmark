package com.ironhack.bookmark_app.services;

import com.google.gson.Gson;
import com.ironhack.bookmark_app.model.Book;

import com.ironhack.bookmark_app.userinput.UserInput;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ApiService {
    @Autowired
    BookServiceImpl bookService;

    final int size = 16 * 1024 * 1024;
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
            .build();
    private WebClient client = WebClient.builder()
            .exchangeStrategies(strategies)
            .build();

    public void searchBook() throws ParseException {
        List<Book> toShow = findByTitle();
        choiceProcessing(toShow);

    }

    public void choiceProcessing(List<Book> booksOptions) {
        boolean answer = true;
        if (booksOptions.isEmpty()) System.out.println("There are no results to show");
        else {
            do {
                int pos = 1;
                for (Book i : booksOptions) {
                    System.out.println("[" + pos + "]" + " " + i);
                    pos++;
                }
                System.out.print("/Choose the book[id] you want to add to the library \n");

                int choice = UserInput.getIntBetween(1, pos) - 1;
                bookService.saveBook(booksOptions.get(choice));
                booksOptions.remove(choice);
                System.out.println("Keep adding books? [y/n]");
                answer = UserInput.getYesNo();
            } while (!answer);
        }

    }

    public List<Book> findByTitle() throws ParseException {
        System.out.println("Write the title:");
        String title = new Scanner(System.in).nextLine();
        String titleFixed = title.trim();
        titleFixed = titleFixed.toLowerCase();
        titleFixed = titleFixed.replace(" ", "+");

        System.out.println("How many results do you want?");
        int limit = new Scanner(System.in).nextInt();

        var bookResults = client.get()
                .uri("https://openlibrary.org/search.json?title=" + titleFixed + "&limit=" + limit)
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        return convertDataToResults(bookResults);
    }

    public static List<Book> convertDataToResults(Object results) throws ParseException {
        Gson booksToShow = new Gson();

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

        return booksFound;
    }

}

