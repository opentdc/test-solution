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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.service.GenericService;

import test.org.opentdc.AbstractTestClient;

public class ContactAllListTest extends AbstractTestClient<AddressbooksService> {
	private static final String API = "api/addressbooks/";
	public static final String PATH_EL_CONTACT = "contact";
	ArrayList<AddressbookModel> addressbooks = null;
	private static final int nrAddressbooks = 3;
	private static final int nrContacts = 10;
	private static int totalContacts = nrAddressbooks * nrContacts;

	@Before
	public void initializeTest(
	) {
		initializeTest(API, AddressbooksService.class);
		addressbooks = new ArrayList<AddressbookModel>();
		AddressbookModel _abm = null;
		for (int i = 0; i < nrAddressbooks; i++) {
			_abm = createAddressbook("ContactAllListTest" + i);
			addressbooks.add(_abm);
			for (int j = 0; j < nrContacts; j++) {
				createContact(_abm.getId(), "addressbook" + i + "_contact" + j, "Test");
			}
		}
	}
	
	@After
	public void cleanupTest() {
		for (AddressbookModel _am : addressbooks) {
			deleteAddressbook(_am.getId());
		}
	}
	
	@Test
	public void testContactAllList() {
		Response _response = webclient.replacePath("/").path("allContacts").query("size", totalContacts + 1).get();
		List<ContactModel> _remoteList = new ArrayList<ContactModel>(webclient.getCollection(ContactModel.class));
		assertEquals("listAllContacts() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("should have returned all objects", totalContacts, _remoteList.size());		
	}
	
	@Test
	public void testContactAllListBatched() {
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
		List<ContactModel> _remoteList = null;
		Response _response = null;
		while(true) {
			_numberOfBatches++;
			webclient.resetQuery();
			_response = webclient.replacePath("/").path("allContacts").query("position", _position).get();
			_remoteList = new ArrayList<ContactModel>(webclient.getCollection(ContactModel.class));
			assertEquals("listAllContacts() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_numberOfReturnedObjects += _remoteList.size();
			System.out.println("batch " + _numberOfBatches + ": position=" + _position + ", returnedObjects=" + _numberOfReturnedObjects);
			if (_remoteList.size() < GenericService.DEF_SIZE) {
				break;
			} else {
				_position += GenericService.DEF_SIZE;					
			}
		}
		int nrFullBatches = totalContacts / GenericService.DEF_SIZE;
		int lastIncrement = totalContacts % GenericService.DEF_SIZE;
		
		assertEquals("number of batches should be as expected", nrFullBatches + 1, _numberOfBatches);
		assertEquals("should have returned all objects", totalContacts, _numberOfReturnedObjects);
		assertEquals("last batch size should be as expected", lastIncrement, _remoteList.size());
	}
	
	@Test
	public void testContactAllListExplicitQueries() {
		// testing some explicit positions and sizes
		webclient.resetQuery();
		// get next _nrElements elements from position _nrElements
		int _nrElements = 7;
		Response _response = webclient.replacePath("/").path("allContacts").query("position", _nrElements).query("size", _nrElements).get();
		List<ContactModel> _remoteList = new ArrayList<ContactModel>(webclient.getCollection(ContactModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", _nrElements, _remoteList.size());
		
		// get last _nrElements elements 
		webclient.resetQuery();
		_response = webclient.replacePath("/").path("allContacts").query("position", totalContacts - _nrElements).query("size", _nrElements).get();
		_remoteList = new ArrayList<ContactModel>(webclient.getCollection(ContactModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", _nrElements, _remoteList.size());
		
		// read over end of list
		webclient.resetQuery();
		_response = webclient.replacePath("/").path("allContacts").query("position", totalContacts - _nrElements).query("size", 2 * _nrElements).get();
		_remoteList = new ArrayList<ContactModel>(webclient.getCollection(ContactModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", _nrElements, _remoteList.size());		
	}
	
	private AddressbookModel createAddressbook(String name) {
		Response _response = webclient.replacePath("/").post(new AddressbookModel(name));
		AddressbookModel _adb = _response.readEntity(AddressbookModel.class);
		System.out.println("posted AddressbookModel " + name + ": " + _adb.getId());
		return _adb;
	}
	
	private void deleteAddressbook(String id) {
		webclient.replacePath("/").path(id).delete();
		System.out.println("deleted AddressbookModel " + id);
	}

	private ContactModel createContact(String aid, String firstName, String lastName) {
		ContactModel _cm = new ContactModel();
		_cm.setFirstName(firstName);
		_cm.setLastName(lastName);
		Response _response = webclient.replacePath("/").path(aid).path(PATH_EL_CONTACT).post(_cm);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("posted ContactModel " + _cm.getFn() + " in addressbook " + aid);
		return _cm;
	}
}