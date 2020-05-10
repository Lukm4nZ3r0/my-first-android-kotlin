package com.example.belajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.activity_second.next_button
import kotlinx.android.synthetic.main.activity_second.prev_button
import okhttp3.*
import java.io.IOException

class SecondActivity : AppCompatActivity() {
    var currentPage: Int = 1
    var totalPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        title_page.text = "Your Text Here !"
        subtitle_page.text = intent.getStringExtra("MESSAGE_KEY")

        item_does_recycle_view.layoutManager = LinearLayoutManager(this)
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

    private fun fetchJson(page: Int, thisActivity: SecondActivity){
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

        item_does_recycle_view.adapter = SecondAdapter(dummyHomeModel, true)

        thisActivity.next_button.isEnabled = false
        thisActivity.prev_button.isEnabled = false
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val homeModel = gson.fromJson(body, HomeModel::class.java)

                totalPage = homeModel.total_pages
                currentPage = page

                runOnUiThread{
                    thisActivity.next_button.isEnabled = true
                    thisActivity.prev_button.isEnabled = true
                    item_does_recycle_view.adapter = SecondAdapter(homeModel, false)
                    item_does_recycle_view.scheduleLayoutAnimation()
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("GAGAL MENDAPATKAN REQUEST")
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(thisActivity)
                runOnUiThread {
                    alertDialog.setTitle("Try to connecting again!")
                    alertDialog.setMessage("Please wait ..!")
                    alertDialog.setNeutralButton("OK!"){ _, _ ->

                    }
                    alertDialog.show()

//                    recursive
                    fetchJson(page, thisActivity)
                }
            }
        })
    }
}
