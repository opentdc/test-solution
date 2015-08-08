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
package test.org.opentdc.gifts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.gifts.GiftModel;
import org.opentdc.service.GenericService;
import org.opentdc.gifts.GiftsService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/*
 * Tests for batched listing of gifts
 * @author Bruno Kaiser
 */
public class GiftsBatchedListTest extends AbstractTestClient {
	private WebClient giftWC = null;

	@Before
	public void initializeTests() {
		giftWC = initializeTest(ServiceUtil.GIFTS_API_URL, GiftsService.class);
	}

	@After
	public void cleanupTest() {
		giftWC.close();
	}

	@Test
	public void testGiftBatchedList() {
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
		Response _response = null;
		ArrayList<GiftModel> _localList = new ArrayList<GiftModel>();		
		System.out.println("***** testGiftBatchedList:");
		giftWC.replacePath("/");
		// we want to allocate more than double the amount of default list size objects
		int _batchSize = GenericService.DEF_SIZE;
		int _increment = 5;
		int _limit2 = 2 * _batchSize + _increment;		// if DEF_SIZE == 25 -> _limit2 = 55
		GiftModel _res = null;
		for (int i = 0; i < _limit2; i++) {
			// create(new()) -> _localList
			_res = new GiftModel();
			_res.setTitle(String.format("%2d", i));
			_response = giftWC.post(_res);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(GiftModel.class));
			System.out.println("posted GiftModel " + _res.getTitle());
		}
		System.out.println("****** locallist:");
		for (GiftModel _rm : _localList) {
			System.out.println(_rm.getTitle());
		}

		List<GiftModel> _remoteList = null;
		// printCounters("testGiftBatchedList");
		while(true) {
			_numberOfBatches++;
			giftWC.resetQuery();
			_response = giftWC.replacePath("/").query("position", _position).get();
			_remoteList = new ArrayList<GiftModel>(giftWC.getCollection(GiftModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_numberOfReturnedObjects += _remoteList.size();
			System.out.println("batch " + _numberOfBatches + ": position=" + _position + ", returnedObjects=" + _numberOfReturnedObjects);
			if (_remoteList.size() < GenericService.DEF_SIZE) {
				break;
			} else {
				_position += GenericService.DEF_SIZE;					
			}
		}
		validateBatches(_numberOfBatches, _numberOfReturnedObjects, _remoteList.size());
	
		// testing some explicit positions and sizes
		giftWC.resetQuery();
		// get next 5 elements from position 5
		_response = giftWC.replacePath("/").query("position", 5).query("size", 5).get();
		_remoteList = new ArrayList<GiftModel>(giftWC.getCollection(GiftModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// get last 4 elements 
		giftWC.resetQuery();
		_response = giftWC.replacePath("/").query("position", _limit2-4).query("size", 4).get();
		_remoteList = new ArrayList<GiftModel>(giftWC.getCollection(GiftModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 4, _remoteList.size());
		
		// read over end of list
		giftWC.resetQuery();
		_response = giftWC.replacePath("/").query("position", _limit2-5).query("size", 10).get();
		_remoteList = new ArrayList<GiftModel>(giftWC.getCollection(GiftModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// removing all test objects
		for (GiftModel _c : _localList) {
			_response = giftWC.replacePath("/").path(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}		
	}
	
	protected int calculateMembers() {
		return 2 * GenericService.DEF_SIZE + 5;
	}
}