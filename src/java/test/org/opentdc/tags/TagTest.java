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
package test.org.opentdc.tags;

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
import org.opentdc.service.LocalizedTextModel;
import org.opentdc.service.ServiceUtil;
import org.opentdc.tags.SingleLangTag;
import org.opentdc.tags.TagModel;
import org.opentdc.tags.TagsService;
import org.opentdc.util.LanguageCode;

import test.org.opentdc.AbstractTestClient;

/**
 * Tests the Tags-Service.
 * @author Bruno Kaiser
 *
 */
public class TagTest extends AbstractTestClient {
	private WebClient wc = null;

	/**
	 * Initializes the test case.
	 */
	@Before
	public void initializeTest() {
		wc = initializeTest(ServiceUtil.TAGS_API_URL, TagsService.class);
	}
	
	/**
	 * Cleanup all resources needed by the test case.
	 */
	@After
	public void cleanupTest() {
		wc.close();
	}

	/********************************** tags attributes tests *********************************/	
	/**
	 * Test the empty constructor.
	 */
	@Test
	public void testEmptyConstructor() {
		TagModel _model = new TagModel();
		assertNull("id should not be set by empty constructor", _model.getId());
	}
	
	/**
	 * Test id attribute.
	 */
	@Test
	public void testId() {
		TagModel _model = new TagModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed", "testId", _model.getId());
	}
	
