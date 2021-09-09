package com.example.android.architecture.blueprints.todoapp.tasks

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.getOrAwaitValue
import org.apache.tools.ant.taskdefs.Tstamp
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

class TasksViewModelTest{

    private lateinit var tasksRepository: FakeTestRepository

    /*
     learn how to properly test live data

     there are two main things that you should do to test live data.
     i) The first is to use the architecture components Instant Task Executor Rule.
     ii) The second is to make sure that live data is observed.

     The instant task executor rule is a JUnit rule. JUnit rules are classes that allow you to define some code that runs before and after each test runs.
     This rule here runs all architecture components related background jobs in the same thread. This ensures that the test results happen synchronously and in
     a repeatable order. two things that are pretty important for tests.

     Basically, when you write tests that include testing live data you should also include this rule.

    */
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var taskViewModel: TasksViewModel
    @Before
    fun setupViewModel(){
        tasksRepository = FakeTestRepository()
        val task1 = Task("Title1", "Description1")
        val task2 = Task("Title2", "Description2", true)
        val task3 = Task("Title3", "Description3", true)
        tasksRepository.addTasks(task1,task2, task3)

        taskViewModel = TasksViewModel(tasksRepository)
    }

    @Test
    fun addNewTask_setsNewTaskEvent(){

        /*
        * Normally, ViewModels are retrieved using a ViewModel provider, Which requires that you have an associated fragment or activity
        * or navGraph for the viewModel. But for unit testing a viewModel, you can literally just construct a new ViewModel,
        * you don't need an associated fragment or activity.
        * But in our case, the viewModel is AndroidViewModel which requires an application context to construct. So we need to get
        * the Application Context
        */
        /*
        In situations like this, where you want to run something as local test, but it requires a class from android(in our case, application)
        there's basically 4 steps that we follow.
        i) Add AndroidX Test dependency
        ii) Add Robolectric dependency
        iii) Use AndroidX Text
        iv) Add annotation that we are running AndroidJUnit4 test runner
         */

        /*
        Testing live data is tricky because live data is normally observed. The fact that live data is observed is important, when live data isn't observed it doesn't do much.
        You need active observers to trigger any on-change events and to trigger any live data transformations. In short, to get the expected behavior for your live data,
        you need to observe the live data.

         */


        //Given a fresh ViewModel

        //When adding a new task
        taskViewModel.addNewTask()

        // Then the new task event is triggered
        /*
        We want to observe it but this poses a problem. In my Task View Model test, We don't have an activity or fragment or other life cycle owner with which to observe this live data.
        To get around this, there is this observe forever method, what this does is it ensures that your live data is constantly observed without needing a life cycle owner.
         But here, because there was a lot of boilerplate code, we have added the LiveDataTestUtil class.
         */
        val value = taskViewModel.newTaskEvent.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled(), not(nullValue()) )
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible(){
        //Given

        //When
        taskViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        //Then
        val value = taskViewModel.tasksAddViewVisible.getOrAwaitValue()
        assertThat(value, `is`(true))
    }

}