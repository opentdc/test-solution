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
import org.opentdc.tags.TagsModel;
import org.opentdc.tags.TagsService;
import org.opentdc.util.LanguageCode;

import test.org.opentdc.AbstractTestClient;

public class LocalizedTextTest extends AbstractTestClient {
	public static final String PATH_EL_LANG = "lang";
	private WebClient tagWC = null;
	private TagsModel tag = null;

	@Before
	public void initializeTests() {
		tagWC = initializeTest(TagsTest.API_URL, TagsService.class);
		tag = TagsTest.createTag(tagWC);
	}

	@After
	public void cleanupTest() {
		TagsTest.cleanup(tagWC, tag.getId(), this.getClass().getName());
	}
	
	/********************************** localizedText attributes tests *********************************/			
	@Test
	public void testEmptyConstructor() {
		// new() -> _ltm
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("id should not be set by empty constructor", _ltm.getId());
		assertNull("languageCode should not be set by empty constructor", _ltm.getLangCode());
		assertNull("text should not be set by empty constructor", _ltm.getText());
	}
	
	@Test
	public void testConstructor() {		
		// new(DE, "testConstructor") -> _ltm
		LocalizedTextModel _ltm = new LocalizedTextModel(LanguageCode.DE, "testConstructor");
		assertNull("id should not be set by constructor", _ltm.getId());
		assertEquals("languageCode should be set by constructor", LanguageCode.DE, _ltm.getLangCode());
		assertEquals("text should be set by constructor", "testConstructor", _ltm.getText());
	}
	
	@Test
	public void testIdChange() {
		// new() -> _ltm -> _ltm.setId()
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("id should not be set by constructor", _ltm.getId());
		_ltm.setId("testIdChange");
		assertEquals("id should have changed", "testIdChange", _ltm.getId());
	}

	@Test
	public void testLanguageCodeChange() {
		// new() -> _ltm -> _ltm.setLangCode()
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("languageCode should not be set by empty constructor", _ltm.getLangCode());
		_ltm.setLangCode(LanguageCode.EN);
		assertEquals("languageCode should have changed:", LanguageCode.EN, _ltm.getLangCode());
	}
	
	@Test
	public void testTextChange() {
		// new() -> _ltm -> _ltm.setText()
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("text should not be set by empty constructor", _ltm.getText());
		_ltm.setText("testTextChange");
		assertEquals("text should have changed:", "testTextChange", _ltm.getText());
	}	
	
	@Test
	public void testCreatedBy() {
		// new() -> _ltm -> _ltm.setCreatedBy()
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("createdBy should not be set by empty constructor", _ltm.getCreatedBy());
		_ltm.setCreatedBy("testProjectCreatedBy");
		assertEquals("createdBy should have changed", "testProjectCreatedBy", _ltm.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		// new() -> _ltm -> _ltm.setCreatedAt()
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("createdAt should not be set by empty constructor", _ltm.getCreatedAt());
		_ltm.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _ltm.getCreatedAt());
	}
		
