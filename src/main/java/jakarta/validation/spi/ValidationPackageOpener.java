/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation.spi;

import java.lang.invoke.MethodHandles;

/**
 * Callback provided by the Jakarta Validation bootstrap to relay
 * JPMS package-opens through the {@code jakarta.validation} module to a
 * validation provider module.
 * <p>
 * When a user module opens a package to {@code jakarta.validation}
 * via a qualified {@code opens ... to jakarta.validation} directive,
 * this callback relays that access to the validation provider's module,
 * so the user never needs to name the provider module directly.
 * <p>
 * The opener is bound to the provider module that was discovered during
 * bootstrap. The {@link MethodHandles.Lookup} parameter serves as an
 * unforgeable proof of caller identity: the implementation verifies
 * that the lookup has {@linkplain MethodHandles.Lookup#hasFullPrivilegeAccess()
 * full privilege access} and that its
 * {@linkplain MethodHandles.Lookup#lookupClass() lookup class} belongs
 * to the bound provider module.
 * <p>
 * <b>Usage by providers:</b>
 * <pre>
 * // During bootstrap, obtain the opener from BootstrapState
 * ValidationPackageOpener opener = bootstrapState.getPackageOpener();
 *
 * // Only relay when the user module has opened the package to jakarta.validation
 * Module targetModule = beanClass.getModule();
 * String packageName = beanClass.getPackageName();
 * if (targetModule.isOpen(packageName, ValidationPackageOpener.class.getModule())) {
 *     // Create lookup at the call site — do not cache as a static field
 *     opener.openPackage(MethodHandles.lookup(), targetModule, packageName);
 * }
 * </pre>
 * <p>
 * In non-modular environments (classpath), invoking this callback
 * is a safe no-op.
 * <p>
 * If the provider needs to further relay access to a third-party
 * library module, it can call {@link Module#addOpens(String, Module)}
 * from its own code after obtaining access through this callback.
 *
 * @since 4.0
 */
@FunctionalInterface
public interface ValidationPackageOpener {

	/**
	 * Opens {@code packageName} in {@code targetModule} to the bound
	 * provider module, provided that the package is open to the
	 * {@code jakarta.validation} module and not already open to the
	 * provider.
	 *
	 * @param providerLookup a full-privilege {@link MethodHandles.Lookup}
	 *        obtained via {@code MethodHandles.lookup()} in the provider's
	 *        code; used to verify the caller's identity
	 * @param targetModule the module containing the package to open
	 *        (typically the user's module)
	 * @param packageName the fully qualified package name to open
	 *        (e.g. {@code "com.example.beans"})
	 * @throws PackageAccessException if the lookup does not
	 *         have full privilege access, does not belong to the bound
	 *         provider module, or the package could not be opened
	 */
	void openPackage(MethodHandles.Lookup providerLookup, Module targetModule, String packageName);
}
