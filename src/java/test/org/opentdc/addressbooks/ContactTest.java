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
package test.org.opentdc.addressbooks;

import static org.hamcrest.CoreMatchers.*;
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
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

public class ContactTest extends AbstractTestClient {
		private static AddressbookModel adb = null;
		private WebClient wc = null;
				
		@Before
		public void initializeTests() {
			wc = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
			adb = AddressbookTest.createAddressbook(wc, this.getClass().getName(), Status.OK);
		}
		
		@After
		public void cleanupTest() {
			AddressbookTest.delete(wc, adb.getId(), Status.NO_CONTENT);
			wc.close();
		}

		/********************************** contact attribute tests *********************************/	
		@Test
		public void testEmptyConstructor() {
			ContactModel _cm = new ContactModel();
			assertNull("id should not be set by empty constructor", _cm.getId());
			assertNull("birthday should not be set by empty constructor", _cm.getBirthday());
			assertNull("company should not be set by empty constructor", _cm.getCompany());
			assertNull("department should not be set by empty constructor", _cm.getDepartment());
			assertNull("firstname should not be set by empty constructor", _cm.getFirstName());
			assertNull("fn should not be set by empty constructor", _cm.getFn());
			assertNull("jobtitle should not be set by empty constructor", _cm.getJobTitle());
			assertNull("lastname should not be set by empty constructor", _cm.getLastName());
			assertNull("maidenname should not be set by empty constructor", _cm.getMaidenName());
			assertNull("middlename should not be set by empty constructor", _cm.getMiddleName());
			assertNull("nickname should not be set by empty constructor", _cm.getNickName());
			assertNull("note should not be set by empty constructor", _cm.getNote());
			assertNull("photourl should not be set by empty constructor", _cm.getPhotoUrl());
			assertNull("prefix should not be set by empty constructor", _cm.getPrefix());
			assertNull("suffix should not be set by empty constructor", _cm.getSuffix());
		}
				
		@Test
		public void testId() {
			ContactModel _model = new ContactModel();
			assertNull("id should not be set by constructor", _model.getId());
			_model.setId("testId");
			assertEquals("id should have changed:", "testId", _model.getId());
		}

		@Test
		public void testBirthday() {
			ContactModel _model = new ContactModel();
			assertNull("birthday should not be set by empty constructor", _model.getBirthday());
			Date _bdate = new Date();
			_model.setBirthday(_bdate);
			assertEquals("birthday should have changed:", _bdate, _model.getBirthday());
		}
		
		@Test
		public void testCompany() {
			ContactModel _model = new ContactModel();
			assertNull("company should not be set by empty constructor", _model.getCompany());
			_model.setCompany("testCompany");
			assertEquals("company should have changed:", "testCompany", _model.getCompany());
		}
		
		@Test
		public void testDepartment() {
			ContactModel _model = new ContactModel();
			assertNull("department should not be set by empty constructor", _model.getDepartment());
			_model.setDepartment("testDepartment");
			assertEquals("department should have changed:", "testDepartment", _model.getDepartment());
		}
		
		@Test
		public void testFirstName() {
			ContactModel _model = new ContactModel();
			assertNull("firstName should not be set by empty constructor", _model.getFirstName());
			_model.setFirstName("testFirstName");
			assertEquals("firstName should have changed:", "testFirstName", _model.getFirstName());
		}
		
		@Test
		public void testJobTitle() {
			ContactModel _model = new ContactModel();
			assertNull("jobTitle should not be set by empty constructor", _model.getJobTitle());
			_model.setJobTitle("testJobTitle");
			assertEquals("jobTitle should have changed:", "testJobTitle", _model.getJobTitle());
		}
		
		@Test
		public void testLastName() {
			ContactModel _model = new ContactModel();
			assertNull("LastName should not be set by empty constructor", _model.getLastName());
			_model.setLastName("testLastName");
			assertEquals("LastName should have changed:", "testLastName", _model.getLastName());
		}

		@Test
		public void testMaidenName() {
			ContactModel _model = new ContactModel();
			assertNull("MaidenName should not be set by empty constructor", _model.getMaidenName());
			_model.setMaidenName("testMaidenName");
			assertEquals("MaidenName should have changed:", "testMaidenName", _model.getMaidenName());
		}

