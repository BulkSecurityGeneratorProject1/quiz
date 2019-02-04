import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserAnswer from './user-answer';
import UserAnswerDetail from './user-answer-detail';
import UserAnswerUpdate from './user-answer-update';
import UserAnswerDeleteDialog from './user-answer-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserAnswerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserAnswerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserAnswerDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserAnswer} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UserAnswerDeleteDialog} />
  </>
);

export default Routes;
