/*
 * Copyright (c) 2011-2021 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package io.vertx.core.net.impl.pool;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.impl.EventLoopContext;
import io.vertx.core.net.impl.clientconnection.Lease;

/**
 * A waiter for a connection.
 */
public class PoolWaiter<C> {

  static final Listener NULL_LISTENER = new Listener() {
  };

  /**
   * An interface notifying the connection borrower of the lifecycle of a waiter.
   */
  public interface Listener<C> {

    /**
     * The waiter is moved to the pool wait queue.
     *
     * @param waiter the waiter
     */
    default void onEnqueue(PoolWaiter<C> waiter) {
    }

    /**
     * The waiter is associated with a connection request.
     *
     * @param waiter the waiter
     */
    default void onConnect(PoolWaiter<C> waiter) {
    }
  }

  final PoolWaiter.Listener<C> listener;
  final EventLoopContext context;
  final int weight;
  final Handler<AsyncResult<Lease<C>>> handler;
  PoolWaiter<C> prev;
  PoolWaiter<C> next;
  boolean done;

  PoolWaiter(PoolWaiter.Listener<C> listener, EventLoopContext context, final int weight, Handler<AsyncResult<Lease<C>>> handler) {
    this.listener = listener;
    this.context = context;
    this.weight = weight;
    this.handler = handler;
  }
}
