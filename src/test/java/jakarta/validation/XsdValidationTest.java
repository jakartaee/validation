/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package jakarta.validation;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XsdValidationTest {

	private Set<String> versions = new HashSet<>();

	@BeforeClass
	public void beforeClass() throws Exception {
		Pattern pattern = Pattern.compile( ".+(\\d+\\.\\d+)\\.xsd" );

		try ( Stream<Path> stream = Files.list( Paths.get( XsdValidationTest.this.getClass().getResource( "/META-INF/validation-configuration-1.0.xsd" ).toURI() ).getParent() ) ) {
			stream.forEach( path -> {
				String filename = path.getFileName().toString();
				if ( filename.endsWith( ".xsd" ) ) {
					Matcher matcher = pattern.matcher( filename );
					if ( matcher.find() ) {
						versions.add( matcher.group(1) );
					}
				}
			} );
		}
	}

	@Test(dataProvider = "xsd")
	public void validate(String version, String xsd, String xml) throws Exception {
		try ( InputStream inputStream = XsdValidationTest.this.getClass().getResourceAsStream( xsd ) ) {
			// 1. check that XSD is valid
			SchemaFactory factory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
			ErrorHandler errorHandler = new ErrorHandler() {
				@Override
				public void warning(SAXParseException exception) throws SAXException {
					fail( xsd + ": warning: " + exception.getMessage() );
				}

				@Override
				public void error(SAXParseException exception) throws SAXException {
					fail( xsd + ": error: " + exception.getMessage() );
				}

				@Override
				public void fatalError(SAXParseException exception) throws SAXException {
					fail( xsd + ": fatalError: " + exception.getMessage() );
				}
			};
			factory.setErrorHandler( errorHandler );

			Source xsdSource = new StreamSource( inputStream );
			Schema schema = factory.newSchema( xsdSource );

			// 2 validate some xml against that xsd:

			Validator validator = schema.newValidator();

			validator.validate( new StreamSource( new ByteArrayInputStream( xml.getBytes( StandardCharsets.UTF_8 ) ) ) );

			// 3 check that the xml has no other versions besides the one it was intended for:
			String xmlWithoutFirstLine = xml.substring( xml.indexOf( '\n' ) );
			for ( String v : versions ) {
				if ( v.equals( version ) ) {
					continue;
				}
				assertFalse( xmlWithoutFirstLine.contains( v ) );
			}
		}
	}

	@DataProvider(name = "xsd")
	public static Object[][] xsd() {
		return new Object[][] {
				{ "1.0", "/META-INF/validation-configuration-1.0.xsd", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<validation-config xmlns=\"http://jboss.org/xml/ns/javax/validation/configuration\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jboss.org/xml/ns/javax/validation/configuration validation-configuration-1.0.xsd\"><property name=\"com.acme.validation.safetyChecking\">failOnError</property></validation-config>"
				},
				{ "1.1", "/META-INF/validation-configuration-1.1.xsd", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<validation-config xmlns=\"http://jboss.org/xml/ns/javax/validation/configuration\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jboss.org/xml/ns/javax/validation/configuration validation-configuration-1.1.xsd\" version=\"1.1\"><property name=\"com.acme.validation.safetyChecking\">failOnError</property></validation-config>"
				},
				{ "2.0", "/META-INF/validation-configuration-2.0.xsd", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<validation-config xmlns=\"http://xmlns.jcp.org/xml/ns/validation/configuration\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://xmlns.jcp.org/xml/ns/validation/configuration http://xmlns.jcp.org/xml/ns/validation/configuration/validation-configuration-2.0.xsd\" version=\"2.0\"><property name=\"com.acme.validation.safetyChecking\">failOnError</property></validation-config>"
				},
				{ "3.0", "/META-INF/validation-configuration-3.0.xsd", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<validation-config xmlns=\"https://jakarta.ee/xml/ns/validation/configuration\" xsi:schemaLocation=\"https://jakarta.ee/xml/ns/validation/configuration https://jakarta.ee/xml/ns/validation/validation-configuration-3.0.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"3.0\"><property name=\"com.acme.validation.safetyChecking\">failOnError</property></validation-config>"
				},
				{ "3.1", "/META-INF/validation-configuration-3.1.xsd", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<validation-config xmlns=\"https://jakarta.ee/xml/ns/validation/configuration\" xsi:schemaLocation=\"https://jakarta.ee/xml/ns/validation/configuration https://jakarta.ee/xml/ns/validation/validation-configuration-3.1.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"3.1\"><property name=\"com.acme.validation.safetyChecking\">failOnError</property></validation-config>"
				},
				{ "4.0", "/META-INF/validation-configuration-4.0.xsd", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<validation-config xmlns=\"https://jakarta.ee/xml/ns/validation/configuration\" xsi:schemaLocation=\"https://jakarta.ee/xml/ns/validation/configuration https://jakarta.ee/xml/ns/validation/validation-configuration-4.0.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"4.0\"><property name=\"com.acme.validation.safetyChecking\">failOnError</property></validation-config>"
				},

				{ "1.0", "/META-INF/validation-mapping-1.0.xsd", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<constraint-mappings xmlns=\"http://jboss.org/xml/ns/javax/validation/mapping\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jboss.org/xml/ns/javax/validation/mapping validation-mapping-1.0.xsd\"><constraint-definition annotation=\"jakarta.validation.constraints.NotNull\"><validated-by><value>com.acme.SomeValidator</value></validated-by></constraint-definition></constraint-mappings>"
				},
				{ "1.1", "/META-INF/validation-mapping-1.1.xsd", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<constraint-mappings xmlns=\"http://jboss.org/xml/ns/javax/validation/mapping\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jboss.org/xml/ns/javax/validation/mapping validation-mapping-1.1.xsd\" version=\"1.1\"><constraint-definition annotation=\"jakarta.validation.constraints.NotNull\"><validated-by><value>com.acme.SomeValidator</value></validated-by></constraint-definition></constraint-mappings>"
				},
				{ "2.0", "/META-INF/validation-mapping-2.0.xsd", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<constraint-mappings xmlns=\"http://xmlns.jcp.org/xml/ns/validation/mapping\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://xmlns.jcp.org/xml/ns/validation/mapping http://xmlns.jcp.org/xml/ns/validation/mapping/validation-mapping-2.0.xsd\" version=\"2.0\"><constraint-definition annotation=\"jakarta.validation.constraints.NotNull\"><validated-by><value>com.acme.SomeValidator</value></validated-by></constraint-definition></constraint-mappings>"
				},
				{ "3.0", "/META-INF/validation-mapping-3.0.xsd", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<constraint-mappings xmlns=\"https://jakarta.ee/xml/ns/validation/mapping\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"https://jakarta.ee/xml/ns/validation/mapping https://jakarta.ee/xml/ns/validation/validation-mapping-3.0.xsd\" version=\"3.0\"><constraint-definition annotation=\"jakarta.validation.constraints.NotNull\"><validated-by><value>com.acme.SomeValidator</value></validated-by></constraint-definition></constraint-mappings>"
				},
				{ "3.1", "/META-INF/validation-mapping-3.1.xsd", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<constraint-mappings xmlns=\"https://jakarta.ee/xml/ns/validation/mapping\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"https://jakarta.ee/xml/ns/validation/mapping https://jakarta.ee/xml/ns/validation/validation-mapping-3.1.xsd\" version=\"3.1\"><constraint-definition annotation=\"jakarta.validation.constraints.NotNull\"><validated-by><value>com.acme.SomeValidator</value></validated-by></constraint-definition></constraint-mappings>"
				},
				{ "4.0", "/META-INF/validation-mapping-4.0.xsd", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<constraint-mappings xmlns=\"https://jakarta.ee/xml/ns/validation/mapping\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"https://jakarta.ee/xml/ns/validation/mapping https://jakarta.ee/xml/ns/validation/validation-mapping-4.0.xsd\" version=\"4.0\"><constraint-definition annotation=\"jakarta.validation.constraints.NotNull\"><validated-by><value>com.acme.SomeValidator</value></validated-by></constraint-definition></constraint-mappings>"
				},
		};
	}

}
