package com.ironhack.bookmark_app.repository;

import com.ironhack.bookmark_app.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {


}
