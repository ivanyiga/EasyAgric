package com.ivanyiga.easyagric

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.sale_item.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class salesAdapter(private val context: Activity, val invoicesList: ArrayList<sale>) : RecyclerView.Adapter<salesAdapter.ViewHolder>() {




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sale: sale = invoicesList[position]

        holder.title.text = sale.title
        holder.price.text = sale.price.toString()


        holder.bind(invoicesList[position])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.sale_item, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return  invoicesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val title = itemView.title as TextView
        val price = itemView.price as TextView
        val deleteBtn = itemView.deleteBtn as MaterialButton

        fun bind(sale: sale) {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NewSale::class.java)
                intent.putExtra("edit",true)
                intent.putExtra("title",sale.title)
                intent.putExtra("price",sale.price)
                intent.putExtra("id",sale.id)
                itemView.context.startActivity(intent)
            }
            deleteBtn.setOnClickListener {
                val sales = CRUD.getInstance().sales
                var salesData = JSONArray(sales)
                val list = ArrayList<JSONObject>()
                for (i in 0 until salesData.length()) {
                    val item = salesData.getJSONObject(i)
                    list.add(item)
                    if (sale.id == item.getInt("id")){
                        Log.d("sales", "ready to delete")
                        list.removeAt(i)
                        salesData = JSONArray(list)
                        CRUD.getInstance().saveNewSale(salesData.toString())
                        val intent = Intent(itemView.context, Deleting::class.java)
                        itemView.context.startActivity(intent)
                    }
                }
            }
        }


    }

}