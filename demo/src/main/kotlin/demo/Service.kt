package demo

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class TaskService(val db: TaskRepository) {

    val taskRep = TaskRepositoryTemp()

    //fun allTasks(): List<Task> = db.allTasks()
    fun allTasks(): List<Task> = taskRep.allTasks()

    fun post(message: Task){
        //db.save(message)
        taskRep.postTask(message)
    }

    fun allTasksDisplay() : List<DisplayTask> {
        val list : MutableList<DisplayTask> = mutableListOf()
        val untilDate : LocalDate = LocalDate.now().plusYears(2)

        allTasks().forEach {
            if (it.repeat > 1)
            {
                var prev : DisplayTask? = null
                var date : LocalDate = LocalDate.parse(it.endDate, DateTimeFormatter.ISO_DATE)
                var count = 1
                while (date.isBefore(untilDate) && it.repeat > count){
                    val cur = DisplayTask(it, date, prev = prev)
                    list.add(cur)
                    if (prev != null) prev.nextTask = cur
                    prev = cur
                    date = date.plusDays(it.repeatDays.toLong())
                    count ++
                }
            }
            else list.add(DisplayTask(it))
        }

        return list.sortedBy { it.date }
    }

    fun skipTask(id : Int) {
        TODO() //change in database
    }

    fun dayEnd(){
        TODO() //change/delete from database
    }

    fun completeTask(){
        TODO() //delete from database
    }
}

class DisplayTask
{
    val task : Task //if in database, ref by id
    var date : LocalDate
    var nextTask : DisplayTask? //do I need this?
    var previousTask : DisplayTask?


    constructor(task : Task, next : DisplayTask? = null, prev : DisplayTask? = null ){
        this.task = task
        date = LocalDate.parse(task.endDate, DateTimeFormatter.ISO_DATE)
        nextTask = next
        previousTask = prev
    }

    constructor(task : Task, endDate : LocalDate, next : DisplayTask? = null, prev : DisplayTask? = null) : this(task, next, prev)
    {
        this.date = endDate
    }
}