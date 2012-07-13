package kw.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.primitives.Primitives;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This class is responsible for handling conversion of
 * values of object of a RunTimeType class between
 * their runtime value representation and their Storage Result get method
 * database representation.
 * <p/>
 * Each integralType of FieldTypeManager should be registered in
 * FieldTypeManager.Registry in order to be used by the system.
 * <p/>
 */
public abstract class FieldTypeManager<RunTimeType> {

    protected FieldTypeManager(
            Class<RunTimeType> typeClass,
            Formatter<? super RunTimeType> typeValueFormat,
            Parser<? super RunTimeType> typeValueParser
    ) {
        this.typeClass = typeClass;
        this.typeValueFormat = typeValueFormat;
        this.typeValueParser = typeValueParser;
    }


    /**
     * A class that represent the SQL formatter part of a TypeManager
     */
    public abstract static class Formatter<Type> {
        public abstract String toEngineValue(Type value);
    }

    /**
     * A class that represent the DBManager parser part of a TypeManager
     */
    public abstract static class Parser<Type> {
        public abstract Type toRuntimeValue(Object value);

    }

    private final Class<RunTimeType> typeClass;
    private final Formatter<? super RunTimeType> typeValueFormat;
    private final Parser<? super RunTimeType> typeValueParser;
    private static final DecimalFormatSymbols formatSymbols = DecimalFormatSymbols.getInstance(Locale.getDefault());

    public final static Formatter<CharSequence> CHAR_SEQUENCE_TYPE_FORMATTER = new Formatter<CharSequence>() {
        @Override public String toEngineValue(CharSequence value) {

            return value.toString();
        }
    };
    public  final static Formatter<Character> CHAR_TYPE_FORMATTER = new Formatter<Character>() {
        @Override public String toEngineValue(Character value) {
            if (Objects.equal(value, '\0')) return "";
            return CHAR_SEQUENCE_TYPE_FORMATTER.toEngineValue(value.toString());
        }
    };
    public  final static Formatter<Number> INTEGRAL_TYPE_FORMATTER = new Formatter<Number>() {
        private final NumberFormat FORMAT = new DecimalFormat("0",formatSymbols);

        @Override public String toEngineValue(Number value) {
            return FORMAT.format(value.longValue());
        }
    };
    public  final static Formatter<Number> DECIMAL_TYPE_FORMATTER = new Formatter<Number>() {
        private final NumberFormat FORMAT = new DecimalFormat("0.0###########################", formatSymbols);

        @Override public String toEngineValue(Number value) {
            return FORMAT.format(value.doubleValue());
        }
    };

    /**
     * This pair of Class formatter and parser allow to save to db Class
     * instances.
     */
    public  final static Formatter<Class> CLASS_FORMATTER = new Formatter<Class>() {

        @Override public String toEngineValue(Class value) {


            return getStringTypeManager().toEngineValue(value.getName());
        }


    };
    private static FieldTypeManager<String> stringFieldTypeManager;

    private static FieldTypeManager<String> getStringTypeManager() {
        if (stringFieldTypeManager ==null)
            stringFieldTypeManager = Registry.findManager(String.class);
        if (stringFieldTypeManager ==null)
            throw new RuntimeException("cannot find manager for String class");
        return stringFieldTypeManager;
    }

    private static FieldTypeManager<Class> classFieldTypeManager;

    private static FieldTypeManager<Class> getClassFieldTypeManager() {
        if (classFieldTypeManager ==null)
            classFieldTypeManager = Registry.findManager(Class.class);
        if (classFieldTypeManager ==null)
            throw new RuntimeException("cannot find manager for Class class");
        return classFieldTypeManager;
    }

    /**
     * This pair of Class formatter and parser allow to save to db Class
     * instances.
     */
    public  final static Parser<Class> CLASS_PARSER = new Parser<Class>() {


        @Override public Class toRuntimeValue(Object value) {

            try {
                return Class.forName( getStringTypeManager().toRuntimeValue(value.toString()));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("class not found for "+ value.toString(),e);
            }
        }
    };

    /**
     * This parser simply return for the run time the same object
     * retrieved by the Storage object.
     */
    public  final static Parser<Object> DEFAULT_PARSER = new Parser<Object>() {
        @Override public Object toRuntimeValue(Object value) {
            return value;
        }
    };


