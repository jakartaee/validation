/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
module jakarta.validation {
    exports jakarta.validation;
    exports jakarta.validation.bootstrap;
    exports jakarta.validation.constraints;
    exports jakarta.validation.constraintvalidation;
    exports jakarta.validation.executable;
    exports jakarta.validation.groups;
    exports jakarta.validation.metadata;
    exports jakarta.validation.spi;
    exports jakarta.validation.valueextraction;

    uses jakarta.validation.spi.BootstrapState;
    uses jakarta.validation.spi.ConfigurationState;
    uses jakarta.validation.spi.ValidationProvider;
}