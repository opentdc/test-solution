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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressModel;
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.service.ServiceUtil;

/**
 * Testing addresses of organizations.
 * @author Bruno Kaiser
 *
 */
public class OrgAddressTest extends AbstractAddressTestClient {
	public static final String CN = "OrgAddressTest";
	private static OrgModel org = null;
		
	@Before
	public void initializeTests() {
		super.initializeTests(CN);
		org = OrgTest.post(wc, adb.getId(), new OrgModel(CN, OrgType.getDefaultOrgType()), Status.OK);
	}
	
	@After
	public void cleanupTest() {
		super.cleanupTest();
	}
	
	/********************************** address attributes tests *********************************/
	@Test
	public void testEmptyConstructor() {
		super.testEmptyConstructor();
	}
			
	@Test
	public void testId() {
		super.testId();
	}

	@Test
	public void testAddressType() {
		super.testAddressType();
	}
	
	@Test
	public void testAttributeType() {
		super.testAttributeType();
	}

	@Test
	public void testMsgType() {
		super.testMsgType();
	}
	
	@Test
	public void testValue() {
		super.testValue();
	}
	
	@Test
	public void testStreet() {
		super.testStreet();
	}
	
	@Test
	public void testPostalCode() {
		super.testPostalCode();
	}
	
	@Test
	public void testCity() {
		super.testCity();
	}

	@Test
	public void testCountry() {
		super.testCountry();
	}
	
	@Test
	public void testCreatedBy() {
		super.testCreatedBy();
	}
	
	@Test
	public void testCreatedAt() {
		super.testCreatedAt();
	}
		
	@Test
	public void testModifiedBy() {
		super.testModifiedBy();
	}
	
	@Test
	public void testModifiedAt() {
		super.testModifiedAt();
	}
	
