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
import java.util.HashMap;
import java.util.Map;
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
    public ModelAndView verifyUser(User user, HttpServletResponse response) throws Exception {

        User userLoggedIn = userService.findByUsernameOrEmailAndPassword(user);
        response.addCookie(new Cookie("id", ""+userLoggedIn.getId()));

        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");

        return modelAndView;
    }

    @GetMapping("/dashboard")
    public ModelAndView getDashboardForm(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Map<String, Cookie> cookieMap = new HashMap<>();
        for(Cookie cookie: cookies){
            cookieMap.put(cookie.getName(), cookie);
        }
        Cookie firstRequiredCookie = cookieMap.get("id");
        int cookieValue = Integer.parseInt(firstRequiredCookie.getValue());//doar pentru a proba dashboard-ul
        System.out.println("the cookie value is: "+firstRequiredCookie.getValue());

        ModelAndView modelAndView = new ModelAndView("dashboard-form");

        modelAndView.addObject("user", userService.getUserById(cookieValue).getRole());
        modelAndView.addObject("user",userService.getUserById(cookieValue));
        modelAndView.addObject("bookList", bookService.getBooks());
        return modelAndView;
    }
    @PostMapping("/dashboard")
    public ModelAndView verifyUserForAdd(User user, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Map<String, Cookie> cookieMap = new HashMap<>();
        for(Cookie cookie: cookies){
            cookieMap.put(cookie.getName(), cookie);
        }
        Cookie firstRequiredCookie = cookieMap.get("id");
        int cookieValue = Integer.parseInt(firstRequiredCookie.getValue());//doar pentru a proba dashboard-ul
        System.out.println("the cookie value is: "+firstRequiredCookie.getValue());

        if(userService.getUserById(cookieValue).getRole().equals("admin")==true){
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
