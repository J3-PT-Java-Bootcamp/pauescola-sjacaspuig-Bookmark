package com.ironhack.bookmark_app.services;

import com.google.gson.Gson;
import com.ironhack.bookmark_app.dto.BookDTO;
import com.ironhack.bookmark_app.dto.UserDTO;
import com.ironhack.bookmark_app.error.NotFoundException;

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
public class ApiServiceImpl implements ApiService {

    @Autowired
    BookService bookService;

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
    public void searchBook() throws ParseException, NotFoundException {
        List<BookDTO> booksDTO = findByTitle();
        choiceProcessing(booksDTO);
    }

    public void choiceProcessing(List<BookDTO> booksOptions) throws NotFoundException {
        boolean answer;
        List<BookDTO> booksToShow = new ArrayList<>();
        for (BookDTO book:booksOptions){
            booksToShow.add(book);
        }
        do {
            if (booksToShow.isEmpty()) {
                System.out.println("There are no results to show");
                break;
            }
            else{

                int pos = 1;
                for (BookDTO i : booksToShow) {
                    System.out.println("[" + pos + "]" + " " + i.toString(false));
                    pos++;
                }
                System.out.print("\nChoose the book [id] that you want to add to the library\n");
                int choice = UserInput.getIntBetween(1, pos) - 1;
                var bookSelected = booksToShow.get(choice);
                bookService.saveBook(bookSelected);
                booksToShow.remove(choice);

                System.out.println("Do you want to assign the book to some user? [y/n]");
                var answerAssign = UserInput.getYesNo();

                if (answerAssign) assignBookToUser(bookSelected);

                System.out.println("Keep adding books of the previous list? [y/n]");
                answer = UserInput.getYesNo();
            }
        } while (answer);


    }

    @Override
    public List<BookDTO> findByTitle() throws ParseException {
        System.out.println("Search by title:\n");
        String title = new Scanner(System.in).nextLine();
        String titleFixed = title.trim();
        titleFixed = titleFixed.toLowerCase();
        titleFixed = titleFixed.replace(" ", "+");

        System.out.println("How many results do you want?\n");
        int limit = new Scanner(System.in).nextInt();

        System.out.println("\nLoading...\n");

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

    private void assignBookToUser(BookDTO bookDTO) throws NotFoundException {
        var usersDTO = userService.findAll();

        int pos = 1;
        for (UserDTO userDTO : usersDTO) {
            System.out.println("[" + pos + "] Name: " + userDTO.getName());
            pos++;
        }
        System.out.print("\nChoose the user [id]\n");
        int posUser = UserInput.getIntBetween(1, pos) - 1;
        var userDTO = usersDTO.get(posUser);

        userService.assignBook(userDTO.getId(), bookDTO.getId());
    }

}

