package org.eclipse.yasson.internal.deserializer;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Optional;

import javax.json.JsonValue;
import javax.json.bind.JsonbException;

import org.eclipse.yasson.internal.ReflectionUtils;
import org.eclipse.yasson.internal.RuntimeTypeInfo;
import org.eclipse.yasson.internal.deserializer.deserializers.ContainerArray;
import org.eclipse.yasson.internal.deserializer.deserializers.ContainerGenericArrayFromArray.ComponentType;
import org.eclipse.yasson.internal.deserializer.deserializers.ContainerObject;
import org.eclipse.yasson.internal.deserializer.deserializers.ContainerPoJoFromObject;
import org.eclipse.yasson.internal.deserializer.deserializers.Containers.ArrayContainerBuilder;
import org.eclipse.yasson.internal.deserializer.deserializers.Containers.ObjectContainerBuilder;
import org.eclipse.yasson.internal.model.customization.PropertyCustomization;
import org.eclipse.yasson.internal.properties.MessageKeys;
import org.eclipse.yasson.internal.properties.Messages;

// This class is optimized combination of org.eclipse.yasson.internal.ReflectionUtils
// and org.eclipse.yasson.internal.serializer.DeserializerBuilder.
/**
 * Target Java type resolver.
 */
public final class ResolveType {

    private ResolveType() {
        throw new UnsupportedOperationException("Instances of ResolveType are not allowed.");
    }

    /**
     * Select and initialize container deserializer for JSON object parsing.
     *
     * @param uCtx parser context
     * @return container deserializer for JSON object
     */
    static ContainerObject<?, ?, ?> deserializerForObject(ParserContext uCtx, StackNode parent) {
        return deserializerForObject(uCtx, parent.getContainer().valueType());
    }

    /**
     * Select and initialize container deserializer for JSON object parsing.
     *
     * @param uCtx parser context
     * @param type unresolved target Java type
     * @return container deserializer for JSON object
     */
    public static ContainerObject<?, ?, ?> deserializerForObject(ParserContext uCtx, Type type) {
        Class<?> typeClass;
        if (type instanceof Class) {
            typeClass = (Class<?>) type;
            final ObjectContainerBuilder cb = uCtx.getContainers().objectContainerBuilder(typeClass);
            if (cb != null) {
                return cb.newInstance(typeClass, Object.class, Object.class);
            }
            if (JsonValue.class.isAssignableFrom(typeClass)) {
                return null; // TODO Return JsonObjectDeserializer
            } else if (typeClass.isInterface()) {
                Class<?> mappedType = getInterfaceMappedType(uCtx, typeClass, null);
                if (mappedType == null) {
                    throw new JsonbException(
                            Messages.getMessage(MessageKeys.INFER_TYPE_FOR_UNMARSHALL, typeClass.getName()));
                }
                return ContainerPoJoFromObject.newInstance(mappedType, String.class, Object.class);
            } else {
                return ContainerPoJoFromObject.newInstance(typeClass, String.class, Object.class);
            }
        } else if (type instanceof ParameterizedType) {
            typeClass = (Class<?>) ((ParameterizedType) type).getRawType();
            final ObjectContainerBuilder cb = uCtx.getContainers().objectContainerBuilder(typeClass);
            if (cb != null) {
                final Type[] genericsArray = ((ParameterizedType) type).getActualTypeArguments();
                Type valueType;
                Type keyType;
                if (genericsArray.length > 0) {
                    if (genericsArray.length > 1) {
                        keyType = genericsArray[0] != null ? genericsArray[0] : Object.class;
                        valueType = genericsArray[1] != null ? genericsArray[1] : Object.class;
                    } else {
                        valueType = genericsArray[0] != null ? genericsArray[0] : Object.class;
                        keyType = Object.class;
                    }
                } else {
                    valueType = Object.class;
                    keyType = Object.class;
                }
                return cb.newInstance(typeClass, keyType, valueType);
            }
            if (JsonValue.class.isAssignableFrom(typeClass)) {
                return null; // TODO Return JsonObjectDeserializer
            } else if (typeClass.isInterface()) {
                Class<?> mappedType = getInterfaceMappedType(uCtx, typeClass, null);
                if (mappedType == null) {
                    throw new JsonbException(
                            Messages.getMessage(MessageKeys.INFER_TYPE_FOR_UNMARSHALL, typeClass.getName()));
                }
                return ContainerPoJoFromObject.newInstance(mappedType, String.class, Object.class);
            } else {
                return ContainerPoJoFromObject.newInstance(typeClass, String.class, Object.class);
            }
        }
        throw new JsonbException(Messages.getMessage(MessageKeys.TYPE_RESOLUTION_ERROR, type));
    }

