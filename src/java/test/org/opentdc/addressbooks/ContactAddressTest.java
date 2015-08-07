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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressModel;
import org.opentdc.addressbooks.AddressType;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.AttributeType;
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.addressbooks.MessageType;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

public class ContactAddressTest extends AbstractTestClient {
	public static final String PATH_EL_ADDRESS = "address";
	private static AddressbookModel adb = null;
	private static ContactModel contact = null;
	private WebClient addressbookWC = null;
		
	@Before
	public void initializeTests() {
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		adb = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName(), Status.OK);
		contact = ContactTest.createContact(addressbookWC, adb.getId(), "Address", "Test");
	}
	
	@After
	public void cleanupTest() {
		AddressbookTest.cleanup(addressbookWC, adb.getId(), this.getClass().getName());
	}
	
	/********************************** address attributes tests *********************************/			
	@Test
	public void testAddressModelEmptyConstructor() {
		// new() -> _am
		AddressModel _am = new AddressModel();
		assertNull("id should not be set by empty constructor", _am.getId());
		assertNull("addressType should not be set by empty constructor", _am.getAddressType());
		assertNull("attributeType should not be set by empty constructor", _am.getAttributeType());
		assertNull("msgType should not be set by empty constructor", _am.getMsgType());
		assertNull("value should not be set by empty constructor", _am.getValue());
		assertNull("street should not be set by empty constructor", _am.getStreet());
		assertNull("postalCode should not be set by empty constructor", _am.getPostalCode());
		assertNull("city should not be set by empty constructor", _am.getCity());
		assertEquals("countryCode should be set to zero by empty constructor", 0, _am.getCountryCode());
	}
			
	@Test
	public void testIdChange() {
		// new() -> _am -> _am.setId()
		AddressModel _am = new AddressModel();
		assertNull("id should not be set by constructor", _am.getId());
		_am.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _am.getId());
	}

	// addressType
	@Test
	public void testAddressTypeChange() {
		// new() -> _am -> _am.setAddressType()
		AddressModel _am = new AddressModel();
		assertNull("addressType should not be set by empty constructor", _am.getAddressType());
		_am.setAddressType(AddressType.PHONE);
		assertEquals("addressType should have changed", AddressType.PHONE, _am.getAddressType());
	}
	
	// Type
	@Test
	public void testAttributeTypeChange() {
		// new() -> _am -> _am.setType()
		AddressModel _am = new AddressModel();
		assertNull("attributeType should not be set by empty constructor", _am.getAttributeType());
		_am.setAttributeType(AttributeType.OTHER);
		assertEquals("attributeType should have changed:", AttributeType.OTHER, _am.getAttributeType());
	}

	// MsgType
	@Test
	public void testMsgTypeChange() {
		// new() -> _am -> _am.setMsgType()
		AddressModel _am = new AddressModel();
		assertNull("msgType should not be set by empty constructor", _am.getMsgType());
		_am.setMsgType(MessageType.FACEBOOK);
		assertEquals("msgType should have changed:", MessageType.FACEBOOK, _am.getMsgType());
	}
	
	// Value
	@Test
	public void testValueChange() {
		// new() -> _am -> _am.setValue()
		AddressModel _am = new AddressModel();
		assertNull("value should not be set by empty constructor", _am.getValue());
		_am.setValue("MY_VALUE");
		assertEquals("value should have changed:", "MY_VALUE", _am.getValue());
	}
	
	// Street
	@Test
	public void testStreetChange() {
		// new() -> _am -> _am.setStreet()
		AddressModel _am = new AddressModel();
		assertNull("street should not be set by empty constructor", _am.getStreet());
		_am.setStreet("MY_STREET");
		assertEquals("street should have changed:", "MY_STREET", _am.getStreet());
	}
	
	// PostalCode
	@Test
	public void testPostalCodeChange() {
		// new() -> _am -> _am.setPostalCode()
		AddressModel _am = new AddressModel();
		assertNull("postalCode should not be set by empty constructor", _am.getPostalCode());
		_am.setPostalCode("MY_POSTAL_CODE");
		assertEquals("postalCode should have changed:", "MY_POSTAL_CODE", _am.getPostalCode());
	}
	
	// City
	@Test
	public void testCityChange() {
		// new() -> _am -> _am.setCity()
		AddressModel _am = new AddressModel();
		assertNull("city should not be set by empty constructor", _am.getCity());
		_am.setCity("MY_CITY");
		assertEquals("city should have changed:", "MY_CITY", _am.getCity());
	}

	// Country
	@Test
	public void testCountryChange() {
		// new() -> _am -> _am.setCountry()
		AddressModel _am = new AddressModel();
		assertEquals("countryCode should be set to 0 by empty constructor", 0, _am.getCountryCode());
		_am.setCountryCode((short) 100);
		assertEquals("countryCode should have changed:", 100, _am.getCountryCode());
	}
	
	@Test
	public void testAddressCreatedBy() {
		// new() -> _am -> _am.setCreatedBy()
		AddressModel _am = new AddressModel();
		assertNull("createdBy should not be set by empty constructor", _am.getCreatedBy());
		_am.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _am.getCreatedBy());	
	}
	
	@Test
	public void testAddressCreatedAt() {
		// new() -> _am -> _am.setCreatedAt()
		AddressModel _am = new AddressModel();
		assertNull("createdAt should not be set by empty constructor", _am.getCreatedAt());
		_am.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _am.getCreatedAt());
	}
		
	@Test
	public void testAddressModifiedBy() {
		// new() -> _am -> _am.setModifiedBy()
		AddressModel _am = new AddressModel();
		assertNull("modifiedBy should not be set by empty constructor", _am.getModifiedBy());
		_am.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _am.getModifiedBy());	
	}
	
	@Test
	public void testAddressModifiedAt() {
		// new() -> _am -> _am.setModifiedAt()
		AddressModel _am = new AddressModel();
		assertNull("modifiedAt should not be set by empty constructor", _am.getModifiedAt());
		_am.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _am.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	
	// create:  POST p "api/addressbooks/{abid}/contact/{cid}/address"
	// read:    GET "api/addressbooks/{abid}/contact/{cid}/address/{adrid}"
	// update:  PUT p "api/addressbooks/{abid}/contact/{cid}/address/{adrid}"
	// delete:  DELETE "api/addressbooks/{abid}/contact/{cid}/address/{ardid}"


	@Test
	public void testAddressCreateReadDeleteWithEmptyConstructor() {
		// new() -> _am1
		AddressModel _am1 = new AddressModel();
		assertNull("id should not be set by empty constructor", _am1.getId());
		assertNull("addressType should not be set by empty constructor", _am1.getAddressType());
		assertNull("attributeType should not be set by empty constructor", _am1.getAttributeType());
		assertNull("msgType should not be set by empty constructor", _am1.getMsgType());
		assertNull("value should not be set by empty constructor", _am1.getValue());
		assertNull("street should not be set by empty constructor", _am1.getStreet());
		assertNull("postalCode should not be set by empty constructor", _am1.getPostalCode());
		assertNull("city should not be set by empty constructor", _am1.getCity());
		assertEquals("countryCode should be set to zero by empty constructor", 0, _am1.getCountryCode());
		
		// create(_am1) -> BAD_REQUEST (because of empty addressType)
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setAddressType(AddressType.EMAIL);
		
		// create(_am1) -> BAD_REQUEST (because of empty attributeType)
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setAttributeType(AttributeType.OTHER);

		// create(_am1) -> BAD_REQUEST (because of empty value)
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setValue("MY_VALUE");
		
		// create(_am1) -> _am2
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am2 = _response.readEntity(AddressModel.class);
		
		// validate _am1
		assertNull("create() should not change the id of the local object", _am1.getId());
		assertEquals("create() should not change the addressType of the local object", AddressType.EMAIL, _am1.getAddressType());
		assertEquals("create() should not change the attributeType of the local object", AttributeType.OTHER, _am1.getAttributeType());
		assertNull("create() should not change the msgType of the local object", _am1.getMsgType());
		assertEquals("create() should not change the value of the local object", "MY_VALUE", _am1.getValue());
		assertNull("create() should not change the street of the local object", _am1.getStreet());
		assertNull("create() should not change the postalCode of the local object", _am1.getPostalCode());
		assertNull("create() should not change the city of the local object", _am1.getCity());
		assertEquals("create() should not change the countryCode of the local object", 0, _am1.getCountryCode());

		// validate _am2
		assertNotNull("create() should set a valid id on the remote object returned", _am2.getId());
		assertEquals("addressType of returned object should be unchanged after remote create", AddressType.EMAIL, _am2.getAddressType()); 
		assertEquals("attributeType of returned object should still be unchanged after remote create", AttributeType.OTHER, _am2.getAttributeType());
		assertNull("msgType of returned object should still be null after remote create", _am2.getMsgType());
		assertEquals("value of returned object should still be unchanged after remote create", "MY_VALUE", _am2.getValue());
		assertNull("street of returned object should still be null after remote create", _am2.getStreet());
		assertNull("postalCode of returned object should still be null after remote create", _am2.getPostalCode());
		assertNull("city of returned object should still be null after remote create", _am2.getCity());
		assertEquals("countryCode of returned object should still be zero after remote create", 0, _am2.getCountryCode());

		// read(_am2) -> _am3
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am2.getId()).get();
		assertEquals("read(" + _am2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am3 = _response.readEntity(AddressModel.class);
		assertEquals("id of returned object should be the same", _am2.getId(), _am3.getId());
		assertEquals("addressType of returned object should be unchanged after remote create", _am2.getAddressType(), _am3.getAddressType());
		assertEquals("attributeType of returned object should be unchanged after remote create", _am2.getAttributeType(), _am3.getAttributeType());
		assertEquals("msgType of returned object should be unchanged after remote create", _am2.getMsgType(), _am3.getMsgType());
		assertEquals("value of returned object should be unchanged after remote create", _am2.getValue(), _am3.getValue());
		assertEquals("street of returned object should be unchanged after remote create", _am2.getStreet(), _am3.getStreet());
		assertEquals("postalCode of returned object should be unchanged after remote create", _am2.getPostalCode(), _am3.getPostalCode());
		assertEquals("city of returned object should be unchanged after remote create", _am2.getCity(), _am3.getCity());
		assertEquals("countryCode of returned object should be unchanged after remote create", _am2.getCountryCode(), _am3.getCountryCode());
		// delete(_p3)
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am3.getId()).delete();
		assertEquals("delete(" + _am3.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
//	"MY_ATTR_TYPE"		setAddressType			getAddressType		addressType
//	"MY_TYPE"			setType					getType				type
//	"MY_MSG_TYPE"		setMsgType				getMsgType			msgType
//	"MY_VALUE"			setValue				getValue			value
//	"MY_STREET"			setStreet				getStreet			street
//	"MY_POSTAL_CODE"	setPostalCode			getPostalCode		postalCode
//	"MY_CITY"			setCity					getCity				city
//	0					setCountryCode			getCountryCode		countryCode
	
	@Test
	public void testAddressCreateReadDelete() {
		// new(with custom attributes) -> _am1
		AddressModel _am1 = setPostalAddressDefaultValues(new AddressModel(), 0);
		assertNull("id should not be set by constructor", _am1.getId());
		assertEquals("addressType should be set correctly", AddressType.POSTAL, _am1.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.getDefaultAttributeType(), _am1.getAttributeType());
		assertNull("msgType should not be set", _am1.getMsgType());
		assertNull("value should be not be set", _am1.getValue());
		assertEquals("street should be set correctly", "MY_STREET0", _am1.getStreet());
		assertEquals("postalCode should be set correctly", "MY_POSTAL_CODE0", _am1.getPostalCode());
		assertEquals("city should be set correctly", "MY_CITY0", _am1.getCity());
		assertEquals("countryCode should be set correctly", 0, _am1.getCountryCode());

		// create(_am1) -> _am2
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am2 = _response.readEntity(AddressModel.class);
		
		// validate the local object after the remote create()
		assertNull("id should not be set by constructor", _am1.getId());
		assertEquals("addressType should be unchanged", AddressType.POSTAL, _am1.getAddressType());
		assertEquals("attributeType should be unchanged", AttributeType.getDefaultAttributeType(), _am1.getAttributeType());
		assertNull("msgType should be unchanged", _am1.getMsgType());
		assertNull("value should be be unchanged", _am1.getValue());
		assertEquals("street should be unchanged", "MY_STREET0", _am1.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE0", _am1.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY0", _am1.getCity());
		assertEquals("countryCode should be unchanged", 0, _am1.getCountryCode());
		
		// validate the returned remote object
		assertNotNull("id of returned object should be set", _am2.getId());
		assertEquals("addressType should be unchanged", AddressType.POSTAL, _am2.getAddressType());
		assertEquals("attributeType should be unchanged", AttributeType.getDefaultAttributeType(), _am2.getAttributeType());
		assertNull("msgType should be unchanged", _am2.getMsgType());
		assertNull("value should be be unchanged", _am2.getValue());
		assertEquals("street should be unchanged", "MY_STREET0", _am2.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE0", _am2.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY0", _am2.getCity());
		assertEquals("countryCode should be unchanged", 0, _am2.getCountryCode());
		
		// read(_am2)  -> _am3
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am2.getId()).get();
		assertEquals("read(" + _am2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am3 = _response.readEntity(AddressModel.class);
		assertEquals("id of returned object should be the same", _am2.getId(), _am3.getId());
		assertEquals("addressType should be the same", _am2.getAddressType(), _am3.getAddressType());
		assertEquals("attributeType should be the same", _am2.getAttributeType(), _am3.getAttributeType());
		assertNull("msgType should be the same", _am3.getMsgType());
		assertNull("value should be be the same", _am3.getValue());
		assertEquals("street should be the same", _am2.getStreet(), _am3.getStreet());
		assertEquals("postalCode should be the same", _am2.getPostalCode(), _am3.getPostalCode());
		assertEquals("city should be the same", _am2.getCity(), _am3.getCity());
		assertEquals("countryCode should be the same", _am2.getCountryCode(), _am3.getCountryCode());
		
		// delete(_am3)
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am3.getId()).delete();
		assertEquals("delete(" + _am3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressProjectWithClientSideId() {
		// new() -> _am1 -> _am1.setId()
		AddressModel _am1 = createUrlAddress(AttributeType.HOME, "testAddressProjectWithClientSideId");
		_am1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _am1.getId());
		// create(_am1) -> BAD_REQUEST
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressProjectWithDuplicateId() {
		// create(new()) -> _am1
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(
				createUrlAddress(AttributeType.HOME, "testAddressProjectWithDuplicateId"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am1 = _response.readEntity(AddressModel.class);

		// new() -> _am2 -> _am2.setId(_am1.getId())
		AddressModel _am2 = createUrlAddress(AttributeType.HOME, "testAddressProjectWithDuplicateId2");
		_am2.setId(_am1.getId());		// wrongly create a 2nd AddressModel object with the same ID
		
		// create(_am2) -> CONFLICT
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressList() {
		ArrayList<AddressModel> _localList = new ArrayList<AddressModel>();		
		Response _response = null;
		addressbookWC.replacePath("/").path(adb.getId()).
			path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
			path(PATH_EL_ADDRESS);
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = addressbookWC.post(createUrlAddress(AttributeType.HOME, "testAddressList" + i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(AddressModel.class));
		}
		
		// list(/) -> _remoteList
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).get();
		List<AddressModel> _remoteList = new ArrayList<AddressModel>(addressbookWC.getCollection(AddressModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (AddressModel _am : _remoteList) {
			_remoteListIds.add(_am.getId());
		}
		
		for (AddressModel _am : _localList) {
			assertTrue("project <" + _am.getId() + "> should be listed", _remoteListIds.contains(_am.getId()));
		}
		
		for (AddressModel _am : _localList) {
			_response = addressbookWC.replacePath("/").path(adb.getId()).
					path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
					path(PATH_EL_ADDRESS).path(_am.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(AddressModel.class);
		}
		
		for (AddressModel _am : _localList) {
			_response = addressbookWC.replacePath("/").path(adb.getId()).
					path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
					path(PATH_EL_ADDRESS).path(_am.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testAddressCreate() {	
		// new(custom attributes1) -> _am1
		AddressModel _am1 = setPostalAddressDefaultValues(new AddressModel(), 1);
		// new(custom attributes2) -> _am2
		AddressModel _am2 = setPostalAddressDefaultValues(new AddressModel(), 2);
		
		// create(_am1)  -> _am3
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am3 = _response.readEntity(AddressModel.class);

		// create(_am2) -> _am4
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am4 = _response.readEntity(AddressModel.class);		
		assertNotNull("ID should be set", _am3.getId());
		assertNotNull("ID should be set", _am4.getId());
		assertThat(_am4.getId(), not(equalTo(_am3.getId())));
		
		// validate attributes of _am3
		assertEquals("addressType should be unchanged", AddressType.POSTAL, _am3.getAddressType());
		assertEquals("attributeType should be unchanged", AttributeType.getDefaultAttributeType(), _am3.getAttributeType());
		assertNull("msgType should be unchanged", _am3.getMsgType());
		assertNull("value should be unchanged", _am3.getValue());
		assertEquals("street should be unchanged", "MY_STREET1", _am3.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE1", _am3.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY1", _am3.getCity());
		assertEquals("countryCode should be unchanged", 1, _am3.getCountryCode());
		
		// validate attributes of _am4
		assertEquals("addressType should be unchanged", AddressType.POSTAL, _am4.getAddressType());
		assertEquals("attributeType should be unchanged", AttributeType.getDefaultAttributeType(), _am4.getAttributeType());
		assertNull("msgType should be unchanged", _am4.getMsgType());
		assertNull("value should be unchanged", _am4.getValue());
		assertEquals("street should be unchanged", "MY_STREET2", _am4.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE2", _am4.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY2", _am4.getCity());
		assertEquals("countryCode should be unchanged", 2, _am4.getCountryCode());
		
		// delete(_am3) -> NO_CONTENT
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_am4) -> NO_CONTENT
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testAddressCreateDouble() {		
		// create(new()) -> _am
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(createUrlAddress(AttributeType.HOME, "testAddressCreateDouble"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am = _response.readEntity(AddressModel.class);
		assertNotNull("ID should be set:", _am.getId());		
		
		// create(_am) -> CONFLICT
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_am) -> NO_CONTENT
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressRead() {
		ArrayList<AddressModel> _localList = new ArrayList<AddressModel>();
		Response _response = null;
		addressbookWC.replacePath("/").path(adb.getId()).
			path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
			path(PATH_EL_ADDRESS);
		for (int i = 0; i < LIMIT; i++) {
			_response = addressbookWC.post(createUrlAddress(AttributeType.HOME, "testAddressRead" + i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(AddressModel.class));
		}
	
		// test read on each local element
		for (AddressModel _am : _localList) {
			_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(AddressModel.class);
		}

		// test read on each listed element
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).get();
		List<AddressModel> _remoteList = new ArrayList<AddressModel>(addressbookWC.getCollection(AddressModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		AddressModel _tmpObj = null;
		for (AddressModel _am : _remoteList) {
			_response = addressbookWC.replacePath("/").path(adb.getId()).
					path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
					path(PATH_EL_ADDRESS).path(_am.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(AddressModel.class);
			assertEquals("ID should be unchanged when reading a project", _am.getId(), _tmpObj.getId());						
		}

		for (AddressModel _am : _localList) {
			_response = addressbookWC.replacePath("/").path(adb.getId()).
					path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
					path(PATH_EL_ADDRESS).path(_am.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}
		
	@Test
	public void testAddressMultiRead() {
		// new() -> _am1
		AddressModel _am1 = setPostalAddressDefaultValues(new AddressModel(), 1);
		
		// create(_am1) -> _am2
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		AddressModel _am2 = _response.readEntity(AddressModel.class);

		// read(_am2) -> _am3
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am3 = _response.readEntity(AddressModel.class);
		assertEquals("ID should be unchanged after read:", _am2.getId(), _am3.getId());		

		// read(_am2) -> _am4
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am4 = _response.readEntity(AddressModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same:", _am3.getId(), _am4.getId());
		assertEquals("addressType should be the same", _am3.getAddressType(), _am4.getAddressType());
		assertEquals("type should be the same", _am3.getAttributeType(), _am4.getAttributeType());
		assertEquals("msgType should be the same", _am3.getMsgType(), _am4.getMsgType());
		assertEquals("value should be the same", _am3.getValue(), _am4.getValue());
		assertEquals("street should be the same", _am3.getStreet(), _am4.getStreet());
		assertEquals("postalCode should be the same", _am3.getPostalCode(), _am4.getPostalCode());
		assertEquals("city should be the same", _am3.getCity(), _am4.getCity());
		assertEquals("countryCode should be the same", _am3.getCountryCode(), _am4.getCountryCode());
		
		assertEquals("ID should be the same:", _am3.getId(), _am2.getId());
		assertEquals("addressType should be the same", _am3.getAddressType(), _am2.getAddressType());
		assertEquals("type should be the same", _am3.getAttributeType(), _am2.getAttributeType());
		assertEquals("msgType should be the same", _am3.getMsgType(), _am2.getMsgType());
		assertEquals("value should be the same", _am3.getValue(), _am2.getValue());
		assertEquals("street should be the same", _am3.getStreet(), _am2.getStreet());
		assertEquals("postalCode should be the same", _am3.getPostalCode(), _am2.getPostalCode());
		assertEquals("city should be the same", _am3.getCity(), _am2.getCity());
		assertEquals("countryCode should be the same", _am3.getCountryCode(), _am2.getCountryCode());
		
		// delete(_am2)
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressUpdate() {
		// create() -> _am1
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(
				createPostalAddress(AttributeType.WORK, "testAddressUpdate2", "MY_POSTALCODE", "MY_CITY", 100));
		AddressModel _am1 = _response.readEntity(AddressModel.class);
		
		// change the attributes
		// update(_am1) -> _am2
		addressbookWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).put(setPostalAddressDefaultValues(_am1, 2));
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am2 = _response.readEntity(AddressModel.class);

		assertNotNull("ID should be set", _am2.getId());
		assertEquals("ID should be unchanged", _am1.getId(), _am2.getId());
		assertEquals("addressType should be set correctly", AddressType.POSTAL, _am2.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.getDefaultAttributeType(), _am2.getAttributeType());
		assertNull("msgType should not be set", _am2.getMsgType());
		assertNull("value should not be set", _am2.getValue());
		assertEquals("street should be set correctly", "MY_STREET2", _am2.getStreet());
		assertEquals("postalCode should be set correctly", "MY_POSTAL_CODE2", _am2.getPostalCode());
		assertEquals("city should be set correctly", "MY_CITY2", _am2.getCity());
		assertEquals("countryCode should be set correctly", 2, _am2.getCountryCode());
		
		// try to change the addressType (from POSTAL to PHONE)
		// update(_am2) -> BAD_REQUEST (ValidationException)
		addressbookWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am2.getId()).put(setPhoneDefaultValues(_am2, 3));
		assertEquals("update() should return with status BAD_REQUEST(400)", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
				
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressTypePhone() {
		// PHONE:   addressType, attributeType and value are mandatory, rest of fields are ignored
		AddressModel _am1 = new AddressModel();
		
		// create(_am) -> BAD_REQUEST (because of empty addressType)
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setAddressType(AddressType.PHONE);

		// create(_am) -> BAD_REQUEST (because of empty attributeType)
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setAttributeType(AttributeType.HOME);
		
		// create(_am) -> BAD_REQUEST (because of empty value)
		 _response = addressbookWC.replacePath("/").path(adb.getId()).
				 path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				 path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setValue("testAddressTypePhone");

		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// check the values of the attributes
		AddressModel _am2 = _response.readEntity(AddressModel.class);
		assertEquals("addressType should be set correctly", AddressType.PHONE, _am2.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.HOME, _am2.getAttributeType());
		assertEquals("value should be set correctly", "testAddressTypePhone", _am2.getValue());

		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
		
	@Test
	public void testAddressTypeEmail() {
		// EMAIL:   addressType, attributeType and value are mandatory, rest of fields are ignored
		AddressModel _am1 = new AddressModel();
		
		// create(_am) -> BAD_REQUEST (because of empty type)
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setAddressType(AddressType.EMAIL);

		// create(_am) -> BAD_REQUEST (because of empty attributeType)
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setAttributeType(AttributeType.HOME);
		
		// create(_am) -> BAD_REQUEST (because of empty value)
		 _response = addressbookWC.replacePath("/").path(adb.getId()).
				 path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				 path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setValue("testAddressTypeEmail");

		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		// check the values of the attributes
		AddressModel _am2 = _response.readEntity(AddressModel.class);
		assertEquals("addressType should be set correctly", AddressType.EMAIL, _am2.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.HOME, _am2.getAttributeType());
		assertEquals("value should be set correctly", "testAddressTypeEmail", _am2.getValue());

		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
}
	
	@Test
	public void testAddressTypeWeb() {
		// WEB:   addressType, attributeType and value are mandatory, rest of fields are ignored
		AddressModel _am1 = new AddressModel();
		
		// create(_am) -> BAD_REQUEST (because of empty type)
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setAddressType(AddressType.WEB);

		// create(_am) -> BAD_REQUEST (because of empty attributeType)
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setAttributeType(AttributeType.HOME);
		
		// create(_am) -> BAD_REQUEST (because of empty value)
		 _response = addressbookWC.replacePath("/").path(adb.getId()).
				 path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				 path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setValue("testAddressTypeWeb");

		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		// check the values of the attributes
		AddressModel _am2 = _response.readEntity(AddressModel.class);
		assertEquals("addressType should be set correctly", AddressType.WEB, _am2.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.HOME, _am2.getAttributeType());
		assertEquals("value should be set correctly", "testAddressTypeWeb", _am2.getValue());

		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
}

	@Test
	public void testAddressTypeMessaging() {
		// MESSAGING:   addressType, attributeType, msgType and value are mandatory, rest of fields are ignored
		AddressModel _am1 = new AddressModel();
		
		// create(_am) -> BAD_REQUEST (because of empty type)
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setAddressType(AddressType.MESSAGING);

		// create(_am) -> BAD_REQUEST (because of empty attributeType)
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setAttributeType(AttributeType.HOME);
		
		// create(_am) -> BAD_REQUEST (because of empty msgType)
		 _response = addressbookWC.replacePath("/").path(adb.getId()).
				 path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				 path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setMsgType(MessageType.FACEBOOK);

		// create(_am) -> BAD_REQUEST (because of empty value)
		 _response = addressbookWC.replacePath("/").path(adb.getId()).
				 path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				 path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setValue("testAddressTypeMessaging");

		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		// check the values of the attributes
		AddressModel _am2 = _response.readEntity(AddressModel.class);
		assertEquals("addressType should be set correctly", AddressType.MESSAGING, _am2.getAddressType());
		assertEquals("type should be set correctly", AttributeType.HOME, _am2.getAttributeType());
		assertEquals("msgType should be set correctly", MessageType.FACEBOOK, _am2.getMsgType());
		assertEquals("value should be set correctly", "testAddressTypeMessaging", _am2.getValue());

		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testAddressTypePostal() {
		// POSTAL: 	only addressType and attributeType are mandatory, all other fields can be empty (empty address)
		AddressModel _am1 = new AddressModel();
		
		// create(_am) -> BAD_REQUEST (because of empty type)
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setAddressType(AddressType.POSTAL);

		// create(_am) -> BAD_REQUEST (because of empty attributeType)
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setAttributeType(AttributeType.HOME);
		
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		// check the values of the attributes
		AddressModel _am2 = _response.readEntity(AddressModel.class);
		assertEquals("addressType should be set correctly", AddressType.POSTAL, _am2.getAddressType());
		assertEquals("type should be set correctly", AttributeType.HOME, _am2.getAttributeType());

		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressDelete(
	) {
		// create() -> _am1
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(
				createUrlAddress(AttributeType.WORK, "testAddressDelete"));
		AddressModel _am1 = _response.readEntity(AddressModel.class);
		
		// read(_am1) -> _tmpObj
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _tmpObj = _response.readEntity(AddressModel.class);
		assertEquals("ID should be unchanged when reading a project (remote):", _am1.getId(), _tmpObj.getId());						

		// read(_am1) -> _tmpObj
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		_tmpObj = _response.readEntity(AddressModel.class);
		assertEquals("ID should be unchanged when reading a project (remote):", _am1.getId(), _tmpObj.getId());						
		
		// delete(_am1) -> OK
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_am1) -> NOT_FOUND
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_am1) -> NOT_FOUND
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressDoubleDelete() {
		// new() -> _am
		AddressModel _am = createUrlAddress(AttributeType.OTHER, "testAddressDoubleDelete");
		
		// create(_am) -> _am1
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(_am);
		AddressModel _am1 = _response.readEntity(AddressModel.class);

		// read(_am1) -> OK
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_am1) -> OK
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_am1) -> NOT_FOUND   (try to read a deleted address)
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _am1 -> NOT_FOUND  (try to delete an already deleted address)
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _am1 -> NOT_FOUND
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressModifications() {
		// create(new AddressModel()) -> _am1
		Response _response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).post(
				createUrlAddress(AttributeType.HOME, "testAddressModifications"));
		AddressModel _am1 = _response.readEntity(AddressModel.class);
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _am1.getCreatedAt());
		assertNotNull("create() should set createdBy", _am1.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _am1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _am1.getModifiedBy());
		assertTrue("createdAt should be less than or identical to modifiedAt after create()", _am1.getCreatedAt().compareTo(_am1.getModifiedAt()) <= 0);
		assertEquals("createdBy and modifiedBy should be identical after create()", _am1.getCreatedBy(), _am1.getModifiedBy());
		
		// update(_am1)  -> _am2
		_am1.setValue("MY_VALUE");
		addressbookWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).put(_am1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am2 = _response.readEntity(AddressModel.class);

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _am1.getCreatedAt(), _am2.getCreatedAt());
		assertEquals("update() should not change createdBy", _am1.getCreatedBy(), _am2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		assertThat(_am2.getModifiedAt(), not(equalTo(_am2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_am2.getModifiedBy(), not(equalTo(_am2.getCreatedBy())));

		// update(_am1) with modifiedBy/At set on client side -> ignored by server
		_am1.setModifiedBy("MYSELF");
		_am1.setModifiedAt(new Date(1000));
		addressbookWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).put(_am1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _o3 = _response.readEntity(AddressModel.class);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_am1.getModifiedBy(), not(equalTo(_o3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_am1.getModifiedAt(), not(equalTo(_o3.getModifiedAt())));
		
		// delete(_am1) -> NO_CONTENT
		_response = addressbookWC.replacePath("/").path(adb.getId()).
				path(ContactTest.PATH_EL_CONTACT).path(contact.getId()).
				path(PATH_EL_ADDRESS).path(_am1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	/********************************** helper methods *********************************/			
	private AddressModel setPostalAddressDefaultValues(AddressModel am, int suffix) {
		am.setAddressType(AddressType.POSTAL);
		am.setAttributeType(AttributeType.getDefaultAttributeType());
		am.setStreet("MY_STREET" + suffix);
		am.setPostalCode("MY_POSTAL_CODE" + suffix);
		am.setCity("MY_CITY" + suffix);
		am.setCountryCode((short) suffix);
		return am;
	}
	
	private AddressModel setPhoneDefaultValues(AddressModel am, int suffix) {
		am.setAddressType(AddressType.PHONE);
		am.setAttributeType(AttributeType.getDefaultAttributeType());
		am.setValue("MY_PHONE" + suffix);
		return am;
	}
	
	/*
	private AddressModel setMsgDefaultValues(AddressModel am, String suffix) {
		am.setAddressType(AddressType.MESSAGING);
		am.setAttributeType(AttributeType.getDefaultAttributeType());
		am.setMsgType(MessageType.getDefaultMessageType());
		am.setValue("MY_VALUE" + suffix);
		return am;
	}
	*/
	
	public static AddressModel createAddress(
			AddressType adrType, 
			AttributeType attrType, 
			String value) {
		AddressModel _am = new AddressModel();
		_am.setAddressType(adrType);
		_am.setAttributeType(attrType);
		_am.setValue(value);
		return _am;
	}
	
	public static AddressModel createUrlAddress(
			AttributeType attrType,
			String value) {
		AddressModel _am = new AddressModel();
		_am.setAddressType(AddressType.WEB);
		_am.setAttributeType(attrType);
		_am.setValue(value);
		return _am;
	}

	public static AddressModel createPostalAddress(
			AttributeType attrType, 
			String street, 
			String postalCode, 
			String city, 
			int countryCode) 
	{
		AddressModel _am = new AddressModel();
		_am.setAddressType(AddressType.POSTAL);
		_am.setAttributeType(attrType);
		_am.setStreet(street);
		_am.setPostalCode(postalCode);
		_am.setCity(city);
		_am.setCountryCode((short) countryCode);
		return _am;
	}
	
	public static AddressModel createMsgAddress(
			AttributeType attrType,
			MessageType msgType,
			String value) {
		AddressModel _am = new AddressModel();
		_am.setAddressType(AddressType.MESSAGING);
		_am.setAttributeType(attrType);
		_am.setMsgType(msgType);
		_am.setValue(value);
		return _am;
	}
}
