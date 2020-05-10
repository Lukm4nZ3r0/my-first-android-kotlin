package com.example.belajar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_does.view.*

class SecondAdapter(val homeModel: HomeModel, val showShimmer: Boolean): RecyclerView.Adapter<CustomSecondViewHolder>() {
    var allData: List<DataObject> = emptyList()
    var shimmerItemNumber: Int = 5

    init {
        this.allData = homeModel.data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomSecondViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_does, parent, false)
        return CustomSecondViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return if(showShimmer) this.shimmerItemNumber
        else this.allData.count()
    }

    override fun onBindViewHolder(holder: CustomSecondViewHolder, position: Int) {
        if(showShimmer){
            holder.view.item_does_shimmer.startShimmer()
        }
        else{
            holder.view.item_does_shimmer.stopShimmer()
            holder.view.item_does_shimmer.setShimmer(null)

            val listData = homeModel.data.get(position)

            val fullName = listData.first_name + " " + listData.last_name
            holder.view.titledoes?.text = fullName
            holder.view.titledoes?.background = null
            holder.view.titledoes?.width = holder.view.header_center.width

            holder.view.descdoes?.text = listData.email
            holder.view.descdoes?.background = null
            holder.view.descdoes?.width = holder.view.header_center.width

            Picasso.get().load(listData.avatar).into(holder.view.image_avatar)
            holder.view.image_avatar?.background = null
        }
    }
}

class CustomSecondViewHolder(val view: View): RecyclerView.ViewHolder(view){

}