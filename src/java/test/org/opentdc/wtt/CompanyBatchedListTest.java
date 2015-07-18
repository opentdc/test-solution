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
import org.opentdc.wtt.WttService;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.OrgTest;

public class CompanyBatchedListTest extends AbstractTestClient {
	private WebClient wttWC = null;
	private static AddressbookModel adb = null;
	private static OrgModel org = null;
	private WebClient addressbookWC = null;
	
	@Before
	public void initializeTests() {
		wttWC = initializeTest(ServiceUtil.WTT_API_URL, WttService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		
		adb = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName(), Status.OK);
		org = OrgTest.createOrg(addressbookWC, adb.getId(), this.getClass().getName(), OrgType.CLUB);
	}

	@After
	public void cleanupTest() {
		AddressbookTest.cleanup(addressbookWC, adb.getId(), this.getClass().getName());
		wttWC.close();
	}

	@Test
	public void testCompanyBatchedList() {
		ArrayList<CompanyModel> _localList = new ArrayList<CompanyModel>();		
		Response _response = null;
		System.out.println("***** testCompanyBatchedList:");
		wttWC.replacePath("/");
		// we want to allocate more than double the amount of default list size objects
		int _batchSize = GenericService.DEF_SIZE;
		int _increment = 5;
		int _limit2 = 2 * _batchSize + _increment;		// if DEF_SIZE == 25 -> _limit2 = 55
		CompanyModel _cm = null;
		for (int i = 0; i < _limit2; i++) {
			// create(new()) -> _localList
			_cm = new CompanyModel();
			_cm.setTitle(String.format("%2d", i));
			_cm.setOrgId(org.getId());
			_response = wttWC.post(_cm);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(CompanyModel.class));
			System.out.println("posted CompanyModel " + _cm.getTitle());
		}
		System.out.println("****** locallist:");
		for (CompanyModel _cm1 : _localList) {
			System.out.println(_cm1.getTitle());
		}

		// get first batch
		// list(position=0, size=25) -> elements 0 .. 24
		wttWC.resetQuery();
		_response = wttWC.replacePath("/").query("size", Integer.toString(_batchSize)).get();
		List<CompanyModel> _remoteList1 = new ArrayList<CompanyModel>(wttWC.getCollection(CompanyModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 1st Batch:");
		for (CompanyModel _cm2 : _remoteList1) {
			System.out.println(_cm2.getTitle());
		}
		assertEquals("size of lists should be the same", _batchSize, _remoteList1.size());
		
		// get second batch
		// list(position=25, size=25) -> elements 25 .. 49
		wttWC.resetQuery();
		_response = wttWC.replacePath("/").query("position", 25).query("size", Integer.toString(_batchSize)).get();
		List<CompanyModel> _remoteList2 = new ArrayList<CompanyModel>(wttWC.getCollection(CompanyModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("size of lists should be the same", _batchSize, _remoteList2.size());
		System.out.println("****** 2nd Batch:");
		for (CompanyModel _cm3 : _remoteList2) {
			System.out.println(_cm3.getTitle());
		}
		
		// get rest 
		// list(position=50, size=25) ->   elements 50 .. 54
		wttWC.resetQuery();
		_response = wttWC.replacePath("/").query("position", 50).query("size", Integer.toString(_increment)).get();
		List<CompanyModel> _remoteList3 = new ArrayList<CompanyModel>(wttWC.getCollection(CompanyModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 3rd Batch:");
		for (CompanyModel _cm4 : _remoteList3) {
			System.out.println(_cm4.getTitle());
		}
		assertEquals("size of lists should be the same", _increment, _remoteList3.size());
		
		// testing the batches
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
		List<CompanyModel> _remoteList = null;
		System.out.println("***** testCompanyListIterate:");
		while(true) {
			_numberOfBatches++;
			wttWC.resetQuery();
			_response = wttWC.replacePath("/").query("position", _position).get();
			_remoteList = new ArrayList<CompanyModel>(wttWC.getCollection(CompanyModel.class));
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
		wttWC.resetQuery();
		// get next 5 elements from position 5
		_response = wttWC.replacePath("/").query("position", 5).query("size", 5).get();
		_remoteList = new ArrayList<CompanyModel>(wttWC.getCollection(CompanyModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// get last 4 elements 
		wttWC.resetQuery();
		_response = wttWC.replacePath("/").query("position", _limit2-4).query("size", 4).get();
		_remoteList = new ArrayList<CompanyModel>(wttWC.getCollection(CompanyModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 4, _remoteList.size());
		
		// removing all test objects
		for (CompanyModel _cm5 : _localList) {
			_response = wttWC.replacePath(_cm5.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}		
	}
}