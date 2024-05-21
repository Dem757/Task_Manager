package task_mgr.models

fun Task.toDto() : TaskDto =
    TaskDto(
        id = this.id,
        name = this.name,
        description = this.description,
        label = this.label,
        owner = this.owner,
        status = this.status,
        dueDate = this.dueDate
    )

fun TaskDto.toTask(id: Int) : Task =
    Task(
        id = id,
        name = this.name,
        description = this.description,
        label = this.label,
        owner = this.owner,
        status = this.status,
        dueDate = this.dueDate
    )