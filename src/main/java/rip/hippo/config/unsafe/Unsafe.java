package rip.hippo.config.unsafe;

import java.lang.reflect.Field;

/**
 * @author Hippo
 * @version 1.0.0, 7/2/21
 * @since 1.0.4
 */
public enum Unsafe {
  ;

  private static sun.misc.Unsafe unsafe;

  public static sun.misc.Unsafe getUnsafe() {
    if (unsafe == null) {
      try {
        Field theUnsafe = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        unsafe = (sun.misc.Unsafe) theUnsafe.get(null);
      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    }
    return unsafe;
  }
}
