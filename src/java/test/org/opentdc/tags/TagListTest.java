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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.tags.SingleLangTag;
import org.opentdc.tags.TagModel;
import org.opentdc.tags.TagsService;
import org.opentdc.util.LanguageCode;
import org.opentdc.service.GenericService;
import org.opentdc.service.LocalizedTextModel;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

public class TagListTest extends AbstractTestClient {
	private WebClient wc = null;

	@Before
	public void initializeTests() {
		wc = createWebClient(ServiceUtil.TAGS_API_URL, TagsService.class);
	}

	@After
	public void cleanupTest() {
		wc.close();
	}
	

	@Test
	public void testTagBatchedList() {
		ArrayList<TagModel> _localList = new ArrayList<TagModel>();		
		System.out.println("***** testTagBatchedList:");
		wc.replacePath("/");
		// we want to allocate more than double the amount of default list size objects
		int _batchSize = GenericService.DEF_SIZE;
		int _increment = 5;
		int _limit2 = 2 * _batchSize + _increment;		// if DEF_SIZE == 25 -> _limit2 = 55
		TagModel _model1 = null;
		for (int i = 0; i < _limit2; i++) {
			_model1 = TagTest.create(wc, Status.OK);
			_localList.add(_model1);
			LocalizedTextTest.post(wc, _model1, new LocalizedTextModel(LanguageCode.ES, "testTagBatchedList" + i), Status.OK);
			System.out.println("posted TagsModel " + _model1.getId() + " with LocalizedText <testTagBatchedList" + i + ">");
		}
		assertEquals("testcase should create the right amount of Tags", _limit2, _localList.size());

		// get first batch
		// list(position=0, size=25) -> elements 0 .. 24
		List<SingleLangTag> _remoteList1 = TagTest.list(wc, null, -1, -1, Status.OK);
		System.out.println("****** 1st Batch:");
		for (SingleLangTag _model : _remoteList1) {
			System.out.println(_model.getTagId());
		}
		assertEquals("size of lists should be the same", _batchSize, _remoteList1.size());
			
		// get second batch
		// list(position=25, size=25) -> elements 25 .. 49
		List<SingleLangTag> _remoteList2 = TagTest.list(wc, null, 25, -1, Status.OK);
		assertEquals("size of lists should be the same", _batchSize, _remoteList2.size());
		System.out.println("****** 2nd Batch:");
		for (SingleLangTag _model : _remoteList2) {
			System.out.println(_model.getTagId());
		}
		
		// get rest 
		// list(position=50, size=25) ->   elements 50 .. 54
		List<SingleLangTag> _remoteList3 = TagTest.list(wc, null, 50, _increment, Status.OK);
		System.out.println("****** 3rd Batch:");
		for (SingleLangTag _model : _remoteList3) {
			System.out.println(_model.getTagId());
		}
		assertEquals("size of lists should be the same", _increment, _remoteList3.size());
		
		// testing the batches
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
		List<SingleLangTag> _remoteList = null;
		while(true) {
			_numberOfBatches++;
			_remoteList = TagTest.list(wc, null, _position, -1, Status.OK);
			_numberOfReturnedObjects += _remoteList.size();
			System.out.println("batch " + _numberOfBatches + ": position=" + _position + ", returnedObjects=" + _numberOfReturnedObjects);
			if (_remoteList.size() < GenericService.DEF_SIZE) {
				break;
			} else {
				_position += GenericService.DEF_SIZE;					
			}
		}
		assertTrue("number of batches should be as expected", _numberOfBatches >= 3);
		assertTrue("should have returned all objects", _numberOfReturnedObjects >= _limit2);
		assertTrue("last batch size should be as expected", _remoteList.size() >= _increment);
	
		// testing some explicit positions and sizes
		_remoteList = TagTest.list(wc, null, 5, 5, Status.OK);  // get next 5 elements from position 5
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		_remoteList = TagTest.list(wc, null, _limit2-4, 4, Status.OK);  // get last 4 elements
		assertEquals("list() should return correct number of elements", 4, _remoteList.size());
		
		_remoteList = TagTest.list(wc, null, _limit2-5, 10, Status.OK);  // read over end of list
		assertTrue("list() should return correct number of elements", _remoteList.size() >= 5);
		
		// removing all test objects
		for (TagModel _model : _localList) {
			TagTest.delete(wc, _model.getId(), Status.NO_CONTENT);
		}		
	}
	
	protected int calculateMembers() {
		return 0;
	}
}