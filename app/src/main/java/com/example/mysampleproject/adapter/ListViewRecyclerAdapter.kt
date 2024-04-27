package com.example.mysampleproject.adapter

import ListViewResponseItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mysampleproject.R


class ListViewRecyclerAdapter(
    private val data: List<ListViewResponseItem>,
    private val callback: (ListViewResponseItem) -> Unit
) : RecyclerView.Adapter<ListViewRecyclerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userId: TextView = itemView.findViewById<TextView>(R.id.txt_item_id)
        val userTitle: TextView = itemView.findViewById<TextView>(R.id.txt_title)
        val userDesc: TextView = itemView.findViewById<TextView>(R.id.txt_desc)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewRecyclerAdapter.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_view_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ListViewResponseItem = data[position]
        holder.userId.text = item.userId.toString()
        holder.userTitle.text = item.title
        holder.userDesc.text = item.body
        holder.itemView.setOnClickListener {
            callback(item)
        }
    }

    override fun getItemCount(): Int = data.size

}