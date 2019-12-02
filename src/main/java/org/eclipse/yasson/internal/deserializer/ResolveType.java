package org.eclipse.yasson.internal.deserializer;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.json.JsonValue;
import javax.json.bind.JsonbException;

import org.eclipse.yasson.internal.ReflectionUtils;
import org.eclipse.yasson.internal.RuntimeTypeInfo;
import org.eclipse.yasson.internal.deserializer.deserializers.ContainerArray;
import org.eclipse.yasson.internal.deserializer.deserializers.ContainerObject;
import org.eclipse.yasson.internal.deserializer.deserializers.ContainerPoJoFromObject;
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
     * @param type unresolved target Java type
     * @return container deserializer for JSON object
     */
    static ContainerObject<?, ?, ?> deserializerForObject(ParserContext uCtx, Type type) {
        Class<?> typeClass;
        if (type instanceof Class) {
            typeClass = (Class<?>) type;
            if (JsonValue.class.isAssignableFrom(typeClass)) {
                return null; // TODO Return JsonObjectDeserializer
            } else if (typeClass == Object.class || Map.class.isAssignableFrom(typeClass)) {
                return uCtx.getContainers().objectContainer(
                        uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(typeClass), String.class,
                        Object.class);
            } else if (typeClass.isInterface()) {
                Class<?> mappedType = getInterfaceMappedType(uCtx, typeClass, null);
                if (mappedType == null) {
                    throw new JsonbException(
                            Messages.getMessage(MessageKeys.INFER_TYPE_FOR_UNMARSHALL, typeClass.getName()));
                }
                return ContainerPoJoFromObject.newInstance(
                        uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(mappedType), String.class,
                        Object.class);
            } else {
                return ContainerPoJoFromObject.newInstance(
                        uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(typeClass), String.class,
                        Object.class);
            }
        } else if (type instanceof ParameterizedType) {
            typeClass = (Class<?>) ((ParameterizedType) type).getRawType();
            if (JsonValue.class.isAssignableFrom(typeClass)) {
                return null; // TODO Return JsonObjectDeserializer
            } else if (Map.class.isAssignableFrom(typeClass)) {
                // Array will never be null, but may be empty.
                Type[] genericsArray = ((ParameterizedType) type).getActualTypeArguments();
                Class<?> valueClass;
                if (genericsArray.length > 1 && genericsArray[1] != null) {
                    Type valueType = genericsArray[1];
                    valueClass = resolveGenericType(valueType);
                } else {
                    valueClass = Object.class;
                }
                return uCtx.getContainers().objectContainer(
                        uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(typeClass), String.class,
                        valueClass);
            } else if (typeClass.isInterface()) {
                Class<?> mappedType = getInterfaceMappedType(uCtx, typeClass, null);
                if (mappedType == null) {
                    throw new JsonbException(
                            Messages.getMessage(MessageKeys.INFER_TYPE_FOR_UNMARSHALL, typeClass.getName()));
                }
                return ContainerPoJoFromObject.newInstance(
                        uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(mappedType), String.class,
                        Object.class);
            } else {
                return ContainerPoJoFromObject.newInstance(
                        uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(typeClass), String.class,
                        Object.class);
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
    static ContainerArray<?, ?> deserializerForArray(ParserContext uCtx, Type type) {
        if (type instanceof Class) {
            if (type == Object.class) {
                return uCtx.getContainers().arrayContainer(
                        uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel((Class<?>) type),
                        Object.class);
            } else if (JsonValue.class.isAssignableFrom((Class<?>) type)) {
                return null; // TODO Return JsonArrayDeserializer
            } else if (((Class<?>) type).isArray()) {
                return uCtx.getContainers().arrayContainer(
                        uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel((Class<?>) type),
                        ((Class<?>) type).getComponentType());
            } else {
                return uCtx.getContainers().arrayContainer(
                        uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel((Class<?>) type),
                        Object.class);
            }
        } else if (type instanceof GenericArrayType) {
            final Class<?> typeClass = ((GenericArrayType) type).getClass();
            final Type valueType = ((GenericArrayType) type).getGenericComponentType();
            return selectDeserializerForArray(uCtx, typeClass, resolveGenericType(valueType));
        } else if (type instanceof ParameterizedType) {
            final Class<?> typeClass = (Class<?>) ((ParameterizedType) type).getRawType();
            final Type[] argsTypes = ((ParameterizedType) type).getActualTypeArguments();
            final Type valueType = argsTypes.length > 0 ? argsTypes[0] : Object.class;
            return selectDeserializerForArray(uCtx, typeClass, resolveGenericType(valueType));
        }
        throw new JsonbException(Messages.getMessage(MessageKeys.TYPE_RESOLUTION_ERROR, type));
    }

    private static ContainerArray<?, ?> selectDeserializerForArray(ParserContext uCtx, Class<?> typeClass,
            Class<?> valueClass) {
        if (typeClass == Object.class) {
            return uCtx.getContainers().arrayContainer(
                    uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(typeClass), valueClass);
        } else if (JsonValue.class.isAssignableFrom(typeClass)) {
            return null; // TODO Return JsonArrayDeserializer
        } else if (typeClass.isArray()) {
            return uCtx.getContainers().arrayContainer(
                    uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(typeClass), valueClass);
        } else if (Collection.class.isAssignableFrom(typeClass)) {
            return uCtx.getContainers().arrayContainer(
                    uCtx.getJsonbContext().getMappingContext().getOrCreateClassModel(typeClass), valueClass);
        } else {
            throw new JsonbException("Can't deserialize JSON array into: " + typeClass.getName());
        }
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

    static Class<?> resolveGenericType(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
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
