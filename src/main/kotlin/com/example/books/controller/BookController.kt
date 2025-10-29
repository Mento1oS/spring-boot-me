package com.example.books.controller

import com.example.books.model.Book
import com.example.books.service.BookService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*
import kotlin.streams.toList

@RestController
@RequestMapping("/api/books")
class BookController(private val bookService: BookService) {

    @GetMapping
    fun getAll(): ResponseEntity<List<Book>> {
        val list = bookService.findAll()
        return ResponseEntity.ok(list)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Book> {
        val bookOpt = bookService.findById(id)
        return bookOpt.map { ResponseEntity.ok(it) }.orElseGet { ResponseEntity.notFound().build() }
    }

    @PostMapping
    fun create(@Valid @RequestBody book: Book): ResponseEntity<Book> {
        val saved = bookService.create(book)
        val location = URI.create("/api/books/${saved.id}")
        return ResponseEntity.created(location).body(saved)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody book: Book): ResponseEntity<Book> {
        val updated = bookService.update(id, book)
        return updated.map { ResponseEntity.ok(it) }.orElseGet { ResponseEntity.notFound().build() }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        val deleted = bookService.delete(id)
        return if (deleted) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
    }

    // Validation error handler -> return 400 with field messages
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String?>> {
        val errors: MutableMap<String, String?> = LinkedHashMap()
        ex.bindingResult.fieldErrors.forEach { fieldError: FieldError ->
            errors[fieldError.field] = fieldError.defaultMessage
        }
        return ResponseEntity.badRequest().body(errors)
    }
}
