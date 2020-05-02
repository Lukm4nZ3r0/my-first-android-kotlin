package com.example.belajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    var currentPage: Int = 1
    var totalPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        my_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        fetchJson(1,this)
        prevNextButtonListener()
    }

    private fun prevNextButtonListener(){
        prev_button.setOnClickListener {
            if(currentPage > 1){
                fetchJson(currentPage-1, this)
            }
        }

        next_button.setOnClickListener {
            if(currentPage < totalPage) {
                fetchJson(currentPage+1, this)
            }
        }
    }

    private fun fetchJson(page: Int, mainActivity: MainActivity){
        val url = "https://reqres.in/api/users?page=${page}"
        val request = Request
            .Builder()
            .url(url)
//            .addHeader("Autherization","TOKEN")
            .build()
        val client = OkHttpClient()

        var dummyDataObject: List<DataObject> = emptyList()
        var dummyAddObject = AdObject("","","")
        var dummyHomeModel = HomeModel(0,0,0,0,dummyDataObject,dummyAddObject)

        my_recycler_view.adapter = MainAdapter(dummyHomeModel, true)


        mainActivity.next_button.isEnabled = false
        mainActivity.prev_button.isEnabled = false
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val homeModel = gson.fromJson(body, HomeModel::class.java)

                totalPage = homeModel.total_pages
                currentPage = page

                runOnUiThread{
                    mainActivity.next_button.isEnabled = true
                    mainActivity.prev_button.isEnabled = true
                    my_recycler_view.adapter = MainAdapter(homeModel, false)
                    my_recycler_view.scheduleLayoutAnimation()
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("GAGAL MENDAPATKAN REQUEST")
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(mainActivity)
                runOnUiThread {
                    alertDialog.setTitle("Try to connecting again!")
                    alertDialog.setMessage("Please wait ..!")
                    alertDialog.setNeutralButton("OK!"){ _, _ ->

                    }
                    alertDialog.show()

//                    recursive
                    fetchJson(page, mainActivity)
                }
            }
        })
    }
}

class HomeModel(var page: Int, var per_page: Int, var total: Int, var total_pages: Int, var data: List<DataObject>, var ad: AdObject)

class DataObject(var id: Int, var email: String, var first_name: String, var last_name: String, var avatar: String)

class AdObject(var company: String, var url: String, var text: String)