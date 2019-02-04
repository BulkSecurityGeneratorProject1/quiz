import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PossibleAnswer from './possible-answer';
import PossibleAnswerDetail from './possible-answer-detail';
import PossibleAnswerUpdate from './possible-answer-update';
import PossibleAnswerDeleteDialog from './possible-answer-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PossibleAnswerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PossibleAnswerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PossibleAnswerDetail} />
      <ErrorBoundaryRoute path={match.url} component={PossibleAnswer} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PossibleAnswerDeleteDialog} />
  </>
);

export default Routes;
