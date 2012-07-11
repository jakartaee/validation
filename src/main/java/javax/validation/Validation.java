/*
* JBoss, Home of Professional Open Source
* Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package javax.validation;

import java.lang.ref.SoftReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.WeakHashMap;
import javax.validation.bootstrap.GenericBootstrap;
import javax.validation.bootstrap.ProviderSpecificBootstrap;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ValidationProvider;

/**
 * This class is the entry point for Bean Validation. There are three ways
 * to bootstrap it:
 * <ul>
 * <li>
 * The easiest approach is to build the default {@code ValidatorFactory}.
 * <pre>{@code ValidatorFactory factory = Validation.buildDefaultValidatorFactory();}</pre>
 * In this case, the default validation provider resolver
 * will be used to locate available providers.
 * The chosen provider is defined as followed:
 * <ul>
 * <li>if the XML configuration defines a provider, this provider is used</li>
 * <li>if the XML configuration does not define a provider or if no XML configuration
 * is present the first provider returned by the
 * {@code ValidationProviderResolver} instance is used.</li>
 * </ul>
 * </li>
 * <li>
 * The second bootstrap approach allows to choose a custom
 * {@code ValidationProviderResolver}. The chosen
 * {@code ValidationProvider} is then determined in the same way
 * as in the default bootstrapping case (see above).
 * <pre>{@code
 * Configuration<?> configuration = Validation
 *    .byDefaultProvider()
 *    .providerResolver( new MyResolverStrategy() )
 *    .configure();
 * ValidatorFactory factory = configuration.buildValidatorFactory();}
 * </pre>
 * </li>
 * <li>
 * The third approach allows you to specify explicitly and in
 * a type safe fashion the expected provider.
 * <p/>
 * Optionally you can choose a custom {@code ValidationProviderResolver}.
 * <pre>{@code
 * ACMEConfiguration configuration = Validation
 *    .byProvider(ACMEProvider.class)
 *    .providerResolver( new MyResolverStrategy() )  // optionally set the provider resolver
 *    .configure();
 * ValidatorFactory factory = configuration.buildValidatorFactory();}
 * </pre>
 * </li>
 * </ul>
 * Note:<br/>
 * <ul>
 * <li>
 * The {@code ValidatorFactory} object built by the bootstrap process should be cached
 * and shared amongst {@code Validator} consumers.
 * </li>
 * <li>
 * This class is thread-safe.
 * </li>
 * </ul>
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public class Validation {

	/**
	 * Build and return a {@code ValidatorFactory} instance based on the
	 * default Bean Validation provider and following the XML configuration.
	 * <p/>
	 * The provider list is resolved using the default validation provider resolver
	 * logic.
	 * <p/> The code is semantically equivalent to
	 * {@code Validation.byDefaultProvider().configure().buildValidatorFactory()}
	 *
	 * @return {@code ValidatorFactory} instance.
	 *
	 * @throws ValidationException if the ValidatorFactory cannot be built
	 */
	public static ValidatorFactory buildDefaultValidatorFactory() {
		return byDefaultProvider().configure().buildValidatorFactory();
	}

	/**
	 * Build a {@code Configuration}. The provider list is resolved
	 * using the strategy provided to the bootstrap state.
	 * <pre>
	 * Configuration&lt?&gt; configuration = Validation
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
	 *         compliant with the bootstrap state provided.
	 */
	public static GenericBootstrap byDefaultProvider() {
		return new GenericBootstrapImpl();
	}

	/**
	 * Build a {@code Configuration} for a particular provider implementation.
	 * Optionally overrides the provider resolution strategy used to determine the provider.
	 * <p/>
	 * Used by applications targeting a specific provider programmatically.
	 * <p/>
	 * <pre>
	 * ACMEConfiguration configuration =
	 *     Validation.byProvider(ACMEProvider.class)
	 *             .providerResolver( new MyResolverStrategy() )
	 *             .configure();
	 * </pre>,
	 * where {@code ACMEConfiguration} is the
	 * {@code Configuration} sub interface uniquely identifying the
	 * ACME Bean Validation provider. and {@code ACMEProvider} is the
	 * {@code ValidationProvider} implementation of the ACME provider.
	 *
	 * @param providerType the {@code ValidationProvider} implementation type
	 *
	 * @return instance building a provider specific {@code Configuration}
	 *         sub interface implementation.
	 */
	public static <T extends Configuration<T>, U extends ValidationProvider<T>>
	ProviderSpecificBootstrap<T> byProvider(Class<U> providerType) {
		return new ProviderSpecificBootstrapImpl<T, U>( providerType );
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
		 * Optionally define the provider resolver implementation used.
		 * If not defined, use the default ValidationProviderResolver
		 *
		 * @param resolver ValidationProviderResolver implementation used
		 *
		 * @return self
		 */
		public ProviderSpecificBootstrap<T> providerResolver(ValidationProviderResolver resolver) {
			this.resolver = resolver;
			return this;
		}

		/**
		 * Determine the provider implementation suitable for byProvider(Class)
		 * and delegate the creation of this specific Configuration subclass to the provider.
		 *
		 * @return a Configuration sub interface implementation
		 */
		public T configure() {
			if ( validationProviderClass == null ) {
				throw new ValidationException(
						"builder is mandatory. Use Validation.byDefaultProvider() to use the generic provider discovery mechanism"
				);
			}
			//used mostly as a BootstrapState
			GenericBootstrapImpl state = new GenericBootstrapImpl();
			if ( resolver == null ) {
				resolver = state.getDefaultValidationProviderResolver();
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

			for ( ValidationProvider provider : resolvers ) {
				if ( validationProviderClass.isAssignableFrom( provider.getClass() ) ) {
					ValidationProvider<T> specificProvider = validationProviderClass.cast( provider );
					return specificProvider.createSpecializedConfiguration( state );

				}
			}
			throw new ValidationException( "Unable to find provider: " + validationProviderClass );
		}
	}

	//private class, not exposed
	private static class GenericBootstrapImpl implements GenericBootstrap, BootstrapState {

		private ValidationProviderResolver resolver;
		private ValidationProviderResolver defaultResolver;

		public GenericBootstrap providerResolver(ValidationProviderResolver resolver) {
			this.resolver = resolver;
			return this;
		}

		public ValidationProviderResolver getValidationProviderResolver() {
			return resolver;
		}

		public ValidationProviderResolver getDefaultValidationProviderResolver() {
			if ( defaultResolver == null ) {
				defaultResolver = new DefaultValidationProviderResolver();
			}
			return defaultResolver;
		}

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

			if ( validationProviders.size() == 0 ) {
				String msg = "Unable to create a Configuration, because no Bean Validation provider could be found." +
						" Add a provider like Hibernate Validator (RI) to your classpath.";
				throw new ValidationException( msg );
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
	 * Find {@code ValidationProvider} according to the default {@code ValidationProviderResolver} defined in the
	 * Bean Validation specification. This implementation uses the current classloader or the classloader which has loaded
	 * the current class if the current class loader is unavailable. The classloader is used to retrieve the Service Provider files.
	 * <p>
	 * This class implements the Service Provider pattern described <a href="http://java.sun.com/j2se/1.5.0/docs/guide/jar/jar.html#Service%20Provider">here</a>.
	 * Since we cannot rely on Java 6 we have to re-implement the {@code Service} functionality.
	 * </p>
	 *
	 * @author Emmanuel Bernard
	 * @author Hardy Ferentschik
	 */
	private static class DefaultValidationProviderResolver implements ValidationProviderResolver {
		public List<ValidationProvider<?>> getValidationProviders() {
			// class loading and ServiceLoader methods should happen in a PrivilegedAction
			return GetValidationProviderList.getValidationProviderList();
		}
	}

	private static class GetValidationProviderList implements PrivilegedAction<List<ValidationProvider<?>>> {

		//cache per classloader for an appropriate discovery
		//keep them in a weak hash map to avoid memory leaks and allow proper hot redeployment
		//TODO use a WeakConcurrentHashMap
		private static final WeakHashMap<ClassLoader, SoftReference<List<ValidationProvider<?>>>> providersPerClassloader =
				new WeakHashMap<ClassLoader, SoftReference<List<ValidationProvider<?>>>>();

		public static List<ValidationProvider<?>> getValidationProviderList() {
			final GetValidationProviderList action = new GetValidationProviderList();
			if ( System.getSecurityManager() != null ) {
				return AccessController.doPrivileged( action );
			}
			else {
				return action.run();
			}
		}

		public List<ValidationProvider<?>> run() {
			// try first context class loader
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			List<ValidationProvider<?>> cachedContextClassLoaderProviderList = getCachedValidationProviders( classloader );
			if ( cachedContextClassLoaderProviderList != null ) {
				// if already processed return the cached provider list
				return cachedContextClassLoaderProviderList;
			}

			ServiceLoader<ValidationProvider> loader = ServiceLoader.load( ValidationProvider.class, classloader );
			Iterator<ValidationProvider> providerIterator = loader.iterator();

			// if we cannot find any service files with the context class loader use the current class loader
			if ( !providerIterator.hasNext() ) {
				classloader = DefaultValidationProviderResolver.class.getClassLoader();
				List<ValidationProvider<?>> cachedCurrentClassLoaderProviderList = getCachedValidationProviders(
						classloader
				);
				if ( cachedCurrentClassLoaderProviderList != null ) {
					// if already processed return the cached provider list
					return cachedCurrentClassLoaderProviderList;
				}
				loader = ServiceLoader.load( ValidationProvider.class, classloader );
				providerIterator = loader.iterator();
			}

			List<ValidationProvider<?>> validationProviderList = new ArrayList<ValidationProvider<?>>();
			while ( providerIterator.hasNext() ) {
				try {
					validationProviderList.add( providerIterator.next() );
				}
				catch ( ServiceConfigurationError e ) {
					throw new ValidationException(
							"Unable to load Bean Validation provider non.existent.ValidationProvider",
							e
					);
				}
			}

			cacheValidationProviders( classloader, validationProviderList );

			return validationProviderList;
		}

		private synchronized List<ValidationProvider<?>> getCachedValidationProviders(ClassLoader classLoader) {
			SoftReference<List<ValidationProvider<?>>> ref = providersPerClassloader.get( classLoader );
			return ref != null ? ref.get() : null;
		}

		private synchronized void cacheValidationProviders(ClassLoader classLoader, List<ValidationProvider<?>> providers) {
			providersPerClassloader.put( classLoader, new SoftReference<List<ValidationProvider<?>>>( providers ) );
		}
	}
}