		@Test
		public void testMiddleName() {
			ContactModel _model = new ContactModel();
			assertNull("MiddleName should not be set by empty constructor", _model.getMiddleName());
			_model.setMiddleName("testMiddleName");
			assertEquals("MiddleName should have changed:", "testMiddleName", _model.getMiddleName());
		}

		@Test
		public void testNickName() {
			ContactModel _model = new ContactModel();
			assertNull("NickName should not be set by empty constructor", _model.getNickName());
			_model.setNickName("testNickName");
			assertEquals("NickName should have changed:", "testNickName", _model.getNickName());
		}

		@Test
		public void testNote() {
			ContactModel _model = new ContactModel();
			assertNull("Note should not be set by empty constructor", _model.getNote());
			_model.setNote("testNote");
			assertEquals("Note should have changed:", "testNote", _model.getNote());
		}

		@Test
		public void testPhotoUrl() {
			ContactModel _model = new ContactModel();
			assertNull("PhotoUrl should not be set by empty constructor", _model.getPhotoUrl());
			_model.setPhotoUrl("testPhotoUrl");
			assertEquals("PhotoUrl should have changed:", "testPhotoUrl", _model.getPhotoUrl());
		}

		@Test
		public void testPrefix() {
			ContactModel _model = new ContactModel();
			assertNull("Prefix should not be set by empty constructor", _model.getPrefix());
			_model.setPrefix("testPrefix");
			assertEquals("Prefix should have changed:", "testPrefix", _model.getPrefix());
		}

		@Test
		public void testSuffix() {
			ContactModel _model = new ContactModel();
			assertNull("Suffix should not be set by empty constructor", _model.getSuffix());
			_model.setSuffix("testSuffix");
			assertEquals("Suffix should have changed:", "testSuffix", _model.getSuffix());
		}

		@Test
		public void testCreatedBy() {
			ContactModel _model = new ContactModel();
			assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
			_model.setCreatedBy("testCreatedBy");
			assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
		}
		
		@Test
		public void testCreatedAt() {
			ContactModel _model = new ContactModel();
			assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
			_model.setCreatedAt(new Date());
			assertNotNull("createdAt should have changed", _model.getCreatedAt());
		}
			
		@Test
		public void testModifiedBy() {
			ContactModel _model = new ContactModel();
			assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
			_model.setModifiedBy("testModifiedBy");
			assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
		}
		
		@Test
		public void testModifiedAt() {
			ContactModel _model = new ContactModel();
			assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
			_model.setModifiedAt(new Date());
			assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
		}

		/********************************* REST service tests *********************************/	
				
		@Test
		public void testCreateReadDeleteWithEmptyConstructor() {
			ContactModel _model1 = new ContactModel();
			assertNull("id should not be set by empty constructor", _model1.getId());
			assertNull("birthday should not be set by empty constructor", _model1.getBirthday());
			assertNull("company should not be set by empty constructor", _model1.getCompany());
			assertNull("department should not be set by empty constructor", _model1.getDepartment());
			assertNull("firstname should not be set by empty constructor", _model1.getFirstName());
			assertNull("fn should not be set by empty constructor", _model1.getFn());
			assertNull("jobtitle should not be set by empty constructor", _model1.getJobTitle());
			assertNull("lastname should not be set by empty constructor", _model1.getLastName());
			assertNull("maidenname should not be set by empty constructor", _model1.getMaidenName());
			assertNull("middlename should not be set by empty constructor", _model1.getMiddleName());
			assertNull("nickname should not be set by empty constructor", _model1.getNickName());
			assertNull("note should not be set by empty constructor", _model1.getNote());
			assertNull("photourl should not be set by empty constructor", _model1.getPhotoUrl());
			assertNull("prefix should not be set by empty constructor", _model1.getPrefix());
			assertNull("suffix should not be set by empty constructor", _model1.getSuffix());
			
			Response _response = wc.replacePath("/").post(_model1);
			assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());

			_model1.setFirstName("testCreateReadDeleteWithEmptyConstructor");
			_model1.setLastName("Test");
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model2 = _response.readEntity(ContactModel.class);
			
