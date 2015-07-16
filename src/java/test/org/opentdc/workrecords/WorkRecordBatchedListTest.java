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
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.resources.ResourceModel;
import org.opentdc.service.GenericService;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.ContactTest;
import test.org.opentdc.resources.ResourcesTest;
import test.org.opentdc.wtt.CompanyTest;
import test.org.opentdc.wtt.ProjectTest;

public class WorkRecordBatchedListTest extends AbstractTestClient {
	private WebClient workRecordWC = null;
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
		workRecordWC = WorkRecordsTest.createWorkRecordsWebClient();
		wttWC = CompanyTest.createWttWebClient();
		resourceWC = ResourcesTest.createResourcesWebClient();
		addressbookWC = AddressbookTest.createAddressbookWebClient();

		addressbook = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName());
		company = CompanyTest.createCompany(wttWC, addressbookWC, addressbook, this.getClass().getName(), "MY_DESC");
		project = ProjectTest.createProject(wttWC, company.getId(), this.getClass().getName(), "MY_DESC");
		contact = ContactTest.createContact(addressbookWC, addressbook.getId(), "FNAME", "LNAME");
		resource = ResourcesTest.createResource(resourceWC, addressbookWC, 
				this.getClass().getName(), "FNAME", "LNAME", addressbook.getId(), contact.getId());
	}

	@After
	public void cleanupTest() {
		AddressbookTest.cleanup(addressbookWC, addressbook.getId(), this.getClass().getName());
		ResourcesTest.cleanup(resourceWC, resource.getId(), this.getClass().getName());
		CompanyTest.cleanup(wttWC, company.getId(), this.getClass().getName());
		workRecordWC.close();
	}

	@Test
	public void testWorkRecordBatchedList() {
		ArrayList<WorkRecordModel> _localList = new ArrayList<WorkRecordModel>();		
		Response _response = null;
		System.out.println("***** testWorkRecordListBatchDefSizeStatic:");
		workRecordWC.replacePath("/");
		// we want to allocate more than double the amount of default list size objects
		int _batchSize = GenericService.DEF_SIZE;
		int _increment = 5;
		int _limit2 = 2 * _batchSize + _increment;		// if DEF_SIZE == 25 -> _limit2 = 55
		WorkRecordModel _res = null;
		Date _d = new Date();
		for (int i = 0; i < _limit2; i++) {
			// create(new()) -> _localList
			_res = WorkRecordsTest.createWorkRecord(company, project, resource,
					_d, i, 10 * i, true, "testWorkRecordBatchedList" + i);
			_res.setComment(String.format("%2d", i));
			_response = workRecordWC.post(_res);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(WorkRecordModel.class));
			System.out.println("posted WorkRecordModel " + _res.getComment());
		}
		System.out.println("****** locallist:");
		for (WorkRecordModel _rm : _localList) {
			System.out.println(_rm.getComment());
		}

		// get first batch
		// list(position=0, size=25) -> elements 0 .. 24
		workRecordWC.resetQuery();
		_response = workRecordWC.replacePath("/").get();
		List<WorkRecordModel> _remoteList1 = new ArrayList<WorkRecordModel>(workRecordWC.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 1st Batch:");
		for (WorkRecordModel _rm : _remoteList1) {
			System.out.println(_rm.getComment());
		}
		assertEquals("size of lists should be the same", _batchSize, _remoteList1.size());
		
		// get second batch
		// list(position=25, size=25) -> elements 25 .. 49
		workRecordWC.resetQuery();
		_response = workRecordWC.replacePath("/").query("position", 25).get();
		List<WorkRecordModel> _remoteList2 = new ArrayList<WorkRecordModel>(workRecordWC.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("size of lists should be the same", _batchSize, _remoteList2.size());
		System.out.println("****** 2nd Batch:");
		for (WorkRecordModel _rm : _remoteList2) {
			System.out.println(_rm.getComment());
		}
		
		// get rest 
		// list(position=50, size=25) ->   elements 50 .. 54
		workRecordWC.resetQuery();
		_response = workRecordWC.replacePath("/").query("position", 50).query("size", Integer.toString(_increment)).get();
		List<WorkRecordModel> _remoteList3 = new ArrayList<WorkRecordModel>(workRecordWC.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 3rd Batch:");
		for (WorkRecordModel _rm : _remoteList3) {
			System.out.println(_rm.getComment());
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
			workRecordWC.resetQuery();
			_response = workRecordWC.replacePath("/").query("position", _position).get();
			_remoteList = new ArrayList<WorkRecordModel>(workRecordWC.getCollection(WorkRecordModel.class));
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
		workRecordWC.resetQuery();
		// get next 5 elements from position 5
		_response = workRecordWC.replacePath("/").query("position", 5).query("size", 5).get();
		_remoteList = new ArrayList<WorkRecordModel>(workRecordWC.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// get last 4 elements 
		workRecordWC.resetQuery();
		_response = workRecordWC.replacePath("/").query("position", _limit2-4).query("size", 4).get();
		_remoteList = new ArrayList<WorkRecordModel>(workRecordWC.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 4, _remoteList.size());
		
		// read over end of list
		workRecordWC.resetQuery();
		_response = workRecordWC.replacePath("/").query("position", _limit2-5).query("size", 5).get();
		_remoteList = new ArrayList<WorkRecordModel>(workRecordWC.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// removing all test objects
		for (WorkRecordModel _c : _localList) {
			_response = workRecordWC.replacePath(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}		
	}
}