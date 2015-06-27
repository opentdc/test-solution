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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.ContactModel;

import test.org.opentdc.AbstractTestClient;

public class ContactTest extends AbstractTestClient<AddressbooksService> {
		
		private static final String API = "api/addressbooks/";
		public static final String PATH_EL_CONTACT = "contact";
		private static AddressbookModel adb = null;

		@Before
		public void initializeTest(
		) {
			initializeTest(API, AddressbooksService.class);
			Response _response = webclient.replacePath("/").post(new AddressbookModel("ContactTest"));
			adb = _response.readEntity(AddressbookModel.class);
			System.out.println("ContactTest posted AddressbookModel " + adb.getId());
		}
		
		@After
		public void cleanupTest() {
			webclient.replacePath("/").path(adb.getId()).delete();
			System.out.println("ContactTest deleted AddressbookModel " + adb.getId());
		}
		
		/********************************** contact attribute tests *********************************/	
		@Test
		public void testContactModelEmptyConstructor() {
			// new() -> _cm
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
		public void testContactIdAttributeChange() {
			// new() -> _cm -> _cm.setId()
			ContactModel _cm = new ContactModel();
			assertNull("id should not be set by constructor", _cm.getId());
			_cm.setId("MY_ID");
			assertEquals("id should have changed:", "MY_ID", _cm.getId());
		}

		// Birthday
		@Test
		public void testContactBirthdayAttributeChange() {
			// new() -> _cm -> _cm.setBirthday()
			ContactModel _cm = new ContactModel();
			assertNull("birthday should not be set by empty constructor", _cm.getBirthday());
			Date _bdate = new Date();
			_cm.setBirthday(_bdate);
			assertEquals("birthday should have changed:", _bdate, _cm.getBirthday());
		}
		
		// Company
		@Test
		public void testContactCompanyAttributeChange() {
			// new() -> _cm -> _cm.setCompany()
			ContactModel _cm = new ContactModel();
			assertNull("company should not be set by empty constructor", _cm.getCompany());
			_cm.setCompany("MY_COMPANY");
			assertEquals("company should have changed:", "MY_COMPANY", _cm.getCompany());
		}
		
		// Department
		@Test
		public void testContactDepartmentAttributeChange() {
			// new() -> _cm -> _cm.setDepartment()
			ContactModel _cm = new ContactModel();
			assertNull("department should not be set by empty constructor", _cm.getDepartment());
			_cm.setDepartment("MY_DEPT");
			assertEquals("department should have changed:", "MY_DEPT", _cm.getDepartment());
		}
		
		// FirstName
		@Test
		public void testContactFirstNameAttributeChange() {
			// new() -> _cm -> _cm.setFirstName()
			ContactModel _cm = new ContactModel();
			assertNull("firstName should not be set by empty constructor", _cm.getFirstName());
			_cm.setFirstName("MY_FNAME");
			assertEquals("firstName should have changed:", "MY_FNAME", _cm.getFirstName());
		}
		
		// JobTitle
		@Test
		public void testContactJobTitleAttributeChange() {
			// new() -> _cm -> _cm.setJobTitle()
			ContactModel _cm = new ContactModel();
			assertNull("jobTitle should not be set by empty constructor", _cm.getJobTitle());
			_cm.setJobTitle("MY_JOBTITLE");
			assertEquals("jobTitle should have changed:", "MY_JOBTITLE", _cm.getJobTitle());
		}
		
		// LastName
		@Test
		public void testContactLastNameAttributeChange() {
			// new() -> _cm -> _cm.setLastName()
			ContactModel _cm = new ContactModel();
			assertNull("LastName should not be set by empty constructor", _cm.getLastName());
			_cm.setLastName("MY_LNAME");
			assertEquals("LastName should have changed:", "MY_LNAME", _cm.getLastName());
		}

		// MaidenName
		@Test
		public void testContactMaidenNameAttributeChange() {
			// new() -> _cm -> _cm.setMaidenName()
			ContactModel _cm = new ContactModel();
			assertNull("MaidenName should not be set by empty constructor", _cm.getMaidenName());
			_cm.setMaidenName("MY_MNAME");
			assertEquals("MaidenName should have changed:", "MY_MNAME", _cm.getMaidenName());
		}

		// MiddleName
		@Test
		public void testContactMiddleNameAttributeChange() {
			// new() -> _cm -> _cm.setMiddleName()
			ContactModel _cm = new ContactModel();
			assertNull("MiddleName should not be set by empty constructor", _cm.getMiddleName());
			_cm.setMiddleName("MY_MNAME");
			assertEquals("MiddleName should have changed:", "MY_MNAME", _cm.getMiddleName());
		}

		// NickName
		@Test
		public void testContactNickNameAttributeChange() {
			// new() -> _cm -> _cm.setNickName()
			ContactModel _cm = new ContactModel();
			assertNull("NickName should not be set by empty constructor", _cm.getNickName());
			_cm.setNickName("MY_NNAME");
			assertEquals("NickName should have changed:", "MY_NNAME", _cm.getNickName());
		}

		// Note
		@Test
		public void testContactNoteAttributeChange() {
			// new() -> _cm -> _cm.setNote()
			ContactModel _cm = new ContactModel();
			assertNull("Note should not be set by empty constructor", _cm.getNote());
			_cm.setNote("MY_NOTE");
			assertEquals("Note should have changed:", "MY_NOTE", _cm.getNote());
		}

		// PhotoUrl
		@Test
		public void testContactPhotoUrlAttributeChange() {
			// new() -> _cm -> _cm.setPhotoUrl()
			ContactModel _cm = new ContactModel();
			assertNull("PhotoUrl should not be set by empty constructor", _cm.getPhotoUrl());
			_cm.setPhotoUrl("MY_PHOTO_URL");
			assertEquals("PhotoUrl should have changed:", "MY_PHOTO_URL", _cm.getPhotoUrl());
		}

		// Prefix
		@Test
		public void testContactPrefixAttributeChange() {
			// new() -> _cm -> _cm.setPrefix()
			ContactModel _cm = new ContactModel();
			assertNull("Prefix should not be set by empty constructor", _cm.getPrefix());
			_cm.setPrefix("MY_PREFIX");
			assertEquals("Prefix should have changed:", "MY_PREFIX", _cm.getPrefix());
		}

		// Suffix
		@Test
		public void testContactSuffixAttributeChange() {
			// new() -> _cm -> _cm.setSuffix()
			ContactModel _cm = new ContactModel();
			assertNull("Suffix should not be set by empty constructor", _cm.getSuffix());
			_cm.setSuffix("MY_SUFFIX");
			assertEquals("Suffix should have changed:", "MY_SUFFIX", _cm.getSuffix());
		}

		@Test
		public void testContactCreatedBy() {
			// new() -> _cm -> _cm.setCreatedBy()
			ContactModel _cm = new ContactModel();
			assertNull("createdBy should not be set by empty constructor", _cm.getCreatedBy());
			_cm.setCreatedBy("MY_NAME");
			assertEquals("createdBy should have changed", "MY_NAME", _cm.getCreatedBy());	
		}
		
		@Test
		public void testContactCreatedAt() {
			// new() -> _cm -> _cm.setCreatedAt()
			ContactModel _cm = new ContactModel();
			assertNull("createdAt should not be set by empty constructor", _cm.getCreatedAt());
			_cm.setCreatedAt(new Date());
			assertNotNull("createdAt should have changed", _cm.getCreatedAt());
		}
			
		@Test
		public void testContactModifiedBy() {
			// new() -> _cm -> _cm.setModifiedBy()
			ContactModel _cm = new ContactModel();
			assertNull("modifiedBy should not be set by empty constructor", _cm.getModifiedBy());
			_cm.setModifiedBy("MY_NAME");
			assertEquals("modifiedBy should have changed", "MY_NAME", _cm.getModifiedBy());	
		}
		
		@Test
		public void testContactModifiedAt() {
			// new() -> _cm -> _cm.setModifiedAt()
			ContactModel _cm = new ContactModel();
			assertNull("modifiedAt should not be set by empty constructor", _cm.getModifiedAt());
			_cm.setModifiedAt(new Date());
			assertNotNull("modifiedAt should have changed", _cm.getModifiedAt());
		}

		/********************************* REST service tests *********************************/	
				
		@Test
		public void testContactCreateReadDeleteWithEmptyConstructor() {
			// new() -> _cm1
			ContactModel _cm1 = new ContactModel();
			assertNull("id should not be set by empty constructor", _cm1.getId());
			assertNull("birthday should not be set by empty constructor", _cm1.getBirthday());
			assertNull("company should not be set by empty constructor", _cm1.getCompany());
			assertNull("department should not be set by empty constructor", _cm1.getDepartment());
			assertNull("firstname should not be set by empty constructor", _cm1.getFirstName());
			assertNull("fn should not be set by empty constructor", _cm1.getFn());
			assertNull("jobtitle should not be set by empty constructor", _cm1.getJobTitle());
			assertNull("lastname should not be set by empty constructor", _cm1.getLastName());
			assertNull("maidenname should not be set by empty constructor", _cm1.getMaidenName());
			assertNull("middlename should not be set by empty constructor", _cm1.getMiddleName());
			assertNull("nickname should not be set by empty constructor", _cm1.getNickName());
			assertNull("note should not be set by empty constructor", _cm1.getNote());
			assertNull("photourl should not be set by empty constructor", _cm1.getPhotoUrl());
			assertNull("prefix should not be set by empty constructor", _cm1.getPrefix());
			assertNull("suffix should not be set by empty constructor", _cm1.getSuffix());
			
			// create(_cm1) -> BAD_REQUEST (because of empty firstName / lastName)
			Response _response = webclient.replacePath("/").post(_cm1);
			assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());

			// _cm1.setFirstName() -> create(_cm1) -> _cm2
			_cm1.setFirstName("testContactCreateReadDeleteWithEmptyConstructor");
			_cm1.setLastName("Test");
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm2 = _response.readEntity(ContactModel.class);
			
			// validate _cm1
			assertNull("create() should not change the id of the local object", _cm1.getId());
			assertNull("create() should not change the birthday of the local object", _cm1.getBirthday());
			assertNull("create() should not change the company of the local object", _cm1.getCompany());
			assertNull("create() should not change the department of the local object", _cm1.getDepartment());
			assertNotNull("create() should not change the firstName of the local object", _cm1.getFirstName());
			assertNotNull("create() should not change the lastName of the local object", _cm1.getLastName());
			assertNull("create() should not change the fn of the local object", _cm1.getFn());
			assertNull("create() should not change the jobTitle of the local object", _cm1.getJobTitle());
			assertNull("create() should not change the maidenName of the local object", _cm1.getMaidenName());
			assertNull("create() should not change the middleName of the local object", _cm1.getMiddleName());
			assertNull("create() should not change the nickName of the local object", _cm1.getNickName());
			assertNull("create() should not change the note of the local object", _cm1.getNote());
			assertNull("create() should not change the photoUrl of the local object", _cm1.getPhotoUrl());
			assertNull("create() should not change the prefix of the local object", _cm1.getPrefix());
			assertNull("create() should not change the suffix of the local object", _cm1.getSuffix());
			
			// validate _cm2
			assertNotNull("create() should set a valid id on the remote object returned", _cm2.getId());
			assertNull("birthday of returned object should be null after remote create", _cm2.getBirthday());   // what is the correct value of a date?
			assertNull("company of returned object should still be null after remote create", _cm2.getCompany());
			assertNull("department of returned object should still be null after remote create", _cm2.getDepartment());
			assertNotNull("firstName of returned object should still be null after remote create", _cm2.getFirstName());
			assertNotNull("lastName of returned object should still be null after remote create", _cm2.getLastName());
			assertNotNull("fn of returned object should be unchanged", _cm2.getFn());
			assertNull("jobTitle of returned object should still be null after remote create", _cm2.getJobTitle());
			assertNull("maidenName of returned object should still be null after remote create", _cm2.getMaidenName());
			assertNull("middleName of returned object should still be null after remote create", _cm2.getMiddleName());
			assertNull("nickName of returned object should still be null after remote create", _cm2.getNickName());
			assertNull("note of returned object should still be null after remote create", _cm2.getNote());
			assertNull("photoUrl of returned object should still be null after remote create", _cm2.getPhotoUrl());
			assertNull("prefix of returned object should still be null after remote create", _cm2.getPrefix());
			assertNull("suffix of returned object should still be null after remote create", _cm2.getSuffix());

			// read(_cm2) -> _cm3
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm2.getId()).get();
			assertEquals("read(" + _cm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm3 = _response.readEntity(ContactModel.class);
			assertEquals("id of returned object should be the same", _cm2.getId(), _cm3.getId());
			assertEquals("birthday of returned object should be unchanged after remote create", _cm2.getBirthday(), _cm3.getBirthday());
			assertEquals("company of returned object should be unchanged after remote create", _cm2.getCompany(), _cm3.getCompany());
			assertEquals("department of returned object should be unchanged after remote create", _cm2.getDepartment(), _cm3.getDepartment());
			assertEquals("firstName of returned object should be unchanged after remote create", _cm2.getFirstName(), _cm3.getFirstName());
			assertEquals("fn of returned object should be unchanged after remote create", _cm2.getFn(), _cm3.getFn());
			assertEquals("jobTitle of returned object should be unchanged after remote create", _cm2.getJobTitle(), _cm3.getJobTitle());
			assertEquals("lastName of returned object should be unchanged after remote create", _cm2.getLastName(), _cm3.getLastName());
			assertEquals("maidenName of returned object should be unchanged after remote create", _cm2.getMaidenName(), _cm3.getMaidenName());
			assertEquals("middleName of returned object should be unchanged after remote create", _cm2.getMiddleName(), _cm3.getMiddleName());
			assertEquals("nickName of returned object should be unchanged after remote create", _cm2.getNickName(), _cm3.getNickName());
			assertEquals("note of returned object should be unchanged after remote create", _cm2.getNote(), _cm3.getNote());
			assertEquals("photoUrl of returned object should be unchanged after remote create", _cm2.getPhotoUrl(), _cm3.getPhotoUrl());
			assertEquals("prefix of returned object should be unchanged after remote create", _cm2.getPrefix(), _cm3.getPrefix());
			assertEquals("suffix of returned object should be unchanged after remote create", _cm2.getSuffix(), _cm3.getSuffix());
			// delete(_p3)
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm3.getId()).delete();
			assertEquals("delete(" + _cm3.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactCreateReadDelete() {
			// new(with custom attributes) -> _cm1
			Date _bdate = new Date();
			ContactModel _cm1 = setDefaultValues(new ContactModel(), _bdate, "");
			assertNull("id should not be set by constructor", _cm1.getId());
			assertEquals("birthday should be set correctly", _bdate, _cm1.getBirthday());
			assertEquals("company should be set correctly", "MY_COMPANY", _cm1.getCompany());
			assertEquals("department should be set correctly", "MY_DEPT", _cm1.getDepartment());
			assertEquals("firstName should be set correctly", "MY_FNAME", _cm1.getFirstName());
			assertEquals("jobTitle should be set correctly", "MY_JOBTITLE", _cm1.getJobTitle());
			assertEquals("lastName should be set correctly", "MY_LNAME", _cm1.getLastName());
			assertEquals("maidenName should be set correctly", "MY_MNAME", _cm1.getMaidenName());
			assertEquals("middleName should be set correctly", "MY_MNAME", _cm1.getMiddleName());
			assertEquals("nickName should be set correctly", "MY_NNAME", _cm1.getNickName());
			assertEquals("note should be set correctly", "MY_NOTE", _cm1.getNote());
			assertEquals("photoUrl should be set correctly", "MY_PHOTO_URL", _cm1.getPhotoUrl());
			assertEquals("prefix should be set correctly", "MY_PREFIX", _cm1.getPrefix());
			assertEquals("suffix should be set correctly", "MY_SUFFIX", _cm1.getSuffix());
			
			// create(_cm1) -> _cm2
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm2 = _response.readEntity(ContactModel.class);
			
			// validate the local object after the remote create()
			assertNull("id should still be null", _cm1.getId());
			assertEquals("birthday should be unchanged", _bdate, _cm1.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY", _cm1.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT", _cm1.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME", _cm1.getFirstName());
			assertNull("fn should be empty", _cm1.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE", _cm1.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME", _cm1.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME", _cm1.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME", _cm1.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME", _cm1.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE", _cm1.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL", _cm1.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX", _cm1.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX", _cm1.getSuffix());
			
			// validate the returned remote object
			assertNotNull("id of returned object should be set", _cm2.getId());
			assertEquals("birthday should be unchanged", _bdate, _cm2.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY", _cm2.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT", _cm2.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME", _cm2.getFirstName());
			assertNotNull("fn should be set", _cm2.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE", _cm2.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME", _cm2.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME", _cm2.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME", _cm2.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME", _cm2.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE", _cm2.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL", _cm2.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX", _cm2.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX", _cm2.getSuffix());
			
			// read(_cm2)  -> _cm3
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm2.getId()).get();
			assertEquals("read(" + _cm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm3 = _response.readEntity(ContactModel.class);
			assertEquals("id of returned object should be the same", _cm2.getId(), _cm3.getId());
			assertEquals("birthday should be the same", _cm2.getBirthday(), _cm3.getBirthday());
			assertEquals("company should be the same", _cm2.getCompany(), _cm3.getCompany());
			assertEquals("department should be the same", _cm2.getDepartment(), _cm3.getDepartment());
			assertEquals("firstName should be the same", _cm2.getFirstName(), _cm3.getFirstName());
			assertEquals("fn should be the same", _cm2.getFn(), _cm3.getFn());
			assertEquals("jobTitle should be the same", _cm2.getJobTitle(), _cm3.getJobTitle());
			assertEquals("lastName should be the same", _cm2.getLastName(), _cm3.getLastName());
			assertEquals("maidenName should be the same", _cm2.getMaidenName(), _cm3.getMaidenName());
			assertEquals("middleName should be the same", _cm2.getMiddleName(), _cm3.getMiddleName());
			assertEquals("nickName should be the same", _cm2.getNickName(), _cm3.getNickName());
			assertEquals("note should be the same", _cm2.getNote(), _cm3.getNote());
			assertEquals("photoUrl should be the same", _cm2.getPhotoUrl(), _cm3.getPhotoUrl());
			assertEquals("prefix should be the same", _cm2.getPrefix(), _cm3.getPrefix());
			assertEquals("suffix should be the same", _cm2.getSuffix(), _cm3.getSuffix());
			// delete(_cm3)
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm3.getId()).delete();
			assertEquals("delete(" + _cm3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactProjectWithClientSideId() {
			// new() -> _cm1 -> _cm1.setId()
			ContactModel _cm1 = new ContactModel();
			_cm1.setFirstName("testContactProjectWithClientSideId");
			_cm1.setLastName("Test");
			_cm1.setId("LOCAL_ID");
			assertEquals("id should have changed", "LOCAL_ID", _cm1.getId());
			// create(_cm1) -> BAD_REQUEST
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm1);
			assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactProjectWithDuplicateId() {
			// create(new()) -> _cm1
			ContactModel _cm = new ContactModel();
			_cm.setFirstName("testContactProjectWithDuplicateId");
			_cm.setLastName("Test");
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm1 = _response.readEntity(ContactModel.class);

			// new() -> _cm2 -> _cm2.setId(_cm1.getId())
			ContactModel _cm2 = new ContactModel();
			_cm2.setFirstName("testContactProjectWithDuplicateId2");
			_cm2.setLastName("Test");
			_cm2.setId(_cm1.getId());		// wrongly create a 2nd ContactModel object with the same ID
			
			// create(_cm2) -> CONFLICT
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm2);
			assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactList() {
			ArrayList<ContactModel> _localList = new ArrayList<ContactModel>();		
			Response _response = null;
			webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT);
			ContactModel _c = null;
			for (int i = 0; i < LIMIT; i++) {
				// create(new()) -> _localList
				_c = new ContactModel();
				_c.setFirstName("testContactList" + i);
				_c.setLastName("Test");
				_response = webclient.post(_c);
				assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_localList.add(_response.readEntity(ContactModel.class));
			}
			assertEquals("size of lists should be the same", LIMIT, _localList.size());
			
			// list(/) -> _remoteList
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).get();
			List<ContactModel> _remoteList = new ArrayList<ContactModel>(webclient.getCollection(ContactModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			assertEquals("size of lists should be the same", LIMIT, _remoteList.size());
			// implicit proof: _localList.size() = _remoteList.size = LIMIT

			ArrayList<String> _remoteListIds = new ArrayList<String>();
			for (ContactModel _cm : _remoteList) {
				_remoteListIds.add(_cm.getId());
			}
			
			for (ContactModel _cm : _localList) {
				assertTrue("project <" + _cm.getId() + "> should be listed", _remoteListIds.contains(_cm.getId()));
			}
			
			for (ContactModel _cm : _localList) {
				_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_response.readEntity(ContactModel.class);
			}
			
			for (ContactModel _cm : _localList) {
				_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm.getId()).delete();
				assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			}
		}

		@Test
		public void testContactCreate() {	
			// new(custom attributes1) -> _cm1
			Date _bdate1 = new Date(1000);
			ContactModel _cm1 = setDefaultValues(new ContactModel(), _bdate1, "1");
			_cm1.setFirstName("MY_FNAME1");
			_cm1.setLastName("MY_LNAME1");
			// new(custom attributes2) -> _cm2
			Date _bdate2 = new Date(2000);
			ContactModel _cm2 = setDefaultValues(new ContactModel(), _bdate2, "2");
			_cm2.setFirstName("MY_FNAME2");
			_cm2.setLastName("MY_LNAME2");
			
			// create(_cm1)  -> _cm3
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm3 = _response.readEntity(ContactModel.class);

			// create(_cm2) -> _cm4
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm2);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm4 = _response.readEntity(ContactModel.class);		
			assertNotNull("ID should be set", _cm3.getId());
			assertNotNull("ID should be set", _cm4.getId());
			assertThat(_cm4.getId(), not(equalTo(_cm3.getId())));
			
			// validate attributes of _cm3
			assertEquals("birthday should be unchanged", _bdate1, _cm3.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY1", _cm3.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT1", _cm3.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME1", _cm3.getFirstName());
			assertNotNull("fn should be set", _cm3.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE1", _cm3.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME1", _cm3.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME1", _cm3.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME1", _cm3.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME1", _cm3.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE1", _cm3.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL1", _cm3.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX1", _cm3.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX1", _cm3.getSuffix());
			
			// validate attributes of _cm4
			assertEquals("birthday should be unchanged", _bdate2, _cm4.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY2", _cm4.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT2", _cm4.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME2", _cm4.getFirstName());
			assertNotNull("fn should be set", _cm4.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE2", _cm4.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME2", _cm4.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME2", _cm4.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME2", _cm4.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME2", _cm4.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE2", _cm4.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL2", _cm4.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX2", _cm4.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX2", _cm4.getSuffix());

			// delete(_cm3) -> NO_CONTENT
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm3.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

			// delete(_cm4) -> NO_CONTENT
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm4.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactCreateDouble() {		
			// create(new()) -> _cm
			ContactModel _c = new ContactModel();
			_c.setFirstName("testContactCreateDouble");
			_c.setLastName("Test");
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_c);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm = _response.readEntity(ContactModel.class);
			assertNotNull("ID should be set:", _cm.getId());		
			
			// create(_cm) -> CONFLICT
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm);
			assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

			// delete(_cm) -> NO_CONTENT
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactRead() {
			ArrayList<ContactModel> _localList = new ArrayList<ContactModel>();
			Response _response = null;
			webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT);
			ContactModel _c = null;
			for (int i = 0; i < LIMIT; i++) {
				_c = new ContactModel();
				_c.setFirstName("testContactRead" + i);
				_c.setLastName("Test");
				_response = webclient.post(_c);
				assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_localList.add(_response.readEntity(ContactModel.class));
			}
		
			// test read on each local element
			for (ContactModel _cm : _localList) {
				_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_response.readEntity(ContactModel.class);
			}

			// test read on each listed element
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).get();
			List<ContactModel> _remoteList = new ArrayList<ContactModel>(webclient.getCollection(ContactModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

			ContactModel _tmpObj = null;
			for (ContactModel _cm : _remoteList) {
				_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_tmpObj = _response.readEntity(ContactModel.class);
				assertEquals("ID should be unchanged when reading a project", _cm.getId(), _tmpObj.getId());						
			}

			for (ContactModel _cm : _localList) {
				_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm.getId()).delete();
				assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			}
		}
			
		@Test
		public void testContactMultiRead() {
			// new() -> _cm1
			Date _bdate1 = new Date(1000);
			ContactModel _cm1 = setDefaultValues(new ContactModel(), _bdate1, "1");
			_cm1.setFirstName("testContactMultiRead");
			_cm1.setLastName("Test");
			
			// create(_cm1) -> _cm2
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm1);
			ContactModel _cm2 = _response.readEntity(ContactModel.class);

			// read(_cm2) -> _cm3
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm2.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm3 = _response.readEntity(ContactModel.class);
			assertEquals("ID should be unchanged after read:", _cm2.getId(), _cm3.getId());		

			// read(_cm2) -> _cm4
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm2.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm4 = _response.readEntity(ContactModel.class);
			
			// but: the two objects are not equal !
			assertEquals("ID should be the same:", _cm3.getId(), _cm4.getId());
			assertEquals("birthday should be the same", _cm3.getBirthday(), _cm4.getBirthday());
			assertEquals("company should be the same", _cm3.getCompany(), _cm4.getCompany());
			assertEquals("department should be the same", _cm3.getDepartment(), _cm4.getDepartment());
			assertEquals("firstName should be the same", _cm3.getFirstName(), _cm4.getFirstName());
			assertEquals("fn should be the same", _cm3.getFn(), _cm4.getFn());
			assertEquals("jobTitle should be the same", _cm3.getJobTitle(), _cm4.getJobTitle());
			assertEquals("lastName should be the same", _cm3.getLastName(), _cm4.getLastName());
			assertEquals("maidenName should be the same", _cm3.getMaidenName(), _cm4.getMaidenName());
			assertEquals("middleName should be the same", _cm3.getMiddleName(), _cm4.getMiddleName());
			assertEquals("nickName should be the same", _cm3.getNickName(), _cm4.getNickName());
			assertEquals("note should be the same", _cm3.getNote(), _cm4.getNote());
			assertEquals("photoUrl should be the same", _cm3.getPhotoUrl(), _cm4.getPhotoUrl());
			assertEquals("prefix should be the same", _cm3.getPrefix(), _cm4.getPrefix());
			assertEquals("suffix should be the same", _cm3.getSuffix(), _cm4.getSuffix());
			
			
			assertEquals("ID should be the same:", _cm3.getId(), _cm2.getId());
			assertEquals("birthday should be the same", _cm3.getBirthday(), _cm2.getBirthday());
			assertEquals("company should be the same", _cm3.getCompany(), _cm2.getCompany());
			assertEquals("department should be the same", _cm3.getDepartment(), _cm2.getDepartment());
			assertEquals("firstName should be the same", _cm3.getFirstName(), _cm2.getFirstName());
			assertEquals("fn should be the same", _cm3.getFn(), _cm2.getFn());
			assertEquals("jobTitle should be the same", _cm3.getJobTitle(), _cm2.getJobTitle());
			assertEquals("lastName should be the same", _cm3.getLastName(), _cm2.getLastName());
			assertEquals("maidenName should be the same", _cm3.getMaidenName(), _cm2.getMaidenName());
			assertEquals("middleName should be the same", _cm3.getMiddleName(), _cm2.getMiddleName());
			assertEquals("nickName should be the same", _cm3.getNickName(), _cm2.getNickName());
			assertEquals("note should be the same", _cm3.getNote(), _cm2.getNote());
			assertEquals("photoUrl should be the same", _cm3.getPhotoUrl(), _cm2.getPhotoUrl());
			assertEquals("prefix should be the same", _cm3.getPrefix(), _cm2.getPrefix());
			assertEquals("suffix should be the same", _cm3.getSuffix(), _cm2.getSuffix());
			
			// delete(_p2)
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm2.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactUpdate() {			
			// create() -> _cm1
			ContactModel _cm = new ContactModel();
			_cm.setFirstName("testContactUpdate");
			_cm.setLastName("Test");
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm);
			ContactModel _cm1 = _response.readEntity(ContactModel.class);
			
			// change the attributes
			// update(_cm1) -> _cm2
			Date _bdate3 = new Date(3000);
			webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).put(setDefaultValues(_cm1, _bdate3, "3"));
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm2 = _response.readEntity(ContactModel.class);

			assertNotNull("ID should be set", _cm2.getId());
			assertEquals("ID should be unchanged", _cm1.getId(), _cm2.getId());	
			assertEquals("birthday should be set correctly", _bdate3, _cm2.getBirthday());
			assertEquals("company should be set correctly", "MY_COMPANY3", _cm2.getCompany());
			assertEquals("department should be set correctly", "MY_DEPT3", _cm2.getDepartment());
			assertEquals("firstName should be set correctly", "MY_FNAME3", _cm2.getFirstName());
			assertNotNull("fn should be set", _cm2.getFn());
			assertEquals("jobTitle should be set correctly", "MY_JOBTITLE3", _cm2.getJobTitle());
			assertEquals("lastName should be set correctly", "MY_LNAME3", _cm2.getLastName());
			assertEquals("maidenName should be set correctly", "MY_MNAME3", _cm2.getMaidenName());
			assertEquals("middleName should be set correctly", "MY_MNAME3", _cm2.getMiddleName());
			assertEquals("nickName should be set correctly", "MY_NNAME3", _cm2.getNickName());
			assertEquals("note should be set correctly", "MY_NOTE3", _cm2.getNote());
			assertEquals("photoUrl should be set correctly", "MY_PHOTO_URL3", _cm2.getPhotoUrl());
			assertEquals("prefix should be set correctly", "MY_PREFIX3", _cm2.getPrefix());
			assertEquals("suffix should be set correctly", "MY_SUFFIX3", _cm2.getSuffix());

			// reset the attributes
			// update(_cm1) -> _cm3
			Date _bdate4 = new Date(4000);
			webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).put(setDefaultValues(_cm1, _bdate4, "4"));
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm3 = _response.readEntity(ContactModel.class);

			assertNotNull("ID should be set", _cm3.getId());
			assertEquals("ID should be unchanged", _cm1.getId(), _cm3.getId());
			assertEquals("birthday should be set correctly", _bdate4, _cm3.getBirthday());
			assertEquals("company should be set correctly", "MY_COMPANY4", _cm3.getCompany());
			assertEquals("department should be set correctly", "MY_DEPT4", _cm3.getDepartment());
			assertEquals("firstName should be set correctly", "MY_FNAME4", _cm3.getFirstName());
			assertNotNull("fn should be set", _cm3.getFn());
			assertEquals("jobTitle should be set correctly", "MY_JOBTITLE4", _cm3.getJobTitle());
			assertEquals("lastName should be set correctly", "MY_LNAME4", _cm3.getLastName());
			assertEquals("maidenName should be set correctly", "MY_MNAME4", _cm3.getMaidenName());
			assertEquals("middleName should be set correctly", "MY_MNAME4", _cm3.getMiddleName());
			assertEquals("nickName should be set correctly", "MY_NNAME4", _cm3.getNickName());
			assertEquals("note should be set correctly", "MY_NOTE4", _cm3.getNote());
			assertEquals("photoUrl should be set correctly", "MY_PHOTO_URL4", _cm3.getPhotoUrl());
			assertEquals("prefix should be set correctly", "MY_PREFIX4", _cm3.getPrefix());
			assertEquals("suffix should be set correctly", "MY_SUFFIX4", _cm3.getSuffix());
			
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}

		@Test
		public void testContactDelete(
		) {
			// create() -> _cm1
			ContactModel _cm = new ContactModel();
			_cm.setFirstName("testContactDelete");
			_cm.setLastName("Test");
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm);
			ContactModel _cm1 = _response.readEntity(ContactModel.class);
			
			// read(_cm1) -> _tmpObj
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _tmpObj = _response.readEntity(ContactModel.class);
			assertEquals("ID should be unchanged when reading a project (remote):", _cm1.getId(), _tmpObj.getId());						

			// read(_cm1) -> _tmpObj
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(ContactModel.class);
			assertEquals("ID should be unchanged when reading a project (remote):", _cm1.getId(), _tmpObj.getId());						
			
			// delete(_cm1) -> OK
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
			// read the deleted object twice
			// read(_cm1) -> NOT_FOUND
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			// read(_cm1) -> NOT_FOUND
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactDoubleDelete() {
			ContactModel _cm = new ContactModel();
			_cm.setFirstName("testContactDoubleDelete");
			_cm.setLastName("Test");
			
			// create() -> _cm1
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm);
			ContactModel _cm1 = _response.readEntity(ContactModel.class);

			// read(_cm1) -> OK
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			
			// delete(_cm1) -> OK
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			
			// read(_cm1) -> NOT_FOUND
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			// delete _cm1 -> NOT_FOUND
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).delete();		
			assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			// read _cm1 -> NOT_FOUND
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactModifications() {
			// create(new ContactModel()) -> _cm1
			ContactModel _cm = new ContactModel();
			_cm.setFirstName("testContactModifications");
			_cm.setLastName("Test");

			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm);
			ContactModel _cm1 = _response.readEntity(ContactModel.class);
			
			// test createdAt and createdBy
			assertNotNull("create() should set createdAt", _cm1.getCreatedAt());
			assertNotNull("create() should set createdBy", _cm1.getCreatedBy());
			// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
			assertNotNull("create() should set modifiedAt", _cm1.getModifiedAt());
			assertNotNull("create() should set modifiedBy", _cm1.getModifiedBy());
			assertEquals("createdAt and modifiedAt should be identical after create()", _cm1.getCreatedAt(), _cm1.getModifiedAt());
			assertEquals("createdBy and modifiedBy should be identical after create()", _cm1.getCreatedBy(), _cm1.getModifiedBy());
			
			// update(_cm1)  -> _cm2
			_cm1.setFirstName("MY_NAME2");
			_cm1.setLastName("Test");
			webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).put(_cm1);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm2 = _response.readEntity(ContactModel.class);

			// test createdAt and createdBy (unchanged)
			assertEquals("update() should not change createdAt", _cm1.getCreatedAt(), _cm2.getCreatedAt());
			assertEquals("update() should not change createdBy", _cm1.getCreatedBy(), _cm2.getCreatedBy());
			
			// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
			assertThat(_cm2.getModifiedAt(), not(equalTo(_cm2.getCreatedAt())));
			// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
			// assertThat(_cm2.getModifiedBy(), not(equalTo(_cm2.getCreatedBy())));

			// update(_cm1) with modifiedBy/At set on client side -> ignored by server
			_cm1.setModifiedBy("MYSELF");
			_cm1.setModifiedAt(new Date(1000));
			webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).put(_cm1);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _o3 = _response.readEntity(ContactModel.class);
			
			// test, that modifiedBy really ignored the client-side value "MYSELF"
			assertThat(_cm1.getModifiedBy(), not(equalTo(_o3.getModifiedBy())));
			// check whether the client-side modifiedAt() is ignored
			assertThat(_cm1.getModifiedAt(), not(equalTo(_o3.getModifiedAt())));
			
			// delete(_cm1) -> NO_CONTENT
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testFn() {
			// test 1:   create
			// _cm1: fn=null, firstName="FirstName", lastName=null -> fn="FirstName"
			Response _response = createContact("FirstName", null);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm1 = _response.readEntity(ContactModel.class);
			assertNotNull("create() should set a valid fn", _cm1.getFn());
			assertEquals("create() should not change the firstName", "FirstName", _cm1.getFirstName());
			assertNull("create() should not change the lastName", _cm1.getLastName());

			// _cm2: fn=null, firstName=null, lastName="LastName" -> fn="LastName"
			_response = createContact(null, "LastName");
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm2 = _response.readEntity(ContactModel.class);
			assertNotNull("create() should set a valid fn", _cm2.getFn());
			assertNull("create() should not change the firstName", _cm2.getFirstName());
			assertEquals("create() should not change the lastName", "LastName", _cm2.getLastName());

			 // _cm3: fn=null, firstName="FirstName", lastName="LastName" -> fn="FirstName LastName"
			_response = createContact("FirstName", "LastName");
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm3 = _response.readEntity(ContactModel.class);
			assertNotNull("create() should set a valid fn", _cm3.getFn());
			assertEquals("create() should not change the firstName", "FirstName", _cm3.getFirstName());
			assertEquals("create() should not change the lastName", "LastName", _cm3.getLastName());
			
			// fn=null, firstName=null, lastName=null -> BAD_REQUEST 
			_response = createContact(null, null);
			assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
						
			// _cm5: fn="FN", firstName="FirstName", lastName="LastName" -> fn="FN"
			_response = createContact("FirstName", "LastName");
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm5 = _response.readEntity(ContactModel.class);
			assertNotNull("create() should not change a valid fn", _cm5.getFn());
			assertEquals("create() should not change the firstName", "FirstName", _cm5.getFirstName());
			assertEquals("create() should not change the lastName", "LastName", _cm5.getLastName());
			
			// test 3:  update on firstName -> should not change fn and lastName
			_cm5.setFirstName("FirstName2");
			webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm5.getId()).put(_cm5);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm7 = _response.readEntity(ContactModel.class);
			assertNotNull("create() should not change the fn", _cm7.getFn());
			assertEquals("create() should  change the firstName", "FirstName2", _cm7.getFirstName());
			assertEquals("create() should not change the lastName", "LastName", _cm7.getLastName());
			
			// test 4:  update on lastName -> should not change fn and firstName
			_cm7.setLastName("LastName2");
			webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm7.getId()).put(_cm7);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _cm8 = _response.readEntity(ContactModel.class);
			assertNotNull("create() should not change the fn", _cm8.getFn());
			assertEquals("create() should not change the firstName", "FirstName2", _cm8.getFirstName());
			assertEquals("create() should change the lastName", "LastName2", _cm8.getLastName());
			
			// delete all created contacts -> NO_CONTENT
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm1.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm2.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_cm3.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}

		/********************************** helper methods *********************************/			
		private ContactModel setDefaultValues(ContactModel cm, Date bdate, String suffix) {
			cm.setBirthday(bdate);
			cm.setCompany("MY_COMPANY" + suffix);
			cm.setDepartment("MY_DEPT" + suffix);
			cm.setFirstName("MY_FNAME" + suffix);
			cm.setJobTitle("MY_JOBTITLE" + suffix);
			cm.setLastName("MY_LNAME" + suffix);
			cm.setMaidenName("MY_MNAME" + suffix);
			cm.setMiddleName("MY_MNAME" + suffix);
			cm.setNickName("MY_NNAME" + suffix);
			cm.setNote("MY_NOTE" + suffix);
			cm.setPhotoUrl("MY_PHOTO_URL" + suffix);
			cm.setPrefix("MY_PREFIX" + suffix);
			cm.setSuffix("MY_SUFFIX" + suffix);
			return cm;
		}
		
		private Response createContact(String fName, String lName) {
			ContactModel _cm = new ContactModel();
			_cm.setFirstName(fName);
			_cm.setLastName(lName);
			return webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm);
		}
}
