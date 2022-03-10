# <img height="164" src="https://voynich.dashelvest.com/webasset/img/flytrap-text-logo-low.png"/>
# Flytrap - Lifecycle-aware objects and multithreaded execution for FRC robots

## 🦋 Features
**Some key features of Flytrap:**

* 💜 Written in Kotlin

* ⚡️ Fast

* 🦾 Powerful

* ⚙️ Component based

* ⚛️ Null-safe

* 🔒 Thread-safe

* ⏱ Lifecycle-aware

* 🔨 Customizable

* 📦 Modular

* 🔋 Batteries included

**Also includes:**

- [x] Message based interaction between components via ObjectMessages
- [x] Core components for hardware interaction and communication
- [x] Flytrap Logger
- [x] Debug Server and Remote Configuration
- [x] MultimethodServer component for custom HTTP communication

### ⚠️ DISCLAIMER: Flytrap is in alpha stage, and it's still under active development. As it's a new library it will likely to have more issues. That also means core interfaces and references can change at any time. 
Please make sure that your team can/will handle any possible problem and future change before using Flytrap in a robot codebase. 

We don't take responsibility for any form of failure or unexpected behaviour.

## Getting started

### Create a robot component
In Flytrap, each component is a `LifecycleObject`.
`LifecycleObjects` are units that can run periodic task, receive or send messages, and launch servers.

We will create a robot component that will persist during different robot states and modes `RobotComponent.kt`:
```
class RobotComponent(context: LifecycleContext) : LifecycleObject(context) {
    override fun onTick(context: ObjectContext<*>) {}
}
```

### Implement custom onCreate
onCreate and onDestroy methods are called once per object. These methods may contain special initialization and destruction code.

We will implement a custom onCreate that logs a simple message using FlytrapLogger `RobotComponent.kt`:
```
val tag = "RobotComponent"

override fun onCreate(context: ObjectContext<*>) {
    super.onCreate(context)
    log(tag, "This is my first component!")
}
```

### Create your FlytrapRobot
Your FlytrapRobot class provides your main components for robot and operation modes (auto, teleop).

We will add `RobotComponent` to our robot class `MyRobot.kt`:
```
class MyRobot(context: LifecycleContext) : FlytrapRobot(context) {
    override fun onTick(context: ObjectContext<*>) {}
    
    override fun robotComponent(): ObjectReference<out LifecycleObject> = ObjectReference(RobotComponent::class.java)
}
```

### Define an entrypoint
In order to run your robot code you must define an entrypoint function that will launch the robot.

We will create a new class and add launcher code `RobotLauncher.kt`:
```
fun main() {
    runFlytrapRobot(MyRobot::class.java)
}
```

## Congratulations you've just created your own FlytrapRobot 🎉
Now deploy it to your robot using GradleRIO or WPILib plugins



## Need help?
### Ask your questions on Discord: https://discord.gg/DbTQHenjhK
### Seeing a problem? Open an issue on Github.
