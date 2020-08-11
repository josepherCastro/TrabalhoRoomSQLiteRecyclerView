package br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview.adapters

import br.edu.ifpr.josepher.trabalhoroomsqliterecyclerview.model.Task

interface TaskAdapterListener {
    fun onTaskSelected(task: Task)
}