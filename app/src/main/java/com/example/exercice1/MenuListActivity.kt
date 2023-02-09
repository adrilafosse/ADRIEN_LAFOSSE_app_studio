package com.example.exercice1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request.Method
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.exercice1.databinding.ActivityMenuListBinding
import com.google.gson.GsonBuilder
import org.json.JSONObject
import com.example.exercice1.network.NetworkConstants
import com.example.exercice1.network.MenuResult


enum class Category { STARTER, MAIN, DESSERT }

class MenuListActivity : AppCompatActivity() {

    companion object {
        val extraKey = "extraKey"
    }

    lateinit var binding: ActivityMenuListBinding
    lateinit var currentCategory: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = intent.getSerializableExtra(extraKey) as? Category
        currentCategory = category ?: Category.STARTER
        supportActionBar?.title = categoryName()

        makeRequest()
    }
    private fun categoryName(): String {
        return when(currentCategory) {
            Category.STARTER -> getString(R.string.starter)
            Category.MAIN -> getString(R.string.main)
            Category.DESSERT -> getString(R.string.finish)
        }
    }

    private fun showDatas(category: com.example.exercice1.network.Category) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CustomAdapter(category.items) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.PLATE_EXTRA, it)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d( "lifeCycle", "MenuListActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifeCycle", "MenuListActivity onResume")
    }

    override fun onDestroy() {
        Log.d("lifeCycle", "MainActivity onDestroy")
        super.onDestroy()
    }
    override fun onPause() {
        super.onPause()
        Log.d("lifeCycle", "MainActivity onPause")
    }

    private fun parseData(data: String) {
        val result = GsonBuilder().create().fromJson(data, MenuResult::class.java)
        val category = result.data.first { it.name == categoryFilterKey() }
        showDatas(category)
    }

    private fun makeRequest() {
        val queue = Volley.newRequestQueue(this)
        val params = JSONObject()
        params.put(NetworkConstants.idShopKey, 1)
        val request = JsonObjectRequest(
            Method.POST,
            NetworkConstants.url,
            params,
            { result ->
                // Success of request
                Log.d("request", result.toString(2))
                parseData(result.toString())
            },
            { error ->
                // Error when request
                Log.e("request", error.toString())
            }
        )
        queue.add(request)
        //showDatas()
    }
    private fun categoryFilterKey(): String {
        return when(currentCategory) {
            Category.STARTER -> "Entrées"
            Category.MAIN -> "Plats"
            Category.DESSERT -> "Desserts"
        }
    }
}