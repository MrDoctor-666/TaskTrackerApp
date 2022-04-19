package demo

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface TaskRepository : CrudRepository<Task, String>{

    @Query("select * from tasks")
    fun allTasks(): List<Task>
}

class TaskRepositoryTemp() {

    private var list : MutableList<Task> = mutableListOf()

    fun allTasks() : List<Task> = list

    fun postTask(task : Task) {list.add(task)}

    fun find(id : String) : Task? {
        return list.find {it.id == id}
    }

    fun deleteByID(id : String){ list.remove(find(id) ?: return)}

    fun delete(task : Task) { list.remove(task) }

    fun changeRepeat(id: String, repeat: Int){
        val task = find(id) ?: return
        task.repeat = repeat
    }

    fun changeEndDate(id: String, endDate: String){
        val task = find(id) ?: return
        task.endDate = endDate
    }
}

//@Table("TASKS")
data class Task(
    //@Id val id: Int,
    var id: String,
    var taskName: String,
    var endDate : String,
    var repeat : Int = 1,
    var canSkip : Boolean = false,
    var repeatDays : Int = 0,
    var tag : String? = null,
    var color : Int = 0,
    var note : String? = null
)