package ru.dogapp.database.topics

import org.jetbrains.exposed.sql.Table

object Topics: Table("topics") {
    private val id = Topics.varchar("id", 50)
    private val created_by = Topics.varchar("created_by", 25)
    private val title = Topics.varchar("title", 100)
    private val firstmassage = Topics.varchar("firstmassage", 500)
    private val date = Topics.varchar("date", 15)

}