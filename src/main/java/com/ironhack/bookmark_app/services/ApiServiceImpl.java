package com.ironhack.bookmark_app.services;

import com.google.gson.Gson;
import com.ironhack.bookmark_app.dto.BookDTO;
import com.ironhack.bookmark_app.dto.UserDTO;
import com.ironhack.bookmark_app.model.Book;

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
public class ApiServiceImpl implements ApiService {

    @Autowired
    BookService bookService;

    @Autowired
    FavouriteService favouriteService;

    @Autowired
    UserService userService;

    final int size = 16 * 1024 * 1024;
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
            .build();
    private WebClient client = WebClient.builder()
            .exchangeStrategies(strategies)
            .build();

    @Override
    public void searchBook() throws ParseException {
        try {
            List<BookDTO> booksDTO = findByTitle();

            var bookSavedDTO = bookService.saveBook(booksDTO.get(0));
            UserDTO user = userService.findById(1L);
            userService.addBook(user.getId(), bookSavedDTO);

            for(BookDTO bookDTO: booksDTO){
                System.out.println(bookDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<BookDTO> findByTitle() throws ParseException {
        System.out.println("Write the title:");
        String title = new Scanner(System.in).nextLine();
        String titleFixed = title.trim();
        titleFixed = titleFixed.toLowerCase();
        titleFixed = titleFixed.replace(" ","+");

        System.out.println("How many results do you want?");
        int limit = new Scanner(System.in).nextInt();

        var bookResults = client.get()
                .uri("https://openlibrary.org/search.json?title=" + titleFixed + "&limit=" + limit)
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        return convertDataToResults(bookResults);
    }

    private List<BookDTO> convertDataToResults(Object results) throws ParseException {
        Gson booksToShow = new Gson();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(booksToShow.toJson(results));
        JSONArray jsonArray = (JSONArray) jsonObject.get("docs");
        Object[] array = jsonArray.toArray();
        var booksFound = new ArrayList<BookDTO>();

        for (int i = 0; i < array.length; i++) {
            JSONObject jsonObject1 = (JSONObject) jsonParser.parse(booksToShow.toJson(array[i]));
            var bookDTO = new BookDTO(jsonObject1.get("key").toString(), jsonObject1.get("title").toString(), jsonObject1.get("author_name").toString());
            booksFound.add(bookDTO);
        }

        return booksFound;
    }

}

