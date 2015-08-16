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
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.rates.RateModel;
import org.opentdc.rates.RatesService;
import org.opentdc.resources.ResourceModel;
import org.opentdc.resources.ResourcesService;
import org.opentdc.service.LocalizedTextModel;
import org.opentdc.service.ServiceUtil;
import org.opentdc.tags.TagModel;
import org.opentdc.tags.TagsService;
import org.opentdc.util.LanguageCode;
import org.opentdc.workrecords.TagRefModel;
import org.opentdc.workrecords.WorkRecordModel;
import org.opentdc.workrecords.WorkRecordsService;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.WttService;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.ContactTest;
import test.org.opentdc.addressbooks.OrgTest;
import test.org.opentdc.rates.RateTest;
import test.org.opentdc.resources.ResourceTest;
import test.org.opentdc.tags.LocalizedTextTest;
import test.org.opentdc.tags.TagTest;
import test.org.opentdc.wtt.CompanyTest;
import test.org.opentdc.wtt.ProjectTest;

/**
 * Testing references to tags.
 * @author Bruno Kaiser
 *
 */
public class TagRefTest extends AbstractTestClient {
	private static final String CN = "TagRefTest";
	private WebClient wc = null;
	private WebClient tagWC = null;
	private WebClient wttWC = null;
	private WebClient addressbookWC = null;
	private WebClient resourceWC = null;
	private WebClient rateWC = null;
	
	private AddressbookModel addressbook = null;
	private OrgModel org = null;
	private CompanyModel company = null;
	private ProjectModel project = null;
	private ContactModel contact = null;
	private ResourceModel resource = null;
	private RateModel rate = null;
	private WorkRecordModel workRecord = null;
	private TagModel tag = null;
	private Date date; 

