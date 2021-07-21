package novel.internal.factories;

import novel.api.Novel;
import novel.api.types.factory.AdapterFactory;
import novel.api.types.adapt.adapters.*;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.token.TypeToken;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultAdapterFactories {

    private static final AdapterFactory
            ATOMIC_BOOLEAN_FACTORY = newFactory(new AtomicBooleanAdapter(), AtomicBoolean.class),
            ATOMIC_INTEGER_FACTORY = newFactory(new AtomicIntegerAdapter(), AtomicInteger.class),
            ATOMIC_LONG_FACTORY = newFactory(new AtomicLongAdapter(), AtomicLong.class),
            BIG_DECIMAL_FACTORY = newFactory(new BigDecimalAdapter(), BigDecimal.class),
            BIG_INTEGER_FACTORY = newFactory(new BigIntegerAdapter(), BigInteger.class),
            CALENDAR_FACTORY = newFactory(new CalendarAdapter(), Calendar.class),
            CLASS_FACTORY = newFactory(new ClassAdapter(), Class.class),
            CURRENCY_FACTORY = newFactory(new CurrencyAdapter(), Currency.class),
            INETADDRESS_FACTORY = newFactory(new InetAddressAdapter(), InetAddress.class),
            LOCALE_FACTORY = newFactory(new LocaleAdapter(), Locale.class),
            INSTANT_FACTORY = newFactory(new InstantAdapter(), Instant.class),
            STRING_BUFFER_FACTORY = newFactory(new StringBufferAdapter(), StringBuffer.class),
            STRING_BUILDER_FACTORY = newFactory(new StringBuilderAdapter(), StringBuilder.class),
            URI_FACTORY = newFactory(new URIAdapter(), URI.class),
            URL_FACTORY = newFactory(new URLAdapter(), URL.class),
            UUID_FACTORY = newFactory(new UUIDAdapter(), UUID.class)
            ;

    private static final AdapterFactory ENUM_FACTORY = new AdapterFactory() {
        @Override
        @SuppressWarnings("unchecked")
        public <T> ObjectDataAdapter<T> create(Novel novel, TypeToken<T> typeToken) {
            Class<? super T> rawType = typeToken.getRawType();
            if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
                return null;
            }
            if (!rawType.isEnum()) {
                rawType = rawType.getSuperclass();//handle anonymous subclasses
            }
            //noinspection rawtypes
            return (ObjectDataAdapter<T>) new EnumAdapter(rawType);
        }
    };

    private static <TT> AdapterFactory newFactory(ObjectDataAdapter<TT> adapter, Class<TT> clazz) {
        return new AdapterFactory() {
            @SuppressWarnings("unchecked")
            @Override
            public <T> ObjectDataAdapter<T> create(Novel novel, TypeToken<T> typeToken) {
            return clazz.isAssignableFrom(typeToken.getRawType()) ? (ObjectDataAdapter<T>) adapter : null;
            }
        };
    }

    public static final List<AdapterFactory> DEFAULT_JAVA_FACTORIES = List.of(
            //todo: get rid of needless factories?
            //todo: primtiives here first? ~> migrate from registry to here?
            //todo: NumberFactory (?)
            ATOMIC_BOOLEAN_FACTORY, ATOMIC_INTEGER_FACTORY, ATOMIC_LONG_FACTORY,
            BIG_DECIMAL_FACTORY, BIG_INTEGER_FACTORY,
            CALENDAR_FACTORY,
            CLASS_FACTORY,
            CURRENCY_FACTORY,
            INETADDRESS_FACTORY,
            LOCALE_FACTORY,
            ENUM_FACTORY,
            INSTANT_FACTORY,
            STRING_BUFFER_FACTORY, STRING_BUILDER_FACTORY,
            URI_FACTORY, URL_FACTORY,
            UUID_FACTORY
    );

    public static final List<AdapterFactory> REQUIRED_FACTORIES = List.of(
            //todo: migrate remaining adapters to static factories?
            new CollectionAdapterFactory(),
            new MapAdapterFactory(),
            new ArrayAdapterFactory(),
            new StreamAdapterFactory(),
            new NovelWrapperAdapterFactory(),
            new ReflectiveAdapterFactory()//This MUST go last
    );
}
