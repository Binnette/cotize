package net.bons.comptes.service;

/* Licence Public Barmic
 * copyright 2014-2016 Michel Barret <michel.barret@gmail.com>
 */

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import net.bons.comptes.service.model.DecisionProjectionProject;

@ProxyGen
@VertxGen
public interface EventStore {
  // The service methods
  void loadEvents(String projectId, Handler<AsyncResult<DecisionProjectionProject>> resultHandler);
//  void saveEvent(ProjectCreated event, Handler<AsyncResult<String>> resultHandler);
}
