package com.iteratrlearning.shu_book.kotlin.chapter_04

class Document(attributes: Map<String, String>) {

    /*
        In the original Document Java class, there's no getter for the attribute. To achieve this,
        val was not used in the constructor. This does not generate a backing-field with getter.
     */
    private val _attribute = attributes

    fun getAttribute(attributeName: String): String? {
        return _attribute[attributeName]
    }

}