	/********************************* REST service tests *********************************/	
	// create:  POST p "api/addressbooks/{abid}/org/{oid}/address"
	// read:    GET "api/addressbooks/{abid}/org/{oid}/address/{adrid}"
	// update:  PUT p "api/addressbooks/{abid}/org/{oid}/address/{adrid}"
	// delete:  DELETE "api/addressbooks/{abid}/org/{oid}/address/{ardid}"

	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		super.testCreateReadDeleteWithEmptyConstructor();
	}
	
	@Test
	public void testCreateReadDelete() {
		super.testCreateReadDelete();
	}
	
	@Test
	public void testClientSideId() {
		super.testClientSideId();
	}
	
	@Test
	public void testDuplicateId() {
		super.testDuplicateId();
	}
	
	@Test
	public void testList() {
		super.testList();
	}

	@Test
	public void testCreate() {	
		super.testCreate();
	}

	@Test
	public void testCreateDouble() {		
		super.testCreateDouble();
	}
	
	@Test
	public void testRead() {
		super.testRead();
	}
		
	@Test
	public void testMultiRead() {
		super.testMultiRead();
	}
	
	@Test
	public void testUpdate() {
		super.testUpdate();
	}
	
	@Test
	public void testAddressTypePhone() {
		super.testAddressTypePhone();
	}
		
	@Test
	public void testAddressTypeEmail() {
		super.testAddressTypeEmail();
	}
	
	@Test
	public void testAddressTypeWeb() {
		super.testAddressTypeWeb();
	}

	@Test
	public void testAddressTypeMessaging() {
		super.testAddressTypeMessaging();
	}

	@Test
	public void testAddressTypePostal() {
		super.testAddressTypePostal();
	}
	
	@Test
	public void testDelete() {
		super.testDelete();
	}
	
	@Test
	public void testDoubleDelete() {
		super.testDoubleDelete();
	}
	
	@Test
	public void testModifications() {
		super.testModifications();
	}
	
	/********************************** helper methods *********************************/					
	/* (non-Javadoc)
	 * @see test.org.opentdc.addressbooks.AbstractAddressTest#list(java.lang.String, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected List<AddressModel> list(
			String query, 
			Status expectedStatus) {
		return list(wc, adb.getId(), org.getId(), query, 0, Integer.MAX_VALUE, expectedStatus);
	}

	/**
	 * Retrieve a list of AddressModel from AddressbooksService by executing a HTTP GET request.
	 * @param webClient the WebClient for the AddressbooksService
	 * @param aid the id of the addressbook
	 * @param cid the id of the contact
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of AddressModel objects in JSON format
	 */
	public static List<AddressModel> list(
			WebClient webClient, 
			String aid,
			String oid,
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		webClient.resetQuery();
		webClient.replacePath("/").path(aid).path(ServiceUtil.ORG_PATH_EL).path(oid).path(ServiceUtil.ADDRESS_PATH_EL);
		Response _response = executeListQuery(webClient, query, position, size);
		List<AddressModel> _texts = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_texts = new ArrayList<AddressModel>(webClient.getCollection(AddressModel.class));
			System.out.println("list(webClient, " + aid + ", " + oid + ", " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + 
					") ->" + _texts.size() + " objects");
		}
		else {
			System.out.println("list(webClient, " + aid + ", " + oid + ", "+ query + ", " + position + ", " + size + ", " + expectedStatus.toString() + 
					") -> Status: " + _response.getStatus());
		}
		return _texts;
	}
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.addressbooks.AbstractAddressTest#post(org.opentdc.addressbooks.AddressModel, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected AddressModel post(
			AddressModel model, 
			Status expectedStatus) {
		return post(wc, adb.getId(), org.getId(), model, expectedStatus);
	}
	
	/**
	 * Create a new AddressModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the AddressbooksService
	 * @param aid the id of the addressbook
	 * @param cid the id of the contact
	 * @param model the AddressModel data to create on the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created AddressModel
	 */
	public static AddressModel post(
			WebClient webClient,
			String aid,
			String cid,
			AddressModel model,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(aid).path(ServiceUtil.ORG_PATH_EL).path(cid).path(ServiceUtil.ADDRESS_PATH_EL).post(model);
		if (expectedStatus != null) {
			assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(AddressModel.class);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see test.org.opentdc.addressbooks.AbstractAddressTest#get(java.lang.String, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected AddressModel get(
			String adrId, 
			Status expectedStatus) {
		return get(wc, adb.getId(), org.getId(), adrId, expectedStatus);
	}
	
	/**
	 * Read the AddressModel with id from AddressbooksService by executing a HTTP GET method.
	 * @param webClient the web client representing the AddressbooksService
	 * @param aid the id of the addressbook
	 * @param oid the id of the org
	 * @param adrId the id of the AddressModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved AddressModel object in JSON format
	 */
	public static AddressModel get(
			WebClient webClient,
			String aid,
			String oid,
			String adrId,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(aid).path(ServiceUtil.ORG_PATH_EL).path(oid).path(ServiceUtil.ADDRESS_PATH_EL).path(adrId).get();
		if (expectedStatus != null) {
			assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(AddressModel.class);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see test.org.opentdc.addressbooks.AbstractAddressTest#put(org.opentdc.addressbooks.AddressModel, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected AddressModel put(
			AddressModel model, 
			Status expectedStatus) {
		return put(wc, adb.getId(), org.getId(), model, expectedStatus);
	}
	
	/**
	 * Update a AddressModel on the AddressbooksService by executing a HTTP PUT method.
	 * @param webClient the web client representing the AddressbooksService
	 * @param aid the id of the addressbook
	 * @param oid the id of the org
	 * @param model the new AddressModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated AddressModel object in JSON format
	 */
	public static AddressModel put(
			WebClient webClient,
			String aid,
			String oid,
			AddressModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(aid).path(ServiceUtil.ORG_PATH_EL).path(oid).path(ServiceUtil.ADDRESS_PATH_EL).path(model.getId()).put(model);
		if (expectedStatus != null) {
			assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(AddressModel.class);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see test.org.opentdc.addressbooks.AbstractAddressTest#delete(java.lang.String, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected void delete(String id, Status expectedStatus) {
		delete(wc, adb.getId(), org.getId(), id, expectedStatus);
	}
	
	/**
	 * Delete the AddressModel with id on the AddressbooksService by executing a HTTP DELETE method.
	 * @param webClient the WebClient representing the AddressbooksService
	 * @param aid the id of the addressbook
	 * @param oid the id of the org
	 * @param adrId the id of the AddressModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void delete(
			WebClient webClient,
			String aid,
			String oid,
			String adrId,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(aid).path(ServiceUtil.ORG_PATH_EL).path(oid).path(ServiceUtil.ADDRESS_PATH_EL).path(adrId).delete();	
		if (expectedStatus != null) {
			assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
	}
}
