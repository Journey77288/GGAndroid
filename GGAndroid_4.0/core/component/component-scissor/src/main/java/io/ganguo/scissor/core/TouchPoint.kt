/*
 * Copyright (C) 2015 Lyft, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.ganguo.scissor.core

class TouchPoint {
    var x = 0f
        private set
    var y = 0f
        private set

    constructor() {}
    constructor(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    val length: Float
        get() = Math.sqrt((x * x + y * y).toDouble()).toFloat()

    fun copy(other: TouchPoint): TouchPoint {
        x = other.x
        y = other.y
        return this
    }

    operator fun set(x: Float, y: Float): TouchPoint {
        this.x = x
        this.y = y
        return this
    }

    fun add(value: TouchPoint): TouchPoint {
        x += value.x
        y += value.y
        return this
    }

    override fun toString(): String {
        return String.format("(%.4f, %.4f)", x, y)
    }

    companion object {
        fun subtract(lhs: TouchPoint, rhs: TouchPoint): TouchPoint {
            return TouchPoint(lhs.x - rhs.x, lhs.y - rhs.y)
        }
    }
}