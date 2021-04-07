package com.ivanyiga.easyagric

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_sale.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class NewSale : AppCompatActivity() {
    private var sales = JSONArray()
    var sale:JSONObject = JSONObject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_sale)

        val isEdit = intent.getBooleanExtra("edit",false)
        val _title = intent.getStringExtra("title")
        val _price = intent.getStringExtra("price")
        val _id = intent.getIntExtra("id",0)
        saveSaleBtn.setOnClickListener {
            if (!isEdit) {
                val r = Random()
                val id: Int = r.nextInt(80 - 5) + 5
                val title = salesTitle.text.toString()
                val price = salesPrice.text.toString()
                var salesJson = JSONArray()
                if (CRUD.getInstance().sales!=null) {
                    salesJson= JSONArray(CRUD.getInstance().sales)
                }

                if (salesJson.length() > 0){
                    sales = salesJson
                }

                sale.put("price", price)
                sale.put("title", title)
                sale.put("id", id)
                sales.put(sale)
                CRUD.getInstance().saveNewSale(sales.toString())
                finish()
            } else {
                val r = Random()
                val id: Int = r.nextInt(80 - 5) + 5
                var title = salesTitle.text.toString()
                var price = salesPrice.text.toString()
                var salesJson = JSONArray()
                if (CRUD.getInstance().sales!=null) {
                    salesJson= JSONArray(CRUD.getInstance().sales)
                }

                if (salesJson.length() > 0){
                    sales = salesJson
                }

                sale.put("price", price)
                sale.put("title", title)
                sale.put("id", id)
                val list = ArrayList<JSONObject>()
                for (i in 0 until sales.length()) {
                    val item = sales.getJSONObject(i)
                    list.add(item)
                    if (_id==item.getInt("id")){
                        list.removeAt(i)
                        sales = JSONArray(list)
                    }
                }
                //martinkatamba@gmail.com
                sales.put(sale)
                CRUD.getInstance().saveNewSale(sales.toString())
                finish()
            }
        }

        if(isEdit){
            salesTitle.setText(_title)
            salesPrice.setText(_price)
        }
    }
}