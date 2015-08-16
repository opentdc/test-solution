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
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
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
import test.org.opentdc.addressbooks.OrgTest;
import test.org.opentdc.resources.ResourceTest;
import test.org.opentdc.wtt.CompanyTest;
import test.org.opentdc.wtt.ProjectTest;

/**
 * Testing workrecords.
 * @author Bruno Kaiser
 *
 */
public class WorkRecordTest extends AbstractTestClient {
	private static final String CN = "WorkRecordTest";
	private Date date;
	private WebClient wc = null;
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
	private OrgModel org = null;

	@Before
	public void initializeTests() {
		date = new Date();
		wc = createWebClient(ServiceUtil.WORKRECORDS_API_URL, WorkRecordsService.class);
		wttWC = createWebClient(ServiceUtil.WTT_API_URL, WttService.class);
		resourceWC = createWebClient(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);

		addressbook = AddressbookTest.post(addressbookWC, 
				new AddressbookModel(CN), Status.OK);
		contact = ContactTest.post(addressbookWC, addressbook.getId(),
				new ContactModel(CN + "1", CN + "2"), Status.OK);
		org = OrgTest.post(addressbookWC, addressbook.getId(), 
				new OrgModel(CN, OrgType.LTD), Status.OK);
		company = CompanyTest.post(wttWC, 
				new CompanyModel(CN + "1", "MY_DESC", org.getId()), Status.OK);
		company2 = CompanyTest.post(wttWC, 
				new CompanyModel(CN + "2", "MY_DESC2", org.getId()), Status.OK);
		project = ProjectTest.post(wttWC, company.getId(), 
				new ProjectModel(CN + "1", "MY_DESC1"), Status.OK);
		project2 = ProjectTest.post(wttWC, company2.getId(), 
				new ProjectModel(CN + "2", "MY_DESC2"), Status.OK);
		resource = ResourceTest.post(resourceWC, 
				new ResourceModel(CN + "1", contact.getId()), Status.OK);
		resource2 = ResourceTest.post(resourceWC, 
				new ResourceModel(CN + "2", contact.getId()), Status.OK);
	}
			
	@After
	public void cleanupTest() {
		AddressbookTest.delete(addressbookWC, addressbook.getId(), Status.NO_CONTENT);
		addressbookWC.close();
		
		ResourceTest.delete(resourceWC, resource.getId(), Status.NO_CONTENT);
		ResourceTest.delete(resourceWC, resource2.getId(), Status.NO_CONTENT);
		resourceWC.close();
		
		CompanyTest.delete(wttWC, company.getId(), Status.NO_CONTENT);
		CompanyTest.delete(wttWC, company2.getId(), Status.NO_CONTENT);
		wttWC.close();
		
		wc.close();
	}

	/********************************** workrecord attributes tests *********************************/	
	@Test
	public void testEmptyConstructor() {
		WorkRecordModel _model = new WorkRecordModel();
		assertNull("id should not be set by empty constructor", _model.getId());
		assertNull("companyId should not be set by empty constructor", _model.getCompanyId());
		assertNull("companyTitle should not be set by empty constructor", _model.getCompanyTitle());
		assertNull("projectId should not be set by empty constructor", _model.getProjectId());
		assertNull("projectTitle should not be set by empty constructor", _model.getProjectTitle());
		assertNull("resourceId should not be set by empty constructor", _model.getResourceId());
		assertNull("startAt should not be set by empty constructor", _model.getStartAt());
		assertEquals("durationHours should be set on default initial value", 1, _model.getDurationHours());
		assertEquals("durationMinutes should be set on default initial value", 30, _model.getDurationMinutes());
		assertNull("comment should not be set by empty constructor", _model.getComment());
		assertEquals("isBillable should be set on default initial value", true, _model.isBillable());
	}

	@Test
	public void testConstructor() {		
		WorkRecordModel _model = create(company, project, resource, date, 3, 45, false, "testConstructor1");
		assertNull("id should not be set by constructor", _model.getId());
		assertEquals("companyId should be set by constructor", company.getId(), _model.getCompanyId());
		assertEquals("companyTitle should be set by constructor", company.getTitle(), _model.getCompanyTitle());
		assertEquals("projectId should be set by constructor", project.getId(), _model.getProjectId());
		assertEquals("projectTitle should be set by constructor", project.getTitle(), _model.getProjectTitle());
		assertEquals("resourceId should be set by constructor", resource.getId(), _model.getResourceId());
		assertEquals("startAt should be set by constructor", date.toString(), _model.getStartAt().toString());
		assertEquals("durationHours should be set by constructor", 3, _model.getDurationHours());
		assertEquals("durationMinutes should be set by constructor", 45, _model.getDurationMinutes());
		assertEquals("comment should be set by constructor", "testConstructor1", _model.getComment());
		assertEquals("isBillable should be set by constructor", false, _model.isBillable());
	}

