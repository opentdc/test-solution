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
package test.org.opentdc.addressbooks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.service.exception.DuplicateException;
import org.opentdc.service.exception.NotFoundException;

import test.org.opentdc.AbstractTestClient;

public class AddressbookTest extends AbstractTestClient {
	
	private static final String APP_URI = "http://localhost:8080/opentdc-services-test/api/addressbooks/";

	@BeforeClass
	public static void initializeTests(
	) {
		initializeTests(APP_URI);
	}
	
	private List<AddressbookModel> list(
	) throws Exception {
		System.out.println("listing all addressbooks");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		return (List<AddressbookModel>)webclient.replacePath("/").getCollection(AddressbookModel.class);
	}

	private AddressbookModel create(
		AddressbookModel p
	) throws DuplicateException {
		System.out.println("creating an addressbook");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response resp = webclient.replacePath("/").post(p);
		if(resp.getStatus() == Status.CONFLICT.getStatusCode()) {
			throw new DuplicateException();
		} else {
			return resp.readEntity(AddressbookModel.class);
		}
	}

	private AddressbookModel read(
		String id
	) throws NotFoundException {
		System.out.println("reading an addressbook");
		webclient.accept(MediaType.APPLICATION_JSON);
		Response resp = webclient.replacePath(id).get();
		if(resp.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			throw new NotFoundException();
		} else {
			return resp.readEntity(AddressbookModel.class);
		}
	}

	private AddressbookModel update(
		AddressbookModel p
	) {
		System.out.println("updating an addressbook");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response resp = webclient.replacePath("/").path(p.getId()).put(p);
		return resp.readEntity(AddressbookModel.class);
	}

	private void delete(
		String id
	) throws NotFoundException {
		System.out.println("deleting an addressbook");
		Response resp = webclient.replacePath(id).delete();
		if(resp.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			throw new NotFoundException();
		}
	}

	private void deleteAll(
	) {
		System.out.println("deleting all addressbooks");
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
		
		AddressbookModel p0 = new AddressbookModel();
		// TODO: set some attributes manually
		assertNull("initially, there should be no ID", p0.getId());
		assertEquals("there should be no data to start with", 0, count());
		AddressbookModel p1 = create(p0);
		assertNotNull("a unique ID should be set", p1.getId());
		// TODO: all other attributes should be the same.

		AddressbookModel p2 = create(new AddressbookModel());
		assertNotNull("a unique ID should be set", p2.getId());
		assertNotSame("IDs should be different", p1.getId(), p2.getId());
		// TODO: check on the default attribute values of _p2
		// TODO: try to set invalid data attributes

		AddressbookModel p3 = create(new AddressbookModel());
		assertNotNull("a unique ID should be set", p3.getId());
		assertNotSame("IDs should be different", p2.getId(), p3.getId());
		assertNotSame("IDs should be different", p1.getId(), p3.getId());
		assertEquals("there should be 3 addressbooks", 3, count());
		try {
			create(p1);
			assertTrue("creating a duplicate should raise an exception", true);
		} catch (DuplicateException _ex) {
			System.out.println("DuplicateException was raised correctly when trying to create a duplicate");
			// test for exception message
		}
		List<AddressbookModel> addressbooks = list();
		System.out.println("list() = <" + addressbooks + ">");
		AddressbookModel p11 = read(p1.getId());
		assertEquals("IDs should be equal when read twice", p1.getId(), p11.getId());
		// TODO: same for all attributes
		try {
			read(null);
			assertTrue("reading an addressbook with ID = null should fail", true);
		} catch (NotFoundException ex) {
			System.out.println("NotFoundException was raised correctly for invalid ID");
		}
		try {
			read("12366");
			assertTrue(
					"reading a non-existing addressbook should fail", true);
		} catch (NotFoundException ex) {
			System.out.println("NotFoundException was raised correctly");
		}
		assertEquals("there should be still 3 addressbooks", 3, count());
		String id = p2.getId();
		delete(id);
		assertEquals("there should be only 2 addressbooks", 2, count());

		try {
			delete(id);
			assertTrue( "deleting a deleted addressbook should fail", true);
		} catch (NotFoundException ex) {
			System.out.println("NotFoundException was raised correctly");
		}
		assertEquals("there should be still 2 addressbooks", 2, count());
		try {
			read(id);
			assertTrue("reading a deleted addressbook should fail", true);
		} catch (NotFoundException ex) {
			System.out.println("NotFoundException was raised correctly");
		}
		p1.setName("TestName");
		p1 = update(p1);
		assertEquals("from should be TestName", "TestName", p1.getName());

		p2 = read(p1.getId());
		assertEquals("from should be TestName", "TestName", p1.getName());
		assertEquals("IDs should be the same", p1.getId(), p2.getId());
	
	}

}
