/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.credhub.core;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.credhub.support.CredentialPermissions;
import org.springframework.credhub.support.SimpleCredentialName;
import org.springframework.credhub.support.permissions.Actor;
import org.springframework.credhub.support.permissions.ActorType;
import org.springframework.credhub.support.permissions.CredentialPermission;
import org.springframework.credhub.support.permissions.Operation;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.credhub.core.CredHubPermissionsTemplate.PERMISSIONS_ACTOR_URL_QUERY;
import static org.springframework.credhub.core.CredHubPermissionsTemplate.PERMISSIONS_URL_PATH;
import static org.springframework.credhub.core.CredHubPermissionsTemplate.PERMISSIONS_URL_QUERY;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class CredHubPermissionsTemplateUnitTests {
	private static final SimpleCredentialName NAME = new SimpleCredentialName("example", "credential");

	@Mock
	private RestTemplate restTemplate;

	private CredHubPermissionsOperations credHubTemplate;

	@Before
	public void setUp() {
		credHubTemplate = new CredHubTemplate(restTemplate).permissions();
	}

	@Test
	public void getPermissions() {
		CredentialPermissions expectedResponse = new CredentialPermissions(
				NAME,
				CredentialPermission.builder()
						.app("app-id")
						.operation(Operation.READ)
						.operation(Operation.WRITE)
				.build(),
				CredentialPermission.builder()
						.user("zone1", "user-id")
						.operations(Operation.READ_ACL)
						.operations(Operation.WRITE_ACL)
						.operation(Operation.DELETE)
				.build()
		);

		when(restTemplate.getForEntity(PERMISSIONS_URL_QUERY, CredentialPermissions.class, NAME.getName()))
				.thenReturn(new ResponseEntity<>(expectedResponse, OK));

		List<CredentialPermission> response = credHubTemplate.getPermissions(NAME);

		assertNotNull(response);
		assertThat(response.size(), equalTo(expectedResponse.getPermissions().size()));
		assertThat(response, equalTo(expectedResponse.getPermissions()));
	}

	@Test
	public void addPermissions() {
		CredentialPermission permission1 = CredentialPermission.builder()
				.app("app-id")
				.operation(Operation.READ)
				.operation(Operation.WRITE)
				.build();
		
		CredentialPermission permission2 = CredentialPermission.builder()
				.user("zone1", "user-id")
				.operations(Operation.READ_ACL)
				.operations(Operation.WRITE_ACL)
				.operation(Operation.DELETE)
				.build();

		CredentialPermissions expectedResponse = new CredentialPermissions(NAME, permission1, permission2);

		when(restTemplate.exchange(PERMISSIONS_URL_PATH, POST,
				new HttpEntity<>(expectedResponse), CredentialPermissions.class))
				.thenReturn(new ResponseEntity<>(expectedResponse, OK));

		List<CredentialPermission> response = credHubTemplate.addPermissions(NAME, permission1, permission2);

		assertNotNull(response);
		assertThat(response.size(), equalTo(expectedResponse.getPermissions().size()));
		assertThat(response, equalTo(expectedResponse.getPermissions()));
	}

	@Test
	public void deletePermission() {
		credHubTemplate.deletePermission(NAME, Actor.app("appid1"));

		verify(restTemplate).delete(PERMISSIONS_ACTOR_URL_QUERY, NAME.getName(), ActorType.APP + ":appid1");
	}
}