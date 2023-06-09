package com.example.slotmachine
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.example.slotmachine.databinding.ActivityGameBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar
import kotlin.random.Random
class GameActivity : AppCompatActivity() {
    private val btnBet5 by lazy { binding.btnBet5}
    private val btnBet10 by lazy { binding.btnBet10}
    private val topDisplay by lazy {binding.TopDisplay}
    private val betDisplay by lazy {binding.BetDisplay}
    private val ImageViews by lazy {
        listOf<ImageView>(binding.img1, binding.img2, binding.img3)
    }
    private lateinit var images: IntArray
    private lateinit var message:String
    private lateinit var binding: ActivityGameBinding
    private var betCoins: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            betCoins = savedInstanceState.getInt("betCoins")
        } else {
            val extras = intent.extras ?: return
            val qString = extras.getString("qString")
            betDisplay.text = "You have ${qString} coins"
            betCoins = qString?.toIntOrNull() ?: 0
        }

        message = savedInstanceState?.getString(MESSAGE) ?: "Show that APPLE"
        images = savedInstanceState?.getIntArray(IMAGES)
            ?: intArrayOf(R.drawable.empty, R.drawable.empty,R.drawable.empty)

        DisplayRandomImage()
        displayMessage()

        btnBet5.setOnClickListener{
            drawSlotMachine()
            var applesCount = getAppleCount()
            if(applesCount == 0){
                betCoins -= 5
                displayResults()
            }
            else if(applesCount == 1){
                betCoins += 5
                displayResults()

            }
            else if(applesCount == 2){
                betCoins += 10
                displayResults()

            }
            else if(applesCount == 3){
                betCoins += 15
                displayResults()

            }

            if(betCoins <= 0){
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
                displayResults()
            }
            else if(applesCount == 1){
                betCoins += 10
                displayResults()
            }
            else if(applesCount == 2){
                betCoins += 20
                displayResults()
            }
            else if(applesCount == 3){
                betCoins += 30
                displayResults()
            }

            if(betCoins <= 0){
                btnBet5.isEnabled = false
                btnBet10.isEnabled = false
                betDisplay.text = "You Lose!"
                Snackbar.make(it, "You don't have enough coins", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        //save state
        outState.putString(MESSAGE,message)
        outState.putIntArray(IMAGES,images)
        outState.putInt("betCoins", betCoins)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        betCoins = savedInstanceState.getInt("betCoins")
        betDisplay.text = "You have ${betCoins} coins"
    }

    override fun finish() {
        val returnIntent = Intent()
        returnIntent.putExtra("betCoins", betCoins)
        setResult(Activity.RESULT_OK, returnIntent)
        super.finish()
    }

    // menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game,menu)
        return super.onCreateOptionsMenu(menu)
    }// https://youtu.be/VzUjz9xdVDI?t=1098
    //menu function.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.itemshare){
            val intent = Intent().apply{
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,"Lucky Apple Slot Machine: \n" +
                        "You Draw: ${getAppleCount().toString()} apple(s) \n" +
                        "on ${Calendar.getInstance().time}")
                type="text/plain"
            }
            startActivity(intent)
            Snackbar.make(binding.root, "Message Sent", Snackbar.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun drawSlotMachine() {
        SetRandomImages()
        DisplayRandomImage()
        displayMessage()
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
            else -> message = "Show that APPLE!"
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

    private fun displayResults(){
        betDisplay.text = "You have ${betCoins} coins"
    }

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

