package com.teambutterflyeffect.flytrap.component.fvm2.protocol

open class VisionMapEntity(
    id: String, probability: Float, x_0: Float, y_0: Float, x_1: Float, y_1: Float, val distanceToReference: Float
) : VisionEntity(id, probability, x_0, y_0, x_1, y_1) {

    constructor(entity: VisionEntity, distanceToReference: Float) : this(
        entity.id,
        entity.probability,
        entity.x_0,
        entity.y_0,
        entity.x_1,
        entity.y_1,
        distanceToReference
    )
}