package com.db.bookstore.service;

import com.db.bookstore.model.User;
import com.db.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void insertUser(User user){
        user.setRole("client");
        userRepository.save(user);
    }


    public List<User> getUsers(){
        return userRepository.findAll();
    }


    public User getUserById(int id){
        return userRepository.findUserById(id);
    }

    public User findByUsernameOrEmailAndPassword(User user) throws Exception {
       List<User> listUser =
               userRepository.findByUsernameOrEmailAndPassword(user.getUsername(), user.getEmail(), user.getPassword());
       if(listUser.size()==0){
           throw new Exception("no user found");
       }
       if(listUser.size()==1){
           return listUser.get(0);
       }
       if(listUser.size()>1){
           throw  new Exception("duplicate users error");
       }

        return null;
    }

}
