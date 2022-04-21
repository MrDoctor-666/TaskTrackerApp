package demo

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


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