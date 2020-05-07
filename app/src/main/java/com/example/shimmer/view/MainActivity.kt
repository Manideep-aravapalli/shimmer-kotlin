package com.example.shimmer.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shimmer.R
import com.example.shimmer.model.adapter.MainAdapter
import com.example.shimmer.model.dto.Data
import com.example.shimmer.model.service.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    //    var dataLists: List<Data> = List<Data>()
    private var dataList: List<Data>? = null
    private var mainAdapter: MainAdapter? = null
    private var context: Context? = null
    private var list_data: RecyclerView? = null
    var manager: LinearLayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list_data = findViewById<View>(R.id.recyclerView) as RecyclerView
        manager = LinearLayoutManager(context)
        list_data!!.setLayoutManager(manager)
        setupAPICall()
    }

    private fun setupAPICall() {
        var call: Call<List<Data>> = RetrofitClient
            .getInstance()
            .apiService()
            .getName()

        call.enqueue(object : Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                shimmerFrameLayout.stopShimmerAnimation()
                shimmerFrameLayout.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                dataList = response.body()
                list_data?.adapter = dataList?.let {
                    MainAdapter(
                        dataList!!,
                        applicationContext
                    )
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                shimmerFrameLayout.visibility = View.GONE
                Log.e("------------", t.toString())
                Toast.makeText(this@MainActivity, "Something Went Wrong", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }
}
