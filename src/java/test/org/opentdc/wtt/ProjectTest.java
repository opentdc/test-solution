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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.service.ServiceUtil;
import org.opentdc.wtt.ProjectModel;

/**
 * Testing Projects in WttService
 * @author Bruno Kaiser
 *
 */
public class ProjectTest extends AbstractProjectTestClient {
	/**
	 * Initialize test resources.
	 */
	@Before
	public void initializeTests() {
		super.initializeTests();
	}

	/**
	 * Free all allocated test resources.
	 */
	@After
	public void cleanupTest() {
		super.cleanupTest();
	}
	
	/********************************** project attributes tests *********************************/			
	@Test
	public void testEmptyConstructor() {
		super.testEmptyConstructor();
	}
	
	@Test
	public void testConstructor() {	
		super.testConstructor();
	}
	
	@Test
	public void testId() {
		super.testId();
	}

	@Test
	public void testTitle() {
		super.testTitle();
	}
	
	@Test
	public void testDescription() {
		super.testDescription();
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
	public void testDoubleCreate() {
		super.testDoubleCreate();
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
	
	/********************************* helper methods *********************************/	
	/* (non-Javadoc)
	 * @see test.org.opentdc.wtt.AbstractProjectTestClient#list(java.lang.String, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected List<ProjectModel> list(
			String query, 
			Status expectedStatus) {
		return list(wc, company.getId(), query, -1, Integer.MAX_VALUE, expectedStatus);
	}
	
	/**
	 * Retrieve a list of ProjectModel from WttService by executing a HTTP GET request.
	 * @param webClient the WebClient for the WttService
	 * @param companyId the ID of the company to be listed
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of ProjectModel objects in JSON format
	 */
	public static List<ProjectModel> list(
			WebClient webClient, 
			String companyId,
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		webClient.resetQuery();
		webClient.replacePath("/").path(companyId).path(ServiceUtil.PROJECT_PATH_EL);
		Response _response = executeListQuery(webClient, query, position, size);
		List<ProjectModel> _list = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_list = new ArrayList<ProjectModel>(webClient.getCollection(ProjectModel.class));
			System.out.println("list(webClient, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ") ->" + _list.size());
		}
		return _list;
	}
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.wtt.AbstractProjectTestClient#post(org.opentdc.wtt.ProjectModel, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected ProjectModel post(
			ProjectModel model,
			Status expectedStatus) {
		return post(wc, company.getId(), model, expectedStatus);
	}

	/**
	 * Create a new ProjectModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the WttService
	 * @param companyId the ID of the company
	 * @param model the ProjectModel data to create on the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created ProjectModel
	 */
	public static ProjectModel post(
			WebClient webClient,
			String companyId,
			ProjectModel model,
			Status expectedStatus) 
	{
		Response _response = webClient.replacePath("/").path(companyId).path(ServiceUtil.PROJECT_PATH_EL).post(model);
		if (expectedStatus != null) {
			assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(ProjectModel.class);
		} else {
			return null;
		}
	}		
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.wtt.AbstractProjectTestClient#get(java.lang.String, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected ProjectModel get(
			String id, 
			Status expectedStatus) {
		return get(wc, company.getId(), id, expectedStatus);
	}
	
	/**
	 * Read the ProjectModel with id from WttService by executing a HTTP GET method.
	 * @param webClient the web client representing the WttService
	 * @param companyId the ID of the company 
	 * @param id the id of the ProjectModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved ProjectModel object in JSON format
	 */
	public static ProjectModel get(
			WebClient webClient,
			String companyId,
			String id,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(companyId).path(ServiceUtil.PROJECT_PATH_EL).path(id).get();
		if (expectedStatus != null) {
			assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(ProjectModel.class);
		} else {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.wtt.AbstractProjectTestClient#put(org.opentdc.wtt.ProjectModel, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected ProjectModel put(
			ProjectModel model, 
			Status expectedStatus) {
		return put(wc, company.getId(), model, expectedStatus);
	}
	
	/**
	 * Update a ProjectModel on the WttService by executing a HTTP PUT method.
	 * @param webClient the web client representing the WttService
	 * @param companyId the ID of the company 
	 * @param model the new ProjectModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated ProjectModel object in JSON format
	 */
	public static ProjectModel put(
			WebClient webClient,
			String companyId,
			ProjectModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(companyId).path(ServiceUtil.PROJECT_PATH_EL).path(model.getId()).put(model);
		if (expectedStatus != null) {
			assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(ProjectModel.class);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see test.org.opentdc.wtt.AbstractProjectTestClient#delete(java.lang.String, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected void delete(
			String id, 
			Status expectedStatus) {
		delete(wc, company.getId(), id, expectedStatus);
	}
	
	/**
	 * Delete the ProjectModel with id on the WttService by executing a HTTP DELETE method.
	 * @param webClient the WebClient for the WttService
	 * @param companyId the ID of the company 
	 * @param id the id of the ProjectModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void delete(
			WebClient webClient,
			String companyId,
			String id,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(companyId).path(ServiceUtil.PROJECT_PATH_EL).path(id).delete();	
		if (expectedStatus != null) {
			assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
	}		
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	@Override
	protected int calculateMembers() {
		return list(wc, company.getId(), null, 0, Integer.MAX_VALUE, Status.OK).size();
	}
}
