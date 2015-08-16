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

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.opentdc.addressbooks.AddressModel;
import org.opentdc.addressbooks.AddressType;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.AttributeType;
import org.opentdc.addressbooks.MessageType;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/**
 * Abstract class for testing addresses for contacts and orgs,
 * @author Bruno Kaiser
 *
 */
public abstract class AbstractAddressTestClient extends AbstractTestClient {
	protected static AddressbookModel adb = null;
	protected WebClient wc = null;
	
	/**
	 * Initialize this class.
	 * @param className
	 */
	protected void initializeTests(String className) {
		wc = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		adb = AddressbookTest.post(wc, new AddressbookModel(className), Status.OK);
		System.out.println("***** " + className);
	}
	
	/**
	 * Cleanup allocated test resources
	 */
	protected void cleanupTest() {
		AddressbookTest.delete(wc, adb.getId(), Status.NO_CONTENT);
		wc.close();
	}
	
	/********************************** address attributes tests *********************************/			
	protected void testEmptyConstructor() {
		AddressModel _model = new AddressModel();
		assertNull("id should not be set by empty constructor", _model.getId());
		assertNull("addressType should not be set by empty constructor", _model.getAddressType());
		assertNull("attributeType should not be set by empty constructor", _model.getAttributeType());
		assertNull("msgType should not be set by empty constructor", _model.getMsgType());
		assertNull("value should not be set by empty constructor", _model.getValue());
		assertNull("street should not be set by empty constructor", _model.getStreet());
		assertNull("postalCode should not be set by empty constructor", _model.getPostalCode());
		assertNull("city should not be set by empty constructor", _model.getCity());
		assertEquals("countryCode should be set to zero by empty constructor", 0, _model.getCountryCode());
	}
	
