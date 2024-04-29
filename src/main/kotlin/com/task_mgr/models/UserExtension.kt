package com.task_mgr.models

fun User.toDto() : UserDto =
    UserDto(
        id = this.id.toString(),
        username = this.username,
        password = this.password
    )

fun UserDto.toUser() : User =
    User(
        username = this.username,
        password = this.password
    )