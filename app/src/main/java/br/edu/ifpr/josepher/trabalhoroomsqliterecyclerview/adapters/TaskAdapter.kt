package br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview.R
import br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview.database.AppDatabase
import br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview.database.dao.TaskDao
import br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview.model.Task
import kotlinx.android.synthetic.main.edit_one_task.view.*
import kotlinx.android.synthetic.main.one_task.view.*

class TaskAdapter(val listener: TaskAdapterListener, context: Context): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private val dao: TaskDao
    private var tasks: MutableList<Task>

    //item - tarefa editavel
    private var taskEditing : Task? = null


    fun save(task: Task): Int{
        return if (task.id == 0L){
            task.id = dao.insert(task)

            //add tarefa para editavel
            taskEditing = task

            val position = 0
            tasks.add(position, task)
            notifyItemInserted(position)
            position
        }else{
            dao.update(task)
            val position = tasks.indexOf(task)
            notifyItemChanged(position)
            position
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun fillView(task: Task){
//            if (task == taskEditing){
//                itemView.etTitle.setText(task.title)
//                itemView.etDescription.setText(task.description)
//
//                itemView.btSave.setOnClickListener{
//                    if(taskEditing != null){
//                        if (task.id == 0){
//                            taskEditing?.let{task ->
//                                task.title = itemView.etTitle.text.toString()
//                                task.description = itemView.etDescription.text.toString()
//
//                                listener.task
//                            }
//                        }
//                    }
//                }
//            }else{
//
//            }
        }
    }

    init {
        val db = Room.databaseBuilder(context, AppDatabase::class.java, "tasks-bd")
            .allowMainThreadQueries()
            .build()
        dao = db.taskDao()
        tasks = dao.getAll().toMutableList()
    }

    override fun getItemViewType(position: Int): Int {
        val task = tasks[position]
        //se a tarefa da vez for a mesma que esta na editavel exibe o card de edição se não o card compacto
        return if(task == taskEditing)
            R.layout.edit_one_task
        else
            R.layout.one_task
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false))

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task =  tasks[position]
        holder.fillView(task)
    }

}