package com.test.mykotlindemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.mykotlindemo.Model.Product
import com.test.mykotlindemo.R

class ProductListAdapter(private val clickListener: (product: Product) -> Unit,private val context: Context, private var productList: MutableList<Product>,private val loadMoreListener: LoadMoreListener) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {


    private var isLoading = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_product_list, parent, false)
        return ViewHolder(view,clickListener)

    }

    fun setProducts(users: List<Product>) {
        productList = users.toMutableList()
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return productList.size
    }

    fun addData(data: List<Product>) {
        productList.addAll(data)
        notifyDataSetChanged()
        isLoading = false
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){

        val product = productList[position]
        holder.bind(product)

        // Bind data to views here
        if (position == productList.size - 1 && !isLoading) {
            loadMoreListener.onLoadMore()
            isLoading = true
        }

        holder.tvProName.text="Name :"+productList.get(position).title
        holder.tvProPrice.text="Price :Rs."+productList.get(position).price.toString()
        Glide.with(context).load(productList.get(position).thumbnail).into(holder.ivproImg)
    }

    class ViewHolder(view: View,private val clickListener: (product: Product) -> Unit): RecyclerView.ViewHolder(view) {
        private lateinit var product: Product
        val ivproImg:ImageView = view.findViewById(R.id.ivProImg)
        val tvProName:TextView = view.findViewById(R.id.tvProName)
        val tvProPrice:TextView = view.findViewById(R.id.tvProPrice)
        val layout:ConstraintLayout = view.findViewById(R.id.layout)

        init {
            initClickListeners()
        }

        fun bind(product: Product) {
            this.product = product
            // Bind product data to views here
        }

        //And pass data here with invoke
        private fun initClickListeners() {
            layout.setOnClickListener { clickListener.invoke(product) }
        }

    }




    interface LoadMoreListener {
        fun onLoadMore()
    }

}

