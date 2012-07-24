/*
* JBoss, Home of Professional Open Source
* Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual contributors
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


import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import javax.validation.spi.ValidationProvider;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * @author Hardy Ferentschik
 */
public class ValidationTest {

	// BVAL-298
	@Test
	public void testCurrentClassLoaderIsUsedInCaseContextClassLoaderCannotLoadServiceFile() {
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader dummyClassLoader = new URLClassLoader( new URL[] { }, null );
		Thread.currentThread().setContextClassLoader( dummyClassLoader );
		try {
			Validation.buildDefaultValidatorFactory();
			fail();
		}
		catch ( ValidationException e ) {
			// the custom context URL class loader cannot load the service file, so the exception
			// must be triggered by using the current class loader
			assertEquals(
					e.getMessage(),
					"Unable to load Bean Validation provider"
			);
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
			Validation.buildDefaultValidatorFactory();
			fail();
		}
		catch ( ValidationException e ) {
			// context class loader is not. exception must be caused by using the current class loader
			assertEquals(
					e.getMessage(),
					"Unable to load Bean Validation provider"
			);
		}
		finally {
			Thread.currentThread().setContextClassLoader( contextClassLoader );
		}
	}

	// BVAL-298
	@Test
	public void testCachedProvidersCanBeGarbageCollected() {
		int LOOP_COUNT = 100;

		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			for ( int i = 1; i <= LOOP_COUNT; i++ ) {
				Thread.currentThread().setContextClassLoader( new CustomValidationXmlClassLoader( "-2" ) );
				Validation.buildDefaultValidatorFactory();
			}

			int createdProviders = countInMemoryProviders();
			assertTrue( createdProviders > 1, "There should be cached providers" );

			try {
				byte[][] buf = new byte[1024][];
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

	private int countInMemoryProviders() {
		int count = 0;
		// we cannot access Validation.DefaultValidationProviderResolver#providersPerClassloader, so we have to
		// indirectly count the providers via DummyValidationProvider#createdValidationProviders
		for ( SoftReference<DummyValidationProvider> ref : DummyValidationProvider.createdValidationProviders ) {
			if ( ref.get() != null ) {
				count++;
			}
		}
		return count;
	}

	public static class CustomValidationXmlClassLoader extends ClassLoader {
		private static final String SERVICES_FILE = "META-INF/services/" + ValidationProvider.class.getName();
		private final String validationXmlSuffix;


		public CustomValidationXmlClassLoader(String suffix) {
			super( CustomValidationXmlClassLoader.class.getClassLoader() );
			this.validationXmlSuffix = suffix;
		}

		public Enumeration<URL> getResources(String name) throws IOException {
			if ( SERVICES_FILE.equals( name ) && validationXmlSuffix != null ) {
				name = name + validationXmlSuffix;
			}
			return super.getResources( name );
		}
	}
}


