package com.codespacepro.moviecompose.model.person

data class Person(
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)