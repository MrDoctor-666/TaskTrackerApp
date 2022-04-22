package demo

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class TaskService(val db: TaskRepository) {

    //TODO() maybe create list with overdue tasks (completed or not)
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

    fun allTasksToday() : List<DisplayTask> = displayTasks.filter { it.date == LocalDate.now() }

    fun deleteTask(task: Task) {
        displayTasks.removeIf { it.initialTask.id == task.id }
        taskRep.delete(task)
    }

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
        //TODO() change in database when skip
        //TODO() skip to given amount of days
        val daysToAdd: Long = 1
        val task: DisplayTask = displayTasks.find { it.thisTaskID == id } ?: return
        if (!task.initialTask.canSkip) return

        val date = LocalDate.parse(task.initialTask.endDate, DateTimeFormatter.ISO_DATE)
        taskRep.changeEndDate(task.initialTask.id, date.plusDays(daysToAdd).toString())

        var curTask: DisplayTask = task
        while (true) {
            curTask.date = curTask.date.plusDays(daysToAdd)
            curTask = displayTasks.find { it.thisTaskID == curTask.nextTaskID } ?: break
        }
    }

    fun dayEnd() {
        //TODO() change/delete from database on end day
        displayTasks.clear()

        allTasks().forEach {
            val date = LocalDate.parse(it.endDate, DateTimeFormatter.ISO_DATE)
            if (date.isBefore(LocalDate.now()))
                if (it.canSkip) {
                    taskRep.changeEndDate(
                        it.id,
                        LocalDate.now().toString()
                    )
                } else
                    if (it.repeat == 1) taskRep.delete(it)
                    else {
                        taskRep.changeRepeat(it.id, it.repeat - 1)
                        taskRep.changeEndDate(it.id, date.plusDays(it.repeatDays.toLong()).toString())
                    }
            createDisplay(it)
        }
    }

    fun completeTask(id: String) {
        //TODO() delete from database when complete
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