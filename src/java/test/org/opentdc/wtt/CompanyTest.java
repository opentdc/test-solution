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
package test.org.opentdc.wtt;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.WttService;

import test.org.opentdc.AbstractTestClient;

public class CompanyTest extends AbstractTestClient<WttService> {
	
	private static final String API = "api/company/";	
	
	@Before
	public void initializeTest(
	) {
		initializeTest(API, WttService.class);
	}
	
	/********************************** company tests *********************************/	
	@Test
	public void testCompanyModelEmptyConstructor() {
		// new() -> _c
		CompanyModel _c = new CompanyModel();
		assertNull("id should not be set by empty constructor", _c.getId());
		assertNull("title should not be set by empty constructor", _c.getTitle());
		assertNull("description should not be set by empty constructor", _c.getDescription());
	}
	
	@Test
	public void testCompanyModelConstructor() {		
		// new("MY_TITLE", "MY_DESC") -> _c
		CompanyModel _c = new CompanyModel("MY_TITLE", "MY_DESC");
		assertNull("id should not be set by constructor", _c.getId());
		assertEquals("title should be set by constructor", "MY_TITLE", _c.getTitle());
		assertEquals("description should not set by constructor", "MY_DESC", _c.getDescription());
	}
	
	@Test
	public void testCompanyIdAttributeChange() {
		// new() -> _c -> _c.setId()
		CompanyModel _c = new CompanyModel();
		assertNull("id should not be set by constructor", _c.getId());
		_c.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _c.getId());
	}

	@Test
	public void testCompanyTitleAttributeChange() {
		// new() -> _c -> _c.setTitle()
		CompanyModel _c = new CompanyModel();
		assertNull("title should not be set by empty constructor", _c.getTitle());
		_c.setTitle("MY_TITLE");
		assertEquals("title should have changed:", "MY_TITLE", _c.getTitle());
	}
	
	@Test
	public void testCompanyDescriptionAttributeChange() {
		// new() -> _c -> _c.setDescription()
		CompanyModel _c = new CompanyModel();
		assertNull("description should not be set by empty constructor", _c.getDescription());
		_c.setDescription("MY_DESC");
		assertEquals("description should have changed:", "MY_DESC", _c.getDescription());
	}
	
	@Test
	public void testCompanyCreateReadDeleteWithEmptyConstructor() {
		// new() -> _c1
		CompanyModel _c1 = new CompanyModel();
		assertNull("id should not be set by empty constructor", _c1.getId());
		assertNull("title should not be set by empty constructor", _c1.getTitle());
		assertNull("description should not be set by empty constructor", _c1.getDescription());
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c2 = _response.readEntity(CompanyModel.class);
		assertNull("create() should not change the id of the local object", _c1.getId());
		assertNull("create() should not change the title of the local object", _c1.getTitle());
		assertNull("create() should not change the description of the local object", _c1.getDescription());
		assertNotNull("create() should set a valid id on the remote object returned", _c2.getId());
		assertNull("title of returned object should still be null after remote create", _c2.getTitle());
		assertNull("description of returned object should still be null after remote create", _c2.getDescription());
		
		// read(_c2) -> _c3
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c3 = _response.readEntity(CompanyModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _c2.getTitle(), _c3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _c2.getDescription(), _c3.getDescription());
		// delete(_c3)
		_response = webclient.replacePath(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCompanyCreateReadDelete() {
		// new("MY_TITLE", "MY_DESC") -> _c1
		CompanyModel _c1 = new CompanyModel("MY_TITLE", "MY_DESC");
		assertNull("id should not be set by constructor", _c1.getId());
		assertEquals("title should be set by constructor", "MY_TITLE", _c1.getTitle());
		assertEquals("description should be set by constructor", "MY_DESC", _c1.getDescription());
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c2 = _response.readEntity(CompanyModel.class);
		assertNull("id should be still null after remote create", _c1.getId());
		assertEquals("title should be unchanged after remote create", "MY_TITLE", _c1.getTitle());
		assertEquals("description should be unchanged after remote create", "MY_DESC", _c1.getDescription());
		
		assertNotNull("id of returned object should be set", _c2.getId());
		assertEquals("title of returned object should be unchanged after remote create", "MY_TITLE", _c2.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", "MY_DESC", _c2.getDescription());
		// read(_c2)  -> _c3
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c3 = _response.readEntity(CompanyModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _c2.getTitle(), _c3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _c2.getDescription(), _c3.getDescription());
		// delete(_c3)
		_response = webclient.replacePath(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateCompanyWithClientSideId() {
		// new() -> _c1 -> _c1.setId()
		CompanyModel _c1 = new CompanyModel();
		_c1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _c1.getId());
		// create(_c1) -> BAD_REQUEST
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateCompanyWithDuplicateId() {
		// create(new()) -> _c2
		Response _response = webclient.replacePath("/").post(new CompanyModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c2 = _response.readEntity(CompanyModel.class);

		// new() -> _c3 -> _c3.setId(_c2.getId())
		CompanyModel _c3 = new CompanyModel();
		_c3.setId(_c2.getId());		// wrongly create a 2nd CompanyModel object with the same ID
		
		// create(_c3) -> CONFLICT
		_response = webclient.replacePath("/").post(_c3);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCompanyList(
	) {		
		ArrayList<CompanyModel> _localList = new ArrayList<CompanyModel>();
		Response _response = null;
		webclient.replacePath("/");
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = webclient.post(new CompanyModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(CompanyModel.class));
		}
		
		// list(/) -> _remoteList
		_response = webclient.replacePath("/").get();
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		List<CompanyModel> _remoteList = new ArrayList<CompanyModel>(webclient.getCollection(CompanyModel.class));

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (CompanyModel _c : _remoteList) {
			_remoteListIds.add(_c.getId());
		}
		
		for (CompanyModel _c : _localList) {
			assertTrue("company <" + _c.getId() + "> should be listed", _remoteListIds.contains(_c.getId()));
		}
		for (CompanyModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(CompanyModel.class);
		}
		for (CompanyModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testCompanyCreate() {
		// new("MY_TITLE", "MY_DESC") -> _c1
		CompanyModel _c1 = new CompanyModel("MY_TITLE", "MY_DESC");
		// new("MY_TITLE2", "MY_DESC2") -> _c2
		CompanyModel _c2 = new CompanyModel("MY_TITLE2", "MY_DESC2");
		
		// create(_c1)  -> _c3
		Response _response = webclient.post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c3 = _response.readEntity(CompanyModel.class);

		// create(_c2) -> _c4
		_response = webclient.post(_c2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c4 = _response.readEntity(CompanyModel.class);		
		assertNotNull("ID should be set", _c3.getId());
		assertNotNull("ID should be set", _c4.getId());
		assertThat(_c4.getId(), not(equalTo(_c3.getId())));
		assertEquals("title1 should be set correctly", "MY_TITLE", _c3.getTitle());
		assertEquals("description1 should be set correctly", "MY_DESC", _c3.getDescription());
		assertEquals("title2 should be set correctly", "MY_TITLE2", _c4.getTitle());
		assertEquals("description2 should be set correctly", "MY_DESC2", _c4.getDescription());

		// delete(_c1) -> METHOD_NOT_ALLOWED (_c1.getId() = null)
		_response = webclient.replacePath(_c1.getId()).delete();
		assertEquals("delete() should return with status METHOD_NOT_ALLOWED", Status.METHOD_NOT_ALLOWED.getStatusCode(), _response.getStatus());

		// delete(_c2) -> METHOD_NOT_ALLOWED (_c2.getId() = null)
		_response = webclient.replacePath(_c2.getId()).delete();
		assertEquals("delete() should return with status METHOD_NOT_ALLOWED", Status.METHOD_NOT_ALLOWED.getStatusCode(), _response.getStatus());

		// delete(_c3) -> NO_CONTENT
		_response = webclient.replacePath(_c3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_c4) -> NO_CONTENT
		_response = webclient.replacePath(_c4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCompanyDoubleCreate(
	) {
		// create(new()) -> _c
		Response _response = webclient.replacePath("/").post(new CompanyModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c = _response.readEntity(CompanyModel.class);
		assertNotNull("ID should be set:", _c.getId());		
		
		// create(_c) -> CONFLICT
		_response = webclient.replacePath("/").post(_c);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_c) -> NO_CONTENT
		_response = webclient.replacePath(_c.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testCompanyRead(
	) {
		ArrayList<CompanyModel> _localList = new ArrayList<CompanyModel>();
		Response _response = null;
		webclient.replacePath("/");
		for (int i = 0; i < LIMIT; i++) {
			_response = webclient.post(new CompanyModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(CompanyModel.class));
		}
	
		// test read on each local element
		for (CompanyModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(CompanyModel.class);
		}

		// test read on each listed element
		_response = webclient.replacePath("/").get();
		List<CompanyModel> _remoteList = new ArrayList<CompanyModel>(webclient.getCollection(CompanyModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		CompanyModel _tmpObj = null;
		for (CompanyModel _c : _remoteList) {
			_response = webclient.replacePath(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(CompanyModel.class);
			assertEquals("ID should be unchanged when reading a company", _c.getId(), _tmpObj.getId());						
		}

		// TODO: "reading a company with ID = null should fail" -> ValidationException
		// TODO: "reading a non-existing company should fail"

		for (CompanyModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testCompanyMultiRead(
	) {
		// new() -> _c1
		CompanyModel _c1 = new CompanyModel();
		
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		CompanyModel _c2 = _response.readEntity(CompanyModel.class);

		// read(_c2) -> _c3
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c3 = _response.readEntity(CompanyModel.class);
		assertEquals("ID should be unchanged after read", _c2.getId(), _c3.getId());		

		// read(_c2) -> _c4
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c4 = _response.readEntity(CompanyModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same", _c3.getId(), _c4.getId());
		assertEquals("title should be the same", _c3.getTitle(), _c4.getTitle());
		assertEquals("description should be the same", _c3.getDescription(), _c4.getDescription());
		
		assertEquals("ID should be the same", _c3.getId(), _c2.getId());
		assertEquals("title should be the same", _c3.getTitle(), _c2.getTitle());
		assertEquals("description should be the same", _c3.getDescription(), _c2.getDescription());
		
		// delete(_c2)
		_response = webclient.replacePath(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCompanyUpdate(
	) {
		// new() -> _c1
		CompanyModel _c1 = new CompanyModel();
		
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		CompanyModel _c2 = _response.readEntity(CompanyModel.class);
		
		// change the attributes
		// update(_c2) -> _c3
		_c2.setTitle("MY_TITLE");
		_c2.setDescription("MY_DESC");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_c2.getId()).put(_c2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c3 = _response.readEntity(CompanyModel.class);

		assertNotNull("ID should be set", _c3.getId());
		assertEquals("ID should be unchanged", _c2.getId(), _c3.getId());	
		assertEquals("title should have changed", "MY_TITLE", _c3.getTitle());
		assertEquals("description should have changed", "MY_DESC", _c3.getDescription());

		// reset the attributes
		// update(_c2) -> _c4
		_c2.setTitle("MY_TITLE2");
		_c2.setDescription("MY_DESC2");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_c2.getId()).put(_c2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c4 = _response.readEntity(CompanyModel.class);

		assertNotNull("ID should be set", _c4.getId());
		assertEquals("ID should be unchanged", _c2.getId(), _c4.getId());	
		assertEquals("title should have changed", "MY_TITLE2", _c4.getTitle());
		assertEquals("description should have changed", "MY_DESC2", _c4.getDescription());
		
		_response = webclient.replacePath(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCompanyDelete(
	) {
		// new() -> _c0
		CompanyModel _c0 = new CompanyModel();
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").post(_c0);
		CompanyModel _c1 = _response.readEntity(CompanyModel.class);
		
		// read(_c0) -> _tmpObj
		_response = webclient.replacePath(_c0.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _tmpObj = _response.readEntity(CompanyModel.class);
		assertEquals("ID should be unchanged when reading a company (remote):", _c0.getId(), _tmpObj.getId());						

		// read(_c1) -> _tmpObj
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		_tmpObj = _response.readEntity(CompanyModel.class);
		assertEquals("ID should be unchanged when reading a company (remote):", _c1.getId(), _tmpObj.getId());						
		
		// delete(_c1) -> OK
		_response = webclient.replacePath(_c1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCompanyDoubleDelete(
	) {
		// new() -> _c0
		CompanyModel _c0 = new CompanyModel();
		
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").post(_c0);
		CompanyModel _c1 = _response.readEntity(CompanyModel.class);

		// read(_c1) -> OK
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_c1) -> OK
		_response = webclient.replacePath(_c1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _c1 -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _c1 -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
}