	@Test
	public void testId() {
		WorkRecordModel _model = new WorkRecordModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed", "testId", _model.getId());
	}

	@Test
	public void testCompanyId() {
		WorkRecordModel _model = new WorkRecordModel();
		assertNull("companyId should not be set by empty constructor", _model.getCompanyId());
		_model.setCompanyId("testCompanyId");
		assertEquals("companyId should have changed", "testCompanyId", _model.getCompanyId());
	}
	
	@Test
	public void testCompanyTitle() {
		WorkRecordModel _model = new WorkRecordModel();
		assertNull("CompanyTitle should not be set by empty constructor", _model.getCompanyTitle());
		_model.setCompanyTitle("testCompanyTitle");
		assertEquals("CompanyTitle should have changed", "testCompanyTitle", _model.getCompanyTitle());
	}
	
	@Test
	public void testProjectId() {
		WorkRecordModel _model = new WorkRecordModel();
		assertNull("projectId should not be set by empty constructor", _model.getProjectId());
		_model.setProjectId("testProjectId");
		assertEquals("projectId should have changed", "testProjectId", _model.getProjectId());
	}
	
	@Test
	public void testProjectTitle() {
		WorkRecordModel _model = new WorkRecordModel();
		assertNull("projectTitle should not be set by empty constructor", _model.getProjectTitle());
		_model.setProjectTitle("testProjectTitle");
		assertEquals("projectTitle should have changed", "testProjectTitle", _model.getProjectTitle());
	}
	
	@Test
	public void testResourceId() {
		WorkRecordModel _model = new WorkRecordModel();
		assertNull("resourceId should not be set by empty constructor", _model.getResourceId());
		_model.setResourceId("testResourceId");
		assertEquals("resourceId should have changed", "testResourceId", _model.getResourceId());
	}
			
	@Test
	public void testResourceName() {
		WorkRecordModel _model = new WorkRecordModel();
		assertNull("resourceName should not be set by empty constructor", _model.getResourceName());
		_model.setResourceName("testResourceName");
		assertEquals("resourceName should have changed", "testResourceName", _model.getResourceName());
	}
	@Test
	public void testStartAt() {
		WorkRecordModel _model = new WorkRecordModel();
		assertNull("startAt should not be set by empty constructor", _model.getStartAt());
		Date _d = new Date();
		_model.setStartAt(_d);
		assertEquals("startAt should have changed", _d.toString(), _model.getStartAt().toString());
	}
		
	@Test
	public void testDurationHours() {
		WorkRecordModel _model = new WorkRecordModel();
		assertEquals("durationHours should be initialized to default value by empty constructor", 1, _model.getDurationHours());
		_model.setDurationHours(3);
		assertEquals("durationHours should have changed", 3, _model.getDurationHours());
	}
	
	@Test
	public void testDurationMinutes() {
		WorkRecordModel _model = new WorkRecordModel();
		assertEquals("durationMinutes should be initialized to default value by empty constructor", 30, _model.getDurationMinutes());
		_model.setDurationMinutes(45);
		assertEquals("durationMinutes should have changed", 45, _model.getDurationMinutes());
	}
	
	@Test
	public void testComment() {
		WorkRecordModel _model = new WorkRecordModel();
		assertNull("comment should not be set by empty constructor", _model.getComment());
		_model.setComment("testComment");
		assertEquals("comment should have changed", "testComment", _model.getComment());
	}
	
	@Test
	public void testIsBillable() {
		WorkRecordModel _model = new WorkRecordModel();
		assertEquals("isBillable should be initialized to default value by empty constructor", true, _model.isBillable());
		_model.setBillable(false);
		assertEquals("isBillable should have changed", false, _model.isBillable());
	}
	
