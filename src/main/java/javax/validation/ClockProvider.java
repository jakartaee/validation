/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation;

import java.time.Clock;

/**
 * Contract for obtaining the {@link Clock} used as the reference for {@code now} when
 * validating the {@code @Future} and {@code @Past} constraints.
 * <p>
 * The default implementation will return the current system time. Plugging in custom
 * implementations may be useful for instance in batch applications which need to run with a
 * specific logical date, e.g. with yesterday's date when re-running a failed batch job
 * execution.
 * <p>
 * Implementations must be safe for access from several threads at the same time.
 *
 * @author Gunnar Morling
 * @author Guillaume Smet
 * @since 2.0
 */
public interface ClockProvider {

	/**
	 * Returns the clock which serves as the reference for {@code now}.
	 *
	 * @return the clock which serves as the reference for {@code now}; must not be
	 * {@code null}
	 */
	Clock getClock();
}
