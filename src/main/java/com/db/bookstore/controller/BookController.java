package com.db.bookstore.controller;

import com.db.bookstore.model.Book;
import com.db.bookstore.service.AuthorService;
import com.db.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    AuthorService authorService;

    @GetMapping("/add")
    public ModelAndView getAddView(){
        ModelAndView modelAndView = new ModelAndView("add-form");
        modelAndView.addObject("authorList",authorService.getAuthors());
        //modelAndView.addObject("bookList", bookService.getBooks());
        return modelAndView;
    }
    @PostMapping("/add")
    public ModelAndView addBook(Book book){
        bookService.insertBook(book);
        ModelAndView modelAndView = new ModelAndView("redirect:/book");
        return modelAndView;
    }
    @GetMapping("/book")
    public ModelAndView getRegisterForm(){
        ModelAndView modelAndView= new ModelAndView("book-form");
        modelAndView.addObject("bookList", bookService.getBooks());
        return modelAndView;
    }
}
