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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
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
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.rates.RatesModel;
import org.opentdc.rates.RatesService;
import org.opentdc.resources.RateRefModel;
import org.opentdc.resources.ResourceModel;
import org.opentdc.resources.ResourcesService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.ContactTest;
import test.org.opentdc.rates.RatesTest;

public class RateRefTest extends AbstractTestClient {
	public static final String PATH_EL_RATEREF = "rateref";
	private WebClient resourceWC = null;
	private WebClient addressbookWC = null;
	private WebClient rateWC = null;
	
	private AddressbookModel adb = null;
	private ContactModel contact = null;
	private ResourceModel resource = null;
	private RatesModel rate = null;

	@Before
	public void initializeTests() {
		resourceWC = initializeTest(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		rateWC = createWebClient(ServiceUtil.RATES_API_URL, RatesService.class);
		
		adb = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName());
		contact = ContactTest.createContact(addressbookWC, adb.getId(), "FNAME", "LNAME");
		resource = ResourcesTest.createResource(resourceWC, adb, contact, "RateRefTest", Status.OK);
		rate = RatesTest.createRate(rateWC, Status.OK);
	}

	@After
	public void cleanupTest() {
		AddressbookTest.cleanup(addressbookWC, adb.getId(), this.getClass().getName());
		RatesTest.deleteRate(rateWC, rate.getId(), Status.NO_CONTENT);
		rateWC.close();
		ResourcesTest.cleanup(resourceWC, resource.getId(), this.getClass().getName());
	}
	
	/********************************** localizedText attributes tests *********************************/			
	@Test
	public void testEmptyConstructor() {
		RateRefModel _model = new RateRefModel();
		assertNull("id should not be set by empty constructor", _model.getId());
		assertNull("rateId should not be set by empty constructor", _model.getRateId());
		assertNull("rateTitle should not be set by empty constructor", _model.getRateTitle());
	}
	
	@Test
	public void testConstructor() {		
		RateRefModel _model = new RateRefModel("testConstructor1");
		assertNull("id should not be set by constructor", _model.getId());
		assertEquals("rateId should be set by constructor", "testConstructor1", _model.getRateId());
		assertNull("rateTitle should be set by constructor", _model.getRateTitle());
	}
	
	@Test
	public void testId() {
		RateRefModel _model = new RateRefModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testIdChange");
		assertEquals("id should have changed", "testIdChange", _model.getId());
	}

	@Test
	public void testRateId() {
		RateRefModel _model = new RateRefModel();
		assertNull("rateId should not be set by empty constructor", _model.getRateId());
		_model.setRateId("testRateId");
		assertEquals("rateId should have changed", "testRateId", _model.getRateId());
	}
	
	@Test
	public void testRateTitle() {
		RateRefModel _model = new RateRefModel();
		assertNull("rateTitle should not be set by empty constructor", _model.getRateTitle());
		_model.setRateTitle("testRateTitle");
		assertEquals("rateTitle should have changed:", "testRateTitle", _model.getRateTitle());
	}	
	
