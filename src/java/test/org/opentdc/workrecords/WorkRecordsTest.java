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
	private Date date;

	@Before
	public void initializeTests(
	) {
		initializeTest(API, WorkRecordsService.class);
		date = new Date();
	}
	
	/********************************** workrecord attributes tests *********************************/	
	@Test
	public void testWorkRecordModelEmptyConstructor() {
		// new() -> _c
		WorkRecordModel _c = new WorkRecordModel();
		assertNull("id should not be set by empty constructor", _c.getId());
		assertNull("companyId should not be set by empty constructor", _c.getCompanyId());
		assertNull("companyTitle should not be set by empty constructor", _c.getCompanyTitle());
		assertNull("projectId should not be set by empty constructor", _c.getProjectId());
		assertNull("projectTitle should not be set by empty constructor", _c.getProjectTitle());
		assertNull("resourceId should not be set by empty constructor", _c.getResourceId());
		assertNull("rateId should not be set by empty constructor", _c.getRateId());
		assertNull("startAt should not be set by empty constructor", _c.getStartAt());
		assertEquals("durationHours should be set on default initial value", 1, _c.getDurationHours());
		assertEquals("durationMinutes should be set on default initial value", 30, _c.getDurationMinutes());
		assertNull("comment should not be set by empty constructor", _c.getComment());
		assertEquals("isBillable should be set on default initial value", true, _c.isBillable());
	}

	@Test
	public void testWorkRecordModelConstructor() {		
		// new("CID1", "CTITLE1", "PID1", "PTITLE1", "RID1", date, 3, 45, "RATEID1", false, "MY_COMMENT1") -> _c
		WorkRecordModel _c = createWorkRecord(1, date, 3, 45, false);
		assertNull("id should not be set by constructor", _c.getId());
		assertEquals("companyId should be set by constructor", "CID1", _c.getCompanyId());
		assertEquals("companyTitle should be set by constructor", "CTITLE1", _c.getCompanyTitle());
		assertEquals("projectId should be set by constructor", "PID1", _c.getProjectId());
		assertEquals("projectTitle should be set by constructor", "PTITLE1", _c.getProjectTitle());
		assertEquals("resourceId should be set by constructor", "RID1", _c.getResourceId());
		assertEquals("rateId should be set by constructor", "RATEID1", _c.getRateId());
		assertEquals("startAt should be set by constructor", date.toString(), _c.getStartAt().toString());
		assertEquals("durationHours should be set by constructor", 3, _c.getDurationHours());
		assertEquals("durationMinutes should be set by constructor", 45, _c.getDurationMinutes());
		assertEquals("comment should be set by constructor", "MY_COMMENT1", _c.getComment());
		assertEquals("isBillable should be set by constructor", false, _c.isBillable());
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
	public void testCompanyIdAttributeChange() {
		// new() -> _c -> _c.setCompanyId()
		WorkRecordModel _c = new WorkRecordModel();
		assertNull("companyId should not be set by empty constructor", _c.getCompanyId());
		_c.setCompanyId("CID");
		assertEquals("companyId should have changed:", "CID", _c.getCompanyId());
	}
	
	@Test
	public void testCompanyTitleAttributeChange() {
		// new() -> _c -> _c.setCompanyTitle()
		WorkRecordModel _c = new WorkRecordModel();
		assertNull("CompanyTitle should not be set by empty constructor", _c.getCompanyTitle());
		_c.setCompanyTitle("CTITLE");
		assertEquals("CompanyTitle should have changed:", "CTITLE", _c.getCompanyTitle());
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
	public void testProjectTitleAttributeChange() {
		// new() -> _c -> _c.setProjectTitle()
		WorkRecordModel _c = new WorkRecordModel();
		assertNull("projectTitle should not be set by empty constructor", _c.getProjectTitle());
		_c.setProjectTitle("PTITLE");
		assertEquals("projectTitle should have changed:", "PTITLE", _c.getProjectTitle());
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
		assertEquals("durationHours should be initialized to default value by empty constructor", 1, _c.getDurationHours());
		_c.setDurationHours(3);
		assertEquals("durationHours should have changed:", 3, _c.getDurationHours());
	}
	
	@Test
	public void testDurationMinutesAttributeChange() {
		// new() -> _c -> _c.setDurationMinutes()
		WorkRecordModel _c = new WorkRecordModel();
		assertEquals("durationMinutes should be initialized to default value by empty constructor", 30, _c.getDurationMinutes());
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
		assertEquals("isBillable should be initialized to default value by empty constructor", true, _c.isBillable());
		_c.setBillable(false);
		assertEquals("isBillable should have changed:", false, _c.isBillable());
	}
	
	@Test
	public void testWorkRecordCreatedBy() {
		// new() -> _o -> _o.setCreatedBy()
		WorkRecordModel _o = new WorkRecordModel();
		assertNull("createdBy should not be set by empty constructor", _o.getCreatedBy());
		_o.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _o.getCreatedBy());	
	}
	
	@Test
	public void testWorkRecordCreatedAt() {
		// new() -> _o -> _o.setCreatedAt()
		WorkRecordModel _o = new WorkRecordModel();
		assertNull("createdAt should not be set by empty constructor", _o.getCreatedAt());
		_o.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _o.getCreatedAt());
	}
		
	@Test
	public void testWorkRecordModifiedBy() {
		// new() -> _o -> _o.setModifiedBy()
		WorkRecordModel _o = new WorkRecordModel();
		assertNull("modifiedBy should not be set by empty constructor", _o.getModifiedBy());
		_o.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _o.getModifiedBy());	
	}
	
	@Test
	public void testWorkRecordModifiedAt() {
		// new() -> _o -> _o.setModifiedAt()
		WorkRecordModel _o = new WorkRecordModel();
		assertNull("modifiedAt should not be set by empty constructor", _o.getModifiedAt());
		_o.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _o.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testWorkRecordCreateReadDeleteWithEmptyConstructor() {
		// new() -> _c1
		WorkRecordModel _c1 = new WorkRecordModel();
		assertNull("id should not be set by empty constructor", _c1.getId());
		assertNull("companyId should not be set by empty constructor", _c1.getCompanyId());
		assertNull("companyTitle should not be set by empty constructor", _c1.getCompanyTitle());
		assertNull("projectId should not be set by empty constructor", _c1.getProjectId());
		assertNull("projectTitle should not be set by empty constructor", _c1.getProjectTitle());
		assertNull("resourceId should not be set by empty constructor", _c1.getResourceId());
		assertNull("rateId should not be set by empty constructor", _c1.getRateId());
		assertNull("startAt should not be set by empty constructor", _c1.getStartAt());
		assertEquals("durationHours should be set on default initial value", 1, _c1.getDurationHours());
		assertEquals("durationMinutes should be set on default initial value", 30, _c1.getDurationMinutes());
		assertNull("comment should not be set by empty constructor", _c1.getComment());
		assertEquals("isBillable should be set on default initial value", true, _c1.isBillable());
		
		// create(_c1) -> BAD_REQUEST (because of empty companyId)
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());

		// create(_c1).setCompanyId("CID") -> BAD_REQUEST (because of empty companyTitle)
		_c1.setCompanyId("CID");
		_response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());

		// create(_c1).setCompanyTitle("CTITLE") -> BAD_REQUEST (because of empty projectId)
		_c1.setCompanyTitle("CTITLE");
		_response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		
		// create(_c1).setProjectId("PID") -> BAD_REQUEST (because of empty projectTitle)
		_c1.setProjectId("PID");
		_response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());

		// create(_c1).setProjectTitle("PTITLE") -> BAD_REQUEST (because of empty resourceId)
		_c1.setProjectTitle("PTITLE");
		_response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());

		// create(_c1).setResourceId("RID") -> BAD_REQUEST (because of empty startAt)
		_c1.setResourceId("RID");
		_response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());

		// create(_c1).setStartAt(new Date()) -> _c2
		Date _date = new Date();
		_c1.setStartAt(_date);
		_response = webclient.replacePath("/").post(_c1);		
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _c2 = _response.readEntity(WorkRecordModel.class);
		
		// validate _c1
		assertNull("create() should not change the id of the local object", _c1.getId());
		assertEquals("create() should not change the companyId of the local object", "CID", _c1.getCompanyId());
		assertEquals("create() should not change the companyTitle of the local object", "CTITLE", _c1.getCompanyTitle());
		assertEquals("create() should not change the projectId of the local object", "PID", _c1.getProjectId());
		assertEquals("create() should not change the projectTitle of the local object", "PTITLE", _c1.getProjectTitle());
		assertEquals("create() should not change the resourceId of the local object", "RID", _c1.getResourceId());
		assertNull("create() should not change the rateId of the local object", _c1.getRateId());
		assertEquals("create() should not change the startAt Date of the local object", _date.toString(), _c1.getStartAt().toString());
		assertEquals("create() should not change durationHours on the local object", 1, _c1.getDurationHours());
		assertEquals("create() should not change durationMinutes on the local object", 30, _c1.getDurationMinutes());
		assertNull("create() should not change comment on the local object", _c1.getComment());
		assertEquals("create() should not change isBillable on the local object", true, _c1.isBillable());
		
		// validate _c2
		assertNotNull("create() should set a valid id on the remote object returned", _c2.getId());
		assertEquals("create() should not change the companyId", "CID", _c2.getCompanyId());
		assertEquals("create() should not change the companyTitle", "CTITLE", _c2.getCompanyTitle());
		assertEquals("create() should not change the projectId", "PID", _c2.getProjectId());
		assertEquals("create() should not change the projectTitle", "PTITLE", _c2.getProjectTitle());
		assertEquals("create() should not change the resourceId", "RID", _c2.getResourceId());
		assertNull("create() should not change the rateId", _c2.getRateId());
		assertEquals("create() should not change the startAt Date", _date.toString(), _c2.getStartAt().toString());
		assertEquals("create() should not change durationHours", 1, _c2.getDurationHours());
		assertEquals("create() should not change durationMinutes", 30, _c2.getDurationMinutes());
		assertNull("create() should not change comment", _c2.getComment());
		assertEquals("create() should not change isBillable", true, _c2.isBillable());

		// read(_c2) -> _c3
		_response = webclient.replacePath("/").path(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _c3 = _response.readEntity(WorkRecordModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("companyId of returned object should be unchanged after remote create", _c2.getCompanyId(), _c3.getCompanyId());
		assertEquals("companyTitle of returned object should be unchanged after remote create", _c2.getCompanyTitle(), _c3.getCompanyTitle());
		assertEquals("projectId of returned object should be unchanged after remote create", _c2.getProjectId(), _c3.getProjectId());
		assertEquals("projectTitle of returned object should be unchanged after remote create", _c2.getProjectTitle(), _c3.getProjectTitle());
		assertEquals("resourceId of returned object should be unchanged after remote create", _c2.getResourceId(), _c3.getResourceId());
		assertEquals("rateId of returned object should be unchanged after remote create", _c2.getRateId(), _c3.getRateId());
		assertEquals("startAt of returned object should be unchanged after remote create", _c2.getStartAt(), _c3.getStartAt());
		assertEquals("durationHours of returned object should be unchanged after remote create", _c2.getDurationHours(), _c3.getDurationHours());
		assertEquals("durationMinutes of returned object should be unchanged after remote create", _c2.getDurationMinutes(), _c3.getDurationMinutes());
		assertEquals("comment of returned object should be unchanged after remote create", _c2.getComment(), _c3.getComment());
		assertEquals("isBillable of returned object should be unchanged after remote create", _c2.isBillable(), _c3.isBillable());

		// delete(_c3)
		_response = webclient.replacePath("/").path(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordCreateReadDelete() {
		// new("MY_TITLE", "MY_DESC") -> _wrm1
		WorkRecordModel _wrm1 = createWorkRecord(1, date, 4, 20, true);
		
		// validate _wrm1
		assertNull("id should not be set by constructor", _wrm1.getId());
		assertEquals("companyId should be set by constructor", "CID1", _wrm1.getCompanyId());
		assertEquals("companyTitle should be set by constructor", "CTITLE1", _wrm1.getCompanyTitle());
		assertEquals("projectId should be set by constructor", "PID1", _wrm1.getProjectId());
		assertEquals("projectTitle should be set by constructor", "PTITLE1", _wrm1.getProjectTitle());
		assertEquals("resourceId should be set by constructor", "RID1", _wrm1.getResourceId());
		assertEquals("rateId should be set by constructor", "RATEID1", _wrm1.getRateId());
		assertEquals("startAt should be set by constructor", date, _wrm1.getStartAt());
		assertEquals("durationHours should be set by constructor", 4, _wrm1.getDurationHours());
		assertEquals("durationMinutes should be set by constructor", 20, _wrm1.getDurationMinutes());
		assertEquals("comment should be set by constructor", "MY_COMMENT1", _wrm1.getComment());
		assertEquals("isBillable should be set by constructor", true, _wrm1.isBillable());
		
		// create(_wrm1) -> _wrm2
		Response _response = webclient.replacePath("/").post(_wrm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm2 = _response.readEntity(WorkRecordModel.class);
		
		// validate _wrm1 (after create())
		assertNull("create() should not change the id of the local object", _wrm1.getId());
		assertEquals("create() should not change the companyId of the local object", "CID1", _wrm1.getCompanyId());
		assertEquals("create() should not change the companyTitle of the local object", "CTITLE1", _wrm1.getCompanyTitle());
		assertEquals("create() should not change the projectId of the local object", "PID1", _wrm1.getProjectId());
		assertEquals("create() should not change the projectTitle of the local object", "PTITLE1", _wrm1.getProjectTitle());
		assertEquals("create() should not change the resourceId of the local object", "RID1", _wrm1.getResourceId());
		assertEquals("create() should not change the rateId of the local object", "RATEID1", _wrm1.getRateId());
		assertEquals("create() should not change the startAt Date of the local object", date, _wrm1.getStartAt());
		assertEquals("create() should not change durationHours on the local object", 4, _wrm1.getDurationHours());
		assertEquals("create() should not change durationMinutes on the local object", 20, _wrm1.getDurationMinutes());
		assertEquals("create() should not change comment on the local object", "MY_COMMENT1", _wrm1.getComment());
		assertEquals("create() should not change isBillable on the local object", true, _wrm1.isBillable());
		
		// validate _wrm2
		assertNotNull("id of returned object should be set", _wrm2.getId());
		assertEquals("companyId of returned object should be unchanged after remote create", "CID1", _wrm2.getCompanyId());
		assertEquals("companyTitle of returned object should be unchanged after remote create", "CTITLE1", _wrm2.getCompanyTitle());
		assertEquals("projectId of returned object should be unchanged after remote create", "PID1", _wrm2.getProjectId());
		assertEquals("projectTitle of returned object should be unchanged after remote create", "PTITLE1", _wrm2.getProjectTitle());
		assertEquals("resourceId of returned object should be unchanged after remote create", "RID1", _wrm2.getResourceId());
		assertEquals("rateId of returned object should be unchanged after remote create", "RATEID1", _wrm2.getRateId());
		assertEquals("create() should not change the startAt Date of the local object", date, _wrm2.getStartAt());
		assertEquals("durationHours of returned object should be unchanged", 4, _wrm2.getDurationHours());
		assertEquals("durationMinutes of returned object should be unchanged", 20, _wrm2.getDurationMinutes());
		assertEquals("comment of returned object should be unchanged", "MY_COMMENT1", _wrm2.getComment());
		assertEquals("create() should not change isBillable", true, _wrm2.isBillable());

		// read(_wrm2)  -> _wrm3
		_response = webclient.replacePath("/").path(_wrm2.getId()).get();
		assertEquals("read(" + _wrm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm3 = _response.readEntity(WorkRecordModel.class);
		assertEquals("id of returned object should be the same", _wrm2.getId(), _wrm3.getId());
		assertEquals("companyId of returned object should be the same", _wrm2.getCompanyId(), _wrm3.getCompanyId());
		assertEquals("companyTitle of returned object should be the same", _wrm2.getCompanyTitle(), _wrm3.getCompanyTitle());
		assertEquals("projectId of returned object should be the same", _wrm2.getProjectId(), _wrm3.getProjectId());
		assertEquals("projectTitle of returned object should be the same", _wrm2.getProjectTitle(), _wrm3.getProjectTitle());
		assertEquals("resourceId of returned object should be the same", _wrm2.getResourceId(), _wrm3.getResourceId());
		assertEquals("rateId of returned object should be the same", _wrm2.getRateId(), _wrm3.getRateId());
		assertEquals("the startAt Date should be the same", _wrm2.getStartAt(), _wrm3.getStartAt());
		assertEquals("durationHours of returned object should be the same", _wrm2.getDurationHours(), _wrm3.getDurationHours());
		assertEquals("durationMinutes of returned object should be the same", _wrm2.getDurationMinutes(), _wrm3.getDurationMinutes());
		assertEquals("comment of returned object should be the same", _wrm2.getComment(), _wrm3.getComment());
		assertEquals("isBillable should be the same", _wrm2.isBillable(), _wrm3.isBillable());		

		// delete(_wrm3)
		_response = webclient.replacePath("/").path(_wrm3.getId()).delete();
		assertEquals("delete(" + _wrm3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateWorkRecordWithClientSideId() {
		// new(1) -> _wrm1 -> _wrm1.setId()
		WorkRecordModel _wrm1 = createWorkRecord(1, date, 4, 20, true);
		_wrm1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _wrm1.getId());
		// create(_c1) -> BAD_REQUEST
		Response _response = webclient.replacePath("/").post(_wrm1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateWorkRecordWithDuplicateId() {
		// create(new(1)) -> _wrm2
		WorkRecordModel _wrm1 = createWorkRecord(1, date, 4, 20, true);
		Response _response = webclient.replacePath("/").post(_wrm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm2 = _response.readEntity(WorkRecordModel.class);

		// new(3) -> _wrm3 -> _wrm3.setId(_wrm2.getId())
		WorkRecordModel _wrm3 = createWorkRecord(3, date, 1, 30, true);
		_wrm3.setId(_wrm2.getId());		// wrongly create a 2nd WorkRecordModel object with the same ID
		
		// create(_wrm3) -> CONFLICT
		_response = webclient.replacePath("/").post(_wrm3);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_wrm2)
		_response = webclient.replacePath("/").path(_wrm2.getId()).delete();
		assertEquals("delete(" + _wrm2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordList(
	) {		
		ArrayList<WorkRecordModel> _localList = new ArrayList<WorkRecordModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			// create(new(i)) -> _localList
			_response = webclient.replacePath("/").post(createWorkRecord(i, date, i, i, true));
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
			_response = webclient.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(WorkRecordModel.class);
		}
		for (WorkRecordModel _c : _localList) {
			_response = webclient.replacePath("/").path(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}
		
	@Test
	public void testWorkRecordCreate() {
		// new(1) -> _wrm1
		Date _date1 = new Date(1000);
		WorkRecordModel _wrm1 = createWorkRecord(1, _date1, 1, 10, true);
		// new(2) -> _wrm2
		Date _date2 = new Date(2000);
		WorkRecordModel _wrm2 = createWorkRecord(2, _date2, 2, 20, false);
		
		// create(_wrm1)  -> _wrm3
		Response _response = webclient.replacePath("/").post(_wrm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm3 = _response.readEntity(WorkRecordModel.class);

		// create(_wrm2) -> _wrm4
		_response = webclient.replacePath("/").post(_wrm2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm4 = _response.readEntity(WorkRecordModel.class);		
		assertNotNull("ID should be set", _wrm3.getId());
		assertNotNull("ID should be set", _wrm4.getId());
		assertThat(_wrm4.getId(), not(equalTo(_wrm3.getId())));

		// examine _c3
		assertEquals("companyId should be set correctly", "CID1", _wrm3.getCompanyId());
		assertEquals("companyTitle should be set correctly", "CTITLE1", _wrm3.getCompanyTitle());
		assertEquals("projectId should be set correctly", "PID1", _wrm3.getProjectId());
		assertEquals("projectTitle should be set correctly", "PTITLE1", _wrm3.getProjectTitle());
		assertEquals("resourceId should be set correctly", "RID1", _wrm3.getResourceId());
		assertEquals("rateId should be set correctly", "RATEID1", _wrm3.getRateId());
		assertEquals("the startAt Date should be set correctly", _date1, _wrm3.getStartAt());
		assertEquals("durationHours should be set correctly", 1, _wrm3.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 10, _wrm3.getDurationMinutes());
		assertEquals("comment should be set correctly", "MY_COMMENT1", _wrm3.getComment());
		assertEquals("isBillable should be set correctly", true, _wrm3.isBillable());			
		
		// examine _c4
		assertEquals("companyId should be set correctly", "CID2", _wrm4.getCompanyId());
		assertEquals("companyTitle should be set correctly", "CTITLE2", _wrm4.getCompanyTitle());
		assertEquals("projectId should be set correctly", "PID2", _wrm4.getProjectId());
		assertEquals("projectTitle should be set correctly", "PTITLE2", _wrm4.getProjectTitle());
		assertEquals("resourceId should be set correctly", "RID2", _wrm4.getResourceId());
		assertEquals("rateId should be set correctly", "RATEID2", _wrm4.getRateId());
		assertEquals("the startAt Date should be set correctly", _date2, _wrm4.getStartAt());
		assertEquals("durationHours should be set correctly", 2, _wrm4.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 20, _wrm4.getDurationMinutes());
		assertEquals("comment should be set correctly", "MY_COMMENT2", _wrm4.getComment());
		assertEquals("isBillable should be set correctly", false, _wrm4.isBillable());			
		
		// delete(_c3) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_wrm3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_c4) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_wrm4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordDoubleCreate(
	) {
		// create(new()) -> _wrm1
		Response _response = webclient.replacePath("/").post(createWorkRecord(1, date, 1, 10, true));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm1 = _response.readEntity(WorkRecordModel.class);
		assertNotNull("ID should be set:", _wrm1.getId());		
		
		// create(_wrm1) -> CONFLICT
		_response = webclient.replacePath("/").post(_wrm1);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_wrm1) -> NO_CONTENT
		_response = webclient.replacePath(_wrm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testWorkRecordRead(
	) {
		ArrayList<WorkRecordModel> _localList = new ArrayList<WorkRecordModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			_response = webclient.replacePath("/").post(createWorkRecord(i, date, i, i*10, true));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(WorkRecordModel.class));
		}
	
		// test read on each local element
		for (WorkRecordModel _c : _localList) {
			_response = webclient.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(WorkRecordModel.class);
		}

		// test read on each listed element
		_response = webclient.replacePath("/").get();
		List<WorkRecordModel> _remoteList = new ArrayList<WorkRecordModel>(webclient.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		WorkRecordModel _tmpObj = null;
		for (WorkRecordModel _c : _remoteList) {
			_response = webclient.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(WorkRecordModel.class);
			assertEquals("ID should be unchanged when reading a workrecord", _c.getId(), _tmpObj.getId());						
		}

		for (WorkRecordModel _c : _localList) {
			_response = webclient.replacePath("/").path(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testWorkRecordMultiRead(
	) {
		// new() -> _wrm1
		WorkRecordModel _wrm1 = createWorkRecord(1, date, 1, 10, true);
		
		// create(_wrm1) -> _wrm2
		Response _response = webclient.replacePath("/").post(_wrm1);
		WorkRecordModel _wrm2 = _response.readEntity(WorkRecordModel.class);

		// read(_wrm2) -> _wrm3
		_response = webclient.replacePath("/").path(_wrm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm3 = _response.readEntity(WorkRecordModel.class);
		assertEquals("ID should be unchanged after read", _wrm2.getId(), _wrm3.getId());		

		// read(_wrm2) -> _wrm4
		_response = webclient.replacePath("/").path(_wrm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm4 = _response.readEntity(WorkRecordModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same", _wrm4.getId(), _wrm3.getId());
		assertEquals("companyId should be the same", _wrm4.getCompanyId(), _wrm3.getCompanyId());
		assertEquals("companyTitle should be the same", _wrm4.getCompanyTitle(), _wrm3.getCompanyTitle());
		assertEquals("projectId should be the same", _wrm4.getProjectId(), _wrm3.getProjectId());
		assertEquals("projectTitle should be the same", _wrm4.getProjectTitle(), _wrm3.getProjectTitle());
		assertEquals("resourceId should be the same", _wrm4.getResourceId(), _wrm3.getResourceId());
		assertEquals("rateId should be the same", _wrm4.getRateId(), _wrm3.getRateId());
		assertEquals("the startAt Date should be the same", _wrm4.getStartAt(), _wrm3.getStartAt());
		assertEquals("durationHours should be the same", _wrm4.getDurationHours(), _wrm3.getDurationHours());
		assertEquals("durationMinutes should be the same", _wrm4.getDurationMinutes(), _wrm3.getDurationMinutes());
		assertEquals("comment should be the same", _wrm4.getComment(), _wrm3.getComment());
		assertEquals("isBillable should be the same", _wrm4.isBillable(), _wrm3.isBillable());		
				
		assertEquals("ID should be the same", _wrm2.getId(), _wrm3.getId());
		assertEquals("companyId should be the same", _wrm2.getCompanyId(), _wrm3.getCompanyId());
		assertEquals("companyTitle should be the same", _wrm2.getCompanyTitle(), _wrm3.getCompanyTitle());
		assertEquals("projectId should be the same", _wrm2.getProjectId(), _wrm3.getProjectId());
		assertEquals("projectTitle should be the same", _wrm2.getProjectTitle(), _wrm3.getProjectTitle());
		assertEquals("resourceId should be the same", _wrm2.getResourceId(), _wrm3.getResourceId());
		assertEquals("rateId should be the same", _wrm2.getRateId(), _wrm3.getRateId());
		assertEquals("the startAt Date should be the same", _wrm2.getStartAt(), _wrm3.getStartAt());
		assertEquals("durationHours should be the same", _wrm2.getDurationHours(), _wrm3.getDurationHours());
		assertEquals("durationMinutes should be the same", _wrm2.getDurationMinutes(), _wrm3.getDurationMinutes());
		assertEquals("comment should be the same", _wrm2.getComment(), _wrm3.getComment());
		assertEquals("isBillable should be the same", _wrm2.isBillable(), _wrm3.isBillable());		
		
		// delete(_wrm2)
		_response = webclient.replacePath("/").path(_wrm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordUpdate() {
		// new() -> _wrm1
		WorkRecordModel _wrm1 = createWorkRecord(1, date, 1, 10, true);
		
		// create(_wrm1) -> _wrm2
		Response _response = webclient.replacePath("/").post(_wrm1);
		WorkRecordModel _wrm2 = _response.readEntity(WorkRecordModel.class);
		
		// change the attributes
		// update(_wrm2) -> _wrm3
		_wrm2.setCompanyId("CID2");
		_wrm2.setCompanyTitle("CTITLE2");
		_wrm2.setProjectId("PID2");
		_wrm2.setProjectTitle("PTITLE2");
		_wrm2.setResourceId("RID2");
		_wrm2.setRateId("RATEID2");
		Date _date2 = new Date(2000);
		_wrm2.setStartAt(_date2);
		_wrm2.setDurationHours(2);
		_wrm2.setDurationMinutes(20);
		_wrm2.setComment("MY_COMMENT2");
		_wrm2.setBillable(false);
		
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_wrm2.getId()).put(_wrm2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm3 = _response.readEntity(WorkRecordModel.class);

		assertNotNull("ID should be set", _wrm3.getId());
		assertEquals("ID should be unchanged", _wrm2.getId(), _wrm3.getId());
		assertEquals("companyId should be set correctly", "CID2", _wrm3.getCompanyId());
		assertEquals("companyTitle should be set correctly", "CTITLE2", _wrm3.getCompanyTitle());
		assertEquals("projectId should be set correctly", "PID2", _wrm3.getProjectId());
		assertEquals("projectTitle should be set correctly", "PTITLE2", _wrm3.getProjectTitle());
		assertEquals("resourceId should be set correctly", "RID2", _wrm3.getResourceId());
		assertEquals("rateId should be set correctly", "RATEID2", _wrm3.getRateId());
		assertEquals("the startAt Date should be set correctly", _date2, _wrm3.getStartAt());
		assertEquals("durationHours should be set correctly", 2, _wrm3.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 20, _wrm3.getDurationMinutes());
		assertEquals("comment should be set correctly", "MY_COMMENT2", _wrm3.getComment());
		assertEquals("isBillable should be set correctly", false, _wrm3.isBillable());			

		// reset the attributes
		// update(_wrm2) -> _wrm4
		_wrm2.setCompanyId("CID4");
		_wrm2.setCompanyTitle("CTITLE4");
		_wrm2.setProjectId("PID4");
		_wrm2.setProjectTitle("PTITLE4");
		_wrm2.setResourceId("RID4");
		_wrm2.setRateId("RATEID4");
		Date _date4 = new Date(4000);
		_wrm2.setStartAt(_date4);
		_wrm2.setDurationHours(4);
		_wrm2.setDurationMinutes(40);
		_wrm2.setComment("MY_COMMENT4");
		_wrm2.setBillable(true);

		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_wrm2.getId()).put(_wrm2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm4 = _response.readEntity(WorkRecordModel.class);

		assertNotNull("ID should be set", _wrm4.getId());
		assertEquals("ID should be unchanged", _wrm2.getId(), _wrm4.getId());	
		assertEquals("companyId should be set correctly", "CID4", _wrm4.getCompanyId());
		assertEquals("companyTitle should be set correctly", "CTITLE4", _wrm4.getCompanyTitle());
		assertEquals("projectId should be set correctly", "PID4", _wrm4.getProjectId());
		assertEquals("projectTitle should be set correctly", "PTITLE4", _wrm4.getProjectTitle());
		assertEquals("resourceId should be set correctly", "RID4", _wrm4.getResourceId());
		assertEquals("rateId should be set correctly", "RATEID4", _wrm4.getRateId());
		assertEquals("the startAt Date should be set correctly", _date4, _wrm4.getStartAt());
		assertEquals("durationHours should be set correctly", 4, _wrm4.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 40, _wrm4.getDurationMinutes());
		assertEquals("comment should be set correctly", "MY_COMMENT4", _wrm4.getComment());
		assertEquals("isBillable should be set correctly", true, _wrm4.isBillable());			
		
		_response = webclient.replacePath("/").path(_wrm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordDelete(
	) {
		// create(1) -> _wrm1
		Response _response = webclient.replacePath("/").post(createWorkRecord(1, date, 1, 10, true));
		WorkRecordModel _wrm1 = _response.readEntity(WorkRecordModel.class);
		
		// read(_wrm1) -> _wrm2
		_response = webclient.replacePath("/").path(_wrm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm2 = _response.readEntity(WorkRecordModel.class);
		assertEquals("ID should be unchanged when reading a workrecord (remote):", _wrm1.getId(), _wrm2.getId());						
		
		// delete(_wrm1) -> OK
		_response = webclient.replacePath("/").path(_wrm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_wrm1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(_wrm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_wrm1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(_wrm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordDoubleDelete(
	) {
		// create(1) -> _wrm1
		Response _response = webclient.replacePath("/").post(createWorkRecord(1, date, 1, 10, true));
		WorkRecordModel _wrm1 = _response.readEntity(WorkRecordModel.class);

		// read(_wrm1) -> OK
		_response = webclient.replacePath("/").path(_wrm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_wrm1) -> OK
		_response = webclient.replacePath("/").path(_wrm1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_wrm1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(_wrm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _wrm1 -> NOT_FOUND
		_response = webclient.replacePath("/").path(_wrm1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _wrm1 -> NOT_FOUND
		_response = webclient.replacePath("/").path(_wrm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordModifications() {
		// create(1) -> _wrm1
		Response _response = webclient.replacePath("/").post(createWorkRecord(1, date, 1, 10, true));
		WorkRecordModel _wrm1 = _response.readEntity(WorkRecordModel.class);
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _wrm1.getCreatedAt());
		assertNotNull("create() should set createdBy", _wrm1.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _wrm1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _wrm1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _wrm1.getCreatedAt(), _wrm1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _wrm1.getCreatedBy(), _wrm1.getModifiedBy());
		
		// update(_wrm1)  -> _wrm2
		_wrm1.setProjectId("NEW_PROJECTID");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_wrm1.getId()).put(_wrm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm2 = _response.readEntity(WorkRecordModel.class);

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _wrm1.getCreatedAt(), _wrm2.getCreatedAt());
		assertEquals("update() should not change createdBy", _wrm1.getCreatedBy(), _wrm2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		assertThat(_wrm2.getModifiedAt(), not(equalTo(_wrm2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_o2.getModifiedBy(), not(equalTo(_o2.getCreatedBy())));

		// update(o2) with createdBy set on client side -> error
		String _createdBy = _wrm1.getCreatedBy();
		_wrm1.setCreatedBy("MYSELF");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_wrm1.getId()).put(_wrm1);
		assertEquals("update() should return with status BAD_REQUEST", 
				Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_wrm1.setCreatedBy(_createdBy);

		// update(_wrm1) with createdAt set on client side -> error
		Date _d = _wrm1.getCreatedAt();
		_wrm1.setCreatedAt(new Date(1000));
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_wrm1.getId()).put(_wrm1);
		assertEquals("update() should return with status BAD_REQUEST", 
				Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_wrm1.setCreatedAt(_d);

		// update(_wrm1) with modifiedBy/At set on client side -> ignored by server
		_wrm1.setModifiedBy("MYSELF");
		_wrm1.setModifiedAt(new Date(1000));
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_wrm1.getId()).put(_wrm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _o3 = _response.readEntity(WorkRecordModel.class);
		
		// test, that modifiedBy really ignores the client-side value "MYSELF"
		assertThat(_wrm1.getModifiedBy(), not(equalTo(_o3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_wrm1.getModifiedAt(), not(equalTo(_o3.getModifiedAt())));
		
		// delete(_wrm1) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_wrm1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	/********************************** helper methods *********************************/	
	private WorkRecordModel createWorkRecord(int suffix, Date d, int hours, int mins, boolean isBillable) {
		String _buf = new Integer(suffix).toString();
		return new WorkRecordModel(
			"CID" + _buf,
			"CTITLE" + _buf,
			"PID" + _buf,
			"PTITLE" + _buf,
			"RID" + _buf,
			d,
			hours,
			mins,
			"RATEID" + _buf,
			isBillable,
			"MY_COMMENT" + _buf
		);
	}
}
