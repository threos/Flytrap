package com.teambutterflyeffect.flytrap.entrypoint

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val path = Paths.get("/Users/erayeminocak/Documents/data-export_data-frc_2022_rapid_react_ball_target-2022-02-11T17-16-35.008673Z-image_object_detection_1.csv")

    val out = Paths.get("/Users/erayeminocak/Documents/merged_data.csv")

    val content = Files.readAllLines(path)

    val newContent = content.map {
        it.replaceFirst("red_ball", "ball").replaceFirst("blue_ball",  "ball")
    }

    Files.write(out, newContent)
}