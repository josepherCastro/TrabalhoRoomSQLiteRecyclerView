package br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview.adapters.TaskAdapter
import br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview.adapters.TaskAdapterListener
import br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview.database.AppDatabase
import br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview.database.dao.TaskDao
import br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview.model.Task
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , TaskAdapterListener{
    private lateinit var adapter: TaskAdapter
    private lateinit var dao: TaskDao
    private var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "tasks-db")
            .allowMainThreadQueries()
            .build()
        dao = db.taskDao()

        fab_AddTask.setOnClickListener{
            //if(!flag) {
                val task = Task("", "", false)
                task.id = 0L
                adapter.addTask(task)
              //  flag = true
            //}
        }
        loadData()
    }

    override fun taskRemoved(task: Task) {
        dao.delete(task)
        loadData()
    }


    override fun onTaskSelected(task: Task) {
        val position = adapter.addTaskChange(task)
        rc_tasks.scrollToPosition(position)
    }

    override fun taskSave(task: Task) {
//        flag = false
        dao.insert(task)
        loadData()
    }

    override fun taskChange(task: Task) {
        dao.update(task)
        loadData()
    }

    private fun loadData(){
        val tasks = dao.getAll().toMutableList()

        adapter = TaskAdapter(tasks.toMutableList(), this, applicationContext)
        rc_tasks.adapter = adapter
        rc_tasks.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

}