package com.task_mgr.models

fun Task.toDto() : TaskDto =
    TaskDto(
        id = this.id.toString(),
        name = this.name,
        description = this.description,
        label = this.label,
        owner = this.owner,
        status = this.status
    )

fun TaskDto.toTask() : Task =
    Task(
        name = this.name,
        description = this.description,
        label = this.label,
        owner = this.owner,
        status = this.status
    )