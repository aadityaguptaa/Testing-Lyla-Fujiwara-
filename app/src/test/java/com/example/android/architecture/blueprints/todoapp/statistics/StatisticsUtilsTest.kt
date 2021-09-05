package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test

class StatisticsUtilsTest{

    //If there's no completed task and one active task,
    // then there are 100% percent active tasks and 0% completed tasks.

    /*
    Conventional function naming schema
     '''subjectUnderTest_actionOrInput_resultState'''
     */

    /*
    Conventional testing mnemonic
     '''Given/When/Then'''
     */

    /*
    Three ways to make your tests more readable
    i) picking up good names
    ii) commenting with something like Given/When/Then
    iii) And using an assertion framework like hamcrest
     */
    @Test
    fun getActiveAndCompletedStats_noCompleted_returnZeroHundred(){
        //Given
        val tasks = listOf<Task>(
            Task("title", "description", isCompleted = false)
        )

        //When
        val result = getActiveAndCompletedStats(tasks)

        //Then
        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat(result.activeTasksPercent, `is`(100f))
    }

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnFortySixty(){
        val tasks = listOf<Task>(
            Task("title", "description", isCompleted = true),
            Task("title", "description", isCompleted = true),
            Task("title", "description", isCompleted = false),
            Task("title", "description", isCompleted = false),
            Task("title", "description", isCompleted = false)
        )

        val result = getActiveAndCompletedStats(tasks)

        assertEquals(40f, result.completedTasksPercent)
        assertEquals(60f, result.activeTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_empty_returnZeroZero(){
        val tasks = emptyList<Task>()

        val result = getActiveAndCompletedStats(tasks)

        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_null_returnZeroZero(){
        val tasks = null

        val result = getActiveAndCompletedStats(tasks)

        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }
}