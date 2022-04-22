package demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class TaskTrackerApplication

fun main(args: Array<String>) {
    runApplication<TaskTrackerApplication>(*args)
}

@RestController
class MessageResource(val service: TaskService) {
    @GetMapping
    fun index(): List<Task> = service.allTasks()

    @PostMapping("/add")
    fun post(@RequestBody task: Task) {
        service.post(task)
    }

    @PostMapping("/delete")
    fun deleteTask(@RequestBody task: Task) {
        service.deleteTask(task)
    }

    @PostMapping("/dayEnd")
    fun dayEnd() {
        service.dayEnd()
    }

    //display tasks
    @GetMapping("/display")
    fun display(): List<DisplayTask> = service.allTasksDisplay()

    @GetMapping("/display/today")
    fun displayToday(): List<DisplayTask> = service.allTasksToday()

    @PostMapping("/display/complete")
    fun completeTask(@RequestBody id: String) {
        val sendID = if (id[0] == '"') id.substring(1, id.length - 1) else id
        service.completeTask(sendID)
    }

    @PostMapping("/display/skip")
    fun skipTask(@RequestBody id: String) {
        val sendID = if (id[0] == '"') id.substring(1, id.length - 1) else id
        service.skipTask(sendID)
    }
}