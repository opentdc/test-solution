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

public class LocalizedTextTest extends AbstractTestClient {
	public static final String PATH_EL_LANG = "lang";
	private WebClient textWC = null;
	private TextModel textModel = null;

	@Before
	public void initializeTests() {
		textWC = initializeTest(ServiceUtil.TEXTS_API_URL, TextsService.class);
		textModel = TextsTest.create(textWC, "LocalizedTextTest", "MY_DESCRIPTION", Status.OK);
	}

	@After
	public void cleanupTest() {
		TextsTest.delete(textWC, textModel.getId(), Status.NO_CONTENT);
		textWC.close();
	}
	
	/********************************** localizedText attributes tests *********************************/			
	@Test
	public void testEmptyConstructor() {
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("id should not be set by empty constructor", _ltm.getId());
		assertNull("languageCode should not be set by empty constructor", _ltm.getLanguageCode());
		assertNull("text should not be set by empty constructor", _ltm.getText());
	}
	
	@Test
	public void testConstructor() {		
		LocalizedTextModel _ltm = new LocalizedTextModel(LanguageCode.DE, "testConstructor");
		assertNull("id should not be set by constructor", _ltm.getId());
		assertEquals("languageCode should be set by constructor", LanguageCode.DE, _ltm.getLanguageCode());
		assertEquals("text should be set by constructor", "testConstructor", _ltm.getText());
	}
	
	@Test
	public void testIdChange() {
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("id should not be set by constructor", _ltm.getId());
		_ltm.setId("testIdChange");
		assertEquals("id should have changed", "testIdChange", _ltm.getId());
	}

	@Test
	public void testLanguageCodeChange() {
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("languageCode should not be set by empty constructor", _ltm.getLanguageCode());
		_ltm.setLanguageCode(LanguageCode.EN);
		assertEquals("languageCode should have changed:", LanguageCode.EN, _ltm.getLanguageCode());
	}
	
	@Test
	public void testTextChange() {
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("text should not be set by empty constructor", _ltm.getText());
		_ltm.setText("testTextChange");
		assertEquals("text should have changed:", "testTextChange", _ltm.getText());
	}	
	
	@Test
	public void testCreatedBy() {
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("createdBy should not be set by empty constructor", _ltm.getCreatedBy());
		_ltm.setCreatedBy("testProjectCreatedBy");
		assertEquals("createdBy should have changed", "testProjectCreatedBy", _ltm.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("createdAt should not be set by empty constructor", _ltm.getCreatedAt());
		_ltm.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _ltm.getCreatedAt());
	}
		
