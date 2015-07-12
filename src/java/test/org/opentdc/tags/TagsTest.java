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
import org.opentdc.tags.TagsModel;
import org.opentdc.tags.TagsService;

import test.org.opentdc.AbstractTestClient;

public class TagsTest extends AbstractTestClient {
	public static final String API_URL = "api/tag/";
	private WebClient tagWC = null;

	@Before
	public void initializeTest() {
		tagWC = initializeTest(API_URL, TagsService.class);
	}
	
	@After
	public void cleanupTest() {
		tagWC.close();
	}

	/********************************** tags attributes tests *********************************/	
	@Test
	public void testTagsModelEmptyConstructor() {
		// new() -> _c
		TagsModel _tm = new TagsModel();
		assertNull("id should not be set by empty constructor", _tm.getId());
		assertEquals("counter should be 0", 0, _tm.getCounter());
	}
	
	@Test
	public void testTagsModelIdAttributeChange() {
		// new() -> _tm -> _rm.setId()
		TagsModel _tm = new TagsModel();
		assertNull("id should not be set by constructor", _tm.getId());
		_tm.setId("MY_ID");
		assertEquals("id should have changed", "MY_ID", _tm.getId());
	}

	@Test
	public void testTagsModelCounterAttributeChange() {
		// new() -> _tm -> _tm.setCounter()
		TagsModel _tm = new TagsModel();
		assertEquals("Counter should be 0", 0, _tm.getCounter());
		_tm.setCounter(100);
		assertEquals("Counter should have changed", 100, _tm.getCounter());
	}
	
