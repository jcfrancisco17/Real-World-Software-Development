package com.iteratrlearning.shu_book.kotlin.chapter_04

import java.util.function.Predicate

class Query private constructor(val clauses: Map<String, String>) : Predicate<Document> {

    companion object {
        /**
         * Parses the query string to a [Map] of attribute key and query value.
         *
         * Example:
         *
         *  ```
         *  "key1:query1,key2:query2" -> {key1=query1, key2=query2}
         *  ```
         */
        fun parse(query: String): Query {
            val clauses =
                query                                             // "a:b,c:d"
                    .split(",")                         // ["a:b", "c:d"]
                    .map { str -> str.split(":") }      // [["a","b"],["c","d"]]
                    .associate { it[0] to it[1] }                 // {a=b,c=d}
            return Query(clauses)
        }
    }

    /**
     * Tests if the [Document] contains all the clauses.
     *
     * @return true only if all the clauses are found in the [Document].
     */
    override fun test(document: Document): Boolean {
        return clauses.all { (key, queryValue) ->
            document.getAttribute(key)?.contains(queryValue) ?: false
        }
    }

}
