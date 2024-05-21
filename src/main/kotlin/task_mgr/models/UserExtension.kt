package task_mgr.models

fun User.toDto() : UserDto =
    UserDto(
        id = this.id,
        username = this.username,
        password = this.password
    )

fun UserDto.toUser(id: Int) : User =
    User(
        id = id,
        username = this.username,
        password = this.password
    )