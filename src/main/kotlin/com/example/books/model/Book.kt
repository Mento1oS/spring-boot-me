package com.example.books.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "books")
class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @field:NotBlank(message = "title must not be blank")
        @Column(nullable = false)
        var title: String,

        @field:NotBlank(message = "author must not be blank")
        @Column(nullable = false)
        var author: String,

        var pubLicationYear: Int? = null,

        @Column(columnDefinition = "TEXT")
        var description: String? = null
)
