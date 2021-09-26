package com.iteratrlearning.shu_book.kotlin.chapter_03

class HtmlExporter : Exporter {
    override fun export(summaryStatistics: SummaryStatistics): String {
        return """
            <!doctype html>
            <html lang='en'>
                <head>         
                    <title>Bank Transaction Report</title>
                </head>
                <body>
                    <ul>
                        <li><strong>The sum is</strong>: ${summaryStatistics.sum} </li>
                        <li><strong>The average is</strong>: ${summaryStatistics.average} </li>
                        <li><strong>The max is</strong>: ${summaryStatistics.max} </li>
                        <li><strong>The min is</strong>: ${summaryStatistics.min} </li>
                    </ul>
                </body>
            </html>
        """.trimIndent()
    }
}