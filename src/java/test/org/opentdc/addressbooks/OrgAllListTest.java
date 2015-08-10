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
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/**
 * Testing the listAllOrgs() method.
 * @author Bruno Kaiser
 *
 */
public class OrgAllListTest extends AbstractTestClient {
	private static ArrayList<AddressbookModel> addressbooks = null;
	private static ArrayList<OrgModel> orgs = null;
	private static final int nrAddressbooks = 3;
	private static final int nrOrgs = 10;
	private static int totalOrgs = nrAddressbooks * nrOrgs;
	private static WebClient wc = null;

	/**
	 * Initialize the test with several addressbooks containing each some orgs.
	 */
	@BeforeClass
	public static void initializeTest() {
		wc = initializeTest(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);		
		System.out.println("***** OrgAllListTest:");
		addressbooks = new ArrayList<AddressbookModel>();
		orgs = new ArrayList<OrgModel>();
		AddressbookModel _abm = null;
		for (int i = 0; i < nrAddressbooks; i++) {
			_abm = createAddressbook("OrgAllListTest" + i);
			addressbooks.add(_abm);
			for (int j = 0; j < nrOrgs; j++) {
				orgs.add(createOrg(_abm.getId(), "addressbook" + i + "_org" + j));
			}
		}
		System.out.println("OrgAllListTest - Parameters" + 
				":\n\tnrAddressbooks:\t\t" + nrAddressbooks +
				"\n\tnrOrgs:\t\t\t" + nrOrgs + 
				"\n\ttotalOrgs:\t\t" + totalOrgs);		

		printCounters("initializeTest");
	}
	
	private static void printCounters(String title) {
		wc.resetQuery();
		wc.replacePath("/").get();
		List<AddressbookModel> _addressbooks = new ArrayList<AddressbookModel>(wc.getCollection(AddressbookModel.class));
		
		wc.replacePath("/").path("allOrgs").query("size",  1000).get();
		List<OrgModel> _orgs = new ArrayList<OrgModel>(wc.getCollection(OrgModel.class));
		System.out.println(title + 
				":\n\tnrAddressbooks:\t\t" + _addressbooks.size() +
				"\n\tnrOrgs:\t\t\t" + _orgs.size());		
	}
	
	/**
	 * Cleanup after the test execution, ie. delete the addressbooks.
	 */
	@AfterClass
	public static void cleanupTest() {
		for (AddressbookModel _model : addressbooks) {
			deleteAddressbook(_model.getId());
		}
		printCounters("cleanupTest");
		wc.close();
	}
	
	/**
	 * Test whether listAllOrgs() returns the right amount of orgs from all addressbooks.
	 */
	@Test
	public void testOrgsAllList() {
		printCounters("testOrgsAllList");
		Response _response = wc.replacePath("/").path("allOrgs").query("size", totalOrgs + 1).get();
		List<OrgModel> _remoteList = new ArrayList<OrgModel>(wc.getCollection(OrgModel.class));
		assertEquals("listAllOrgs() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertTrue("should have returned all objects", _remoteList.size() >= totalOrgs);
		System.out.println("list() returns " + _remoteList.size() + " elements (_remoteList).");
	}
	
	/**
	 * Same as testOrgsAllList but with batchwise listing.
	 */
	@Test
	public void testOrgAllListBatched() {
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
		List<OrgModel> _remoteList = null;
		printCounters("testOrgAllListBatched");
		Response _response = null;
		while(true) {
			_numberOfBatches++;
			wc.resetQuery();
			_response = wc.replacePath("/").path("allOrgs").query("position", _position).get();
			_remoteList = new ArrayList<OrgModel>(wc.getCollection(OrgModel.class));
			assertEquals("listAllOrgs() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
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
		
	/**
	 * Tests listAllOrgs() with some explicit, critical queries.
	 */
	@Test
	public void testOrgAllListExplicitQueries() {
		// testing some explicit positions and sizes
		wc.resetQuery();
		// get next _nrElements elements from position _nrElements
		int _nrElements = 7;
		Response _response = wc.replacePath("/").path("allOrgs").query("position", _nrElements).query("size", _nrElements).get();
		List<OrgModel> _remoteList = new ArrayList<OrgModel>(wc.getCollection(OrgModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", _nrElements, _remoteList.size());
		
		// get last _nrElements elements 
		wc.resetQuery();
		_response = wc.replacePath("/").path("allOrgs").query("position", totalOrgs - _nrElements).query("size", _nrElements).get();
		_remoteList = new ArrayList<OrgModel>(wc.getCollection(OrgModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", _nrElements, _remoteList.size());
		
		// read over end of list
		wc.resetQuery();
		_response = wc.replacePath("/").path("allOrgs").query("position", totalOrgs - _nrElements).query("size", 2 * _nrElements).get();
		_remoteList = new ArrayList<OrgModel>(wc.getCollection(OrgModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertTrue("list() should return correct number of elements", _remoteList.size() >= _nrElements);		
	}
	
	/**
	 * Create a new addressbook
	 * @param name the name of the addressbook
	 * @return the newly created addressbook
	 */
	private static AddressbookModel createAddressbook(String name) {
		Response _response = wc.replacePath("/").post(new AddressbookModel(name));
		AddressbookModel _adb = _response.readEntity(AddressbookModel.class);
		System.out.println("posted AddressbookModel " + name + ": " + _adb.getId());
		return _adb;
	}
	
	/**
	 * Delete an addressbook.
	 * @param id the unique id of the addressbook
	 */
	private static void deleteAddressbook(String id) {
		wc.replacePath("/").path(id).delete();
		System.out.println("deleted AddressbookModel " + id);
	}
	
	/**
	 * Create a new Organization
	 * @param aid the id of the addressbook
	 * @param name the name of the organization
	 * @return the newly created organization
	 */
	private static OrgModel createOrg(String aid, String name) {
		OrgModel _om = new OrgModel();
		_om.setName(name);
		_om.setOrgType(OrgType.getDefaultOrgType());
		Response _response = wc.replacePath("/").path(aid).
				path(ServiceUtil.ORG_PATH_EL).post(_om);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("posted OrgModel " + _om.getName() + " in addressbook " + aid);
		return _om;
	}
	
	protected int calculateMembers() {
		return nrAddressbooks * nrOrgs;
	}
}