    /**
     * This sets of parsers return the appropriate number instance
     * for each Number instance given as input. It thrown an exception
     * if value is not a number.
     */
    public  final static Parser<Number> LONG_PARSER = new NumberParser() {
        protected Number toExactObject(Number n) {
            return n.longValue();
        }
    };
    public  final static Parser<Number> SHORT_PARSER = new NumberParser() {
        protected Number toExactObject(Number n) {
            return n.shortValue();
        }
    };
    public  final static Parser<Number> INT_PARSER = new NumberParser() {
        protected Number toExactObject(Number n) {
            return n.intValue();
        }
    };
    public  final static Parser<Number> BYTE_PARSER = new NumberParser() {
        protected Number toExactObject(Number n) {
            return n.byteValue();
        }
    };
    public  final static Parser<Number> DOUBLE_PARSER = new NumberParser() {
        protected Number toExactObject(Number n) {
            return n.doubleValue();
        }
    };
    public  final static Parser<Number> FLOAT_PARSER = new NumberParser() {
        protected Number toExactObject(Number n) {
            return n.floatValue();
        }
    };
    public  final static Parser<Number> BIG_DECIMAL_PARSER = new NumberParser() {
        protected Number toExactObject(Number n) {
            return BigDecimal.valueOf(n.doubleValue());
        }
    };

    private final static Parser<StringBuilder> STRING_BUILDER_PARSER = new Parser<StringBuilder>() {
        @Override public StringBuilder toRuntimeValue(Object value) {
            return new StringBuilder(value.toString());
        }
    };
    private final static Parser<StringBuffer> STRING_BUFFER_PARSER = new Parser<StringBuffer>() {
        @Override public StringBuffer toRuntimeValue(Object value) {
            return new StringBuffer(value.toString());
        }
    };
    private final static Parser<CharBuffer> CHAR_BUFFER_PARSER = new Parser<CharBuffer>() {
        @Override public CharBuffer toRuntimeValue(Object value) {
            return CharBuffer.wrap(value.toString());
        }
    };
    private final static Parser<CharSequence> CHAR_SEQUENCE_PARSER = new Parser<CharSequence>() {
        @Override public CharSequence toRuntimeValue(Object value) {
            return CharBuffer.wrap(value.toString());
        }
    };

    private final static Parser<Character> CHARACTER_PARSER = new Parser<Character>() {
        @Override public Character toRuntimeValue(Object value) {
            return value.toString().charAt(0);
        }
    };
    private final static Parser<Number> BIG_INTEGER_PARSER = new NumberParser() {
        protected Number toExactObject(Number n) {
            return BigInteger.valueOf(n.longValue());
        }
    };

