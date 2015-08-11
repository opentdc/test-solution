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
package test.org.opentdc.invitations;

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
import org.opentdc.invitations.InvitationModel;
import org.opentdc.invitations.InvitationsService;
import org.opentdc.invitations.InvitationState;
import org.opentdc.invitations.SalutationType;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/*
 * Tests the Invitations-Service.
 * @author Bruno Kaiser
 */
public class InvitationTest extends AbstractTestClient {
	private WebClient wc = null;

	/**
	 * Initializes the test case.
	 */
	@Before
	public void initializeTest() {
		wc = initializeTest(ServiceUtil.INVITATIONS_API_URL, InvitationsService.class);
	}
	
	/**
	 * Cleanup all resources needed by the test case.
	 */
	@After
	public void cleanupTest() {
		wc.close();
	}

	/********************************** invitations attributes tests *********************************/	
	/**
	 * Test the empty constructor.
	 */
	@Test
	public void testEmptyConstructor() {
		InvitationModel _model = new InvitationModel();
		assertNull("id should not be set by empty constructor", _model.getId());
		assertNull("firstName should not be set by empty constructor", _model.getFirstName());
		assertNull("lastName should not be set by empty constructor", _model.getLastName());
		assertNull("email should not be set by empty constructor", _model.getEmail());
		assertNull("comment should not be set by empty constructor", _model.getComment());
		assertNull("salutation should not be set by empty constructor", _model.getSalutation());
		assertNull("invitationState should not be set by empty constructor", _model.getInvitationState());
	}
	
	/**
	 * Test the custom constructor.
	 */
	@Test
	public void testConstructor() {		
		InvitationModel _model = new InvitationModel("testConstructor1", "testConstructor2", "testConstructor3");
		assertNull("id should not be set by constructor", _model.getId());
		assertEquals("firstName should be set by constructor", "testConstructor1", _model.getFirstName());
		assertEquals("lastName should be set by constructor", "testConstructor2", _model.getLastName());
		assertEquals("email should be set by constructor", "testConstructor3", _model.getEmail());
		assertNull("comment should not be set by constructor", _model.getComment());
		assertNull("salutation should not be set by constructor", _model.getSalutation());
		assertNull("invitationState should not be set by constructor", _model.getInvitationState());
	}
	
	/**
	 * Test id attribute.
	 */
	@Test
	public void testId() {
		InvitationModel _model = new InvitationModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed", "testId", _model.getId());
	}

	/**
	 * Test firstName attribute.
	 */
	@Test
	public void testFirstName() {
		InvitationModel _model = new InvitationModel();
		assertNull("firstName should not be set by empty constructor", _model.getFirstName());
		_model.setFirstName("testFirstName");
		assertEquals("firstName should have changed", "testFirstName", _model.getFirstName());
	}
	
	/**
	 * Test lastName attribute.
	 */
	@Test
	public void testLastName() {
		InvitationModel _model = new InvitationModel();
		assertNull("lastName should not be set by empty constructor", _model.getLastName());
		_model.setLastName("testLastName");
		assertEquals("lastName should have changed", "testLastName", _model.getLastName());
	}
	
	/**
	 * Test email attribute.
	 */
	@Test
	public void testEmail() {
		InvitationModel _model = new InvitationModel();
		assertNull("email should not be set by empty constructor", _model.getEmail());
		_model.setEmail("testEmail");
		assertEquals("email should have changed", "testEmail", _model.getEmail());
	}

	/**
	 * Test comment attribute.
	 */
	@Test
	public void testComment() {
		InvitationModel _model = new InvitationModel();
		assertNull("comment should not be set by empty constructor", _model.getComment());
		_model.setComment("testComment");
		assertEquals("comment should have changed", "testComment", _model.getComment());
	}

	/**
	 * Test salutation attribute.
	 */
	@Test
	public void testSalutation() {
		InvitationModel _model = new InvitationModel();
		assertNull("salutation should not be set by empty constructor", _model.getSalutation());
		_model.setSalutation(SalutationType.HERR);
		assertEquals("salutation should have changed", SalutationType.HERR, _model.getSalutation());
	}

