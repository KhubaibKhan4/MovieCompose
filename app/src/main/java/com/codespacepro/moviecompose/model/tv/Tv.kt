package com.codespacepro.moviecompose.model.tv

data class Tv(
    val page: Int,
    val results: List<ResultTv>,
    val total_pages: Int,
    val total_results: Int
)