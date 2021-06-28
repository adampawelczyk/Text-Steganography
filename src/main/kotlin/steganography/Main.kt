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
            "show" -> show()
            else -> println("Wrong task: $task")
        }
        println("Task (hide, show, exit):")
        task = readLine()
    }
    println("Bye!")
}

fun hide() {
    println("Input image file:")
    val inputImageName = readLine()!!
    println("Output image file:")
    val outputImageName = readLine()!!
    println("Message to hide:")
    val message = readLine()!!

    try {
        val inputImage = ImageIO.read(File(inputImageName))

        printStatistics(inputImage.width, inputImage.height, message.length)

        if (inputImage.width * inputImage.height * 3 < message.length * 8 + 8) {
            println("Not enough space!")
        } else {
            val outputImage = hideTextInImage(inputImage, stringToStringOfBits(message))

            ImageIO.write(outputImage, "png", File(outputImageName))

            println("Input Image: $inputImageName")
            println("Output Image: $outputImageName")
            println("Image $outputImageName is saved.\n")
        }
    } catch (e: IOException) {
        println("IOException")
    }
}

fun hideTextInImage(image: BufferedImage, message: String): BufferedImage {
    val width = image.width
    val height = image.height
    val outputImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    var messageIndex = 0

    for (i in 0 until width) {
        for (j in 0 until height) {
            var color = Color(image.getRGB(i, j))

            if (messageIndex < message.length) {
                val changedRed = changeLSB(color.red, getCharacter(message, messageIndex))
                val changedGreen = changeLSB(color.green, getCharacter(message, messageIndex + 1))
                val changedBlue = changeLSB(color.blue, getCharacter(message, messageIndex + 2))

                messageIndex += 3

                color = Color(changedRed, changedGreen, changedBlue)
            }
            outputImage.setRGB(i, j, color.rgb)
        }
    }
    return outputImage
}

fun changeLSB(value: Int, bit: Int): Int {
    return if (bit == 1) {
        if (value % 2 == 0) value + 1 else value
    } else {
        if (value % 2 == 0) value else value - 1
    }
}

fun stringToStringOfBits(text: String): String {
    val textByteArray = text.encodeToByteArray()
    var stringOfBits = ""

    for (i in textByteArray.indices) {
        stringOfBits += (textByteArray[i].toInt().toString(2)).padStart(8, '0')
    }

    stringOfBits += "11111111"

    return stringOfBits
}

fun getCharacter(string: String, index: Int): Int {
    if (index >= string.length) {
        return 0
    }
    return Character.getNumericValue(string[index])
}

fun printStatistics(imageWidth: Int, imageHeight: Int, messageLength: Int) {
    println("\nAvailable space: ${(imageWidth * imageHeight * 3) / 8} bytes")
    println("Size of message to hide: ${messageLength + 1} bytes\n")
}

fun show() {
    println("Image file:")
    val imageName = readLine()!!

    try {
        val inputImage = ImageIO.read(File(imageName))
        readTextFromImage(inputImage)

    } catch (e: IOException) {
        println("IOException")
    }
}

fun readTextFromImage(image: BufferedImage) {
    val width = image.width
    val height = image.height

    var message = ""

    outLoop@ for (i in 0 until width) {
        for (j in 0 until height) {
            val color = Color(image.getRGB(i, j))
            message += getLSB(color.red)
            message += getLSB(color.green)
            message += getLSB(color.blue)

            if ("11111111" in message && message.length % 8 == 0) {
                message = message.dropLast(8)
                break@outLoop
            }
        }
    }
    printMessage(message)
}

fun getLSB(colorValue: Int): Int {
    return colorValue % 2
}

fun printMessage(stringOfBits: String) {
    println("Message:")
    for (i in stringOfBits.indices step 8) {
        val stringByte = stringOfBits.slice(i..i + 7)
        val asciiValue = stringByte.toInt(2)
        if (asciiValue == 255) {
            break
        }
        print(asciiValue.toChar())
    }
    println()
    println()
}