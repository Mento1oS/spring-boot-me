package com.example.books.service

import com.example.books.model.Book
import java.util.*

interface BookService {
    fun findAll(): List<Book>
    fun findById(id: Long): Optional<Book>
    fun create(book: Book): Book
    fun update(id: Long, book: Book): Optional<Book>
    fun delete(id: Long): Boolean
}
