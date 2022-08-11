package com.db.bookstore.controller;

import com.db.bookstore.model.Book;
import com.db.bookstore.model.User;
import com.db.bookstore.service.BookService;
import com.db.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;

    @GetMapping("/register")
    public ModelAndView getRegisterForm(){
        ModelAndView modelAndView= new ModelAndView("register-form");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView addUser(User user){
       userService.insertUser(user);
       ModelAndView modelAndView = new ModelAndView("redirect:/login");
       return modelAndView;
    }


    @GetMapping("/login")
    public ModelAndView getLoginForm(){
        ModelAndView modelAndView= new ModelAndView("login-form");
        return modelAndView;
    }
    @PostMapping("/login")
    public ModelAndView verifyUser(User user, HttpServletResponse response){
        try {
            User userLoggedIn = userService.findByUsernameOrEmailAndPassword(user);
            response.addCookie(new Cookie("id", ""+userLoggedIn.getId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");

        return modelAndView;
    }

    @GetMapping("/dashboard")
    public ModelAndView getDashboardForm(){
        ModelAndView modelAndView = new ModelAndView("dashboard-form");
        int cookieValue = 3; //doar pentru a proba dashboard-ul
        modelAndView.addObject("user",userService.getUserById(cookieValue));
        modelAndView.addObject("bookList", bookService.getBooks());
        return modelAndView;
    }
    @PostMapping("/dashboard")
    public ModelAndView verifyUserForAdd(User user){
        if(user.getRole() != "admin"){
            ModelAndView modelAndView = new ModelAndView("redirect:/add");
            return modelAndView;
        }else{
            ModelAndView modelAndView = new ModelAndView("redirect:/errors");
            return modelAndView;
        }
    }

    @GetMapping("/add")
    public ModelAndView getAddView(){
        ModelAndView modelAndView = new ModelAndView("add-form");
        return modelAndView;
    }
    @PostMapping("/add")
    public ModelAndView addBook(Book book){
        bookService.insertBook(book);
        ModelAndView modelAndView = new ModelAndView("redirect:/login");
        return modelAndView;
    }

    @GetMapping("/errors")
    public ModelAndView getErrorView(){
        ModelAndView modelAndView = new ModelAndView("errors-form");
        return modelAndView;
    }


}
