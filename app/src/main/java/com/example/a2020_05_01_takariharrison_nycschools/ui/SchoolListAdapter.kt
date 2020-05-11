package com.example.a2020_05_01_takariharrison_nycschools.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a2020_05_01_takariharrison_nycschools.R
import com.example.a2020_05_01_takariharrison_nycschools.data.SchoolListData
import kotlinx.android.synthetic.main.school_list_adapter_layout.view.*

class SchoolListAdapter(
    private val schoolListData: List<SchoolListData>,
    private val onItemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<SchoolListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.school_list_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = schoolListData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {
            schoolName.text = "School Name: ${schoolListData[position].name}"
            city.text = "City: ${schoolListData[position].city}"
            phoneNumber.text = "Phone Number: ${schoolListData[position].phoneNumber}"
            website.text = "Website: ${schoolListData[position].website}"
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val schoolName: TextView = itemView.schoolName
        val city: TextView = itemView.city
        val phoneNumber: TextView = itemView.phoneNumber
        val website: TextView = itemView.website

        init { itemView.setOnClickListener { onItemClick(adapterPosition) } }
    }
}