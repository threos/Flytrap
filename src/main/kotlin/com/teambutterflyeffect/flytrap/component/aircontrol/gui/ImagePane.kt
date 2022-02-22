package com.teambutterflyeffect.flytrap.component.aircontrol.gui

import com.teambutterflyeffect.flytrap.robot.component.imageData
import com.teambutterflyeffect.flytrap.robot.component.onUpdate
import java.awt.*
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class ImagePane() : JPanel() {
    init {
        background = Color.BLACK
        border = EmptyBorder(0, 0, 0, 0)
        layout = GridBagLayout()

        onUpdate = {
            repaint()
        }
    }

    override fun paint(g: Graphics) {
        super.paint(g)
        imageData?.let {
            g.drawImage(ImageIO.read(it), 0, 0, 2000,700, null)
        }
    }
}