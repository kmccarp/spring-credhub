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

package org.springframework.credhub.core.permissionV2;

import reactor.core.publisher.Mono;

import org.springframework.credhub.support.CredentialName;
import org.springframework.credhub.support.CredentialPermission;
import org.springframework.credhub.support.permissions.Actor;
import org.springframework.credhub.support.permissions.Permission;

/**
 * Specifies the interactions with CredHub to add, retrieve, and delete permissions.
 *
 * @author Scott Frederick
 * @author Alberto C. Ríos
 */
public interface ReactiveCredHubPermissionV2Operations {

	/**
	 * Get a permission.
	 * @param id the CredHub-assigned ID of the permission; must not be {@literal null}
	 * @return the details if the specified permission
	 */
	Mono<CredentialPermission> getPermissions(String id);

	/**
	 * Get a permission by path and actor.
	 * @param path the path of the credentials; must not be {@literal null}
	 * @param actor the actor of the credentials; must not be {@literal null}
	 * @return the details if the specified permission
	 * @since API 2.1
	 */
	Mono<CredentialPermission> getPermissionsByPathAndActor(CredentialName path, Actor actor);

	/**
	 * Add permissions to an credential path.
	 * @param path the path of the credentials; must not be {@literal null}
	 * @param permission a permission to add
	 * @return the details if the added permission
	 */
	Mono<CredentialPermission> addPermissions(CredentialName path, Permission permission);

	/**
	 * Add permissions to an existing credential.
	 * @param id the CredHub-assigned ID of the permission; must not be {@literal null}
	 * @param path the path of the credentials; must not be {@literal null}
	 * @param permission a permission to add
	 * @return the details if the added permission
	 */
	Mono<CredentialPermission> updatePermissions(String id, CredentialName path, Permission permission);

	/**
	 * Delete a permission.
	 * @param id the CredHub-assigned ID of the permission; must not be {@literal null}
	 * @return an empty {@code Mono}
	 */
	Mono<Void> deletePermission(String id);

}
