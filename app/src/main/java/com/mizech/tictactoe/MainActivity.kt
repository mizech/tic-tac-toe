package com.mizech.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.mizech.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val TAG = this::class.java.name
    private lateinit var binding: ActivityMainBinding

    var gameState = mutableListOf<FeasibleState>()
    var isPlayerOne = true
    val imageViews = mutableListOf<ImageView>()

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

        imageViews.add(binding.imageView0)
        imageViews.add(binding.imageView1)
        imageViews.add(binding.imageView2)
        imageViews.add(binding.imageView3)
        imageViews.add(binding.imageView4)
        imageViews.add(binding.imageView5)
        imageViews.add(binding.imageView6)
        imageViews.add(binding.imageView7)
        imageViews.add(binding.imageView8)

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

    fun checkGameState(currentPlayer: FeasibleState): Boolean {
        if (gameState.get(0) === currentPlayer && gameState.get(1) === currentPlayer && gameState.get(2) === currentPlayer) {
            return true
        }

        if (gameState.get(3) === currentPlayer && gameState.get(4) === currentPlayer && gameState.get(5) === currentPlayer) {
            return true
        }

        if (gameState.get(6) === currentPlayer && gameState.get(7) === currentPlayer && gameState.get(8) === currentPlayer) {
            return true
        }

        if (gameState.get(0) === currentPlayer && gameState.get(3) === currentPlayer && gameState.get(6) === currentPlayer) {
            return true
        }

        if (gameState.get(1) === currentPlayer && gameState.get(4) === currentPlayer && gameState.get(7) === currentPlayer) {
            return true
        }

        if (gameState.get(2) === currentPlayer && gameState.get(5) === currentPlayer && gameState.get(8) === currentPlayer) {
            return true
        }

        if (gameState.get(0) === currentPlayer && gameState.get(4) === currentPlayer && gameState.get(8) === currentPlayer) {
            return true
        }

        if (gameState.get(5) === currentPlayer && gameState.get(4) === currentPlayer && gameState.get(2) === currentPlayer) {
            return true
        }

        return false
    }

    fun processStateChange(it: View) {
        val imgView = it as ImageView
        if (isPlayerOne) {
            imgView.setImageResource(R.drawable.player_one)
            gameState.set(imgView.tag.toString().toInt(), FeasibleState.PLAYER_ONE)
            if (checkGameState(FeasibleState.PLAYER_ONE) == true) {
                binding.currentMessage.text = "Player One has won"
            }
        } else {
            imgView.setImageResource(R.drawable.player_two)
            gameState.set(imgView.tag.toString().toInt(), FeasibleState.PLAYER_TWO)
            if (checkGameState(FeasibleState.PLAYER_TWO) == true) {
                binding.currentMessage.text = "Player Two has won"
            }
        }

        isPlayerOne = !isPlayerOne
        imgView.isEnabled = false
    }
}