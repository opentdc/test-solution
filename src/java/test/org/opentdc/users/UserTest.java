/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Arbalo AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package test.org.opentdc.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.service.exception.DuplicateException;
import org.opentdc.service.exception.NotFoundException;
import org.opentdc.service.exception.ValidationException;
import org.opentdc.users.UserModel;

import test.org.opentdc.AbstractTestClient;

public class UserTest extends AbstractTestClient {
	
	private static final String APP_URI = "http://localhost:8080/opentdc-services-test/api/users/";

	@BeforeClass
	public static void initializeTests() {
		System.out.println("initializing");
		initializeTests(APP_URI);
	}

	private List<UserModel> list(
	) {
		System.out.println("listing all users");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		List<UserModel> _collection = (List<UserModel>)webclient.replacePath("/").getCollection(UserModel.class);
		return _collection;
	}

	private UserModel create(
		UserModel p
	) throws DuplicateException {
		System.out.println("creating a user");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response resp = webclient.replacePath("/").post(p);
		if(resp.getStatus() == Status.CONFLICT.getStatusCode()) {
			throw new DuplicateException();
		} else {
			return resp.readEntity(UserModel.class);
		}
	}

	private UserModel read(
		String id
	) throws NotFoundException {
		System.out.println("reading a user");
		webclient.accept(MediaType.APPLICATION_JSON);
		Response resp = webclient.replacePath(id).get();
		if(resp.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			throw new NotFoundException();
		} else {
			return resp.readEntity(UserModel.class);
		}
	}

	private UserModel update(
		UserModel p
	) {
		System.out.println("updating a user");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response resp = webclient.replacePath("/").path(p.getId()).put(p);
		return resp.readEntity(UserModel.class);
	}

	private void delete(
		String id
	) {
		System.out.println("deleting a user");
		Response resp = webclient.replacePath(id).delete();
		if(resp.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			throw new NotFoundException();
		}
	}

	private void deleteAll(
	) {
		System.out.println("deleting all users");
		webclient.replacePath("/").delete();
	}

	private int count(
	) {
		return webclient.replacePath("count").get(Integer.class);
	}

	@Test
	public void crudTests(
	) throws Exception {

		deleteAll();
		assertEquals("there should be no user data left", 0, count());
		
		UserModel _p0 = new UserModel();
		// TODO: set some attributes manually
		assertNull("initially, there should be no ID", _p0.getId());
		assertEquals("there should be no data to start with",
				0, count());
		UserModel _p1 = create(_p0);
		assertNotNull("a unique ID should be set", _p1.getId());
		// TODO: all other attributes should be the same.

		UserModel _p2 = create(new UserModel());
		assertNotNull("a unique ID should be set", _p2.getId());
		assertNotSame("IDs should be different", _p1.getId(), _p2.getId());
		// TODO: check on the default attribute values of _p2
		// TODO: try to set invalid data attributes

		UserModel _p3 = create(new UserModel());
		assertNotNull("a unique ID should be set", _p3.getId());
		assertNotSame("IDs should be different", _p2.getId(), _p3.getId());
		assertNotSame("IDs should be different", _p1.getId(), _p3.getId());
		assertEquals("there should be 3 users", 3, count());
		try {
			create(_p1);
			assertTrue("creating a duplicate should raise an exception", true);
		} catch (DuplicateException _ex) {
			System.out.println("DuplicateException was raised correctly when trying to create a duplicate");
			// test for exception message
		}
		List<UserModel> _l = list();
		System.out.println("list() = <" + _l + ">");
		UserModel _p11 = read(_p1.getId());
		assertEquals("IDs should be equal when read twice", _p1.getId(),
				_p11.getId());
		// TODO: same for all attributes

		try {
			read(null);
			assertTrue("reading a user with ID = null should fail", true);
		} catch (ValidationException ex) {
			System.out.println("ValidationException was raised correctly for invalid ID");
		}
		try {
			read("12366");
			assertTrue("reading a non-existing user should fail", true);
		} catch (NotFoundException ex) {
			System.out.println("NotFoundException was raised correctly");
		}
		assertEquals("there should be still 3 users", 3, count());

		String _id = _p2.getId();
		delete(_id);

		assertEquals("there should be only 2 users", 2, count());

		try {
			delete(_id);
			assertTrue("deleting a deleted user should fail", true);
		} catch (NotFoundException ex) {
			System.out.println("NotFoundException was raised correctly");
		}
		assertEquals("there should be still 2 users", 2, count());
		try {
			read(_id);
			assertTrue("reading a deleted user should fail",
					true);
		} catch (NotFoundException ex) {
			System.out.println("NotFoundException was raised correctly");
		}

		_p1.setLoginID("TestLoginID");
		_p1.setHashedPassword("TestHashedPassword");
		_p1.setSalt("TestSalt");
		_p1 = update(_p1);
		assertEquals("from should be TestLoginID", "TestLoginID", _p1.getLoginID());
		assertEquals("to should be TestHashedPassword", "TestHashedPassword", _p1.getHashedPassword());
		assertEquals("comment should be TestSalt", "TestSalt", _p1.getSalt());

		_p2 = read(_p1.getId());
		assertEquals("from should be TestLoginID", "TestLoginID", _p1.getLoginID());
		assertEquals("to should be TestHashedPassword", "TestHashedPassword", _p1.getHashedPassword());
		assertEquals("comment should be TestSalt", "TestSalt", _p1.getSalt());
		assertEquals("IDs should be the same", _p1.getId(), _p2.getId());
	}

}
