package steganography

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

fun main() {
    println("Task (hide, show, exit):")
    var task = readLine()

    while (!task.equals("exit")) {
        when (task) {
            "hide" -> hide()
            "show" -> println("Obtaining message from image.")
            else -> println("Wrong task: $task")
        }
        println("Task (hide, show, exit):")
        task = readLine()
    }
    println("Bye!")
}

fun hide() {
    println("Hiding message in image.")
    println("Input image file:")
    val inputImageName = readLine()!!
    println("Output image file:")
    val outputImageName = readLine()!!

    try {
        val inputImage = ImageIO.read(File(inputImageName))
        val outputImage = hideTextInImage(inputImage)
        ImageIO.write(outputImage, "png", File(outputImageName))

        println("Input Image: $inputImageName")
        println("Output Image: $outputImageName")
        println("Image $outputImageName is saved.")
    } catch (e: IOException) {
        println("Can't read input file!")
    }
}

fun hideTextInImage(image: BufferedImage): BufferedImage {
    val width = image.width
    val height = image.height
    val outputImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

    for (i in 0 until width) {
        for (j in 0 until height) {
            val color = Color(image.getRGB(i, j))
            val changedColor = Color(changeLSB(color.red), changeLSB(color.green), changeLSB(color.blue))
            outputImage.setRGB(i, j, changedColor.rgb)
        }
    }

    return outputImage
}

fun changeLSB(value: Int): Int {
    return if (value % 2 == 0) value + 1 else value
}