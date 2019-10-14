package com.example.rockpaperscissor

import android.content.Context

class GameRepository(context: Context) {

    private var gameDao: GameDAO

    init {
        val reminderRoomDatabase = AppDatabase.getDatabase(context)
        gameDao = reminderRoomDatabase!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> {
        return gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }

    suspend fun getWins(): Int {
        return gameDao.getWins()
    }

    suspend fun getDraws(): Int {
        return gameDao.getDraws()
    }

    suspend fun getLosses(): Int {
        return gameDao.getLosses()
    }

}