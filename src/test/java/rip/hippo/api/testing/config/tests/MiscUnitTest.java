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

import org.bukkit.util.Vector;
import org.junit.Test;
import rip.hippo.api.testing.config.UnitTest;
import rip.hippo.api.testing.config.data.HippoEntity;
import rip.hippo.api.testing.config.data.World;

import java.util.Random;

/**
 * @author Hippo
 * @version 1.0.0, 9/11/20
 * @since 1.0.0
 */
public final class MiscUnitTest extends UnitTest {

  @Test
  public void collectionsTest() {
    World world = new World("NewWorld");
    Random random = new Random();
    for (int i = 0; i < 5; i++) {
      world.getPositions().add(new Vector(random.nextInt(), random.nextInt(), random.nextInt()));
    }

    configAdapterPool.getAdapter("WorldConfig")
        .map(world)
        .save();


    World throwAway = new World("ThrowAway");
    configAdapterPool.getAdapter("WorldConfig")
        .map(throwAway)
        .update();
    System.out.println(throwAway);
  }

  @Test
  public void inheritanceTest() {
    HippoEntity hippo = new HippoEntity("Bungus", new rip.hippo.api.testing.config.data.Vector(69, 69, 69), 5);

    configAdapterPool.getAdapter("HippoConfig")
        .map(hippo)
        .save();

    HippoEntity read = new HippoEntity();
    configAdapterPool.getAdapter("HippoConfig")
        .map(read)
        .update();
  }

  @Test
  public void nonExistentConfig() {
    System.out.println(configAdapterPool.getAdapter("Dont_Exist").exists());
  }
}