	@Test
	public void testTagsCreatedBy() {
		// new() -> _tm -> _tm.setCreatedBy()
		TagsModel _tm = new TagsModel();
		assertNull("createdBy should not be set by empty constructor", _tm.getCreatedBy());
		_tm.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _tm.getCreatedBy());	
	}
	
	@Test
	public void testTagsCreatedAt() {
		// new() -> _tm -> _tm.setCreatedAt()
		TagsModel _tm = new TagsModel();
		assertNull("createdAt should not be set by empty constructor", _tm.getCreatedAt());
		_tm.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _tm.getCreatedAt());
	}
		
	@Test
	public void testTagsModifiedBy() {
		// new() -> _tm -> _tm.setModifiedBy()
		TagsModel _tm = new TagsModel();
		assertNull("modifiedBy should not be set by empty constructor", _tm.getModifiedBy());
		_tm.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _tm.getModifiedBy());	
	}
	
	@Test
	public void testTagsModifiedAt() {
		// new() -> _tm -> _tm.setModifiedAt()
		TagsModel _tm = new TagsModel();
		assertNull("modifiedAt should not be set by empty constructor", _tm.getModifiedAt());
		_tm.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _tm.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testTagCreateReadDeleteWithEmptyConstructor() {
		// new() -> _tm1
		TagsModel _tm1 = new TagsModel();
		assertNull("id should not be set by empty constructor", _tm1.getId());
		assertEquals("counter should be 0", 0, _tm1.getCounter());
		
		// create(_rm1) -> _tm2
		Response _response = tagWC.replacePath("/").post(_tm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _tm2 = _response.readEntity(TagsModel.class);
		
		// validate _rm1
		assertNull("create() should not change the id of the local object", _tm1.getId());
		assertEquals("create() should not change the counter of the local object", 0, _tm1.getCounter());
		
		// validate _rm2
		assertNotNull("create() should set a valid id on the remote object returned", _tm2.getId());
		assertEquals("create() should  change the counter", 1, _tm2.getCounter());
		
		// read(_rm2) -> _tm3
		_response = tagWC.replacePath("/").path(_tm2.getId()).get();
		assertEquals("read(" + _tm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _tm3 = _response.readEntity(TagsModel.class);
		assertEquals("id of returned object should be the same", _tm2.getId(), _tm3.getId());
		assertEquals("counter of returned object should be unchanged", _tm2.getCounter(), _tm3.getCounter());
		// delete(_tm3)
		_response = tagWC.replacePath("/").path(_tm3.getId()).delete();
		assertEquals("delete(" + _tm3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateTagWithClientSideId() {
		// new() -> _tm -> _tm.setId()
		TagsModel _tm = new TagsModel();
		_tm.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _tm.getId());
		// create(_rm) -> BAD_REQUEST
		Response _response = tagWC.replacePath("/").post(_tm);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateTagWithDuplicateId() {
		// create(new()) -> _tm1
		Response _response = tagWC.replacePath("/").post(new TagsModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _tm1 = _response.readEntity(TagsModel.class);
		assertEquals("counter should be correct", 1, _tm1.getCounter());

		// read(_tm1)
		_response = tagWC.replacePath("/").path(_tm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _tm2 = _response.readEntity(TagsModel.class);
		assertEquals("counter should be correct", 1, _tm2.getCounter());

		// new() -> _tm3 -> _tm3.setId(_tm1.getId())
		TagsModel _tm3 = new TagsModel();
		_tm3.setId(_tm1.getId());

		// create(_tm3) -> OK (counter incremented) -> _tm4_
		_response = tagWC.replacePath("/").post(_tm3);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _tm4 = _response.readEntity(TagsModel.class);
		assertEquals("counter should be correct", 2, _tm4.getCounter());

		_response = tagWC.replacePath("/").path(_tm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _tm5 = _response.readEntity(TagsModel.class);
		assertEquals("counter should be correct", 2, _tm5.getCounter());

		// delete(_tm1) -> NO_CONTENT (counter decremented)
		_response = tagWC.replacePath("/").path(_tm1.getId()).delete();
		assertEquals("delete(" + _tm1.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		_response = tagWC.replacePath("/").path(_tm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _tm6 = _response.readEntity(TagsModel.class);
		assertEquals("counter should be correct", 1, _tm6.getCounter());

		// delete(_tm1) -> NO_CONTENT (counter decremented)
		_response = tagWC.replacePath("/").path(_tm1.getId()).delete();
		assertEquals("delete(" + _tm1.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		_response = tagWC.replacePath("/").path(_tm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testTagList(
	) {		
		ArrayList<TagsModel> _localList = new ArrayList<TagsModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = tagWC.replacePath("/").post(new TagsModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(TagsModel.class));
		}
		
		// list(/) -> _remoteList
		_response = tagWC.replacePath("/").get();
		List<TagsModel> _remoteList = new ArrayList<TagsModel>(tagWC.getCollection(TagsModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (TagsModel _rm : _remoteList) {
			_remoteListIds.add(_rm.getId());
		}
		
		for (TagsModel _rm : _localList) {
			assertTrue("tag <" + _rm.getId() + "> should be listed", _remoteListIds.contains(_rm.getId()));
		}
		for (TagsModel _rm : _localList) {
			_response = tagWC.replacePath("/").path(_rm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(TagsModel.class);
		}
		for (TagsModel _rm : _localList) {
			_response = tagWC.replacePath("/").path(_rm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}
		
	@Test
	public void testTagCreate() {
		// new() -> _tm1
		TagsModel _tm1 = new TagsModel();
		// new() -> _tm2
		TagsModel _tm2 = new TagsModel();
		
		// create(_tm1)  -> _tm3
		Response _response = tagWC.replacePath("/").post(_tm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _tm3 = _response.readEntity(TagsModel.class);

		// create(_tm2) -> _tm4
		_response = tagWC.replacePath("/").post(_tm2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _tm4 = _response.readEntity(TagsModel.class);		
		assertNotNull("ID should be set", _tm3.getId());
		assertNotNull("ID should be set", _tm4.getId());
		assertThat(_tm4.getId(), not(equalTo(_tm3.getId())));
		assertEquals("counter should be set correctly", 1, _tm3.getCounter());
		assertEquals("counter should be set correctly", 1, _tm4.getCounter());

		// delete(_tm3) -> NO_CONTENT
		_response = tagWC.replacePath("/").path(_tm3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_tm4) -> NO_CONTENT
		_response = tagWC.replacePath("/").path(_tm4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testTagMultiCreate(
	) {
		// create(_tm) -> _tm1
		TagsModel _tm = new TagsModel();
		Response _response = tagWC.replacePath("/").post(_tm);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _tm1 = _response.readEntity(TagsModel.class);
		assertNotNull("ID should be set:", _tm1.getId());
		assertEquals("counter should be correct", 1, _tm1.getCounter());
		
		// create(_tm) -> _tm2
		_response = tagWC.replacePath("/").post(_tm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _tm2 = _response.readEntity(TagsModel.class);
		assertEquals("ID should be the same", _tm1.getId(), _tm2.getId());
		assertEquals("counter should be correct", 2, _tm2.getCounter());

		// delete(_tm) -> NO_CONTENT
		_response = tagWC.replacePath("/").path(_tm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_tm) -> NO_CONTENT
		_response = tagWC.replacePath("/").path(_tm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testTagRead(
	) {
		ArrayList<TagsModel> _localList = new ArrayList<TagsModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			_response = tagWC.replacePath("/").post(new TagsModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(TagsModel.class));
		}
	
		// test read on each local element
		for (TagsModel _rm : _localList) {
			_response = tagWC.replacePath("/").path(_rm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(TagsModel.class);
		}

		// test read on each listed element
		_response = tagWC.replacePath("/").get();
		List<TagsModel> _remoteList = new ArrayList<TagsModel>(tagWC.getCollection(TagsModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		TagsModel _tmpObj = null;
		for (TagsModel _rm : _remoteList) {
			_response = tagWC.replacePath("/").path(_rm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(TagsModel.class);
			assertEquals("ID should be unchanged when reading a tag", _rm.getId(), _tmpObj.getId());						
		}

		for (TagsModel _rm : _localList) {
			_response = tagWC.replacePath("/").path(_rm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testTagMultiRead(
	) {
		// create() -> _rm1
		Response _response = tagWC.replacePath("/").post(new TagsModel());
		TagsModel _rm1 = _response.readEntity(TagsModel.class);

		// read(_rm1) -> _rm2
		_response = tagWC.replacePath("/").path(_rm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _rm2 = _response.readEntity(TagsModel.class);
		assertEquals("ID should be unchanged after read:", _rm1.getId(), _rm2.getId());		

		// read(_rm1) -> _rm4
		_response = tagWC.replacePath("/").path(_rm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _rm4 = _response.readEntity(TagsModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same:", _rm2.getId(), _rm4.getId());
		assertEquals("counter should be the same:", _rm2.getCounter(), _rm4.getCounter());
		
		assertEquals("ID should be the same:", _rm2.getId(), _rm1.getId());
		assertEquals("counter should be the same:", _rm2.getCounter(), _rm1.getCounter());
		
		// delete(_rm1)
		_response = tagWC.replacePath("/").path(_rm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testTagUpdate(
	) {
		// create() -> _rm1		
		Response _response = tagWC.replacePath("/").post(new TagsModel());
		TagsModel _rm1 = _response.readEntity(TagsModel.class);
		
		// change the attributes
		// update(_rm1) -> _rm2
		// TODO: update needs to be tested with LocalizedText, because there are no updatable fields in Tag
		tagWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = tagWC.replacePath("/").path(_rm1.getId()).put(_rm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _rm2 = _response.readEntity(TagsModel.class);

		assertNotNull("ID should be set", _rm2.getId());
		assertEquals("ID should be unchanged", _rm1.getId(), _rm2.getId());	

		// reset the attributes
		// update(_rm1) -> _rm3
		tagWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = tagWC.replacePath("/").path(_rm1.getId()).put(_rm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _rm3 = _response.readEntity(TagsModel.class);

		assertNotNull("ID should be set", _rm3.getId());
		assertEquals("ID should be unchanged", _rm1.getId(), _rm3.getId());	
		
		_response = tagWC.replacePath("/").path(_rm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testTagDelete(
	) {
		// create() -> _rm1
		Response _response = tagWC.replacePath("/").post(new TagsModel());
		TagsModel _rm1 = _response.readEntity(TagsModel.class);
		
		// read(_rm1) -> _tmpObj
		_response = tagWC.replacePath("/").path(_rm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _tmpObj = _response.readEntity(TagsModel.class);
		assertEquals("ID should be unchanged when reading a tag (remote):", _rm1.getId(), _tmpObj.getId());						

		// delete(_rm1) -> OK
		_response = tagWC.replacePath("/").path(_rm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_rm1) -> NOT_FOUND
		_response = tagWC.replacePath("/").path(_rm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_rm1) -> NOT_FOUND
		_response = tagWC.replacePath("/").path(_rm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testTagDoubleDelete(
	) {
		// new() -> _rm1
		TagsModel _rm1 = new TagsModel();
		
		// create(_rm1) -> _rm2
		Response _response = tagWC.replacePath("/").post(_rm1);
		TagsModel _rm2 = _response.readEntity(TagsModel.class);

		// read(_rm2) -> OK
		_response = tagWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_rm2) -> OK
		_response = tagWC.replacePath("/").path(_rm2.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_rm2) -> NOT_FOUND
		_response = tagWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _rm2 -> NOT_FOUND
		_response = tagWC.replacePath("/").path(_rm2.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _rm2 -> NOT_FOUND
		_response = tagWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testTagsModifications() {
		// create(new TagsModel()) -> _rm1
		Response _response = tagWC.replacePath("/").post(new TagsModel());
		TagsModel _rm1 = _response.readEntity(TagsModel.class);
		System.out.println("created TagsModel " + _rm1.getId());
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _rm1.getCreatedAt());
		assertNotNull("create() should set createdBy", _rm1.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _rm1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _rm1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _rm1.getCreatedAt(), _rm1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _rm1.getCreatedBy(), _rm1.getModifiedBy());
		try {
			System.out.println("sleeping for 3 secs"); // in order to get different modifiedAt Dates
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// update(_rm1)  -> _rm2
		tagWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = tagWC.replacePath("/").path(_rm1.getId()).put(_rm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _rm2 = _response.readEntity(TagsModel.class);
		System.out.println("updated TagsModel " + _rm2.getId());

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _rm1.getCreatedAt(), _rm2.getCreatedAt());
		assertEquals("update() should not change createdBy", _rm1.getCreatedBy(), _rm2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		System.out.println("comparing " + _rm2.getModifiedAt().toString() + " with " + _rm2.getCreatedAt().toString());
		assertThat(_rm2.getModifiedAt().toString(), not(equalTo(_rm2.getCreatedAt().toString())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_rm2.getModifiedBy(), not(equalTo(_rm2.getCreatedBy())));

		// update(_rm2) with createdBy set on client side -> ignored
		String _createdBy = _rm1.getCreatedBy();
		_rm1.setCreatedBy("testTagsModifications2");
		tagWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = tagWC.replacePath("/").path(_rm1.getId()).put(_rm1);
		assertEquals("update() should ignore client-side generated createdBy", 
				Status.OK.getStatusCode(), _response.getStatus());
		_rm1.setCreatedBy(_createdBy);

		// update(_rm1) with createdAt set on client side -> ignored
		Date _d = _rm1.getCreatedAt();
		_rm1.setCreatedAt(new Date(1000));
		tagWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = tagWC.replacePath("/").path(_rm1.getId()).put(_rm1);
		assertEquals("update() should ignore client-side generated createdAt", 
				Status.OK.getStatusCode(), _response.getStatus());
		_rm1.setCreatedAt(_d);

		// update(_rm1) with modifiedBy/At set on client side -> ignored by server
		_rm1.setModifiedBy("testTagsModifications3");
		_rm1.setModifiedAt(new Date(1000));
		tagWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = tagWC.replacePath("/").path(_rm1.getId()).put(_rm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		TagsModel _o3 = _response.readEntity(TagsModel.class);
		System.out.println("updated TagsModel " + _o3.getId());
	
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_rm1.getModifiedBy(), not(equalTo(_o3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_rm1.getModifiedAt(), not(equalTo(_o3.getModifiedAt())));
		
		// delete(_rm1) -> NO_CONTENT
		_response = tagWC.replacePath("/").path(_rm1.getId()).delete();		
		System.out.println("deleted TagsModel " + _rm1.getId());
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	/********************************* helper methods *********************************/	
	public static WebClient createTagsWebClient() {
		return createWebClient(createUrl(DEFAULT_BASE_URL, API_URL), TagsService.class);
	}
	
	public static TagsModel createTag(
			WebClient tagWC) 
	{
		TagsModel _rm = new TagsModel();
		Response _response = tagWC.replacePath("/").post(_rm);
		return _response.readEntity(TagsModel.class);
	}
	
	public static void cleanup(
			WebClient tagWC,
			String tagId,
			String testName) {
		cleanup(tagWC, tagId, testName, true);
	}
		
	public static void cleanup(
			WebClient tagWC,
			String tagId,
			String testName,
			boolean closeWC) {
		tagWC.replacePath("/").path(tagId).delete();
		System.out.println(testName + " deleted tag <" + tagId + ">.");
		if (closeWC) {
			tagWC.close();
		}
	}	
}
