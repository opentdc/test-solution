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
package test.org.opentdc.invitations;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.invitations.InvitationModel;
import org.opentdc.invitations.InvitationsService;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/*
 * Tests for batched listing of invitations
 * @author Bruno Kaiser
 */
public class InvitationsBatchedListTest extends AbstractTestClient {
	private WebClient invitationWC = null;

	@Before
	public void initializeTests() {
		invitationWC = initializeTest(ServiceUtil.INVITATIONS_API_URL, InvitationsService.class);
	}

	@After
	public void cleanupTest() {
		invitationWC.close();
	}

	@Test
	public void testInvitationBatchedList() {
		ArrayList<InvitationModel> _localList = new ArrayList<InvitationModel>();		
		Response _response = null;
		System.out.println("***** testInvitationBatchedList:");
		invitationWC.replacePath("/");
		// we want to allocate more than double the amount of default list size objects
		int _batchSize = GenericService.DEF_SIZE;
		int _increment = 5;
		int _limit2 = 2 * _batchSize + _increment;		// if DEF_SIZE == 25 -> _limit2 = 55
		InvitationModel _res = null;
		for (int i = 0; i < _limit2; i++) {
			// create(new()) -> _localList
			_res = new InvitationModel();
			_res.setFirstName("Hans" + i);
			_res.setLastName("Muster");
			_res.setEmail("hans.muster@test.com");
			_response = invitationWC.post(_res);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(InvitationModel.class));
			System.out.println("posted InvitationModel " + _res.getFirstName() + " " + _res.getLastName());
		}
		System.out.println("****** locallist:");
		for (InvitationModel _rm : _localList) {
			System.out.println(_rm.getFirstName() + " " + _rm.getLastName());
		}

		// get first batch
		// list(position=0, size=25) -> elements 0 .. 24
		invitationWC.resetQuery();
		_response = invitationWC.replacePath("/").get();
		List<InvitationModel> _remoteList1 = new ArrayList<InvitationModel>(invitationWC.getCollection(InvitationModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 1st Batch:");
		for (InvitationModel _rm : _remoteList1) {
			System.out.println(_rm.getFirstName() + " " + _rm.getLastName());
		}
		assertEquals("size of lists should be the same", _batchSize, _remoteList1.size());
		
		// get second batch
		// list(position=25, size=25) -> elements 25 .. 49
		invitationWC.resetQuery();
		_response = invitationWC.replacePath("/").query("position", 25).get();
		List<InvitationModel> _remoteList2 = new ArrayList<InvitationModel>(invitationWC.getCollection(InvitationModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("size of lists should be the same", _batchSize, _remoteList2.size());
		System.out.println("****** 2nd Batch:");
		for (InvitationModel _rm : _remoteList2) {
			System.out.println(_rm.getFirstName() + " " + _rm.getLastName());
		}
		
		// get rest 
		// list(position=50, size=25) ->   elements 50 .. 54
		invitationWC.resetQuery();
		_response = invitationWC.replacePath("/").query("position", 50).get();
		List<InvitationModel> _remoteList3 = new ArrayList<InvitationModel>(invitationWC.getCollection(InvitationModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 3rd Batch:");
		for (InvitationModel _rm : _remoteList3) {
			System.out.println(_rm.getFirstName() + " " + _rm.getLastName());
		}
		assertEquals("size of lists should be the same", _increment, _remoteList3.size());
		
		// testing the batches
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
		List<InvitationModel> _remoteList = null;
		while(true) {
			_numberOfBatches++;
			invitationWC.resetQuery();
			_response = invitationWC.replacePath("/").query("position", _position).get();
			_remoteList = new ArrayList<InvitationModel>(invitationWC.getCollection(InvitationModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_numberOfReturnedObjects += _remoteList.size();
			System.out.println("batch " + _numberOfBatches + ": position=" + _position + ", returnedObjects=" + _numberOfReturnedObjects);
			if (_remoteList.size() < GenericService.DEF_SIZE) {
				break;
			} else {
				_position += GenericService.DEF_SIZE;					
			}
		}
		assertEquals("number of batches should be as expected", 3, _numberOfBatches);
		assertEquals("should have returned all objects", _limit2, _numberOfReturnedObjects);
		assertEquals("last batch size should be as expected", _increment, _remoteList.size());
	
		// testing some explicit positions and sizes
		invitationWC.resetQuery();
		// get next 5 elements from position 5
		_response = invitationWC.replacePath("/").query("position", 5).query("size", 5).get();
		_remoteList = new ArrayList<InvitationModel>(invitationWC.getCollection(InvitationModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// get last 4 elements 
		invitationWC.resetQuery();
		_response = invitationWC.replacePath("/").query("position", _limit2-4).query("size", 4).get();
		_remoteList = new ArrayList<InvitationModel>(invitationWC.getCollection(InvitationModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 4, _remoteList.size());
		
		// read over end of list
		invitationWC.resetQuery();
		_response = invitationWC.replacePath("/").query("position", _limit2-5).query("size", 10).get();
		_remoteList = new ArrayList<InvitationModel>(invitationWC.getCollection(InvitationModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// removing all test objects
		for (InvitationModel _c : _localList) {
			_response = invitationWC.replacePath("/").path(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}		
	}
}