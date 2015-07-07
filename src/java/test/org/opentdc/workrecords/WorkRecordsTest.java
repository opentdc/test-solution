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
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.rates.Currency;
import org.opentdc.rates.RatesModel;
import org.opentdc.resources.ResourceModel;
import org.opentdc.workrecords.WorkRecordModel;
import org.opentdc.workrecords.WorkRecordsService;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.ContactTest;
import test.org.opentdc.rates.RatesTest;
import test.org.opentdc.resources.ResourcesTest;
import test.org.opentdc.wtt.CompanyTest;
import test.org.opentdc.wtt.ProjectTest;

public class WorkRecordsTest extends AbstractTestClient {
	public static final String API_URL = "api/workrecord/";
	private Date date;
	private WebClient workRecordWC = null;
	private WebClient wttWC = null;
	private WebClient addressbookWC = null;
	private WebClient resourceWC = null;
	private WebClient rateWC = null;
	private CompanyModel company = null;
	private CompanyModel company2 = null;
	private ProjectModel project = null;
	private ProjectModel project2 = null;
	private AddressbookModel addressbook = null;
	private ResourceModel resource = null;
	private ResourceModel resource2 = null;
	private RatesModel rate = null;
	private RatesModel rate2 = null;
	private ContactModel contact = null;

