package ru.dogapp.database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens: Table("tokens") {
    private val id = Tokens.varchar("id", 50)
    private val login = Tokens.varchar("login", 25)
    private val token = Tokens.varchar("token",50)

    fun insert(tokenDTO: TokenDTO){
        transaction {
            Tokens.insert {
                it [id] = tokenDTO.rowId
                it [login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }

    fun fetchTokens(token: String): TokenDTO? {
        return try {
            transaction {
                val tokenModel = Tokens.select{ Tokens.token.eq(token)}.single()
                TokenDTO(
                    rowId = tokenModel[Tokens.id],
                    token = tokenModel[Tokens.token],
                    login = tokenModel[Tokens.login]
                )
//                Tokens.selectAll().toList()
//                    .map {
//                        TokenDTO(
//                            rowId = it[Tokens.id],
//                            token = it[Tokens.token],
//                            login = it[Tokens.login]
//                        )
//                    }
            }
        } catch (e: Exception) {
            null
        }
    }
}