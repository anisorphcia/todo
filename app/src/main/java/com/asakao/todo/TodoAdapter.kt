package com.asakao.todo

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val todos: MutableList<Todo>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val todoTitle: TextView = itemView.findViewById(R.id.tv_todo_title)
        val todoCheck: CheckBox = itemView.findViewById(R.id.cb_done)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false))
    }

    fun addTodo(todo: Todo){
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos(){
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean){
        if(isChecked){
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }else{
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = todos[position]
        holder.todoTitle.text = item.title
        holder.todoCheck.isChecked = item.isChecked
        toggleStrikeThrough(holder.todoTitle, item.isChecked)
        holder.todoCheck.setOnCheckedChangeListener { _, isChecked ->
            toggleStrikeThrough(holder.todoTitle, isChecked)
            item.isChecked = !item.isChecked
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }


}