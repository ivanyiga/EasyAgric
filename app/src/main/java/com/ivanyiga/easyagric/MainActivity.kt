package com.ivanyiga.easyagric

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newSaleBtn.setOnClickListener {
            val intent = Intent(this,NewSale::class.java)
//            intent.putExtra("edit",false)
            startActivity(intent)
        }
        newExpBtn.setOnClickListener {
            val intent = Intent(this,NewExpense::class.java)
            startActivity(intent)
        }
    }

    fun getSales(){
        var sales = CRUD.getInstance().sales
        if (sales == null) {
            Log.d("sales","null")
        }else{
            Log.d("sales",sales)
            val SalesRecycl = salesRecycler as RecyclerView
            SalesRecycl.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            SalesRecycl.isNestedScrollingEnabled = false

            val salesData = JSONArray(sales)
            val saleList = ArrayList<sale>()

            var price = 0
            for (i in 0 until salesData.length()) {
                val item = salesData.getJSONObject(i)
                var sale = sale(
                    item.getString("title"),
                    item.getString("price").toInt(),
                    item.getInt("id")
                )
                saleList.add(sale)
                saleList.isEmpty()
                val adapter = salesAdapter(this, saleList)
                SalesRecycl.adapter = adapter
                adapter.notifyItemRemoved(i)

                price += item.getString("price").toInt()
            }
            salesTotal.text = "UGX ${NumberFormat.getNumberInstance(Locale.US)
                .format(price)}"
        }
    }
    fun getExpenses(){
        var sales = CRUD.getInstance().expenses
        if (sales == null) {
            Log.d("sales","null")
        }else{
            Log.d("sales",sales)
            val SalesRecycl = expensesRecycler as RecyclerView
            SalesRecycl.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            SalesRecycl.isNestedScrollingEnabled = false

            val salesData = JSONArray(sales)
            val saleList = ArrayList<sale>()

            var price  = 0
            saleList.clear()
            for (i in 0 until salesData.length()) {
                val item = salesData.getJSONObject(i)
                var sale = sale(
                    item.getString("title"),
                    item.getString("price").toInt(),
                    item.getInt("id")
                )
                saleList.add(sale)
                val adapter = expAdapter(this, saleList)
                SalesRecycl.adapter = adapter
                adapter.notifyItemRemoved(i)
                price += item.getString("price").toInt()
            }
            expTotal.text = "UGX ${
                NumberFormat.getNumberInstance(Locale.US)
                .format(price)}"
        }
    }

    override fun onResume() {
        getSales()
        getExpenses()
        super.onResume()
    }


    override fun onAttachedToWindow() {
        getSales()
        getExpenses()
        super.onAttachedToWindow()
    }
}