	@Test
	public void testCreatedBy() {
		WorkRecordModel _model = new WorkRecordModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		WorkRecordModel _model = new WorkRecordModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	@Test
	public void testModifiedBy() {
		WorkRecordModel _model = new WorkRecordModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	@Test
	public void testModifiedAt() {
		WorkRecordModel _model = new WorkRecordModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		WorkRecordModel _model1 = new WorkRecordModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("companyId should not be set by empty constructor", _model1.getCompanyId());
		assertNull("companyTitle should not be set by empty constructor", _model1.getCompanyTitle());
		assertNull("projectId should not be set by empty constructor", _model1.getProjectId());
		assertNull("projectTitle should not be set by empty constructor", _model1.getProjectTitle());
		assertNull("resourceId should not be set by empty constructor", _model1.getResourceId());
		assertNull("startAt should not be set by empty constructor", _model1.getStartAt());
		assertEquals("durationHours should be set on default initial value", 1, _model1.getDurationHours());
		assertEquals("durationMinutes should be set on default initial value", 30, _model1.getDurationMinutes());
		assertNull("comment should not be set by empty constructor", _model1.getComment());
		assertEquals("isBillable should be set on default initial value", true, _model1.isBillable());
		
		post(_model1, Status.BAD_REQUEST);
		_model1.setCompanyId(company.getId());

		post(_model1, Status.BAD_REQUEST);
		_model1.setCompanyTitle(company.getTitle());

		post(_model1, Status.BAD_REQUEST);
		_model1.setProjectId(project.getId());
		
		post(_model1, Status.BAD_REQUEST);
		_model1.setProjectTitle(project.getTitle());

		post(_model1, Status.BAD_REQUEST);
		_model1.setResourceId(resource.getId());

		post(_model1, Status.BAD_REQUEST);
		Date _date = new Date();
		_model1.setStartAt(_date);

		WorkRecordModel _model2 = post(_model1, Status.OK);
		
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the companyId of the local object", company.getId(), _model1.getCompanyId());
		assertEquals("create() should not change the companyTitle of the local object", company.getTitle(), _model1.getCompanyTitle());
		assertEquals("create() should not change the projectId of the local object", project.getId(), _model1.getProjectId());
		assertEquals("create() should not change the projectTitle of the local object", project.getTitle(), _model1.getProjectTitle());
		assertEquals("create() should not change the resourceId of the local object", resource.getId(), _model1.getResourceId());
		assertEquals("create() should not change the startAt Date of the local object", _date.toString(), _model1.getStartAt().toString());
		assertEquals("create() should not change durationHours on the local object", 1, _model1.getDurationHours());
		assertEquals("create() should not change durationMinutes on the local object", 30, _model1.getDurationMinutes());
		assertNull("create() should not change comment on the local object", _model1.getComment());
		assertEquals("create() should not change isBillable on the local object", true, _model1.isBillable());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("create() should not change the companyId", company.getId(), _model2.getCompanyId());
		assertEquals("create() should not change the companyTitle", company.getTitle(), _model2.getCompanyTitle());
		assertEquals("create() should not change the projectId", project.getId(), _model2.getProjectId());
		assertEquals("create() should not change the projectTitle", project.getTitle(), _model2.getProjectTitle());
		assertEquals("create() should not change the resourceId", resource.getId(), _model2.getResourceId());
		assertEquals("create() should not change the startAt Date", _date.toString(), _model2.getStartAt().toString());
		assertEquals("create() should not change durationHours", 1, _model2.getDurationHours());
		assertEquals("create() should not change durationMinutes", 30, _model2.getDurationMinutes());
		assertEquals("create() should not change comment", null, _model2.getComment());
		assertEquals("create() should not change isBillable", true, _model2.isBillable());

		WorkRecordModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("companyId of returned object should be unchanged after remote create", _model2.getCompanyId(), _model3.getCompanyId());
		assertEquals("companyTitle of returned object should be unchanged after remote create", _model2.getCompanyTitle(), _model3.getCompanyTitle());
		assertEquals("projectId of returned object should be unchanged after remote create", _model2.getProjectId(), _model3.getProjectId());
		assertEquals("projectTitle of returned object should be unchanged after remote create", _model2.getProjectTitle(), _model3.getProjectTitle());
		assertEquals("resourceId of returned object should be unchanged after remote create", _model2.getResourceId(), _model3.getResourceId());
		assertEquals("startAt of returned object should be unchanged after remote create", _model2.getStartAt(), _model3.getStartAt());
		assertEquals("durationHours of returned object should be unchanged after remote create", _model2.getDurationHours(), _model3.getDurationHours());
		assertEquals("durationMinutes of returned object should be unchanged after remote create", _model2.getDurationMinutes(), _model3.getDurationMinutes());
		assertEquals("comment of returned object should be unchanged after remote create", _model2.getComment(), _model3.getComment());
		assertEquals("isBillable of returned object should be unchanged after remote create", _model2.isBillable(), _model3.isBillable());

		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		WorkRecordModel _model1 = create(company, project, resource, date, 4, 20, true, "testCreateReadDelete1");
		
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("companyId should be set by constructor", company.getId(), _model1.getCompanyId());
		assertEquals("companyTitle should be set by constructor", company.getTitle(), _model1.getCompanyTitle());
		assertEquals("projectId should be set by constructor", project.getId(), _model1.getProjectId());
		assertEquals("projectTitle should be set by constructor", project.getTitle(), _model1.getProjectTitle());
		assertEquals("resourceId should be set by constructor", resource.getId(), _model1.getResourceId());
		assertEquals("startAt should be set by constructor", date, _model1.getStartAt());
		assertEquals("durationHours should be set by constructor", 4, _model1.getDurationHours());
		assertEquals("durationMinutes should be set by constructor", 20, _model1.getDurationMinutes());
		assertEquals("comment should be set by constructor", "testCreateReadDelete1", _model1.getComment());
		assertEquals("isBillable should be set by constructor", true, _model1.isBillable());
		
		WorkRecordModel _model2 = post(_model1, Status.OK);
		
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the companyId of the local object", company.getId(), _model1.getCompanyId());
		assertEquals("create() should not change the companyTitle of the local object", company.getTitle(), _model1.getCompanyTitle());
		assertEquals("create() should not change the projectId of the local object", project.getId(), _model1.getProjectId());
		assertEquals("create() should not change the projectTitle of the local object", project.getTitle(), _model1.getProjectTitle());
		assertEquals("create() should not change the resourceId of the local object", resource.getId(), _model1.getResourceId());
		assertEquals("create() should not change the startAt Date of the local object", date, _model1.getStartAt());
		assertEquals("create() should not change durationHours on the local object", 4, _model1.getDurationHours());
		assertEquals("create() should not change durationMinutes on the local object", 20, _model1.getDurationMinutes());
		assertEquals("create() should not change comment on the local object", "testCreateReadDelete1", _model1.getComment());
		assertEquals("create() should not change isBillable on the local object", true, _model1.isBillable());
		
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("companyId of returned object should be unchanged after remote create", company.getId(), _model2.getCompanyId());
		assertEquals("companyTitle of returned object should be unchanged after remote create", company.getTitle(), _model2.getCompanyTitle());
		assertEquals("projectId of returned object should be unchanged after remote create", project.getId(), _model2.getProjectId());
		assertEquals("projectTitle of returned object should be unchanged after remote create", project.getTitle(), _model2.getProjectTitle());
		assertEquals("resourceId of returned object should be unchanged after remote create", resource.getId(), _model2.getResourceId());
		assertEquals("create() should not change the startAt Date of the local object", date, _model2.getStartAt());
		assertEquals("durationHours of returned object should be unchanged", 4, _model2.getDurationHours());
		assertEquals("durationMinutes of returned object should be unchanged", 20, _model2.getDurationMinutes());
		assertEquals("comment of returned object should be unchanged", "testCreateReadDelete1", _model2.getComment());
		assertEquals("create() should not change isBillable", true, _model2.isBillable());

		WorkRecordModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("companyId of returned object should be the same", _model2.getCompanyId(), _model3.getCompanyId());
		assertEquals("companyTitle of returned object should be the same", _model2.getCompanyTitle(), _model3.getCompanyTitle());
		assertEquals("projectId of returned object should be the same", _model2.getProjectId(), _model3.getProjectId());
		assertEquals("projectTitle of returned object should be the same", _model2.getProjectTitle(), _model3.getProjectTitle());
		assertEquals("resourceId of returned object should be the same", _model2.getResourceId(), _model3.getResourceId());
		assertEquals("the startAt Date should be the same", _model2.getStartAt(), _model3.getStartAt());
		assertEquals("durationHours of returned object should be the same", _model2.getDurationHours(), _model3.getDurationHours());
		assertEquals("durationMinutes of returned object should be the same", _model2.getDurationMinutes(), _model3.getDurationMinutes());
		assertEquals("comment of returned object should be the same", _model2.getComment(), _model3.getComment());
		assertEquals("isBillable should be the same", _model2.isBillable(), _model3.isBillable());		

		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testClientSideId() {
		WorkRecordModel _model1 = create(company, project, resource, date, 4, 20, true, "testClientSideId");
		_model1.setId("testClientSideId");
		assertEquals("id should have changed", "testClientSideId", _model1.getId());
		post(_model1, Status.BAD_REQUEST);
	}
	
	@Test
	public void testDuplicateId() {
		WorkRecordModel _model1 = post(
				create(company, project, resource, date, 4, 20, true, "testDuplicateId"), 
				Status.OK);
		WorkRecordModel _wrm2 = create(company2, project2, resource2, date, 1, 30, true, "testDuplicateId");
		_wrm2.setId(_model1.getId());		// wrongly create a 2nd WorkRecordModel object with the same ID
		post(_wrm2, Status.CONFLICT);
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testList() {		
		ArrayList<WorkRecordModel> _localList = new ArrayList<WorkRecordModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(
					create(company, project, resource, date, i, i, true, "testList" + i),
					Status.OK));
		}
		List<WorkRecordModel> _remoteList = list(null, Status.OK);
		
		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (WorkRecordModel _model : _remoteList) {
			_remoteListIds.add(_model.getId());
		}
		for (WorkRecordModel _model : _localList) {
			assertTrue("workrecord <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		for (WorkRecordModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		for (WorkRecordModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}
		
	@Test
	public void testCreate() {
		Date _date1 = new Date(1000);
		Date _date2 = new Date(2000);		
		WorkRecordModel _model1 = post(
				create(company, project, resource, _date1, 1, 10, true, "testCreate1"), 
				Status.OK);
		WorkRecordModel _model2 = post(
				create(company2, project2, resource2, _date2, 2, 20, false, "testCreate2"), 
				Status.OK);

		assertNotNull("ID should be set", _model1.getId());
		assertNotNull("ID should be set", _model2.getId());
		assertThat(_model2.getId(), not(equalTo(_model1.getId())));

		assertEquals("companyId should be set correctly", company.getId(), _model1.getCompanyId());
		assertEquals("companyTitle should be set correctly", company.getTitle(), _model1.getCompanyTitle());
		assertEquals("projectId should be set correctly", project.getId(), _model1.getProjectId());
		assertEquals("projectTitle should be set correctly", project.getTitle(), _model1.getProjectTitle());
		assertEquals("resourceId should be set correctly", resource.getId(), _model1.getResourceId());
		assertEquals("the startAt Date should be set correctly", _date1, _model1.getStartAt());
		assertEquals("durationHours should be set correctly", 1, _model1.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 10, _model1.getDurationMinutes());
		assertEquals("comment should be set correctly", "testCreate1", _model1.getComment());
		assertEquals("isBillable should be set correctly", true, _model1.isBillable());			
		
		assertEquals("companyId should be set correctly", company2.getId(), _model2.getCompanyId());
		assertEquals("companyTitle should be set correctly", company2.getTitle(), _model2.getCompanyTitle());
		assertEquals("projectId should be set correctly", project2.getId(), _model2.getProjectId());
		assertEquals("projectTitle should be set correctly", project2.getTitle(), _model2.getProjectTitle());
		assertEquals("resourceId should be set correctly", resource2.getId(), _model2.getResourceId());
		assertEquals("the startAt Date should be set correctly", _date2, _model2.getStartAt());
		assertEquals("durationHours should be set correctly", 2, _model2.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 20, _model2.getDurationMinutes());
		assertEquals("comment should be set correctly", "testCreate2", _model2.getComment());
		assertEquals("isBillable should be set correctly", false, _model2.isBillable());			
		
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testWrongCompanyId() {
		WorkRecordModel _model1 = create(company, project, resource, new Date(), 1, 10, true, "testWrongCompanyId");
		_model1.setCompanyId("");
		post(_model1, Status.BAD_REQUEST);
		_model1.setCompanyId(null);
		post(_model1, Status.BAD_REQUEST);
		_model1.setCompanyId("INVALID_COMPANY_ID");
		post(_model1, Status.BAD_REQUEST);		
	}
	
	@Test
	public void testWrongProjectId() {
		WorkRecordModel _model1 = create(company, project, resource, new Date(), 1, 10, true, "testWrongProjectId");
		_model1.setProjectId("");
		post(_model1, Status.BAD_REQUEST);
		_model1.setProjectId(null);
		post(_model1, Status.BAD_REQUEST);
		_model1.setProjectId("INVALID_PROJECT_ID");
		post(_model1, Status.BAD_REQUEST);		
	}
	
	@Test
	public void testWrongResourceId() {
		WorkRecordModel _model1 = create(company, project, resource, new Date(), 1, 10, true, "testWrongResourceId");
		_model1.setResourceId("");
		post(_model1, Status.BAD_REQUEST);
		_model1.setResourceId(null);
		post(_model1, Status.BAD_REQUEST);
		_model1.setResourceId("INVALID_RESOURCE_ID");
		post(_model1, Status.BAD_REQUEST);		
	}

	@Test
	public void testCompanyIdChange() {
		WorkRecordModel _model1 = post(
				create(company, project, resource, new Date(), 1, 10, true, "testCompanyIdChange"), Status.OK);
		_model1.setCompanyId(company2.getId());
		put(_model1, Status.BAD_REQUEST);
		delete(_model1.getId(), Status.NO_CONTENT);			
	}

	@Test
	public void testProjectIdChange() {
		WorkRecordModel _model1 = post(
				create(company, project, resource, new Date(), 1, 10, true, "testProjectIdChange"), Status.OK);
		_model1.setProjectId(project2.getId());
		put(_model1, Status.BAD_REQUEST);
		delete(_model1.getId(), Status.NO_CONTENT);			
	}

	@Test
	public void testResourceIdChange() {
		WorkRecordModel _model1 = post(
				create(company, project, resource, new Date(), 1, 10, true, "testResourceIdChange"), Status.OK);
		_model1.setResourceId(resource2.getId());
		put(_model1, Status.BAD_REQUEST);
		delete(_model1.getId(), Status.NO_CONTENT);			
	}

	@Test
	public void testDerivedFields() {
		CompanyModel _company = CompanyTest.post(wttWC, 
				new CompanyModel(CN + "1", "MY_DESC", org.getId()), Status.OK);
		ProjectModel _project = ProjectTest.post(wttWC, _company.getId(), 
				new ProjectModel(CN + "1", "MY_DESC1"), Status.OK);
		ResourceModel _resource = ResourceTest.post(resourceWC, 
				new ResourceModel(CN + "1", contact.getId()), Status.OK);
		WorkRecordModel _model1 = post(
				create(_company, _project, _resource, new Date(), 1, 10, true, "testDerivedFields"), Status.OK);
		assertEquals("companyTitle should be derived correctly", _company.getTitle(), _model1.getCompanyTitle());
		assertEquals("projectTitle should be derived correctly", _project.getTitle(), _model1.getProjectTitle());
		assertEquals("resourceName should be set correctly", _resource.getName(), _model1.getResourceName());
		_company.setTitle("NEW_COMPANY_TITLE");
		CompanyTest.put(wttWC, _company, Status.OK);
		_project.setTitle("NEW_PROJECT_TITLE");
		ProjectTest.put(wttWC, _company.getId(), _project, Status.OK);
		_resource.setName("NEW_RESOURCE_NAME");
		ResourceTest.put(resourceWC, _resource, Status.OK);
		_model1.setComment("changed");
		WorkRecordModel _model2 = put(_model1, Status.OK);
		assertEquals("id should not change", _model1.getId(), _model2.getId());
		assertEquals("comment should have changed", "changed", _model2.getComment());
		assertEquals("companyTitle should be derived correctly", "NEW_COMPANY_TITLE", _model2.getCompanyTitle());
		assertEquals("projectTitle should be derived correctly", "NEW_PROJECT_TITLE", _model2.getProjectTitle());
		assertEquals("resourceName should be set correctly", "NEW_RESOURCE_NAME", _model2.getResourceName());
		delete(_model1.getId(), Status.NO_CONTENT);	
		CompanyTest.delete(wttWC, _company.getId(), Status.NO_CONTENT);
		ResourceTest.delete(resourceWC, _resource.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testDoubleCreate(
	) {
		WorkRecordModel _model1 = post(
				create(company, project, resource, date, 1, 10, true, "testDoubleCreate1"),
				Status.OK);
		assertNotNull("ID should be set:", _model1.getId());		
		post(_model1, Status.CONFLICT);
		delete(_model1.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testRead(
	) {
		ArrayList<WorkRecordModel> _localList = new ArrayList<WorkRecordModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(
					create(company, project, resource, date, i, i*10, true, "testRead" + i),
					Status.OK));
		}
	
		for (WorkRecordModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}

		for (WorkRecordModel _model : list(null, Status.OK)) {
			assertEquals("ID should be unchanged when reading a workrecord",
					_model.getId(), get(_model.getId(), Status.OK).getId());
		}

		for (WorkRecordModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}	

	@Test
	public void testMultiRead(
	) {
		WorkRecordModel _model1 = post(
				create(company, project, resource, date, 1, 10, true, "testMultiRead1"),
				Status.OK);
		WorkRecordModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read", _model1.getId(), _model2.getId());
		WorkRecordModel _wrm3 = get(_model1.getId(), Status.OK);
		
		assertEquals("ID should be the same", _wrm3.getId(), _model2.getId());
		assertEquals("companyId should be the same", _wrm3.getCompanyId(), _model2.getCompanyId());
		assertEquals("companyTitle should be the same", _wrm3.getCompanyTitle(), _model2.getCompanyTitle());
		assertEquals("projectId should be the same", _wrm3.getProjectId(), _model2.getProjectId());
		assertEquals("projectTitle should be the same", _wrm3.getProjectTitle(), _model2.getProjectTitle());
		assertEquals("resourceId should be the same", _wrm3.getResourceId(), _model2.getResourceId());
		assertEquals("the startAt Date should be the same", _wrm3.getStartAt(), _model2.getStartAt());
		assertEquals("durationHours should be the same", _wrm3.getDurationHours(), _model2.getDurationHours());
		assertEquals("durationMinutes should be the same", _wrm3.getDurationMinutes(), _model2.getDurationMinutes());
		assertEquals("comment should be the same", _wrm3.getComment(), _model2.getComment());
		assertEquals("isBillable should be the same", _wrm3.isBillable(), _model2.isBillable());		
				
		assertEquals("ID should be the same", _model1.getId(), _model2.getId());
		assertEquals("companyId should be the same", _model1.getCompanyId(), _model2.getCompanyId());
		assertEquals("companyTitle should be the same", _model1.getCompanyTitle(), _model2.getCompanyTitle());
		assertEquals("projectId should be the same", _model1.getProjectId(), _model2.getProjectId());
		assertEquals("projectTitle should be the same", _model1.getProjectTitle(), _model2.getProjectTitle());
		assertEquals("resourceId should be the same", _model1.getResourceId(), _model2.getResourceId());
		assertEquals("the startAt Date should be the same", _model1.getStartAt(), _model2.getStartAt());
		assertEquals("durationHours should be the same", _model1.getDurationHours(), _model2.getDurationHours());
		assertEquals("durationMinutes should be the same", _model1.getDurationMinutes(), _model2.getDurationMinutes());
		assertEquals("comment should be the same", _model1.getComment(), _model2.getComment());
		assertEquals("isBillable should be the same", _model1.isBillable(), _model2.isBillable());		
		
		delete(_model1.getId(), Status.NO_CONTENT);
	}
		
	@Test
	public void testUpdate() {
		WorkRecordModel _model1 = post(
				create(company, project, resource, date, 1, 10, true, "testUpdate1"),
				Status.OK);
		
		Date _date2 = new Date(2000);
		_model1.setStartAt(_date2);
		_model1.setDurationHours(2);
		_model1.setDurationMinutes(20);
		_model1.setComment("testUpdate2");
		_model1.setBillable(false);
		
		WorkRecordModel _model2 = put(_model1, Status.OK);
		
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());
		assertEquals("companyId should be unchanged", _model1.getCompanyId(), _model2.getCompanyId());
		assertEquals("companyTitle should be unchanged", _model1.getCompanyTitle(), _model2.getCompanyTitle());
		assertEquals("projectId should be unchanged", _model1.getProjectId(), _model2.getProjectId());
		assertEquals("projectTitle should be unchanged", _model1.getProjectTitle(), _model2.getProjectTitle());
		assertEquals("resourceId should be unchanged", _model1.getResourceId(), _model2.getResourceId());
		assertEquals("the startAt Date should be set correctly", _date2, _model2.getStartAt());
		assertEquals("durationHours should be set correctly", 2, _model2.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 20, _model2.getDurationMinutes());
		assertEquals("comment should be set correctly", "testUpdate2", _model2.getComment());
		assertEquals("isBillable should be set correctly", false, _model2.isBillable());			

		Date _date4 = new Date(4000);
		_model1.setStartAt(_date4);
		_model1.setDurationHours(4);
		_model1.setDurationMinutes(40);
		_model1.setComment("testUpdate4");
		_model1.setBillable(true);
		
		WorkRecordModel _model3 = put(_model1, Status.OK);

		assertNotNull("ID should be set", _model3.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model3.getId());	
		assertEquals("companyId should be set correctly", _model1.getCompanyId(), _model3.getCompanyId());
		assertEquals("companyTitle should be set correctly", _model1.getCompanyTitle(), _model3.getCompanyTitle());
		assertEquals("projectId should be set correctly", _model1.getProjectId(), _model3.getProjectId());
		assertEquals("projectTitle should be set correctly", _model1.getProjectTitle(), _model3.getProjectTitle());
		assertEquals("resourceId should be set correctly", _model1.getResourceId(), _model3.getResourceId());
		assertEquals("the startAt Date should be set correctly", _date4, _model3.getStartAt());
		assertEquals("durationHours should be set correctly", 4, _model3.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 40, _model3.getDurationMinutes());
		assertEquals("comment should be set correctly", "testUpdate4", _model3.getComment());
		assertEquals("isBillable should be set correctly", true, _model3.isBillable());			
		
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDelete() {
		WorkRecordModel _model1 = post(
				create(company, project, resource, date, 1, 10, true, "testDelete"),
				Status.OK);
		WorkRecordModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a workrecord (remote):", _model1.getId(), _model2.getId());						
		delete(_model1.getId(), Status.NO_CONTENT);
		get(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		WorkRecordModel _model1 = post(
				create(company, project, resource, date, 1, 10, true, "testDoubleDelete"),
				Status.OK);
		get(_model1.getId(), Status.OK);
		delete(_model1.getId(), Status.NO_CONTENT);
		get(_model1.getId(), Status.NOT_FOUND);
		delete(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		WorkRecordModel _model1 = post(
				create(company, project, resource, date, 1, 10, true, "testModifications"),
				Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertTrue("modifiedAt should be greater than or equal to createdAt after create()", _model1.getModifiedAt().compareTo(_model1.getCreatedAt()) >= 0);
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		
		_model1.setDurationHours(2);
		WorkRecordModel _model2 = put(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		assertThat(_model2.getModifiedAt(), not(equalTo(_model2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?

		String _createdBy = _model1.getCreatedBy();
		_model1.setCreatedBy("testModifications");
		WorkRecordModel _model3 = put(_model1, Status.OK);	// update should ignore client-side generated createdBy
		assertEquals("update() should not change createdBy", _createdBy, _model3.getCreatedBy());
		_model1.setCreatedBy(_createdBy);

		Date _createdAt = _model1.getCreatedAt();
		_model1.setCreatedAt(new Date(1000));
		WorkRecordModel _model4 = put(_model1, Status.OK);	// update should ignore client-side generated createdAt
		assertEquals("update() should not change createdAt", _createdAt, _model4.getCreatedAt());
		_model1.setCreatedAt(_createdAt);
		
		String _modifiedBy = _model1.getModifiedBy();
		_model1.setModifiedBy("testModifications");
		WorkRecordModel _model5 = put(_model1, Status.OK);	// update should ignore client-side generated modifiedBy
		assertEquals("update() should not change modifiedBy", _modifiedBy, _model5.getModifiedBy());

		Date _modifiedAt = _model1.getModifiedAt();
		Date _modifiedAt2 = new Date(1000);
		_model1.setModifiedAt(_modifiedAt2);
		WorkRecordModel _wrm6 = put(_model1, Status.OK);	// update should ignore client-side generated modifiedAt
		assertThat(_wrm6.getModifiedAt(), not(equalTo(_modifiedAt)));
		assertThat(_wrm6.getModifiedAt(), not(equalTo(_modifiedAt2)));
		
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************** helper methods *********************************/	
	/**
	 * Retrieve a list of WorkRecordModel from WorkRecordsService by executing a HTTP GET request.
	 * This uses neither position nor size queries.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of WorkRecordModel objects in JSON format
	 */
	public List<WorkRecordModel> list(
			String query, 
			Status expectedStatus) {
		return list(wc, query, -1, Integer.MAX_VALUE, expectedStatus);
	}
	
	/**
	 * Retrieve a list of WorkRecordModel from WorkRecordsService by executing a HTTP GET request.
	 * @param webClient the WebClient for the WorkRecordsService
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of WorkRecordModel objects in JSON format
	 */
	public static List<WorkRecordModel> list(
			WebClient webClient, 
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		webClient.resetQuery();
		webClient.replacePath("/");
		Response _response = executeListQuery(webClient, query, position, size);
		List<WorkRecordModel> _list = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_list = new ArrayList<WorkRecordModel>(webClient.getCollection(WorkRecordModel.class));
			System.out.println("list(webClient, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ") ->" + _list.size());
		}
		return _list;
	}
	
	/**
	 * Create a new WorkRecordModel on the server by executing a HTTP POST request.
	 * @param model the data to post
	 * @param expectedStatus the expected HTTP status to test on; if this is null, it will not be tested
	 * @return the created data object
	 */
	private WorkRecordModel post(
			WorkRecordModel model,
			Status expectedStatus) {
		return post(wc, model, expectedStatus);
	}

	/**
	 * Create a new WorkRecordModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the service
	 * @param model the data to create on the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created data
	 */
	public static WorkRecordModel post(
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
	
	/**
	 * Create a new WorkRecordModel
	 * @param company
	 * @param project
	 * @param resource
	 * @param d
	 * @param hours
	 * @param mins
	 * @param isBillable
	 * @param testCaseName
	 * @return
	 */
	public static WorkRecordModel create(
			CompanyModel company,
			ProjectModel project,
			ResourceModel resource,
			Date d, 
			int hours, 
			int mins, 
			boolean isBillable,
			String testCaseName) 
	{
		WorkRecordModel _model = new WorkRecordModel(company, project, resource, d);
		_model.setDurationHours(hours);
		_model.setDurationMinutes(mins);
		_model.setBillable(isBillable);
		_model.setComment(testCaseName);
		return _model;
	}
		
	/**
	 * Read the WorkRecordModel with id from WorkRecordsService by executing a HTTP GET method.
	 * @param id the id of the WorkRecordModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved WorkRecordModel object in JSON format
	 */
	private WorkRecordModel get(
			String id, 
			Status expectedStatus) {
		return get(wc, id, expectedStatus);
	}
	
	/**
	 * Read the WorkRecordModel with id from WorkRecordsService by executing a HTTP GET method.
	 * @param webClient the web client representing the WorkRecordsService
	 * @param id the id of the WorkRecordModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved WorkRecordModel object in JSON format
	 */
	public static WorkRecordModel get(
			WebClient webClient,
			String id,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(id).get();
		if (expectedStatus != null) {
			assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(WorkRecordModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Update a WorkRecordModel on the WorkRecordsService by executing a HTTP PUT method.
	 * @param model the new WorkRecordModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated WorkRecordModel object in JSON format
	 */
	public WorkRecordModel put(
			WorkRecordModel model, 
			Status expectedStatus) {
		return put(wc, model, expectedStatus);
	}
	
	/**
	 * Update a WorkRecordModel on the WorkRecordsService by executing a HTTP PUT method.
	 * @param webClient the web client representing the WorkRecordsService
	 * @param model the new WorkRecordModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated WorkRecordModel object in JSON format
	 */
	public static WorkRecordModel put(
			WebClient webClient,
			WorkRecordModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(model.getId()).put(model);
		if (expectedStatus != null) {
			assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(WorkRecordModel.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Delete the WorkRecordModel with id on the WorkRecordsService by executing a HTTP DELETE method.
	 * @param id the id of the WorkRecordModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public void delete(String id, Status expectedStatus) {
		delete(wc, id, expectedStatus);
	}
	
	/**
	 * Delete the WorkRecordModel with id on the WorkRecordsService by executing a HTTP DELETE method.
	 * @param webClient the WebClient for the WorkRecordsService
	 * @param id the id of the WorkRecordModel object to delete
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
		return list(wc, null, 0, Integer.MAX_VALUE, Status.OK).size();
	}
}
