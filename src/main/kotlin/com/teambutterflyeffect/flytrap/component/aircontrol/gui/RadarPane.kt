package com.teambutterflyeffect.flytrap.component.aircontrol.gui

import com.teambutterflyeffect.flytrap.component.fvm2.protocol.VisionMapEntity
import java.awt.*
import javax.swing.JPanel
import kotlin.math.max
import kotlin.math.roundToInt

class RadarPane : JPanel() {
    var entities: List<VisionMapEntity> = emptyList()
        set(value) {
            field = value
            repaint()
        }

    init {
        background = Color.BLACK
    }
    override fun getPreferredSize(): Dimension {
        return Dimension(1100, 1100)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g.create() as Graphics2D

        val width = width - 20
        val height = height - 20
        val size = max(width, height)
        val robotSize = 200
        g2d.color = Color.GRAY
        g2d.background = Color(0, 0, 0, 0)
        var ovalSize = 0
        g2d.color = Color.GRAY
        g2d.stroke = BasicStroke(2f)
        while(ovalSize < size) {
            val a = (1.0f - (ovalSize.toFloat() / size.toFloat()) * 0.8f)
            g2d.color = Color(0.8f, 0.8f, 0.8f, a)
            g2d.drawOval((getWidth() - ovalSize) / 2, (getHeight() - ovalSize) / 2, ovalSize, ovalSize)
            ovalSize += 150
        }

        for(entity in entities) {
            g2d.color = Color(200, 40, 40, 255)
            val x = (((((entity.x_0 + entity.x_1) / 2) - 0.5) * (1.2 - entity.distanceToReference)) * getWidth() + getWidth() / 2).toInt() - 50
            val y = ((entity.distanceToReference) * (getWidth() / 2.4)).roundToInt() - 50
            g2d.fillOval(
                x,
                y, 100, 100)
        }

        g2d.paint = GradientPaint((getWidth()) / 2f, (getHeight()) / 2f, Color(255, 255, 255, 50), (getWidth()) / 2f, 100f, Color(255, 255, 255, 5))
        g2d.fillPolygon(
            Polygon(
                intArrayOf(0, 0, getWidth(), getWidth(), (getWidth()) / 2),
                intArrayOf(150, 0, 0, 150, getHeight() / 2),
                5,
            )
        )

        g2d.paint = null

        g2d.color = Color.GRAY
        g2d.fillRoundRect((getWidth() - robotSize) / 2, (getHeight() - robotSize) / 2, robotSize, robotSize,16, 16)


        g2d.dispose()
    }
}