			assertNull("create() should not change the id of the local object", _model1.getId());
			assertNull("create() should not change the birthday of the local object", _model1.getBirthday());
			assertNull("create() should not change the company of the local object", _model1.getCompany());
			assertNull("create() should not change the department of the local object", _model1.getDepartment());
			assertNotNull("create() should not change the firstName of the local object", _model1.getFirstName());
			assertNotNull("create() should not change the lastName of the local object", _model1.getLastName());
			assertNull("create() should not change the fn of the local object", _model1.getFn());
			assertNull("create() should not change the jobTitle of the local object", _model1.getJobTitle());
			assertNull("create() should not change the maidenName of the local object", _model1.getMaidenName());
			assertNull("create() should not change the middleName of the local object", _model1.getMiddleName());
			assertNull("create() should not change the nickName of the local object", _model1.getNickName());
			assertNull("create() should not change the note of the local object", _model1.getNote());
			assertNull("create() should not change the photoUrl of the local object", _model1.getPhotoUrl());
			assertNull("create() should not change the prefix of the local object", _model1.getPrefix());
			assertNull("create() should not change the suffix of the local object", _model1.getSuffix());
			
			assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
			assertNull("birthday of returned object should be null after remote create", _model2.getBirthday());   // what is the correct value of a date?
			assertNull("company of returned object should still be null after remote create", _model2.getCompany());
			assertNull("department of returned object should still be null after remote create", _model2.getDepartment());
			assertNotNull("firstName of returned object should still be null after remote create", _model2.getFirstName());
			assertNotNull("lastName of returned object should still be null after remote create", _model2.getLastName());
			assertNotNull("fn of returned object should be unchanged", _model2.getFn());
			assertNull("jobTitle of returned object should still be null after remote create", _model2.getJobTitle());
			assertNull("maidenName of returned object should still be null after remote create", _model2.getMaidenName());
			assertNull("middleName of returned object should still be null after remote create", _model2.getMiddleName());
			assertNull("nickName of returned object should still be null after remote create", _model2.getNickName());
			assertNull("note of returned object should still be null after remote create", _model2.getNote());
			assertNull("photoUrl of returned object should still be null after remote create", _model2.getPhotoUrl());
			assertNull("prefix of returned object should still be null after remote create", _model2.getPrefix());
			assertNull("suffix of returned object should still be null after remote create", _model2.getSuffix());

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model2.getId()).get();
			assertEquals("read(" + _model2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model3 = _response.readEntity(ContactModel.class);
			assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
			assertEquals("birthday of returned object should be unchanged after remote create", _model2.getBirthday(), _model3.getBirthday());
			assertEquals("company of returned object should be unchanged after remote create", _model2.getCompany(), _model3.getCompany());
			assertEquals("department of returned object should be unchanged after remote create", _model2.getDepartment(), _model3.getDepartment());
			assertEquals("firstName of returned object should be unchanged after remote create", _model2.getFirstName(), _model3.getFirstName());
			assertEquals("fn of returned object should be unchanged after remote create", _model2.getFn(), _model3.getFn());
			assertEquals("jobTitle of returned object should be unchanged after remote create", _model2.getJobTitle(), _model3.getJobTitle());
			assertEquals("lastName of returned object should be unchanged after remote create", _model2.getLastName(), _model3.getLastName());
			assertEquals("maidenName of returned object should be unchanged after remote create", _model2.getMaidenName(), _model3.getMaidenName());
			assertEquals("middleName of returned object should be unchanged after remote create", _model2.getMiddleName(), _model3.getMiddleName());
			assertEquals("nickName of returned object should be unchanged after remote create", _model2.getNickName(), _model3.getNickName());
			assertEquals("note of returned object should be unchanged after remote create", _model2.getNote(), _model3.getNote());
			assertEquals("photoUrl of returned object should be unchanged after remote create", _model2.getPhotoUrl(), _model3.getPhotoUrl());
			assertEquals("prefix of returned object should be unchanged after remote create", _model2.getPrefix(), _model3.getPrefix());
			assertEquals("suffix of returned object should be unchanged after remote create", _model2.getSuffix(), _model3.getSuffix());

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model3.getId()).delete();
			assertEquals("delete(" + _model3.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testCreateReadDelete() {
			Date _bdate = new Date();
			ContactModel _model1 = setDefaultValues(new ContactModel(), _bdate, "");
			assertNull("id should not be set by constructor", _model1.getId());
			assertEquals("birthday should be set correctly", _bdate, _model1.getBirthday());
			assertEquals("company should be set correctly", "MY_COMPANY", _model1.getCompany());
			assertEquals("department should be set correctly", "MY_DEPT", _model1.getDepartment());
			assertEquals("firstName should be set correctly", "MY_FNAME", _model1.getFirstName());
			assertEquals("jobTitle should be set correctly", "MY_JOBTITLE", _model1.getJobTitle());
			assertEquals("lastName should be set correctly", "MY_LNAME", _model1.getLastName());
			assertEquals("maidenName should be set correctly", "MY_MNAME", _model1.getMaidenName());
			assertEquals("middleName should be set correctly", "MY_MNAME", _model1.getMiddleName());
			assertEquals("nickName should be set correctly", "MY_NNAME", _model1.getNickName());
			assertEquals("note should be set correctly", "MY_NOTE", _model1.getNote());
			assertEquals("photoUrl should be set correctly", "MY_PHOTO_URL", _model1.getPhotoUrl());
			assertEquals("prefix should be set correctly", "MY_PREFIX", _model1.getPrefix());
			assertEquals("suffix should be set correctly", "MY_SUFFIX", _model1.getSuffix());
			
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model2 = _response.readEntity(ContactModel.class);
			
			assertNull("id should still be null", _model1.getId());
			assertEquals("birthday should be unchanged", _bdate, _model1.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY", _model1.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT", _model1.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME", _model1.getFirstName());
			assertNull("fn should be empty", _model1.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE", _model1.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME", _model1.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME", _model1.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME", _model1.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME", _model1.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE", _model1.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL", _model1.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX", _model1.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX", _model1.getSuffix());
			
			assertNotNull("id of returned object should be set", _model2.getId());
			assertEquals("birthday should be unchanged", _bdate, _model2.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY", _model2.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT", _model2.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME", _model2.getFirstName());
			assertNotNull("fn should be set", _model2.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE", _model2.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME", _model2.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME", _model2.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME", _model2.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME", _model2.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE", _model2.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL", _model2.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX", _model2.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX", _model2.getSuffix());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model2.getId()).get();
			assertEquals("read(" + _model2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model3 = _response.readEntity(ContactModel.class);
			assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
			assertEquals("birthday should be the same", _model2.getBirthday(), _model3.getBirthday());
			assertEquals("company should be the same", _model2.getCompany(), _model3.getCompany());
			assertEquals("department should be the same", _model2.getDepartment(), _model3.getDepartment());
			assertEquals("firstName should be the same", _model2.getFirstName(), _model3.getFirstName());
			assertEquals("fn should be the same", _model2.getFn(), _model3.getFn());
			assertEquals("jobTitle should be the same", _model2.getJobTitle(), _model3.getJobTitle());
			assertEquals("lastName should be the same", _model2.getLastName(), _model3.getLastName());
			assertEquals("maidenName should be the same", _model2.getMaidenName(), _model3.getMaidenName());
			assertEquals("middleName should be the same", _model2.getMiddleName(), _model3.getMiddleName());
			assertEquals("nickName should be the same", _model2.getNickName(), _model3.getNickName());
			assertEquals("note should be the same", _model2.getNote(), _model3.getNote());
			assertEquals("photoUrl should be the same", _model2.getPhotoUrl(), _model3.getPhotoUrl());
			assertEquals("prefix should be the same", _model2.getPrefix(), _model3.getPrefix());
			assertEquals("suffix should be the same", _model2.getSuffix(), _model3.getSuffix());

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model3.getId()).delete();
			assertEquals("delete(" + _model3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testClientSideId() {
			ContactModel _model1 = new ContactModel();
			_model1.setFirstName("testClientSideId");
			_model1.setLastName("Test");
			_model1.setId("LOCAL_ID");
			assertEquals("id should have changed", "LOCAL_ID", _model1.getId());
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model1);
			assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testDuplicateId() {
			ContactModel _model = new ContactModel();
			_model.setFirstName("testDuplicateId");
			_model.setLastName("Test");
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model1 = _response.readEntity(ContactModel.class);

			ContactModel _model2 = new ContactModel();
			_model2.setFirstName("testDuplicateId");
			_model2.setLastName("Test");
			_model2.setId(_model1.getId());		// wrongly create a 2nd ContactModel object with the same ID
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model2);
			assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testList() {
			ArrayList<ContactModel> _localList = new ArrayList<ContactModel>();		
			Response _response = null;
			wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL);
			ContactModel _model = null;
			for (int i = 0; i < LIMIT; i++) {
				_model = new ContactModel();
				_model.setFirstName("testContactList" + i);
				_model.setLastName("Test");
				_response = wc.post(_model);
				assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_localList.add(_response.readEntity(ContactModel.class));
			}
			assertEquals("size of lists should be the same", LIMIT, _localList.size());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).get();
			List<ContactModel> _remoteList = new ArrayList<ContactModel>(wc.getCollection(ContactModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			assertEquals("size of lists should be the same", LIMIT, _remoteList.size());
			// implicit proof: _localList.size() = _remoteList.size = LIMIT

			ArrayList<String> _remoteListIds = new ArrayList<String>();
			for (ContactModel _model1 : _remoteList) {
				_remoteListIds.add(_model1.getId());
			}
			
			for (ContactModel _model1 : _localList) {
				assertTrue("project <" + _model1.getId() + "> should be listed", _remoteListIds.contains(_model1.getId()));
			}
			
			for (ContactModel _model1 : _localList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_response.readEntity(ContactModel.class);
			}
			
			for (ContactModel _model1 : _localList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).delete();
				assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			}
		}

		@Test
		public void testCreate() {	
			Date _bdate1 = new Date(1000);
			ContactModel _model1 = setDefaultValues(new ContactModel(), _bdate1, "1");
			_model1.setFirstName("MY_FNAME1");
			_model1.setLastName("MY_LNAME1");
			// new(custom attributes2) -> _cm2
			Date _bdate2 = new Date(2000);
			ContactModel _model2 = setDefaultValues(new ContactModel(), _bdate2, "2");
			_model2.setFirstName("MY_FNAME2");
			_model2.setLastName("MY_LNAME2");
			
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model3 = _response.readEntity(ContactModel.class);

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model2);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model4 = _response.readEntity(ContactModel.class);		
			assertNotNull("ID should be set", _model3.getId());
			assertNotNull("ID should be set", _model4.getId());
			assertThat(_model4.getId(), not(equalTo(_model3.getId())));
			
			assertEquals("birthday should be unchanged", _bdate1, _model3.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY1", _model3.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT1", _model3.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME1", _model3.getFirstName());
			assertNotNull("fn should be set", _model3.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE1", _model3.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME1", _model3.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME1", _model3.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME1", _model3.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME1", _model3.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE1", _model3.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL1", _model3.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX1", _model3.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX1", _model3.getSuffix());
			
			assertEquals("birthday should be unchanged", _bdate2, _model4.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY2", _model4.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT2", _model4.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME2", _model4.getFirstName());
			assertNotNull("fn should be set", _model4.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE2", _model4.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME2", _model4.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME2", _model4.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME2", _model4.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME2", _model4.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE2", _model4.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL2", _model4.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX2", _model4.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX2", _model4.getSuffix());

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model3.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model4.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testCreateDouble() {		
			ContactModel _model1 = new ContactModel();
			_model1.setFirstName("testCreateDouble");
			_model1.setLastName("Test");
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model2 = _response.readEntity(ContactModel.class);
			assertNotNull("ID should be set:", _model2.getId());		
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model2);
			assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model2.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testRead() {
			ArrayList<ContactModel> _localList = new ArrayList<ContactModel>();
			Response _response = null;
			wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL);
			ContactModel _model = null;
			for (int i = 0; i < LIMIT; i++) {
				_model = new ContactModel();
				_model.setFirstName("testRead" + i);
				_model.setLastName("Test");
				_response = wc.post(_model);
				assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_localList.add(_response.readEntity(ContactModel.class));
			}
		
			for (ContactModel _model1 : _localList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_response.readEntity(ContactModel.class);
			}

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).get();
			List<ContactModel> _remoteList = new ArrayList<ContactModel>(wc.getCollection(ContactModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

			ContactModel _model2 = null;
			for (ContactModel _model3 : _remoteList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model3.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_model2 = _response.readEntity(ContactModel.class);
				assertEquals("ID should be unchanged when reading a project", _model3.getId(), _model2.getId());						
			}

			for (ContactModel _model4 : _localList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model4.getId()).delete();
				assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			}
		}
			
		@Test
		public void testMultiRead() {
			Date _bdate1 = new Date(1000);
			ContactModel _model1 = setDefaultValues(new ContactModel(), _bdate1, "1");
			_model1.setFirstName("testMultiRead");
			_model1.setLastName("Test");
			
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model1);
			ContactModel _model2 = _response.readEntity(ContactModel.class);

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model2.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model3 = _response.readEntity(ContactModel.class);
			assertEquals("ID should be unchanged after read:", _model2.getId(), _model3.getId());		

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model2.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model4 = _response.readEntity(ContactModel.class);
			
			assertEquals("ID should be the same:", _model3.getId(), _model4.getId());
			assertEquals("birthday should be the same", _model3.getBirthday(), _model4.getBirthday());
			assertEquals("company should be the same", _model3.getCompany(), _model4.getCompany());
			assertEquals("department should be the same", _model3.getDepartment(), _model4.getDepartment());
			assertEquals("firstName should be the same", _model3.getFirstName(), _model4.getFirstName());
			assertEquals("fn should be the same", _model3.getFn(), _model4.getFn());
			assertEquals("jobTitle should be the same", _model3.getJobTitle(), _model4.getJobTitle());
			assertEquals("lastName should be the same", _model3.getLastName(), _model4.getLastName());
			assertEquals("maidenName should be the same", _model3.getMaidenName(), _model4.getMaidenName());
			assertEquals("middleName should be the same", _model3.getMiddleName(), _model4.getMiddleName());
			assertEquals("nickName should be the same", _model3.getNickName(), _model4.getNickName());
			assertEquals("note should be the same", _model3.getNote(), _model4.getNote());
			assertEquals("photoUrl should be the same", _model3.getPhotoUrl(), _model4.getPhotoUrl());
			assertEquals("prefix should be the same", _model3.getPrefix(), _model4.getPrefix());
			assertEquals("suffix should be the same", _model3.getSuffix(), _model4.getSuffix());
			
			
			assertEquals("ID should be the same:", _model3.getId(), _model2.getId());
			assertEquals("birthday should be the same", _model3.getBirthday(), _model2.getBirthday());
			assertEquals("company should be the same", _model3.getCompany(), _model2.getCompany());
			assertEquals("department should be the same", _model3.getDepartment(), _model2.getDepartment());
			assertEquals("firstName should be the same", _model3.getFirstName(), _model2.getFirstName());
			assertEquals("fn should be the same", _model3.getFn(), _model2.getFn());
			assertEquals("jobTitle should be the same", _model3.getJobTitle(), _model2.getJobTitle());
			assertEquals("lastName should be the same", _model3.getLastName(), _model2.getLastName());
			assertEquals("maidenName should be the same", _model3.getMaidenName(), _model2.getMaidenName());
			assertEquals("middleName should be the same", _model3.getMiddleName(), _model2.getMiddleName());
			assertEquals("nickName should be the same", _model3.getNickName(), _model2.getNickName());
			assertEquals("note should be the same", _model3.getNote(), _model2.getNote());
			assertEquals("photoUrl should be the same", _model3.getPhotoUrl(), _model2.getPhotoUrl());
			assertEquals("prefix should be the same", _model3.getPrefix(), _model2.getPrefix());
			assertEquals("suffix should be the same", _model3.getSuffix(), _model2.getSuffix());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model2.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testUpdate() {			
			ContactModel _model = new ContactModel();
			_model.setFirstName("testUpdate");
			_model.setLastName("Test");
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model);
			ContactModel _model1 = _response.readEntity(ContactModel.class);
			
			Date _bdate3 = new Date(3000);
			wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).put(setDefaultValues(_model1, _bdate3, "3"));
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model2 = _response.readEntity(ContactModel.class);

			assertNotNull("ID should be set", _model2.getId());
			assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
			assertEquals("birthday should be set correctly", _bdate3, _model2.getBirthday());
			assertEquals("company should be set correctly", "MY_COMPANY3", _model2.getCompany());
			assertEquals("department should be set correctly", "MY_DEPT3", _model2.getDepartment());
			assertEquals("firstName should be set correctly", "MY_FNAME3", _model2.getFirstName());
			assertNotNull("fn should be set", _model2.getFn());
			assertEquals("jobTitle should be set correctly", "MY_JOBTITLE3", _model2.getJobTitle());
			assertEquals("lastName should be set correctly", "MY_LNAME3", _model2.getLastName());
			assertEquals("maidenName should be set correctly", "MY_MNAME3", _model2.getMaidenName());
			assertEquals("middleName should be set correctly", "MY_MNAME3", _model2.getMiddleName());
			assertEquals("nickName should be set correctly", "MY_NNAME3", _model2.getNickName());
			assertEquals("note should be set correctly", "MY_NOTE3", _model2.getNote());
			assertEquals("photoUrl should be set correctly", "MY_PHOTO_URL3", _model2.getPhotoUrl());
			assertEquals("prefix should be set correctly", "MY_PREFIX3", _model2.getPrefix());
			assertEquals("suffix should be set correctly", "MY_SUFFIX3", _model2.getSuffix());

			Date _bdate4 = new Date(4000);
			wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).put(setDefaultValues(_model1, _bdate4, "4"));
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model3 = _response.readEntity(ContactModel.class);

			assertNotNull("ID should be set", _model3.getId());
			assertEquals("ID should be unchanged", _model1.getId(), _model3.getId());
			assertEquals("birthday should be set correctly", _bdate4, _model3.getBirthday());
			assertEquals("company should be set correctly", "MY_COMPANY4", _model3.getCompany());
			assertEquals("department should be set correctly", "MY_DEPT4", _model3.getDepartment());
			assertEquals("firstName should be set correctly", "MY_FNAME4", _model3.getFirstName());
			assertNotNull("fn should be set", _model3.getFn());
			assertEquals("jobTitle should be set correctly", "MY_JOBTITLE4", _model3.getJobTitle());
			assertEquals("lastName should be set correctly", "MY_LNAME4", _model3.getLastName());
			assertEquals("maidenName should be set correctly", "MY_MNAME4", _model3.getMaidenName());
			assertEquals("middleName should be set correctly", "MY_MNAME4", _model3.getMiddleName());
			assertEquals("nickName should be set correctly", "MY_NNAME4", _model3.getNickName());
			assertEquals("note should be set correctly", "MY_NOTE4", _model3.getNote());
			assertEquals("photoUrl should be set correctly", "MY_PHOTO_URL4", _model3.getPhotoUrl());
			assertEquals("prefix should be set correctly", "MY_PREFIX4", _model3.getPrefix());
			assertEquals("suffix should be set correctly", "MY_SUFFIX4", _model3.getSuffix());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}

		@Test
		public void testDelete(
		) {
			ContactModel _model = new ContactModel();
			_model.setFirstName("testDelete");
			_model.setLastName("Test");
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model);
			ContactModel _model1 = _response.readEntity(ContactModel.class);
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model2 = _response.readEntity(ContactModel.class);
			assertEquals("ID should be unchanged when reading a project (remote):", _model1.getId(), _model2.getId());						

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_model2 = _response.readEntity(ContactModel.class);
			assertEquals("ID should be unchanged when reading a project (remote):", _model1.getId(), _model2.getId());						
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testDoubleDelete() {
			ContactModel _model = new ContactModel();
			_model.setFirstName("testDoubleDelete");
			_model.setLastName("Test");
			
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model);
			ContactModel _model1 = _response.readEntity(ContactModel.class);

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).delete();		
			assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testModifications() {
			ContactModel _model = new ContactModel();
			_model.setFirstName("testModifications");
			_model.setLastName("Test");

			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model);
			ContactModel _model1 = _response.readEntity(ContactModel.class);
			
			assertNotNull("create() should set createdAt", _model1.getCreatedAt());
			assertNotNull("create() should set createdBy", _model1.getCreatedBy());
			assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
			assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
			assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
			assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
			
			_model1.setFirstName("MY_NAME2");
			_model1.setLastName("Test");
			wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).put(_model1);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model2 = _response.readEntity(ContactModel.class);

			assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
			assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
			assertThat(_model2.getModifiedAt(), not(equalTo(_model2.getCreatedAt())));
			// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?

			_model1.setModifiedBy("MYSELF");
			_model1.setModifiedAt(new Date(1000));
			wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).put(_model1);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model3 = _response.readEntity(ContactModel.class);
			
			assertThat(_model1.getModifiedBy(), not(equalTo(_model3.getModifiedBy())));
			assertThat(_model1.getModifiedAt(), not(equalTo(_model3.getModifiedAt())));
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testFn() {
			// test 1:   create
			// _model1: fn=null, firstName="FirstName", lastName=null -> fn="FirstName"
			Response _response = create("FirstName", null);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model1 = _response.readEntity(ContactModel.class);
			assertNotNull("create() should set a valid fn", _model1.getFn());
			assertEquals("create() should not change the firstName", "FirstName", _model1.getFirstName());
			assertNull("create() should not change the lastName", _model1.getLastName());

			// _model2: fn=null, firstName=null, lastName="LastName" -> fn="LastName"
			_response = create(null, "LastName");
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model2 = _response.readEntity(ContactModel.class);
			assertNotNull("create() should set a valid fn", _model2.getFn());
			assertNull("create() should not change the firstName", _model2.getFirstName());
			assertEquals("create() should not change the lastName", "LastName", _model2.getLastName());

			 // _model3: fn=null, firstName="FirstName", lastName="LastName" -> fn="FirstName LastName"
			_response = create("FirstName", "LastName");
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model3 = _response.readEntity(ContactModel.class);
			assertNotNull("create() should set a valid fn", _model3.getFn());
			assertEquals("create() should not change the firstName", "FirstName", _model3.getFirstName());
			assertEquals("create() should not change the lastName", "LastName", _model3.getLastName());
			
			// fn=null, firstName=null, lastName=null -> BAD_REQUEST 
			_response = create(null, null);
			assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
						
			// _model4: fn="FN", firstName="FirstName", lastName="LastName" -> fn="FN"
			_response = create("FirstName", "LastName");
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model4 = _response.readEntity(ContactModel.class);
			assertNotNull("create() should not change a valid fn", _model4.getFn());
			assertEquals("create() should not change the firstName", "FirstName", _model4.getFirstName());
			assertEquals("create() should not change the lastName", "LastName", _model4.getLastName());
			
			// test 3:  update on firstName -> should not change fn and lastName
			_model4.setFirstName("FirstName2");
			wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model4.getId()).put(_model4);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model5 = _response.readEntity(ContactModel.class);
			assertNotNull("create() should not change the fn", _model5.getFn());
			assertEquals("create() should  change the firstName", "FirstName2", _model5.getFirstName());
			assertEquals("create() should not change the lastName", "LastName", _model5.getLastName());
			
			// test 4:  update on lastName -> should not change fn and firstName
			_model5.setLastName("LastName2");
			wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model5.getId()).put(_model5);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _model6 = _response.readEntity(ContactModel.class);
			assertNotNull("create() should not change the fn", _model6.getFn());
			assertEquals("create() should not change the firstName", "FirstName2", _model6.getFirstName());
			assertEquals("create() should change the lastName", "LastName2", _model6.getLastName());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model1.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model2.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).path(_model3.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}

		/********************************** helper methods *********************************/			
		private ContactModel setDefaultValues(ContactModel model, Date bdate, String suffix) {
			model.setBirthday(bdate);
			model.setCompany("MY_COMPANY" + suffix);
			model.setDepartment("MY_DEPT" + suffix);
			model.setFirstName("MY_FNAME" + suffix);
			model.setJobTitle("MY_JOBTITLE" + suffix);
			model.setLastName("MY_LNAME" + suffix);
			model.setMaidenName("MY_MNAME" + suffix);
			model.setMiddleName("MY_MNAME" + suffix);
			model.setNickName("MY_NNAME" + suffix);
			model.setNote("MY_NOTE" + suffix);
			model.setPhotoUrl("MY_PHOTO_URL" + suffix);
			model.setPrefix("MY_PREFIX" + suffix);
			model.setSuffix("MY_SUFFIX" + suffix);
			return model;
		}
		
		private Response create(String fName, String lName) {
			ContactModel _model = new ContactModel();
			_model.setFirstName(fName);
			_model.setLastName(lName);
			return wc.replacePath("/").path(adb.getId()).path(ServiceUtil.CONTACT_PATH_EL).post(_model);
		}
		
		public static ContactModel create(WebClient abWC, String aid, String fName, String lName) {
			ContactModel _model = new ContactModel();
			_model.setFirstName(fName);
			_model.setLastName(lName);
			Response _response = abWC.replacePath("/").path(aid).path(ServiceUtil.CONTACT_PATH_EL).post(_model);
			return _response.readEntity(ContactModel.class);
		}
		
		protected int calculateMembers() {
			return 1;
		}
}
