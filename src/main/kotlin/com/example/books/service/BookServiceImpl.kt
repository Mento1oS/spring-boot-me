package com.example.books.service

import com.example.books.model.Book
import com.example.books.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class BookServiceImpl(
        private val bookRepository: BookRepository
) : BookService {
    override fun findAll(): List<Book> = bookRepository.findAll()

    override fun findById(id: Long): Optional<Book> = bookRepository.findById(id)

    override fun create(book: Book): Book = bookRepository.save(book)

    override fun update(id: Long, book: Book): Optional<Book> {
        val existing = bookRepository.findById(id)
        return if (existing.isPresent) {
            val e = existing.get()
            e.title = book.title
            e.author = book.author
            e.pubLicationYear = book.pubLicationYear
            e.description = book.description
            Optional.of(bookRepository.save(e))
        } else {
            Optional.empty()
        }
    }

    override fun delete(id: Long): Boolean {
        return if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
