package com.example.rockpaperscissor

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDAO {
    @Query("SELECT * FROM rockPaperScissorsTable ORDER BY date DESC")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Query("DELETE FROM rockPaperScissorsTable")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT() FROM rockPaperScissorsTable WHERE result = 'You win!'")
    suspend fun getWins(): Int

    @Query("SELECT COUNT() FROM rockPaperScissorsTable WHERE result = 'Draw'")
    suspend fun getDraws(): Int

    @Query("SELECT COUNT() FROM rockPaperScissorsTable WHERE result = 'Computer wins!'")
    suspend fun getLosses(): Int
}