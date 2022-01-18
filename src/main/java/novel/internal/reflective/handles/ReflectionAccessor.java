/*
 * Copyright (C) 2017 The Gson authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package novel.internal.reflective.handles;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;


/**
 * Provides a replacement for {@link AccessibleObject#setAccessible(boolean)}, which may be used to
 * avoid reflective access issues appeared in Java 9, like {@link java.lang.reflect.InaccessibleObjectException}
 * thrown or warnings like
 * <pre>
 *   WARNING: An illegal reflective access operation has occurred
 *   WARNING: Illegal reflective access by ...
 * </pre>
 * <p/>
 * Works both for Java 9 and earlier Java versions.
 */
public abstract class ReflectionAccessor {

	/**
	 * Does the same as {@code ao.setAccessible(true)}, but never throws
	 * {@link java.lang.reflect.InaccessibleObjectException}
	 */
	public abstract void makeAccessible(Field field);

}