	protected void testId() {
		AddressModel _model = new AddressModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _model.getId());
	}

	protected void testAddressType() {
		AddressModel _model = new AddressModel();
		assertNull("addressType should not be set by empty constructor", _model.getAddressType());
		_model.setAddressType(AddressType.PHONE);
		assertEquals("addressType should have changed", AddressType.PHONE, _model.getAddressType());
	}
	
	protected void testAttributeType() {
		AddressModel _model = new AddressModel();
		assertNull("attributeType should not be set by empty constructor", _model.getAttributeType());
		_model.setAttributeType(AttributeType.OTHER);
		assertEquals("attributeType should have changed:", AttributeType.OTHER, _model.getAttributeType());
	}

	protected void testMsgType() {
		AddressModel _model = new AddressModel();
		assertNull("msgType should not be set by empty constructor", _model.getMsgType());
		_model.setMsgType(MessageType.FACEBOOK);
		assertEquals("msgType should have changed:", MessageType.FACEBOOK, _model.getMsgType());
	}
	
	protected void testValue() {
		AddressModel _model = new AddressModel();
		assertNull("value should not be set by empty constructor", _model.getValue());
		_model.setValue("MY_VALUE");
		assertEquals("value should have changed:", "MY_VALUE", _model.getValue());
	}
	
	protected void testStreet() {
		AddressModel _model = new AddressModel();
		assertNull("street should not be set by empty constructor", _model.getStreet());
		_model.setStreet("MY_STREET");
		assertEquals("street should have changed:", "MY_STREET", _model.getStreet());
	}
	
	protected void testPostalCode() {
		AddressModel _model = new AddressModel();
		assertNull("postalCode should not be set by empty constructor", _model.getPostalCode());
		_model.setPostalCode("MY_POSTAL_CODE");
		assertEquals("postalCode should have changed:", "MY_POSTAL_CODE", _model.getPostalCode());
	}
	
	protected void testCity() {
		AddressModel _model = new AddressModel();
		assertNull("city should not be set by empty constructor", _model.getCity());
		_model.setCity("MY_CITY");
		assertEquals("city should have changed:", "MY_CITY", _model.getCity());
	}

	protected void testCountry() {
		AddressModel _model = new AddressModel();
		assertEquals("countryCode should be set to 0 by empty constructor", 0, _model.getCountryCode());
		_model.setCountryCode((short) 100);
		assertEquals("countryCode should have changed:", 100, _model.getCountryCode());
	}
	
	protected void testCreatedBy() {
		AddressModel _model = new AddressModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _model.getCreatedBy());	
	}
	
	protected void testCreatedAt() {
		AddressModel _model = new AddressModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	protected void testModifiedBy() {
		AddressModel _model = new AddressModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _model.getModifiedBy());	
	}
	
	protected void testModifiedAt() {
		AddressModel _model = new AddressModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	protected void testCreateReadDeleteWithEmptyConstructor() {
		AddressModel _model1 = new AddressModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("addressType should not be set by empty constructor", _model1.getAddressType());
		assertNull("attributeType should not be set by empty constructor", _model1.getAttributeType());
		assertNull("msgType should not be set by empty constructor", _model1.getMsgType());
		assertNull("value should not be set by empty constructor", _model1.getValue());
		assertNull("street should not be set by empty constructor", _model1.getStreet());
		assertNull("postalCode should not be set by empty constructor", _model1.getPostalCode());
		assertNull("city should not be set by empty constructor", _model1.getCity());
		assertEquals("countryCode should be set to zero by empty constructor", 0, _model1.getCountryCode());
		
		post(_model1, Status.BAD_REQUEST);
		_model1.setAddressType(AddressType.EMAIL);
		
		post(_model1, Status.BAD_REQUEST);
		_model1.setAttributeType(AttributeType.OTHER);

		post(_model1, Status.BAD_REQUEST);
		_model1.setValue("MY_VALUE");
		
		AddressModel _model2 = post(_model1, Status.OK);

		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the addressType of the local object", AddressType.EMAIL, _model1.getAddressType());
		assertEquals("create() should not change the attributeType of the local object", AttributeType.OTHER, _model1.getAttributeType());
		assertNull("create() should not change the msgType of the local object", _model1.getMsgType());
		assertEquals("create() should not change the value of the local object", "MY_VALUE", _model1.getValue());
		assertNull("create() should not change the street of the local object", _model1.getStreet());
		assertNull("create() should not change the postalCode of the local object", _model1.getPostalCode());
		assertNull("create() should not change the city of the local object", _model1.getCity());
		assertEquals("create() should not change the countryCode of the local object", 0, _model1.getCountryCode());

		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("addressType of returned object should be unchanged after remote create", AddressType.EMAIL, _model2.getAddressType()); 
		assertEquals("attributeType of returned object should still be unchanged after remote create", AttributeType.OTHER, _model2.getAttributeType());
		assertNull("msgType of returned object should still be null after remote create", _model2.getMsgType());
		assertEquals("value of returned object should still be unchanged after remote create", "MY_VALUE", _model2.getValue());
		assertNull("street of returned object should still be null after remote create", _model2.getStreet());
		assertNull("postalCode of returned object should still be null after remote create", _model2.getPostalCode());
		assertNull("city of returned object should still be null after remote create", _model2.getCity());
		assertEquals("countryCode of returned object should still be zero after remote create", 0, _model2.getCountryCode());

		AddressModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("addressType of returned object should be unchanged after remote create", _model2.getAddressType(), _model3.getAddressType());
		assertEquals("attributeType of returned object should be unchanged after remote create", _model2.getAttributeType(), _model3.getAttributeType());
		assertEquals("msgType of returned object should be unchanged after remote create", _model2.getMsgType(), _model3.getMsgType());
		assertEquals("value of returned object should be unchanged after remote create", _model2.getValue(), _model3.getValue());
		assertEquals("street of returned object should be unchanged after remote create", _model2.getStreet(), _model3.getStreet());
		assertEquals("postalCode of returned object should be unchanged after remote create", _model2.getPostalCode(), _model3.getPostalCode());
		assertEquals("city of returned object should be unchanged after remote create", _model2.getCity(), _model3.getCity());
		assertEquals("countryCode of returned object should be unchanged after remote create", _model2.getCountryCode(), _model3.getCountryCode());

		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	protected void testCreateReadDelete() {
		AddressModel _model1 = setPostalAddressDefaultValues(new AddressModel(), 0);
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("addressType should be set correctly", AddressType.POSTAL, _model1.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.getDefaultAttributeType(), _model1.getAttributeType());
		assertNull("msgType should not be set", _model1.getMsgType());
		assertNull("value should be not be set", _model1.getValue());
		assertEquals("street should be set correctly", "MY_STREET0", _model1.getStreet());
		assertEquals("postalCode should be set correctly", "MY_POSTAL_CODE0", _model1.getPostalCode());
		assertEquals("city should be set correctly", "MY_CITY0", _model1.getCity());
		assertEquals("countryCode should be set correctly", 0, _model1.getCountryCode());

		AddressModel _model2 = post(_model1, Status.OK);
		
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("addressType should be unchanged", AddressType.POSTAL, _model1.getAddressType());
		assertEquals("attributeType should be unchanged", AttributeType.getDefaultAttributeType(), _model1.getAttributeType());
		assertNull("msgType should be unchanged", _model1.getMsgType());
		assertNull("value should be be unchanged", _model1.getValue());
		assertEquals("street should be unchanged", "MY_STREET0", _model1.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE0", _model1.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY0", _model1.getCity());
		assertEquals("countryCode should be unchanged", 0, _model1.getCountryCode());
		
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("addressType should be unchanged", AddressType.POSTAL, _model2.getAddressType());
		assertEquals("attributeType should be unchanged", AttributeType.getDefaultAttributeType(), _model2.getAttributeType());
		assertNull("msgType should be unchanged", _model2.getMsgType());
		assertNull("value should be be unchanged", _model2.getValue());
		assertEquals("street should be unchanged", "MY_STREET0", _model2.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE0", _model2.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY0", _model2.getCity());
		assertEquals("countryCode should be unchanged", 0, _model2.getCountryCode());
		
		AddressModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("addressType should be the same", _model2.getAddressType(), _model3.getAddressType());
		assertEquals("attributeType should be the same", _model2.getAttributeType(), _model3.getAttributeType());
		assertNull("msgType should be the same", _model3.getMsgType());
		assertNull("value should be be the same", _model3.getValue());
		assertEquals("street should be the same", _model2.getStreet(), _model3.getStreet());
		assertEquals("postalCode should be the same", _model2.getPostalCode(), _model3.getPostalCode());
		assertEquals("city should be the same", _model2.getCity(), _model3.getCity());
		assertEquals("countryCode should be the same", _model2.getCountryCode(), _model3.getCountryCode());
		
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	protected void testClientSideId() {
		AddressModel _model = createUrlAddress(AttributeType.HOME, "testClientSideId");
		_model.setId("testClientSideId");
		assertEquals("id should have changed", "testClientSideId", _model.getId());
		post(_model, Status.BAD_REQUEST);
	}
	
	protected void testDuplicateId() {
		AddressModel _model1 = post(createUrlAddress(AttributeType.HOME, "testDuplicateId1"), Status.OK);
		AddressModel _model2 = post(createUrlAddress(AttributeType.HOME, "testDuplicateId2"), Status.OK);
		String _model2Id = _model2.getId();
		_model2.setId(_model1.getId());
		post(_model2, Status.CONFLICT);
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2Id, Status.NO_CONTENT);
	}
	
	protected void testList() {
		List<AddressModel> _listBefore = list(null, Status.OK);
		ArrayList<AddressModel> _localList = new ArrayList<AddressModel>();		
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(createUrlAddress(AttributeType.HOME, "testAddressList" + i), Status.OK));
		}
		assertEquals("correct number of addresses should be created", LIMIT, _localList.size());

		List<AddressModel> _listAfter = list(null, Status.OK);		
		assertEquals("list() should return the correct number of addresses", _listBefore.size() + LIMIT, _listAfter.size());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (AddressModel _model : _listAfter) {
			_remoteListIds.add(_model.getId());
		}
		for (AddressModel _model : _localList) {
			assertTrue("address <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		for (AddressModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		for (AddressModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}
	
	protected void testCreate() {	
		AddressModel _model1 = post(setPostalAddressDefaultValues(new AddressModel(), 1), Status.OK);
		AddressModel _model2 = post(setPostalAddressDefaultValues(new AddressModel(), 2), Status.OK);
		assertNotNull("ID should be set", _model1.getId());
		assertNotNull("ID should be set", _model2.getId());
		assertThat(_model1.getId(), not(equalTo(_model2.getId())));
				
		assertEquals("addressType should be unchanged", AddressType.POSTAL, _model1.getAddressType());
		assertEquals("attributeType should be unchanged", AttributeType.getDefaultAttributeType(), _model1.getAttributeType());
		assertNull("msgType should be unchanged", _model1.getMsgType());
		assertNull("value should be unchanged", _model1.getValue());
		assertEquals("street should be unchanged", "MY_STREET1", _model1.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE1", _model1.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY1", _model1.getCity());
		assertEquals("countryCode should be unchanged", 1, _model1.getCountryCode());
		
		assertEquals("addressType should be unchanged", AddressType.POSTAL, _model2.getAddressType());
		assertEquals("attributeType should be unchanged", AttributeType.getDefaultAttributeType(), _model2.getAttributeType());
		assertNull("msgType should be unchanged", _model2.getMsgType());
		assertNull("value should be unchanged", _model2.getValue());
		assertEquals("street should be unchanged", "MY_STREET2", _model2.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE2", _model2.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY2", _model2.getCity());
		assertEquals("countryCode should be unchanged", 2, _model2.getCountryCode());
		
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
	}

	protected void testCreateDouble() {	
		AddressModel _model = post(createUrlAddress(AttributeType.HOME, "testCreateDouble"), Status.OK);
		assertNotNull("ID should be set", _model.getId());		
		post(_model, Status.CONFLICT);
		delete(_model.getId(), Status.NO_CONTENT);
	}
	
	protected void testRead() {
		ArrayList<AddressModel> _localList = new ArrayList<AddressModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(createUrlAddress(AttributeType.HOME, "testRead" + i), Status.OK));
		}
		for (AddressModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		List<AddressModel> _remoteList = list(null, Status.OK);
		
		for (AddressModel _model : _remoteList) {
			get(_model.getId(), Status.OK);
		}
		for (AddressModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}	
	}
		
	protected void testMultiRead() {
		AddressModel _model1 = post(setPostalAddressDefaultValues(new AddressModel(), 1), Status.OK);
		AddressModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read:", _model1.getId(), _model2.getId());	
		AddressModel _model3 = get(_model1.getId(), Status.OK);
		
		assertEquals("ID should be the same:", _model3.getId(), _model2.getId());
		assertEquals("addressType should be the same", _model3.getAddressType(), _model2.getAddressType());
		assertEquals("type should be the same", _model3.getAttributeType(), _model2.getAttributeType());
		assertEquals("msgType should be the same", _model3.getMsgType(), _model2.getMsgType());
		assertEquals("value should be the same", _model3.getValue(), _model2.getValue());
		assertEquals("street should be the same", _model3.getStreet(), _model2.getStreet());
		assertEquals("postalCode should be the same", _model3.getPostalCode(), _model2.getPostalCode());
		assertEquals("city should be the same", _model3.getCity(), _model2.getCity());
		assertEquals("countryCode should be the same", _model3.getCountryCode(), _model2.getCountryCode());
		
		assertEquals("ID should be the same:", _model1.getId(), _model2.getId());
		assertEquals("addressType should be the same", _model1.getAddressType(), _model2.getAddressType());
		assertEquals("type should be the same", _model1.getAttributeType(), _model2.getAttributeType());
		assertEquals("msgType should be the same", _model1.getMsgType(), _model2.getMsgType());
		assertEquals("value should be the same", _model1.getValue(), _model2.getValue());
		assertEquals("street should be the same", _model1.getStreet(), _model2.getStreet());
		assertEquals("postalCode should be the same", _model1.getPostalCode(), _model2.getPostalCode());
		assertEquals("city should be the same", _model1.getCity(), _model2.getCity());
		assertEquals("countryCode should be the same", _model1.getCountryCode(), _model2.getCountryCode());
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	protected void testUpdate() {
		AddressModel _model1 = post(createPostalAddress(AttributeType.WORK, "testUpdate", "MY_POSTALCODE", "MY_CITY", 100), Status.OK);
		AddressModel _model2 = put(setPostalAddressDefaultValues(_model1, 2), Status.OK);
		
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());
		assertEquals("addressType should be set correctly", AddressType.POSTAL, _model2.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.getDefaultAttributeType(), _model2.getAttributeType());
		assertNull("msgType should not be set", _model2.getMsgType());
		assertNull("value should not be set", _model2.getValue());
		assertEquals("street should be set correctly", "MY_STREET2", _model2.getStreet());
		assertEquals("postalCode should be set correctly", "MY_POSTAL_CODE2", _model2.getPostalCode());
		assertEquals("city should be set correctly", "MY_CITY2", _model2.getCity());
		assertEquals("countryCode should be set correctly", 2, _model2.getCountryCode());
		
		put(setPhoneDefaultValues(_model2, 3), Status.BAD_REQUEST);
		delete(_model1.getId(), Status.NO_CONTENT);		
	}
	
	protected void testAddressTypePhone() {
		// PHONE:   addressType, attributeType and value are mandatory, rest of fields are ignored
		AddressModel _model1 = new AddressModel();
		post(_model1, Status.BAD_REQUEST);
		_model1.setAddressType(AddressType.PHONE);
		post(_model1, Status.BAD_REQUEST);
		_model1.setAttributeType(AttributeType.HOME);
		post(_model1, Status.BAD_REQUEST);
		_model1.setValue("testAddressTypePhone");
		AddressModel _model2 = post(_model1, Status.OK);
		assertEquals("addressType should be set correctly", AddressType.PHONE, _model2.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.HOME, _model2.getAttributeType());
		assertEquals("value should be set correctly", "testAddressTypePhone", _model2.getValue());
		delete(_model2.getId(), Status.NO_CONTENT);
	}
		
	protected void testAddressTypeEmail() {
		// EMAIL:   addressType, attributeType and value are mandatory, rest of fields are ignored
		AddressModel _model1 = new AddressModel();
		post(_model1, Status.BAD_REQUEST);
		_model1.setAddressType(AddressType.EMAIL);
		post(_model1, Status.BAD_REQUEST);
		_model1.setAttributeType(AttributeType.HOME);
		post(_model1, Status.BAD_REQUEST);
		_model1.setValue("testAddressTypeEmail");
		AddressModel _model2 = post(_model1, Status.OK);
		assertEquals("addressType should be set correctly", AddressType.EMAIL, _model2.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.HOME, _model2.getAttributeType());
		assertEquals("value should be set correctly", "testAddressTypeEmail", _model2.getValue());
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	protected void testAddressTypeWeb() {
		// WEB:   addressType, attributeType and value are mandatory, rest of fields are ignored
		AddressModel _model1 = new AddressModel();
		post(_model1, Status.BAD_REQUEST);
		_model1.setAddressType(AddressType.WEB);
		post(_model1, Status.BAD_REQUEST);
		_model1.setAttributeType(AttributeType.HOME);
		post(_model1, Status.BAD_REQUEST);
		_model1.setValue("testAddressTypeWeb");
		AddressModel _model2 = post(_model1, Status.OK);
		assertEquals("addressType should be set correctly", AddressType.WEB, _model2.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.HOME, _model2.getAttributeType());
		assertEquals("value should be set correctly", "testAddressTypeWeb", _model2.getValue());
		delete(_model2.getId(), Status.NO_CONTENT);
	}

	protected void testAddressTypeMessaging() {
		// MESSAGING:   addressType, attributeType, msgType and value are mandatory, rest of fields are ignored
		AddressModel _model1 = new AddressModel();
		post(_model1, Status.BAD_REQUEST);
		_model1.setAddressType(AddressType.MESSAGING);
		post(_model1, Status.BAD_REQUEST);
		_model1.setAttributeType(AttributeType.HOME);
		post(_model1, Status.BAD_REQUEST);
		_model1.setMsgType(MessageType.FACEBOOK);
		post(_model1, Status.BAD_REQUEST);
		_model1.setValue("testAddressTypeMessaging");
		AddressModel _model2 = post(_model1, Status.OK);
		assertEquals("addressType should be set correctly", AddressType.MESSAGING, _model2.getAddressType());
		assertEquals("type should be set correctly", AttributeType.HOME, _model2.getAttributeType());
		assertEquals("msgType should be set correctly", MessageType.FACEBOOK, _model2.getMsgType());
		assertEquals("value should be set correctly", "testAddressTypeMessaging", _model2.getValue());
		delete(_model2.getId(), Status.NO_CONTENT);
	}

	protected void testAddressTypePostal() {
		// POSTAL: 	only addressType and attributeType are mandatory, all other fields can be empty (empty address)
		AddressModel _model1 = new AddressModel();
		post(_model1, Status.BAD_REQUEST);
		_model1.setAddressType(AddressType.POSTAL);
		post(_model1, Status.BAD_REQUEST);
		_model1.setAttributeType(AttributeType.HOME);
		AddressModel _model2 = post(_model1, Status.OK);
		assertEquals("addressType should be set correctly", AddressType.POSTAL, _model2.getAddressType());
		assertEquals("type should be set correctly", AttributeType.HOME, _model2.getAttributeType());
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	protected void testDelete() {
		AddressModel _model1 = post(createUrlAddress(AttributeType.WORK, "testDelete"), Status.OK);
		AddressModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a project (remote):", _model1.getId(), _model2.getId());						
		delete(_model1.getId(), Status.NO_CONTENT);
		get(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	protected void testDoubleDelete() {
		AddressModel _model = post(createUrlAddress(AttributeType.OTHER, "testDoubleDelete"), Status.OK);
		get(_model.getId(), Status.OK);
		delete(_model.getId(), Status.NO_CONTENT);
		get(_model.getId(), Status.NOT_FOUND);
		delete(_model.getId(), Status.NOT_FOUND);
		get(_model.getId(), Status.NOT_FOUND);
	}
	
	protected void testModifications() {
		AddressModel _model1 = post(createUrlAddress(AttributeType.HOME, "testModifications"), Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertTrue("createdAt should be less than or identical to modifiedAt after create()", _model1.getCreatedAt().compareTo(_model1.getModifiedAt()) <= 0);
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		
		_model1.setValue("MY_VALUE");		
		AddressModel _model2 = put(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		// next test fails because of timing issue; but we do not want to introduce a sleep() here
		// assertThat(_model2.getModifiedAt().toString(), not(equalTo(_model2.getCreatedAt().toString())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_model2.getModifiedBy(), not(equalTo(_model2.getCreatedBy())));

		String _createdBy = _model1.getCreatedBy();
		_model1.setCreatedBy("testModifications3");
		AddressModel _model3 = put(_model1, Status.OK);
		assertEquals("update() should not change createdBy", _createdBy, _model3.getCreatedBy());

		Date _createdAt = _model1.getCreatedAt();
		_model1.setCreatedAt(new Date(1000));
		AddressModel _model4 = put(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _createdAt, _model4.getCreatedAt());

		String _modifiedBy = _model1.getModifiedBy();
		_model1.setModifiedBy("testModifications5");
		AddressModel _model5 = put(_model1, Status.OK);
		assertEquals("update() should not change modifiedBy", _modifiedBy, _model5.getModifiedBy());

		Date _modifiedAt = _model1.getModifiedAt();
		Date _modifiedAt2 = new Date(1000);
		_model1.setModifiedAt(_modifiedAt2);
		AddressModel _model6 = put(_model1, Status.OK);
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt)));
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt2)));

		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************** helper methods *********************************/			
	protected AddressModel setPostalAddressDefaultValues(AddressModel model, int suffix) {
		model.setAddressType(AddressType.POSTAL);
		model.setAttributeType(AttributeType.getDefaultAttributeType());
		model.setStreet("MY_STREET" + suffix);
		model.setPostalCode("MY_POSTAL_CODE" + suffix);
		model.setCity("MY_CITY" + suffix);
		model.setCountryCode((short) suffix);
		return model;
	}
	
	protected AddressModel setPhoneDefaultValues(AddressModel model, int suffix) {
		model.setAddressType(AddressType.PHONE);
		model.setAttributeType(AttributeType.getDefaultAttributeType());
		model.setValue("MY_PHONE" + suffix);
		return model;
	}
	
	/*
	protected AddressModel setMsgDefaultValues(AddressModel model, String suffix) {
		model.setAddressType(AddressType.MESSAGING);
		model.setAttributeType(AttributeType.getDefaultAttributeType());
		model.setMsgType(MessageType.getDefaultMessageType());
		model.setValue("MY_VALUE" + suffix);
		return model;
	}
	*/
	
	public static AddressModel createAddress(
			AddressType adrType, 
			AttributeType attrType, 
			String value) 
	{
		AddressModel _model = new AddressModel();
		_model.setAddressType(adrType);
		_model.setAttributeType(attrType);
		_model.setValue(value);
		return _model;
	}
	
	public static AddressModel createUrlAddress(
			AttributeType attrType,
			String value) 
	{
		AddressModel _model = new AddressModel();
		_model.setAddressType(AddressType.WEB);
		_model.setAttributeType(attrType);
		_model.setValue(value);
		return _model;
	}

	public static AddressModel createPostalAddress(
			AttributeType attrType, 
			String street, 
			String postalCode, 
			String city, 
			int countryCode) 
	{
		AddressModel _model = new AddressModel();
		_model.setAddressType(AddressType.POSTAL);
		_model.setAttributeType(attrType);
		_model.setStreet(street);
		_model.setPostalCode(postalCode);
		_model.setCity(city);
		_model.setCountryCode((short) countryCode);
		return _model;
	}
	
	public static AddressModel createMsgAddress(
			AttributeType attrType,
			MessageType msgType,
			String value) 
	{
		AddressModel _model = new AddressModel();
		_model.setAddressType(AddressType.MESSAGING);
		_model.setAttributeType(attrType);
		_model.setMsgType(msgType);
		_model.setValue(value);
		return _model;
	}
	
	/**
	 * Retrieve a list of AddressModel from AddressbooksService by executing a HTTP GET request.
	 * This uses neither position nor size queries, ie returning all elements.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of AddressModel object in JSON format
	 */
	abstract protected List<AddressModel> list(String query, Status expectedStatus);
	
	/**
	 * Create a new AddressModel on the server by executing a HTTP POST request.
	 * @param model the AddressModel to post to the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created AddressModel
	 */
	abstract protected AddressModel post(AddressModel model, Status expectedStatus);
	
	/**
	 * Read the AddressModel with id from AddressbooksService by executing a HTTP GET method.
	 * @param textId the id of the AddressModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved AddressModel object in JSON format
	 */
	abstract protected AddressModel get(String adrId, Status expectedStatus);
	
	/**
	 * Update a AddressModel on the AddressbooksService by executing a HTTP PUT method.
	 * @param model the new AddressModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated AddressModel object in JSON format
	 */
	abstract protected AddressModel put(AddressModel model, Status expectedStatus);
	
	/**
	 * Delete the AddressModel by id on the AddressbooksService by executing a HTTP DELETE method.
	 * @param id the id of the AddressModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	abstract protected void delete(String id, Status expectedStatus);
		
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	protected int calculateMembers() {
		return AddressbookTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK).size();
	}
}
