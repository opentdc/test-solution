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
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

public class AddressbookBatchedListTest extends AbstractTestClient {
	private WebClient addressbookWC = null;

	@Before
	public void initializeTests() {
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
	}

	@After
	public void cleanupTest() {
		addressbookWC.close();
	}
	
	@Test
	public void testAddressbookBatchedList() {
		ArrayList<AddressbookModel> _localList = new ArrayList<AddressbookModel>();		
		Response _response = null;
		System.out.println("***** testAddressbookBatchedList:");
		System.out.println("initial list:");
		_response = addressbookWC.replacePath("/").get();
		List<AddressbookModel> _initialList = new ArrayList<AddressbookModel>(addressbookWC.getCollection(AddressbookModel.class));
		for (AddressbookModel _iam : _initialList) {
			System.out.println(_iam.getId() + ": " + _iam.getName());
		}	
		System.out.println("total: " + _initialList.size());
		System.out.println("(initially, only the implicit 'all' addressbook should exist)");
				
		// we want to allocate more than double the amount of default list size objects
		int _batchSize = GenericService.DEF_SIZE;
		int _increment = 5;
		int _limit2 = 2 * _batchSize + _increment;		// if DEF_SIZE == 25 -> _limit2 = 55
		AddressbookModel _am = null;
		for (int i = 0; i < _limit2; i++) {
			// create(new()) -> _localList
			_am = new AddressbookModel();
			_am.setName(String.format("%2d", i));
			_response = addressbookWC.post(_am);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(AddressbookModel.class));
			System.out.println("posted AddressbookModel " + _am.getName());
		}

		// get first batch
		// list(position=0, size=25) -> elements 0 .. 24
		addressbookWC.resetQuery();
		_response = addressbookWC.replacePath("/").get();
		List<AddressbookModel> _remoteList1 = new ArrayList<AddressbookModel>(addressbookWC.getCollection(AddressbookModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 1st Batch:");
		for (AddressbookModel _am1 : _remoteList1) {
			System.out.println(_am1.getName());
		}
		System.out.println("total: " + _remoteList1.size());
	
		assertEquals("size of lists should be the same", _batchSize, _remoteList1.size());
		
		// get second batch
		// list(position=25, size=25) -> elements 25 .. 49
		addressbookWC.resetQuery();
		_response = addressbookWC.replacePath("/").query("position", 25).get();
		List<AddressbookModel> _remoteList2 = new ArrayList<AddressbookModel>(addressbookWC.getCollection(AddressbookModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("size of lists should be the same", _batchSize, _remoteList2.size());
		System.out.println("****** 2nd Batch:");
		for (AddressbookModel _am2 : _remoteList2) {
			System.out.println(_am2.getName());
		}
		System.out.println("total: " + _remoteList2.size());
		
		// get rest 
		// list(position=50, size=25) ->   elements 50 .. 54
		addressbookWC.resetQuery();
		_response = addressbookWC.replacePath("/").query("position", 50).get();
		List<AddressbookModel> _remoteList3 = new ArrayList<AddressbookModel>(addressbookWC.getCollection(AddressbookModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 3rd Batch:");
		for (AddressbookModel _am3 : _remoteList3) {
			System.out.println(_am3.getName());
		}
		System.out.println("total: " + _remoteList3.size());
		assertEquals("size of lists should be the same", _increment + _initialList.size(), _remoteList3.size());
		
		// testing the batches
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
		List<AddressbookModel> _remoteList = null;
		System.out.println("***** testAddressbookListIterate:");
		while(true) {
			_numberOfBatches++;
			addressbookWC.resetQuery();
			_response = addressbookWC.replacePath("/").query("position", _position).get();
			_remoteList = new ArrayList<AddressbookModel>(addressbookWC.getCollection(AddressbookModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_numberOfReturnedObjects += _remoteList.size();
			System.out.println("batch " + _numberOfBatches + ": position=" + _position + ", returnedObjectsByThisBatch=" + _remoteList.size() + ", returnedObjectsInTotal=" + _numberOfReturnedObjects);
			if (_remoteList.size() < GenericService.DEF_SIZE) {
				break;
			} else {
				_position += GenericService.DEF_SIZE;					
			}
		}
		assertEquals("number of batches should be as expected", 3, _numberOfBatches);
		assertEquals("should have returned all objects", _limit2 + _initialList.size(), _numberOfReturnedObjects);
		assertEquals("last batch size should be as expected", _increment + _initialList.size(), _remoteList.size());
	
		// testing some explicit positions and sizes
		addressbookWC.resetQuery();
		// get next 5 elements from position 5
		_response = addressbookWC.replacePath("/").query("position", 5).query("size", 5).get();
		_remoteList = new ArrayList<AddressbookModel>(addressbookWC.getCollection(AddressbookModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// get last 4 elements 
		addressbookWC.resetQuery();
		_response = addressbookWC.replacePath("/").query("position", _limit2-4).query("size", 4).get();
		_remoteList = new ArrayList<AddressbookModel>(addressbookWC.getCollection(AddressbookModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 4, _remoteList.size());
		
		// read over end of list
		addressbookWC.resetQuery();
		_response = addressbookWC.replacePath("/").query("position", _limit2 + _initialList.size() -5).query("size", 10).get();
		_remoteList = new ArrayList<AddressbookModel>(addressbookWC.getCollection(AddressbookModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// try to read some objects starting from the end of the list -> should return an empty list
		addressbookWC.resetQuery();
		_response = addressbookWC.replacePath("/").query("position", _limit2 + _initialList.size()).query("size", 10).get();
		_remoteList = new ArrayList<AddressbookModel>(addressbookWC.getCollection(AddressbookModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return with zero elements", 0, _remoteList.size());
		
		// removing all test objects
		for (AddressbookModel _c : _localList) {
			_response = addressbookWC.replacePath("/").path(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}		
	}
}
