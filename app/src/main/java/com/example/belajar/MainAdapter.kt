package com.example.belajar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*

class MainAdapter(val homeModel: HomeModel): RecyclerView.Adapter<CustomViewHolder>() {
    override fun getItemCount(): Int {
        return homeModel.data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_view, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val listData = homeModel.data.get(position)
        holder.view.user_full_name?.text = listData.first_name + " " + listData.last_name + "\n" + listData.email
        Picasso.get().load(listData.avatar).into(holder.view.user_avatar)
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}