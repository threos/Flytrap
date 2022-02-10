import com.teambutterflyeffect.flytrap.component.aircontrol.AirControlModule
import com.teambutterflyeffect.flytrap.component.fvm2.FlytrapVisionModule
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference

fun main(args: Array<String>) {
    /*runFlytrapRobot(
        Robot::class.java
    )*/
    LifecycleContext.attach(
        ObjectReference(FlytrapVisionModule::class.java),
        ObjectReference(AirControlModule::class.java)
    )
}