	@Before
	public void initializeTests() {
		date = new Date();
		workRecordWC = WorkRecordsTest.createWorkRecordsWebClient();
		wttWC = CompanyTest.createWttWebClient();
		resourceWC = ResourcesTest.createResourcesWebClient();
		addressbookWC = AddressbookTest.createAddressbookWebClient();
		rateWC = RatesTest.createRatesWebClient();

		addressbook = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName());
		company = CompanyTest.createCompany(wttWC, addressbookWC, addressbook, this.getClass().getName(), "MY_DESC");
		company2 = CompanyTest.createCompany(wttWC, addressbookWC, addressbook, this.getClass().getName(), "MY_DESC2");
		project = ProjectTest.createProject(wttWC, company.getId(), this.getClass().getName(), "MY_DESC");
		project2 = ProjectTest.createProject(wttWC, company2.getId(), this.getClass().getName(), "MY_DESC2");
		contact = ContactTest.createContact(addressbookWC, addressbook.getId(), "FNAME", "LNAME");
		resource = ResourcesTest.createResource(resourceWC, addressbookWC, 
				this.getClass().getName(), "FNAME", "LNAME", addressbook.getId(), contact.getId());
		resource2 = ResourcesTest.createResource(resourceWC, addressbookWC, 
				this.getClass().getName(), "FNAME2", "LNAME2", addressbook.getId(), contact.getId());
		rate = RatesTest.createRate(rateWC, this.getClass().getName(), 100, Currency.CHF, "MY_DESC");
		rate2 = RatesTest.createRate(rateWC, this.getClass().getName(), 200, Currency.EUR, "MY_DESC2");
	}
	
	@After
	public void cleanupTest() {
		AddressbookTest.cleanup(addressbookWC, addressbook.getId(), this.getClass().getName());
		ResourcesTest.cleanup(resourceWC, resource.getId(), this.getClass().getName(), false);
		ResourcesTest.cleanup(resourceWC, resource2.getId(), this.getClass().getName(), true);
		CompanyTest.cleanup(wttWC, company.getId(), this.getClass().getName(), false);
		CompanyTest.cleanup(wttWC, company2.getId(), this.getClass().getName(), true);
		RatesTest.cleanup(rateWC, rate.getId(), this.getClass().getName(), false);
		RatesTest.cleanup(rateWC, rate2.getId(), this.getClass().getName(), true);
		workRecordWC.close();
	}

	/********************************** workrecord attributes tests *********************************/	
	@Test
	public void testWorkRecordModelEmptyConstructor() {
		// new() -> _wrm
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("id should not be set by empty constructor", _wrm.getId());
		assertNull("companyId should not be set by empty constructor", _wrm.getCompanyId());
		assertNull("companyTitle should not be set by empty constructor", _wrm.getCompanyTitle());
		assertNull("projectId should not be set by empty constructor", _wrm.getProjectId());
		assertNull("projectTitle should not be set by empty constructor", _wrm.getProjectTitle());
		assertNull("resourceId should not be set by empty constructor", _wrm.getResourceId());
		assertNull("rateId should not be set by empty constructor", _wrm.getRateId());
		assertNull("startAt should not be set by empty constructor", _wrm.getStartAt());
		assertEquals("durationHours should be set on default initial value", 1, _wrm.getDurationHours());
		assertEquals("durationMinutes should be set on default initial value", 30, _wrm.getDurationMinutes());
		assertNull("comment should not be set by empty constructor", _wrm.getComment());
		assertEquals("isBillable should be set on default initial value", true, _wrm.isBillable());
	}

	@Test
	public void testWorkRecordModelConstructor() {		
		WorkRecordModel _wrm = createWorkRecord("CID1", "PID1", "RID1", "RATEID1", 1, date, 3, 45, false);
		assertNull("id should not be set by constructor", _wrm.getId());
		assertEquals("companyId should be set by constructor", "CID1", _wrm.getCompanyId());
		assertEquals("companyTitle should be set by constructor", "CTITLE1", _wrm.getCompanyTitle());
		assertEquals("projectId should be set by constructor", "PID1", _wrm.getProjectId());
		assertEquals("projectTitle should be set by constructor", "PTITLE1", _wrm.getProjectTitle());
		assertEquals("resourceId should be set by constructor", "RID1", _wrm.getResourceId());
		assertEquals("rateId should be set by constructor", "RATEID1", _wrm.getRateId());
		assertEquals("startAt should be set by constructor", date.toString(), _wrm.getStartAt().toString());
		assertEquals("durationHours should be set by constructor", 3, _wrm.getDurationHours());
		assertEquals("durationMinutes should be set by constructor", 45, _wrm.getDurationMinutes());
		assertEquals("comment should be set by constructor", "MY_COMMENT1", _wrm.getComment());
		assertEquals("isBillable should be set by constructor", false, _wrm.isBillable());
	}

	@Test
	public void testIdAttributeChange() {
		// new() -> _wrm -> _wrm.setId()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("id should not be set by constructor", _wrm.getId());
		_wrm.setId("MY_ID");
		assertEquals("id should have changed", "MY_ID", _wrm.getId());
	}

	@Test
	public void testCompanyIdAttributeChange() {
		// new() -> _wrm -> _wrm.setCompanyId()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("companyId should not be set by empty constructor", _wrm.getCompanyId());
		_wrm.setCompanyId("CID");
		assertEquals("companyId should have changed", "CID", _wrm.getCompanyId());
	}
	
	@Test
	public void testCompanyTitleAttributeChange() {
		// new() -> _wrm -> _wrm.setCompanyTitle()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("CompanyTitle should not be set by empty constructor", _wrm.getCompanyTitle());
		_wrm.setCompanyTitle("CTITLE");
		assertEquals("CompanyTitle should have changed", "CTITLE", _wrm.getCompanyTitle());
	}
	
	@Test
	public void testProjectIdAttributeChange() {
		// new() -> _wrm -> _wrm.setProjectId()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("projectId should not be set by empty constructor", _wrm.getProjectId());
		_wrm.setProjectId("PID");
		assertEquals("projectId should have changed", "PID", _wrm.getProjectId());
	}
	
	@Test
	public void testProjectTitleAttributeChange() {
		// new() -> _wrm -> _wrm.setProjectTitle()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("projectTitle should not be set by empty constructor", _wrm.getProjectTitle());
		_wrm.setProjectTitle("PTITLE");
		assertEquals("projectTitle should have changed", "PTITLE", _wrm.getProjectTitle());
	}
	
	@Test
	public void testResourceIdAttributeChange() {
		// new() -> _wrm -> _wrm.setResourceId()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("resourceId should not be set by empty constructor", _wrm.getResourceId());
		_wrm.setResourceId("RID");
		assertEquals("resourceId should have changed", "RID", _wrm.getResourceId());
	}
		
	@Test
	public void testRateIdAttributeChange() {
		// new() -> _wrm -> _wrm.setRateId()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("rateId should not be set by empty constructor", _wrm.getRateId());
		_wrm.setRateId("RATEID");
		assertEquals("rateId should have changed", "RATEID", _wrm.getRateId());
	}
	
	@Test
	public void testStartAtAttributeChange() {
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("startAt should not be set by empty constructor", _wrm.getStartAt());
		Date _d = new Date();
		_wrm.setStartAt(_d);
		assertEquals("startAt should have changed", _d.toString(), _wrm.getStartAt().toString());
	}
		
	@Test
	public void testDurationHoursAttributeChange() {
		// new() -> _wrm -> _wrm.setDurationHours()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertEquals("durationHours should be initialized to default value by empty constructor", 1, _wrm.getDurationHours());
		_wrm.setDurationHours(3);
		assertEquals("durationHours should have changed", 3, _wrm.getDurationHours());
	}
	
	@Test
	public void testDurationMinutesAttributeChange() {
		// new() -> _wrm -> _wrm.setDurationMinutes()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertEquals("durationMinutes should be initialized to default value by empty constructor", 30, _wrm.getDurationMinutes());
		_wrm.setDurationMinutes(45);
		assertEquals("durationMinutes should have changed", 45, _wrm.getDurationMinutes());
	}
	
	@Test
	public void testCommentAttributeChange() {
		// new() -> _wrm -> _wrm.setComment()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("comment should not be set by empty constructor", _wrm.getComment());
		_wrm.setComment("MY_COMMENT");
		assertEquals("comment should have changed", "MY_COMMENT", _wrm.getComment());
	}
	
	@Test
	public void testIsBillableAttributeChange() {
		// new() -> _wrm -> _wrm.setBillable()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertEquals("isBillable should be initialized to default value by empty constructor", true, _wrm.isBillable());
		_wrm.setBillable(false);
		assertEquals("isBillable should have changed", false, _wrm.isBillable());
	}
	
	@Test
	public void testWorkRecordCreatedBy() {
		// new() -> _wrm -> _wrm.setCreatedBy()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("createdBy should not be set by empty constructor", _wrm.getCreatedBy());
		_wrm.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _wrm.getCreatedBy());	
	}
	
	@Test
	public void testWorkRecordCreatedAt() {
		// new() -> _wrm -> _wrm.setCreatedAt()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("createdAt should not be set by empty constructor", _wrm.getCreatedAt());
		_wrm.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _wrm.getCreatedAt());
	}
		
	@Test
	public void testWorkRecordModifiedBy() {
		// new() -> _wrm -> _wrm.setModifiedBy()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("modifiedBy should not be set by empty constructor", _wrm.getModifiedBy());
		_wrm.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _wrm.getModifiedBy());	
	}
	
	@Test
	public void testWorkRecordModifiedAt() {
		// new() -> _wrm -> _wrm.setModifiedAt()
		WorkRecordModel _wrm = new WorkRecordModel();
		assertNull("modifiedAt should not be set by empty constructor", _wrm.getModifiedAt());
		_wrm.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _wrm.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testWorkRecordCreateReadDeleteWithEmptyConstructor() {
		// new() -> _wrm1
		WorkRecordModel _wrm1 = new WorkRecordModel();
		assertNull("id should not be set by empty constructor", _wrm1.getId());
		assertNull("companyId should not be set by empty constructor", _wrm1.getCompanyId());
		assertNull("companyTitle should not be set by empty constructor", _wrm1.getCompanyTitle());
		assertNull("projectId should not be set by empty constructor", _wrm1.getProjectId());
		assertNull("projectTitle should not be set by empty constructor", _wrm1.getProjectTitle());
		assertNull("resourceId should not be set by empty constructor", _wrm1.getResourceId());
		assertNull("rateId should not be set by empty constructor", _wrm1.getRateId());
		assertNull("startAt should not be set by empty constructor", _wrm1.getStartAt());
		assertEquals("durationHours should be set on default initial value", 1, _wrm1.getDurationHours());
		assertEquals("durationMinutes should be set on default initial value", 30, _wrm1.getDurationMinutes());
		assertNull("comment should not be set by empty constructor", _wrm1.getComment());
		assertEquals("isBillable should be set on default initial value", true, _wrm1.isBillable());
		
		// create(_wrm1) -> BAD_REQUEST (because of empty companyId)
		Response _response = workRecordWC.replacePath("/").post(_wrm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_wrm1.setCompanyId(company.getId());

		// create(_wrm1) -> BAD_REQUEST (because of empty companyTitle)
		_response = workRecordWC.replacePath("/").post(_wrm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_wrm1.setCompanyTitle("CTITLE");

		// create(_wrm1) -> BAD_REQUEST (because of empty projectId)
		_response = workRecordWC.replacePath("/").post(_wrm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_wrm1.setProjectId(project.getId());
		
		// create(_wrm1) -> BAD_REQUEST (because of empty projectTitle)
		_response = workRecordWC.replacePath("/").post(_wrm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_wrm1.setProjectTitle("PTITLE");

		// create(_wrm1) -> BAD_REQUEST (because of empty resourceId)
		_response = workRecordWC.replacePath("/").post(_wrm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_wrm1.setResourceId(resource.getId());

		// create(_wrm1) -> BAD_REQUEST (because of empty rateId)
		_response = workRecordWC.replacePath("/").post(_wrm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_wrm1.setRateId(rate.getId());

		// create(_wrm1) -> BAD_REQUEST (because of empty startAt)
		_response = workRecordWC.replacePath("/").post(_wrm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		Date _date = new Date();
		_wrm1.setStartAt(_date);

		// create(_wrm1) -> _wrm2
		_response = workRecordWC.replacePath("/").post(_wrm1);		
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm2 = _response.readEntity(WorkRecordModel.class);
		
		// validate _wrm1
		assertNull("create() should not change the id of the local object", _wrm1.getId());
		assertEquals("create() should not change the companyId of the local object", company.getId(), _wrm1.getCompanyId());
//		assertEquals("create() should not change the companyTitle of the local object", "CTITLE", _wrm1.getCompanyTitle());
		assertEquals("create() should not change the projectId of the local object", project.getId(), _wrm1.getProjectId());
//		assertEquals("create() should not change the projectTitle of the local object", "PTITLE", _wrm1.getProjectTitle());
		assertEquals("create() should not change the resourceId of the local object", resource.getId(), _wrm1.getResourceId());
		assertEquals("create() should not change the rateId of the local object", rate.getId(), _wrm1.getRateId());
		assertEquals("create() should not change the startAt Date of the local object", _date.toString(), _wrm1.getStartAt().toString());
		assertEquals("create() should not change durationHours on the local object", 1, _wrm1.getDurationHours());
		assertEquals("create() should not change durationMinutes on the local object", 30, _wrm1.getDurationMinutes());
		assertNull("create() should not change comment on the local object", _wrm1.getComment());
		assertEquals("create() should not change isBillable on the local object", true, _wrm1.isBillable());
		
		// validate _wrm2
		assertNotNull("create() should set a valid id on the remote object returned", _wrm2.getId());
		assertEquals("create() should not change the companyId", company.getId(), _wrm2.getCompanyId());
//		assertEquals("create() should not change the companyTitle", "CTITLE", _wrm2.getCompanyTitle());
		assertEquals("create() should not change the projectId", project.getId(), _wrm2.getProjectId());
//		assertEquals("create() should not change the projectTitle", "PTITLE", _wrm2.getProjectTitle());
		assertEquals("create() should not change the resourceId", resource.getId(), _wrm2.getResourceId());
		assertEquals("create() should not change the rateId", rate.getId(), _wrm2.getRateId());
		assertEquals("create() should not change the startAt Date", _date.toString(), _wrm2.getStartAt().toString());
		assertEquals("create() should not change durationHours", 1, _wrm2.getDurationHours());
		assertEquals("create() should not change durationMinutes", 30, _wrm2.getDurationMinutes());
		assertEquals("create() should not change comment", null, _wrm2.getComment());
		assertEquals("create() should not change isBillable", true, _wrm2.isBillable());

		// read(_wrm2) -> _wrm3
		_response = workRecordWC.replacePath("/").path(_wrm2.getId()).get();
		assertEquals("read(" + _wrm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm3 = _response.readEntity(WorkRecordModel.class);
		assertEquals("id of returned object should be the same", _wrm2.getId(), _wrm3.getId());
		assertEquals("companyId of returned object should be unchanged after remote create", _wrm2.getCompanyId(), _wrm3.getCompanyId());
//		assertEquals("companyTitle of returned object should be unchanged after remote create", _wrm2.getCompanyTitle(), _wrm3.getCompanyTitle());
		assertEquals("projectId of returned object should be unchanged after remote create", _wrm2.getProjectId(), _wrm3.getProjectId());
//		assertEquals("projectTitle of returned object should be unchanged after remote create", _wrm2.getProjectTitle(), _wrm3.getProjectTitle());
		assertEquals("resourceId of returned object should be unchanged after remote create", _wrm2.getResourceId(), _wrm3.getResourceId());
		assertEquals("rateId of returned object should be unchanged after remote create", _wrm2.getRateId(), _wrm3.getRateId());
		assertEquals("startAt of returned object should be unchanged after remote create", _wrm2.getStartAt(), _wrm3.getStartAt());
		assertEquals("durationHours of returned object should be unchanged after remote create", _wrm2.getDurationHours(), _wrm3.getDurationHours());
		assertEquals("durationMinutes of returned object should be unchanged after remote create", _wrm2.getDurationMinutes(), _wrm3.getDurationMinutes());
		assertEquals("comment of returned object should be unchanged after remote create", _wrm2.getComment(), _wrm3.getComment());
		assertEquals("isBillable of returned object should be unchanged after remote create", _wrm2.isBillable(), _wrm3.isBillable());

		// delete(_wrm3)
		_response = workRecordWC.replacePath("/").path(_wrm3.getId()).delete();
		assertEquals("delete(" + _wrm3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordCreateReadDelete() {
		// new() -> _wrm1
		WorkRecordModel _wrm1 = createWorkRecord(
				company.getId(), project.getId(), resource.getId(), rate.getId(), 1, date, 4, 20, true);
		
		// validate _wrm1
		assertNull("id should not be set by constructor", _wrm1.getId());
		assertEquals("companyId should be set by constructor", company.getId(), _wrm1.getCompanyId());
		assertEquals("companyTitle should be set by constructor", "CTITLE1", _wrm1.getCompanyTitle());
		assertEquals("projectId should be set by constructor", project.getId(), _wrm1.getProjectId());
		assertEquals("projectTitle should be set by constructor", "PTITLE1", _wrm1.getProjectTitle());
		assertEquals("resourceId should be set by constructor", resource.getId(), _wrm1.getResourceId());
		assertEquals("rateId should be set by constructor", rate.getId(), _wrm1.getRateId());
		assertEquals("startAt should be set by constructor", date, _wrm1.getStartAt());
		assertEquals("durationHours should be set by constructor", 4, _wrm1.getDurationHours());
		assertEquals("durationMinutes should be set by constructor", 20, _wrm1.getDurationMinutes());
		assertEquals("comment should be set by constructor", "MY_COMMENT1", _wrm1.getComment());
		assertEquals("isBillable should be set by constructor", true, _wrm1.isBillable());
		
		// create(_wrm1) -> _wrm2
		Response _response = workRecordWC.replacePath("/").post(_wrm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm2 = _response.readEntity(WorkRecordModel.class);
		
		// validate _wrm1 (after create())
		assertNull("create() should not change the id of the local object", _wrm1.getId());
		assertEquals("create() should not change the companyId of the local object", company.getId(), _wrm1.getCompanyId());
		assertEquals("create() should not change the companyTitle of the local object", "CTITLE1", _wrm1.getCompanyTitle());
		assertEquals("create() should not change the projectId of the local object", project.getId(), _wrm1.getProjectId());
		assertEquals("create() should not change the projectTitle of the local object", "PTITLE1", _wrm1.getProjectTitle());
		assertEquals("create() should not change the resourceId of the local object", resource.getId(), _wrm1.getResourceId());
		assertEquals("create() should not change the rateId of the local object", rate.getId(), _wrm1.getRateId());
		assertEquals("create() should not change the startAt Date of the local object", date, _wrm1.getStartAt());
		assertEquals("create() should not change durationHours on the local object", 4, _wrm1.getDurationHours());
		assertEquals("create() should not change durationMinutes on the local object", 20, _wrm1.getDurationMinutes());
		assertEquals("create() should not change comment on the local object", "MY_COMMENT1", _wrm1.getComment());
		assertEquals("create() should not change isBillable on the local object", true, _wrm1.isBillable());
		
		// validate _wrm2
		assertNotNull("id of returned object should be set", _wrm2.getId());
		assertEquals("companyId of returned object should be unchanged after remote create", company.getId(), _wrm2.getCompanyId());
//		assertEquals("companyTitle of returned object should be unchanged after remote create", "CTITLE1", _wrm2.getCompanyTitle());
		assertEquals("projectId of returned object should be unchanged after remote create", project.getId(), _wrm2.getProjectId());
//		assertEquals("projectTitle of returned object should be unchanged after remote create", "PTITLE1", _wrm2.getProjectTitle());
		assertEquals("resourceId of returned object should be unchanged after remote create", resource.getId(), _wrm2.getResourceId());
		assertEquals("rateId of returned object should be unchanged after remote create", rate.getId(), _wrm2.getRateId());
		assertEquals("create() should not change the startAt Date of the local object", date, _wrm2.getStartAt());
		assertEquals("durationHours of returned object should be unchanged", 4, _wrm2.getDurationHours());
		assertEquals("durationMinutes of returned object should be unchanged", 20, _wrm2.getDurationMinutes());
		assertEquals("comment of returned object should be unchanged", "MY_COMMENT1", _wrm2.getComment());
		assertEquals("create() should not change isBillable", true, _wrm2.isBillable());

		// read(_wrm2)  -> _wrm3
		_response = workRecordWC.replacePath("/").path(_wrm2.getId()).get();
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
		_response = workRecordWC.replacePath("/").path(_wrm3.getId()).delete();
		assertEquals("delete(" + _wrm3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateWorkRecordWithClientSideId() {
		// new(1) -> _wrm1 -> _wrm1.setId()
		WorkRecordModel _wrm1 = createWorkRecord(
				company.getId(), project.getId(), resource.getId(), rate.getId(), 1, date, 4, 20, true);

		_wrm1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _wrm1.getId());
		// create(_c1) -> BAD_REQUEST
		Response _response = workRecordWC.replacePath("/").post(_wrm1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateWorkRecordWithDuplicateId() {
		// create(new(1)) -> _wrm2
		WorkRecordModel _wrm1 = createWorkRecord(
				company.getId(), project.getId(), resource.getId(), rate.getId(), 1, date, 4, 20, true);

		Response _response = workRecordWC.replacePath("/").post(_wrm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm2 = _response.readEntity(WorkRecordModel.class);

		// new(3) -> _wrm3 -> _wrm3.setId(_wrm2.getId())
		WorkRecordModel _wrm3 = createWorkRecord(
				company.getId(), project.getId(), resource.getId(), rate.getId(), 3, date, 1, 30, true);

		_wrm3.setId(_wrm2.getId());		// wrongly create a 2nd WorkRecordModel object with the same ID
		
		// create(_wrm3) -> CONFLICT
		_response = workRecordWC.replacePath("/").post(_wrm3);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_wrm2)
		_response = workRecordWC.replacePath("/").path(_wrm2.getId()).delete();
		assertEquals("delete(" + _wrm2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordList(
	) {		
		ArrayList<WorkRecordModel> _localList = new ArrayList<WorkRecordModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			// create(new(i)) -> _localList
			_response = workRecordWC.replacePath("/").post(createWorkRecord(
					company.getId(), project.getId(), resource.getId(), rate.getId(), i, date, i, i, true));

			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(WorkRecordModel.class));
		}
		
		// list(/) -> _remoteList
		_response = workRecordWC.replacePath("/").get();
		List<WorkRecordModel> _remoteList = new ArrayList<WorkRecordModel>(workRecordWC.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (WorkRecordModel _c : _remoteList) {
			_remoteListIds.add(_c.getId());
		}
		
		for (WorkRecordModel _c : _localList) {
			assertTrue("workrecord <" + _c.getId() + "> should be listed", _remoteListIds.contains(_c.getId()));
		}
		for (WorkRecordModel _c : _localList) {
			_response = workRecordWC.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(WorkRecordModel.class);
		}
		for (WorkRecordModel _c : _localList) {
			_response = workRecordWC.replacePath("/").path(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}
		
	@Test
	public void testWorkRecordCreate() {
		// new(1) -> _wrm1
		Date _date1 = new Date(1000);
		WorkRecordModel _wrm1 = createWorkRecord(
				company.getId(), project.getId(), resource.getId(), rate.getId(), 1, _date1, 1, 10, true);
		// new(2) -> _wrm2
		Date _date2 = new Date(2000);
		WorkRecordModel _wrm2 = createWorkRecord(
				company2.getId(), project2.getId(), resource2.getId(), rate2.getId(), 2, _date2, 2, 20, false);
		
		// create(_wrm1)  -> _wrm3
		Response _response = workRecordWC.replacePath("/").post(_wrm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm3 = _response.readEntity(WorkRecordModel.class);

		// create(_wrm2) -> _wrm4
		_response = workRecordWC.replacePath("/").post(_wrm2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm4 = _response.readEntity(WorkRecordModel.class);		
		assertNotNull("ID should be set", _wrm3.getId());
		assertNotNull("ID should be set", _wrm4.getId());
		assertThat(_wrm4.getId(), not(equalTo(_wrm3.getId())));

		// examine _c3
		assertEquals("companyId should be set correctly", company.getId(), _wrm3.getCompanyId());
//		assertEquals("companyTitle should be set correctly", "CTITLE1", _wrm3.getCompanyTitle());
		assertEquals("projectId should be set correctly", project.getId(), _wrm3.getProjectId());
//		assertEquals("projectTitle should be set correctly", "PTITLE1", _wrm3.getProjectTitle());
		assertEquals("resourceId should be set correctly", resource.getId(), _wrm3.getResourceId());
		assertEquals("rateId should be set correctly", rate.getId(), _wrm3.getRateId());
		assertEquals("the startAt Date should be set correctly", _date1, _wrm3.getStartAt());
		assertEquals("durationHours should be set correctly", 1, _wrm3.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 10, _wrm3.getDurationMinutes());
		assertEquals("comment should be set correctly", "MY_COMMENT1", _wrm3.getComment());
		assertEquals("isBillable should be set correctly", true, _wrm3.isBillable());			
		
		// examine _c4
		assertEquals("companyId should be set correctly", company2.getId(), _wrm4.getCompanyId());
//		assertEquals("companyTitle should be set correctly", "CTITLE2", _wrm4.getCompanyTitle());
		assertEquals("projectId should be set correctly", project2.getId(), _wrm4.getProjectId());
//		assertEquals("projectTitle should be set correctly", "PTITLE2", _wrm4.getProjectTitle());
		assertEquals("resourceId should be set correctly", resource2.getId(), _wrm4.getResourceId());
		assertEquals("rateId should be set correctly", rate2.getId(), _wrm4.getRateId());
		assertEquals("the startAt Date should be set correctly", _date2, _wrm4.getStartAt());
		assertEquals("durationHours should be set correctly", 2, _wrm4.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 20, _wrm4.getDurationMinutes());
		assertEquals("comment should be set correctly", "MY_COMMENT2", _wrm4.getComment());
		assertEquals("isBillable should be set correctly", false, _wrm4.isBillable());			
		
		// delete(_c3) -> NO_CONTENT
		_response = workRecordWC.replacePath("/").path(_wrm3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_c4) -> NO_CONTENT
		_response = workRecordWC.replacePath("/").path(_wrm4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordDoubleCreate(
	) {
		// create(new()) -> _wrm1
		Response _response = workRecordWC.replacePath("/").post(
				createWorkRecord(
						company.getId(), project.getId(), resource.getId(), rate.getId(), 1, date, 1, 10, true));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm1 = _response.readEntity(WorkRecordModel.class);
		assertNotNull("ID should be set:", _wrm1.getId());		
		
		// create(_wrm1) -> CONFLICT
		_response = workRecordWC.replacePath("/").post(_wrm1);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_wrm1) -> NO_CONTENT
		_response = workRecordWC.replacePath(_wrm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testWorkRecordRead(
	) {
		ArrayList<WorkRecordModel> _localList = new ArrayList<WorkRecordModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			_response = workRecordWC.replacePath("/").post(
					createWorkRecord(
							company.getId(), project.getId(), resource.getId(), rate.getId(), i, date, i, i*10, true));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(WorkRecordModel.class));
		}
	
		// test read on each local element
		for (WorkRecordModel _c : _localList) {
			_response = workRecordWC.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(WorkRecordModel.class);
		}

		// test read on each listed element
		_response = workRecordWC.replacePath("/").get();
		List<WorkRecordModel> _remoteList = new ArrayList<WorkRecordModel>(workRecordWC.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		WorkRecordModel _tmpObj = null;
		for (WorkRecordModel _c : _remoteList) {
			_response = workRecordWC.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(WorkRecordModel.class);
			assertEquals("ID should be unchanged when reading a workrecord", _c.getId(), _tmpObj.getId());						
		}

		for (WorkRecordModel _c : _localList) {
			_response = workRecordWC.replacePath("/").path(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testWorkRecordMultiRead(
	) {
		// new() -> _wrm1
		WorkRecordModel _wrm1 = createWorkRecord(
				company.getId(), project.getId(), resource.getId(), rate.getId(), 1, date, 1, 10, true);
		
		// create(_wrm1) -> _wrm2
		Response _response = workRecordWC.replacePath("/").post(_wrm1);
		WorkRecordModel _wrm2 = _response.readEntity(WorkRecordModel.class);

		// read(_wrm2) -> _wrm3
		_response = workRecordWC.replacePath("/").path(_wrm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm3 = _response.readEntity(WorkRecordModel.class);
		assertEquals("ID should be unchanged after read", _wrm2.getId(), _wrm3.getId());		

		// read(_wrm2) -> _wrm4
		_response = workRecordWC.replacePath("/").path(_wrm2.getId()).get();
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
		_response = workRecordWC.replacePath("/").path(_wrm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordUpdate() {
		// new() -> _wrm1
		WorkRecordModel _wrm1 = createWorkRecord(
				company.getId(), project.getId(), resource.getId(), rate.getId(), 1, date, 1, 10, true);
		
		// create(_wrm1) -> _wrm2
		Response _response = workRecordWC.replacePath("/").post(_wrm1);
		WorkRecordModel _wrm2 = _response.readEntity(WorkRecordModel.class);
		
		// change the attributes
		// update(_wrm2) -> _wrm3
//		_wrm2.setCompanyId(company2.getId());
//		_wrm2.setCompanyTitle("CTITLE2");
//		_wrm2.setProjectId(project2.getId());
//		_wrm2.setProjectTitle("PTITLE2");
//		_wrm2.setResourceId(resource2.getId());
		_wrm2.setRateId(rate2.getId());
		Date _date2 = new Date(2000);
		_wrm2.setStartAt(_date2);
		_wrm2.setDurationHours(2);
		_wrm2.setDurationMinutes(20);
		_wrm2.setComment("MY_COMMENT2");
		_wrm2.setBillable(false);
		
		workRecordWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = workRecordWC.replacePath("/").path(_wrm2.getId()).put(_wrm2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm3 = _response.readEntity(WorkRecordModel.class);

		assertNotNull("ID should be set", _wrm3.getId());
		assertEquals("ID should be unchanged", _wrm2.getId(), _wrm3.getId());
//		assertEquals("companyId should be set correctly", company2.getId(), _wrm3.getCompanyId());
//		assertEquals("companyTitle should be set correctly", "CTITLE2", _wrm3.getCompanyTitle());
//		assertEquals("projectId should be set correctly", project2.getId(), _wrm3.getProjectId());
//		assertEquals("projectTitle should be set correctly", "PTITLE2", _wrm3.getProjectTitle());
//		assertEquals("resourceId should be set correctly", resource2.getId(), _wrm3.getResourceId());
//		assertEquals("rateId should be set correctly", rate2.getId(), _wrm3.getRateId());
		assertEquals("the startAt Date should be set correctly", _date2, _wrm3.getStartAt());
		assertEquals("durationHours should be set correctly", 2, _wrm3.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 20, _wrm3.getDurationMinutes());
		assertEquals("comment should be set correctly", "MY_COMMENT2", _wrm3.getComment());
		assertEquals("isBillable should be set correctly", false, _wrm3.isBillable());			

		// reset the attributes
		// update(_wrm2) -> _wrm4
		_wrm2.setCompanyId(company.getId());
		_wrm2.setCompanyTitle("CTITLE4");
		_wrm2.setProjectId(project.getId());
		_wrm2.setProjectTitle("PTITLE4");
		_wrm2.setResourceId(resource.getId());
		_wrm2.setRateId(rate.getId());
		Date _date4 = new Date(4000);
		_wrm2.setStartAt(_date4);
		_wrm2.setDurationHours(4);
		_wrm2.setDurationMinutes(40);
		_wrm2.setComment("MY_COMMENT4");
		_wrm2.setBillable(true);

		workRecordWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = workRecordWC.replacePath("/").path(_wrm2.getId()).put(_wrm2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm4 = _response.readEntity(WorkRecordModel.class);

		assertNotNull("ID should be set", _wrm4.getId());
		assertEquals("ID should be unchanged", _wrm2.getId(), _wrm4.getId());	
		assertEquals("companyId should be set correctly", company.getId(), _wrm4.getCompanyId());
//		assertEquals("companyTitle should be set correctly", "CTITLE4", _wrm4.getCompanyTitle());
		assertEquals("projectId should be set correctly", project.getId(), _wrm4.getProjectId());
//		assertEquals("projectTitle should be set correctly", "PTITLE4", _wrm4.getProjectTitle());
		assertEquals("resourceId should be set correctly", resource.getId(), _wrm4.getResourceId());
		assertEquals("rateId should be set correctly", rate.getId(), _wrm4.getRateId());
		assertEquals("the startAt Date should be set correctly", _date4, _wrm4.getStartAt());
		assertEquals("durationHours should be set correctly", 4, _wrm4.getDurationHours());
		assertEquals("durationMinutes should be set correctly", 40, _wrm4.getDurationMinutes());
		assertEquals("comment should be set correctly", "MY_COMMENT4", _wrm4.getComment());
		assertEquals("isBillable should be set correctly", true, _wrm4.isBillable());			
		
		_response = workRecordWC.replacePath("/").path(_wrm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordDelete(
	) {
		// create(1) -> _wrm1
		Response _response = workRecordWC.replacePath("/").post(createWorkRecord(
				company.getId(), project.getId(), resource.getId(), rate.getId(), 1, date, 1, 10, true));

		WorkRecordModel _wrm1 = _response.readEntity(WorkRecordModel.class);
		
		// read(_wrm1) -> _wrm2
		_response = workRecordWC.replacePath("/").path(_wrm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm2 = _response.readEntity(WorkRecordModel.class);
		assertEquals("ID should be unchanged when reading a workrecord (remote):", _wrm1.getId(), _wrm2.getId());						
		
		// delete(_wrm1) -> OK
		_response = workRecordWC.replacePath("/").path(_wrm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_wrm1) -> NOT_FOUND
		_response = workRecordWC.replacePath("/").path(_wrm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_wrm1) -> NOT_FOUND
		_response = workRecordWC.replacePath("/").path(_wrm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordDoubleDelete(
	) {
		// create(1) -> _wrm1
		Response _response = workRecordWC.replacePath("/").post(createWorkRecord(
				company.getId(), project.getId(), resource.getId(), rate.getId(), 1, date, 1, 10, true));

		WorkRecordModel _wrm1 = _response.readEntity(WorkRecordModel.class);

		// read(_wrm1) -> OK
		_response = workRecordWC.replacePath("/").path(_wrm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_wrm1) -> OK
		_response = workRecordWC.replacePath("/").path(_wrm1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_wrm1) -> NOT_FOUND
		_response = workRecordWC.replacePath("/").path(_wrm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _wrm1 -> NOT_FOUND
		_response = workRecordWC.replacePath("/").path(_wrm1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _wrm1 -> NOT_FOUND
		_response = workRecordWC.replacePath("/").path(_wrm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testWorkRecordModifications() {
		// create(1) -> _wrm1
		Response _response = workRecordWC.replacePath("/").post(createWorkRecord(
				company.getId(), project.getId(), resource.getId(), rate.getId(), 1, date, 1, 10, true));

		WorkRecordModel _wrm1 = _response.readEntity(WorkRecordModel.class);
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _wrm1.getCreatedAt());
		assertNotNull("create() should set createdBy", _wrm1.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _wrm1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _wrm1.getModifiedBy());
		assertTrue("modifiedAt should be greater than or equal to createdAt after create()", _wrm1.getModifiedAt().compareTo(_wrm1.getCreatedAt()) >= 0);
		assertEquals("createdBy and modifiedBy should be identical after create()", _wrm1.getCreatedBy(), _wrm1.getModifiedBy());
		
		// update(_wrm1)  -> _wrm2
		_wrm1.setProjectId("NEW_PROJECTID");
		workRecordWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = workRecordWC.replacePath("/").path(_wrm1.getId()).put(_wrm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _wrm2 = _response.readEntity(WorkRecordModel.class);

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _wrm1.getCreatedAt(), _wrm2.getCreatedAt());
		assertEquals("update() should not change createdBy", _wrm1.getCreatedBy(), _wrm2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		assertThat(_wrm2.getModifiedAt(), not(equalTo(_wrm2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_o2.getModifiedBy(), not(equalTo(_o2.getCreatedBy())));

		// update(o2) with createdBy set on client side -> ignore
		String _createdBy = _wrm1.getCreatedBy();
		_wrm1.setCreatedBy("MYSELF");
		workRecordWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = workRecordWC.replacePath("/").path(_wrm1.getId()).put(_wrm1);
		assertEquals("update() should ignore client-side generated createdBy", 
				Status.OK.getStatusCode(), _response.getStatus());
		_wrm1.setCreatedBy(_createdBy);

		// update(_wrm1) with createdAt set on client side -> ignore
		Date _d = _wrm1.getCreatedAt();
		_wrm1.setCreatedAt(new Date(1000));
		workRecordWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = workRecordWC.replacePath("/").path(_wrm1.getId()).put(_wrm1);
		assertEquals("update() should ignore client-side generated createdAt", 
				Status.OK.getStatusCode(), _response.getStatus());
		_wrm1.setCreatedAt(_d);

		// update(_wrm1) with modifiedBy/At set on client side -> ignored by server
		_wrm1.setModifiedBy("MYSELF");
		_wrm1.setModifiedAt(new Date(1000));
		workRecordWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = workRecordWC.replacePath("/").path(_wrm1.getId()).put(_wrm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		WorkRecordModel _o3 = _response.readEntity(WorkRecordModel.class);
		
		// test, that modifiedBy really ignores the client-side value "MYSELF"
		assertThat(_wrm1.getModifiedBy(), not(equalTo(_o3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_wrm1.getModifiedAt(), not(equalTo(_o3.getModifiedAt())));
		
		// delete(_wrm1) -> NO_CONTENT
		_response = workRecordWC.replacePath("/").path(_wrm1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	/********************************** helper methods *********************************/	
	public static WebClient createWorkRecordsWebClient() {
		return createWebClient(createUrl(DEFAULT_BASE_URL, API_URL), WorkRecordsService.class);
	}
	
	public static WorkRecordModel createWorkRecord(
			String companyId, 
			String projectId, 
			String resourceId, 
			String rateId,
			int suffix, 
			Date d, 
			int hours, 
			int mins, 
			boolean isBillable) {
		return new WorkRecordModel(
			companyId,
			"CTITLE" + suffix,
			projectId,
			"PTITLE" + suffix,
			resourceId,
			d,
			hours,
			mins,
			rateId,
			isBillable,
			"MY_COMMENT" + suffix
		);
	}
}
