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
package test.org.opentdc.events;

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
import org.opentdc.events.EventsModel;
import org.opentdc.events.EventsService;
import org.opentdc.events.InvitationState;
import org.opentdc.events.SalutationType;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/*
 * Tests the Events-Service.
 * @author Bruno Kaiser
 */
public class EventsTest extends AbstractTestClient {
	public static final String API_URL = "api/event/";
	private WebClient eventWC = null;

	/**
	 * Initializes the test case.
	 */
	@Before
	public void initializeTest() {
		eventWC = initializeTest(ServiceUtil.EVENTS_API_URL, EventsService.class);
	}
	
	/**
	 * Cleanup all resources needed by the test case.
	 */
	@After
	public void cleanupTest() {
		eventWC.close();
	}

	/********************************** events attributes tests *********************************/	
	/**
	 * Test the empty constructor.
	 */
	@Test
	public void testEmptyConstructor() {
		EventsModel _model = new EventsModel();
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
		EventsModel _model = new EventsModel("testConstructor1", "testConstructor2", "testConstructor3");
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
		EventsModel _model = new EventsModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed", "testId", _model.getId());
	}

	/**
	 * Test firstName attribute.
	 */
	@Test
	public void testFirstName() {
		EventsModel _model = new EventsModel();
		assertNull("firstName should not be set by empty constructor", _model.getFirstName());
		_model.setFirstName("testFirstName");
		assertEquals("firstName should have changed", "testFirstName", _model.getFirstName());
	}
	
	/**
	 * Test lastName attribute.
	 */
	@Test
	public void testLastName() {
		EventsModel _model = new EventsModel();
		assertNull("lastName should not be set by empty constructor", _model.getLastName());
		_model.setLastName("testLastName");
		assertEquals("lastName should have changed", "testLastName", _model.getLastName());
	}
	
	/**
	 * Test email attribute.
	 */
	@Test
	public void testEmail() {
		EventsModel _model = new EventsModel();
		assertNull("email should not be set by empty constructor", _model.getEmail());
		_model.setEmail("testEmail");
		assertEquals("email should have changed", "testEmail", _model.getEmail());
	}

	/**
	 * Test comment attribute.
	 */
	@Test
	public void testComment() {
		EventsModel _model = new EventsModel();
		assertNull("comment should not be set by empty constructor", _model.getComment());
		_model.setComment("testComment");
		assertEquals("comment should have changed", "testComment", _model.getComment());
	}

	/**
	 * Test salutation attribute.
	 */
	@Test
	public void testSalutation() {
		EventsModel _model = new EventsModel();
		assertNull("salutation should not be set by empty constructor", _model.getSalutation());
		_model.setSalutation(SalutationType.HERR);
		assertEquals("salutation should have changed", SalutationType.HERR, _model.getSalutation());
	}

	/**
	 * Test invitationState attribute.
	 */
	@Test
	public void testInvitationState() {
		EventsModel _model = new EventsModel();
		assertNull("invitationState should not be set by empty constructor", _model.getInvitationState());
		_model.setInvitationState(InvitationState.REGISTERED);
		assertEquals("invitationState should have changed", InvitationState.REGISTERED, _model.getInvitationState());
	}

