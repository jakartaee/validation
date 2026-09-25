/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation.internal;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import jakarta.validation.spi.PackageAccessException;
import jakarta.validation.spi.ValidationPackageOpener;

/**
 * Implementation of {@link ValidationPackageOpener} bound to a specific provider module.
 * <p>
 * This class resides in a non-exported package so that it cannot be accessed,
 * subclassed, or instantiated from outside the {@code jakarta.validation} module.
 * <p>
 * The {@link Module#addOpens(String, Module)} call is {@code @CallerSensitive};
 * because this class belongs to the {@code jakarta.validation} module, the JVM
 * recognizes it as a valid caller when the user has opened a package to
 * {@code jakarta.validation}.
 *
 * @since 4.0
 */
public final class ValidationPackageOpenerImpl implements ValidationPackageOpener {

	private final Module providerModule;

	/**
	 * @param providerModule the module of the validation provider discovered
	 *        during bootstrap; this opener will only open packages to this module
	 */
	public ValidationPackageOpenerImpl(Module providerModule) {
		this.providerModule = Objects.requireNonNull( providerModule );
	}

	@Override
	public void openPackage(MethodHandles.Lookup providerLookup, Module targetModule, String packageName) {
		Objects.requireNonNull( providerLookup );
		Objects.requireNonNull( targetModule );
		Objects.requireNonNull( packageName );

		if ( !providerLookup.hasFullPrivilegeAccess() ) {
			throw new PackageAccessException(
					"ValidationPackageOpener requires a full-privilege Lookup (obtained via MethodHandles.lookup())"
			);
		}

		if ( !providerLookup.lookupClass().getModule().equals( providerModule ) ) {
			throw new PackageAccessException(
					"Lookup class module " + providerLookup.lookupClass().getModule().getName()
							+ " does not match the bound provider module " + providerModule.getName()
			);
		}

		if ( !targetModule.isNamed() ) {
			return;
		}

		try {
			if ( !targetModule.isOpen( packageName, providerModule ) ) {
				targetModule.addOpens( packageName, providerModule );
			}
		}
		catch (IllegalCallerException e) {
			throw new PackageAccessException(
					"Cannot open package " + packageName + " in module " + targetModule.getName()
							+ " to provider module " + providerModule.getName()
							+ ". Ensure the module has 'opens " + packageName + " to jakarta.validation'",
					e
			);
		}
	}
}
