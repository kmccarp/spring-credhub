/*
 * Copyright 2016-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.credhub.support;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceInstanceCredentialNameUnitTests {

	@Test
	public void simpleNameIsConstructed() {
		CredentialName credentialName = new ServiceInstanceCredentialName("broker-name", "service-name", "binding-id",
				"credential-name");

		assertThat(credentialName.getName()).isEqualTo("/c/broker-name/service-name/binding-id/credential-name");
	}

}
