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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.opentdc.workrecords.WorkRecordModel;
import org.opentdc.workrecords.WorkRecordsService;

import test.org.opentdc.AbstractTestClient;

public class WorkRecordsTest extends AbstractTestClient<WorkRecordsService> {

	private static final String API = "api/workrecord/";

	@Before
	public void initializeTests(
	) {
		initializeTest(API, WorkRecordsService.class);
	}
	
	/********************************** workrecord tests *********************************/	
	@Test
	public void testWorkRecordModelEmptyConstructor() {
		// new() -> _c
		WorkRecordModel _c = new WorkRecordModel();
		assertNull("id should not be set by empty constructor", _c.getId());
		assertNull("projectId should not be set by empty constructor", _c.getProjectId());
		assertNull("resourceId should not be set by empty constructor", _c.getResourceId());
		assertNull("rateId should not be set by empty constructor", _c.getRateId());
		assertNull("startAt should not be set by empty constructor", _c.getStartAt());
		assertEquals("durationHours should be set on default initial value of an integer", 0, _c.getDurationHours());
		assertEquals("durationMinutes should be set on default initial value of an integer", 0, _c.getDurationMinutes());
		assertNull("comment should not be set by empty constructor", _c.getComment());
		assertEquals("isBillable should be set on default initial value of a boolean", false, _c.isBillable());
	}

	@Test
	public void testWorkRecordModelConstructor() {		
		// new("PID", "RID", new Date(), 1, 30, "RATEID", true, "MY_COMMENT") -> _c
		WorkRecordModel _c = new WorkRecordModel("PID", "RID", new Date(), 1, 30, "RATEID", true, "MY_COMMENT");
		assertNull("id should not be set by constructor", _c.getId());
		assertEquals("projectId should be set by constructor", "PID", _c.getProjectId());
		assertEquals("resourceId should be set by constructor", "RID", _c.getResourceId());
		assertEquals("rateId should be set by constructor", "RATEID", _c.getRateId());
		assertNotNull("startAt should be set by constructor", _c.getStartAt());
		assertEquals("durationHours should be set by constructor", 1, _c.getDurationHours());
		assertEquals("durationMinutes should be set by constructor", 30, _c.getDurationMinutes());
		assertEquals("comment should be set by constructor", "MY_COMMENT", _c.getComment());
		assertEquals("isBillable should be set by constructor", true, _c.isBillable());
	}

