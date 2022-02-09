package com.teambutterflyeffect.flytrap.system.error

enum class ErrorCode(i: Int) {
    ERROR(100),
    NONE(101),
    REMOTE(102),
    HARDWARE_FAULT(110),
    HW_POWER_FAULT(111),
    HW_CONTROLLER_FAULT(112),
    HW_DRIVER_FAULT(113),
    HW_PNEUMATICS_FAULT(114),
    SOFTWARE_FAULT(120),
    TEST(130),
    EMERGENCY(140),
}