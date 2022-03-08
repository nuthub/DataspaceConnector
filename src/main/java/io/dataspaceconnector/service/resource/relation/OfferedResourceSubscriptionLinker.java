/*
 * Copyright 2020-2022 Fraunhofer Institute for Software and Systems Engineering
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.dataspaceconnector.service.resource.relation;

import io.dataspaceconnector.model.resource.OfferedResource;
import io.dataspaceconnector.model.subscription.Subscription;
import io.dataspaceconnector.service.resource.base.OwningRelationService;
import io.dataspaceconnector.service.resource.type.OfferedResourceService;
import io.dataspaceconnector.service.resource.type.SubscriptionService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles the relation between offered resources and subscriptions.
 */
@Service
@NoArgsConstructor
public class OfferedResourceSubscriptionLinker extends OwningRelationService<OfferedResource,
        Subscription, OfferedResourceService, SubscriptionService> {

    /**
     * Returns the list of subscriptions owned by a given offered resource.
     *
     * @param owner the offered resource whose subscriptions should be received.
     * @return the list of owned subscriptions.
     */
    @Override
    protected List<Subscription> getInternal(final OfferedResource owner) {
        return owner.getSubscriptions();
    }
}