    private final static Parser<ReferenceFieldValue> REFERENCE_FIELD_VALUE_PARSER = new Parser<ReferenceFieldValue>() {

        @Override public ReferenceFieldValue toRuntimeValue(Object value) {
            List<String> strings = ImmutableList.copyOf(Splitter.on("|").split(value.toString()));
            Class aClass = getClassFieldTypeManager().toRuntimeValue(strings.get(1));
            ReferenceFieldValue referenceFieldValue = null;
            try {
                referenceFieldValue = ReferenceFieldValue.class.cast(aClass.newInstance());
                Object obj = referenceFieldValue.fromEngine(strings.get(0));
                return ReferenceFieldValue.class.cast(obj);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return null;
        }
    };
    private final static Formatter<ReferenceFieldValue> REFERENCE_FIELD_VALUE_FORMATTER = new Formatter<ReferenceFieldValue>() {
        @Override public String toEngineValue(ReferenceFieldValue value) {
            String fieldValue = value.toEngine();
            return getStringTypeManager().toEngineValue(fieldValue);
        }
    };

    public final static class Registry {
        private static final Map<Class<?>, FieldTypeManager<?>> items = Maps.newHashMap();

        public static <ManagedType> void registerManager(FieldTypeManager<ManagedType> manager, Class<ManagedType> clazz) {
            items.put(clazz, manager);
        }

        public static <ManagedType> void registerDefault(Class<ManagedType> clazz, Formatter<? super ManagedType> formatter, Parser<? super ManagedType> parser) {
            items.put(clazz, new FieldTypeManager<ManagedType>(clazz, formatter, parser) {
            });
        }

        public static <ManagedType> FieldTypeManager<ManagedType> findManager(Class<ManagedType> clazz) {

            Preconditions.checkNotNull(clazz,"clazz parameter");
            FieldTypeManager<ManagedType> typeManager = checkInterfaces(clazz);
            if (typeManager == null)
                typeManager = recurseAllSubClassFindManager(clazz,clazz);
            return typeManager;
        }

        private static <ManagedType> FieldTypeManager<ManagedType> recurseAllSubClassFindManager(Class<?> original, Class<ManagedType> clazz) {
            if (clazz==null)
                throw new RuntimeException("Manager not found for clazz "+original.getName());

            FieldTypeManager<ManagedType> typeManager = (FieldTypeManager<ManagedType>) items.get(clazz);
            if(typeManager == null)
                return FieldTypeManager.class.cast(recurseAllSubClassFindManager(original, clazz.getSuperclass()));

            return typeManager;
        }

        private static <ManagedType> FieldTypeManager<ManagedType> checkInterfaces(Class<ManagedType> clazz) {
            FieldTypeManager<ManagedType> typeManager;
            for (Class aClass:clazz.getInterfaces()){
                typeManager = (FieldTypeManager<ManagedType>) items.get(aClass);
                if(typeManager != null)
                    return typeManager;

            }

            return null;
        }

        public static void initialize() {
            if (!items.isEmpty()) return;
            Registry.registerDefault(Integer.TYPE, INTEGRAL_TYPE_FORMATTER, INT_PARSER);

            Registry.registerDefault(Integer.class, INTEGRAL_TYPE_FORMATTER, INT_PARSER);
            Registry.registerDefault(Long.class, INTEGRAL_TYPE_FORMATTER, LONG_PARSER);
            Registry.registerDefault(Short.class, INTEGRAL_TYPE_FORMATTER, SHORT_PARSER);
            Registry.registerDefault(Byte.class, INTEGRAL_TYPE_FORMATTER, BYTE_PARSER);
            Registry.registerDefault(BigInteger.class, INTEGRAL_TYPE_FORMATTER, BIG_INTEGER_PARSER);

            Registry.registerDefault(Float.class, DECIMAL_TYPE_FORMATTER, FLOAT_PARSER);
            Registry.registerDefault(Double.class, DECIMAL_TYPE_FORMATTER, DOUBLE_PARSER);
            Registry.registerDefault(BigDecimal.class, DECIMAL_TYPE_FORMATTER, BIG_DECIMAL_PARSER);

            Registry.registerDefault(StringBuffer.class, CHAR_SEQUENCE_TYPE_FORMATTER, STRING_BUFFER_PARSER);
            Registry.registerDefault(Character.class, CHAR_TYPE_FORMATTER, CHARACTER_PARSER);
            Registry.registerDefault(String.class, CHAR_SEQUENCE_TYPE_FORMATTER, DEFAULT_PARSER);
            //Registry.registerDefault(CharSequence.class, CHAR_SEQUENCE_TYPE_FORMATTER, CHAR_SEQUENCE_PARSER);
            Registry.registerDefault(CharBuffer.class, CHAR_SEQUENCE_TYPE_FORMATTER, CHAR_BUFFER_PARSER);
            Registry.registerDefault(StringBuilder.class, CHAR_SEQUENCE_TYPE_FORMATTER, STRING_BUILDER_PARSER);
            Registry.registerDefault(Class.class, CLASS_FORMATTER, CLASS_PARSER);
            Registry.registerDefault(ReferenceFieldValue.class, REFERENCE_FIELD_VALUE_FORMATTER, REFERENCE_FIELD_VALUE_PARSER);


        }
    }

    /**
     * Return a string representation of a value of this
     * type in the SQL language.
     * <p/>
     * This implementation return "NULL" if value is null, otherwise the result of
     * calling typeValueFormat.format(), where typeValueFormat is the Format integralType
     * passed as a parameter to the constructor of this object integralType.
     * <p/>
     * Instances of Format passed to the constructor as thus not required to
     * format null value.
     *
     * @param value the value to convert. Maybe null.
     * @return the string representation of a value of this
     *         type in the storage engine language, not quoted in any
     *         way.
     *         <p/>
     */
    public final String toEngineValue(RunTimeType value) {
        return value == null ? "" : typeValueFormat.toEngineValue(value);
    }

    /**
     * Return a representation of a value of this
     * type suitable be uses at run-time type.
     * Receive as parameter a value returned by a Storage implementation
     * Result Row get method.
     * <p/>
     * This implementation return "NULL" if value is null, otherwise the result of
     * calling typeValueFormat.format(), where typeValueFormat is the Format integralType
     * passed as a parameter to the constructor of this object integralType.
     * <p/>
     * Instances of Format passed to the constructor as thus not required to
     * format null value.
     *
     * @param value the value to convert. Maybe null.
     * @return the string representation of a value of this
     *         type in the storage engine language, not quoted in any
     *         way.
     *         <p/>
     */
    public final RunTimeType toRuntimeValue(Object value) {
        if (value==null)
            return null;

        Object obj = typeValueParser.toRuntimeValue(value);
        if (typeClass.isPrimitive()) {
            Class<RunTimeType> wrap = Primitives.wrap(typeClass);
            return wrap.cast(obj);
        }
        return (RunTimeType)obj;
    }

    private static abstract class NumberParser extends Parser<Number> {
        @Override public Number toRuntimeValue(Object value) {
            if (CharSequence.class.isAssignableFrom(value.getClass())) {
                 return toExactObject(new BigDecimal(value.toString()));
            }

            if (!(value instanceof Number))
                throw new InvalidParameterException("parameter value is not an instance of Number");

            return toExactObject(Number.class.cast(value));

        }

        protected abstract Number toExactObject(Number n);
    }
}
