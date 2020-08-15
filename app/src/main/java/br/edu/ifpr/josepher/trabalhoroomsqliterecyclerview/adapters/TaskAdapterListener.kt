package br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview.adapters

import br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview.model.Task

interface TaskAdapterListener {
    fun taskRemoved(task: Task)
    fun onTaskSelected(task: Task)
    fun taskSave(task: Task)
    fun taskChange(task: Task)
//    fun share(task: Task)
}