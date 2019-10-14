package com.example.rockpaperscissor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1

    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        gameRepository = GameRepository(this)
        initViews()
    }

    /**
     * Initializes view elements.
     *
     */
    private fun initViews() {
        initListeners()
        updateStats()
    }

    /**
     * Updates the statistics by retrieving the data from the database.
     *
     */
    private fun updateStats() {
        CoroutineScope(Dispatchers.Main).launch {
            val wins = withContext(Dispatchers.IO) {
                gameRepository.getWins()
            }
            val draws = withContext(Dispatchers.IO) {
                gameRepository.getDraws()
            }
            val losses = withContext(Dispatchers.IO) {
                gameRepository.getLosses()
            }
            txtScore.text = getString(
                R.string.wdl,
                wins.toString(),
                draws.toString(),
                losses.toString()
            )
        }
    }

    /**
     * Initializes the onClickListeners.
     *
     */
    private fun initListeners() {
        btnGamePlayed.setOnClickListener {
            startActivity()
        }
        btnRock.setOnClickListener { handleGame(Choice.ROCK) }
        btnPaper.setOnClickListener { handleGame(Choice.PAPER) }
        btnScissors.setOnClickListener { handleGame(Choice.SCISSOR) }
    }

    /**
     * Starts the GameHistoryActivity.
     *
     */
    private fun startActivity() {
        val intent = Intent(this, GamePlayedActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    /**
     * Game handler that will simulate the game and call the necessary methods.
     *
     * @param gesture Action that the user has chosen.
     */
    private fun handleGame(choice: Choice) {
        val computerAction = (0..3).shuffled().first()
        val computerGesture = assignChoice(computerAction)
        var game = Game(
            null,
            "",
            Date(),
            choice,
            computerGesture
        )

        game = calculateWinner(game)
        insertGameIntoDatabase(game)
        displayGameResults(game)
        updateStats()
    }

    /**
     * Will insert the game into the database.
     *
     * @param game Game to be inserted.
     */
    private fun insertGameIntoDatabase(game: Game) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }
    }

    /**
     * Will assign a gesture to the given value.
     *
     * @param value To be assigned.
     * @return The assigned gesture.
     */
    private fun assignChoice(value: Int): Choice {
        return when (value) {
            0 -> Choice.ROCK
            1 -> Choice.PAPER
            3 -> Choice.SCISSOR
            else -> Choice.ROCK
        }
    }

    /**
     * Will check who the winner is according to the basic game rules.
     *
     * @param game Game that needs the winner calculation.
     * @return Updated game with winner property set.
     */
    private fun calculateWinner(game: Game): Game {
        game.result = when {
            game.computerChoice == game.humanChoice -> getString(R.string.draw)
            game.computerChoice == Choice.ROCK && game.humanChoice == Choice.SCISSOR -> getString(R.string.computerWin)
            game.computerChoice == Choice.SCISSOR && game.humanChoice == Choice.PAPER -> getString(
                R.string.computerWin
            )
            game.computerChoice == Choice.PAPER && game.humanChoice == Choice.ROCK -> getString(R.string.computerWin)
            else -> getString(R.string.youWin)
        }
        return game
    }

    /**
     * Method that will display the game results.
     *
     * @param game Game to be displayed.
     */
    private fun displayGameResults(game: Game) {
        txtResult.text = game.result
        ComputerChoice.setImageDrawable(getDrawable(game.computerChoice.drawableId))
        humanChoice.setImageDrawable(getDrawable(game.humanChoice.drawableId))
    }

    /**
     * Will make sure the stats will be updated and the previous game won't be visible but a new
     * game can be started.
     *
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        updateStats()
        resetGameResults()
    }

    /**
     * Resets the current game view elements.
     *
     */
    private fun resetGameResults() {
        txtResult.text = getString(R.string.winnerMessage)
        ComputerChoice.setImageDrawable(getDrawable(Choice.PAPER.drawableId))
        humanChoice.setImageDrawable(getDrawable(Choice.PAPER.drawableId))
    }
}