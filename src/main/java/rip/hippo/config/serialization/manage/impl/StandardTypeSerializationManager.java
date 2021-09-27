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

package rip.hippo.config.serialization.manage.impl;

import rip.hippo.config.map.Mappable;
import rip.hippo.config.serialization.ConfigDeserializer;
import rip.hippo.config.serialization.ConfigSerializer;
import rip.hippo.config.serialization.impl.deserializers.MappableConfigDeserializer;
import rip.hippo.config.serialization.impl.deserializers.PrimitiveConfigDeserializer;
import rip.hippo.config.serialization.impl.serializers.MappableConfigSerializer;
import rip.hippo.config.serialization.impl.serializers.PrimitiveConfigSerializer;
import rip.hippo.config.serialization.manage.TypeSerializationManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hippo
 * @version 1.0.0, 9/9/20
 * @since 1.0.0
 */
public final class StandardTypeSerializationManager implements TypeSerializationManager {

  private final Map<Class<?>, ConfigSerializer> typeSerializerMap;
  private final Map<Class<?>, ConfigDeserializer<?>> typeDeserializerMap;

  private final ConfigSerializer primitiveConfigSerializer, mappableConfigSerializer;
  private final ConfigDeserializer<?> primitiveConfigDeserializer, mappableConfigDeserializer;

  public StandardTypeSerializationManager() {
    this.typeSerializerMap = new HashMap<>();
    this.typeDeserializerMap = new HashMap<>();
    this.primitiveConfigSerializer = new PrimitiveConfigSerializer();
    this.mappableConfigSerializer = new MappableConfigSerializer(this);
    this.primitiveConfigDeserializer = new PrimitiveConfigDeserializer();
    this.mappableConfigDeserializer = new MappableConfigDeserializer(this);

    this.typeSerializerMap.put(Mappable.class, mappableConfigSerializer);
    this.typeDeserializerMap.put(Mappable.class, mappableConfigDeserializer);
  }

  @Override
  public void register(Class<?> type, ConfigSerializer configSerializer) {
    typeSerializerMap.put(type, configSerializer);
  }

  @Override
  public void register(Class<?> type, ConfigDeserializer<?> configDeserializer) {
    typeDeserializerMap.put(type, configDeserializer);
  }

  @Override
  public void registerMappable(Class<? extends Mappable> mappableClass) {
    typeSerializerMap.put(mappableClass, mappableConfigSerializer);
    typeDeserializerMap.put(mappableClass, mappableConfigDeserializer);
  }

  @Override
  public ConfigSerializer getSerializer(Class<?> type) {
    return typeSerializerMap.getOrDefault(type, primitiveConfigSerializer);
  }

  @Override
  public ConfigDeserializer<?> getDeserializer(Class<?> type) {
    return typeDeserializerMap.getOrDefault(type, primitiveConfigDeserializer);
  }
}
