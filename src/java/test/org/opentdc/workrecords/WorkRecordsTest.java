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

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.resources.ResourceModel;
import org.opentdc.resources.ResourcesService;
import org.opentdc.service.ServiceUtil;
import org.opentdc.workrecords.WorkRecordModel;
import org.opentdc.workrecords.WorkRecordsService;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.WttService;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.ContactTest;
import test.org.opentdc.resources.ResourcesTest;
import test.org.opentdc.wtt.CompanyTest;
import test.org.opentdc.wtt.ProjectTest;

public class WorkRecordsTest extends AbstractTestClient {
	private Date date;
	private WebClient workRecordWC = null;
	private WebClient wttWC = null;
	private WebClient addressbookWC = null;
	private WebClient resourceWC = null;
	private CompanyModel company = null;
	private CompanyModel company2 = null;
	private ProjectModel project = null;
	private ProjectModel project2 = null;
	private AddressbookModel addressbook = null;
	private ResourceModel resource = null;
	private ResourceModel resource2 = null;
	private ContactModel contact = null;

	@Before
	public void initializeTests() {
		date = new Date();
		workRecordWC = createWebClient(ServiceUtil.WORKRECORDS_API_URL, WorkRecordsService.class);
		wttWC = createWebClient(ServiceUtil.WTT_API_URL, WttService.class);
		resourceWC = createWebClient(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);

		addressbook = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName(), Status.OK);
		company = CompanyTest.createCompany(wttWC, addressbookWC, addressbook, this.getClass().getName(), "MY_DESC");
		company2 = CompanyTest.createCompany(wttWC, addressbookWC, addressbook, this.getClass().getName(), "MY_DESC2");
		project = ProjectTest.createProject(wttWC, company.getId(), this.getClass().getName(), "MY_DESC");
		project2 = ProjectTest.createProject(wttWC, company2.getId(), this.getClass().getName(), "MY_DESC2");
		contact = ContactTest.createContact(addressbookWC, addressbook.getId(), "FNAME", "LNAME");
		resource = ResourcesTest.createResource(resourceWC, addressbook, contact, this.getClass().getName() + "1", Status.OK);
		resource2 = ResourcesTest.createResource(resourceWC, addressbook, contact, this.getClass().getName() + "2", Status.OK);
	}
	
	@After
	public void cleanupTest() {
		AddressbookTest.delete(addressbookWC, addressbook.getId(), Status.NO_CONTENT);
		System.out.println("deleted 1 addressbook with 1 contact");
		addressbookWC.close();
		ResourcesTest.cleanup(resourceWC, resource.getId(), this.getClass().getName(), false);
		ResourcesTest.cleanup(resourceWC, resource2.getId(), this.getClass().getName(), true);
		CompanyTest.cleanup(wttWC, company.getId(), this.getClass().getName(), false);
		CompanyTest.cleanup(wttWC, company2.getId(), this.getClass().getName(), true);
		workRecordWC.close();
	}

	/********************************** workrecord attributes tests *********************************/	
	@Test
	public void testEmptyConstructor() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("id should not be set by empty constructor", _wrm.getId());
		assertNull("companyId should not be set by empty constructor", _wrm.getCompanyId());
		assertNull("companyTitle should not be set by empty constructor", _wrm.getCompanyTitle());
		assertNull("projectId should not be set by empty constructor", _wrm.getProjectId());
		assertNull("projectTitle should not be set by empty constructor", _wrm.getProjectTitle());
		assertNull("resourceId should not be set by empty constructor", _wrm.getResourceId());
		assertNull("startAt should not be set by empty constructor", _wrm.getStartAt());
		assertEquals("durationHours should be set on default initial value", 1, _wrm.getDurationHours());
		assertEquals("durationMinutes should be set on default initial value", 30, _wrm.getDurationMinutes());
		assertNull("comment should not be set by empty constructor", _wrm.getComment());
		assertEquals("isBillable should be set on default initial value", true, _wrm.isBillable());
	}

	@Test
	public void testConstructor() {		
		WorkRecordModel _wrm = createWorkRecord(company, project, resource, date, 3, 45, false, "testConstructor1");
		assertNull("id should not be set by constructor", _wrm.getId());
		assertEquals("companyId should be set by constructor", company.getId(), _wrm.getCompanyId());
		assertEquals("companyTitle should be set by constructor", company.getTitle(), _wrm.getCompanyTitle());
		assertEquals("projectId should be set by constructor", project.getId(), _wrm.getProjectId());
		assertEquals("projectTitle should be set by constructor", project.getTitle(), _wrm.getProjectTitle());
		assertEquals("resourceId should be set by constructor", resource.getId(), _wrm.getResourceId());
		assertEquals("startAt should be set by constructor", date.toString(), _wrm.getStartAt().toString());
		assertEquals("durationHours should be set by constructor", 3, _wrm.getDurationHours());
		assertEquals("durationMinutes should be set by constructor", 45, _wrm.getDurationMinutes());
		assertEquals("comment should be set by constructor", "testConstructor1", _wrm.getComment());
		assertEquals("isBillable should be set by constructor", false, _wrm.isBillable());
	}

	@Test
	public void testId() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("id should not be set by constructor", _wrm.getId());
		_wrm.setId("testId");
		assertEquals("id should have changed", "testId", _wrm.getId());
	}

	@Test
	public void testCompanyId() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("companyId should not be set by empty constructor", _wrm.getCompanyId());
		_wrm.setCompanyId("testCompanyId");
		assertEquals("companyId should have changed", "testCompanyId", _wrm.getCompanyId());
	}
	
	@Test
	public void testCompanyTitle() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("CompanyTitle should not be set by empty constructor", _wrm.getCompanyTitle());
		_wrm.setCompanyTitle("testCompanyTitle");
		assertEquals("CompanyTitle should have changed", "testCompanyTitle", _wrm.getCompanyTitle());
	}
	
	@Test
	public void testProjectId() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("projectId should not be set by empty constructor", _wrm.getProjectId());
		_wrm.setProjectId("testProjectId");
		assertEquals("projectId should have changed", "testProjectId", _wrm.getProjectId());
	}
	
	@Test
	public void testProjectTitle() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("projectTitle should not be set by empty constructor", _wrm.getProjectTitle());
		_wrm.setProjectTitle("testProjectTitle");
		assertEquals("projectTitle should have changed", "testProjectTitle", _wrm.getProjectTitle());
	}
	
	@Test
	public void testResourceId() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("resourceId should not be set by empty constructor", _wrm.getResourceId());
		_wrm.setResourceId("testResourceId");
		assertEquals("resourceId should have changed", "testResourceId", _wrm.getResourceId());
	}
			
	@Test
	public void testStartAt() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("startAt should not be set by empty constructor", _wrm.getStartAt());
		Date _d = new Date();
		_wrm.setStartAt(_d);
		assertEquals("startAt should have changed", _d.toString(), _wrm.getStartAt().toString());
	}
		
	@Test
	public void testDurationHours() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertEquals("durationHours should be initialized to default value by empty constructor", 1, _wrm.getDurationHours());
		_wrm.setDurationHours(3);
		assertEquals("durationHours should have changed", 3, _wrm.getDurationHours());
	}
	
	@Test
	public void testDurationMinutes() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertEquals("durationMinutes should be initialized to default value by empty constructor", 30, _wrm.getDurationMinutes());
		_wrm.setDurationMinutes(45);
		assertEquals("durationMinutes should have changed", 45, _wrm.getDurationMinutes());
	}
	
	@Test
	public void testComment() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("comment should not be set by empty constructor", _wrm.getComment());
		_wrm.setComment("testComment");
		assertEquals("comment should have changed", "testComment", _wrm.getComment());
	}
	
	@Test
	public void testIsBillable() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertEquals("isBillable should be initialized to default value by empty constructor", true, _wrm.isBillable());
		_wrm.setBillable(false);
		assertEquals("isBillable should have changed", false, _wrm.isBillable());
	}
	
	@Test
	public void testCreatedBy() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("createdBy should not be set by empty constructor", _wrm.getCreatedBy());
		_wrm.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _wrm.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("createdAt should not be set by empty constructor", _wrm.getCreatedAt());
		_wrm.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _wrm.getCreatedAt());
	}
		
	@Test
	public void testModifiedBy() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("modifiedBy should not be set by empty constructor", _wrm.getModifiedBy());
		_wrm.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _wrm.getModifiedBy());	
	}
	
	@Test
	public void testModifiedAt() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("modifiedAt should not be set by empty constructor", _wrm.getModifiedAt());
		_wrm.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _wrm.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		WorkRecordModel _wrm1 = new WorkRecordModel();
		assertNull("id should not be set by empty constructor", _wrm1.getId());
		assertNull("companyId should not be set by empty constructor", _wrm1.getCompanyId());
		assertNull("companyTitle should not be set by empty constructor", _wrm1.getCompanyTitle());
		assertNull("projectId should not be set by empty constructor", _wrm1.getProjectId());
		assertNull("projectTitle should not be set by empty constructor", _wrm1.getProjectTitle());
		assertNull("resourceId should not be set by empty constructor", _wrm1.getResourceId());
		assertNull("startAt should not be set by empty constructor", _wrm1.getStartAt());
		assertEquals("durationHours should be set on default initial value", 1, _wrm1.getDurationHours());
		assertEquals("durationMinutes should be set on default initial value", 30, _wrm1.getDurationMinutes());
		assertNull("comment should not be set by empty constructor", _wrm1.getComment());
		assertEquals("isBillable should be set on default initial value", true, _wrm1.isBillable());
		
		postWorkRecord(_wrm1, Status.BAD_REQUEST);
		_wrm1.setCompanyId(company.getId());

		postWorkRecord(_wrm1, Status.BAD_REQUEST);
		_wrm1.setCompanyTitle(company.getTitle());

		postWorkRecord(_wrm1, Status.BAD_REQUEST);
		_wrm1.setProjectId(project.getId());
		
		postWorkRecord(_wrm1, Status.BAD_REQUEST);
		_wrm1.setProjectTitle(project.getTitle());

		postWorkRecord(_wrm1, Status.BAD_REQUEST);
		_wrm1.setResourceId(resource.getId());

		postWorkRecord(_wrm1, Status.BAD_REQUEST);
		Date _date = new Date();
		_wrm1.setStartAt(_date);

		WorkRecordModel _wrm2 = postWorkRecord(_wrm1, Status.OK);
		
		// validate _wrm1
		assertNull("create() should not change the id of the local object", _wrm1.getId());
		assertEquals("create() should not change the companyId of the local object", company.getId(), _wrm1.getCompanyId());
		assertEquals("create() should not change the companyTitle of the local object", company.getTitle(), _wrm1.getCompanyTitle());
		assertEquals("create() should not change the projectId of the local object", project.getId(), _wrm1.getProjectId());
		assertEquals("create() should not change the projectTitle of the local object", project.getTitle(), _wrm1.getProjectTitle());
		assertEquals("create() should not change the resourceId of the local object", resource.getId(), _wrm1.getResourceId());
		assertEquals("create() should not change the startAt Date of the local object", _date.toString(), _wrm1.getStartAt().toString());
		assertEquals("create() should not change durationHours on the local object", 1, _wrm1.getDurationHours());
		assertEquals("create() should not change durationMinutes on the local object", 30, _wrm1.getDurationMinutes());
		assertNull("create() should not change comment on the local object", _wrm1.getComment());
		assertEquals("create() should not change isBillable on the local object", true, _wrm1.isBillable());
		
		// validate _wrm2
		assertNotNull("create() should set a valid id on the remote object returned", _wrm2.getId());
		assertEquals("create() should not change the companyId", company.getId(), _wrm2.getCompanyId());
		assertEquals("create() should not change the companyTitle", company.getTitle(), _wrm2.getCompanyTitle());
		assertEquals("create() should not change the projectId", project.getId(), _wrm2.getProjectId());
		assertEquals("create() should not change the projectTitle", project.getTitle(), _wrm2.getProjectTitle());
		assertEquals("create() should not change the resourceId", resource.getId(), _wrm2.getResourceId());
		assertEquals("create() should not change the startAt Date", _date.toString(), _wrm2.getStartAt().toString());
		assertEquals("create() should not change durationHours", 1, _wrm2.getDurationHours());
		assertEquals("create() should not change durationMinutes", 30, _wrm2.getDurationMinutes());
		assertEquals("create() should not change comment", null, _wrm2.getComment());
		assertEquals("create() should not change isBillable", true, _wrm2.isBillable());

		// read(_wrm2) -> _wrm3
		WorkRecordModel _wrm3 = getWorkRecord(_wrm2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _wrm2.getId(), _wrm3.getId());
		assertEquals("companyId of returned object should be unchanged after remote create", _wrm2.getCompanyId(), _wrm3.getCompanyId());
		assertEquals("companyTitle of returned object should be unchanged after remote create", _wrm2.getCompanyTitle(), _wrm3.getCompanyTitle());
		assertEquals("projectId of returned object should be unchanged after remote create", _wrm2.getProjectId(), _wrm3.getProjectId());
		assertEquals("projectTitle of returned object should be unchanged after remote create", _wrm2.getProjectTitle(), _wrm3.getProjectTitle());
		assertEquals("resourceId of returned object should be unchanged after remote create", _wrm2.getResourceId(), _wrm3.getResourceId());
		assertEquals("startAt of returned object should be unchanged after remote create", _wrm2.getStartAt(), _wrm3.getStartAt());
		assertEquals("durationHours of returned object should be unchanged after remote create", _wrm2.getDurationHours(), _wrm3.getDurationHours());
		assertEquals("durationMinutes of returned object should be unchanged after remote create", _wrm2.getDurationMinutes(), _wrm3.getDurationMinutes());
		assertEquals("comment of returned object should be unchanged after remote create", _wrm2.getComment(), _wrm3.getComment());
		assertEquals("isBillable of returned object should be unchanged after remote create", _wrm2.isBillable(), _wrm3.isBillable());

		deleteWorkRecord(_wrm3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		WorkRecordModel _wrm1 = createWorkRecord(company, project, resource, date, 4, 20, true, "testCreateReadDelete1");
		
		// validate _wrm1
		assertNull("id should not be set by constructor", _wrm1.getId());
		assertEquals("companyId should be set by constructor", company.getId(), _wrm1.getCompanyId());
		assertEquals("companyTitle should be set by constructor", company.getTitle(), _wrm1.getCompanyTitle());
		assertEquals("projectId should be set by constructor", project.getId(), _wrm1.getProjectId());
		assertEquals("projectTitle should be set by constructor", project.getTitle(), _wrm1.getProjectTitle());
		assertEquals("resourceId should be set by constructor", resource.getId(), _wrm1.getResourceId());
		assertEquals("startAt should be set by constructor", date, _wrm1.getStartAt());
		assertEquals("durationHours should be set by constructor", 4, _wrm1.getDurationHours());
		assertEquals("durationMinutes should be set by constructor", 20, _wrm1.getDurationMinutes());
		assertEquals("comment should be set by constructor", "testCreateReadDelete1", _wrm1.getComment());
		assertEquals("isBillable should be set by constructor", true, _wrm1.isBillable());
		
		WorkRecordModel _wrm2 = postWorkRecord(_wrm1, Status.OK);
		
		// validate _wrm1 (after create())
		assertNull("create() should not change the id of the local object", _wrm1.getId());
		assertEquals("create() should not change the companyId of the local object", company.getId(), _wrm1.getCompanyId());
		assertEquals("create() should not change the companyTitle of the local object", company.getTitle(), _wrm1.getCompanyTitle());
		assertEquals("create() should not change the projectId of the local object", project.getId(), _wrm1.getProjectId());
		assertEquals("create() should not change the projectTitle of the local object", project.getTitle(), _wrm1.getProjectTitle());
		assertEquals("create() should not change the resourceId of the local object", resource.getId(), _wrm1.getResourceId());
		assertEquals("create() should not change the startAt Date of the local object", date, _wrm1.getStartAt());
		assertEquals("create() should not change durationHours on the local object", 4, _wrm1.getDurationHours());
		assertEquals("create() should not change durationMinutes on the local object", 20, _wrm1.getDurationMinutes());
		assertEquals("create() should not change comment on the local object", "testCreateReadDelete1", _wrm1.getComment());
		assertEquals("create() should not change isBillable on the local object", true, _wrm1.isBillable());
		
		// validate _wrm2
		assertNotNull("id of returned object should be set", _wrm2.getId());
		assertEquals("companyId of returned object should be unchanged after remote create", company.getId(), _wrm2.getCompanyId());
		assertEquals("companyTitle of returned object should be unchanged after remote create", company.getTitle(), _wrm2.getCompanyTitle());
		assertEquals("projectId of returned object should be unchanged after remote create", project.getId(), _wrm2.getProjectId());
		assertEquals("projectTitle of returned object should be unchanged after remote create", project.getTitle(), _wrm2.getProjectTitle());
		assertEquals("resourceId of returned object should be unchanged after remote create", resource.getId(), _wrm2.getResourceId());
		assertEquals("create() should not change the startAt Date of the local object", date, _wrm2.getStartAt());
		assertEquals("durationHours of returned object should be unchanged", 4, _wrm2.getDurationHours());
		assertEquals("durationMinutes of returned object should be unchanged", 20, _wrm2.getDurationMinutes());
		assertEquals("comment of returned object should be unchanged", "testCreateReadDelete1", _wrm2.getComment());
		assertEquals("create() should not change isBillable", true, _wrm2.isBillable());

		// read(_wrm2)  -> _wrm3
		WorkRecordModel _wrm3 = getWorkRecord(_wrm2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _wrm2.getId(), _wrm3.getId());
		assertEquals("companyId of returned object should be the same", _wrm2.getCompanyId(), _wrm3.getCompanyId());
		assertEquals("companyTitle of returned object should be the same", _wrm2.getCompanyTitle(), _wrm3.getCompanyTitle());
		assertEquals("projectId of returned object should be the same", _wrm2.getProjectId(), _wrm3.getProjectId());
		assertEquals("projectTitle of returned object should be the same", _wrm2.getProjectTitle(), _wrm3.getProjectTitle());
		assertEquals("resourceId of returned object should be the same", _wrm2.getResourceId(), _wrm3.getResourceId());
		assertEquals("the startAt Date should be the same", _wrm2.getStartAt(), _wrm3.getStartAt());
		assertEquals("durationHours of returned object should be the same", _wrm2.getDurationHours(), _wrm3.getDurationHours());
		assertEquals("durationMinutes of returned object should be the same", _wrm2.getDurationMinutes(), _wrm3.getDurationMinutes());
		assertEquals("comment of returned object should be the same", _wrm2.getComment(), _wrm3.getComment());
		assertEquals("isBillable should be the same", _wrm2.isBillable(), _wrm3.isBillable());		

		deleteWorkRecord(_wrm3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateWithClientSideId() {
		WorkRecordModel _wrm1 = createWorkRecord(company, project, resource, date, 4, 20, true, "testCreateWithClientSideId1");
		_wrm1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _wrm1.getId());
		postWorkRecord(_wrm1, Status.BAD_REQUEST);
	}
	
	@Test
	public void testCreateWithDuplicateId() {
		WorkRecordModel _wrm1 = postWorkRecord(
				createWorkRecord(company, project, resource, date, 4, 20, true, "testCreateWithDuplicateId1"), 
				Status.OK);
		WorkRecordModel _wrm2 = createWorkRecord(company2, project2, resource2, date, 1, 30, true, "testCreateWithDuplicateId2");
		_wrm2.setId(_wrm1.getId());		// wrongly create a 2nd WorkRecordModel object with the same ID
		postWorkRecord(_wrm2, Status.CONFLICT);
		deleteWorkRecord(_wrm1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testList(
	) {		
		ArrayList<WorkRecordModel> _localList = new ArrayList<WorkRecordModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(postWorkRecord(
					createWorkRecord(company, project, resource, date, i, i, true, "testList" + i),
					Status.OK));
		}
		List<WorkRecordModel> _remoteList = listWorkRecords(Status.OK);
		
		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (WorkRecordModel _c : _remoteList) {
			_remoteListIds.add(_c.getId());
		}
		for (WorkRecordModel _c : _localList) {
			assertTrue("workrecord <" + _c.getId() + "> should be listed", _remoteListIds.contains(_c.getId()));
		}
		for (WorkRecordModel _c : _localList) {
			getWorkRecord(_c.getId(), Status.OK);
		}
		for (WorkRecordModel _c : _localList) {
			deleteWorkRecord(_c.getId(), Status.NO_CONTENT);
		}
	}
		
	@Test
	public void testCreate() {
		Date _date1 = new Date(1000);
		Date _date2 = new Date(2000);		
		WorkRecordModel _wrm1 = postWorkRecord(
				createWorkRecord(company, project, resource, _date1, 1, 10, true, "testCreate1"), 
				Status.OK);
		WorkRecordModel _wrm2 = postWorkRecord(
				createWorkRecord(company2, project2, resource2, _date2, 2, 20, false, "testCreate2"), 
				Status.OK);

		assertNotNull("ID should be set", _wrm1.getId());
		assertNotNull("ID should be set", _wrm2.getId());
		assertThat(_wrm2.getId(), not(equalTo(_wrm1.getId())));

		// validate _wrm1
		assertEquals("companyId should be set correctly", company.getId(), _wrm1.getCompanyId());
		assertEquals("companyTitle should be set correctly", company.getTitle(), _wrm1.getCompanyTitle());
		assertEquals("projectId should be set correctly", project.getId(), _wrm1.getProjectId());
		assertEquals("projectTitle should be set correctly", project.getTitle(), _wrm1.getProjectTitle());
		assertEquals("resourceId should be set correctly", resource.getId(), _wrm1.getResourceId());
		assertEquals("the startAt Date should be set correctly", _date1, _wrm1.getStartAt());
		assertEquals("durationHours should be set correctly", 1, _wrm1.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 10, _wrm1.getDurationMinutes());
		assertEquals("comment should be set correctly", "testCreate1", _wrm1.getComment());
		assertEquals("isBillable should be set correctly", true, _wrm1.isBillable());			
		
		// validate _wrm2
		assertEquals("companyId should be set correctly", company2.getId(), _wrm2.getCompanyId());
		assertEquals("companyTitle should be set correctly", company2.getTitle(), _wrm2.getCompanyTitle());
		assertEquals("projectId should be set correctly", project2.getId(), _wrm2.getProjectId());
		assertEquals("projectTitle should be set correctly", project2.getTitle(), _wrm2.getProjectTitle());
		assertEquals("resourceId should be set correctly", resource2.getId(), _wrm2.getResourceId());
		assertEquals("the startAt Date should be set correctly", _date2, _wrm2.getStartAt());
		assertEquals("durationHours should be set correctly", 2, _wrm2.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 20, _wrm2.getDurationMinutes());
		assertEquals("comment should be set correctly", "testCreate2", _wrm2.getComment());
		assertEquals("isBillable should be set correctly", false, _wrm2.isBillable());			
		
		deleteWorkRecord(_wrm1.getId(), Status.NO_CONTENT);
		deleteWorkRecord(_wrm2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDoubleCreate(
	) {
		WorkRecordModel _wrm1 = postWorkRecord(
				createWorkRecord(company, project, resource, date, 1, 10, true, "testDoubleCreate1"),
				Status.OK);
		assertNotNull("ID should be set:", _wrm1.getId());		
		postWorkRecord(_wrm1, Status.CONFLICT);
		deleteWorkRecord(_wrm1.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testRead(
	) {
		ArrayList<WorkRecordModel> _localList = new ArrayList<WorkRecordModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(postWorkRecord(
					createWorkRecord(company, project, resource, date, i, i*10, true, "testRead" + i),
					Status.OK));
		}
	
		// test read on each local element
		for (WorkRecordModel _wrm : _localList) {
			getWorkRecord(_wrm.getId(), Status.OK);
		}

		// test read on each listed element
		for (WorkRecordModel _wrm : listWorkRecords(Status.OK)) {
			assertEquals("ID should be unchanged when reading a workrecord",
					_wrm.getId(), getWorkRecord(_wrm.getId(), Status.OK).getId());
		}

		for (WorkRecordModel _wrm : _localList) {
			deleteWorkRecord(_wrm.getId(), Status.NO_CONTENT);
		}
	}	

	@Test
	public void testMultiRead(
	) {
		WorkRecordModel _wrm1 = postWorkRecord(
				createWorkRecord(company, project, resource, date, 1, 10, true, "testMultiRead1"),
				Status.OK);
		WorkRecordModel _wrm2 = getWorkRecord(_wrm1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read", _wrm1.getId(), _wrm2.getId());
		WorkRecordModel _wrm3 = getWorkRecord(_wrm1.getId(), Status.OK);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same", _wrm3.getId(), _wrm2.getId());
		assertEquals("companyId should be the same", _wrm3.getCompanyId(), _wrm2.getCompanyId());
		assertEquals("companyTitle should be the same", _wrm3.getCompanyTitle(), _wrm2.getCompanyTitle());
		assertEquals("projectId should be the same", _wrm3.getProjectId(), _wrm2.getProjectId());
		assertEquals("projectTitle should be the same", _wrm3.getProjectTitle(), _wrm2.getProjectTitle());
		assertEquals("resourceId should be the same", _wrm3.getResourceId(), _wrm2.getResourceId());
		assertEquals("the startAt Date should be the same", _wrm3.getStartAt(), _wrm2.getStartAt());
		assertEquals("durationHours should be the same", _wrm3.getDurationHours(), _wrm2.getDurationHours());
		assertEquals("durationMinutes should be the same", _wrm3.getDurationMinutes(), _wrm2.getDurationMinutes());
		assertEquals("comment should be the same", _wrm3.getComment(), _wrm2.getComment());
		assertEquals("isBillable should be the same", _wrm3.isBillable(), _wrm2.isBillable());		
				
		assertEquals("ID should be the same", _wrm1.getId(), _wrm2.getId());
		assertEquals("companyId should be the same", _wrm1.getCompanyId(), _wrm2.getCompanyId());
		assertEquals("companyTitle should be the same", _wrm1.getCompanyTitle(), _wrm2.getCompanyTitle());
		assertEquals("projectId should be the same", _wrm1.getProjectId(), _wrm2.getProjectId());
		assertEquals("projectTitle should be the same", _wrm1.getProjectTitle(), _wrm2.getProjectTitle());
		assertEquals("resourceId should be the same", _wrm1.getResourceId(), _wrm2.getResourceId());
		assertEquals("the startAt Date should be the same", _wrm1.getStartAt(), _wrm2.getStartAt());
		assertEquals("durationHours should be the same", _wrm1.getDurationHours(), _wrm2.getDurationHours());
		assertEquals("durationMinutes should be the same", _wrm1.getDurationMinutes(), _wrm2.getDurationMinutes());
		assertEquals("comment should be the same", _wrm1.getComment(), _wrm2.getComment());
		assertEquals("isBillable should be the same", _wrm1.isBillable(), _wrm2.isBillable());		
		
		deleteWorkRecord(_wrm1.getId(), Status.NO_CONTENT);
	}
		
	@Test
	public void testUpdate() {
		WorkRecordModel _wrm1 = postWorkRecord(
				createWorkRecord(company, project, resource, date, 1, 10, true, "testUpdate1"),
				Status.OK);
		
		Date _date2 = new Date(2000);
		_wrm1.setStartAt(_date2);
		_wrm1.setDurationHours(2);
		_wrm1.setDurationMinutes(20);
		_wrm1.setComment("testUpdate2");
		_wrm1.setBillable(false);
		
		WorkRecordModel _wrm2 = putWorkRecord(_wrm1, Status.OK);
		
		assertNotNull("ID should be set", _wrm2.getId());
		assertEquals("ID should be unchanged", _wrm1.getId(), _wrm2.getId());
		assertEquals("companyId should be unchanged", _wrm1.getCompanyId(), _wrm2.getCompanyId());
		assertEquals("companyTitle should be unchanged", _wrm1.getCompanyTitle(), _wrm2.getCompanyTitle());
		assertEquals("projectId should be unchanged", _wrm1.getProjectId(), _wrm2.getProjectId());
		assertEquals("projectTitle should be unchanged", _wrm1.getProjectTitle(), _wrm2.getProjectTitle());
		assertEquals("resourceId should be unchanged", _wrm1.getResourceId(), _wrm2.getResourceId());
		assertEquals("the startAt Date should be set correctly", _date2, _wrm2.getStartAt());
		assertEquals("durationHours should be set correctly", 2, _wrm2.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 20, _wrm2.getDurationMinutes());
		assertEquals("comment should be set correctly", "testUpdate2", _wrm2.getComment());
		assertEquals("isBillable should be set correctly", false, _wrm2.isBillable());			

		Date _date4 = new Date(4000);
		_wrm1.setStartAt(_date4);
		_wrm1.setDurationHours(4);
		_wrm1.setDurationMinutes(40);
		_wrm1.setComment("testUpdate4");
		_wrm1.setBillable(true);
		
		WorkRecordModel _wrm3 = putWorkRecord(_wrm1, Status.OK);

		assertNotNull("ID should be set", _wrm3.getId());
		assertEquals("ID should be unchanged", _wrm1.getId(), _wrm3.getId());	
		assertEquals("companyId should be set correctly", _wrm1.getCompanyId(), _wrm3.getCompanyId());
		assertEquals("companyTitle should be set correctly", _wrm1.getCompanyTitle(), _wrm3.getCompanyTitle());
		assertEquals("projectId should be set correctly", _wrm1.getProjectId(), _wrm3.getProjectId());
		assertEquals("projectTitle should be set correctly", _wrm1.getProjectTitle(), _wrm3.getProjectTitle());
		assertEquals("resourceId should be set correctly", _wrm1.getResourceId(), _wrm3.getResourceId());
		assertEquals("the startAt Date should be set correctly", _date4, _wrm3.getStartAt());
		assertEquals("durationHours should be set correctly", 4, _wrm3.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 40, _wrm3.getDurationMinutes());
		assertEquals("comment should be set correctly", "testUpdate4", _wrm3.getComment());
		assertEquals("isBillable should be set correctly", true, _wrm3.isBillable());			
		
		deleteWorkRecord(_wrm1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDelete() {
		WorkRecordModel _wrm1 = postWorkRecord(
				createWorkRecord(company, project, resource, date, 1, 10, true, "testDelete"),
				Status.OK);
		WorkRecordModel _wrm2 = getWorkRecord(_wrm1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a workrecord (remote):", _wrm1.getId(), _wrm2.getId());						
		deleteWorkRecord(_wrm1.getId(), Status.NO_CONTENT);
		getWorkRecord(_wrm1.getId(), Status.NOT_FOUND);
		getWorkRecord(_wrm1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		WorkRecordModel _wrm1 = postWorkRecord(
				createWorkRecord(company, project, resource, date, 1, 10, true, "testDoubleDelete"),
				Status.OK);
		getWorkRecord(_wrm1.getId(), Status.OK);
		deleteWorkRecord(_wrm1.getId(), Status.NO_CONTENT);
		getWorkRecord(_wrm1.getId(), Status.NOT_FOUND);
		deleteWorkRecord(_wrm1.getId(), Status.NOT_FOUND);
		getWorkRecord(_wrm1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		WorkRecordModel _wrm1 = postWorkRecord(
				createWorkRecord(company, project, resource, date, 1, 10, true, "testModifications"),
				Status.OK);
		assertNotNull("create() should set createdAt", _wrm1.getCreatedAt());
		assertNotNull("create() should set createdBy", _wrm1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _wrm1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _wrm1.getModifiedBy());
		assertTrue("modifiedAt should be greater than or equal to createdAt after create()", _wrm1.getModifiedAt().compareTo(_wrm1.getCreatedAt()) >= 0);
		assertEquals("createdBy and modifiedBy should be identical after create()", _wrm1.getCreatedBy(), _wrm1.getModifiedBy());
		
		_wrm1.setDurationHours(2);
		WorkRecordModel _wrm2 = putWorkRecord(_wrm1, Status.OK);
		assertEquals("update() should not change createdAt", _wrm1.getCreatedAt(), _wrm2.getCreatedAt());
		assertEquals("update() should not change createdBy", _wrm1.getCreatedBy(), _wrm2.getCreatedBy());
		assertThat(_wrm2.getModifiedAt(), not(equalTo(_wrm2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_o2.getModifiedBy(), not(equalTo(_o2.getCreatedBy())));

		String _createdBy = _wrm1.getCreatedBy();
		_wrm1.setCreatedBy("testModifications");
		WorkRecordModel _wrm3 = putWorkRecord(_wrm1, Status.OK);	// update should ignore client-side generated createdBy
		assertEquals("update() should not change createdBy", _createdBy, _wrm3.getCreatedBy());
		_wrm1.setCreatedBy(_createdBy);

		Date _createdAt = _wrm1.getCreatedAt();
		_wrm1.setCreatedAt(new Date(1000));
		WorkRecordModel _wrm4 = putWorkRecord(_wrm1, Status.OK);	// update should ignore client-side generated createdAt
		assertEquals("update() should not change createdAt", _createdAt, _wrm4.getCreatedAt());
		_wrm1.setCreatedAt(_createdAt);
		
		String _modifiedBy = _wrm1.getModifiedBy();
		_wrm1.setModifiedBy("testModifications");
		WorkRecordModel _wrm5 = putWorkRecord(_wrm1, Status.OK);	// update should ignore client-side generated modifiedBy
		assertEquals("update() should not change modifiedBy", _modifiedBy, _wrm5.getModifiedBy());

		Date _modifiedAt = _wrm1.getModifiedAt();
		Date _modifiedAt2 = new Date(1000);
		_wrm1.setModifiedAt(_modifiedAt2);
		WorkRecordModel _wrm6 = putWorkRecord(_wrm1, Status.OK);	// update should ignore client-side generated modifiedAt
		assertThat(_wrm6.getModifiedAt(), not(equalTo(_modifiedAt)));
		assertThat(_wrm6.getModifiedAt(), not(equalTo(_modifiedAt2)));
		
		deleteWorkRecord(_wrm1.getId(), Status.NO_CONTENT);
	}
	
	/********************************** helper methods *********************************/	
	public static WorkRecordModel createWorkRecord(
			CompanyModel company,
			ProjectModel project,
			ResourceModel resource,
			Date d, 
			int hours, 
			int mins, 
			boolean isBillable,
			String testCaseName) {
			return new WorkRecordModel(
					company.getId(), 
					company.getTitle(), 
					project.getId(), 
					project.getTitle(), 
					resource.getId(), 
					d, 
					hours, 
					mins, 
					isBillable, 
					testCaseName);
	}
	
	private List<WorkRecordModel> listWorkRecords(Status expectedStatus) {
		Response _response = workRecordWC.replacePath("/").get();
		assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return new ArrayList<WorkRecordModel>(workRecordWC.getCollection(WorkRecordModel.class));
		}
		else {
			return null;
		}
	}

	private WorkRecordModel postWorkRecord(
			WorkRecordModel wrm, 
			Status expectedStatus) {
		return postWorkRecord(workRecordWC, wrm, expectedStatus);
	}
	
	public static WorkRecordModel postWorkRecord(
			WebClient webClient,
			WorkRecordModel model,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(WorkRecordModel.class);
		} else {
			return null;
		}
	}
	
	private WorkRecordModel getWorkRecord(String id, Status expectedStatus) {
		Response _response = workRecordWC.replacePath("/").path(id).get();
		assertEquals("read(" + id + ") should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(WorkRecordModel.class);
		}
		else {
			return null;
		}
	}
	
	private WorkRecordModel putWorkRecord(WorkRecordModel wrm, Status expectedStatus) {
		workRecordWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = workRecordWC.replacePath("/").path(wrm.getId()).put(wrm);
		assertEquals("update() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(WorkRecordModel.class);
		}
		else {
			return null;
		}
	}
	
	private void deleteWorkRecord(String id, Status expectedStatus) {
		Response _response = workRecordWC.replacePath("/").path(id).delete();
		assertEquals("delete(" + id + ") should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());	
	}
	
	protected int calculateMembers() {
		return 1;
	}
}
