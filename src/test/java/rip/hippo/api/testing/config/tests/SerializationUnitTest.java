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

package rip.hippo.api.testing.config.tests;

import org.junit.Test;
import rip.hippo.api.testing.config.UnitTest;
import rip.hippo.api.testing.config.data.Box;
import rip.hippo.api.testing.config.data.Vector;


/**
 * @author Hippo
 * @version 2.0.0, 9/9/20
 * @since 1.0.0
 */
public final class SerializationUnitTest extends UnitTest {

    @Test
    public void boxTest() {
        Box box = new Box(new Vector(69, 69, 69), new Vector(420, 420, 420));

        configAdapterPool.getAdapter("NewBox")
                .header("Cool header test")
                .map(box)
                .save();
    }
}
