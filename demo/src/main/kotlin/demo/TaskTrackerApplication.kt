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

    @GetMapping("/display")
    fun display() : List<DisplayTask> = service.allTasksDisplay()

	@PostMapping
	fun post(@RequestBody task: Task) {
		service.post(task)
	}
}