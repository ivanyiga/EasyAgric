package com.ivanyiga.easyagric

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new_expense.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class NewExpense : AppCompatActivity() {
    private var expenses = JSONArray()
    var expense: JSONObject = JSONObject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_expense)

        saveExpBtn.setOnClickListener {
            val r = Random()
            val id: Int = r.nextInt(80 - 5) + 5

            var title = expTitle.text.toString()
            var price = expPrice.text.toString()
            var expenseJson = JSONArray()
            if (CRUD.getInstance().expenses!=null) {
                expenseJson = JSONArray(CRUD.getInstance().expenses)
            }

            if(expenseJson.length() > 0){
                expenses = expenseJson
            }

            expense.put("title", title)
            expense.put("price", price)
            expense.put("id", id)
            expenses.put(expense)
            CRUD.getInstance().saveNewExpense(expenses.toString())
            finish()
        }
    }
}