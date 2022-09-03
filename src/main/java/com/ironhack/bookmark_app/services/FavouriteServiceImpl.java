package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.FavouriteDTO;
import com.ironhack.bookmark_app.enums.FavouriteStatus;
import com.ironhack.bookmark_app.error.NotFoundException;
import com.ironhack.bookmark_app.model.Favourite;
import com.ironhack.bookmark_app.repository.FavouriteRepository;
import com.ironhack.bookmark_app.userinput.UserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    @Autowired
    FavouriteRepository favouriteRepository;

    @Autowired
    UserService userService;

    @Override
    public FavouriteDTO saveFavourite(FavouriteDTO favouriteDTO) {
        var favourite = Favourite.fromDTO(favouriteDTO);
        favourite.setId(null);
        Favourite favouriteSaved = favouriteRepository.save(favourite);
        return FavouriteDTO.fromEntity(favouriteSaved);
    }

    @Override
    public FavouriteDTO updateStatus(Long userID) throws NotFoundException {
        var userDTO = userService.findById(userID);

        // Select favourite
        int posFav = 1;
        for (Favourite favourite : userDTO.getFavourites()) {
            var favouriteDTO = FavouriteDTO.fromEntity(favourite);
            System.out.println("[" + posFav + "]" + " " + favouriteDTO.toStringHorizontal());
            posFav++;
        }
        System.out.print("\nChoose the book [id] that you want to change its status\n");
        int favouritePosition = UserInput.getIntBetween(1, posFav) - 1;

        // Select status
        System.out.println("[1] To read");
        System.out.println("[2] Reading");
        System.out.println("[3] Read");
        System.out.print("\nChoose the status [id] to set\n");
        FavouriteStatus newStatus = new FavouriteStatus[] {
                FavouriteStatus.TODO,
                FavouriteStatus.DOING,
                FavouriteStatus.DONE
        }[UserInput.getIntBetween(1,3) - 1];

        var favourite = userDTO.getFavourites().get(favouritePosition);
        favourite.setStatus(newStatus);
        var favouriteUpdated = favouriteRepository.save(favourite);

        System.out.println("\nStatus changed\n");
        showById(favouriteUpdated.getId());

        return FavouriteDTO.fromEntity(favouriteUpdated);
    }

    @Override
    public FavouriteDTO findById(Long id) throws NotFoundException {
        final var favourite = favouriteRepository.findById(id).orElseThrow(() -> new NotFoundException());
        var favouriteDTO = FavouriteDTO.fromEntity(favourite);
        return favouriteDTO;
    }

    @Override
    public void showAll() {
        favouriteRepository.findAll().forEach((Favourite favourite) -> {
            var favouriteDTODTO = FavouriteDTO.fromEntity(favourite);
            System.out.println("\n" + favouriteDTODTO);
        });
    }

    @Override
    public void showById(Long id) {
        try {
            var favouriteDTO = findById(id);
            System.out.println("\n" + favouriteDTO + "\n");

        } catch(NotFoundException error) {
            System.out.println("\n" + error.getMessage() + "\n");
        }
    }

    @Override
    public void removeById(Long id) throws NotFoundException {
        Favourite favToDelete = favouriteRepository.findById(id).orElseThrow(() -> new NotFoundException());
        favouriteRepository.delete(favToDelete);
        System.out.println(favToDelete.getUser().getName()+ " no longer has " + favToDelete.getItem().getTitle() + " assigned.");
    }
}
