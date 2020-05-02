package com.example.belajar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*

class MainAdapter(val homeModel: HomeModel, val showShimmer: Boolean): RecyclerView.Adapter<CustomViewHolder>() {
    var allData: List<DataObject> = emptyList()
    var shimmerItemNumber: Int = 5

    init {
        this.allData = homeModel.data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_view, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return if(showShimmer) this.shimmerItemNumber
        else this.allData.count()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if(showShimmer){
            holder.view.shimmer_layout.startShimmer()
        }
        else{
            holder.view.shimmer_layout.stopShimmer()
            holder.view.shimmer_layout.setShimmer(null)

            val listData = homeModel.data.get(position)

            val fullName = listData.first_name + " " + listData.last_name
            holder.view.user_full_name?.text = fullName
            holder.view.user_full_name?.background = null

            holder.view.user_email?.text = listData.email
            holder.view.user_email?.background = null

            Picasso.get().load(listData.avatar).into(holder.view.user_avatar)
            holder.view.user_avatar?.background = null
        }
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){
}