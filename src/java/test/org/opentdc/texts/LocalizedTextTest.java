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
package test.org.opentdc.texts;

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
import org.opentdc.texts.TextModel;
import org.opentdc.texts.TextsService;
import org.opentdc.util.LanguageCode;

import test.org.opentdc.AbstractTestClient;

/**
 * Testing localized texts in TextService.
 * @author Bruno Kaiser
 *
 */
public class LocalizedTextTest extends AbstractTestClient {
	private static final String CN = "LocalizedTextTest";
	private WebClient wc = null;
	private TextModel textModel = null;

	@Before
	public void initializeTests() {
		wc = initializeTest(ServiceUtil.TEXTS_API_URL, TextsService.class);
		textModel = TextTest.create(wc, CN, "MY_DESCRIPTION", Status.OK);
	}

	@After
	public void cleanupTest() {
		TextTest.delete(wc, textModel.getId(), Status.NO_CONTENT);
		wc.close();
	}
	
	/********************************** localizedText attributes tests *********************************/			
	@Test
	public void testEmptyConstructor() {
		LocalizedTextModel _model = new LocalizedTextModel();
		assertNull("id should not be set by empty constructor", _model.getId());
		assertNull("languageCode should not be set by empty constructor", _model.getLanguageCode());
		assertNull("text should not be set by empty constructor", _model.getText());
	}
	
	@Test
	public void testConstructor() {		
		LocalizedTextModel _model = new LocalizedTextModel(LanguageCode.DE, "testConstructor");
		assertNull("id should not be set by constructor", _model.getId());
		assertEquals("languageCode should be set by constructor", LanguageCode.DE, _model.getLanguageCode());
		assertEquals("text should be set by constructor", "testConstructor", _model.getText());
	}
	
	@Test
	public void testId() {
		LocalizedTextModel _model = new LocalizedTextModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed", "testId", _model.getId());
	}

	@Test
	public void testLanguageCode() {
		LocalizedTextModel _model = new LocalizedTextModel();
		assertNull("languageCode should not be set by empty constructor", _model.getLanguageCode());
		_model.setLanguageCode(LanguageCode.EN);
		assertEquals("languageCode should have changed:", LanguageCode.EN, _model.getLanguageCode());
	}
	
	@Test
	public void testText() {
		LocalizedTextModel _model = new LocalizedTextModel();
		assertNull("text should not be set by empty constructor", _model.getText());
		_model.setText("testText");
		assertEquals("text should have changed:", "testText", _model.getText());
	}	
	
