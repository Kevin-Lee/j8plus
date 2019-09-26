/**
 * Copyright 2014 Lee, Seong Hyun (Kevin)
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
package j8plus.recursion;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2014-06-14)
 *
 * @param <T> The type of the result from this tailcallable operation
 */
@FunctionalInterface
public interface TailCallable<T> {
  TailCallable<T> next();

  default boolean isDone() {
    return false;
  }

  default T result() {
    throw new RuntimeException("It does not have the result yet");
  }
}
