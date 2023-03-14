package com.example.slotmachine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.slotmachine.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    //using lazy init.
    private val btnBet5 by lazy { binding.btnBet5}
    private val btnBet10 by lazy { binding.btnBet10}
    private val topDisplay by lazy {binding.TopDisplay}
    private val betDisplay by lazy {binding.BetDisplay}
    private val ImageViews by lazy {
        listOf<ImageView>(binding.img1, binding.img2, binding.img3)
    }
    private lateinit var images: IntArray
    private lateinit var message:String
    private  var betCoins: Int = 10
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // initImageView()
        message = savedInstanceState?.getString(MESSAGE) ?: "Show that APPLE"
        //if it is null/empty return 3 empty images.
        images = savedInstanceState?.getIntArray(IMAGES)
            ?: intArrayOf(R.drawable.empty, R.drawable.empty,R.drawable.empty)
        betCoins = savedInstanceState?.getInt(betCoins.toString()) ?: 10

        DisplayRandomImage()
        displayMessage()
        displayScore()

        btnBet5.setOnClickListener{
            drawSlotMachine()
            var applesCount = getAppleCount()
            if(applesCount == 0){
                betCoins -= 5
                displayScore()
            }
            else if(applesCount == 1){
                betCoins += 5
                displayScore()
            }
            else if(applesCount == 2){
                betCoins += 10
                displayScore()
            }
            else if(applesCount == 3){
                betCoins += 15
                displayScore()
            }

            if(betCoins === 0){
                btnBet5.isEnabled = false
                btnBet10.isEnabled = false
                betDisplay.text = "You Lose!"
                Snackbar.make(it, "You don't have enough coins", Snackbar.LENGTH_SHORT).show()
            }
        }

        btnBet10.setOnClickListener{
            drawSlotMachine()
            var applesCount = getAppleCount()

            if(applesCount == 0){
                betCoins -= 10
                displayScore()
            }
            else if(applesCount == 1){
                betCoins += 10
                displayScore()
            }
            else if(applesCount == 2){
                betCoins += 20
                displayScore()
            }
            else if(applesCount == 3){
                betCoins += 35
                displayScore()
            }

            if(betCoins === 0){
                btnBet5.isEnabled = false
                btnBet10.isEnabled = false
                betDisplay.text = "You Lose!"
                Snackbar.make(it, "You don't have enough coins", Snackbar.LENGTH_SHORT).show()
            }
        }
        Log.i(SHOW_IMAGE_TAG,"onCreate Fires!")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //save state
        outState.putString(MESSAGE,message)
        outState.putIntArray(IMAGES,images)
        outState.putInt(SCORE.toString(),betCoins)
        super.onSaveInstanceState(outState)
    }

    private fun drawSlotMachine() {
        //randomly display images.
        SetRandomImages()
        DisplayRandomImage()
        displayMessage()
        displayScore()
    }

    private fun DisplayRandomImage() {
        for (i in 0 until images.size) {
            ImageViews[i].setImageResource(images[i])
        }
    }

    private fun randomImage():Int{
        val r = Random.nextInt(3)
        return when (r){
            0 -> R.drawable.apple
            1 -> R.drawable.grapes
            2 -> R.drawable.orange
            else -> R.drawable.empty
        }
    }

    private fun SetRandomImages(){
        images = intArrayOf(randomImage(),randomImage(),randomImage())
    }

    private fun initImageView() {
       for(i in ImageViews) i.setImageResource(R.drawable.empty)
    }

    private fun displayMessage(){
        when(getAppleCount())
        {
            1 -> message = "Nice one..."
            2 -> message = "Good for Two"
            3 -> message = "Lucky Three!"
            else -> message = "Show that APPLE"
        }
        topDisplay.text = message
    }

    private fun getAppleCount():Int{
        var countApple = 0
        for(i in images)
        {
            if(R.drawable.apple == i) countApple++
        }
       return countApple
    }

    private fun displayScore(){
        betDisplay.text = "You have $betCoins coins"
    }


// UI State

    override fun onStart() {
        super.onStart()
        Log.i(SHOW_IMAGE_TAG,"onStart Fires!")
    }

    override fun onResume() {
        super.onResume()
        Log.i(SHOW_IMAGE_TAG,"onResume Fires!")
    }

    override fun onPause() {
        super.onPause()
        Log.i(SHOW_IMAGE_TAG,"onPause Fires!")
    }

    override fun onStop() {
        super.onStop()
        Log.i(SHOW_IMAGE_TAG,"onStop Fires!")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(SHOW_IMAGE_TAG,"onDestroy Fires!")

    }

}
