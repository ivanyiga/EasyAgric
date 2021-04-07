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


class expAdapter(private val context: Activity, val invoicesList: ArrayList<sale>) : RecyclerView.Adapter<expAdapter.ViewHolder>() {




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sale: sale = invoicesList[position]

        holder.title.text = sale.title
        holder.price.text = "UGX ${NumberFormat.getNumberInstance(Locale.US)
            .format(sale.price)}"


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
                /*val intent = Intent(itemView.context, NewExpense::class.java)
                intent.putExtra("edit",true)
                intent.putExtra("data",sale.toString())
                itemView.context.startActivity(intent)*/
            }
            deleteBtn.setOnClickListener {
                val sales = CRUD.getInstance().expenses
                var expData = JSONArray(sales)
                val list = ArrayList<JSONObject>()
                for (i in 0 until expData.length()) {
                    val item = expData.getJSONObject(i)
                    list.add(item)
                    if (sale.id == item.getInt("id")){
                        Log.d("sales", "ready to delete")
                        list.removeAt(i)
                        expData = JSONArray(list)
                        CRUD.getInstance().saveNewExpense(expData.toString())
                        val intent = Intent(itemView.context, Deleting::class.java)
                        itemView.context.startActivity(intent)
                        (itemView.context as Activity).finish()
                    }
                }
            }
        }

    }

}