package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.UserDTO;
import com.ironhack.bookmark_app.error.NotFoundException;
import com.ironhack.bookmark_app.model.User;
import com.ironhack.bookmark_app.repository.UserRepository;
import com.ironhack.bookmark_app.userinput.UserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDTO createUser() {

        System.out.print("\nIndicates a user name:\n");
        final String name = UserInput.readText();

        var user = new User(name);
        var userCreated = userRepository.save(user);

        System.out.println("\nThe ID of " + userCreated.getName() + " is " + userCreated.getId());

        var userCreatedDTO = new UserDTO().fromEntity(userCreated);
        return userCreatedDTO;
    }

    @Override
    public UserDTO findById(Long id) throws NotFoundException {
        final var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        var userDTO = new UserDTO().fromEntity(user);
        return userDTO;
    }

    @Override
    public void showAll() {
        userRepository.findAll().forEach((User user) -> {
            var userDTO = new UserDTO().fromEntity(user);
            System.out.println("\n" + userDTO.toString()  + "\n");
        });
    }

    @Override
    public void showById(Long id) {
        try {
            var userDTO = findById(id);
            System.out.println("\n" + userDTO.toString() + "\n");

        } catch(NotFoundException error) {
            System.out.println("\n" + error.getMessage() + "\n");
        }
    }


}
