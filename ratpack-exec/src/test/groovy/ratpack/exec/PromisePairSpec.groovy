/*
 * Copyright 2016 the original author or authors.
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

package ratpack.exec

import ratpack.func.Pair
import ratpack.test.exec.ExecHarness
import ratpack.test.internal.BaseRatpackSpec
import spock.lang.AutoCleanup

class PromisePairSpec extends BaseRatpackSpec {

  @AutoCleanup
  ExecHarness harness = ExecHarness.harness()

  def "can push left and right"() {
    expect:
    harness.yield { Promise.value(1).left { 1 + 1 } }.valueOrThrow == Pair.of(2, 1)
    harness.yield { Promise.value(1).right { 1 + 1 } }.valueOrThrow == Pair.of(1, 2)
    harness.yield { Promise.value(1).flatLeft { Promise.sync { 1 + 1 } } }.valueOrThrow == Pair.of(2, 1)
    harness.yield { Promise.value(1).flatRight { Promise.sync { 1 + 1 } } }.valueOrThrow == Pair.of(1, 2)
  }
}
