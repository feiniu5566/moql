/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.moql.operand.expression.array;

import org.moql.NumberConvertable;
import org.moql.operand.OperandContextLinkedList;
import org.moql.operand.OperandContextList;
import org.moql.util.StringFormater;

import java.util.Iterator;

/**
 * 
 * @author Tang Tadin
 *
 */
@SuppressWarnings("unchecked")
public class IteratorAccessor implements ArrayAccessor {

	@Override
	public Object getObject(Object array, Object index) {
		// TODO Auto-generated method stub
		Iterator<Object> it = ((Iterable<Object>)array).iterator();
		if (index instanceof Number) {
			return getObject(it, ((Number)index).intValue());
		}
		if (index.getClass().equals(String.class)) {
			return getObject(it, Integer.valueOf((String)index));
		}
		if (index instanceof NumberConvertable) {
			Number inx = ((NumberConvertable)index).toNumber();
			return getObject(it, inx.intValue());
		}
		throw new IllegalArgumentException(StringFormater.format("Unsupport 'index' of class '{}'!", index.getClass().getName()));
	}
	
	protected Object getObject(Iterator<Object> it, int index) {
		int i = 0;
		while(it.hasNext()) {
			Object o = it.next();
			if (i++ == index)
				return o;
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	public OperandContextList toOperandContextList(Object array) {
		// TODO Auto-generated method stub
		Iterator<Object> it = ((Iterable<Object>)array).iterator();
		OperandContextList ctxList = new OperandContextLinkedList();
		while(it.hasNext()) {
			ctxList.add(it.next());
		}
		return ctxList;
	}

}
