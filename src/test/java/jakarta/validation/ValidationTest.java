/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.testng.annotations.Test;

import jakarta.validation.NoProviderFoundException;
import jakarta.validation.Validation;
import jakarta.validation.ValidationProviderResolver;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.bootstrap.GenericBootstrap;
import jakarta.validation.NonRegisteredValidationProvider.NonRegisteredConfiguration;
import jakarta.validation.spi.ValidationProvider;

/**
 * @author Hardy Ferentschik
 */
public class ValidationTest {

	// BVAL-298
	@Test
	public void testContextClassLoaderIsUsedFirst() {
		// setting a context class loader which is not able to load the service file
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader customClassLoader = new CustomValidationProviderClassLoader( "-1" );
		Thread.currentThread().setContextClassLoader( customClassLoader );
		try {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			assertTrue( factory instanceof BarValidationProvider.DummyValidatorFactory );
		}
		finally {
			Thread.currentThread().setContextClassLoader( contextClassLoader );
		}
	}

	// BVAL-298
	@Test
	public void testCurrentClassLoaderIsUsedInCaseContextClassLoaderCannotLoadServiceFile() {
		// setting a context class loader which is not able to load the service file
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader dummyClassLoader = new URLClassLoader( new URL[] { }, null );
		Thread.currentThread().setContextClassLoader( dummyClassLoader );
		try {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			assertTrue( factory instanceof FooValidationProvider.DummyValidatorFactory );
		}
		finally {
			Thread.currentThread().setContextClassLoader( contextClassLoader );
		}
	}

	// BVAL-298
	@Test
	public void testCurrentClassLoaderIsUsedInCaseContextClassLoaderIsNull() {
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader( null );
		try {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			assertNotNull( factory );
			assertTrue( factory instanceof FooValidationProvider.DummyValidatorFactory );
		}
		finally {
			Thread.currentThread().setContextClassLoader( contextClassLoader );
		}
	}

	// BVAL-298
	@Test
	public void testCachedProvidersCanBeGarbageCollected() {
		int LOOP_COUNT = 100;
		int BUF_SIZE = 10 * 1024 * 1024;
		// Need to calcuate how much memory to use
		long maxMemory = Runtime.getRuntime().maxMemory();
		long BUF_COUNT = maxMemory / BUF_SIZE + 1;
		assertTrue(BUF_COUNT < Integer.MAX_VALUE, "BUF_COUNT > Integer.MAX_VALUE");
		int ALLOC_COUNT = Math.toIntExact(BUF_COUNT);

		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			for ( int i = 1; i <= LOOP_COUNT; i++ ) {
				Thread.currentThread().setContextClassLoader( new CustomValidationProviderClassLoader( "-1" ) );
				Validation.buildDefaultValidatorFactory();
			}

			int createdProviders = countInMemoryProviders();
			assertTrue( createdProviders > 1, "There should be cached providers" );

			try {
				byte[][] buf = new byte[ALLOC_COUNT][];
				for ( int i = 0; i < buf.length; i++ ) {
					buf[i] = new byte[10 * 1024 * 1024];
				}
				fail( "The byte array allocation should have triggered a OutOfMemoryError" );
			}
			catch ( OutOfMemoryError ex ) {
				// expected
			}

			// the VM guarantees that all soft references are cleared before a OutOfMemoryError occurs
			assertEquals( countInMemoryProviders(), 0 );
		}
		finally {
			Thread.currentThread().setContextClassLoader( contextClassLoader );
		}
	}

	// BVAL-280, BVAL-343
	@Test
	public void testUnknownProviderGetsIgnored() {
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader( new CustomValidationProviderClassLoader( "-1", "-2" ) );

		try {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			assertNotNull( factory );
		}
		finally {
			Thread.currentThread().setContextClassLoader( contextClassLoader );
		}
	}

	// BVAL-486
	@Test
	public void testByProviderDoesNotRequireRetrievalViaProviderResolver() {
		NonRegisteredConfiguration configuration = Validation.byProvider( NonRegisteredValidationProvider.class )
				.configure();
		assertNotNull( configuration );
	}

	// BVAL-559
	@Test(expectedExceptions = NoProviderFoundException.class)
	public void testNoProviderFoundThrowsNoProviderFoundException() {
		Validation.byDefaultProvider()
				.providerResolver( new EmptyValidationProviderResolver() )
				.configure()
				.buildValidatorFactory();
	}

	private int countInMemoryProviders() {
		int count = 0;
		// we cannot access Validation.DefaultValidationProviderResolver#providersPerClassloader, so we have to
		// indirectly count the providers via BarValidationProvider#createdValidationProviders
		for ( SoftReference<BarValidationProvider> ref : BarValidationProvider.createdValidationProviders ) {
			if ( ref.get() != null ) {
				count++;
			}
		}
		return count;
	}
	
	@Test
	public void testWeakHashMapSynchronization() {
		try {
			Validation validation = new Validation();
			GenericBootstrap f1 = Validation.byDefaultProvider();
			System.out.println(f1.getClass().getDeclaredField("defaultResolver"));
			Class<?>[] classes = validation.getClass().getDeclaredClasses();
			for (int i = 0; i < classes.length; i++) {
				if (classes[i].getSimpleName().equals("GetValidationProviderListAction")) {
					Field field = classes[i].getDeclaredField("providersPerClassloader");
					field.setAccessible(true);
					Constructor<?> constructor = classes[i].getDeclaredConstructor();
					constructor.setAccessible(true);
					Object obj = constructor.newInstance();
					assertTrue(field.get(obj).getClass().getName().equals("java.util.Collections$SynchronizedMap"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} 
	}

	private static class CustomValidationProviderClassLoader extends ClassLoader {
		private static final String SERVICES_FILE = "META-INF/services/" + ValidationProvider.class.getName();
		private final String[] serviceFileSuffixes;

		public CustomValidationProviderClassLoader(String... suffixes) {
			super( CustomValidationProviderClassLoader.class.getClassLoader() );
			this.serviceFileSuffixes = suffixes;
		}

		@Override
		public Enumeration<URL> getResources(String name) throws IOException {
			CustomEnumeration<URL> customEnumeration = new CustomEnumeration<>();

			if ( SERVICES_FILE.equals( name ) && serviceFileSuffixes != null ) {
				for ( String suffix : serviceFileSuffixes ) {
					customEnumeration.addElements( super.getResources( name + suffix ) );
				}
			}
			else {
				customEnumeration.addElements( super.getResources( name ) );
			}
			return customEnumeration;
		}
	}

	private static class CustomEnumeration<E> implements Enumeration<E> {
		private final List<E> enumList = new ArrayList<>();
		int currentIndex = 0;

		public void addElements(Enumeration<E> enumeration) {
			while ( enumeration.hasMoreElements() ) {
				enumList.add( enumeration.nextElement() );
			}
		}

		@Override
		public boolean hasMoreElements() {
			return currentIndex < enumList.size();
		}

		@Override
		public E nextElement() {
			E element = enumList.get( currentIndex );
			currentIndex++;
			return element;
		}
	}

	private static class EmptyValidationProviderResolver implements ValidationProviderResolver {

		@Override
		public List<ValidationProvider<?>> getValidationProviders() {
			return Collections.emptyList();
		}
	}
	
}
