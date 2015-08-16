/**
a * The MIT License (MIT)
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.service.ServiceUtil;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.WttService;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.OrgTest;

/**
 * Testing Companies in time tracking service WTT.
 * @author Bruno Kaiser
 *
 */
public class CompanyTest extends AbstractTestClient {
	private static final String CN = "CompanyTest";
	private WebClient wc = null;
	private static AddressbookModel adb = null;
	private static OrgModel org = null;
	private WebClient addressbookWC = null;
	
	@Before
	public void initializeTests() {
		wc = initializeTest(ServiceUtil.WTT_API_URL, WttService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		
		adb = AddressbookTest.post(addressbookWC, 
				new AddressbookModel(CN), Status.OK);
		org = OrgTest.post(addressbookWC, adb.getId(), 
				new OrgModel(CN, OrgType.CLUB), Status.OK);
	}

	@After
	public void cleanupTest() {
		AddressbookTest.delete(addressbookWC, adb.getId(), Status.NO_CONTENT);
		System.out.println("deleted 1 addressbook");
		addressbookWC.close();
		wc.close();
	}

	/********************************** company attributes tests *********************************/	
	@Test
	public void testEmptyConstructor() {
		CompanyModel _model = new CompanyModel();
		assertNull("id should not be set by empty constructor", _model.getId());
		assertNull("title should not be set by empty constructor", _model.getTitle());
		assertNull("orgId should not be set by empty constructor", _model.getOrgId());
		assertNull("description should not be set by empty constructor", _model.getDescription());
	}
	
	@Test
	public void testConstructor() {		
		CompanyModel _model = new CompanyModel("testConstructor", "MY_DESC", org.getId());
		assertNull("id should not be set by constructor", _model.getId());
		assertEquals("title should be set by constructor", "testConstructor", _model.getTitle());
		assertEquals("description should set by constructor", "MY_DESC", _model.getDescription());
		assertEquals("orgId should be set by constructor", org.getId(), _model.getOrgId());
	}
	
	@Test
	public void testId() {
		CompanyModel _model = new CompanyModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed:", "testId", _model.getId());
	}

	@Test
	public void testTitle() {
		CompanyModel _model = new CompanyModel();
		assertNull("title should not be set by empty constructor", _model.getTitle());
		_model.setTitle("testTitle");
		assertEquals("title should have changed:", "testTitle", _model.getTitle());
	}
	
	@Test
	public void testOrgId() {
		CompanyModel _model = new CompanyModel();
		assertNull("orgId should not be set by empty constructor", _model.getOrgId());
		_model.setOrgId("testOrgId");
		assertEquals("orgId should have changed", "testOrgId", _model.getOrgId());
	}
	
	@Test
	public void testDescription() {
		CompanyModel _model = new CompanyModel();
		assertNull("description should not be set by empty constructor", _model.getDescription());
		_model.setDescription("testDescription");
		assertEquals("description should have changed:", "testDescription", _model.getDescription());
	}
	
	@Test
	public void testCreatedBy() {
		CompanyModel _model = new CompanyModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		CompanyModel _model = new CompanyModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	@Test
	public void testModifiedBy() {
		CompanyModel _model = new CompanyModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	@Test
	public void testModifiedAt() {
		CompanyModel _model = new CompanyModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		CompanyModel _model1 = new CompanyModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("title should not be set by empty constructor", _model1.getTitle());
		assertNull("description should not be set by empty constructor", _model1.getDescription());
		assertNull("orgId should not be set by empty constructor", _model1.getOrgId());

		post(_model1, Status.BAD_REQUEST);
		_model1.setTitle("testCreateReadDeleteWithEmptyConstructor");
		post(_model1, Status.BAD_REQUEST);
		_model1.setOrgId(org.getId());
		CompanyModel _model2 = post(_model1, Status.OK);
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the title of the local object", "testCreateReadDeleteWithEmptyConstructor", _model1.getTitle());
		assertNull("create() should not change the description of the local object", _model1.getDescription());
		assertEquals("create() should not change the orgId of the local object", org.getId(), _model1.getOrgId());
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("create() should not change the title of the local object", "testCreateReadDeleteWithEmptyConstructor", _model2.getTitle());
		assertNull("description of returned object should still be null after remote create", _model2.getDescription());
		CompanyModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("title of returned object should be unchanged after remote create", "testCreateReadDeleteWithEmptyConstructor", _model3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _model2.getDescription(), _model3.getDescription());
		assertEquals("orgId of returned object should be unchanged after remote create", _model2.getOrgId(), _model3.getOrgId());
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		CompanyModel _model1 = new CompanyModel("testCreateReadDelete", "MY_DESC", org.getId());
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("title should be set by constructor", "testCreateReadDelete", _model1.getTitle());
		assertEquals("description should be set by constructor", "MY_DESC", _model1.getDescription());
		assertEquals("orgId should be set by constructor", org.getId(), _model1.getOrgId());
		CompanyModel _model2 = post(_model1, Status.OK);
		assertNull("id should be still null after remote create", _model1.getId());
		assertEquals("title should be unchanged after remote create", "testCreateReadDelete", _model1.getTitle());
		assertEquals("description should be unchanged after remote create", "MY_DESC", _model1.getDescription());
		assertEquals("remote create should not change the orgId", org.getId(), _model1.getOrgId());
		
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("title of returned object should be unchanged after remote create", "testCreateReadDelete", _model2.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", "MY_DESC", _model2.getDescription());
		assertEquals("remote create should not change the orgId", org.getId(), _model2.getOrgId());
		CompanyModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _model2.getTitle(), _model3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _model2.getDescription(), _model3.getDescription());
		assertEquals("remote create should not change the orgId", org.getId(), _model3.getOrgId());
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testClientSideId() 
	{
		CompanyModel _model = new CompanyModel();
		_model.setId("testClientSideId");
		assertEquals("id should have changed", "testClientSideId", _model.getId());
		post(_model, Status.BAD_REQUEST);
	}
	
	@Test
	public void testDuplicateId() 
	{
		CompanyModel _model1 = post(new CompanyModel("testDuplicateId", "MY_DESC", org.getId()), Status.OK);
		CompanyModel _model2 = new CompanyModel();
		_model2.setId(_model1.getId());		// wrongly create a 2nd CompanyModel object with the same ID
		post(_model2, Status.CONFLICT);
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testList() 
	{		
		List<CompanyModel> _listBefore = list(null, Status.OK);
		ArrayList<CompanyModel> _localList = new ArrayList<CompanyModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(new CompanyModel("testList" + i, "MY_DESC", org.getId()), Status.OK));
		}
		assertEquals("correct number of companies should be created", LIMIT, _localList.size());
		List<CompanyModel> _listAfter = list(null, Status.OK);		
		assertEquals("list() should return the correct number of companies", _listBefore.size() + LIMIT, _listAfter.size());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (CompanyModel _model : _listAfter) {
			_remoteListIds.add(_model.getId());
		}
		for (CompanyModel _model : _localList) {
			assertTrue("company <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		for (CompanyModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		for (CompanyModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}		
	}

	@Test
	public void testCreate() {
		CompanyModel _model1 = post(new CompanyModel("testCreate1", "MY_DESC1", org.getId()), Status.OK);
		CompanyModel _model2 = post(new CompanyModel("testCreate2", "MY_DESC2", org.getId()), Status.OK);
		
		assertNotNull("ID should be set", _model1.getId());
		assertNotNull("ID should be set", _model2.getId());
		assertThat(_model1.getId(), not(equalTo(_model2.getId())));
		assertEquals("title1 should be set correctly", "testCreate1", _model1.getTitle());
		assertEquals("description1 should be set correctly", "MY_DESC1", _model1.getDescription());
		assertEquals("orgId1 should be set correctly", org.getId(), _model1.getOrgId());
		assertEquals("title2 should be set correctly", "testCreate2", _model2.getTitle());
		assertEquals("description2 should be set correctly", "MY_DESC2", _model2.getDescription());
		assertEquals("orgId2 should be set correctly", org.getId(), _model2.getOrgId());
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDoubleCreate() 
	{
		CompanyModel _model = post(new CompanyModel("testDoubleCreate", "MY_DESC", org.getId()), Status.OK);
		assertNotNull("ID should be set:", _model.getId());		
		post(_model, Status.CONFLICT);
		delete(_model.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testRead() 
	{
		ArrayList<CompanyModel> _localList = new ArrayList<CompanyModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(new CompanyModel("testRead" + i, "MY_DESC", org.getId()), Status.OK));
		}
		for (CompanyModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		List<CompanyModel> _remoteList = list(null, Status.OK);
		for (CompanyModel _model : _remoteList) {
			assertEquals("ID should be unchanged when reading an project", _model.getId(), get(_model.getId(), Status.OK).getId());						
		}
		for (CompanyModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}	

	@Test
	public void testMultiRead() {
		CompanyModel _model1 = post(new CompanyModel("testMultiRead", "MY_DESC", org.getId()), Status.OK);
		CompanyModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read", _model1.getId(), _model2.getId());		
		CompanyModel _model3 = get(_model1.getId(), Status.OK);		
		assertEquals("ID should be the same", _model2.getId(), _model3.getId());
		assertEquals("title should be the same", _model2.getTitle(), _model3.getTitle());
		assertEquals("ID should be the same", _model2.getId(), _model1.getId());
		assertEquals("title should be the same", _model2.getTitle(), _model1.getTitle());
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testUpdate() {
		CompanyModel _model1 = post(new CompanyModel("testUpdate", "MY_DESC", org.getId()), Status.OK);
		_model1.setTitle("testUpdate1");
		CompanyModel _model2 = put(_model1, Status.OK);
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		assertEquals("title should have changed", "testUpdate1", _model2.getTitle());
		_model1.setTitle("testUpdate2");
		CompanyModel _model3 = put(_model1, Status.OK);
		assertNotNull("ID should be set", _model3.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model3.getId());	
		assertEquals("title should have changed", "testUpdate2", _model3.getTitle());
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDelete() {
		CompanyModel _model1 = post(new CompanyModel("testDelete", "MY_DESC", org.getId()), Status.OK);
		CompanyModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading an project", _model1.getId(), _model2.getId());						
		delete(_model1.getId(), Status.NO_CONTENT);
		get(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		CompanyModel _model1 = post(new CompanyModel("testDoubleDelete", "MY_DESC", org.getId()), Status.OK);
		get(_model1.getId(), Status.OK);
		delete(_model1.getId(), Status.NO_CONTENT);
		get(_model1.getId(), Status.NOT_FOUND);
		delete(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		CompanyModel _model1 = post(new CompanyModel("testModifications", "MY_DESC", org.getId()), Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		_model1.setTitle("testModifications2");
		CompanyModel _model2 = put(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		assertThat(_model2.getModifiedAt(), not(equalTo(_model2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_model2.getModifiedBy(), not(equalTo(_model2.getCreatedBy())));
		_model1.setModifiedBy("MYSELF");
		_model1.setModifiedAt(new Date(1000));
		CompanyModel _model3 = put(_model1, Status.OK);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_model1.getModifiedBy(), not(equalTo(_model3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_model1.getModifiedAt(), not(equalTo(_model3.getModifiedAt())));
		
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************** helper methods *********************************/	
	/**
	 * Retrieve a list of CompanyModel from WttService by executing a HTTP GET request.
	 * This uses neither position nor size queries.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of CompanyModel objects in JSON format
	 */
	public List<CompanyModel> list(
			String query, 
			Status expectedStatus) {
		return list(wc, query, -1, Integer.MAX_VALUE, expectedStatus);
	}
	
	/**
	 * Retrieve a list of CompanyModel from WttService by executing a HTTP GET request.
	 * @param webClient the WebClient for the WttService
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of CompanyModel objects in JSON format
	 */
	public static List<CompanyModel> list(
			WebClient webClient, 
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		webClient.resetQuery();
		webClient.replacePath("/");
		Response _response = executeListQuery(webClient, query, position, size);
		List<CompanyModel> _list = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_list = new ArrayList<CompanyModel>(webClient.getCollection(CompanyModel.class));
			System.out.println("list(webClient, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ") ->" + _list.size());
		}
		return _list;
	}
	
	/**
	 * Create a new CompanyModel on the server by executing a HTTP POST request.
	 * @param model the data to post
	 * @param expectedStatus the expected HTTP status to test on; if this is null, it will not be tested
	 * @return the created data object
	 */
	private CompanyModel post(
			CompanyModel model,
			Status expectedStatus) {
		return post(wc, model, expectedStatus);
	}

	/**
	 * Create a new CompanyModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the service
	 * @param model the data to create on the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created data
	 */
	public static CompanyModel post(
			WebClient webClient,
			CompanyModel model,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(CompanyModel.class);
		} else {
			return null;
		}
	}
		
	/**
	 * Read the CompanyModel with id from WttService by executing a HTTP GET method.
	 * @param id the id of the CompanyModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved CompanyModel object in JSON format
	 */
	private CompanyModel get(
			String id, 
			Status expectedStatus) {
		return get(wc, id, expectedStatus);
	}
	
	/**
	 * Read the CompanyModel with id from WttService by executing a HTTP GET method.
	 * @param webClient the web client representing the WttService
	 * @param id the id of the CompanyModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved CompanyModel object in JSON format
	 */
	public static CompanyModel get(
			WebClient webClient,
			String id,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(id).get();
		if (expectedStatus != null) {
			assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(CompanyModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Update a CompanyModel on the WttService by executing a HTTP PUT method.
	 * @param model the new CompanyModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated CompanyModel object in JSON format
	 */
	public CompanyModel put(
			CompanyModel model, 
			Status expectedStatus) {
		return put(wc, model, expectedStatus);
	}
	
	/**
	 * Update a CompanyModel on the WttService by executing a HTTP PUT method.
	 * @param webClient the web client representing the WttService
	 * @param model the new CompanyModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated CompanyModel object in JSON format
	 */
	public static CompanyModel put(
			WebClient webClient,
			CompanyModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(model.getId()).put(model);
		if (expectedStatus != null) {
			assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(CompanyModel.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Delete the CompanyModel with id on the WttService by executing a HTTP DELETE method.
	 * @param id the id of the CompanyModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public void delete(String id, Status expectedStatus) {
		delete(wc, id, expectedStatus);
	}
	
	/**
	 * Delete the CompanyModel with id on the WttService by executing a HTTP DELETE method.
	 * @param webClient the WebClient for the WttService
	 * @param id the id of the CompanyModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void delete(
			WebClient webClient,
			String id,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(id).delete();	
		if (expectedStatus != null) {
			assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
	}
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	protected int calculateMembers() {
		return 1;
	}
}
