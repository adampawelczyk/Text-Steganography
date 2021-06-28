# Project Name
> Text-Steganography

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Code Example](#code-example)
* [Status](#status)

## General info
You can use encryption to send secret messages, but if you want to be even more subtle, you can hide them in plain sight: in an image. This kind of encryption is called steganography. The message will be hiding in a picture on some website, and only those who know will understand it. In this project I create a program for concealing a message within an image.

## Technologies
* Kotlin - version 1.5.20
* Gradle - version 7.0.2

## Code Example
```
Task (hide, show, exit):
hide
Input image file:
image.png
Output image file:
image_with_text.png
Message to hide:
Hello World!

Available space: 15000 bytes
Size of message to hide: 13 bytes

Input Image: image.png
Output Image: image_with_text.png
Image image_with_text.png is saved.

Task (hide, show, exit):
show
Image file:
image_with_text.png
Message:
Hello World!

Task (hide, show, exit):
exit
Bye!
```

## Status
Project is: _finished_