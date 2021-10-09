package com.iteratrlearning.shu_book.kotlin.chapter_04

import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.HEIGHT
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.PATH
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.TYPE
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.WIDTH
import java.io.File
import javax.imageio.ImageIO

class ImageImporter : Importer {
    override fun importFile(file: File): Document {
        val attributes = mutableMapOf<String, String>()
        attributes[PATH] = file.path

        val image = ImageIO.read(file)
        attributes[WIDTH] = image.width.toString()
        attributes[HEIGHT] = image.height.toString()
        attributes[TYPE] = "IMAGE"

        return Document(attributes)
    }

}
