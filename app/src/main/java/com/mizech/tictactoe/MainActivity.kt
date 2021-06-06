@file:Suppress("DEPRECATION")

package com.mizech.tictactoe

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.mizech.tictactoe.databinding.ActivityMainBinding

/*
    Todo: https://stackoverflow.com/questions/26893796/how-to-set-emoji-by-unicode-in-a-textview
    Siehe resources, DE, app_name als Beispiel

    Todo: Top-bar ausblenden.
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var gameState = mutableListOf<FeasibleState>()
    private var fieldsUsed = 0
    private val imageViews = mutableListOf<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var i = 0
        while (i < 9) {
            gameState.add(FeasibleState.NOT_SET)
            i++
        }
        // Add the imageViews to the List
        imageViews.add(binding.imageView0)
        imageViews.add(binding.imageView1)
        imageViews.add(binding.imageView2)
        imageViews.add(binding.imageView3)
        imageViews.add(binding.imageView4)
        imageViews.add(binding.imageView5)
        imageViews.add(binding.imageView6)
        imageViews.add(binding.imageView7)
        imageViews.add(binding.imageView8)
        // Iterate the list and attach an Listener to each.
        imageViews.forEach {
            it.setOnClickListener {
                processStateChange(it)
            }
        }

        binding.resetGame.setOnClickListener {
            val snackbar = Snackbar.make(it, getString(R.string.reset_cancel_msg),
                    Snackbar.LENGTH_LONG)
            val dialog = AlertDialog.Builder(this)
            dialog.apply {
                setIcon(R.drawable.ic_baseline_priority_high_24)
                setTitle(getString(R.string.alert_title))
                setMessage(getString(R.string.alert_text))
                setPositiveButton(getString(R.string.alert_yes), DialogInterface.OnClickListener { dialog, which ->
                    resetGameState()
                })
                setNegativeButton(getString(R.string.alert_no), DialogInterface.OnClickListener { dialog, which ->
                    snackbar.show()
                })
            }.show()
        }
    }

    private fun checkGameState(currentPlayer: FeasibleState): Boolean {
        if (gameState[0] === currentPlayer && gameState[1] === currentPlayer
                && gameState[2] === currentPlayer) {
            return true
        }

        if (gameState[3] === currentPlayer && gameState[4] === currentPlayer
                && gameState[5] === currentPlayer) {
            return true
        }

        if (gameState[6] === currentPlayer && gameState[7] === currentPlayer
                && gameState[8] === currentPlayer) {
            return true
        }

        if (gameState[0] === currentPlayer && gameState[3] === currentPlayer
                && gameState[6] === currentPlayer) {
            return true
        }

        if (gameState[1] === currentPlayer && gameState[4] === currentPlayer
                && gameState[7] === currentPlayer) {
            return true
        }

        if (gameState[2] === currentPlayer && gameState[5] === currentPlayer
                && gameState[8] === currentPlayer) {
            return true
        }

        if (gameState[0] === currentPlayer && gameState[4] === currentPlayer
                && gameState[8] === currentPlayer) {
            return true
        }

        if (gameState[2] === currentPlayer && gameState[4] === currentPlayer
                && gameState[6] === currentPlayer) {
            return true
        }

        return false
    }

    private fun processStateChange(it: View) {
        val imgView = it as ImageView

        imgView.setImageResource(R.drawable.player)
        fieldsUsed++
        gameState[imgView.tag.toString().toInt()] = FeasibleState.PLAYER_ONE

        var hasWon = checkResult(FeasibleState.PLAYER_ONE, R.string.player_won_message, "#64FF64")
        if (!hasWon) {
            val handler = Handler()
            handler.postDelayed({
                doComputerMove()
            }, 300)
        }
    }

    private fun doComputerMove() {
        var iRandom = (0..8).random()

        var isNotSet = gameState[iRandom] === FeasibleState.NOT_SET

        while (!isNotSet) {
            iRandom = ++iRandom % gameState.size
            isNotSet = gameState[iRandom] === FeasibleState.NOT_SET
        }

        var imgView = imageViews[iRandom]
        imgView.isEnabled = false
        imgView.setImageResource(R.drawable.computer)
        fieldsUsed++
        gameState[imgView.tag.toString().toInt()] = FeasibleState.COMPUTER

        checkResult(FeasibleState.COMPUTER, R.string.computer_won_message, "#FF6464")
    }

    private fun checkResult(currentPlayer: FeasibleState, message: Int,
                            winnerColor: String): Boolean {
        if (checkGameState(currentPlayer)) {
            setFinalResult(message, winnerColor)
            return true
        } else if (fieldsUsed == 9) {
            setFinalResult(R.string.tie_message, "#ff00ff")
            return true
        }

        return false
    }

    private fun setFinalResult(winnerString: Int, winnerColor: String) {
        imageViews.forEach {
            it.isEnabled = false
        }
        binding.currentMessage.text = getString(winnerString)
        binding.currentMessage.setTextColor(Color.parseColor(winnerColor))
    }

    private fun resetGameState() {
        for (index in 0..8) {
            gameState[index] = FeasibleState.NOT_SET
        }

        imageViews.forEach {
            it.setImageResource(R.drawable.not_set)
            it.isEnabled = true
        }

        binding.currentMessage.text = ""
        fieldsUsed = 0
    }
}