	@Test
	public void testCreatedBy() {
		LocalizedTextModel _model = new LocalizedTextModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		LocalizedTextModel _model = new LocalizedTextModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	@Test
	public void testModifiedBy() {
		LocalizedTextModel _model = new LocalizedTextModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	@Test
	public void testModifiedAt() {
		LocalizedTextModel _model = new LocalizedTextModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		LocalizedTextModel _model1 = new LocalizedTextModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("languageCode should not be set by empty constructor", _model1.getLanguageCode());
		assertNull("text should not be set by empty constructor", _model1.getText());
	
		post(_model1, Status.BAD_REQUEST);
		_model1.setLanguageCode(LanguageCode.ES);
		post(_model1, Status.BAD_REQUEST);
		_model1.setText("testCreateReadDeleteWithEmptyConstructor");
		LocalizedTextModel _model2 = post(_model1, Status.OK);

		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the languageCode of the local object", LanguageCode.ES, _model1.getLanguageCode());
		assertEquals("create() should not change the text of the local object", "testCreateReadDeleteWithEmptyConstructor", _model1.getText());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("create() should not change the languageCode", LanguageCode.ES, _model2.getLanguageCode());
		assertEquals("create() should not change the text", "testCreateReadDeleteWithEmptyConstructor", _model2.getText());
		
		LocalizedTextModel _model3 = get(_model2.getId(), Status.OK);
			
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("languageCode of returned object should be unchanged after remote create", _model2.getLanguageCode(), _model3.getLanguageCode());
		assertEquals("text of returned object should be unchanged after remote create", _model2.getText(), _model3.getText());

		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		LocalizedTextModel _model1 = new LocalizedTextModel(LanguageCode.FR, "testCreateReadDelete");
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("languageCode should be set by constructor", LanguageCode.FR, _model1.getLanguageCode());
		assertEquals("text should be set by constructor", "testCreateReadDelete", _model1.getText());
		
		LocalizedTextModel _model2 = post(_model1, Status.OK);
		assertNull("id should still be null after remote create", _model1.getId());
		assertEquals("create() should not change the languageCode", LanguageCode.FR, _model1.getLanguageCode());
		assertEquals("craete() should not change the text", "testCreateReadDelete", _model1.getText());
		
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("create() should not change the languageCode", LanguageCode.FR, _model1.getLanguageCode());
		assertEquals("create() should not change the text", "testCreateReadDelete", _model1.getText());

		LocalizedTextModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("read() should not change the id", _model2.getId(), _model3.getId());
		assertEquals("read() should not change the languageCode", _model2.getLanguageCode(), _model3.getLanguageCode());
		assertEquals("read() should not change the text", _model2.getText(), _model3.getText());
		
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testClientSideId() {
		LocalizedTextModel _model = new LocalizedTextModel(LanguageCode.RM, "testClientSideId");
		_model.setId("LOCAL_ID");
		post(_model, Status.BAD_REQUEST);
	}
	
	@Test
	public void testDuplicateId() {
		LocalizedTextModel _model1 = create(LanguageCode.IT, "testDuplicateId1", Status.OK);
		LocalizedTextModel _model2 = new LocalizedTextModel(LanguageCode.RM, "testDuplicateId2");
		_model2.setId(_model1.getId());		// wrongly create a 2nd LocalizedTextModel object with the same ID
		post(_model2, Status.CONFLICT);
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testList() {
		ArrayList<LocalizedTextModel> _localList = new ArrayList<LocalizedTextModel>();		
		wc.replacePath("/").path(textModel.getId()).path(ServiceUtil.LANG_PATH_EL);
		_localList.add(create(LanguageCode.DE, "testList1", Status.OK));
		_localList.add(create(LanguageCode.EN, "testList2", Status.OK));
		_localList.add(create(LanguageCode.ES, "testList3", Status.OK));
		_localList.add(create(LanguageCode.FR, "testList4", Status.OK));
		_localList.add(create(LanguageCode.IT, "testList5", Status.OK));
		_localList.add(create(LanguageCode.RM, "testList6", Status.OK));
		
		List<LocalizedTextModel> _remoteList = list(Status.OK);

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (LocalizedTextModel _model : _remoteList) {
			_remoteListIds.add(_model.getId());
		}
		
		for (LocalizedTextModel _model : _localList) {
			assertTrue("LocalizedText <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		
		for (LocalizedTextModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		
		for (LocalizedTextModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}

	@Test
	public void testCreate() {	
		LocalizedTextModel _model1 = create(LanguageCode.EN, "testCreate1", Status.OK);
		assertNotNull("ID should be set", _model1.getId());
		assertEquals("languageCode1 should be set correctly", LanguageCode.EN, _model1.getLanguageCode());
		assertEquals("text1 should be set correctly", "testCreate1", _model1.getText());
		
		LocalizedTextModel _model2 = create(LanguageCode.DE, "testCreate2", Status.OK);
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("languageCode2 should be set correctly", LanguageCode.DE, _model2.getLanguageCode());
		assertEquals("text2 should be set correctly", "testCreate2", _model2.getText());

		assertThat(_model2.getId(), not(equalTo(_model1.getId())));

		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateDouble() {
		LocalizedTextModel _model = create(LanguageCode.ES, "testCreateDouble", Status.OK);
		assertNotNull("ID should be set:", _model.getId());
		post(_model, Status.CONFLICT);
		delete(_model.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDoubleLang() {
		LocalizedTextModel _model1 = create(LanguageCode.DE, "testDoubleLang1", Status.OK);
		LocalizedTextModel _model2 = create(LanguageCode.EN, "testDoubleLang2", Status.OK);
		create(LanguageCode.EN, "testDoubleLang3", Status.CONFLICT);
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testRead() {
		ArrayList<LocalizedTextModel> _localList = new ArrayList<LocalizedTextModel>();
		wc.replacePath("/").path(textModel.getId()).path(ServiceUtil.LANG_PATH_EL);
		_localList.add(create(LanguageCode.DE, "testRead1", Status.OK));
		_localList.add(create(LanguageCode.EN, "testRead2", Status.OK));
		_localList.add(create(LanguageCode.ES, "testRead3", Status.OK));
		_localList.add(create(LanguageCode.FR, "testRead4", Status.OK));
		_localList.add(create(LanguageCode.IT, "testRead5", Status.OK));
		_localList.add(create(LanguageCode.RM, "testRead6", Status.OK));
	
		for (LocalizedTextModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		List<LocalizedTextModel> _remoteList = list(Status.OK);

		for (LocalizedTextModel _model : _remoteList) {
			assertEquals("ID should be unchanged when reading a project", _model.getId(), get(_model.getId(), Status.OK).getId());						
		}

		for (LocalizedTextModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}
	
	@Test
	public void testMultiRead() {
		LocalizedTextModel _model1 = create(LanguageCode.EN, "testMultiRead", Status.OK);
		LocalizedTextModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read:", _model1.getId(), _model2.getId());		
		LocalizedTextModel _ltm3 = get(_model1.getId(), Status.OK);
		
		assertEquals("ID should be the same:", _model2.getId(), _ltm3.getId());
		assertEquals("languageCode should be the same:", _model2.getLanguageCode(), _ltm3.getLanguageCode());
		assertEquals("text should be the same:", _model2.getText(), _ltm3.getText());
		
		assertEquals("ID should be the same:", _model2.getId(), _model1.getId());
		assertEquals("languageCode should be the same:", _model2.getLanguageCode(), _model1.getLanguageCode());
		assertEquals("text should be the same:", _model2.getText(), _model1.getText());
		
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testUpdate() {
		LocalizedTextModel _model1 = create(LanguageCode.IT, "testUpdate", Status.OK);
		_model1.setLanguageCode(LanguageCode.RM);
		put(_model1, Status.BAD_REQUEST);  // languageCode can not be changed
		_model1.setLanguageCode(LanguageCode.IT);
		_model1.setText("testUpdate2");
		LocalizedTextModel _model2 = put(_model1, Status.OK);
		
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		assertEquals("languageCode should not change", LanguageCode.IT, _model2.getLanguageCode());
		assertEquals("text should have changed", "testUpdate2", _model2.getText());

		_model2.setText("testUpdate3");
		LocalizedTextModel _model3 = put(_model2, Status.OK);

		assertNotNull("ID should be set", _model3.getId());
		assertEquals("ID should be unchanged", _model2.getId(), _model3.getId());	
		assertEquals("languageCode should not change", LanguageCode.IT, _model3.getLanguageCode());
		assertEquals("text should have changed", "testUpdate3", _model3.getText());
		
		delete(_model2.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testDelete() {
		LocalizedTextModel _model1 = create(LanguageCode.DE, "testDelete", Status.OK);
		LocalizedTextModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a project (remote):", _model1.getId(), _model2.getId());						
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		LocalizedTextModel _model = create(LanguageCode.EN, "testDoubleDelete", Status.OK);
		get(_model.getId(), Status.OK);
		delete(_model.getId(), Status.NO_CONTENT);
		get(_model.getId(), Status.NOT_FOUND);
		delete(_model.getId(), Status.NOT_FOUND);
		delete(_model.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		LocalizedTextModel _model1 = create(LanguageCode.FR, "testModifications", Status.OK);		
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertTrue("createdAt and modifiedAt should be identical after create()", _model1.getModifiedAt().compareTo(_model1.getCreatedAt()) >= 0);
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());

		_model1.setText("testModifications2");
		LocalizedTextModel _model2 = put(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		assertTrue(_model2.getModifiedAt().compareTo(_model2.getCreatedAt()) >= 0);
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		_model1.setModifiedBy("MYSELF");
		_model1.setModifiedAt(new Date(1000));
		LocalizedTextModel _ltm3 = put(_model1, Status.OK);
		assertThat(_model1.getModifiedBy(), not(equalTo(_ltm3.getModifiedBy())));
		assertThat(_model1.getModifiedAt(), not(equalTo(_ltm3.getModifiedAt())));
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	public List<LocalizedTextModel> list(
			Status expectedStatus) 
	{
		return list(wc, textModel.getId(), expectedStatus);
	}

	public static List<LocalizedTextModel> list(
			WebClient textWC,
			String textId,
			Status expectedStatus) 
	{
		Response _response = textWC.replacePath("/").path(textId).path(ServiceUtil.LANG_PATH_EL).get();
		assertEquals("get should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return new ArrayList<LocalizedTextModel>(textWC.getCollection(LocalizedTextModel.class));
		} else {
			return null;
		}
	}
	
	public LocalizedTextModel post(
			LocalizedTextModel model,
			Status expectedStatus) 
	{
		return post(wc, textModel, model, expectedStatus);
	}

	public static LocalizedTextModel post(
			WebClient textWC,
			TextModel text,
			LocalizedTextModel model,
			Status expectedStatus) 
	{
		Response _response = textWC.replacePath("/").path(text.getId()).path(ServiceUtil.LANG_PATH_EL).post(model);
		assertEquals("post should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(LocalizedTextModel.class);
		} else {
			return null;
		}
	}
	
	private LocalizedTextModel create(
			LanguageCode langCode, 
			String text, 
			Status status) {
		return post(wc, textModel, new LocalizedTextModel(langCode, text), status);
	}

	public LocalizedTextModel get(
			String id,
			Status expectedStatus) 
	{
		return get(wc, textModel, id, expectedStatus);
	}

	public static LocalizedTextModel get(
			WebClient textWC,
			TextModel text,
			String localizedTextId,
			Status expectedStatus) 
	{
		Response _response = textWC.replacePath("/").path(text.getId()).path(ServiceUtil.LANG_PATH_EL).path(localizedTextId).get();
		assertEquals("get should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(LocalizedTextModel.class);
		} else {
			return null;
		}
	}

	private LocalizedTextModel put(
			LocalizedTextModel model, 
			Status expectedStatus) {
		return put(wc, textModel, model, expectedStatus);
	}
	
	public static LocalizedTextModel put(
			WebClient textWC,
			TextModel text,
			LocalizedTextModel model,
			Status expectedStatus) {
		textWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = textWC.replacePath("/").path(text.getId()).path(ServiceUtil.LANG_PATH_EL).path(model.getId()).put(model);
		assertEquals("update() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(LocalizedTextModel.class);
		}
		else {
			return null;
		}
	}
	
	public void delete(
			String id,
			Status expectedStatus) 
	{
		delete(wc, textModel, id, expectedStatus);
	}

	public static void delete(
			WebClient textWC,
			TextModel text,
			String id,
			Status expectedStatus) 
	{
		Response _response = textWC.replacePath("/").path(text.getId()).path(ServiceUtil.LANG_PATH_EL).path(id).delete();
		assertEquals("delete should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
	}
	
	protected int calculateMembers() {
		return 1;
	}
}