	@Test
	public void testCreatedBy() {
		RateRefModel _model = new RateRefModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		RateRefModel _model = new RateRefModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		RateRefModel _model1 = new RateRefModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("rateId should not be set by empty constructor", _model1.getRateId());
		assertNull("rateTitle should not be set by empty constructor", _model1.getRateTitle());
	
		postRateRef(_model1, Status.BAD_REQUEST);
		_model1.setRateId(rate.getId());
		RateRefModel _model2 = postRateRef(_model1, Status.OK);

		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the rateId of the local object", rate.getId(), _model1.getRateId());
		assertNull("create() should not change the rateTitle of the local object", _model1.getRateTitle());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("create() should not change the rateId", rate.getId(), _model2.getRateId());
		assertEquals("create() should derive the rateTitle", rate.getTitle(), _model2.getRateTitle());
		
		RateRefModel _model3 = getRateRef(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("rateId of returned object should be unchanged after remote create", _model2.getRateId(), _model3.getRateId());
		assertEquals("rateTitle of returned object should be unchanged after remote create", _model2.getRateTitle(), _model3.getRateTitle());

		deleteRateRef(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDerivedTitle() {
		RateRefModel _model1 = new RateRefModel();
		_model1.setRateId(rate.getId());
		assertNull("rateTitle should not be set by empty constructor", _model1.getRateTitle());
		RateRefModel _model2 = postRateRef(_model1, Status.OK);
		assertEquals("rateTitle should be derived", rate.getTitle(), _model2.getRateTitle());
		deleteRateRef(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		RateRefModel _model1 = new RateRefModel(rate.getId());
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("rateId should be set by constructor", rate.getId(), _model1.getRateId());
		assertNull("rateTitle should not be set by constructor", _model1.getRateTitle());
		
		RateRefModel _model2 = postRateRef(_model1, Status.OK);
		assertNull("id should still be null after remote create", _model1.getId());
		assertEquals("create() should not change the rateId", rate.getId(), _model1.getRateId());
		assertNull("create() should not change the rateTitle", _model1.getRateTitle());
		
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("create() should not change the rateId", rate.getId(), _model2.getRateId());
		assertEquals("create() should derive the rateTitle", rate.getTitle(), _model2.getRateTitle());

		RateRefModel _model3 = getRateRef(_model2.getId(), Status.OK);
		assertEquals("read() should not change the id", _model2.getId(), _model3.getId());
		assertEquals("read() should not change the rateId", _model2.getRateId(), _model3.getRateId());
		assertEquals("read() should not change the rateTitle", _model2.getRateTitle(), _model3.getRateTitle());
		
		deleteRateRef(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateWithClientSideId() {
		RateRefModel _model = new RateRefModel(rate.getId());
		_model.setId("LOCAL_ID");
		postRateRef(_model, Status.BAD_REQUEST);
	}
	
	@Test
	public void testCreateWithDuplicateId() {
		RateRefModel _model1 = postRateRef(new RateRefModel(rate.getId()), Status.OK);
		RateRefModel _model2 = new RateRefModel(rate.getId());
		_model2.setId(_model1.getId());		// wrongly create a 2nd LocalizedTextModel object with the same ID
		postRateRef(_model2, Status.CONFLICT);
		deleteRateRef(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testList() {
		ArrayList<RateRefModel> _localList = new ArrayList<RateRefModel>();		
		resourceWC.replacePath("/").path(resource.getId()).path(PATH_EL_RATEREF);
		_localList.add(postRateRef(new RateRefModel(rate.getId()), Status.OK));
		_localList.add(postRateRef(new RateRefModel(rate.getId()), Status.OK));
		_localList.add(postRateRef(new RateRefModel(rate.getId()), Status.OK));
		_localList.add(postRateRef(new RateRefModel(rate.getId()), Status.OK));
		_localList.add(postRateRef(new RateRefModel(rate.getId()), Status.OK));
		_localList.add(postRateRef(new RateRefModel(rate.getId()), Status.OK));
		
		List<RateRefModel> _remoteList = listRateRefs(Status.OK);

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (RateRefModel _model : _remoteList) {
			_remoteListIds.add(_model.getId());
		}
		
		for (RateRefModel _model : _localList) {
			assertTrue("RateRef <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		
		for (RateRefModel _model : _localList) {
			getRateRef(_model.getId(), Status.OK);
		}
		
		for (RateRefModel _model : _localList) {
			deleteRateRef(_model.getId(), Status.NO_CONTENT);
		}
	}

	@Test
	public void testCreate() {	
		RateRefModel _model1 = postRateRef(new RateRefModel(rate.getId()), Status.OK);
		assertNotNull("ID should be set", _model1.getId());
		assertEquals("rateId should be set correctly", rate.getId(), _model1.getRateId());
		assertEquals("rateTitle should be set correctly", rate.getTitle(), _model1.getRateTitle());
		
		RateRefModel _model2 = postRateRef(new RateRefModel(rate.getId()), Status.OK);
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("rateId should be set correctly", rate.getId(), _model2.getRateId());
		assertEquals("rateTitle should be set correctly", rate.getTitle(), _model2.getRateTitle());

		assertThat(_model2.getId(), not(equalTo(_model1.getId())));

		deleteRateRef(_model1.getId(), Status.NO_CONTENT);
		deleteRateRef(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateDouble() {
		RateRefModel _model = postRateRef(new RateRefModel(rate.getId()), Status.OK);
		assertNotNull("ID should be set:", _model.getId());
		postRateRef(_model, Status.CONFLICT);
		deleteRateRef(_model.getId(), Status.NO_CONTENT);
	}
		
	@Test
	public void testRead() {
		ArrayList<RateRefModel> _localList = new ArrayList<RateRefModel>();
		resourceWC.replacePath("/").path(resource.getId()).path(PATH_EL_RATEREF);
		_localList.add(postRateRef(new RateRefModel(rate.getId()), Status.OK));
		_localList.add(postRateRef(new RateRefModel(rate.getId()), Status.OK));
		_localList.add(postRateRef(new RateRefModel(rate.getId()), Status.OK));
		_localList.add(postRateRef(new RateRefModel(rate.getId()), Status.OK));
		_localList.add(postRateRef(new RateRefModel(rate.getId()), Status.OK));
		_localList.add(postRateRef(new RateRefModel(rate.getId()), Status.OK));
	
		// test read on each local element
		for (RateRefModel _model : _localList) {
			getRateRef(_model.getId(), Status.OK);
		}
		List<RateRefModel> _remoteList = listRateRefs(Status.OK);

		for (RateRefModel _model : _remoteList) {
			assertEquals("ID should be unchanged when reading a project", _model.getId(), getRateRef(_model.getId(), Status.OK).getId());						
		}

		for (RateRefModel _model : _localList) {
			deleteRateRef(_model.getId(), Status.NO_CONTENT);
		}
	}
	
	@Test
	public void testMultiRead() {
		RateRefModel _model1 = postRateRef(new RateRefModel(rate.getId()), Status.OK);
		RateRefModel _model2 = getRateRef(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read:", _model1.getId(), _model2.getId());		
		RateRefModel _ltm3 = getRateRef(_model1.getId(), Status.OK);
		
		assertEquals("ID should be the same:", _model2.getId(), _ltm3.getId());
		assertEquals("rateId should be the same:", _model2.getRateId(), _ltm3.getRateId());
		assertEquals("rateTitle should be the same:", _model2.getRateTitle(), _ltm3.getRateTitle());
		
		assertEquals("ID should be the same:", _model2.getId(), _model1.getId());
		assertEquals("rateId should be the same:", _model2.getRateId(), _model1.getRateId());
		assertEquals("rateTitle should be the same:", _model2.getRateTitle(), _model1.getRateTitle());
		
		deleteRateRef(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDelete(
	) {
		RateRefModel _model1 = postRateRef(new RateRefModel(rate.getId()), Status.OK);
		RateRefModel _model2 = getRateRef(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a project (remote):", _model1.getId(), _model2.getId());						
		deleteRateRef(_model1.getId(), Status.NO_CONTENT);
		deleteRateRef(_model1.getId(), Status.NOT_FOUND);
		getRateRef(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		RateRefModel _model = postRateRef(new RateRefModel(rate.getId()), Status.OK);
		getRateRef(_model.getId(), Status.OK);
		deleteRateRef(_model.getId(), Status.NO_CONTENT);
		getRateRef(_model.getId(), Status.NOT_FOUND);
		deleteRateRef(_model.getId(), Status.NOT_FOUND);
		deleteRateRef(_model.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		RateRefModel _model = postRateRef(new RateRefModel(rate.getId()), Status.OK);		
		assertNotNull("create() should set createdAt", _model.getCreatedAt());
		assertNotNull("create() should set createdBy", _model.getCreatedBy());
		deleteRateRef(_model.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	public List<RateRefModel> listRateRefs(
			Status expectedStatus) 
	{
		return listRateRefs(resourceWC, resource.getId(), expectedStatus);
	}

	public static List<RateRefModel> listRateRefs(
			WebClient webClient,
			String resourceId,
			Status expectedStatus) 
	{
		Response _response = webClient.replacePath("/").path(resourceId).path(PATH_EL_RATEREF).get();
		assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return new ArrayList<RateRefModel>(webClient.getCollection(RateRefModel.class));
		} else {
			return null;
		}
	}
	
	public RateRefModel postRateRef(
			RateRefModel rateRefModel,
			Status expectedStatus) 
	{
		return postRateRef(resourceWC, resource, rateRefModel, expectedStatus);
	}

	public static RateRefModel postRateRef(
			WebClient webClient,
			ResourceModel resourceModel,
			RateRefModel rateRefModel,
			Status expectedStatus) 
	{
		Response _response = webClient.replacePath("/").path(resourceModel.getId()).path(PATH_EL_RATEREF).post(rateRefModel);
		assertEquals("post should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(RateRefModel.class);
		} else {
			return null;
		}
	}
	
	public RateRefModel getRateRef(
			String rateRefId,
			Status expectedStatus) 
	{
		return getRateRef(resourceWC, resource, rateRefId, expectedStatus);
	}

	public static RateRefModel getRateRef(
			WebClient webClient,
			ResourceModel resourceModel,
			String rateRefId,
			Status expectedStatus) 
	{
		Response _response = webClient.replacePath("/").path(resourceModel.getId()).path(PATH_EL_RATEREF).path(rateRefId).get();
		assertEquals("get should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(RateRefModel.class);
		} else {
			return null;
		}
	}
	
	public void deleteRateRef(
			String rateRefId,
			Status expectedStatus) 
	{
		deleteRateRef(resourceWC, resource, rateRefId, expectedStatus);
	}

	public static void deleteRateRef(
			WebClient webClient,
			ResourceModel resourceModel,
			String rateRefId,
			Status expectedStatus) 
	{
		Response _response = webClient.replacePath("/").
				path(resourceModel.getId()).path(PATH_EL_RATEREF).path(rateRefId).delete();
		assertEquals("delete should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
	}
}