package com.db.bookstore.controller;

import com.db.bookstore.model.Book;
import com.db.bookstore.model.User;
import com.db.bookstore.service.BookService;
import com.db.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

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
    public ModelAndView getDashboardForm(HttpServletRequest request){
        /*
        Cookie[] cookies = request.getCookies();
        String ids = String.valueOf(Arrays.stream(request.getCookies())
                .map(Cookie::getValue)
                .findAny()).toString();
        System.out.println(ids);*/
        //int cookieValue = Integer.parseInt(ids);
        ModelAndView modelAndView = new ModelAndView("dashboard-form");
        int cookieValue = 3;//doar pentru a proba dashboard-ul
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



    @GetMapping("/errors")
    public ModelAndView getErrorView(){
        ModelAndView modelAndView = new ModelAndView("errors-form");
        return modelAndView;
    }


}
