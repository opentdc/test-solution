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
import org.opentdc.tags.TagTextModel;
import org.opentdc.tags.TagsModel;
import org.opentdc.tags.TagsService;
import org.opentdc.util.LanguageCode;

import test.org.opentdc.AbstractTestClient;

/**
 * Tests the Tags-Service.
 * @author Bruno Kaiser
 *
 */
public class TagsTest extends AbstractTestClient {
	private WebClient tagWC = null;

	/**
	 * Initializes the test case.
	 */
	@Before
	public void initializeTest() {
		tagWC = initializeTest(ServiceUtil.TAGS_API_URL, TagsService.class);
	}
	
	/**
	 * Cleanup all resources needed by the test case.
	 */
	@After
	public void cleanupTest() {
		tagWC.close();
	}

	/********************************** tags attributes tests *********************************/	
	/**
	 * Test the empty constructor.
	 */
	@Test
	public void testEmptyConstructor() {
		TagsModel _model = new TagsModel();
		assertNull("id should not be set by empty constructor", _model.getId());
	}
	
	/**
	 * Test id attribute.
	 */
	@Test
	public void testId() {
		TagsModel _model = new TagsModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed", "testId", _model.getId());
	}
	
	/**
	 * Test createdBy attribute.
	 */
	@Test
	public void testCreatedBy() {
		TagsModel _model = new TagsModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	/**
	 * Test createdAt attribute.
	 */
	@Test
	public void testCreatedAt() {
		TagsModel _model = new TagsModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	/**
	 * Test modifiedBy attribute.
	 */
	@Test
	public void testModifiedBy() {
		TagsModel _model = new TagsModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	/**
	 * Test modifiedAt attribute.
	 */
	@Test
	public void testModifiedAt() {
		TagsModel _model = new TagsModel();
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
		TagsModel _model1 = new TagsModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		TagsModel _model2 = postTag(_model1, Status.OK);
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		
		TagsModel _model3 = getTag(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		deleteTag(_model3.getId(), Status.NO_CONTENT);
	}
	
	/**
	 * Test to create a TagsModel with a custom id generated by the client.
	 */
	@Test
	public void testCreateWithClientSideId() {
		TagsModel _model = new TagsModel();
		_model.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _model.getId());
		postTag(_model, Status.BAD_REQUEST);
	}
	
	/**
	 * Test to create a TagsModel with an id that exists already.
	 */
	@Test
	public void testCreateWithDuplicateId() {
		TagsModel _model1 = postTag(new TagsModel(), Status.OK);
		getTag(_model1.getId(), Status.OK);		
		TagsModel _model2 = new TagsModel();
		_model2.setId(_model1.getId());
		postTag(_model2, Status.CONFLICT);
		getTag(_model1.getId(), Status.OK);
		deleteTag(_model1.getId(), Status.NO_CONTENT);
 	}
	
	/**
	 * Test list() operation filtered by LanguageCode.
	 */
	@Test
	public void testListAll()
	{
		TagsModel _model1 = postTag(new TagsModel(), Status.OK);
		TagsModel _model2 = postTag(new TagsModel(), Status.OK);
		TagsModel _model3 = postTag(new TagsModel(), Status.OK);
		LocalizedTextTest.postLocalizedText(tagWC, _model1, new LocalizedTextModel(LanguageCode.DE, "eins"), Status.OK);
		LocalizedTextTest.postLocalizedText(tagWC, _model2, new LocalizedTextModel(LanguageCode.DE, "zwei"), Status.OK);
		LocalizedTextTest.postLocalizedText(tagWC, _model3, new LocalizedTextModel(LanguageCode.DE, "drei"), Status.OK);
		LocalizedTextTest.postLocalizedText(tagWC, _model1, new LocalizedTextModel(LanguageCode.EN, "one"), Status.OK);
		LocalizedTextTest.postLocalizedText(tagWC, _model2, new LocalizedTextModel(LanguageCode.EN, "two"), Status.OK);
		LocalizedTextTest.postLocalizedText(tagWC, _model3, new LocalizedTextModel(LanguageCode.FR, "trois"), Status.OK);

		List<TagTextModel> _tagList = listTags(null, Status.OK);
		logResult("all tags (no query):", _tagList);
		assertEquals("amount of returned tags must be correct", 6, _tagList.size());
		
		_tagList = listTags("lang=" + LanguageCode.DE, Status.OK);
		logResult("DE tags:", _tagList);
		assertEquals("amount of returned tags must be correct", 3, _tagList.size());
		
		_tagList = listTags("lang=" + LanguageCode.EN, Status.OK);
		logResult("EN tags:", _tagList);
		assertEquals("amount of returned tags must be correct", 2, _tagList.size());
		assertEquals("tag text must be correct", "one", _tagList.get(0).getText());
		
		_tagList = listTags("lang=" + LanguageCode.FR, Status.OK);
		logResult("FR tags:", _tagList);
		assertEquals("amount of returned tags must be correct", 1, _tagList.size());
		assertEquals("tag text must be correct", "trois", _tagList.get(0).getText());
		
		_tagList = listTags("lang=" + LanguageCode.IT, Status.OK);
		logResult("IT tags:", _tagList);
		assertEquals("amount of returned tags must be correct", 0, _tagList.size());
		
		listTags("language=DE", Status.BAD_REQUEST);
		listTags("lang=OB", Status.BAD_REQUEST);
		
		deleteTag(_model1.getId(), Status.NO_CONTENT);
		deleteTag(_model2.getId(), Status.NO_CONTENT);
		deleteTag(_model3.getId(), Status.NO_CONTENT);
	}
	
	/**
	 * Print the result of the list() operation onto stdout.
	 * @param title  the title of the log section
	 * @param tagList the result list of TagTextModels
	 */
	private void logResult(String title, List<TagTextModel> tagList) {
		System.out.println(title);
		System.out.println("\ttagId\t\t\t\t\tlocalizedTextId\t\t\t\ttext\tlang");
		for (TagTextModel _model : tagList) { 
			System.out.println(
					"\t" + _model.getTagId() + 
					"\t" + _model.getLocalizedTextId() + 
					"\t" + _model.getText() + 
					"\t" + _model.getLang());
		}
	}

	/**
	 * Test list() operation on TagsModels not containing LocalizedTextModels.
	 */
	@Test
	public void testPureTagsList() 
	{		
		ArrayList<TagsModel> _localList = new ArrayList<TagsModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(postTag(new TagsModel(), Status.OK));
		}
		List<TagTextModel> _remoteList = listTags(null, Status.OK);
		assertEquals("list should be empty (because no LocalizedText is saved", 0, _remoteList.size());		
		for (TagsModel _model : _localList) {
			deleteTag(_model.getId(), Status.NO_CONTENT);
		}
	}
		
	/**
	 * Test list() operation on TagsModels containing LocalizedTextModels).
	 */
	@Test
	public void testTagsTextList() 
	{		
		ArrayList<TagsModel> _localList = new ArrayList<TagsModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(postTag(new TagsModel(), Status.OK));
			LocalizedTextTest.postLocalizedText(tagWC, _localList.get(i), new LocalizedTextModel(LanguageCode.DE, "testTagsTextList" + i), Status.OK);
		}
		List<TagTextModel> _remoteList = listTags(null, Status.OK);
		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (TagTextModel _model : _remoteList) {
			_remoteListIds.add(_model.getTagId());
		}		
		for (TagsModel _model : _localList) {
			assertTrue("tag <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		for (TagsModel _model : _localList) {
			getTag(_model.getId(), Status.OK);
		}
		for (TagsModel _model : _localList) {
			deleteTag(_model.getId(), Status.NO_CONTENT);
		}
	}

	/**
	 * Test create() operation.
	 */
	@Test
	public void testCreate() {
		TagsModel _model1 = postTag(new TagsModel(), Status.OK);
		TagsModel _model2 = postTag(new TagsModel(), Status.OK);
		assertNotNull("ID should be set", _model1.getId());
		assertNotNull("ID should be set", _model2.getId());
		assertThat(_model2.getId(), not(equalTo(_model1.getId())));
		deleteTag(_model1.getId(), Status.NO_CONTENT);
		deleteTag(_model2.getId(), Status.NO_CONTENT);
	}
	
	/**
	 * Test to create the same Model twice.
	 */
	@Test
	public void testDoubleCreate() {
		TagsModel _model = postTag(new TagsModel(), Status.OK);
		assertNotNull("ID should be set:", _model.getId());
		postTag(_model, Status.CONFLICT);
		deleteTag(_model.getId(), Status.NO_CONTENT);
	}

	/**
	 * Test read() operation.
	 */
	@Test
	public void testRead() 
	{
		ArrayList<TagsModel> _localList = new ArrayList<TagsModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(postTag(new TagsModel(), Status.OK));
		}
		for (TagsModel _model : _localList) {
			getTag(_model.getId(), Status.OK);
		}
		List<TagTextModel> _remoteList = listTags(null, Status.OK);
		
		for (TagTextModel _model : _remoteList) {
			getTag(_model.getTagId(), Status.OK);
		}
		for (TagsModel _model : _localList) {
			deleteTag(_model.getId(), Status.NO_CONTENT);
		}
	}	

	/**
	 * Test multiple read() operations.
	 */
	@Test
	public void testMultiRead() 
	{
		TagsModel _model1 = postTag(new TagsModel(), Status.OK);
		TagsModel _model2 = getTag(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read:", _model1.getId(), _model2.getId());		
		TagsModel _model3 = getTag(_model1.getId(), Status.OK);
		assertEquals("ID should be the same:", _model2.getId(), _model3.getId());
		assertEquals("ID should be the same:", _model2.getId(), _model1.getId());
		deleteTag(_model3.getId(), Status.NO_CONTENT);
	}
	
	/**
	 * Test update() operation.
	 */
	@Test
	public void testUpdate() 
	{
		TagsModel _model1 = postTag(new TagsModel(), Status.OK);
		TagsModel _model2 = putTag(_model1, Status.OK);
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		deleteTag(_model1.getId(), Status.NO_CONTENT);
	}
	
	/**
	 * Test delete() operation.
	 */
	@Test
	public void testDelete() 
	{
		TagsModel _model1 = postTag(new TagsModel(), Status.OK);
		TagsModel _model2 = getTag(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a tag (remote):", _model1.getId(), _model2.getId());						
		deleteTag(_model1.getId(), Status.NO_CONTENT);
		getTag(_model1.getId(), Status.NOT_FOUND);
		getTag(_model1.getId(), Status.NOT_FOUND);
	}
	
	/**
	 * Test to delete the same model twice.
	 */
	@Test
	public void testDoubleDelete() 
	{
		TagsModel _model = postTag(new TagsModel(), Status.OK);
		getTag(_model.getId(), Status.OK);
		deleteTag(_model.getId(), Status.NO_CONTENT);
		getTag(_model.getId(), Status.NOT_FOUND);
		deleteTag(_model.getId(), Status.NOT_FOUND);
		getTag(_model.getId(), Status.NOT_FOUND);
	}
	
	/**
	 * Test attributes createdAt, createdBy, modifiedAt, and modifiedBy.
	 */
	@Test
	public void testModifications() 
	{
		TagsModel _model1 = postTag(new TagsModel(), Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());

		TagsModel _model2 = putTag(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		// the following has a timing issue; we don't want to sleep here in order to get two different times.
		// assertThat(_tm2.getModifiedAt().toString(), not(equalTo(_tm2.getCreatedAt().toString())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_tm2.getModifiedBy(), not(equalTo(_tm2.getCreatedBy())));

		String _createdBy = _model1.getCreatedBy();
		_model1.setCreatedBy("testModifications");
		TagsModel _model3 = putTag(_model1, Status.OK);	// update should ignore client-side generated createdBy
		assertEquals("update() should not change createdBy", _createdBy, _model3.getCreatedBy());
		_model1.setCreatedBy(_createdBy);

		Date _createdAt = _model1.getCreatedAt();
		_model1.setCreatedAt(new Date());
		TagsModel _model4 = putTag(_model1, Status.OK);	// update should ignore client-side generated createdAt
		assertEquals("update() should not change createdAt", _createdAt, _model4.getCreatedAt());
		_model1.setCreatedAt(_createdAt);
		
		String _modifiedBy = _model1.getModifiedBy();
		_model1.setModifiedBy("testModifications");
		TagsModel _model5 = putTag(_model1, Status.OK);	// update should ignore client-side generated modifiedBy
		assertEquals("update() should not change modifiedBy", _modifiedBy, _model5.getModifiedBy());

		Date _modifiedAt = _model1.getModifiedAt();
		Date _modifiedAt2 = new Date(1000);
		_model1.setModifiedAt(_modifiedAt2);
		TagsModel _model6 = putTag(_model1, Status.OK);	// update should ignore client-side generated modifiedAt
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt)));
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt2)));
		
		deleteTag(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	/**
	 * Retrieve a list of TagTextModel from TagsService by executing a HTTP GET request.
	 * This uses neither position nor size queries.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of TagTextModel object in JSON format
	 */
	public List<TagTextModel> listTags(
			String query, 
			Status expectedStatus) {
		return listTags(tagWC, query, -1, -1, expectedStatus);
	}
	
	/**
	 * Retrieve a list of TagTextModel from TagsService by executing a HTTP GET request.
	 * @param webClient the WebClient for the TagsService
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of TagTextModel objects in JSON format
	 */
	public static List<TagTextModel> listTags(
			WebClient webClient, 
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		System.out.println("listTags(tagWC, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ")");
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
		List<TagTextModel> _tags = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_tags = new ArrayList<TagTextModel>(webClient.getCollection(TagTextModel.class));
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
	public TagsModel postTag(
			TagsModel model, 
			Status expectedStatus) {
		Response _response = tagWC.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("create() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(TagsModel.class);
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
	public static TagsModel postTag(
			WebClient webClient,
			TagsModel model,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(TagsModel.class);
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
	public static TagsModel createTag(
			WebClient webClient,
			Status expectedStatus) 
	{
		return postTag(webClient, new TagsModel(), expectedStatus);
	}
		
	/**
	 * Read the TagsModel with id from TagsService by executing a HTTP GET method.
	 * @param tagId the id of the TagsModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved TagsModel object in JSON format
	 */
	public TagsModel getTag(
			String tagId, 
			Status expectedStatus) {
		return getTag(tagWC, tagId, expectedStatus);
	}
	
	/**
	 * Read the TagsModel with id from TagsService by executing a HTTP GET method.
	 * @param webClient the web client representing the TagsService
	 * @param tagId the id of the TagsModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved TagsModel object in JSON format
	 */
	public static TagsModel getTag(
			WebClient webClient,
			String tagId,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(tagId).get();
		if (expectedStatus != null) {
			assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(TagsModel.class);
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
	public TagsModel putTag(
			TagsModel tm, 
			Status expectedStatus) {
		return putTag(tagWC, tm, expectedStatus);
	}
	
	/**
	 * Update a TagsModel on the TagsService by executing a HTTP PUT method.
	 * @param webClient the web client representing the TagsService
	 * @param model the new TagsModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated TagsModel object in JSON format
	 */
	public static TagsModel putTag(
			WebClient webClient,
			TagsModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(model.getId()).put(model);
		if (expectedStatus != null) {
			assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(TagsModel.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Delete the TagsModel with id on the TagsService by executing a HTTP DELETE method.
	 * @param id the id of the TagsModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public void deleteTag(String id, Status expectedStatus) {
		deleteTag(tagWC, id, expectedStatus);
	}
	
	/**
	 * Delete the TagsModel with id including all its contained LocalizedTextModels on the TagsService by executing a HTTP DELETE method.
	 * @param webClient the WebClient for the TagsService
	 * @param tagId the id of the TagsModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void deleteTag(
			WebClient webClient,
			String tagId,
			Status expectedStatus) {
		Status _expectedStatusGet = expectedStatus == Status.NO_CONTENT ? Status.OK : expectedStatus;
		TagsModel _tagModel = getTag(webClient, tagId, _expectedStatusGet);
		List<LocalizedTextModel> _localizedTextModels = LocalizedTextTest.listLocalizedTexts(webClient, tagId, _expectedStatusGet);
		if (_localizedTextModels != null) {
			for (LocalizedTextModel _localizedTextModel : _localizedTextModels) {
				LocalizedTextTest.deleteLocalizedText(webClient, _tagModel, _localizedTextModel.getId(), expectedStatus);
			}
		}
		Response _response = webClient.replacePath("/").path(tagId).delete();	
		if (expectedStatus != null) {
			assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
	}
}
