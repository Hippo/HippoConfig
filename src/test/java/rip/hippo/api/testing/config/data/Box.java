/*
 * MIT License
 *
 * Copyright (c) 2020 Hippo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rip.hippo.api.testing.config.data;

import rip.hippo.config.annotation.SerializedKey;
import rip.hippo.config.map.Mappable;

/**
 * @author Hippo
 * @version 1.0.0, 9/9/20
 * @since 1.0.0
 */
public final class Box implements Mappable {

    @SerializedKey("vec1")
    private Vector firstCorner;
    @SerializedKey("vec2")
    private Vector secondCorner;

    public Box() {
        this(new Vector(), new Vector());
    }

    public Box(Vector firstCorner, Vector secondCorner) {
        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;
    }

    public Vector getFirstCorner() {
        return firstCorner;
    }

    public void setFirstCorner(Vector firstCorner) {
        this.firstCorner = firstCorner;
    }

    public Vector getSecondCorner() {
        return secondCorner;
    }

    public void setSecondCorner(Vector secondCorner) {
        this.secondCorner = secondCorner;
    }

    @Override
    public String toString() {
        return "Box{" +
                "firstCorner=" + firstCorner +
                ", secondCorner=" + secondCorner +
                '}';
    }
}
