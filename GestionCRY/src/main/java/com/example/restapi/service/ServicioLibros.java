package com.example.restapi.service;

import com.example.restapi.model.Libro;
import com.example.restapi.repository.RepositorioLibros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioLibros {

    private final RepositorioLibros repositorioLibros;

    @Autowired
    public BookService(RepositorioLibros repositorioLibros) {
        this.repositorioLibros = repositorioLibros;
    }

    public List<Book> getAllBooks() {
        return repositorioLibros.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return repositorioLibros.findById(id);
    }

    public Book createBook(Book book) {
        return repositorioLibros.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Optional<Book> optionalBook = repositorioLibros.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            return repositorioLibros.save(book);
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    public void deleteBook(Long id) {
        if (repositorioLibros.existsById(id)) {
            repositorioLibros.deleteById(id);
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }
}