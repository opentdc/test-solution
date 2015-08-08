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
package test.org.opentdc.resources;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.resources.ResourceModel;
import org.opentdc.resources.ResourcesService;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.ContactTest;

public class ResourcesBatchedListsTest extends AbstractTestClient {
	private static AddressbookModel adb = null;
	private WebClient resourceWC = null;
	private WebClient addressbookWC = null;

	@Before
	public void initializeTests() {
		resourceWC = createWebClient(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		adb = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName(), Status.OK);
	}
	
	@After
	public void cleanupTest() {
		AddressbookTest.delete(addressbookWC, adb.getId(), Status.NO_CONTENT);
		System.out.println("deleted 1 addressbook");
		addressbookWC.close();
		resourceWC.close();
	}

	@Test
	public void testResourceBatchedList() {
		ArrayList<ResourceModel> _localList = new ArrayList<ResourceModel>();		
		Response _response = null;
		System.out.println("***** testResourceListBatchDefSizeStatic:");
		resourceWC.replacePath("/");
		// we want to allocate more than double the amount of default list size objects
		int _batchSize = GenericService.DEF_SIZE;
		int _increment = 5;
		int _limit2 = 2 * _batchSize + _increment;		// if DEF_SIZE == 25 -> _limit2 = 55
		ResourceModel _res = null;
		ContactModel _cm = null;
		for (int i = 0; i < _limit2; i++) {
			// create(new()) -> _localList
			_cm = createContact("MY_FNAME" + i, "MY_LNAME" + i);
			_res = new ResourceModel();
			_res.setName(String.format("%2d", i));
			_res.setFirstName(_cm.getFirstName());
			_res.setLastName(_cm.getLastName());
			_res.setContactId(_cm.getId());
			_response = resourceWC.post(_res);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ResourceModel.class));
			System.out.println("posted ResourceModel " + _res.getName());
		}
		System.out.println("****** locallist:");
		for (ResourceModel _rm : _localList) {
			System.out.println(_rm.getName());
		}

		// get first batch
		// list(position=0, size=25) -> elements 0 .. 24
		resourceWC.resetQuery();
		_response = resourceWC.replacePath("/").get();
		List<ResourceModel> _remoteList1 = new ArrayList<ResourceModel>(resourceWC.getCollection(ResourceModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 1st Batch:");
		for (ResourceModel _rm : _remoteList1) {
			System.out.println(_rm.getName());
		}
		assertEquals("size of lists should be the same", _batchSize, _remoteList1.size());
		
		// get second batch
		// list(position=25, size=25) -> elements 25 .. 49
		resourceWC.resetQuery();
		_response = resourceWC.replacePath("/").query("position", 25).get();
		List<ResourceModel> _remoteList2 = new ArrayList<ResourceModel>(resourceWC.getCollection(ResourceModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("size of lists should be the same", _batchSize, _remoteList2.size());
		System.out.println("****** 2nd Batch:");
		for (ResourceModel _rm : _remoteList2) {
			System.out.println(_rm.getName());
		}
		
		// get rest 
		// list(position=50, size=25) ->   elements 50 .. 54
		resourceWC.resetQuery();
		_response = resourceWC.replacePath("/").query("position", 50).query("size", Integer.toString(_increment)).get();
		List<ResourceModel> _remoteList3 = new ArrayList<ResourceModel>(resourceWC.getCollection(ResourceModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 3rd Batch:");
		for (ResourceModel _rm : _remoteList3) {
			System.out.println(_rm.getName());
		}
		assertEquals("size of lists should be the same", _increment, _remoteList3.size());
		
		// testing the batches
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
		List<ResourceModel> _remoteList = null;
		System.out.println("***** testResourceListIterate:");
		while(true) {
			_numberOfBatches++;
			resourceWC.resetQuery();
			_response = resourceWC.replacePath("/").query("position", _position).get();
			_remoteList = new ArrayList<ResourceModel>(resourceWC.getCollection(ResourceModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_numberOfReturnedObjects += _remoteList.size();
			System.out.println("batch " + _numberOfBatches + ": position=" + _position + ", returnedObjects=" + _numberOfReturnedObjects);
			if (_remoteList.size() < GenericService.DEF_SIZE) {
				break;
			} else {
				_position += GenericService.DEF_SIZE;					
			}
		}
		assertTrue("number of batches should be as expected", _numberOfBatches >= 3);
		assertTrue("should have returned all objects", _numberOfReturnedObjects >= _limit2);
	
		// testing some explicit positions and sizes
		resourceWC.resetQuery();
		// get next 5 elements from position 5
		_response = resourceWC.replacePath("/").query("position", 5).query("size", 5).get();
		_remoteList = new ArrayList<ResourceModel>(resourceWC.getCollection(ResourceModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// get last 4 elements 
		resourceWC.resetQuery();
		_response = resourceWC.replacePath("/").query("position", _limit2-4).query("size", 4).get();
		_remoteList = new ArrayList<ResourceModel>(resourceWC.getCollection(ResourceModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 4, _remoteList.size());
		
		// removing all test objects
		for (ResourceModel _c : _localList) {
			_response = resourceWC.replacePath(_c.getId()).delete();
		}		
	}
	
	private ContactModel createContact(String fName, String lName) {
		ContactModel _cm = new ContactModel();
		_cm.setFirstName(fName);
		_cm.setLastName(lName);
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).path(ContactTest.PATH_EL_CONTACT).post(_cm);
		return _response.readEntity(ContactModel.class);
	}
	
	protected int calculateMembers() {
		return 1;
	}

}
