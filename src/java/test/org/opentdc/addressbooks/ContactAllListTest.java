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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/**
 * Testing the listAllContacts() method
 * @author Bruno Kaiser
 *
 */
public class ContactAllListTest extends AbstractTestClient {
	private static ArrayList<AddressbookModel> addressbooks = null;
	private static final int nrAddressbooks = 3;
	private static final int nrContacts = 10;
	private static int totalContacts = nrAddressbooks * nrContacts;
	private static WebClient wc = null;

	/**
	 * Initialize the test with several contacts
	 */
	@BeforeClass
	public static void initializeTest() {
		wc = initializeTest(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		System.out.println("***** ContactAllListTest:");
		addressbooks = new ArrayList<AddressbookModel>();
		AddressbookModel _abm = null;
		for (int i = 0; i < nrAddressbooks; i++) {
			_abm = AddressbookTest.post(wc, new AddressbookModel("ContactAllListTest" + i), Status.OK);
			addressbooks.add(_abm);
			for (int j = 0; j < nrContacts; j++) {
				ContactTest.create(wc, _abm.getId(), "addressbook" + i + "_contact" + j, "ContactAllListTest");
			}
		}
	}
	
	@AfterClass
	public static void cleanupTest() {
		for (AddressbookModel _model : addressbooks) {
			AddressbookTest.delete(wc, _model.getId(), Status.NO_CONTENT);
		}
		System.out.println("deleted " + addressbooks.size() + " addressbooks");
		wc.close();
	}
	
	@Test
	public void testContactAllList() {
		System.out.println("a) testContactAllList:");
		Response _response = wc.replacePath("/").path("allContacts").query("size", totalContacts + 1).get();
		List<ContactModel> _remoteList = new ArrayList<ContactModel>(wc.getCollection(ContactModel.class));
		assertEquals("listAllContacts() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertTrue("should have returned all objects", _remoteList.size() >= totalContacts);		
		System.out.println("list() returns " + _remoteList.size() + " elements (_remoteList).");
	}
	
	/**
	 * Same as testContactAllList but with batchwise listing.
	 */
	@Test
	public void testContactAllListBatched() {
		System.out.println("b) testContactAllListBatched:");
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
		List<ContactModel> _remoteList = null;
		Response _response = null;
		while(true) {
			_numberOfBatches++;
			wc.resetQuery();
			_response = wc.replacePath("/").path("allContacts").query("position", _position).get();
			_remoteList = new ArrayList<ContactModel>(wc.getCollection(ContactModel.class));
			assertEquals("listAllContacts() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_numberOfReturnedObjects += _remoteList.size();
			System.out.println("batch " + _numberOfBatches + ": position=" + _position + ", returnedObjects=" + _numberOfReturnedObjects);
			if (_remoteList.size() < GenericService.DEF_SIZE) {
				break;
			} else {
				_position += GenericService.DEF_SIZE;					
			}
		}
		validateBatches(_numberOfBatches, _numberOfReturnedObjects, _remoteList.size());
	}
	
	@Test
	public void testContactAllListExplicitQueries() {
		System.out.println("c) testContactAllListExplicitQueries:");
		// testing some explicit positions and sizes
		wc.resetQuery();
		// get next _nrElements elements from position _nrElements
		int _nrElements = 7;
		Response _response = wc.replacePath("/").path("allContacts").query("position", _nrElements).query("size", _nrElements).get();
		List<ContactModel> _remoteList = new ArrayList<ContactModel>(wc.getCollection(ContactModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", _nrElements, _remoteList.size());
		
		// get last _nrElements elements 
		wc.resetQuery();
		_response = wc.replacePath("/").path("allContacts").query("position", totalContacts - _nrElements).query("size", _nrElements).get();
		_remoteList = new ArrayList<ContactModel>(wc.getCollection(ContactModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", _nrElements, _remoteList.size());
		
		// read over end of list
		wc.resetQuery();
		_response = wc.replacePath("/").path("allContacts").query("position", totalContacts - _nrElements).query("size", 2 * _nrElements).get();
		_remoteList = new ArrayList<ContactModel>(wc.getCollection(ContactModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertTrue("list() should return correct number of elements", _remoteList.size() >= _nrElements);		
	}
			
	protected int calculateMembers() {
		return nrAddressbooks * nrContacts;
	}
}