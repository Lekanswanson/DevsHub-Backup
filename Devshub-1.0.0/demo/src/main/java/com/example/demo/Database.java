package com.example.demo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class Database
{
    static List<Book> books = new ArrayList<Book>();

    static
    {
        Book one = new Book(1, "Pirates", "Chandler");
        Book two = new Book(2, "Stormbreaker", "Alex");
        Book three = new Book(3, "WarHorse", "Morpugo");

        books.add(one);
        books.add(two);
        books.add(three);
    }

    public static List<Book> getAllBooks()
    {
        return books;
    }

    public static void addBook(Book book)
    {
        books.add(book);
    }

    public static void deleteBook(int id)
    {
        Book deleteBook=null;
        for(Book book : books)
        {
            if(book.getId()==id)
            {
                System.out.println(book.toString() + " " + id);
                deleteBook=book;
            }
        }
        books.remove(deleteBook);
    }

    public static void updateBook(Book book)
    {
        for(Book b : books)
        {
            if(b.getId()==book.getId())
            {
                if(!book.getAuthor().isEmpty())
                    b.setAuthor(book.getAuthor());
                if(!book.getTitle().isEmpty())
                    b.setTitle(book.getTitle());
            }
        }
    }
}