	/**
	 * Test createdBy attribute.
	 */
	@Test
	public void testCreatedBy() {
		TagModel _model = new TagModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	/**
	 * Test createdAt attribute.
	 */
	@Test
	public void testCreatedAt() {
		TagModel _model = new TagModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	/**
	 * Test modifiedBy attribute.
	 */
	@Test
	public void testModifiedBy() {
		TagModel _model = new TagModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	/**
	 * Test modifiedAt attribute.
	 */
	@Test
	public void testModifiedAt() {
		TagModel _model = new TagModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	/**
	 * Test create() operation.
	 */
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() 
	{
		TagModel _model1 = new TagModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		TagModel _model2 = post(_model1, Status.OK);
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		
		TagModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	/**
	 * Test to create a TagModel with a custom id generated by the client.
	 */
	@Test
	public void testCreateWithClientSideId() {
		TagModel _model = new TagModel();
		_model.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _model.getId());
		post(_model, Status.BAD_REQUEST);
	}
	
	/**
	 * Test to create a TagModel with an id that exists already.
	 */
	@Test
	public void testCreateWithDuplicateId() {
		TagModel _model1 = post(new TagModel(), Status.OK);
		get(_model1.getId(), Status.OK);		
		TagModel _model2 = new TagModel();
		_model2.setId(_model1.getId());
		post(_model2, Status.CONFLICT);
		get(_model1.getId(), Status.OK);
		delete(_model1.getId(), Status.NO_CONTENT);
 	}
	
	/**
	 * Test list() operation filtered by LanguageCode.
	 */
	@Test
	public void testListAll()
	{
		System.out.println("*** testListAll:");
		TagModel _model1 = post(new TagModel(), Status.OK);
		TagModel _model2 = post(new TagModel(), Status.OK);
		TagModel _model3 = post(new TagModel(), Status.OK);
		LocalizedTextTest.post(wc, _model1, new LocalizedTextModel(LanguageCode.DE, "eins"), Status.OK);
		LocalizedTextTest.post(wc, _model2, new LocalizedTextModel(LanguageCode.DE, "zwei"), Status.OK);
		LocalizedTextTest.post(wc, _model3, new LocalizedTextModel(LanguageCode.DE, "drei"), Status.OK);
		LocalizedTextTest.post(wc, _model1, new LocalizedTextModel(LanguageCode.EN, "one"), Status.OK);
		LocalizedTextTest.post(wc, _model2, new LocalizedTextModel(LanguageCode.EN, "two"), Status.OK);
		LocalizedTextTest.post(wc, _model3, new LocalizedTextModel(LanguageCode.FR, "trois"), Status.OK);

		List<SingleLangTag> _tagList = list(null, Status.OK);
		logResult("all tags (no query):", _tagList);
		assertTrue("amount of returned tags must be correct", _tagList.size() >= 6);
		
		_tagList = list("lang=" + LanguageCode.DE, Status.OK);
		logResult("DE tags:", _tagList);
		assertTrue("amount of returned tags must be correct", _tagList.size() >= 3);
		
		_tagList = list("lang=" + LanguageCode.EN, Status.OK);
		logResult("EN tags:", _tagList);
		assertTrue("amount of returned tags must be correct", _tagList.size() >= 2);
		assertEquals("tag text must be correct", "one", _tagList.get(0).getText());
		
		_tagList = list("lang=" + LanguageCode.FR, Status.OK);
		logResult("FR tags:", _tagList);
		assertTrue("amount of returned tags must be correct", _tagList.size() >= 1);
		assertEquals("tag text must be correct", "trois", _tagList.get(0).getText());
		
		_tagList = list("lang=" + LanguageCode.IT, Status.OK);
		logResult("IT tags:", _tagList);
		assertEquals("amount of returned tags must be correct", 0, _tagList.size());
		
		list("language=DE", Status.BAD_REQUEST);
		list("lang=OB", Status.BAD_REQUEST);
		
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	/**
	 * Print the result of the list() operation onto stdout.
	 * @param title  the title of the log section
	 * @param tagList the result list of SingleLangTag
	 */
	private void logResult(String title, List<SingleLangTag> tagList) {
		System.out.println(title);
		System.out.println("\ttagId\t\t\t\t\tlocalizedTextId\t\t\t\ttext\tlanguageCode");
		for (SingleLangTag _model : tagList) { 
			System.out.println(
					"\t" + _model.getTagId() + 
					"\t" + _model.getLocalizedTextId() + 
					"\t" + _model.getText() + 
					"\t" + _model.getLanguageCode());
		}
		System.out.println("\ttotal\t" + tagList.size() + " elements");
	}

	/**
	 * Test list() operation on TagModels not containing LocalizedTextModels.
	 */
	@Test
	public void testPureTagsList() 
	{		
		System.out.println("*** testPureTagsList:");
		List<SingleLangTag> _remoteList1 = list(null, Status.OK);
		ArrayList<TagModel> _localList = new ArrayList<TagModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(new TagModel(), Status.OK));
		}
		List<SingleLangTag> _remoteList2 = list(null, Status.OK);
		assertEquals("list should be empty (because no LocalizedText is saved", _remoteList1.size(), _remoteList2.size());		
		for (TagModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}
		
	/**
	 * Test list() operation on TagModels containing LocalizedTextModels).
	 */
	@Test
	public void testTagsTextList() 
	{		
		System.out.println("*** testTagsTextList:");
		System.out.println("a) creating "  + LIMIT + " elements with 1 LocalizedText each (localList):");
		ArrayList<SingleLangTag> _localList = new ArrayList<SingleLangTag>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(create(wc, "testTagsTextList" + i, LanguageCode.DE));
		}
		List<SingleLangTag> _remoteList = list(null, Status.OK);
		logResult("b) list():", _remoteList);

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (SingleLangTag _model : _remoteList) {
			_remoteListIds.add(_model.getTagId());
		}		
		for (SingleLangTag _model : _localList) {
			assertTrue("tag <" + _model.getTagId() + "> should be listed", _remoteListIds.contains(_model.getTagId()));
		}
		System.out.println("c) reading each test object (localList):");
		for (SingleLangTag _model : _localList) {
			get(_model.getTagId(), Status.OK);
			System.out.println("\t" + _model.getTagId());
		}
		System.out.println("d) deleting each test object (localList):");
		for (SingleLangTag _model : _localList) {
			System.out.println("\t" + _model.getTagId());
			delete(_model.getTagId(), Status.NO_CONTENT);
		}
	}

	/**
	 * Test create() operation.
	 */
	@Test
	public void testCreate() {
		TagModel _model1 = post(new TagModel(), Status.OK);
		TagModel _model2 = post(new TagModel(), Status.OK);
		assertNotNull("ID should be set", _model1.getId());
		assertNotNull("ID should be set", _model2.getId());
		assertThat(_model2.getId(), not(equalTo(_model1.getId())));
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	/**
	 * Test to create the same Model twice.
	 */
	@Test
	public void testDoubleCreate() {
		TagModel _model = post(new TagModel(), Status.OK);
		assertNotNull("ID should be set:", _model.getId());
		post(_model, Status.CONFLICT);
		delete(_model.getId(), Status.NO_CONTENT);
	}

	/**
	 * Test read() operation.
	 */
	@Test
	public void testRead() 
	{
		ArrayList<TagModel> _localList = new ArrayList<TagModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(new TagModel(), Status.OK));
		}
		for (TagModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		List<SingleLangTag> _remoteList = list(null, Status.OK);
		
		for (SingleLangTag _model : _remoteList) {
			get(_model.getTagId(), Status.OK);
		}
		for (TagModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}	

	/**
	 * Test multiple read() operations.
	 */
	@Test
	public void testMultiRead() 
	{
		TagModel _model1 = post(new TagModel(), Status.OK);
		TagModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read:", _model1.getId(), _model2.getId());		
		TagModel _model3 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be the same:", _model2.getId(), _model3.getId());
		assertEquals("ID should be the same:", _model2.getId(), _model1.getId());
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	/**
	 * Test update() operation.
	 */
	@Test
	public void testUpdate() 
	{
		TagModel _model1 = post(new TagModel(), Status.OK);
		TagModel _model2 = put(_model1, Status.OK);
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	/**
	 * Test delete() operation.
	 */
	@Test
	public void testDelete() 
	{
		TagModel _model1 = post(new TagModel(), Status.OK);
		TagModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a tag (remote):", _model1.getId(), _model2.getId());						
		delete(_model1.getId(), Status.NO_CONTENT);
		get(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	/**
	 * Test to delete the same model twice.
	 */
	@Test
	public void testDoubleDelete() 
	{
		TagModel _model = post(new TagModel(), Status.OK);
		get(_model.getId(), Status.OK);
		delete(_model.getId(), Status.NO_CONTENT);
		get(_model.getId(), Status.NOT_FOUND);
		delete(_model.getId(), Status.NOT_FOUND);
		get(_model.getId(), Status.NOT_FOUND);
	}
	
	/**
	 * Test attributes createdAt, createdBy, modifiedAt, and modifiedBy.
	 */
	@Test
	public void testModifications() 
	{
		TagModel _model1 = post(new TagModel(), Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());

		TagModel _model2 = put(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		// the following has a timing issue; we don't want to sleep here in order to get two different times.
		// assertThat(_tm2.getModifiedAt().toString(), not(equalTo(_tm2.getCreatedAt().toString())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_tm2.getModifiedBy(), not(equalTo(_tm2.getCreatedBy())));

		String _createdBy = _model1.getCreatedBy();
		_model1.setCreatedBy("testModifications");
		TagModel _model3 = put(_model1, Status.OK);	// update should ignore client-side generated createdBy
		assertEquals("update() should not change createdBy", _createdBy, _model3.getCreatedBy());
		_model1.setCreatedBy(_createdBy);

		Date _createdAt = _model1.getCreatedAt();
		_model1.setCreatedAt(new Date());
		TagModel _model4 = put(_model1, Status.OK);	// update should ignore client-side generated createdAt
		assertEquals("update() should not change createdAt", _createdAt, _model4.getCreatedAt());
		_model1.setCreatedAt(_createdAt);
		
		String _modifiedBy = _model1.getModifiedBy();
		_model1.setModifiedBy("testModifications");
		TagModel _model5 = put(_model1, Status.OK);	// update should ignore client-side generated modifiedBy
		assertEquals("update() should not change modifiedBy", _modifiedBy, _model5.getModifiedBy());

		Date _modifiedAt = _model1.getModifiedAt();
		Date _modifiedAt2 = new Date(1000);
		_model1.setModifiedAt(_modifiedAt2);
		TagModel _model6 = put(_model1, Status.OK);	// update should ignore client-side generated modifiedAt
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt)));
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt2)));
		
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	/**
	 * Retrieve a list of SingleLangTag from TagsService by executing a HTTP GET request.
	 * This uses neither position nor size queries.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of SingleLangTag object in JSON format
	 */
	public List<SingleLangTag> list(
			String query, 
			Status expectedStatus) {
		return list(wc, query, -1, Integer.MAX_VALUE, expectedStatus);
	}
	
	/**
	 * Retrieve a list of SingleLangTag from TagsService by executing a HTTP GET request.
	 * @param webClient the WebClient for the TagsService
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of SingleLangTag objects in JSON format
	 */
	public static List<SingleLangTag> list(
			WebClient webClient, 
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		webClient.resetQuery();
		webClient.replacePath("/");
		Response _response = executeListQuery(webClient, query, position, size);
		List<SingleLangTag> _tags = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_tags = new ArrayList<SingleLangTag>(webClient.getCollection(SingleLangTag.class));
			System.out.println("listTags(tagWC, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ") ->" + _tags.size());
		}
		return _tags;
	}
	
	/**
	 * Create a new TagsModel on the server by executing a HTTP POST request.
	 * @param model the TagsModel to post to the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created TagsModel
	 */
	public TagModel post(
			TagModel model, 
			Status expectedStatus) {
		Response _response = wc.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("create() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(TagModel.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Create a new TagsModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the TagsService
	 * @param model the TagsModel data to create on the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created TagsModel
	 */
	public static TagModel post(
			WebClient webClient,
			TagModel model,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(TagModel.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Create a new TagsModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the TagsService
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created TagsModel
	 */
	public static TagModel create(
			WebClient webClient,
			Status expectedStatus) 
	{
		return post(webClient, new TagModel(), expectedStatus);
	}
	
	public static SingleLangTag create(
			WebClient webClient,
			String text,
			LanguageCode langCode) {
		TagModel _tag = create(webClient, Status.OK);
		LocalizedTextModel _ltm = LocalizedTextTest.post(webClient, _tag, new LocalizedTextModel(langCode, text), Status.OK);
		return new SingleLangTag(_tag.getId(), _ltm);
	}
		
	/**
	 * Read the TagsModel with id from TagsService by executing a HTTP GET method.
	 * @param tagId the id of the TagsModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved TagsModel object in JSON format
	 */
	public TagModel get(
			String tagId, 
			Status expectedStatus) {
		return get(wc, tagId, expectedStatus);
	}
	
	/**
	 * Read the TagsModel with id from TagsService by executing a HTTP GET method.
	 * @param webClient the web client representing the TagsService
	 * @param tagId the id of the TagsModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved TagsModel object in JSON format
	 */
	public static TagModel get(
			WebClient webClient,
			String tagId,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(tagId).get();
		if (expectedStatus != null) {
			assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(TagModel.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Update a TagsModel on the TagsService by executing a HTTP PUT method.
	 * @param tm the new TagsModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated TagsModel object in JSON format
	 */
	public TagModel put(
			TagModel tm, 
			Status expectedStatus) {
		return put(wc, tm, expectedStatus);
	}
	
	/**
	 * Update a TagsModel on the TagsService by executing a HTTP PUT method.
	 * @param webClient the web client representing the TagsService
	 * @param model the new TagsModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated TagsModel object in JSON format
	 */
	public static TagModel put(
			WebClient webClient,
			TagModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(model.getId()).put(model);
		if (expectedStatus != null) {
			assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(TagModel.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Delete the TagsModel with id on the TagsService by executing a HTTP DELETE method.
	 * @param id the id of the TagsModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public void delete(String id, Status expectedStatus) {
		delete(wc, id, expectedStatus);
	}
	
	/**
	 * Delete the TagsModel with id including all its contained LocalizedTextModels on the TagsService by executing a HTTP DELETE method.
	 * @param webClient the WebClient for the TagsService
	 * @param tagId the id of the TagsModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void delete(
			WebClient webClient,
			String tagId,
			Status expectedStatus) {
		Status _expectedStatusGet = expectedStatus == Status.NO_CONTENT ? Status.OK : expectedStatus;
		TagModel _tagModel = get(webClient, tagId, _expectedStatusGet);
		List<LocalizedTextModel> _localizedTextModels = LocalizedTextTest.list(webClient, tagId, _expectedStatusGet);
		if (_localizedTextModels != null) {
			for (LocalizedTextModel _localizedTextModel : _localizedTextModels) {
				LocalizedTextTest.delete(webClient, _tagModel, _localizedTextModel.getId(), expectedStatus);
			}
		}
		Response _response = webClient.replacePath("/").path(tagId).delete();	
		if (expectedStatus != null) {
			assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
	}
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	protected int calculateMembers() {
		return 0;
	}
}
