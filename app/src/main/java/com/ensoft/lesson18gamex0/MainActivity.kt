package com.ensoft.lesson18gamex0

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.ensoft.lesson18gamex0.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    /** 0 =red x, 1 = blue 0*/
    var activePlayer = (0 until 2).random()

    var isGameActive = true

    //oynalmagan katak
    var gameState = arrayOf(2,2,2,2,2,2,2,2,2)

    //yutish kataklari
    var winningPositions = arrayOf(
        //gorizontal yonalish
        arrayOf(0,1,2),
        arrayOf(3,4,5),
        arrayOf(6,7,8),
        //vertikal yonalish
        arrayOf(0,3,6),
        arrayOf(1,4,7),
        arrayOf(2,5,8),
        //diagonal yonalish
        arrayOf(0,4,8),
        arrayOf(2,4,6)
    )

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun dropIn(view: View){
        if (isGameActive){
            val counter = view as ImageView
            val tapped = counter.tag.toString().toInt()

            if (gameState[tapped] == 2){
                gameState[tapped] = activePlayer

                counter.rotation = 0f
                counter.translationY = -1000f

                if (activePlayer == 0){
                    counter.setImageResource(R.drawable.ic_x)
                    activePlayer = 1
                }
                else{
                    counter.setImageResource(R.drawable.ic_o)
                    activePlayer =0
                }
                counter.animate().translationYBy(1000f).rotation(360f).duration = 600

                for (winningPosition in winningPositions){
                    if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]]== gameState[winningPosition[2]]&&
                        gameState[winningPosition[0]]!=2){
                        //oyin tugadi
                        isGameActive = false
                        var winner = "Red"
                        if (gameState[winningPosition[0]]==1)
                            winner = "Blue"
                        binding.tvWinner.text = "$winner has won"
                        binding.playAgainLayout.visibility = View.VISIBLE
                    }
                    else{
                        var isGameOver = true

                        //durrang
                        for (counterState in gameState){
                            // agar hali 2 raqami bor bo'lsa hali o'yin yakunlanmagan
                            if (counterState == 2) isGameOver = false
                        }
                        if (isGameOver){
                            binding.tvWinner.text = "Draw!"
                            binding.playAgainLayout.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

    }
    fun playAgain(view: View){
        isGameActive = true
        binding.playAgainLayout.visibility = View.INVISIBLE
        activePlayer = (0 until 2).random()
        for (i in 0..gameState.lastIndex){
            gameState[i] = 2
        }
        for (i in 0 until binding.gridLayout.childCount){
            (binding.gridLayout.getChildAt(i) as ImageView).setImageResource(0)
        }
    }
}