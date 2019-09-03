package com.daovu65.employeemanager.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daovu65.employeemanager.R
import com.daovu65.employeemanager.domain.entity.Employee

class MainAdapter(
    private val context: Context,
    private val itemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var listEmployee: List<Employee> = emptyList()

    fun submitValue(value: List<Employee>) {
        listEmployee = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_employee, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listEmployee.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEmployee = listEmployee[position]
        holder.bind(currentEmployee)
    }

    fun getEmployeeAt(position: Int): Employee {
        return listEmployee[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.tv_full_name)
        private val birth: TextView = itemView.findViewById(R.id.tv_birth)
        private val imageProfile: ImageView = itemView.findViewById(R.id.image_profile)

        init {
            itemView.setOnClickListener {
                itemClickListener(adapterPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(employee: Employee) {
            Glide.with(context)
                .load(Uri.parse(employee.profileImage))
                .override(60)
                .error(R.mipmap.ic_launcher_round)
                .into(imageProfile)
            name.text = employee.name
            birth.text = employee.age
        }
    }


}
