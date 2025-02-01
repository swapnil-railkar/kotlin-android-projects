package com.planpal.viewmodel

import androidx.lifecycle.ViewModel
import com.planpal.data.entity.Task

class TaskViewModel: ViewModel() {

    var _taskList: List<Task> = emptyList()
    
    fun init(taskList: List<Task>) {
        this._taskList = taskList
    }
}