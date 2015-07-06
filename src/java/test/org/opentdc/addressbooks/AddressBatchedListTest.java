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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressModel;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AttributeType;
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.service.GenericService;

import test.org.opentdc.AbstractTestClient;

public class AddressBatchedListTest extends AbstractTestClient {
	private static AddressbookModel adb = null;
	private static ContactModel contact = null;
	private WebClient addressbookWC = null;
		
	@Before
	public void initializeTests() {
		addressbookWC = AddressbookTest.createAddressbookWebClient();
		adb = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName());
		contact = ContactTest.createContact(addressbookWC, adb.getId(), this.getClass().getName(), "Test");
	}
	
	@After
	public void cleanupTest() {
		AddressbookTest.cleanup(addressbookWC, adb.getId(), this.getClass().getName());
	}

	@Test
	public void testAddressBatchedList() {
		ArrayList<AddressModel> _localList = new ArrayList<AddressModel>();		
		System.out.println("***** testAddressBatchedList:");
		Response _response = null;
		addressbookWC.replacePath("/").path(adb.getId()).path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).path(AddressTest.PATH_EL_ADDRESS);
		// we want to allocate more than double the amount of default list size objects
		int _batchSize = GenericService.DEF_SIZE;
		int _increment = 5;
		int _limit2 = 2 * _batchSize + _increment;		// if DEF_SIZE == 25 -> _limit2 = 55
		for (int i = 0; i < _limit2; i++) {
			// create(new()) -> _localList
			_response = addressbookWC.post(
					AddressTest.createUrlAddress(
							AttributeType.HOME, "testAddressBatchedList" + i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(AddressModel.class));
		}

		// get first batch
		// list(position=0, size=25) -> elements 0 .. 24
		addressbookWC.resetQuery();
		_response = addressbookWC.replacePath("/").
				path(adb.getId()).path(ContactTest.PATH_EL_CONTACT).
				path(contact.getId()).path(AddressTest.PATH_EL_ADDRESS).get();
		List<AddressModel> _remoteList1 = new ArrayList<AddressModel>(addressbookWC.getCollection(AddressModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 1st Batch:");
		for (AddressModel _am : _remoteList1) {
			System.out.println(_am.getId());
		}
		assertEquals("size of lists should be the same", _batchSize, _remoteList1.size());
		
		// get second batch
		// list(position=25, size=25) -> elements 25 .. 49
		addressbookWC.resetQuery();
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(AddressTest.PATH_EL_ADDRESS).query("position", 25).get();
		List<AddressModel> _remoteList2 = new ArrayList<AddressModel>(addressbookWC.getCollection(AddressModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("size of lists should be the same", _batchSize, _remoteList2.size());
		System.out.println("****** 2nd Batch:");
		for (AddressModel _am : _remoteList2) {
			System.out.println(_am.getId());
		}
		
		// get rest 
		// list(position=50, size=25) ->   elements 50 .. 54
		addressbookWC.resetQuery();
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(AddressTest.PATH_EL_ADDRESS).query("position", 50).get();
		List<AddressModel> _remoteList3 = new ArrayList<AddressModel>(addressbookWC.getCollection(AddressModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 3rd Batch:");
		for (AddressModel _am : _remoteList3) {
			System.out.println(_am.getId());
		}
		assertEquals("size of lists should be the same", _increment, _remoteList3.size());
		
		// testing the batches
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
		List<AddressModel> _remoteList = null;
		while(true) {
			_numberOfBatches++;
			addressbookWC.resetQuery();
			_response = addressbookWC.replacePath("/").path(adb.getId()).
					path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
					path(AddressTest.PATH_EL_ADDRESS).query("position", _position).get();
			_remoteList = new ArrayList<AddressModel>(addressbookWC.getCollection(AddressModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_numberOfReturnedObjects += _remoteList.size();
			System.out.println("batch " + _numberOfBatches + ": position=" + _position + ", returnedObjects=" + _numberOfReturnedObjects);
			if (_remoteList.size() < GenericService.DEF_SIZE) {
				break;
			} else {
				_position += GenericService.DEF_SIZE;					
			}
		}
		assertEquals("number of batches should be as expected", 3, _numberOfBatches);
		assertEquals("should have returned all objects", _limit2, _numberOfReturnedObjects);
		assertEquals("last batch size should be as expected", _increment, _remoteList.size());
	
		// testing some explicit positions and sizes
		addressbookWC.resetQuery();
		// get next 5 elements from position 5
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(AddressTest.PATH_EL_ADDRESS).query("position", 5).query("size", 5).get();
		_remoteList = new ArrayList<AddressModel>(addressbookWC.getCollection(AddressModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// get last 4 elements 
		addressbookWC.resetQuery();
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(AddressTest.PATH_EL_ADDRESS).query("position", _limit2-4).query("size", 4).get();
		_remoteList = new ArrayList<AddressModel>(addressbookWC.getCollection(AddressModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 4, _remoteList.size());
		
		// read over end of list
		addressbookWC.resetQuery();
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(AddressTest.PATH_EL_ADDRESS).query("position", _limit2-5).query("size", 10).get();
		_remoteList = new ArrayList<AddressModel>(addressbookWC.getCollection(AddressModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// removing all test objects
		for (AddressModel _c : _localList) {
			_response = addressbookWC.replacePath("/").path(adb.getId()).
					path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
					path(AddressTest.PATH_EL_ADDRESS).path(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}		
	}
}
