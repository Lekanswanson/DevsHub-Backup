package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

@Controller
@ComponentScan({ "com.test.devshub" })
public class BookController
{
    @Autowired
    private Book book;

    @RequestMapping(path="/")
    public String home(Model model)
    {
        model.addAttribute("updateBook", book);
        model.addAttribute("deleteBook", book);
        model.addAttribute("booksdb", book.booksdb);
        model.addAttribute("addBook", new Book());
        model.addAttribute("Books", Database.getAllBooks());
        return "book";
    }

    @RequestMapping(method = RequestMethod.POST, path="/addBook")
    public String addBook(@ModelAttribute Book book)
    {
        Database.addBook(book);
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/delete")
    public String deleteBook(@RequestParam("id") int id)
    {
        System.out.println(id);
        Database.deleteBook(id);
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/update")
    public String updateBook(@ModelAttribute Book book)
    {
        System.out.println(book);
        Database.updateBook(book);
        return "redirect:/";
    }

}
