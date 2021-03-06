package com.teambutterflyeffect.flytrap.component.aircontrol.gui

import com.teambutterflyeffect.flytrap.robot.component.imageData
import com.teambutterflyeffect.flytrap.robot.component.onUpdate
import java.awt.*
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class AirControlPane(val radar: RadarPane) : JPanel() {
    init {
        background = Color.BLACK
        border = EmptyBorder(0, 0, 0, 0)
        layout = GridBagLayout()
        val gbc = GridBagConstraints()
        gbc.gridwidth = GridBagConstraints.REMAINDER
        gbc.anchor = GridBagConstraints.NORTH
        gbc.anchor = GridBagConstraints.CENTER
        gbc.fill = GridBagConstraints.HORIZONTAL
        val buttons = JPanel(GridBagLayout())

        buttons.add(radar, gbc)
        gbc.weighty = 1.0
        add(buttons, gbc)
    }

    override fun paint(g: Graphics) {
        super.paint(g)
    }
}