import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Question from './question';
import UserAnswer from './user-answer';
import PossibleAnswer from './possible-answer';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/question`} component={Question} />
      <ErrorBoundaryRoute path={`${match.url}/user-answer`} component={UserAnswer} />
      <ErrorBoundaryRoute path={`${match.url}/possible-answer`} component={PossibleAnswer} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