	@Test
	public void testModifiedBy() {
		// new() -> _ltm -> _ltm.setModifiedBy()
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("modifiedBy should not be set by empty constructor", _ltm.getModifiedBy());
		_ltm.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _ltm.getModifiedBy());	
	}
	
	@Test
	public void testModifiedAt() {
		// new() -> _ltm -> _ltm.setModifiedAt()
		LocalizedTextModel _ltm = new LocalizedTextModel();
		assertNull("modifiedAt should not be set by empty constructor", _ltm.getModifiedAt());
		_ltm.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _ltm.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		// new() -> _ltm1
		LocalizedTextModel _ltm1 = new LocalizedTextModel();
		assertNull("id should not be set by empty constructor", _ltm1.getId());
		assertNull("languageCode should not be set by empty constructor", _ltm1.getLangCode());
		assertNull("text should not be set by empty constructor", _ltm1.getText());
		
		// create(_ltm1) -> BAD_REQUEST (because of empty languageCode)
		Response _response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).post(_ltm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_ltm1.setLangCode(LanguageCode.ES);

		// create(_ltm1) -> BAD_REQUEST (because of empty text)
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).post(_ltm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_ltm1.setText("testCreateReadDeleteWithEmptyConstructor");

		// create(_ltm1) -> _ltm2
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).post(_ltm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		LocalizedTextModel _ltm2 = _response.readEntity(LocalizedTextModel.class);
		
		// validate _ltm1 (local object)
		assertNull("create() should not change the id of the local object", _ltm1.getId());
		assertEquals("create() should not change the languageCode of the local object", LanguageCode.ES, _ltm1.getLangCode());
		assertEquals("create() should not change the text of the local object", "testCreateReadDeleteWithEmptyConstructor", _ltm1.getText());
		
		// validate _ltm2 (remote object returned from create())
		assertNotNull("create() should set a valid id on the remote object returned", _ltm2.getId());
		assertEquals("create() should not change the languageCode", LanguageCode.ES, _ltm2.getLangCode());
		assertEquals("create() should not change the text", "testCreateReadDeleteWithEmptyConstructor", _ltm2.getText());
		
		// read(_ltm2) -> _ltm3
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm2.getId()).get();
		assertEquals("read(" + _ltm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		LocalizedTextModel _ltm3 = _response.readEntity(LocalizedTextModel.class);
		
		// validate _ltm3 (remoted objecte returned from read())
		assertEquals("id of returned object should be the same", _ltm2.getId(), _ltm3.getId());
		assertEquals("languageCode of returned object should be unchanged after remote create", _ltm2.getLangCode(), _ltm3.getLangCode());
		assertEquals("text of returned object should be unchanged after remote create", _ltm2.getText(), _ltm3.getText());

		// delete(_ltm3)
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm3.getId()).delete();
		assertEquals("delete(" + _ltm3.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateReadDelete() {
		// new(1) -> _ltm1
		LocalizedTextModel _ltm1 = new LocalizedTextModel(LanguageCode.FR, "testCreateReadDelete");
		assertNull("id should not be set by constructor", _ltm1.getId());
		assertEquals("languageCode should be set by constructor", LanguageCode.FR, _ltm1.getLangCode());
		assertEquals("text should be set by constructor", "testCreateReadDelete", _ltm1.getText());
		
		// create(_ltm1) -> _ltm2
		Response _response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).post(_ltm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		LocalizedTextModel _ltm2 = _response.readEntity(LocalizedTextModel.class);
		
		// validate _ltm1 (local object)
		assertNull("id should still be null after remote create", _ltm1.getId());
		assertEquals("create() should not change the languageCode", LanguageCode.FR, _ltm1.getLangCode());
		assertEquals("craete() should not change the text", "testCreateReadDelete", _ltm1.getText());
		
		// validate _ltm2 (remote object returned from create())
		assertNotNull("id of returned object should be set", _ltm2.getId());
		assertEquals("create() should not change the languageCode", LanguageCode.FR, _ltm1.getLangCode());
		assertEquals("create() should not change the text", "testCreateReadDelete", _ltm1.getText());
		
		// read(_ltm2)  -> _ltm3
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm2.getId()).get();
		assertEquals("read(" + _ltm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		LocalizedTextModel _ltm3 = _response.readEntity(LocalizedTextModel.class);
		
		// validate _ltm3 (remote object returned from read())
		assertEquals("read() should not change the id", _ltm2.getId(), _ltm3.getId());
		assertEquals("read() should not change the languageCode", _ltm2.getLangCode(), _ltm3.getLangCode());
		assertEquals("read() should not change the text", _ltm2.getText(), _ltm3.getText());
		
		// delete(_ltm3)
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm3.getId()).delete();
		assertEquals("delete(" + _ltm3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateWithClientSideId() {
		// new() -> _ltm1 -> _ltm1.setId()
		LocalizedTextModel _ltm1 = new LocalizedTextModel(LanguageCode.RM, "testCreateWithClientSideId");
		_ltm1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _ltm1.getId());
		// create(_ltm1) -> BAD_REQUEST
		Response _response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).post(_ltm1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateWithDuplicateId() {
		// create(new()) -> _ltm1
		Response _response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG)
				.post(new LocalizedTextModel(LanguageCode.IT, "testCreateWithDuplicateId1"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		LocalizedTextModel _ltm1 = _response.readEntity(LocalizedTextModel.class);

		// new() -> _ltm2 -> _ltm2.setId(_ltm1.getId())
		LocalizedTextModel _ltm2 = new LocalizedTextModel(LanguageCode.RM, "testCreateWithDuplicateId2");
		_ltm2.setId(_ltm1.getId());		// wrongly create a 2nd LocalizedTextModel object with the same ID
		
		// create(_ltm2) -> CONFLICT
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).post(_ltm2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testList() {
		ArrayList<LocalizedTextModel> _localList = new ArrayList<LocalizedTextModel>();		
		Response _response = null;
		tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG);
		_localList.add(createLocalizedText(LanguageCode.DE, "testList1", Status.OK));
		_localList.add(createLocalizedText(LanguageCode.EN, "testList2", Status.OK));
		_localList.add(createLocalizedText(LanguageCode.ES, "testList3", Status.OK));
		_localList.add(createLocalizedText(LanguageCode.FR, "testList4", Status.OK));
		_localList.add(createLocalizedText(LanguageCode.IT, "testList5", Status.OK));
		_localList.add(createLocalizedText(LanguageCode.RM, "testList6", Status.OK));
		
		// list(/) -> _remoteList
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).get();
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		List<LocalizedTextModel> _remoteList = new ArrayList<LocalizedTextModel>(tagWC.getCollection(LocalizedTextModel.class));

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (LocalizedTextModel _ltm : _remoteList) {
			_remoteListIds.add(_ltm.getId());
		}
		
		for (LocalizedTextModel _ltm : _localList) {
			assertTrue("LocalizedText <" + _ltm.getId() + "> should be listed", _remoteListIds.contains(_ltm.getId()));
		}
		
		for (LocalizedTextModel _ltm : _localList) {
			_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(LocalizedTextModel.class);
		}
		
		for (LocalizedTextModel _ltm : _localList) {
			_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testCreate() {	
		// new(1) -> _ltm1
		LocalizedTextModel _ltm1 = new LocalizedTextModel(LanguageCode.EN, "testCreate1");
		// new(2) -> _ltm2
		LocalizedTextModel _ltm2 = new LocalizedTextModel(LanguageCode.DE, "testCreate2");
		
		// create(_ltm1)  -> _ltm3
		Response _response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).post(_ltm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		LocalizedTextModel _ltm3 = _response.readEntity(LocalizedTextModel.class);

		// create(_ltm2) -> _ltm4
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).post(_ltm2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		LocalizedTextModel _ltm4 = _response.readEntity(LocalizedTextModel.class);
		
		// validate _ltm3
		assertNotNull("ID should be set", _ltm3.getId());
		assertEquals("languageCode1 should be set correctly", LanguageCode.EN, _ltm3.getLangCode());
		assertEquals("text1 should be set correctly", "testCreate1", _ltm3.getText());
		
		// validate _ltm4
		assertNotNull("ID should be set", _ltm4.getId());
		assertEquals("languageCode2 should be set correctly", LanguageCode.DE, _ltm4.getLangCode());
		assertEquals("text2 should be set correctly", "testCreate2", _ltm4.getText());

		assertThat(_ltm4.getId(), not(equalTo(_ltm3.getId())));

		// delete(_ltm3) -> NO_CONTENT
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_ltm4) -> NO_CONTENT
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateDouble() {		
		// create(new()) -> _ltm
		Response _response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG)
				.post(new LocalizedTextModel(LanguageCode.ES, "testCreateDouble"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		LocalizedTextModel _ltm = _response.readEntity(LocalizedTextModel.class);
		assertNotNull("ID should be set:", _ltm.getId());		
		
		// create(_ltm) -> CONFLICT
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).post(_ltm);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_ltm) -> NO_CONTENT
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testDoubleLang() {
		LocalizedTextModel _ltm1 = createLocalizedText(LanguageCode.DE, "testDoubleLang1", Status.OK);
		LocalizedTextModel _ltm2 = createLocalizedText(LanguageCode.EN, "testDoubleLang2", Status.OK);
		createLocalizedText(LanguageCode.EN, "testDoubleLang3", Status.CONFLICT);
		deleteLocalizedText(_ltm1.getId(), Status.NO_CONTENT);
		deleteLocalizedText(_ltm2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testTagText() {
		createLocalizedText(LanguageCode.DE, "testDoubleLang 1", Status.BAD_REQUEST);
		createLocalizedText(LanguageCode.DE, "", Status.BAD_REQUEST);
		createLocalizedText(LanguageCode.DE, "test Double Lang 2", Status.BAD_REQUEST);
		LocalizedTextModel _ltm1 = createLocalizedText(LanguageCode.DE, "testDoubleLang3", Status.OK);
		deleteLocalizedText(_ltm1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testRead() {
		ArrayList<LocalizedTextModel> _localList = new ArrayList<LocalizedTextModel>();
		Response _response = null;
		tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG);
		_localList.add(createLocalizedText(LanguageCode.DE, "testRead1", Status.OK));
		_localList.add(createLocalizedText(LanguageCode.EN, "testRead2", Status.OK));
		_localList.add(createLocalizedText(LanguageCode.ES, "testRead3", Status.OK));
		_localList.add(createLocalizedText(LanguageCode.FR, "testRead4", Status.OK));
		_localList.add(createLocalizedText(LanguageCode.IT, "testRead5", Status.OK));
		_localList.add(createLocalizedText(LanguageCode.RM, "testRead6", Status.OK));
	
		// test read on each local element
		for (LocalizedTextModel _ltm : _localList) {
			_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(LocalizedTextModel.class);
		}

		// test read on each listed element
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).get();
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		List<LocalizedTextModel> _remoteList = new ArrayList<LocalizedTextModel>(tagWC.getCollection(LocalizedTextModel.class));

		LocalizedTextModel _tmpObj = null;
		for (LocalizedTextModel _ltm : _remoteList) {
			_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(LocalizedTextModel.class);
			assertEquals("ID should be unchanged when reading a project", _ltm.getId(), _tmpObj.getId());						
		}

		for (LocalizedTextModel _ltm : _localList) {
			deleteLocalizedText(_ltm.getId(), Status.NO_CONTENT);
		}
	}
	
	private LocalizedTextModel createLocalizedText(LanguageCode langCode, String text, Status status) {
		Response _response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).post(new LocalizedTextModel(langCode, text));
		assertEquals("create() should return with correct status", status.getStatusCode(), _response.getStatus());
		if (status.getStatusCode() == Status.OK.getStatusCode()) {
			return _response.readEntity(LocalizedTextModel.class);
		}
		else {
			return null;
		}
	}
	
	private void deleteLocalizedText(String id, Status status) {
		Response _response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(id).delete();
		assertEquals("delete() should return with correct status", status.getStatusCode(), _response.getStatus());
	}
		
	@Test
	public void testMultiRead() {
		// new() -> _ltm1
		LocalizedTextModel _ltm1 = new LocalizedTextModel(LanguageCode.EN, "testMultiRead");
		
		// create(_ltm1) -> _p2
		Response _response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).post(_ltm1);
		LocalizedTextModel _ltm2 = _response.readEntity(LocalizedTextModel.class);

		// read(_ltm2) -> _ltm3
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		LocalizedTextModel _ltm3 = _response.readEntity(LocalizedTextModel.class);
		assertEquals("ID should be unchanged after read:", _ltm2.getId(), _ltm3.getId());		

		// read(_ltm2) -> _ltm4
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		LocalizedTextModel _ltm4 = _response.readEntity(LocalizedTextModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same:", _ltm3.getId(), _ltm4.getId());
		assertEquals("languageCode should be the same:", _ltm3.getLangCode(), _ltm4.getLangCode());
		assertEquals("text should be the same:", _ltm3.getText(), _ltm4.getText());
		
		assertEquals("ID should be the same:", _ltm3.getId(), _ltm2.getId());
		assertEquals("languageCode should be the same:", _ltm3.getLangCode(), _ltm2.getLangCode());
		assertEquals("text should be the same:", _ltm3.getText(), _ltm2.getText());
		
		// delete(_ltm2)
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	private LocalizedTextModel updateLocalizedText(LocalizedTextModel ltm, Status status) {
		tagWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(ltm.getId()).put(ltm);
		assertEquals("update() should return with correct status", status.getStatusCode(), _response.getStatus());
		if (status.getStatusCode() == Status.OK.getStatusCode()) {
			return _response.readEntity(LocalizedTextModel.class);
		}
		else {
			return null;
		}
	}
	
	@Test
	public void testUpdate() {
		LocalizedTextModel _ltm1 = createLocalizedText(LanguageCode.IT, "testUpdate", Status.OK);
		_ltm1.setLangCode(LanguageCode.RM);
		updateLocalizedText(_ltm1, Status.BAD_REQUEST);  // languageCode can not be changed
		_ltm1.setLangCode(LanguageCode.IT);
		_ltm1.setText("testUpdate2");
		LocalizedTextModel _ltm2 = updateLocalizedText(_ltm1, Status.OK);
		
		assertNotNull("ID should be set", _ltm2.getId());
		assertEquals("ID should be unchanged", _ltm1.getId(), _ltm2.getId());	
		assertEquals("languageCode should not change", LanguageCode.IT, _ltm2.getLangCode());
		assertEquals("text should have changed", "testUpdate2", _ltm2.getText());

		_ltm2.setText("testUpdate3");
		LocalizedTextModel _ltm3 = updateLocalizedText(_ltm2, Status.OK);

		assertNotNull("ID should be set", _ltm3.getId());
		assertEquals("ID should be unchanged", _ltm2.getId(), _ltm3.getId());	
		assertEquals("languageCode should not change", LanguageCode.IT, _ltm3.getLangCode());
		assertEquals("text should have changed", "testUpdate3", _ltm3.getText());
		
		deleteLocalizedText(_ltm2.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testDelete(
	) {
		// new() -> _ltm1
		LocalizedTextModel _ltm1 = new LocalizedTextModel(LanguageCode.DE, "testDelete");
		// create(_ltm1) -> _ltm2
		Response _response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).post(_ltm1);
		LocalizedTextModel _ltm2 = _response.readEntity(LocalizedTextModel.class);
		
		// read(_ltm2) -> _ltm3
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		LocalizedTextModel _ltm3 = _response.readEntity(LocalizedTextModel.class);
		assertEquals("ID should be unchanged when reading a project (remote):", _ltm2.getId(), _ltm3.getId());						
		
		// delete(_ltm2) -> OK
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_ltm2) -> NOT_FOUND
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_ltm2) -> NOT_FOUND
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testDoubleDelete() {
		// new() -> _ltm1
		LocalizedTextModel _ltm1 = new LocalizedTextModel(LanguageCode.EN, "testDoubleDelete");
		
		// create(_ltm1) -> _ltm2
		Response _response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).post(_ltm1);
		LocalizedTextModel _ltm2 = _response.readEntity(LocalizedTextModel.class);

		// read(_ltm2) -> OK
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_ltm2) -> OK
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm2.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_ltm2) -> NOT_FOUND
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _ltm2 -> NOT_FOUND
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm2.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _ltm2 -> NOT_FOUND
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testModifications() {
		// create(new LocalizedTextModel()) -> _ltm1
		Response _response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG)
				.post(new LocalizedTextModel(LanguageCode.FR, "testModifications"));
		LocalizedTextModel _ltm1 = _response.readEntity(LocalizedTextModel.class);
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _ltm1.getCreatedAt());
		assertNotNull("create() should set createdBy", _ltm1.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _ltm1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _ltm1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _ltm1.getCreatedAt(), _ltm1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _ltm1.getCreatedBy(), _ltm1.getModifiedBy());
		
		// update(_ltm1)  -> _ltm2
		_ltm1.setText("testModifications2");
		tagWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm1.getId()).put(_ltm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		LocalizedTextModel _ltm2 = _response.readEntity(LocalizedTextModel.class);

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _ltm1.getCreatedAt(), _ltm2.getCreatedAt());
		assertEquals("update() should not change createdBy", _ltm1.getCreatedBy(), _ltm2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		assertTrue(_ltm2.getModifiedAt().compareTo(_ltm2.getCreatedAt()) >= 0);
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_ltm2.getModifiedBy(), not(equalTo(_ltm2.getCreatedBy())));

		// update(_ltm1) with modifiedBy/At set on client side -> ignored by server
		_ltm1.setModifiedBy("MYSELF");
		_ltm1.setModifiedAt(new Date(1000));
		tagWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm1.getId()).put(_ltm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		LocalizedTextModel _ltm3 = _response.readEntity(LocalizedTextModel.class);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_ltm1.getModifiedBy(), not(equalTo(_ltm3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_ltm1.getModifiedAt(), not(equalTo(_ltm3.getModifiedAt())));
		
		// delete(_o) -> NO_CONTENT
		_response = tagWC.replacePath("/").path(tag.getId()).path(PATH_EL_LANG).path(_ltm1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

}