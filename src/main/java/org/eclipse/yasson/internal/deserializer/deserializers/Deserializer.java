package org.eclipse.yasson.internal.deserializer.deserializers;

import org.eclipse.yasson.internal.deserializer.ParserContext;

/**
 * JSON structure deserializer.
 * <p>
 * Deserializers are of two basic types:<ul>
 * <li>primitive type deserializer</li>
 * <li>container deserializer</li></ul>
 * <p>
 * Primitive type deserializer
 * <p>
 * Builds proper value for JSON primitive types ({@code string}, {@code number}, {@code true}, {@code false}
 * and {@code null}).
 * Child classes must be stateless to be used as singletons.
 * <p>
 * Container deserializer
 * <p>
 * Builds proper type for complex JSON types: <i>JSON object</i> and <i>JSON array</i>.
 * Child classes are not stateless because they hold Java values container being build.
 *
 * @param <T> the type of returned value
 */
public abstract class Deserializer<T> {

    /**
     * Build Java value from current JSON parser value.
     * Implementing code can only retrieve value from the parser. It shall not change its current position.
     *
     * @param uCtx deserialization context
     * @return deserialized value
     */
    public abstract T deserialize(ParserContext uCtx);

}
