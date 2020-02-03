/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;

import java.lang.ref.SoftReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.WeakHashMap;

import jakarta.validation.bootstrap.GenericBootstrap;
import jakarta.validation.bootstrap.ProviderSpecificBootstrap;
import jakarta.validation.spi.BootstrapState;
import jakarta.validation.spi.ValidationProvider;

/**
 * This class is the entry point for Jakarta Bean Validation.
 * <p>
 * There are three ways to bootstrap it:
 * <ul>
 *     <li>The easiest approach is to build the default {@link ValidatorFactory}.
 *     <pre>
 * ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
 * </pre>
 *     In this case, the default validation provider resolver
 *     will be used to locate available providers.
 *     <p>
 *     The chosen provider is defined as followed:
 *     <ul>
 *         <li>if the XML configuration defines a provider, this provider is used</li>
 *         <li>if the XML configuration does not define a provider or if no XML
 *         configuration is present the first provider returned by the
 *         {@link ValidationProviderResolver} instance is used.</li>
 *     </ul>
 *     </li>
 *     <li>
 *     The second bootstrap approach allows to choose a custom
 *     {@code ValidationProviderResolver}. The chosen
 *     {@link ValidationProvider} is then determined in the same way
 *     as in the default bootstrapping case (see above).
 *     <pre>
 * Configuration&lt;?&gt; configuration = Validation
 *    .byDefaultProvider()
 *    .providerResolver( new MyResolverStrategy() )
 *    .configure();
 * ValidatorFactory factory = configuration.buildValidatorFactory();
 * </pre>
 *     </li>
 *     <li>
 *     The third approach allows you to specify explicitly and in
 *     a type safe fashion the expected provider.
 *     <p>
 *     Optionally you can choose a custom {@code ValidationProviderResolver}.
 *     <pre>
 * ACMEConfiguration configuration = Validation
 *    .byProvider(ACMEProvider.class)
 *    .providerResolver( new MyResolverStrategy() )  // optionally set the provider resolver
 *    .configure();
 * ValidatorFactory factory = configuration.buildValidatorFactory();
 * </pre>
 *     </li>
 * </ul>
 * <p>
 * Note:
 * <ul>
 *     <li>
 *     The {@code ValidatorFactory} object built by the bootstrap process should be cached
 *     and shared amongst {@code Validator} consumers.
 *     </li>
 *     <li>This class is thread-safe.</li>
 * </ul>
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public class Validation {

	/**
	 * Builds and returns a {@link ValidatorFactory} instance based on the
	 * default Jakarta Bean Validation provider and following the XML configuration.
	 * <p>
	 * The provider list is resolved using the default validation provider resolver
	 * logic.
	 * <p>
	 * The code is semantically equivalent to
	 * {@code Validation.byDefaultProvider().configure().buildValidatorFactory()}.
	 *
	 * @return {@code ValidatorFactory} instance
	 *
	 * @throws NoProviderFoundException if no Jakarta Bean Validation provider was found
	 * @throws ValidationException if a Jakarta Bean Validation provider was found but the
	 * {@code ValidatorFactory} cannot be built
	 */
	public static ValidatorFactory buildDefaultValidatorFactory() {
		return byDefaultProvider().configure().buildValidatorFactory();
	}

	/**
	 * Builds a {@link Configuration}. The provider list is resolved
	 * using the strategy provided to the bootstrap state.
	 * <pre>
	 * Configuration&lt;?&gt; configuration = Validation
	 *    .byDefaultProvider()
	 *    .providerResolver( new MyResolverStrategy() )
	 *    .configure();
	 * ValidatorFactory factory = configuration.buildValidatorFactory();
	 * </pre>
	 * The provider can be specified in the XML configuration. If the XML
	 * configuration does not exist or if no provider is specified,
	 * the first available provider will be returned.
	 *
	 * @return instance building a generic {@code Configuration}
	 *         compliant with the bootstrap state provided
	 */
	public static GenericBootstrap byDefaultProvider() {
		return new GenericBootstrapImpl();
	}

	/**
	 * Builds a {@link Configuration} for a particular provider implementation.
	 * <p>
	 * Optionally overrides the provider resolution strategy used to determine the provider.
	 * <p>
	 * Used by applications targeting a specific provider programmatically.
	 * <pre>
	 * ACMEConfiguration configuration =
	 *     Validation.byProvider(ACMEProvider.class)
	 *             .providerResolver( new MyResolverStrategy() )
	 *             .configure();
	 * </pre>,
	 * where {@code ACMEConfiguration} is the
	 * {@code Configuration} sub interface uniquely identifying the
	 * ACME Jakarta Bean Validation provider. and {@code ACMEProvider} is the
	 * {@link ValidationProvider} implementation of the ACME provider.
	 *
	 * @param providerType the {@code ValidationProvider} implementation type
	 * @param <T> the type of the {@code Configuration} corresponding to this
	 *        {@code ValidationProvider}
	 * @param <U> the type of the {@code ValidationProvider} implementation
	 *
	 * @return instance building a provider specific {@code Configuration}
	 *         sub interface implementation
	 */
	public static <T extends Configuration<T>, U extends ValidationProvider<T>>
	ProviderSpecificBootstrap<T> byProvider(Class<U> providerType) {
		return new ProviderSpecificBootstrapImpl<>( providerType );
	}

	/**
	 * Not a public API; it can be used reflectively by code that integrates with Jakarta Bean Validation, e.g. application
	 * servers, to clear the provider cache maintained by the default provider resolver.
	 * <p>
	 * This is a strictly unsupported API, its definition may be changed or removed at any time. Its purpose is to
	 * explore the addition of standardized methods for dealing with provider caching in a future revision of the spec.
	 */
	@SuppressWarnings("unused")
	private static void clearDefaultValidationProviderResolverCache() {
		GetValidationProviderListAction.clearCache();
	}

	//private class, not exposed
	private static class ProviderSpecificBootstrapImpl<T extends Configuration<T>, U extends ValidationProvider<T>>
			implements ProviderSpecificBootstrap<T> {

		private final Class<U> validationProviderClass;
		private ValidationProviderResolver resolver;

		public ProviderSpecificBootstrapImpl(Class<U> validationProviderClass) {
			this.validationProviderClass = validationProviderClass;
		}

		/**
		 * Optionally defines the provider resolver implementation used.
		 * If not defined, use the default ValidationProviderResolver.
		 *
		 * @param resolver {@link ValidationProviderResolver} implementation used
		 *
		 * @return self
		 */
		@Override
		public ProviderSpecificBootstrap<T> providerResolver(ValidationProviderResolver resolver) {
			this.resolver = resolver;
			return this;
		}

		/**
		 * Determines the provider implementation suitable for {@link #byProvider(Class)}
		 * and delegates the creation of this specific {@link Configuration} subclass to the
		 * provider.
		 *
		 * @return a {@code Configuration} sub interface implementation
		 */
		@Override
		public T configure() {
			if ( validationProviderClass == null ) {
				throw new ValidationException(
						"builder is mandatory. Use Validation.byDefaultProvider() to use the generic provider discovery mechanism"
				);
			}
			//used mostly as a BootstrapState
			GenericBootstrapImpl state = new GenericBootstrapImpl();

			// if no resolver is given, simply instantiate the given provider
			if ( resolver == null ) {
				U provider = run( NewProviderInstance.action( validationProviderClass ) );
				return provider.createSpecializedConfiguration( state );
			}
			else {
				//stay null if no resolver is defined
				state.providerResolver( resolver );
			}

			List<ValidationProvider<?>> resolvers;
			try {
				resolvers = resolver.getValidationProviders();
			}
			catch ( RuntimeException re ) {
				throw new ValidationException( "Unable to get available provider resolvers.", re );
			}

			for ( ValidationProvider<?> provider : resolvers ) {
				if ( validationProviderClass.isAssignableFrom( provider.getClass() ) ) {
					ValidationProvider<T> specificProvider = validationProviderClass.cast( provider );
					return specificProvider.createSpecializedConfiguration( state );

				}
			}
			throw new ValidationException( "Unable to find provider: " + validationProviderClass );
		}

		private <P> P run(PrivilegedAction<P> action) {
			return System.getSecurityManager() != null ? AccessController.doPrivileged( action ) : action.run();
		}
	}

	//private class, not exposed
	private static class GenericBootstrapImpl implements GenericBootstrap, BootstrapState {

		private ValidationProviderResolver resolver;
		private ValidationProviderResolver defaultResolver;

		@Override
		public GenericBootstrap providerResolver(ValidationProviderResolver resolver) {
			this.resolver = resolver;
			return this;
		}

		@Override
		public ValidationProviderResolver getValidationProviderResolver() {
			return resolver;
		}

		@Override
		public ValidationProviderResolver getDefaultValidationProviderResolver() {
			if ( defaultResolver == null ) {
				defaultResolver = new DefaultValidationProviderResolver();
			}
			return defaultResolver;
		}

		@Override
		public Configuration<?> configure() {
			ValidationProviderResolver resolver = this.resolver == null ?
					getDefaultValidationProviderResolver() :
					this.resolver;

			List<ValidationProvider<?>> validationProviders;
			try {
				validationProviders = resolver.getValidationProviders();
			}
			// don't wrap existing ValidationExceptions in another ValidationException
			catch ( ValidationException e ) {
				throw e;
			}
			// if any other exception occurs wrap it in a ValidationException
			catch ( RuntimeException re ) {
				throw new ValidationException( "Unable to get available provider resolvers.", re );
			}

			if ( validationProviders.isEmpty() ) {
				String msg = "Unable to create a Configuration, because no Jakarta Bean Validation provider could be found." +
						" Add a provider like Hibernate Validator (RI) to your classpath.";
				throw new NoProviderFoundException( msg );
			}

			Configuration<?> config;
			try {
				config = resolver.getValidationProviders().get( 0 ).createGenericConfiguration( this );
			}
			catch ( RuntimeException re ) {
				throw new ValidationException( "Unable to instantiate Configuration.", re );
			}

			return config;
		}
	}

	/**
	 * Finds {@link ValidationProvider} according to the default {@link ValidationProviderResolver} defined in the
	 * Jakarta Bean Validation specification. This implementation first uses thread's context classloader to locate providers.
	 * If no suitable provider is found using the aforementioned class loader, it uses current class loader.
	 * If it still does not find any suitable provider, it tries to locate the built-in provider using the current
	 * class loader.
	 *
	 * @author Emmanuel Bernard
	 * @author Hardy Ferentschik
	 */
	private static class DefaultValidationProviderResolver implements ValidationProviderResolver {
		@Override
		public List<ValidationProvider<?>> getValidationProviders() {
			// class loading and ServiceLoader methods should happen in a PrivilegedAction
			return GetValidationProviderListAction.getValidationProviderList();
		}
	}

	private static class GetValidationProviderListAction implements PrivilegedAction<List<ValidationProvider<?>>> {

		private final static GetValidationProviderListAction INSTANCE = new GetValidationProviderListAction();

		//cache per classloader for an appropriate discovery
		//keep them in a weak hash map to avoid memory leaks and allow proper hot redeployment
		private final WeakHashMap<ClassLoader, SoftReference<List<ValidationProvider<?>>>> providersPerClassloader =
				new WeakHashMap<>();

		public static synchronized List<ValidationProvider<?>> getValidationProviderList() {
			if ( System.getSecurityManager() != null ) {
				return AccessController.doPrivileged( INSTANCE );
			}
			else {
				return INSTANCE.run();
			}
		}

		public static synchronized void clearCache() {
			INSTANCE.providersPerClassloader.clear();
		}

		@Override
		public List<ValidationProvider<?>> run() {
			// Option #1: try first context class loader
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			List<ValidationProvider<?>> cachedContextClassLoaderProviderList = getCachedValidationProviders( classloader );
			if ( cachedContextClassLoaderProviderList != null ) {
				// if already processed return the cached provider list
				return cachedContextClassLoaderProviderList;
			}

			List<ValidationProvider<?>> validationProviderList = loadProviders( classloader );

			// Option #2: if we cannot find any service files with the context class loader use the current class loader
			if ( validationProviderList.isEmpty() ) {
				classloader = DefaultValidationProviderResolver.class.getClassLoader();
				List<ValidationProvider<?>> cachedCurrentClassLoaderProviderList = getCachedValidationProviders(
						classloader
				);
				if ( cachedCurrentClassLoaderProviderList != null ) {
					// if already processed return the cached provider list
					return cachedCurrentClassLoaderProviderList;
				}
				validationProviderList = loadProviders( classloader );
			}

			// cache the detected providers against the classloader in which they were found
			cacheValidationProviders( classloader, validationProviderList );

			return validationProviderList;
		}

		private List<ValidationProvider<?>> loadProviders(ClassLoader classloader) {
			ServiceLoader<ValidationProvider> loader = ServiceLoader.load( ValidationProvider.class, classloader );
			Iterator<ValidationProvider> providerIterator = loader.iterator();
			List<ValidationProvider<?>> validationProviderList = new ArrayList<>();
			while ( providerIterator.hasNext() ) {
				try {
					validationProviderList.add( providerIterator.next() );
				}
				catch ( ServiceConfigurationError e ) {
					// ignore, because it can happen when multiple
					// providers are present and some of them are not class loader
					// compatible with our API.
				}
			}
			return validationProviderList;
		}

		private synchronized List<ValidationProvider<?>> getCachedValidationProviders(ClassLoader classLoader) {
			SoftReference<List<ValidationProvider<?>>> ref = providersPerClassloader.get( classLoader );
			return ref != null ? ref.get() : null;
		}

		private synchronized void cacheValidationProviders(ClassLoader classLoader, List<ValidationProvider<?>> providers) {
			providersPerClassloader.put( classLoader, new SoftReference<>( providers ) );
		}
	}

	private static class NewProviderInstance<T extends ValidationProvider<?>> implements PrivilegedAction<T> {

		private final Class<T> clazz;

		public static <T extends ValidationProvider<?>> NewProviderInstance<T> action(Class<T> clazz) {
			return new NewProviderInstance<>( clazz );
		}

		private NewProviderInstance(Class<T> clazz) {
			this.clazz = clazz;
		}

		@Override
		public T run() {
			try {
				return clazz.newInstance();
			}
			catch (InstantiationException | IllegalAccessException | RuntimeException e) {
				throw new ValidationException( "Cannot instantiate provider type: " + clazz, e );
			}
		}
	}
}