	@Before
	public void initializeTests() {
		wc = createWebClient(ServiceUtil.WORKRECORDS_API_URL, WorkRecordsService.class);
		tagWC = createWebClient(ServiceUtil.TAGS_API_URL, TagsService.class);
		wttWC = createWebClient(ServiceUtil.WTT_API_URL, WttService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		resourceWC = createWebClient(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		rateWC = createWebClient(ServiceUtil.RATES_API_URL, RatesService.class);
		
		date = new Date();
		addressbook = AddressbookTest.post(addressbookWC, new AddressbookModel(CN), Status.OK);
		contact = ContactTest.post(addressbookWC, addressbook.getId(), 
				new ContactModel(CN + "1", CN + "2"), Status.OK);
		org = OrgTest.post(addressbookWC, addressbook.getId(), 
				new OrgModel(CN, OrgType.COOP), Status.OK);
		
		company = CompanyTest.post(wttWC, 
				new CompanyModel(CN, "MY_DESC", org.getId()), Status.OK);
		project = ProjectTest.post(wttWC, company.getId(), 
				new ProjectModel(CN + "1", CN + "2"), Status.OK);
		resource = ResourceTest.post(resourceWC, 
				new ResourceModel(CN, contact.getId()), Status.OK);
		rate = RateTest.post(rateWC, 
				new RateModel(CN, 100, "MY_DESC"), Status.OK);
		workRecord = WorkRecordTest.post(wc, 
				WorkRecordTest.create(company, project, resource, date, 1, 30, true, true, "TagRefTests"), Status.OK);
		tag = TagTest.create(tagWC, Status.OK);
		LocalizedTextTest.post(tagWC, tag, new LocalizedTextModel(LanguageCode.getDefaultLanguageCode(), "TagRefTest"), Status.OK);
	}

	@After
	public void cleanupTest() {
		AddressbookTest.delete(addressbookWC, addressbook.getId(), Status.NO_CONTENT);
		addressbookWC.close();
		
		ResourceTest.delete(resourceWC, resource.getId(), Status.NO_CONTENT);
		resourceWC.close();
		
		CompanyTest.delete(wttWC, company.getId(), Status.NO_CONTENT);
		wttWC.close();
		
		RateTest.delete(rateWC, rate.getId(), Status.NO_CONTENT);
		rateWC.close();
		
		TagTest.delete(tagWC, tag.getId(), Status.NO_CONTENT);
		tagWC.close();
		
		WorkRecordTest.delete(wc, workRecord.getId(), Status.NO_CONTENT);
		wc.close();
	}
	
	/********************************** localizedText attributes tests *********************************/			
	@Test
	public void testEmptyConstructor() {
		TagRefModel _model = new TagRefModel();
		assertNull("id should not be set by empty constructor", _model.getId());
		assertNull("tagId should not be set by empty constructor", _model.getTagId());
	}
	
	@Test
	public void testConstructor() {		
		TagRefModel _model = new TagRefModel("testConstructor1");
		assertNull("id should not be set by constructor", _model.getId());
		assertEquals("tagId should be set by constructor", "testConstructor1", _model.getTagId());
	}
	
	@Test
	public void testId() {
		TagRefModel _model = new TagRefModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testIdChange");
		assertEquals("id should have changed", "testIdChange", _model.getId());
	}

	@Test
	public void testTagId() {
		TagRefModel _model = new TagRefModel();
		assertNull("tagId should not be set by empty constructor", _model.getTagId());
		_model.setTagId("testTagId");
		assertEquals("tagId should have changed", "testTagId", _model.getTagId());
	}
		
	@Test
	public void testCreatedBy() {
		TagRefModel _model = new TagRefModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		TagRefModel _model = new TagRefModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		TagRefModel _model1 = new TagRefModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("tagId should not be set by empty constructor", _model1.getTagId());
	
		post(_model1, Status.BAD_REQUEST);
		_model1.setTagId(tag.getId());
		TagRefModel _model2 = post(_model1, Status.OK);

		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the tagId of the local object", tag.getId(), _model1.getTagId());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("create() should not change the tagId", tag.getId(), _model2.getTagId());
		
		TagRefModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("tagId of returned object should be unchanged after remote create", _model2.getTagId(), _model3.getTagId());

		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		TagRefModel _model1 = new TagRefModel(tag.getId());
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("tagId should be set by constructor", tag.getId(), _model1.getTagId());
		
		TagRefModel _model2 = post(_model1, Status.OK);
		assertNull("id should still be null after remote create", _model1.getId());
		assertEquals("create() should not change the tagId", tag.getId(), _model1.getTagId());
		
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("create() should not change the tagId", tag.getId(), _model2.getTagId());

		TagRefModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("read() should not change the id", _model2.getId(), _model3.getId());
		assertEquals("read() should not change the tagId", _model2.getTagId(), _model3.getTagId());
		
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateWithClientSideId() {
		TagRefModel _model = new TagRefModel(tag.getId());
		_model.setId("LOCAL_ID");
		post(_model, Status.BAD_REQUEST);
	}
	
	@Test
	public void testCreateWithDuplicateId() {
		TagRefModel _model1 = post(new TagRefModel(tag.getId()), Status.OK);
		TagRefModel _model2 = new TagRefModel(tag.getId());
		_model2.setId(_model1.getId());		// wrongly create a 2nd LocalizedTextModel object with the same ID
		post(_model2, Status.CONFLICT);
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testList() {
		ArrayList<TagRefModel> _localList = new ArrayList<TagRefModel>();		
		wc.replacePath("/").path(resource.getId()).path(ServiceUtil.TAGREF_PATH_EL);
		_localList.add(post(new TagRefModel(tag.getId()), Status.OK));
		_localList.add(post(new TagRefModel(tag.getId()), Status.OK));
		_localList.add(post(new TagRefModel(tag.getId()), Status.OK));
		_localList.add(post(new TagRefModel(tag.getId()), Status.OK));
		_localList.add(post(new TagRefModel(tag.getId()), Status.OK));
		_localList.add(post(new TagRefModel(tag.getId()), Status.OK));
		
		List<TagRefModel> _remoteList = list(Status.OK);

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (TagRefModel _model : _remoteList) {
			_remoteListIds.add(_model.getId());
		}
		
		for (TagRefModel _model : _localList) {
			assertTrue("TagRef <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		
		for (TagRefModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		
		for (TagRefModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}

	@Test
	public void testCreate() {	
		TagRefModel _model1 = post(new TagRefModel(tag.getId()), Status.OK);
		assertNotNull("ID should be set", _model1.getId());
		assertEquals("tagId should be set correctly", tag.getId(), _model1.getTagId());
		
		TagRefModel _model2 = post(new TagRefModel(tag.getId()), Status.OK);
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("tagId should be set correctly", tag.getId(), _model2.getTagId());

		assertThat(_model2.getId(), not(equalTo(_model1.getId())));

		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateDouble() {
		TagRefModel _model = post(new TagRefModel(tag.getId()), Status.OK);
		assertNotNull("ID should be set:", _model.getId());
		post(_model, Status.CONFLICT);
		delete(_model.getId(), Status.NO_CONTENT);
	}
		
	@Test
	public void testRead() {
		ArrayList<TagRefModel> _localList = new ArrayList<TagRefModel>();
		wc.replacePath("/").path(resource.getId()).path(ServiceUtil.TAGREF_PATH_EL);
		_localList.add(post(new TagRefModel(tag.getId()), Status.OK));
		_localList.add(post(new TagRefModel(tag.getId()), Status.OK));
		_localList.add(post(new TagRefModel(tag.getId()), Status.OK));
		_localList.add(post(new TagRefModel(tag.getId()), Status.OK));
		_localList.add(post(new TagRefModel(tag.getId()), Status.OK));
		_localList.add(post(new TagRefModel(tag.getId()), Status.OK));
	
		// test read on each local element
		for (TagRefModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		List<TagRefModel> _remoteList = list(Status.OK);

		for (TagRefModel _model : _remoteList) {
			assertEquals("ID should be unchanged when reading a project", _model.getId(), get(_model.getId(), Status.OK).getId());						
		}

		for (TagRefModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}
	
	@Test
	public void testMultiRead() {
		TagRefModel _model1 = post(new TagRefModel(tag.getId()), Status.OK);
		TagRefModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read:", _model1.getId(), _model2.getId());		
		TagRefModel _ltm3 = get(_model1.getId(), Status.OK);
		
		assertEquals("ID should be the same:", _model2.getId(), _ltm3.getId());
		assertEquals("tagId should be the same:", _model2.getTagId(), _ltm3.getTagId());
		
		assertEquals("ID should be the same:", _model2.getId(), _model1.getId());
		assertEquals("tagId should be the same:", _model2.getTagId(), _model1.getTagId());
		
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDelete(
	) {
		TagRefModel _model1 = post(new TagRefModel(tag.getId()), Status.OK);
		TagRefModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a project (remote):", _model1.getId(), _model2.getId());						
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		TagRefModel _model = post(new TagRefModel(tag.getId()), Status.OK);
		get(_model.getId(), Status.OK);
		delete(_model.getId(), Status.NO_CONTENT);
		get(_model.getId(), Status.NOT_FOUND);
		delete(_model.getId(), Status.NOT_FOUND);
		delete(_model.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		TagRefModel _model = post(new TagRefModel(tag.getId()), Status.OK);		
		assertNotNull("create() should set createdAt", _model.getCreatedAt());
		assertNotNull("create() should set createdBy", _model.getCreatedBy());
		delete(_model.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	public List<TagRefModel> list(
			Status expectedStatus) 
	{
		return list(wc, workRecord.getId(), expectedStatus);
	}

	public static List<TagRefModel> list(
			WebClient webClient,
			String resourceId,
			Status expectedStatus) 
	{
		Response _response = webClient.replacePath("/").path(resourceId).path(ServiceUtil.TAGREF_PATH_EL).get();
		assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return new ArrayList<TagRefModel>(webClient.getCollection(TagRefModel.class));
		} else {
			return null;
		}
	}
	
	public TagRefModel post(
			TagRefModel TagRefModel,
			Status expectedStatus) 
	{
		return post(wc, workRecord, TagRefModel, expectedStatus);
	}

	public static TagRefModel post(
			WebClient webClient,
			WorkRecordModel workRecordModel,
			TagRefModel TagRefModel,
			Status expectedStatus) 
	{
		Response _response = webClient.replacePath("/").path(workRecordModel.getId()).path(ServiceUtil.TAGREF_PATH_EL).post(TagRefModel);
		assertEquals("post should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(TagRefModel.class);
		} else {
			return null;
		}
	}
	
	public TagRefModel get(
			String tagRefId,
			Status expectedStatus) 
	{
		return get(wc, workRecord, tagRefId, expectedStatus);
	}

	public static TagRefModel get(
			WebClient webClient,
			WorkRecordModel workRecordModel,
			String tagRefId,
			Status expectedStatus) 
	{
		Response _response = webClient.replacePath("/").path(workRecordModel.getId()).path(ServiceUtil.TAGREF_PATH_EL).path(tagRefId).get();
		assertEquals("get should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(TagRefModel.class);
		} else {
			return null;
		}
	}
	
	public void delete(
			String tagRefId,
			Status expectedStatus) 
	{
		delete(wc, workRecord, tagRefId, expectedStatus);
	}

	public static void delete(
			WebClient webClient,
			WorkRecordModel workRecordModel,
			String tagRefId,
			Status expectedStatus) 
	{
		Response _response = webClient.replacePath("/").
				path(workRecordModel.getId()).path(ServiceUtil.TAGREF_PATH_EL).path(tagRefId).delete();
		assertEquals("delete should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
	}
	
	protected int calculateMembers() {
		return 1;
	}
}