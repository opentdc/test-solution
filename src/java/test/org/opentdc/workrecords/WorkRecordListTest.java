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
package test.org.opentdc.workrecords;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.workrecords.WorkRecordModel;
import org.opentdc.workrecords.WorkRecordsService;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.WttService;
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
import test.org.opentdc.resources.ResourceTest;
import test.org.opentdc.wtt.CompanyTest;
import test.org.opentdc.wtt.ProjectTest;

public class WorkRecordListTest extends AbstractTestClient {
	private WebClient wc = null;
	private WebClient wttWC = null;
	private WebClient addressbookWC = null;
	private WebClient resourceWC = null;
	private CompanyModel company = null;
	private ProjectModel project = null;
	private AddressbookModel addressbook = null;
	private ResourceModel resource = null;
	private ContactModel contact = null;

	@Before
	public void initializeTests() {
		wc = createWebClient(ServiceUtil.WORKRECORDS_API_URL, WorkRecordsService.class);
		wttWC = createWebClient(ServiceUtil.WTT_API_URL, WttService.class);
		resourceWC = createWebClient(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);

		addressbook = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName(), Status.OK);
		company = CompanyTest.create(wttWC, addressbookWC, addressbook, this.getClass().getName(), "MY_DESC");
		project = ProjectTest.create(wttWC, company.getId(), this.getClass().getName(), "MY_DESC");
		contact = ContactTest.create(addressbookWC, addressbook.getId(), "FNAME", "LNAME");
		resource = ResourceTest.create(resourceWC, addressbook, contact, this.getClass().getName(), Status.OK);
	}

	@After
	public void cleanupTest() {
		AddressbookTest.delete(addressbookWC, addressbook.getId(), Status.NO_CONTENT);
		System.out.println("deleted 1 addressbook");
		addressbookWC.close();
		ResourceTest.cleanup(resourceWC, resource.getId(), this.getClass().getName());
		CompanyTest.cleanup(wttWC, company.getId(), this.getClass().getName());
		wc.close();
	}

	@Test
	public void testWorkRecordBatchedList() {
		ArrayList<WorkRecordModel> _localList = new ArrayList<WorkRecordModel>();		
		Response _response = null;
		System.out.println("***** testWorkRecordListBatchDefSizeStatic:");
		wc.replacePath("/");
		// we want to allocate more than double the amount of default list size objects
		int _batchSize = GenericService.DEF_SIZE;
		int _increment = 5;
		int _limit2 = 2 * _batchSize + _increment;		// if DEF_SIZE == 25 -> _limit2 = 55
		WorkRecordModel _model1 = null;
		Date _d = new Date();
		for (int i = 0; i < _limit2; i++) {
			// create(new()) -> _localList
			_model1 = WorkRecordTest.create(company, project, resource,
					_d, i, 10 * i, true, "testWorkRecordBatchedList" + i);
			_model1.setComment(String.format("%2d", i));
			_response = wc.post(_model1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(WorkRecordModel.class));
			System.out.println("posted WorkRecordModel " + _model1.getComment());
		}
		System.out.println("****** locallist:");
		for (WorkRecordModel _model : _localList) {
			System.out.println(_model.getComment());
		}

		// get first batch
		// list(position=0, size=25) -> elements 0 .. 24
		wc.resetQuery();
		_response = wc.replacePath("/").get();
		List<WorkRecordModel> _remoteList1 = new ArrayList<WorkRecordModel>(wc.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 1st Batch:");
		for (WorkRecordModel _model : _remoteList1) {
			System.out.println(_model.getComment());
		}
		assertEquals("size of lists should be the same", _batchSize, _remoteList1.size());
		
		// get second batch
		// list(position=25, size=25) -> elements 25 .. 49
		wc.resetQuery();
		_response = wc.replacePath("/").query("position", 25).get();
		List<WorkRecordModel> _remoteList2 = new ArrayList<WorkRecordModel>(wc.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("size of lists should be the same", _batchSize, _remoteList2.size());
		System.out.println("****** 2nd Batch:");
		for (WorkRecordModel _model : _remoteList2) {
			System.out.println(_model.getComment());
		}
		
		// get rest 
		// list(position=50, size=25) ->   elements 50 .. 54
		wc.resetQuery();
		_response = wc.replacePath("/").query("position", 50).query("size", Integer.toString(_increment)).get();
		List<WorkRecordModel> _remoteList3 = new ArrayList<WorkRecordModel>(wc.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 3rd Batch:");
		for (WorkRecordModel _model : _remoteList3) {
			System.out.println(_model.getComment());
		}
		assertEquals("size of lists should be the same", _increment, _remoteList3.size());
		
		// testing the batches
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
		List<WorkRecordModel> _remoteList = null;
		System.out.println("***** testWorkRecordListIterate:");
		while(true) {
			_numberOfBatches++;
			wc.resetQuery();
			_response = wc.replacePath("/").query("position", _position).get();
			_remoteList = new ArrayList<WorkRecordModel>(wc.getCollection(WorkRecordModel.class));
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
		assertTrue("last batch size should be as expected", _remoteList.size() >= _increment);
	
		// testing some explicit positions and sizes
		wc.resetQuery();
		// get next 5 elements from position 5
		_response = wc.replacePath("/").query("position", 5).query("size", 5).get();
		_remoteList = new ArrayList<WorkRecordModel>(wc.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// get last 4 elements 
		wc.resetQuery();
		_response = wc.replacePath("/").query("position", _limit2-4).query("size", 4).get();
		_remoteList = new ArrayList<WorkRecordModel>(wc.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 4, _remoteList.size());
		
		// read over end of list
		wc.resetQuery();
		_response = wc.replacePath("/").query("position", _limit2-5).query("size", 5).get();
		_remoteList = new ArrayList<WorkRecordModel>(wc.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// removing all test objects
		for (WorkRecordModel _model : _localList) {
			_response = wc.replacePath(_model.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}		
	}
	
	protected int calculateMembers() {
		return 1;
	}
}