	/**
	 * Test invitationState attribute.
	 */
	@Test
	public void testInvitationState() {
		InvitationModel _model = new InvitationModel();
		assertNull("invitationState should not be set by empty constructor", _model.getInvitationState());
		_model.setInvitationState(InvitationState.REGISTERED);
		assertEquals("invitationState should have changed", InvitationState.REGISTERED, _model.getInvitationState());
	}

	/**
	 * Test createdBy attribute.
	 */
	@Test
	public void testCreatedBy() {
		InvitationModel _model = new InvitationModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	/**
	 * Test createdAt attribute.
	 */
	@Test
	public void testCreatedAt() {
		InvitationModel _model = new InvitationModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	/**
	 * Test modifiedBy attribute.
	 */
	@Test
	public void testModifiedBy() {
		InvitationModel _model = new InvitationModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	/**
	 * Test modifiedAt attribute.
	 */
	@Test
	public void testModifiedAt() {
		InvitationModel _model = new InvitationModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		InvitationModel _model1 = new InvitationModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("firstName should not be set by empty constructor", _model1.getFirstName());
		assertNull("lastName should not be set by empty constructor", _model1.getLastName());
		assertNull("email should not be set by empty constructor", _model1.getEmail());
		assertNull("comment should not be set by empty constructor", _model1.getComment());
		assertNull("salutation should not be set by empty constructor", _model1.getSalutation());
		assertNull("invitationState should not be set by empty constructor", _model1.getInvitationState());

		post(_model1, Status.BAD_REQUEST);
		_model1.setFirstName("Hans");
		post(_model1, Status.BAD_REQUEST);
		_model1.setLastName("Muster");
		post(_model1, Status.BAD_REQUEST);
		_model1.setEmail("hans.muster@test.com");
		InvitationModel _model2 = post(_model1, Status.OK);
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the firstName of the local object", "Hans", _model1.getFirstName());
		assertEquals("create() should not change the lastName of the local object", "Muster", _model1.getLastName());
		assertEquals("create() should not change the email of the local object", "hans.muster@test.com", _model1.getEmail());
		assertNull("create() should not change the comment of the local object", _model1.getComment());
		assertNull("create() should not change the salutation of the local object", _model1.getSalutation());
		assertNull("create() should not change the invitationState of the local object", _model1.getInvitationState());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("create() should not change the firstName", "Hans", _model2.getFirstName());
		assertEquals("create() should not change the lastName", "Muster", _model2.getLastName());
		assertEquals("create() should not change the email", "hans.muster@test.com", _model2.getEmail());
		assertNull("create() should not change the comment", _model2.getComment());
		assertEquals("create() should not change the salutation", SalutationType.DU_M, _model2.getSalutation());
		assertEquals("create() should not change the invitationState", InvitationState.INITIAL, _model2.getInvitationState());
		
		InvitationModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("firstName should be unchanged", _model2.getFirstName(), _model3.getFirstName());
		assertEquals("lastName should be unchanged", _model2.getLastName(), _model3.getLastName());
		assertEquals("email should be unchanged", _model2.getEmail(), _model3.getEmail());
		assertEquals("comment should be unchanged", _model2.getComment(), _model3.getComment());
		assertEquals("salutation should be unchanged", _model2.getSalutation(), _model3.getSalutation());
		assertEquals("invitationState should be unchanged", _model2.getInvitationState(), _model3.getInvitationState());
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		InvitationModel _model1 = new InvitationModel("Hans", "Muster", "hans.muster@test.com");
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("firstName should be set by constructor", "Hans", _model1.getFirstName());
		assertEquals("lastName should be set by constructor", "Muster", _model1.getLastName());
		assertEquals("email should be set by constructor", "hans.muster@test.com", _model1.getEmail());

		InvitationModel _model2 = post(_model1, Status.OK);
		assertNull("id should be still null after remote create", _model1.getId());
		assertEquals("firstName should be unchanged", "Hans", _model1.getFirstName());
		assertEquals("lastName should be unchanged", "Muster", _model1.getLastName());
		assertEquals("email should be unchanged", "hans.muster@test.com", _model1.getEmail());
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("firstName should be unchanged", "Hans", _model2.getFirstName());
		assertEquals("lastName should be unchanged", "Muster", _model2.getLastName());

		InvitationModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("id should be the same", _model2.getId(), _model3.getId());
		assertEquals("firstName should be unchanged", _model2.getFirstName(), _model3.getFirstName());
		assertEquals("lastName be unchanged", _model2.getLastName(), _model3.getLastName());
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateWithClientSideId() {
		InvitationModel _model = new InvitationModel("Hans", "Muster", "hans.muster@test.com");
		_model.setId("abc123");
		assertEquals("id should have changed", "abc123", _model.getId());
		post(_model, Status.BAD_REQUEST);
	}
	
	/* only works with mongo impl
	@Test
	public void testCreateWithInvalidId() {
		InvitationModel _model = new InvitationModel("Hans", "Muster", "hans.muster@test.com");
		_model.setId("LOCAL_ID");		// invalid hexadecimal representation
		assertEquals("id should have changed", "LOCAL_ID", _model.getId());
		postInvitation(_model, Status.INTERNAL_SERVER_ERROR);
	}
	*/
	@Test
	public void testCreateWithDuplicateId() {
		InvitationModel _model1 = post(new InvitationModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);
		InvitationModel _model2 = post(new InvitationModel("John", "Doe", "john.doe@test.com"), Status.OK);
		String _model2Id = _model2.getId();
		_model2.setId(_model1.getId());		// wrongly create a 2nd InvitationModel object with the same ID
		post(_model2, Status.CONFLICT);
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2Id, Status.NO_CONTENT);
	}
	
	@Test
	public void testList(
	) {		
		ArrayList<InvitationModel> _localList = new ArrayList<InvitationModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(
				new InvitationModel("Hans", "Muster", "hans.muster@test.com"),
				Status.OK));
		}
		List<InvitationModel> _remoteList = list(null, Status.OK);

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (InvitationModel _model : _remoteList) {
			_remoteListIds.add(_model.getId());
		}
		
		for (InvitationModel _model : _localList) {
			assertTrue("invitation <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		for (InvitationModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		for (InvitationModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}
		
	@Test
	public void testCreate() {
		InvitationModel _model1 = post(new InvitationModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);
		InvitationModel _model2 = post(new InvitationModel("John", "Doe", "john.doe@test.com"), Status.OK);
		assertNotNull("ID should be set", _model1.getId());
		assertNotNull("ID should be set", _model2.getId());
		assertThat(_model1.getId(), not(equalTo(_model2.getId())));

		assertEquals("firstName should be set correctly", "Hans", _model1.getFirstName());
		assertEquals("lastName should be set correctly", "Muster", _model1.getLastName());
		assertEquals("firstName should be set correctly", "John", _model2.getFirstName());
		assertEquals("lastName should be set correctly", "Doe", _model2.getLastName());

		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDoubleCreate() {
		InvitationModel _model = post(new InvitationModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);
		assertNotNull("ID should be set", _model.getId());
		post(_model, Status.CONFLICT);
		delete(_model.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testRead() {
		ArrayList<InvitationModel> _localList = new ArrayList<InvitationModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(new InvitationModel("Hans" + i, "Muster", "hans.muster@test.com"), Status.OK));
		}
		// test read on each local element
		for (InvitationModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		// test read on each listed element
		for (InvitationModel _model : list(null, Status.OK)) {
			assertEquals("ID should be unchanged when reading a invitation", _model.getId(), get(_model.getId(), Status.OK).getId());
		}
		for (InvitationModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}	

	@Test
	public void testMultiRead() {
		InvitationModel _model1 = post(new InvitationModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);
		InvitationModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read", _model1.getId(), _model2.getId());		
		InvitationModel _model3 = get(_model1.getId(), Status.OK);
		
		assertEquals("ID should be the same", _model3.getId(), _model2.getId());
		assertEquals("firstName should be the same", _model3.getFirstName(), _model2.getFirstName());
		assertEquals("lastName should be the same", _model3.getLastName(), _model2.getLastName());
		
		assertEquals("ID should be the same:", _model1.getId(), _model2.getId());
		assertEquals("firstName should be the same:", _model1.getFirstName(), _model2.getFirstName());
		assertEquals("lastName should be the same:", _model1.getLastName(), _model2.getLastName());
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testUpdate(
	) {
		InvitationModel _model1 = post(new InvitationModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);

		_model1.setFirstName("John");
		_model1.setLastName("Doe");
		_model1.setEmail("john.doe@test.com");
		InvitationModel _model2 = put(_model1, Status.OK);
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		assertEquals("firstName should have changed", "John", _model2.getFirstName());
		assertEquals("lastName should have changed", "Doe", _model2.getLastName());
		assertEquals("email should have changed", "john.doe@test.com", _model2.getEmail());

		_model1.setFirstName("Hans");
		_model1.setLastName("Muster");
		_model1.setEmail("hans.muster@test.com");
		InvitationModel _model3 = put(_model1, Status.OK);
		assertNotNull("ID should be set", _model3.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model3.getId());	
		assertEquals("firstName should have changed", "Hans", _model3.getFirstName());
		assertEquals("lastName should have changed", "Muster", _model3.getLastName());
		assertEquals("email should have changed", "hans.muster@test.com", _model3.getEmail());

		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDelete() {
		InvitationModel _model1 = post(new InvitationModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);
		InvitationModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a invitation (remote):", _model1.getId(), _model2.getId());						
		delete(_model1.getId(), Status.NO_CONTENT);
		get(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		InvitationModel _model = post(new InvitationModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);
		get(_model.getId(), Status.OK);
		delete(_model.getId(), Status.NO_CONTENT);
		get(_model.getId(), Status.NOT_FOUND);
		delete(_model.getId(), Status.NOT_FOUND);
		get(_model.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		InvitationModel _model1 = post(new InvitationModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		_model1.setComment("updated");
		InvitationModel _model2 = put(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		
		// next test fails because of timing issue; but we do not want to introduce a sleep() here
		// assertThat(_model2.getModifiedAt().toString(), not(equalTo(_model2.getCreatedAt().toString())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_model2.getModifiedBy(), not(equalTo(_model2.getCreatedBy())));

		String _createdBy = _model1.getCreatedBy();
		_model1.setCreatedBy("testModifications3");
		InvitationModel _model3 = put(_model1, Status.OK);
		assertEquals("update() should not change createdBy", _createdBy, _model3.getCreatedBy());

		Date _createdAt = _model1.getCreatedAt();
		_model1.setCreatedAt(new Date(1000));
		InvitationModel _model4 = put(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _createdAt, _model4.getCreatedAt());

		String _modifiedBy = _model1.getModifiedBy();
		_model1.setModifiedBy("testModifications5");
		InvitationModel _model5 = put(_model1, Status.OK);
		assertEquals("update() should not change modifiedBy", _modifiedBy, _model5.getModifiedBy());

		Date _modifiedAt = _model1.getModifiedAt();
		Date _modifiedAt2 = new Date(1000);
		_model1.setModifiedAt(_modifiedAt2);
		InvitationModel _model6 = put(_model1, Status.OK);
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt)));
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt2)));

		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	/**
	 * Retrieve a list of InvitationModel from InvitationsService by executing a HTTP GET request.
	 * This uses neither position nor size queries, ie it returns all members of the list.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of InvitationModel object in JSON format
	 */
	public List<InvitationModel> list(
			String query, 
			Status expectedStatus) {
		return list(wc, query, 0, Integer.MAX_VALUE, expectedStatus);
	}
	
	/**
	 * Retrieve a list of InvitationModel from InvitationsService by executing a HTTP GET request.
	 * @param webClient the WebClient for the InvitationsService
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of InvitationModel objects in JSON format
	 */
	public static List<InvitationModel> list(
			WebClient webClient, 
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		System.out.println("list(webClient, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ")");
		Response _response = null;
		webClient.resetQuery();
		if (query == null) {
			if (position >= 0) {
				if (size >= 0) {
					_response = webClient.replacePath("/").query("position", position).query("size", size).get();
				} else {
					_response = webClient.replacePath("/").query("position", position).get();
				}
			} else {
				if (size >= 0) {
					_response = webClient.replacePath("/").query("size", size).get();
				} else {
					_response = webClient.replacePath("/").get();
				}
			}
		} else {
			if (position >= 0) {
				if (size >= 0) {
					_response = webClient.replacePath("/").query("query", query).query("position", position).query("size", size).get();					
				} else {
					_response = webClient.replacePath("/").query("query", query).query("position", position).get();					
				}
			} else {
				if (size >= 0) {
					_response = webClient.replacePath("/").query("query", query).query("size", size).get();					
				} else {
					_response = webClient.replacePath("/").query("query", query).get();					
				}				
			}
		}
		List<InvitationModel> _invitations = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_invitations = new ArrayList<InvitationModel>(webClient.getCollection(InvitationModel.class));
			System.out.println("list(webClient, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ") ->" + _invitations.size());
		}
		return _invitations;
	}

	/**
	 * Create a new InvitationModel on the server by executing a HTTP POST request.
	 * @param model the InvitationModel to post to the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created InvitationModel
	 */
	public InvitationModel post(
			InvitationModel model, 
			Status expectedStatus) {
		Response _response = wc.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("create() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(InvitationModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Create a new InvitationModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the InvitationsService
	 * @param model the InvitationModel data to create on the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created InvitationModel
	 */
	public static InvitationModel post(
			WebClient webClient,
			InvitationModel model,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(InvitationModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Create a new InvitationModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the InvitationsService
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created InvitationModel
	 */
	public static InvitationModel create(
			WebClient webClient, 
			String firstName, 
			String lastName,
			String email,
			Status expectedStatus) 
	{
		return post(webClient, new InvitationModel(firstName, lastName, email), expectedStatus);
	}
	
	/**
	 * Read the InvitationModel with id from InvitationsService by executing a HTTP GET method.
	 * @param invitationId the id of the InvitationModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved InvitationModel object in JSON format
	 */
	public InvitationModel get(
			String invitationId, 
			Status expectedStatus) {
		return get(wc, invitationId, expectedStatus);
	}
	
	/**
	 * Read the InvitationModel with id from InvitationsService by executing a HTTP GET method.
	 * @param webClient the web client representing the InvitationsService
	 * @param invitationId the id of the InvitationModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved InvitationModel object in JSON format
	 */
	public static InvitationModel get(
			WebClient webClient,
			String invitationId,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(invitationId).get();
		if (expectedStatus != null) {
			assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(InvitationModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Update a InvitationModel on the InvitationsService by executing a HTTP PUT method.
	 * @param tm the new InvitationModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated InvitationModel object in JSON format
	 */
	public InvitationModel put(
			InvitationModel model, 
			Status expectedStatus) {
		return put(wc, model, expectedStatus);
	}
	
	/**
	 * Update a InvitationModel on the InvitationsService by executing a HTTP PUT method.
	 * @param webClient the web client representing the InvitationsService
	 * @param model the new InvitationModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated InvitationModel object in JSON format
	 */
	public static InvitationModel put(
			WebClient webClient,
			InvitationModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(model.getId()).put(model);
		if (expectedStatus != null) {
			assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(InvitationModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Delete the InvitationModel with id on the InvitationsService by executing a HTTP DELETE method.
	 * @param id the id of the InvitationModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public void delete(String id, Status expectedStatus) {
		delete(wc, id, expectedStatus);
	}
	
	/**
	 * Delete the InvitationModel with id on the InvitationsService by executing a HTTP DELETE method.
	 * @param webClient the WebClient representing the InvitationsService
	 * @param invitationId the id of the InvitationModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void delete(
			WebClient webClient,
			String invitationId,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(invitationId).delete();	
		if (expectedStatus != null) {
			assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
	}
	
	protected int calculateMembers() {
		return list(wc, null, 0, Integer.MAX_VALUE, Status.OK).size();
	}
}
