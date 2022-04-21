package demo

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class TaskService(val db: TaskRepository) {

    val taskRep = TaskRepositoryTemp()
    var displayTasks: MutableList<DisplayTask> = mutableListOf()

    //fun allTasks(): List<Task> = db.allTasks()
    fun allTasks(): List<Task> = taskRep.allTasks()

    fun post(message: Task) {
        //db.save(message)
        message.id = UUID.randomUUID().toString()
        taskRep.postTask(message)
        createDisplay(message)
    }

    fun allTasksDisplay(): List<DisplayTask> = displayTasks.sortedBy { it.date }

    fun createDisplay(task: Task) {
        val untilDate: LocalDate = LocalDate.now().plusYears(2)

        if (task.repeat > 1) {
            var prev: DisplayTask? = null
            var date: LocalDate = LocalDate.parse(task.endDate, DateTimeFormatter.ISO_DATE)
            var count = 0
            while (date.isBefore(untilDate) && task.repeat > count) {
                val cur = DisplayTask(task, date, prev = prev)
                displayTasks.add(cur)
                if (prev != null) prev.nextTaskID = cur.thisTaskID
                prev = cur
                date = date.plusDays(task.repeatDays.toLong())
                count++
            }
        } else displayTasks.add(DisplayTask(task))
    }

    fun skipTask(id: String) {
        //TODO() change in database
        //TODO() skip to given amount of days
        val daysToAdd : Long = 1
        val task: DisplayTask = displayTasks.find { it.thisTaskID == id } ?: return
        if (!task.initialTask.canSkip)return

        val date = LocalDate.parse(task.initialTask.endDate, DateTimeFormatter.ISO_DATE)
        taskRep.changeEndDate(task.initialTask.id, date.plusDays(daysToAdd).toString())

        var curTask : DisplayTask = task
         while (true) {
             curTask.date = curTask.date.plusDays(daysToAdd)
             curTask = displayTasks.find { it.thisTaskID == curTask.nextTaskID } ?: break
         }
    }

    fun dayEnd() {
        TODO() //change/delete from database
    }

    fun completeTask(id: String) {
        //TODO() delete from database
        val task: DisplayTask = displayTasks.find { it.thisTaskID == id } ?: return

        if (task.initialTask.repeat == 1) taskRep.delete(task.initialTask)

        if (task.nextTaskID != null) {
            val next: DisplayTask = displayTasks.find { it.thisTaskID == task.nextTaskID }!!

            next.previousTaskID = null
            taskRep.changeRepeat(task.initialTask.id, task.initialTask.repeat - 1)
            taskRep.changeEndDate(task.initialTask.id, next.date.toString())
        }

        displayTasks.remove(task)
    }
}

class DisplayTask {
    val initialTask: Task
    val thisTaskID: String
    var date: LocalDate
    var nextTaskID: String? //do I need this?
    var previousTaskID: String?


    constructor(task: Task, next: DisplayTask? = null, prev: DisplayTask? = null) {
        this.initialTask = task
        thisTaskID = UUID.randomUUID().toString()
        date = LocalDate.parse(task.endDate, DateTimeFormatter.ISO_DATE)
        nextTaskID = next?.thisTaskID
        previousTaskID = prev?.thisTaskID
    }

    constructor(task: Task, endDate: LocalDate, next: DisplayTask? = null, prev: DisplayTask? = null) : this(
        task,
        next,
        prev
    ) {
        this.date = endDate
    }
}