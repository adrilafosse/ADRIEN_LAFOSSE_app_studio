package com.example.exercice1
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.exercice1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonsListener()
        Log.d( "lifeCycle", "MainActivity onCreate")
        }

    private fun buttonsListener() {
        binding.buttonEntree.setOnClickListener {

            showCategory(Category.STARTER)
        }
        binding.buttonPlats.setOnClickListener {
            showCategory(Category.MAIN)
        }
        binding.buttonDessert.setOnClickListener {
            showCategory(Category.DESSERT)
        }
    }
    private fun showCategory(category: Category) {
        val intent = Intent( this, MenuListActivity::class.java)
        intent.putExtra(MenuListActivity.extraKey, category)
        startActivity(intent)
    }
    override fun onStart() {
        super.onStart()
        Log.d( "lifeCycle", "MainActivity onCreate")
    }
    override fun onResume() {
        super.onResume()
    }
    override fun onPause() {
        super.onPause()
        Log.d("lifeCycle", "MainActivity onPause")
    }
    override fun onDestroy() {
        Log.d("lifeCycle", "MainActivity onDestroy")
        super.onDestroy()
    }
}