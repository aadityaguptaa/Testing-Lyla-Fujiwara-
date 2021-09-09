package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class TaskDetailFragmentTest{

    /*
    test to launch that fragment
     */
    /*
    We are going to be given one task, an active task and we want to make sure that it properly loads up in our fragment.
     */
    @Test
    fun activeTaskDetails_DisplayedInUi(){

        //GIVEN - Add active(incomplete) task to the DB
        val activeTask = Task("Active Task", "AndroidX Rocks", false)

        //WHEN - Details fragment launched to display task
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)
    }

}