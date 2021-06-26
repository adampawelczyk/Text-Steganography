package steganography

fun main() {
    println("Task (hide, show, exit):")
    var task = readLine()

    while (!task.equals("exit")) {
        when (task) {
            "hide" -> println("Hiding message in image.")
            "show" -> println("Obtaining message from image.")
            else -> println("Wrong task: $task")
        }
        println("Task (hide, show, exit):")
        task = readLine()
    }
    println("Bye!")
}