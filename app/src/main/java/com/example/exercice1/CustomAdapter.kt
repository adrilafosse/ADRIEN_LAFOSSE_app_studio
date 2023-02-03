package com.example.exercice1
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.exercice1.databinding.CellCustomBinding

class CustomAdapter(val items: List<String>, val clickListener:(Int) -> Unit): RecyclerView.Adapter<CustomAdapter.CellViewHolder>() {
    class CellViewHolder(binding: CellCustomBinding) : RecyclerView.ViewHolder(binding.root) {

        val textView: TextView=binding.textView
        val root : ConstraintLayout = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val binding = CellCustomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CellViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {

        holder.textView.text = items[position]
        holder.root.setOnClickListener {
            Log.d("item", "Click on"+position+" position")
            clickListener(position)
        }
    }
}