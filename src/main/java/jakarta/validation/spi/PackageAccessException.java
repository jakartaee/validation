/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation.spi;

import jakarta.validation.ValidationException;

import java.io.Serial;

/**
 * Exception raised when a {@link jakarta.validation.spi.ValidationPackageOpener}
 * cannot relay package access to the validation provider module.
 * <p>
 * Typical causes include a missing or invalid {@link java.lang.invoke.MethodHandles.Lookup}
 * and a package that has not been opened to the {@code jakarta.validation} module.
 *
 * @since 4.0
 */
public class PackageAccessException extends ValidationException {

	@Serial
	private static final long serialVersionUID = 1L;

	public PackageAccessException() {
		super();
	}

	public PackageAccessException(String message) {
		super( message );
	}

	public PackageAccessException(Throwable cause) {
		super( cause );
	}

	public PackageAccessException(String message, Throwable cause) {
		super( message, cause );
	}
}