	@Test
	public void testIdAttributeChange() {
		// new() -> _c -> _c.setId()
		WorkRecordModel _c = new WorkRecordModel();
		assertNull("id should not be set by constructor", _c.getId());
		_c.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _c.getId());
	}

	@Test
	public void testProjectIdAttributeChange() {
		// new() -> _c -> _c.setProjectId()
		WorkRecordModel _c = new WorkRecordModel();
		assertNull("projectId should not be set by empty constructor", _c.getProjectId());
		_c.setProjectId("PID");
		assertEquals("projectId should have changed:", "PID", _c.getProjectId());
	}
	
	@Test
	public void testResourceIdAttributeChange() {
		// new() -> _c -> _c.setResourceId()
		WorkRecordModel _c = new WorkRecordModel();
		assertNull("resourceId should not be set by empty constructor", _c.getResourceId());
		_c.setResourceId("RID");
		assertEquals("resourceId should have changed:", "RID", _c.getResourceId());
	}
		
	@Test
	public void testRateIdAttributeChange() {
		// new() -> _c -> _c.setRateId()
		WorkRecordModel _c = new WorkRecordModel();
		assertNull("rateId should not be set by empty constructor", _c.getRateId());
		_c.setRateId("RATEID");
		assertEquals("rateId should have changed:", "RATEID", _c.getRateId());
	}
	
	// TODO: getStartAt
	
	@Test
	public void testDurationHoursAttributeChange() {
		// new() -> _c -> _c.setDurationHours()
		WorkRecordModel _c = new WorkRecordModel();
		assertEquals("durationHours should be initialized to 0 by empty constructor", 0, _c.getDurationHours());
		_c.setDurationHours(3);
		assertEquals("durationHours should have changed:", 3, _c.getDurationHours());
	}
	
	@Test
	public void testDurationMinutesAttributeChange() {
		// new() -> _c -> _c.setDurationMinutes()
		WorkRecordModel _c = new WorkRecordModel();
		assertEquals("durationMinutes should be initialized to 0 by empty constructor", 0, _c.getDurationMinutes());
		_c.setDurationMinutes(45);
		assertEquals("durationMinutes should have changed:", 45, _c.getDurationMinutes());
	}
	
	@Test
	public void testCommentAttributeChange() {
		// new() -> _c -> _c.setComment()
		WorkRecordModel _c = new WorkRecordModel();
		assertNull("comment should not be set by empty constructor", _c.getComment());
		_c.setComment("MY_COMMENT");
		assertEquals("comment should have changed:", "MY_COMMENT", _c.getComment());
	}
	
	@Test
	public void testIsBillableAttributeChange() {
		// new() -> _c -> _c.setBillable()
		WorkRecordModel _c = new WorkRecordModel();
		assertEquals("isBillable should be initialized to false by empty constructor", false, _c.isBillable());
		_c.setBillable(true);
		assertEquals("isBillable should have changed:", true, _c.isBillable());
	}
	
	@Test
	public void testWorkRecordCreateReadDeleteWithEmptyConstructor() {
		// new() -> _c1
		WorkRecordModel _c1 = new WorkRecordModel();
		assertNull("id should not be set by empty constructor", _c1.getId());
		assertNull("projectId should not be set by empty constructor", _c1.getProjectId());
		assertNull("resourceId should not be set by empty constructor", _c1.getResourceId());
		assertNull("rateId should not be set by empty constructor", _c1.getRateId());
		assertNull("startAt should not be set by empty constructor", _c1.getStartAt());
		assertEquals("durationHours should be set on default initial value of an integer", 0, _c1.getDurationHours());
		assertEquals("durationMinutes should be set on default initial value of an integer", 0, _c1.getDurationMinutes());
		assertNull("comment should not be set by empty constructor", _c1.getComment());
		assertEquals("isBillable should be set on default initial value of a boolean", false, _c1.isBillable());
		
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _c2 = _response.readEntity(WorkRecordModel.class);
		assertNull("create() should not change the id of the local object", _c1.getId());
		assertNull("create() should not change the projectId of the local object", _c1.getProjectId());
		assertNull("create() should not change the resourceId of the local object", _c1.getResourceId());
		assertNull("create() should not change the rateId of the local object", _c1.getRateId());
		assertNull("create() should not change the startAt Date of the local object", _c1.getStartAt());
		assertEquals("create() should not change durationHours on the local object", 0, _c1.getDurationHours());
		assertEquals("create() should not change durationMinutes on the local object", 0, _c1.getDurationMinutes());
		assertNull("create() should not change comment on the local object", _c1.getComment());
		assertEquals("create() should not change isBillable on the local object", false, _c1.isBillable());
		
		assertNotNull("create() should set a valid id on the remote object returned", _c2.getId());
		assertNull("projectId of returned object should  still be null after remote create", _c2.getProjectId());
		assertNull("resourceId of returned object should still be null after remote create", _c2.getResourceId());
		assertNull("rateId of returned object should still be null after remote create", _c2.getRateId());
		assertNull("startAt Date of returned object should still be null", _c2.getStartAt());
		assertEquals("durationHours of returned object should be unchanged", 0, _c2.getDurationHours());
		assertEquals("durationMinutes of returned object should unchanged", 0, _c2.getDurationMinutes());
		
		// read(_c2) -> _c3
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _c3 = _response.readEntity(WorkRecordModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("projectId of returned object should be unchanged after remote create", _c2.getProjectId(), _c3.getProjectId());
		assertEquals("resourceId of returned object should be unchanged after remote create", _c2.getResourceId(), _c3.getResourceId());
		assertEquals("rateId of returned object should be unchanged after remote create", _c2.getRateId(), _c3.getRateId());
		assertEquals("startAt of returned object should be unchanged after remote create", _c2.getStartAt(), _c3.getStartAt());
		assertEquals("durationHours of returned object should be unchanged after remote create", _c2.getDurationHours(), _c3.getDurationHours());
		assertEquals("durationMinutes of returned object should be unchanged after remote create", _c2.getDurationMinutes(), _c3.getDurationMinutes());
		assertEquals("comment of returned object should be unchanged after remote create", _c2.getComment(), _c3.getComment());
		assertEquals("isBillable of returned object should be unchanged after remote create", _c2.isBillable(), _c3.isBillable());
		// delete(_c3)
		_response = webclient.replacePath(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordCreateReadDelete() {
		// new("MY_TITLE", "MY_DESC") -> _c1
		Date _date = new Date();
		WorkRecordModel _c1 = new WorkRecordModel("PID", "RID", _date, 4, 20, "RATEID", true, "MY_COMMENT");
		assertNull("id should not be set by constructor", _c1.getId());
		assertEquals("projectId should be set by constructor", "PID", _c1.getProjectId());
		assertEquals("resourceId should be set by constructor", "RID", _c1.getResourceId());
		assertEquals("rateId should be set by constructor", "RATEID", _c1.getRateId());
		assertEquals("startAt should be set by constructor", _date, _c1.getStartAt());
		assertEquals("durationHours should be set by constructor", 4, _c1.getDurationHours());
		assertEquals("durationMinutes should be set by constructor", 20, _c1.getDurationMinutes());
		assertEquals("comment should be set by constructor", "MY_COMMENT", _c1.getComment());
		assertEquals("isBillable should be set by constructor", true, _c1.isBillable());
		
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _c2 = _response.readEntity(WorkRecordModel.class);
		assertNull("create() should not change the id of the local object", _c1.getId());
		assertEquals("create() should not change the projectId of the local object", "PID", _c1.getProjectId());
		assertEquals("create() should not change the resourceId of the local object", "RID", _c1.getResourceId());
		assertEquals("create() should not change the rateId of the local object", "RATEID", _c1.getRateId());
		assertEquals("create() should not change the startAt Date of the local object", _date, _c1.getStartAt());
		assertEquals("create() should not change durationHours on the local object", 4, _c1.getDurationHours());
		assertEquals("create() should not change durationMinutes on the local object", 20, _c1.getDurationMinutes());
		assertEquals("create() should not change comment on the local object", "MY_COMMENT", _c1.getComment());
		assertEquals("create() should not change isBillable on the local object", true, _c1.isBillable());
				
		assertNotNull("id of returned object should be set", _c2.getId());
		assertEquals("projectId of returned object should be unchanged after remote create", "PID", _c2.getProjectId());
		assertEquals("resourceId of returned object should be unchanged after remote create", "RID", _c2.getResourceId());
		assertEquals("rateId of returned object should be unchanged after remote create", "RATEID", _c2.getRateId());
		assertEquals("create() should not change the startAt Date of the local object", _date, _c2.getStartAt());
		assertEquals("durationHours of returned object should be unchanged", 4, _c2.getDurationHours());
		assertEquals("durationMinutes of returned object should be unchanged", 20, _c2.getDurationMinutes());
		assertEquals("comment of returned object should be unchanged", "MY_COMMENT", _c2.getComment());
		assertEquals("create() should not change isBillable", true, _c2.isBillable());

		// read(_c2)  -> _c3
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _c3 = _response.readEntity(WorkRecordModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("projectId of returned object should be the same", _c2.getProjectId(), _c3.getProjectId());
		assertEquals("resourceId of returned object should be the same", _c2.getResourceId(), _c3.getResourceId());
		assertEquals("rateId of returned object should be the same", _c2.getRateId(), _c3.getRateId());
		assertEquals("the startAt Date should be the same", _c2.getStartAt(), _c3.getStartAt());
		assertEquals("durationHours of returned object should be the same", _c2.getDurationHours(), _c3.getDurationHours());
		assertEquals("durationMinutes of returned object should be the same", _c2.getDurationMinutes(), _c3.getDurationMinutes());
		assertEquals("comment of returned object should be the same", _c2.getComment(), _c3.getComment());
		assertEquals("isBillable should be the same", _c2.isBillable(), _c3.isBillable());		
		// delete(_c3)
		_response = webclient.replacePath(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateWorkRecordWithClientSideId() {
		// new() -> _c1 -> _c1.setId()
		WorkRecordModel _c1 = new WorkRecordModel();
		_c1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _c1.getId());
		// create(_c1) -> BAD_REQUEST
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateWorkRecordWithDuplicateId() {
		// create(new()) -> _c2
		Response _response = webclient.replacePath("/").post(new WorkRecordModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _c2 = _response.readEntity(WorkRecordModel.class);

		// new() -> _c3 -> _c3.setId(_c2.getId())
		WorkRecordModel _c3 = new WorkRecordModel();
		_c3.setId(_c2.getId());		// wrongly create a 2nd WorkRecordModel object with the same ID
		
		// create(_c3) -> CONFLICT
		_response = webclient.replacePath("/").post(_c3);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordList(
	) {		
		ArrayList<WorkRecordModel> _localList = new ArrayList<WorkRecordModel>();
		Response _response = null;
		webclient.replacePath("/");
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = webclient.post(new WorkRecordModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(WorkRecordModel.class));
		}
		
		// list(/) -> _remoteList
		_response = webclient.replacePath("/").get();
		List<WorkRecordModel> _remoteList = new ArrayList<WorkRecordModel>(webclient.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (WorkRecordModel _c : _remoteList) {
			_remoteListIds.add(_c.getId());
		}
		
		for (WorkRecordModel _c : _localList) {
			assertTrue("workrecord <" + _c.getId() + "> should be listed", _remoteListIds.contains(_c.getId()));
		}
		for (WorkRecordModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(WorkRecordModel.class);
		}
		for (WorkRecordModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}
		
	@Test
	public void testWorkRecordCreate() {
		// new("PID1", "RID1", _date1, 10, 10, "RATEID1", true, "MY_COMMENT1") -> _c1
		Date _date1 = new Date(1000);
		WorkRecordModel _c1 = new WorkRecordModel("PID1", "RID1", _date1, 10, 100, "RATEID1", true, "MY_COMMENT1");
		// new("PID2", "RID2", _date2, 20, 20, "RATEID2", true, "MY_COMMENT2") -> _c2
		Date _date2 = new Date(2000);
		WorkRecordModel _c2 = new WorkRecordModel("PID2", "RID2", _date2, 20, 200, "RATEID2", true, "MY_COMMENT2");
		
		// create(_c1)  -> _c3
		Response _response = webclient.post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _c3 = _response.readEntity(WorkRecordModel.class);

		// create(_c2) -> _c4
		_response = webclient.post(_c2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _c4 = _response.readEntity(WorkRecordModel.class);		
		assertNotNull("ID should be set", _c3.getId());
		assertNotNull("ID should be set", _c4.getId());
		assertThat(_c4.getId(), not(equalTo(_c3.getId())));

		// examine _c3
		assertEquals("projectId should be set correctly", "PID1", _c3.getProjectId());
		assertEquals("resourceId should be set correctly", "RID1", _c3.getResourceId());
		assertEquals("rateId should be set correctly", "RATEID1", _c3.getRateId());
		assertEquals("the startAt Date should be set correctly", _date1, _c3.getStartAt());
		assertEquals("durationHours should be set correctly", 10, _c3.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 100, _c3.getDurationMinutes());
		assertEquals("comment should be set correctly", "MY_COMMENT1", _c3.getComment());
		assertEquals("isBillable should be set correctly", true, _c3.isBillable());			
		
		// examine _c4
		assertEquals("projectId should be set correctly", "PID2", _c4.getProjectId());
		assertEquals("resourceId should be set correctly", "RID2", _c4.getResourceId());
		assertEquals("rateId should be set correctly", "RATEID2", _c4.getRateId());
		assertEquals("the startAt Date should be set correctly", _date2, _c4.getStartAt());
		assertEquals("durationHours should be set correctly", 20, _c4.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 200, _c4.getDurationMinutes());
		assertEquals("comment should be set correctly", "MY_COMMENT2", _c4.getComment());
		assertEquals("isBillable should be set correctly", true, _c4.isBillable());			
		
		// delete(_c1) -> METHOD_NOT_ALLOWED  (_c1.getId() = null)
		_response = webclient.replacePath(_c1.getId()).delete();
		assertEquals("delete() should return with status METHOD_NOT_ALLOWED", Status.METHOD_NOT_ALLOWED.getStatusCode(), _response.getStatus());

		// delete(_c2) -> METHOD_NOT_ALLOWED  (_c2.getId() = null)
		_response = webclient.replacePath(_c2.getId()).delete();
		assertEquals("delete() should return with status METHOD_NOT_ALLOWED", Status.METHOD_NOT_ALLOWED.getStatusCode(), _response.getStatus());

		// delete(_c3) -> NO_CONTENT
		_response = webclient.replacePath(_c3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_c4) -> NO_CONTENT
		_response = webclient.replacePath(_c4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordDoubleCreate(
	) {
		// create(new()) -> _c
		Response _response = webclient.replacePath("/").post(new WorkRecordModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _c = _response.readEntity(WorkRecordModel.class);
		assertNotNull("ID should be set:", _c.getId());		
		
		// create(_c) -> CONFLICT
		_response = webclient.replacePath("/").post(_c);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_c) -> NO_CONTENT
		_response = webclient.replacePath(_c.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testWorkRecordRead(
	) {
		ArrayList<WorkRecordModel> _localList = new ArrayList<WorkRecordModel>();
		Response _response = null;
		webclient.replacePath("/");
		for (int i = 0; i < LIMIT; i++) {
			_response = webclient.post(new WorkRecordModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(WorkRecordModel.class));
		}
	
		// test read on each local element
		for (WorkRecordModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(WorkRecordModel.class);
		}

		// test read on each listed element
		_response = webclient.replacePath("/").get();
		List<WorkRecordModel> _remoteList = new ArrayList<WorkRecordModel>(webclient.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		WorkRecordModel _tmpObj = null;
		for (WorkRecordModel _c : _remoteList) {
			_response = webclient.replacePath(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(WorkRecordModel.class);
			assertEquals("ID should be unchanged when reading a workrecord", _c.getId(), _tmpObj.getId());						
		}

		// TODO: "reading a workrecord with ID = null should fail" -> ValidationException
		// TODO: "reading a non-existing workrecord should fail"

		for (WorkRecordModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testWorkRecordMultiRead(
	) {
		// new() -> _c1
		WorkRecordModel _c1 = new WorkRecordModel();
		
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		WorkRecordModel _c2 = _response.readEntity(WorkRecordModel.class);

		// read(_c2) -> _c3
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _c3 = _response.readEntity(WorkRecordModel.class);
		assertEquals("ID should be unchanged after read", _c2.getId(), _c3.getId());		

		// read(_c2) -> _c4
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _c4 = _response.readEntity(WorkRecordModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same", _c4.getId(), _c3.getId());
		assertEquals("projectId should be the same", _c4.getProjectId(), _c3.getProjectId());
		assertEquals("resourceId should be the same", _c4.getResourceId(), _c3.getResourceId());
		assertEquals("rateId should be the same", _c4.getRateId(), _c3.getRateId());
		assertEquals("the startAt Date should be the same", _c4.getStartAt(), _c3.getStartAt());
		assertEquals("durationHours should be the same", _c4.getDurationHours(), _c3.getDurationHours());
		assertEquals("durationMinutes should be the same", _c4.getDurationMinutes(), _c3.getDurationMinutes());
		assertEquals("comment should be the same", _c4.getComment(), _c3.getComment());
		assertEquals("isBillable should be the same", _c4.isBillable(), _c3.isBillable());		
				
		assertEquals("ID should be the same", _c2.getId(), _c3.getId());
		assertEquals("projectId should be the same", _c2.getProjectId(), _c3.getProjectId());
		assertEquals("resourceId should be the same", _c2.getResourceId(), _c3.getResourceId());
		assertEquals("rateId should be the same", _c2.getRateId(), _c3.getRateId());
		assertEquals("the startAt Date should be the same", _c2.getStartAt(), _c3.getStartAt());
		assertEquals("durationHours should be the same", _c2.getDurationHours(), _c3.getDurationHours());
		assertEquals("durationMinutes should be the same", _c2.getDurationMinutes(), _c3.getDurationMinutes());
		assertEquals("comment should be the same", _c2.getComment(), _c3.getComment());
		assertEquals("isBillable should be the same", _c2.isBillable(), _c3.isBillable());		
		
		// delete(_c2)
		_response = webclient.replacePath(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordUpdate() {
		// new() -> _c1
		WorkRecordModel _c1 = new WorkRecordModel();
		
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		WorkRecordModel _c2 = _response.readEntity(WorkRecordModel.class);
		
		// change the attributes
		// update(_c2) -> _c3
		_c2.setProjectId("PID1");
		_c2.setResourceId("RID1");
		_c2.setRateId("RATEID1");
		Date _date1 = new Date(1000);
		_c2.setStartAt(_date1);
		_c2.setDurationHours(10);
		_c2.setDurationMinutes(100);
		_c2.setComment("MY_COMMENT1");
		_c2.setBillable(true);
		
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_c2.getId()).put(_c2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _c3 = _response.readEntity(WorkRecordModel.class);

		assertNotNull("ID should be set", _c3.getId());
		assertEquals("ID should be unchanged", _c2.getId(), _c3.getId());
		assertEquals("projectId should be set correctly", "PID1", _c3.getProjectId());
		assertEquals("resourceId should be set correctly", "RID1", _c3.getResourceId());
		assertEquals("rateId should be set correctly", "RATEID1", _c3.getRateId());
		assertEquals("the startAt Date should be set correctly", _date1, _c3.getStartAt());
		assertEquals("durationHours should be set correctly", 10, _c3.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 100, _c3.getDurationMinutes());
		assertEquals("comment should be set correctly", "MY_COMMENT1", _c3.getComment());
		assertEquals("isBillable should be set correctly", true, _c3.isBillable());			

		// reset the attributes
		// update(_c2) -> _c4
		_c2.setProjectId("PID2");
		_c2.setResourceId("RID2");
		_c2.setRateId("RATEID2");
		Date _date2 = new Date(2000);
		_c2.setStartAt(_date2);
		_c2.setDurationHours(20);
		_c2.setDurationMinutes(200);
		_c2.setComment("MY_COMMENT2");
		_c2.setBillable(false);

		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_c2.getId()).put(_c2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _c4 = _response.readEntity(WorkRecordModel.class);

		assertNotNull("ID should be set", _c4.getId());
		assertEquals("ID should be unchanged", _c2.getId(), _c4.getId());	
		assertEquals("projectId should be set correctly", "PID2", _c4.getProjectId());
		assertEquals("resourceId should be set correctly", "RID2", _c4.getResourceId());
		assertEquals("rateId should be set correctly", "RATEID2", _c4.getRateId());
		assertEquals("the startAt Date should be set correctly", _date2, _c4.getStartAt());
		assertEquals("durationHours should be set correctly", 20, _c4.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 200, _c4.getDurationMinutes());
		assertEquals("comment should be set correctly", "MY_COMMENT2", _c4.getComment());
		assertEquals("isBillable should be set correctly", false, _c4.isBillable());			
		
		_response = webclient.replacePath(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordDelete(
	) {
		// new() -> _c0
		WorkRecordModel _c0 = new WorkRecordModel();
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").post(_c0);
		WorkRecordModel _c1 = _response.readEntity(WorkRecordModel.class);
		
		// read(_c0) -> _tmpObj
		_response = webclient.replacePath(_c0.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _tmpObj = _response.readEntity(WorkRecordModel.class);
		assertEquals("ID should be unchanged when reading a workrecord (remote):", _c0.getId(), _tmpObj.getId());						

		// read(_c1) -> _tmpObj
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		_tmpObj = _response.readEntity(WorkRecordModel.class);
		assertEquals("ID should be unchanged when reading a workrecord (remote):", _c1.getId(), _tmpObj.getId());						
		
		// delete(_c1) -> OK
		_response = webclient.replacePath(_c1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordDoubleDelete(
	) {
		// new() -> _c0
		WorkRecordModel _c0 = new WorkRecordModel();
		
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").post(_c0);
		WorkRecordModel _c1 = _response.readEntity(WorkRecordModel.class);

		// read(_c1) -> OK
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_c1) -> OK
		_response = webclient.replacePath(_c1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _c1 -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _c1 -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
}
