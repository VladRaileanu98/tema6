package com.db.bookstore.repository;

import com.db.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CookieValue;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUsernameOrEmailAndPassword(String username, String email, String password);
    List<User>findAll();
    User findById(@CookieValue int id);
    User findUserById(int id);

}
