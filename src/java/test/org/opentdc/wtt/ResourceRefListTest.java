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
package test.org.opentdc.wtt;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.ResourceRefModel;
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

public class ResourceRefListTest extends AbstractTestClient {
	private WebClient wc = null;
	private WebClient addressbookWC = null;
	private CompanyModel company = null;
	private ProjectModel parentProject = null;
	private AddressbookModel addressbook = null;
	private WebClient resourceWC = null;
	private ResourceModel resource = null;
	private ContactModel contact = null;

	@Before
	public void initializeTest() {
		wc = createWebClient(ServiceUtil.WTT_API_URL, WttService.class);
		resourceWC = createWebClient(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);

		addressbook = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName(), Status.OK);
		company = CompanyTest.create(wc, addressbookWC, addressbook, this.getClass().getName(), "MY_DESC");
		parentProject = ProjectTest.create(wc, company.getId(), this.getClass().getName(), "MY_DESC");
		contact = ContactTest.create(addressbookWC, addressbook.getId(), "FNAME", "LNAME");
		resource = ResourceTest.create(resourceWC, addressbook, contact, this.getClass().getName(), Status.OK);
	}

	@After
	public void cleanupTest() {
		AddressbookTest.delete(addressbookWC, addressbook.getId(), Status.NO_CONTENT);
		System.out.println("deleted 1 addressbook");
		addressbookWC.close();
		ResourceTest.cleanup(resourceWC, resource.getId(), this.getClass().getName());
		CompanyTest.cleanup(wc, company.getId(), this.getClass().getName());
	}

	@Test
	public void testResourceRefBatchedList() {
		ArrayList<ResourceRefModel> _localList = new ArrayList<ResourceRefModel>();		
		System.out.println("***** testResourceRefBatchedList:");		
		// we want to allocate more than double the amount of default list size objects
		int _batchSize = GenericService.DEF_SIZE;
		int _increment = 5;
		int _limit2 = 2 * _batchSize + _increment;		// if DEF_SIZE == 25 -> _limit2 = 55
		ResourceRefModel _model1 = null;
		for (int i = 0; i < _limit2; i++) {
			// create(new()) -> _localList
			_model1 = ResourceRefTest.create(
					wc, company.getId(), parentProject.getId(), resource.getId());
			_localList.add(_model1);
			System.out.println("posted ResourceRefModel " + _model1.getResourceId());
		}
		System.out.println("****** locallist:");
		for (ResourceRefModel _model : _localList) {
			System.out.println(_model.getResourceId());
		}

		// get first batch
		// list(position=0, size=25) -> elements 0 .. 24
		wc.resetQuery();
		Response _response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).get();
		List<ResourceRefModel> _remoteList1 = new ArrayList<ResourceRefModel>(wc.getCollection(ResourceRefModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 1st Batch:");
		for (ResourceRefModel _model : _remoteList1) {
			System.out.println(_model.getResourceId());
		}
		assertEquals("size of lists should be the same", _batchSize, _remoteList1.size());
		
		// get second batch
		// list(position=25, size=25) -> elements 25 .. 49
		wc.resetQuery();
		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).query("position", 25).get();
		List<ResourceRefModel> _remoteList2 = new ArrayList<ResourceRefModel>(wc.getCollection(ResourceRefModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("size of lists should be the same", _batchSize, _remoteList2.size());
		System.out.println("****** 2nd Batch:");
		for (ResourceRefModel _model : _remoteList2) {
			System.out.println(_model.getResourceId());
		}
		
		// get rest 
		// list(position=50, size=25) ->   elements 50 .. 54
		wc.resetQuery();
		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).query("position", 50).get();
		List<ResourceRefModel> _remoteList3 = new ArrayList<ResourceRefModel>(wc.getCollection(ResourceRefModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 3rd Batch:");
		for (ResourceRefModel _model : _remoteList3) {
			System.out.println(_model.getResourceId());
		}
		assertEquals("size of lists should be the same", _increment, _remoteList3.size());
		
		// testing the batches
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
		List<ResourceRefModel> _remoteList = null;
		System.out.println("***** testResourceRefListIterate:");
		while(true) {
			_numberOfBatches++;
			wc.resetQuery();
			_response = wc.replacePath("/").path(company.getId()).
					path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
					path(ServiceUtil.RESREF_PATH_EL).query("position", _position).get();
			_remoteList = new ArrayList<ResourceRefModel>(wc.getCollection(ResourceRefModel.class));
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
		wc.resetQuery();
		// get next 5 elements from position 5
		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).query("position", 5).query("size", 5).get();
		_remoteList = new ArrayList<ResourceRefModel>(wc.getCollection(ResourceRefModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// get last 4 elements 
		wc.resetQuery();
		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).query("position", _limit2-4).query("size", 4).get();
		_remoteList = new ArrayList<ResourceRefModel>(wc.getCollection(ResourceRefModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 4, _remoteList.size());
		
		// read over end of list
		wc.resetQuery();
		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).query("position", _limit2-5).query("size", 10).get();
		_remoteList = new ArrayList<ResourceRefModel>(wc.getCollection(ResourceRefModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// removing all test objects
		for (ResourceRefModel _model : _localList) {
			_response = wc.replacePath("/").path(company.getId()).
					path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
					path(ServiceUtil.RESREF_PATH_EL).path(_model.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}		
	}
	
	protected int calculateMembers() {
		return 1;
	}
}