	/**
	 * Test createdBy attribute.
	 */
	@Test
	public void testCreatedBy() {
		EventsModel _model = new EventsModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	/**
	 * Test createdAt attribute.
	 */
	@Test
	public void testCreatedAt() {
		EventsModel _model = new EventsModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	/**
	 * Test modifiedBy attribute.
	 */
	@Test
	public void testModifiedBy() {
		EventsModel _model = new EventsModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	/**
	 * Test modifiedAt attribute.
	 */
	@Test
	public void testModifiedAt() {
		EventsModel _model = new EventsModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		EventsModel _model1 = new EventsModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("firstName should not be set by empty constructor", _model1.getFirstName());
		assertNull("lastName should not be set by empty constructor", _model1.getLastName());
		assertNull("email should not be set by empty constructor", _model1.getEmail());
		assertNull("comment should not be set by empty constructor", _model1.getComment());
		assertNull("salutation should not be set by empty constructor", _model1.getSalutation());
		assertNull("invitationState should not be set by empty constructor", _model1.getInvitationState());

		postEvent(_model1, Status.BAD_REQUEST);
		_model1.setFirstName("Hans");
		postEvent(_model1, Status.BAD_REQUEST);
		_model1.setLastName("Muster");
		postEvent(_model1, Status.BAD_REQUEST);
		_model1.setEmail("hans.muster@test.com");
		EventsModel _model2 = postEvent(_model1, Status.OK);
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
		
		EventsModel _model3 = getEvent(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("firstName should be unchanged", _model2.getFirstName(), _model3.getFirstName());
		assertEquals("lastName should be unchanged", _model2.getLastName(), _model3.getLastName());
		assertEquals("email should be unchanged", _model2.getEmail(), _model3.getEmail());
		assertEquals("comment should be unchanged", _model2.getComment(), _model3.getComment());
		assertEquals("salutation should be unchanged", _model2.getSalutation(), _model3.getSalutation());
		assertEquals("invitationState should be unchanged", _model2.getInvitationState(), _model3.getInvitationState());
		deleteEvent(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		EventsModel _model1 = new EventsModel("Hans", "Muster", "hans.muster@test.com");
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("firstName should be set by constructor", "Hans", _model1.getFirstName());
		assertEquals("lastName should be set by constructor", "Muster", _model1.getLastName());
		assertEquals("email should be set by constructor", "hans.muster@test.com", _model1.getEmail());

		EventsModel _model2 = postEvent(_model1, Status.OK);
		assertNull("id should be still null after remote create", _model1.getId());
		assertEquals("firstName should be unchanged", "Hans", _model1.getFirstName());
		assertEquals("lastName should be unchanged", "Muster", _model1.getLastName());
		assertEquals("email should be unchanged", "hans.muster@test.com", _model1.getEmail());
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("firstName should be unchanged", "Hans", _model2.getFirstName());
		assertEquals("lastName should be unchanged", "Muster", _model2.getLastName());

		EventsModel _model3 = getEvent(_model2.getId(), Status.OK);
		assertEquals("id should be the same", _model2.getId(), _model3.getId());
		assertEquals("firstName should be unchanged", _model2.getFirstName(), _model3.getFirstName());
		assertEquals("lastName be unchanged", _model2.getLastName(), _model3.getLastName());
		deleteEvent(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateWithClientSideId() {
		EventsModel _model = new EventsModel("Hans", "Muster", "hans.muster@test.com");
		_model.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _model.getId());
		postEvent(_model, Status.BAD_REQUEST);
	}
	
	@Test
	public void testCreateWithDuplicateId() {
		EventsModel _model1 = postEvent(new EventsModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);
		EventsModel _model2 = postEvent(new EventsModel("John", "Doe", "john.doe@test.com"), Status.OK);
		String _id = _model2.getId();
		_model2.setId(_model1.getId());		// wrongly create a 2nd EventsModel object with the same ID
		postEvent(_model2, Status.CONFLICT);
		deleteEvent(_model1.getId(), Status.NO_CONTENT);
		deleteEvent(_id, Status.NO_CONTENT);
	}
	
	@Test
	public void testList(
	) {		
		ArrayList<EventsModel> _localList = new ArrayList<EventsModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(postEvent(
				new EventsModel("Hans", "Muster", "hans.muster@test.com"),
				Status.OK));
		}
		List<EventsModel> _remoteList = listEvents(null, Status.OK);

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (EventsModel _model : _remoteList) {
			_remoteListIds.add(_model.getId());
		}
		
		for (EventsModel _model : _localList) {
			assertTrue("event <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		for (EventsModel _model : _localList) {
			getEvent(_model.getId(), Status.OK);
		}
		for (EventsModel _model : _localList) {
			deleteEvent(_model.getId(), Status.NO_CONTENT);
		}
	}
		
	@Test
	public void testCreate() {
		EventsModel _model1 = postEvent(new EventsModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);
		EventsModel _model2 = postEvent(new EventsModel("John", "Doe", "john.doe@test.com"), Status.OK);
		assertNotNull("ID should be set", _model1.getId());
		assertNotNull("ID should be set", _model2.getId());
		assertThat(_model1.getId(), not(equalTo(_model2.getId())));

		assertEquals("firstName should be set correctly", "Hans", _model1.getFirstName());
		assertEquals("lastName should be set correctly", "Muster", _model1.getLastName());
		assertEquals("firstName should be set correctly", "John", _model2.getFirstName());
		assertEquals("lastName should be set correctly", "Doe", _model2.getLastName());

		deleteEvent(_model1.getId(), Status.NO_CONTENT);
		deleteEvent(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDoubleCreate() {
		EventsModel _model = postEvent(new EventsModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);
		assertNotNull("ID should be set", _model.getId());
		postEvent(_model, Status.CONFLICT);
		deleteEvent(_model.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testRead() {
		ArrayList<EventsModel> _localList = new ArrayList<EventsModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(postEvent(new EventsModel("Hans" + i, "Muster", "hans.muster@test.com"), Status.OK));
		}
		// test read on each local element
		for (EventsModel _model : _localList) {
			getEvent(_model.getId(), Status.OK);
		}
		// test read on each listed element
		for (EventsModel _model : listEvents(null, Status.OK)) {
			assertEquals("ID should be unchanged when reading a event", _model.getId(), getEvent(_model.getId(), Status.OK).getId());
		}
		for (EventsModel _model : _localList) {
			deleteEvent(_model.getId(), Status.NO_CONTENT);
		}
	}	

	@Test
	public void testMultiRead() {
		EventsModel _model1 = postEvent(new EventsModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);
		EventsModel _model2 = getEvent(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read", _model1.getId(), _model2.getId());		
		EventsModel _model3 = getEvent(_model1.getId(), Status.OK);
		
		assertEquals("ID should be the same", _model3.getId(), _model2.getId());
		assertEquals("firstName should be the same", _model3.getFirstName(), _model2.getFirstName());
		assertEquals("lastName should be the same", _model3.getLastName(), _model2.getLastName());
		
		assertEquals("ID should be the same:", _model1.getId(), _model2.getId());
		assertEquals("firstName should be the same:", _model1.getFirstName(), _model2.getFirstName());
		assertEquals("lastName should be the same:", _model1.getLastName(), _model2.getLastName());
		deleteEvent(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testUpdate(
	) {
		EventsModel _model1 = postEvent(new EventsModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);

		_model1.setFirstName("John");
		_model1.setLastName("Doe");
		_model1.setEmail("john.doe@test.com");
		EventsModel _model2 = putEvent(_model1, Status.OK);
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		assertEquals("firstName should have changed", "John", _model2.getFirstName());
		assertEquals("lastName should have changed", "Doe", _model2.getLastName());
		assertEquals("email should have changed", "john.doe@test.com", _model2.getEmail());

		_model1.setFirstName("Hans");
		_model1.setLastName("Muster");
		_model1.setEmail("hans.muster@test.com");
		EventsModel _model3 = putEvent(_model1, Status.OK);
		assertNotNull("ID should be set", _model3.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model3.getId());	
		assertEquals("firstName should have changed", "Hans", _model3.getFirstName());
		assertEquals("lastName should have changed", "Muster", _model3.getLastName());
		assertEquals("email should have changed", "hans.muster@test.com", _model3.getEmail());

		deleteEvent(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDelete() {
		EventsModel _model1 = postEvent(new EventsModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);
		EventsModel _model2 = getEvent(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a event (remote):", _model1.getId(), _model2.getId());						
		deleteEvent(_model1.getId(), Status.NO_CONTENT);
		getEvent(_model1.getId(), Status.NOT_FOUND);
		getEvent(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		EventsModel _model = postEvent(new EventsModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);
		getEvent(_model.getId(), Status.OK);
		deleteEvent(_model.getId(), Status.NO_CONTENT);
		getEvent(_model.getId(), Status.NOT_FOUND);
		deleteEvent(_model.getId(), Status.NOT_FOUND);
		getEvent(_model.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		EventsModel _model1 = postEvent(new EventsModel("Hans", "Muster", "hans.muster@test.com"), Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		_model1.setComment("updated");
		EventsModel _model2 = putEvent(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		
		// next test fails because of timing issue; but we do not want to introduce a sleep() here
		// assertThat(_model2.getModifiedAt().toString(), not(equalTo(_model2.getCreatedAt().toString())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_model2.getModifiedBy(), not(equalTo(_model2.getCreatedBy())));

		String _createdBy = _model1.getCreatedBy();
		_model1.setCreatedBy("testModifications3");
		EventsModel _model3 = putEvent(_model1, Status.OK);
		assertEquals("update() should not change createdBy", _createdBy, _model3.getCreatedBy());

		Date _createdAt = _model1.getCreatedAt();
		_model1.setCreatedAt(new Date(1000));
		EventsModel _model4 = putEvent(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _createdAt, _model4.getCreatedAt());

		String _modifiedBy = _model1.getModifiedBy();
		_model1.setModifiedBy("testModifications5");
		EventsModel _model5 = putEvent(_model1, Status.OK);
		assertEquals("update() should not change modifiedBy", _modifiedBy, _model5.getModifiedBy());

		Date _modifiedAt = _model1.getModifiedAt();
		Date _modifiedAt2 = new Date(1000);
		_model1.setModifiedAt(_modifiedAt2);
		EventsModel _model6 = putEvent(_model1, Status.OK);
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt)));
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt2)));

		deleteEvent(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	/**
	 * Retrieve a list of EventsModel from EventsService by executing a HTTP GET request.
	 * This uses neither position nor size queries.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of EventsModel object in JSON format
	 */
	public List<EventsModel> listEvents(
			String query, 
			Status expectedStatus) {
		return listEvents(eventWC, query, -1, -1, expectedStatus);
	}
	
	/**
	 * Retrieve a list of EventsModel from EventsService by executing a HTTP GET request.
	 * @param webClient the WebClient for the EventsService
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of EventsModel objects in JSON format
	 */
	public static List<EventsModel> listEvents(
			WebClient webClient, 
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		System.out.println("listEvents(eventWC, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ")");
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
		List<EventsModel> _events = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_events = new ArrayList<EventsModel>(webClient.getCollection(EventsModel.class));
			System.out.println("listEvents(eventWC, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ") ->" + _events.size());
		}
		return _events;
	}

	/**
	 * Create a new EventsModel on the server by executing a HTTP POST request.
	 * @param model the EventsModel to post to the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created EventsModel
	 */
	public EventsModel postEvent(
			EventsModel model, 
			Status expectedStatus) {
		Response _response = eventWC.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("create() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(EventsModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Create a new EventsModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the EventsService
	 * @param model the EventsModel data to create on the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created EventsModel
	 */
	public static EventsModel postEvent(
			WebClient webClient,
			EventsModel model,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(EventsModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Create a new EventsModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the EventsService
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created EventsModel
	 */
	public static EventsModel createEvent(
			WebClient webClient, 
			String firstName, 
			String lastName,
			String email,
			Status expectedStatus) 
	{
		return postEvent(webClient, new EventsModel(firstName, lastName, email), expectedStatus);
	}
	
	/**
	 * Read the EventsModel with id from EventsService by executing a HTTP GET method.
	 * @param eventId the id of the EventsModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved EventsModel object in JSON format
	 */
	public EventsModel getEvent(
			String eventId, 
			Status expectedStatus) {
		return getEvent(eventWC, eventId, expectedStatus);
	}
	
	/**
	 * Read the EventsModel with id from EventsService by executing a HTTP GET method.
	 * @param webClient the web client representing the EventsService
	 * @param eventId the id of the EventsModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved EventsModel object in JSON format
	 */
	public static EventsModel getEvent(
			WebClient webClient,
			String eventId,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(eventId).get();
		if (expectedStatus != null) {
			assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(EventsModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Update a EventsModel on the EventsService by executing a HTTP PUT method.
	 * @param tm the new EventsModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated EventsModel object in JSON format
	 */
	public EventsModel putEvent(
			EventsModel model, 
			Status expectedStatus) {
		return putEvent(eventWC, model, expectedStatus);
	}
	
	/**
	 * Update a EventsModel on the EventsService by executing a HTTP PUT method.
	 * @param webClient the web client representing the EventsService
	 * @param model the new EventsModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated EventsModel object in JSON format
	 */
	public static EventsModel putEvent(
			WebClient webClient,
			EventsModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(model.getId()).put(model);
		if (expectedStatus != null) {
			assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(EventsModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Delete the EventsModel with id on the EventsService by executing a HTTP DELETE method.
	 * @param id the id of the EventsModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public void deleteEvent(String id, Status expectedStatus) {
		deleteEvent(eventWC, id, expectedStatus);
	}
	
	/**
	 * Delete the EventsModel with id on the EventsService by executing a HTTP DELETE method.
	 * @param webClient the WebClient representing the EventsService
	 * @param eventId the id of the EventsModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void deleteEvent(
			WebClient webClient,
			String eventId,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(eventId).delete();	
		if (expectedStatus != null) {
			assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
	}
}
