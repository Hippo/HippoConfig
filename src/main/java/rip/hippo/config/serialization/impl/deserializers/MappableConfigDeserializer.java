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

package rip.hippo.config.serialization.impl.deserializers;

import org.bukkit.configuration.file.FileConfiguration;
import rip.hippo.config.annotation.SerializedKey;
import rip.hippo.config.map.Mappable;
import rip.hippo.config.serialization.ConfigDeserializer;
import rip.hippo.config.serialization.manage.TypeSerializationManager;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Hippo
 * @version 1.0.0, 9/10/20
 * @since 1.0.0
 */
public final class MappableConfigDeserializer implements ConfigDeserializer<Mappable> {

  private final TypeSerializationManager typeSerializationManager;

  public MappableConfigDeserializer(TypeSerializationManager typeSerializationManager) {
    this.typeSerializationManager = typeSerializationManager;
  }

  @Override
  public Mappable deserialize(FileConfiguration fileConfiguration, String key, Object instance) {
    if (!(instance instanceof Mappable)) {
      throw new IllegalArgumentException(String.format("Tried to pass %s as a mappable object.", instance));
    }
    List<Field> fields = new LinkedList<>();
    Class<?> current = instance.getClass();
    while (Mappable.class.isAssignableFrom(current)) {
      fields.addAll(Arrays.asList(current.getDeclaredFields()));
      current = current.getSuperclass();
    }

    for (Field field : fields) {
      try {
        if (Modifier.isTransient(field.getModifiers())) {
          continue;
        }
        if (Modifier.isFinal(field.getModifiers())) {
          Field modifiers = Field.class.getDeclaredField("modifiers");
          modifiers.setAccessible(true);
          modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        }

        boolean accessible = field.isAccessible();
        field.setAccessible(true);


        SerializedKey serializedKey = field.getAnnotation(SerializedKey.class);
        String keyField = serializedKey == null ? field.getName() : serializedKey.value();

        ConfigDeserializer<?> deserializer = typeSerializationManager.getDeserializer(field.getType());
        field.set(instance, deserializer.deserialize(fileConfiguration, (key.isEmpty() ? "" : key + ".") + keyField, field.get(instance)));

        field.setAccessible(accessible);
      } catch (ReflectiveOperationException e) {
        e.printStackTrace();
      }
    }
    return ((Mappable) instance);
  }
}
