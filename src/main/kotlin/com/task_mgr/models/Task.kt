package com.task_mgr.models

import kotlinx.serialization.Serializable

@Serializable
data class Task(val id: Int, val name: String, val completed: Boolean)