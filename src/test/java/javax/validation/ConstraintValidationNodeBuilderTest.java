/*
* JBoss, Home of Professional Open Source
* Copyright 2012-2013, Red Hat, Inc. and/or its affiliates, and individual contributors
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

import java.util.Map;

/**
 * This class does not actually run tests but rather makes sure
 * various node creation are compiled.
 *
 * @author Emmanuel Bernard <emmanuel@hibernate.org>
 */
public class ConstraintValidationNodeBuilderTest {
	public void testGenerals() throws Exception {
		ConstraintValidatorContext context = null;

		//From a property-level constraint on User.addresses
		//Build a constraint violation on the default path - ie the "addresses" property
		context.buildConstraintViolationWithTemplate( "this detail is wrong" )
		            .addConstraintViolation();

		//From a class level constraint on Address
		//Build a constraint violation on the default path + "street"
		//ie the street property of Address
		context.buildConstraintViolationWithTemplate( "this detail is wrong" )
		            .addPropertyNode( "street" )
		            .addConstraintViolation();

		//From a property-level constraint on  User.addresses
		//Build a constraint violation on the default path + the bean stored
		//under the "home" key in the map
		context.buildConstraintViolationWithTemplate( "Incorrect home address" )
		            .addBeanNode()
		                .inIterable().atKey( "home" )
		            .addConstraintViolation();

		//note that this fails in IntelliJ IDEA but works for javac
		//context.buildConstraintViolationWithTemplate( "Incorrect home address" )
		//		.addNode( null )
		//		.inIterable().atKey( "home" )
		//		.addConstraintViolation();


		//From a class level constraint on User
		//Build a constraint violation on the default path + addresses["home"].country.name
		//ie property "country.name" on the object stored under "home" in the map
		context.buildConstraintViolationWithTemplate( "this detail is wrong" )
		            .addPropertyNode( "addresses" )
		            .addPropertyNode( "country" )
		                .inIterable().atKey( "home" )
		            .addPropertyNode( "name" )
		            .addConstraintViolation();
	}

	public void testParameterNode() throws Exception {
		ConstraintValidatorContext context = null;

		//cross-parameter constraint on method createUser(String password, String passwordRepeat)
		context.buildConstraintViolationWithTemplate("Passwords do not match")
		            .addParameterNode(1)
		            .addConstraintViolation();

		// Edgy case where simple types are considered beans
		// on createUser(Map<String,String> user, Map<String,String> otherUser)
		context.buildConstraintViolationWithTemplate("Map entry password does not match")
		            .addParameterNode(1)
		            .addBeanNode() //bean node for a primitive type
		                .inIterable().atKey("password")
		            .addConstraintViolation();

		//Cross-parameter constraint on a method
		//mergeAddresses(Map<String,Address> addresses, Map<String,Address> otherAddresses)
		//Build a constraint violation on the default path + "otherAddresses["home"]
		//ie. the Address bean hosted in the "home" key of the "otherAddresses" map parameter
		context.buildConstraintViolationWithTemplate( "Map entry home present in both and does not match")
		            .addParameterNode(1)
		            .addBeanNode()
		                .inIterable().atKey("home")
		            .addConstraintViolation();

		//Cross-parameter constraint on a method
		//mergeAddresses(Map<String,Address> addresses, Map<String,Address> otherAddresses)
		//Build a constraint violation on the default path + "otherAddresses["home"].city
		//ie. on the "city" property of the Address bean hosted in
		//the "home" key of the "otherUser" map
		context.buildConstraintViolationWithTemplate( "Map entry home present in both but city does not match")
		            .addParameterNode(1)
		            .addPropertyNode("city")
		                .inIterable().atKey("home")
		            .addConstraintViolation();
	}

	//assuming the following domain model
	public static class User {
	    public Map<String,Address> getAddresses() { return null; }
	}

	public static class Address {
	    public String getStreet() { return null; }
	    public Country getCountry() { return null; }
	}

	public static class Country {
	   public String getName() { return null; }
	}

}
