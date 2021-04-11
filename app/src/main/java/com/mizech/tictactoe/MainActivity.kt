package com.mizech.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.mizech.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var gameState = mutableListOf<FeasibleState>()
    private var isPlayerOne = true
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
            gameState.clear()
            imageViews.forEach {
                it.setImageResource(R.drawable.not_set)
                it.isEnabled = true
                isPlayerOne = true
            }
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

        if (gameState[5] === currentPlayer && gameState[4] === currentPlayer
                && gameState[2] === currentPlayer) {
            return true
        }

        return false
    }

    private fun processStateChange(it: View) {
        val imgView = it as ImageView
        if (isPlayerOne) {
            imgView.setImageResource(R.drawable.player_one)
            gameState[imgView.tag.toString().toInt()] = FeasibleState.PLAYER_ONE
            if (checkGameState(FeasibleState.PLAYER_ONE)) {
                binding.currentMessage.text = getString(R.string.one_won_message)
                binding.currentMessage.setTextColor(Color.parseColor("#64FF64"))
            }
        } else {
            imgView.setImageResource(R.drawable.player_two)
            gameState[imgView.tag.toString().toInt()] = FeasibleState.PLAYER_TWO
            if (checkGameState(FeasibleState.PLAYER_TWO)) {
                binding.currentMessage.text = getString(R.string.two_one_message)
                binding.currentMessage.setTextColor(Color.parseColor("#FF6464"))
            }
        }

        isPlayerOne = !isPlayerOne
        imgView.isEnabled = false
    }
}