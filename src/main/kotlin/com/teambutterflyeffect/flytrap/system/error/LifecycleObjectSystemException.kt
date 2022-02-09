package com.teambutterflyeffect.flytrap.system.error

open class LifecycleObjectSystemException(objClass: Class<*>, message: String, cause: Throwable?) : Exception("Object \"${objClass.simpleName}\" cannot be created. $message", cause)

class UnknownLifecycleObjectConstructorException(objClass: Class<*>, cause: Throwable?) :
    LifecycleObjectSystemException(objClass, "Object does not have an open constructor with default pattern: Object(LifecycleProvider).", cause)