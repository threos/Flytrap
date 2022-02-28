package com.teambutterflyeffect.flytrap.component.tower.positioning

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.Ultrasonic

class TowerContentPositioner(context: LifecycleContext) : LifecycleObject(context) {
    val state = TowerContentCubit()

    private val topSwitch = DigitalInput(0)
    private val midSwitch = DigitalInput(1)
    private val bottomSwitch = DigitalInput(2)

    private val shooterRanger = Ultrasonic(3, 4)
    private val midRanger = Ultrasonic(5, 6)
    private val intakeRanger = Ultrasonic(7, 8)

    override fun onTick(context: ObjectContext<*>) {
        val topSwActive = !topSwitch.get()
        val midSwActive = !midSwitch.get()
        val bottomSwActive = !bottomSwitch.get()

        val shooterUrdActive = shooterRanger.rangeMM.let { it < 15.0 && it > 0.0 }
        val midUrdActive = midRanger.rangeMM.let { it < 10.0 && it > 0.0 }
        val intakeUrdActive = intakeRanger.rangeMM.let { it < 15.0 && it > 0.0 }

        val result = ArrayList<TowerPosition>()

        if (topSwActive) result.add(TowerPosition.SHOOTER)
        if (shooterUrdActive) result.add(TowerPosition.PRE_SHOOTER)
        if (midUrdActive) result.add(TowerPosition.HOPPER)
        if (intakeUrdActive) result.add(TowerPosition.POST_INTAKE)

        state.emit(context, result.map {
            TowerTarget(it)
        })
    }

    override fun onCreate(context: ObjectContext<*>) {
        super.onCreate(context)
    }

    override fun onDestroy(context: ObjectContext<*>) {
        super.onDestroy(context)
        topSwitch.close()
        midSwitch.close()
        bottomSwitch.close()
        shooterRanger.close()
        midRanger.close()
        intakeRanger.close()
    }
}