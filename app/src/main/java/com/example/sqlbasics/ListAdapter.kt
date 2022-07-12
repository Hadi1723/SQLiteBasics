package com.example.sqlbasics

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListAdapter(
    private val context: Context,
    private val detailModels: ArrayList<ListModel>

) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var onClickDeleteItem: ((StudentModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    fun setOnClickDeleteItem(callback:(StudentModel) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return detailModels.size
    }

    // and we're going to define our own view holder which will encapsulate the memory card view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val itemTv = itemView.findViewById<TextView>(R.id.itemId)
            val nameTv = itemView.findViewById<TextView>(R.id.idName)
            val emailTv = itemView.findViewById<TextView>(R.id.idEmail)

            val button = itemView.findViewById<Button>(R.id.btndelete)

            var model: ListModel = detailModels.get(position)

            itemTv.setText(model.getId().toString())
            nameTv.setText(model.getName())
            emailTv.setText(model.getEmail())

            button.setOnClickListener{
                onClickDeleteItem?.invoke(StudentModel(itemTv.text.toString().toInt(), nameTv.text.toString(), emailTv.text.toString()))
            }

            //var input: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
            //var output: SimpleDateFormat = SimpleDateFormat("hh:mm aa")

            /*
            try {
                var t: Date = input.parse(model.getTime())
                timeTV.setText(output.format(t))
            } catch (e: ParseException) {
                e.printStackTrace()
            } */
        }

    }


}