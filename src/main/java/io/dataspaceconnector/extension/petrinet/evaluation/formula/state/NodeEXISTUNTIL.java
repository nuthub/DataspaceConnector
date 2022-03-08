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
package io.dataspaceconnector.extension.petrinet.evaluation.formula.state;

import io.dataspaceconnector.extension.petrinet.model.Node;
import io.dataspaceconnector.extension.petrinet.model.Place;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Evaluates to true, if a path exists, where parameter1 evaluates to true for every place,
 * until parameter2 evaluates to true.
 */
@AllArgsConstructor
public class NodeEXISTUNTIL implements StateFormula {
    /**
     * Parameter which needs to be evaluated to true every time.
     */
    private StateFormula parameter1;

    /**
     * Parameter which ends the evaluation by turning true.
     */
    private StateFormula parameter2;

    /**
     * Node representing the formula which evaluates to true, if a path exists, where parameter1
     * evaluates to true for every place, until parameter2 evaluates to true.
     * @param parameter1 Parameter which needs to be evaluated to true every time.
     * @param parameter2 Parameter which ends the evaluation by turning true.
     * @return Node representing the formula.
     */
    public static NodeEXISTUNTIL nodeEXISTUNTIL(final StateFormula parameter1,
                                                final StateFormula parameter2) {
        return new NodeEXISTUNTIL(parameter1, parameter2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // True if a path exists, where parameter1 is true on each node of the path,
    // and parameter2 is true on the final node of the path
    //TODO fix evaluation: use filtered paths
    public boolean evaluate(final Node node, final List<List<Node>> paths) {
        if (!(node instanceof Place)) {
            return false;
        }

        check: for (final var path: paths) {
            int offset;

            if (!path.get(0).equals(node)) {
                continue;
            }

            if (path.size() % 2 == 1) {
                offset = 1;
            } else {
                offset = 2;
            }

            for (var i = 2; i < path.size() - offset; i += 2) {
                final var res1 = parameter1.evaluate(path.get(i), paths);
                final var res2 = parameter2.evaluate(path.get(i), paths);
                if (res2) {
                    return true;
                }
                if (!res1) {
                    continue check;
                }
            }

            if (parameter2.evaluate(path.get(path.size() - offset), paths)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "EXIST_UNTIL";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String writeFormula() {
        return String.format("%s(%s, %s)",
                symbol(),
                parameter1.writeFormula(),
                parameter2.writeFormula());
    }
}
