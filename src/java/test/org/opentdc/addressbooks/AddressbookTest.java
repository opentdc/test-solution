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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.opentdc.addressbooks.AddressbookData;
import org.opentdc.addressbooks.AddressbookService;
import org.opentdc.service.exception.DuplicateException;
import org.opentdc.service.exception.NotFoundException;
import org.opentdc.service.exception.ValidationException;

public class AddressbookTest {
	private static final String APP_URI = "http://localhost:8080/opentdc/api/addressbooks/";
	private static WebClient webclient = null;

	@BeforeClass
	public static void initializeTests() {
		System.out.println("initializing");
		JAXRSClientFactoryBean _sf = new JAXRSClientFactoryBean();
		_sf.setResourceClass(AddressbookService.class);
		_sf.setAddress(APP_URI);
		BindingFactoryManager _manager = _sf.getBus().getExtension(
				BindingFactoryManager.class);
		JAXRSBindingFactory _factory = new JAXRSBindingFactory();
		_factory.setBus(_sf.getBus());
		_manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID,
				_factory);
		// AddressbookService _service = _sf.create(AddressbookService.class);
		webclient = _sf.createWebClient();
	}

	@After
	public void reset() {
		System.out.println("resetting the web client");
		webclient.reset();
	}

	@AfterClass
	public static void cleanup() {
		System.out.println("cleaning up");
		webclient.close();
	}

	private List<AddressbookData> list() throws Exception {
		System.out.println("listing all addressbooks");
		webclient.type(MediaType.APPLICATION_JSON).accept(
				MediaType.APPLICATION_JSON);
		List<AddressbookData> _collection = (List<AddressbookData>) webclient
				.getCollection(AddressbookData.class);
		return _collection;
	}

	private AddressbookData create(AddressbookData p) {
		System.out.println("creating an addressbook");
		webclient.type(MediaType.APPLICATION_JSON).accept(
				MediaType.APPLICATION_JSON);
		Response _r = webclient.post(p);
		return _r.readEntity(AddressbookData.class);
	}

	private AddressbookData read(String id) throws Exception {
		System.out.println("reading an addressbook");
		webclient.accept(MediaType.APPLICATION_JSON);
		return webclient.path(id).get(AddressbookData.class);
	}

	private AddressbookData update(AddressbookData p) {
		System.out.println("updating an addressbook");
		webclient.type(MediaType.APPLICATION_JSON).accept(
				MediaType.APPLICATION_JSON);
		Response _r = webclient.put(p);
		return _r.readEntity(AddressbookData.class);
	}

	private int delete(String id) {
		System.out.println("deleting an addressbook");
		return webclient.path(id).delete().getStatus();
	}

	private int deleteAll() {
		System.out.println("deleting all addressbooks");
		// return webclient.delete().getStatus();
		// we don't execute this by default; please be careful and think twice
		// before enabling
		return 200;
	}

	private int count() throws Exception {
		return webclient.path("count").get(Integer.class);
		/*
		 * URL _url = new URL(APP_URI + "count"); InputStream _in =
		 * _url.openStream(); int _recs = new
		 * Integer(getStringFromInputStream(_in));
		 * System.out.println("count() = " + _recs + " records"); _in.close();
		 * return _recs;
		 */
	}

	@Test
	public void crudTests() throws Exception {
		AddressbookData _p0 = new AddressbookData();
		// TODO: set some attributes manually
		org.junit.Assert.assertNull("initially, there should be no ID",
				_p0.getId());
		org.junit.Assert.assertEquals("there should be no data to start with",
				0, count());
		AddressbookData _p1 = create(_p0);
		org.junit.Assert
				.assertNotNull("a unique ID should be set", _p1.getId());
		// TODO: all other attributes should be the same.

		AddressbookData _p2 = create(new AddressbookData());
		org.junit.Assert
				.assertNotNull("a unique ID should be set", _p2.getId());
		org.junit.Assert.assertNotSame("IDs should be different", _p1.getId(),
				_p2.getId());
		// TODO: check on the default attribute values of _p2
		// TODO: try to set invalid data attributes

		AddressbookData _p3 = create(new AddressbookData());
		org.junit.Assert
				.assertNotNull("a unique ID should be set", _p3.getId());
		org.junit.Assert.assertNotSame("IDs should be different", _p2.getId(),
				_p3.getId());
		org.junit.Assert.assertNotSame("IDs should be different", _p1.getId(),
				_p3.getId());
		org.junit.Assert.assertEquals("there should be 3 addressbooks", 3,
				count());
		try {
			create(_p1);
			org.junit.Assert.assertTrue(
					"creating a duplicate should raise an exception", true);
		} catch (DuplicateException _ex) {
			System.out
					.println("DuplicateException was raised correctly when trying to create a duplicate");
			// test for exception message
		}
		List<AddressbookData> _l = list();
		System.out.println("list() = <" + _l + ">");
		AddressbookData _p11 = read(_p1.getId());
		assertEquals("IDs should be equal when read twice", _p1.getId(),
				_p11.getId());
		// TODO: same for all attributes

		try {
			read(null);
			org.junit.Assert.assertTrue(
					"reading an addressbook with ID = null should fail", true);
		} catch (ValidationException ex) {
			System.out
					.println("ValidationException was raised correctly for invalid ID");
		}
		try {
			read("12366");
			org.junit.Assert.assertTrue(
					"reading a non-existing addressbook should fail", true);
		} catch (NotFoundException ex) {
			System.out.println("NotFoundException was raised correctly");
		}
		assertEquals("there should be still 3 addressbooks", 3, count());

		String _id = _p2.getId();
		System.out.println("status = " + delete(_id));

		assertEquals("there should be only 2 addressbooks", 2, count());

		try {
			System.out.println("status = " + delete(_id));
			org.junit.Assert.assertTrue(
					"deleting a deleted addressbook should fail", true);
		} catch (NotFoundException ex) {
			System.out.println("NotFoundException was raised correctly");
		}
		assertEquals("there should be still 2 addressbooks", 2, count());
		try {
			read(_id);
			org.junit.Assert.assertTrue(
					"reading a deleted addressbook should fail", true);
		} catch (NotFoundException ex) {
			System.out.println("NotFoundException was raised correctly");
		}

		_p1.setName("TestName");
		_p1 = update(_p1);
		assertEquals("from should be TestName", "TestName", _p1.getName());

		_p2 = read(_p1.getId());
		assertEquals("from should be TestName", "TestName", _p1.getName());
		assertEquals("IDs should be the same", _p1.getId(), _p2.getId());

		// - update(4) -> not Found
		System.out.println("status = " + deleteAll());
		assertEquals("there should be no addressbook data left", 0, count());
		assertEquals("there should be no addressbook data left", 0, count());
	}

	/*
	 * Test for exceptions: try { mustThrowException(); fail(); } catch
	 * (Exception e) { // expected // could also check for message of exception
	 * etc. }
	 */
	public static void main(String[] args) {
		Result _result = JUnitCore.runClasses(AddressbookTest.class);
		System.out
				.println("BEWARE: this test will the data will be emptied when running this test !!");
		System.out.println("Proceed ? (y/n)");
		BufferedReader _br = new BufferedReader(
				new InputStreamReader(System.in));
		try {
			if (_br.readLine().toLowerCase().startsWith("y")) {
				for (Failure _failure : _result.getFailures()) {
					System.out.println(_failure.toString());
				}
			}
		} catch (IOException ioe) {
			System.out.println("IO error trying to read command line input");
		}
	}
}
