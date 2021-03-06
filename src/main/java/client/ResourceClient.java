/**
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *
 *  The original code are licensed under the GNU Lesser General Public License.
 */
package client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;

public class ResourceClient {

    private static final Logger log = Logger.getLogger(ResourceClient.class);

    private String resourceUrl;
    private String token;
    private WebResource resource;

    public ResourceClient(String resourceUrl, String token) {
        this.resourceUrl = resourceUrl;
        this.token = token;
        Client client = Client.create();
        this.resource = client.resource(resourceUrl);
    }

    public NifOutput request(NifInput input) {
        WebResource.Builder builder = this.resource.header("x-eurosentiment-token", this.token)
                                                   .header("content-type", "application/json");
        log.info("Request to Resources Server at [" + resourceUrl + "] with body: " + input);
        ClientResponse response = builder.post(ClientResponse.class, input.asJson());
        String result = response.getEntity(String.class);
        log.info("Response:" + result);
        return new NifOutput(result);
    }
}