    /**
     * Select and initialize container deserializer for JSON array parsing.
     *
     * @param uCtx parser context
     * @param type unresolved target Java type
     * @return container deserializer for JSON object
     */
    public static ContainerArray<?, ?> deserializerForArray(ParserContext uCtx, Type type) {
        Class<?> typeClass;
        if (type instanceof Class) {
            typeClass = (Class<?>) type;
            final ArrayContainerBuilder ab = uCtx.getContainers().arrayContainerBuilder(typeClass);
            if (ab != null) {
                final ContainerArray<?, ?> container =  ab.newInstance(
                        typeClass, typeClass.isArray() ? typeClass.getComponentType() : Object.class);
                if (container.isMap()) {
                    container.mapKey().setKeyType(Object.class);
                }
                return container;
            }
            if (JsonValue.class.isAssignableFrom((Class<?>) type)) {
                return null; // TODO Return JsonArrayDeserializer
            } else {
                return uCtx.getContainers().arrayContainer(
                        typeClass, typeClass.isArray() ? typeClass.getComponentType() : Object.class);
            }
        } else if (type instanceof GenericArrayType) {
            Class<?> component = (Class<?>) ((GenericArrayType) type).getGenericComponentType();
            return uCtx.getContainers().arrayContainer(type, (Class<?>) type, component);
        } else if (type instanceof ParameterizedType) {
            typeClass = (Class<?>) ((ParameterizedType) type).getRawType();
            final ArrayContainerBuilder ab = uCtx.getContainers().arrayContainerBuilder(typeClass);
            if (ab != null) {
                final Type[] genericsArray = ((ParameterizedType) type).getActualTypeArguments();
                Type valueType;
                Type keyType;
                if (genericsArray.length > 0) {
                    if (genericsArray.length > 1) {
                        keyType = genericsArray[0] != null ? genericsArray[0] : Object.class;
                        valueType = genericsArray[1] != null ? genericsArray[1] : Object.class;
                    } else {
                        valueType = genericsArray[0] != null ? genericsArray[0] : Object.class;
                        keyType = Object.class;
                    }
                } else {
                    valueType = Object.class;
                    keyType = Object.class;
                }
                final ContainerArray<?, ?> container =  ab.newInstance(
                        typeClass, typeClass.isArray() ? typeClass.getComponentType() : valueType);
                if (container.isMap()) {
                    container.mapKey().setKeyType(keyType);
                }
                return container;
            }
            if (JsonValue.class.isAssignableFrom(typeClass)) {
                return null; // TODO Return JsonObjectDeserializer
            } else {
                final Type[] argsTypes = ((ParameterizedType) type).getActualTypeArguments();
                final Type valueType = argsTypes.length > 0 ? argsTypes[0] : Object.class;
                if (typeClass.isArray() || Collection.class.isAssignableFrom(typeClass)) {
                    return uCtx.getContainers().arrayContainer(typeClass, valueType);
                }
                throw new JsonbException("Can't deserialize JSON array into: " + typeClass.getName());
            }
        }
        throw new JsonbException(Messages.getMessage(MessageKeys.TYPE_RESOLUTION_ERROR, type));
    }

    private static Class<?> getInterfaceMappedType(ParserContext uCtx, Class<?> interfaceType,
            PropertyCustomization customization) {
        Class<?> implementationClass = null;
        if (customization != null) {
            implementationClass = ((PropertyCustomization) customization).getImplementationClass();
        }
        // JsonbConfig
        if (implementationClass == null) {
            implementationClass = uCtx.getJsonbContext().getConfigProperties().getUserTypeMapping().get(interfaceType);
        }
        if (implementationClass != null) {
            if (!interfaceType.isAssignableFrom(implementationClass)) {
                throw new JsonbException(
                        Messages.getMessage(MessageKeys.IMPL_CLASS_INCOMPATIBLE, implementationClass, interfaceType));
            }
            return implementationClass;
        }
        return null;
    }

    /**
     * Resolve generic type from {@link Optional}.
     *
     * @param type {@link Optional} type to resolve
     * @return generic type from {@link Optional} or {@code Object.classs} if type could not be resolved.
     */
    public static Type resolveOptionalType(Type type) {
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments()[0];
        }
        return Object.class;
    }

    /**
     * Resolve container property type.
     * Not doing {@code instanceof Class} check which shall be done before calling this method.
     *
     * @param item container type information
     * @param type property type
     * @return resolved type
     */
    public static Type resolveType(RuntimeTypeInfo item, Type type) {
        if (type instanceof TypeVariable) {
            return ReflectionUtils.resolveItemVariableType(item, (TypeVariable<?>) type);
        } else if (type instanceof ParameterizedType) {
            return ReflectionUtils.resolveTypeArguments((ParameterizedType) type, item.getRuntimeType());
        } else if (type instanceof GenericArrayType) {
            return new ComponentType(ReflectionUtils
                    .resolveRawType(item, ((GenericArrayType) type).getGenericComponentType()));
//            return type;//ReflectionUtils.resolveRawType(item, ((GenericArrayType)type).getGenericComponentType());
        } else if (type instanceof WildcardType) {
            return resolveMostSpecificBound((WildcardType) type);
        }
        throw new JsonbException(Messages.getMessage(MessageKeys.TYPE_RESOLUTION_ERROR, type));
    }

    static Class<?> resolveSimpleType(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        } else if (type instanceof GenericArrayType) {
            return ((GenericArrayType) type).getClass();
        }
        throw new JsonbException(Messages.getMessage(MessageKeys.TYPE_RESOLUTION_ERROR, type));
    }

    /**
     * Resolve generic type.
     *
     * @param type type to be resolved
     * @return resolved class of generic type
     */
    public static Class<?> resolveGenericType(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        } else if (type instanceof TypeVariable) {
            ReflectionUtils.resolveRawType(null, type);
        } else if (type instanceof WildcardType) {
            return resolveMostSpecificBound((WildcardType) type);
        }
        throw new JsonbException(Messages.getMessage(MessageKeys.TYPE_RESOLUTION_ERROR, type));
    }

    private static Class<?> resolveMostSpecificBound(WildcardType wildcardType) {
        Class<?> result = Object.class;
        for (Type upperBound : wildcardType.getUpperBounds()) {
            result = getMostSpecificBound(result, upperBound);
        }
        for (Type lowerBound : wildcardType.getLowerBounds()) {
            result = getMostSpecificBound(result, lowerBound);
        }
        return result;
    }

    private static Class<?> getMostSpecificBound(Class<?> result, Type bound) {
        if (bound == Object.class) {
            return result;
        }
        Class<?> boundRawType = resolveSimpleType(bound);
        // resolved class is a subclass of a result candidate
        if (result.isAssignableFrom(boundRawType)) {
            result = boundRawType;
        }
        return result;
    }

}