	@Test
	public void testModifiedBy() {
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("modifiedBy should not be set by empty constructor", _ltm.getModifiedBy());
		_ltm.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _ltm.getModifiedBy());	
	}
	
	@Test
	public void testModifiedAt() {
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("modifiedAt should not be set by empty constructor", _ltm.getModifiedAt());
		_ltm.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _ltm.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		LocalizedTextModel _ltm1 = new LocalizedTextModel();
		assertNull("id should not be set by empty constructor", _ltm1.getId());
		assertNull("languageCode should not be set by empty constructor", _ltm1.getLanguageCode());
		assertNull("text should not be set by empty constructor", _ltm1.getText());
	
		post(_ltm1, Status.BAD_REQUEST);
		_ltm1.setLanguageCode(LanguageCode.ES);
		post(_ltm1, Status.BAD_REQUEST);
		_ltm1.setText("testCreateReadDeleteWithEmptyConstructor");
		LocalizedTextModel _ltm2 = post(_ltm1, Status.OK);

		assertNull("create() should not change the id of the local object", _ltm1.getId());
		assertEquals("create() should not change the languageCode of the local object", LanguageCode.ES, _ltm1.getLanguageCode());
		assertEquals("create() should not change the text of the local object", "testCreateReadDeleteWithEmptyConstructor", _ltm1.getText());
		
		assertNotNull("create() should set a valid id on the remote object returned", _ltm2.getId());
		assertEquals("create() should not change the languageCode", LanguageCode.ES, _ltm2.getLanguageCode());
		assertEquals("create() should not change the text", "testCreateReadDeleteWithEmptyConstructor", _ltm2.getText());
		
		LocalizedTextModel _ltm3 = get(_ltm2.getId(), Status.OK);
			
		assertEquals("id of returned object should be the same", _ltm2.getId(), _ltm3.getId());
		assertEquals("languageCode of returned object should be unchanged after remote create", _ltm2.getLanguageCode(), _ltm3.getLanguageCode());
		assertEquals("text of returned object should be unchanged after remote create", _ltm2.getText(), _ltm3.getText());

		delete(_ltm3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		LocalizedTextModel _ltm1 = new LocalizedTextModel(LanguageCode.FR, "testCreateReadDelete");
		assertNull("id should not be set by constructor", _ltm1.getId());
		assertEquals("languageCode should be set by constructor", LanguageCode.FR, _ltm1.getLanguageCode());
		assertEquals("text should be set by constructor", "testCreateReadDelete", _ltm1.getText());
		
		LocalizedTextModel _ltm2 = post(_ltm1, Status.OK);
		assertNull("id should still be null after remote create", _ltm1.getId());
		assertEquals("create() should not change the languageCode", LanguageCode.FR, _ltm1.getLanguageCode());
		assertEquals("craete() should not change the text", "testCreateReadDelete", _ltm1.getText());
		
		assertNotNull("id of returned object should be set", _ltm2.getId());
		assertEquals("create() should not change the languageCode", LanguageCode.FR, _ltm1.getLanguageCode());
		assertEquals("create() should not change the text", "testCreateReadDelete", _ltm1.getText());

		LocalizedTextModel _ltm3 = get(_ltm2.getId(), Status.OK);
		assertEquals("read() should not change the id", _ltm2.getId(), _ltm3.getId());
		assertEquals("read() should not change the languageCode", _ltm2.getLanguageCode(), _ltm3.getLanguageCode());
		assertEquals("read() should not change the text", _ltm2.getText(), _ltm3.getText());
		
		delete(_ltm3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateWithClientSideId() {
		LocalizedTextModel _ltm1 = new LocalizedTextModel(LanguageCode.RM, "testCreateWithClientSideId");
		_ltm1.setId("LOCAL_ID");
		post(_ltm1, Status.BAD_REQUEST);
	}
	
	@Test
	public void testCreateWithDuplicateId() {
		LocalizedTextModel _ltm1 = create(LanguageCode.IT, "testCreateWithDuplicateId1", Status.OK);
		LocalizedTextModel _ltm2 = new LocalizedTextModel(LanguageCode.RM, "testCreateWithDuplicateId2");
		_ltm2.setId(_ltm1.getId());		// wrongly create a 2nd LocalizedTextModel object with the same ID
		post(_ltm2, Status.CONFLICT);
		delete(_ltm1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testList() {
		ArrayList<LocalizedTextModel> _localList = new ArrayList<LocalizedTextModel>();		
		textWC.replacePath("/").path(textModel.getId()).path(PATH_EL_LANG);
		_localList.add(create(LanguageCode.DE, "testList1", Status.OK));
		_localList.add(create(LanguageCode.EN, "testList2", Status.OK));
		_localList.add(create(LanguageCode.ES, "testList3", Status.OK));
		_localList.add(create(LanguageCode.FR, "testList4", Status.OK));
		_localList.add(create(LanguageCode.IT, "testList5", Status.OK));
		_localList.add(create(LanguageCode.RM, "testList6", Status.OK));
		
		List<LocalizedTextModel> _remoteList = list(Status.OK);

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (LocalizedTextModel _ltm : _remoteList) {
			_remoteListIds.add(_ltm.getId());
		}
		
		for (LocalizedTextModel _ltm : _localList) {
			assertTrue("LocalizedText <" + _ltm.getId() + "> should be listed", _remoteListIds.contains(_ltm.getId()));
		}
		
		for (LocalizedTextModel _ltm : _localList) {
			get(_ltm.getId(), Status.OK);
		}
		
		for (LocalizedTextModel _ltm : _localList) {
			delete(_ltm.getId(), Status.NO_CONTENT);
		}
	}

	@Test
	public void testCreate() {	
		LocalizedTextModel _ltm1 = create(LanguageCode.EN, "testCreate1", Status.OK);
		assertNotNull("ID should be set", _ltm1.getId());
		assertEquals("languageCode1 should be set correctly", LanguageCode.EN, _ltm1.getLanguageCode());
		assertEquals("text1 should be set correctly", "testCreate1", _ltm1.getText());
		
		LocalizedTextModel _ltm2 = create(LanguageCode.DE, "testCreate2", Status.OK);
		assertNotNull("ID should be set", _ltm2.getId());
		assertEquals("languageCode2 should be set correctly", LanguageCode.DE, _ltm2.getLanguageCode());
		assertEquals("text2 should be set correctly", "testCreate2", _ltm2.getText());

		assertThat(_ltm2.getId(), not(equalTo(_ltm1.getId())));

		delete(_ltm1.getId(), Status.NO_CONTENT);
		delete(_ltm2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateDouble() {
		LocalizedTextModel _ltm = create(LanguageCode.ES, "testCreateDouble", Status.OK);
		assertNotNull("ID should be set:", _ltm.getId());
		post(_ltm, Status.CONFLICT);
		delete(_ltm.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDoubleLang() {
		LocalizedTextModel _ltm1 = create(LanguageCode.DE, "testDoubleLang1", Status.OK);
		LocalizedTextModel _ltm2 = create(LanguageCode.EN, "testDoubleLang2", Status.OK);
		create(LanguageCode.EN, "testDoubleLang3", Status.CONFLICT);
		delete(_ltm1.getId(), Status.NO_CONTENT);
		delete(_ltm2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testText() {
		create(LanguageCode.DE, "testDoubleLang 1", Status.BAD_REQUEST);
		create(LanguageCode.DE, "", Status.BAD_REQUEST);
		create(LanguageCode.DE, "test Double Lang 2", Status.BAD_REQUEST);
		LocalizedTextModel _ltm1 = create(LanguageCode.DE, "testDoubleLang3", Status.OK);
		delete(_ltm1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testRead() {
		ArrayList<LocalizedTextModel> _localList = new ArrayList<LocalizedTextModel>();
		textWC.replacePath("/").path(textModel.getId()).path(PATH_EL_LANG);
		_localList.add(create(LanguageCode.DE, "testRead1", Status.OK));
		_localList.add(create(LanguageCode.EN, "testRead2", Status.OK));
		_localList.add(create(LanguageCode.ES, "testRead3", Status.OK));
		_localList.add(create(LanguageCode.FR, "testRead4", Status.OK));
		_localList.add(create(LanguageCode.IT, "testRead5", Status.OK));
		_localList.add(create(LanguageCode.RM, "testRead6", Status.OK));
	
		for (LocalizedTextModel _ltm : _localList) {
			get(_ltm.getId(), Status.OK);
		}
		List<LocalizedTextModel> _remoteList = list(Status.OK);

		for (LocalizedTextModel _ltm : _remoteList) {
			assertEquals("ID should be unchanged when reading a project", _ltm.getId(), get(_ltm.getId(), Status.OK).getId());						
		}

		for (LocalizedTextModel _ltm : _localList) {
			delete(_ltm.getId(), Status.NO_CONTENT);
		}
	}
	
	@Test
	public void testMultiRead() {
		LocalizedTextModel _ltm1 = create(LanguageCode.EN, "testMultiRead", Status.OK);
		LocalizedTextModel _ltm2 = get(_ltm1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read:", _ltm1.getId(), _ltm2.getId());		
		LocalizedTextModel _ltm3 = get(_ltm1.getId(), Status.OK);
		
		assertEquals("ID should be the same:", _ltm2.getId(), _ltm3.getId());
		assertEquals("languageCode should be the same:", _ltm2.getLanguageCode(), _ltm3.getLanguageCode());
		assertEquals("text should be the same:", _ltm2.getText(), _ltm3.getText());
		
		assertEquals("ID should be the same:", _ltm2.getId(), _ltm1.getId());
		assertEquals("languageCode should be the same:", _ltm2.getLanguageCode(), _ltm1.getLanguageCode());
		assertEquals("text should be the same:", _ltm2.getText(), _ltm1.getText());
		
		delete(_ltm1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testUpdate() {
		LocalizedTextModel _ltm1 = create(LanguageCode.IT, "testUpdate", Status.OK);
		_ltm1.setLanguageCode(LanguageCode.RM);
		put(_ltm1, Status.BAD_REQUEST);  // languageCode can not be changed
		_ltm1.setLanguageCode(LanguageCode.IT);
		_ltm1.setText("testUpdate2");
		LocalizedTextModel _ltm2 = put(_ltm1, Status.OK);
		
		assertNotNull("ID should be set", _ltm2.getId());
		assertEquals("ID should be unchanged", _ltm1.getId(), _ltm2.getId());	
		assertEquals("languageCode should not change", LanguageCode.IT, _ltm2.getLanguageCode());
		assertEquals("text should have changed", "testUpdate2", _ltm2.getText());

		_ltm2.setText("testUpdate3");
		LocalizedTextModel _ltm3 = put(_ltm2, Status.OK);

		assertNotNull("ID should be set", _ltm3.getId());
		assertEquals("ID should be unchanged", _ltm2.getId(), _ltm3.getId());	
		assertEquals("languageCode should not change", LanguageCode.IT, _ltm3.getLanguageCode());
		assertEquals("text should have changed", "testUpdate3", _ltm3.getText());
		
		delete(_ltm2.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testDelete(
	) {
		LocalizedTextModel _ltm1 = create(LanguageCode.DE, "testDelete", Status.OK);
		LocalizedTextModel _ltm2 = get(_ltm1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a project (remote):", _ltm1.getId(), _ltm2.getId());						
		delete(_ltm1.getId(), Status.NO_CONTENT);
		delete(_ltm1.getId(), Status.NOT_FOUND);
		get(_ltm1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		LocalizedTextModel _ltm = create(LanguageCode.EN, "testDoubleDelete", Status.OK);
		get(_ltm.getId(), Status.OK);
		delete(_ltm.getId(), Status.NO_CONTENT);
		get(_ltm.getId(), Status.NOT_FOUND);
		delete(_ltm.getId(), Status.NOT_FOUND);
		delete(_ltm.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		LocalizedTextModel _ltm1 = create(LanguageCode.FR, "testModifications", Status.OK);		
		assertNotNull("create() should set createdAt", _ltm1.getCreatedAt());
		assertNotNull("create() should set createdBy", _ltm1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _ltm1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _ltm1.getModifiedBy());
		assertTrue("createdAt and modifiedAt should be identical after create()", _ltm1.getModifiedAt().compareTo(_ltm1.getCreatedAt()) >= 0);
		assertEquals("createdBy and modifiedBy should be identical after create()", _ltm1.getCreatedBy(), _ltm1.getModifiedBy());

		_ltm1.setText("testModifications2");
		LocalizedTextModel _ltm2 = put(_ltm1, Status.OK);
		assertEquals("update() should not change createdAt", _ltm1.getCreatedAt(), _ltm2.getCreatedAt());
		assertEquals("update() should not change createdBy", _ltm1.getCreatedBy(), _ltm2.getCreatedBy());
		assertTrue(_ltm2.getModifiedAt().compareTo(_ltm2.getCreatedAt()) >= 0);
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_ltm2.getModifiedBy(), not(equalTo(_ltm2.getCreatedBy())));

		// update(_ltm1) with modifiedBy/At set on client side -> ignored by server
		_ltm1.setModifiedBy("MYSELF");
		_ltm1.setModifiedAt(new Date(1000));
		LocalizedTextModel _ltm3 = put(_ltm1, Status.OK);
		assertThat(_ltm1.getModifiedBy(), not(equalTo(_ltm3.getModifiedBy())));
		assertThat(_ltm1.getModifiedAt(), not(equalTo(_ltm3.getModifiedAt())));
		delete(_ltm1.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	public List<LocalizedTextModel> list(
			Status expectedStatus) 
	{
		return list(textWC, textModel.getId(), expectedStatus);
	}

	public static List<LocalizedTextModel> list(
			WebClient textWC,
			String textId,
			Status expectedStatus) 
	{
		Response _response = textWC.replacePath("/").path(textId).path(PATH_EL_LANG).get();
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
		return post(textWC, textModel, model, expectedStatus);
	}

	public static LocalizedTextModel post(
			WebClient textWC,
			TextModel text,
			LocalizedTextModel model,
			Status expectedStatus) 
	{
		Response _response = textWC.replacePath("/").path(text.getId()).path(PATH_EL_LANG).post(model);
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
		return post(textWC, textModel, new LocalizedTextModel(langCode, text), status);
	}

	public LocalizedTextModel get(
			String id,
			Status expectedStatus) 
	{
		return get(textWC, textModel, id, expectedStatus);
	}

	public static LocalizedTextModel get(
			WebClient textWC,
			TextModel text,
			String localizedTextId,
			Status expectedStatus) 
	{
		Response _response = textWC.replacePath("/").path(text.getId()).path(PATH_EL_LANG).path(localizedTextId).get();
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
		return put(textWC, textModel, model, expectedStatus);
	}
	
	public static LocalizedTextModel put(
			WebClient textWC,
			TextModel text,
			LocalizedTextModel model,
			Status expectedStatus) {
		textWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = textWC.replacePath("/").path(text.getId()).path(PATH_EL_LANG).path(model.getId()).put(model);
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
		delete(textWC, textModel, id, expectedStatus);
	}

	public static void delete(
			WebClient textWC,
			TextModel text,
			String id,
			Status expectedStatus) 
	{
		Response _response = textWC.replacePath("/").path(text.getId()).path(PATH_EL_LANG).path(id).delete();
		assertEquals("delete should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
	}
	
	protected int calculateMembers() {
		return 1;
	}
}