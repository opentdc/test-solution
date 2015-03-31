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

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.opentdc.wtt.CompanyModel;

public class CompanyClient {
	public static final String APP_URI = "http://localhost:8080/wtt/api/company/";	
	private int status = 0;
	
	public CompanyClient() {
		// System.out.println("> CompanyClient() (constructor)");
	}
	
	public int getStatus() {
		return status;
	}

	public List<CompanyModel> listCompanies() {
		// System.out.println("> listCompanies()");
		WebClient _wc = null;
		try {
			_wc = WttTest.initWebClient(APP_URI);
			Response _r = _wc.get();
			status = _r.getStatus();
			if (status == Status.OK.getStatusCode()) {
				return new ArrayList<CompanyModel>(_wc.getCollection(CompanyModel.class));
			}
			else {
				return new ArrayList<CompanyModel>();
			}
		}
		finally {
			_wc.close();
		}
	}

	public CompanyModel createCompany(CompanyModel c) {
		// System.out.println("> createCompany(" + c.getId() + ")");
		WebClient _wc = null;
		try {
			_wc = WttTest.initWebClient(APP_URI);
			Response _r = _wc.post(c);
			status = _r.getStatus();
			if (status == Status.OK.getStatusCode()) {
				return _r.readEntity(CompanyModel.class);
			}
			else {
				return null;
			}
		}
		finally {
			_wc.close();
		}
	}

	public CompanyModel readCompany(String id) {
		// System.out.println("> readCompany(" + id + ")");
		WebClient _wc = null;
		try {
			_wc = WttTest.initWebClient(APP_URI);
			Response _r = _wc.path(id).get();
			status = _r.getStatus();
			if (status == Status.OK.getStatusCode()) {
				return _r.readEntity(CompanyModel.class);
			}
			else {
				return null;
			}
		}
		finally {
			_wc.close();
		}
	}

	public CompanyModel updateCompany(CompanyModel c) {
		// System.out.println("> updateCompany()");
		WebClient _wc = null;
		try {
			_wc = WttTest.initWebClient(APP_URI);
			Response _r = _wc.put(c);
			status = _r.getStatus();
			if (status == Status.OK.getStatusCode()) {
				return _r.readEntity(CompanyModel.class);
			}
			else {
				return null;
			}
		}
		finally {
			_wc.close();
		}
	}

	public int deleteCompany(String id) {
		// System.out.println("> deleteCompany(" + id + ")");
		WebClient _wc = null;
		try {
			_wc = WttTest.initWebClient(APP_URI);
			Response _r = _wc.path(id).delete();
			status = _r.getStatus();
			return status;
		}
		finally {
			_wc.close();
		}
	}

	public int countCompanies() {
		// System.out.println("> countCompanies()");
		String _countStr = WttTest.initWebClient(APP_URI).path("count").get().readEntity(String.class);
		// System.out.println("countCompanies (stringified): " + _countStr);
		return new Integer(_countStr).intValue();
	}

}
