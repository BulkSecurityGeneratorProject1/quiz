import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './possible-answer.reducer';
import { IPossibleAnswer } from 'app/shared/model/possible-answer.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPossibleAnswerProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class PossibleAnswer extends React.Component<IPossibleAnswerProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { possibleAnswerList, match } = this.props;
    return (
      <div>
        <h2 id="possible-answer-heading">
          Possible Answers
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Possible Answer
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Text</th>
                <th>Correct</th>
                <th>Question</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {possibleAnswerList.map((possibleAnswer, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${possibleAnswer.id}`} color="link" size="sm">
                      {possibleAnswer.id}
                    </Button>
                  </td>
                  <td>{possibleAnswer.text}</td>
                  <td>{possibleAnswer.correct ? 'true' : 'false'}</td>
                  <td>
                    {possibleAnswer.questionId ? <Link to={`question/${possibleAnswer.questionId}`}>{possibleAnswer.questionId}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${possibleAnswer.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${possibleAnswer.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${possibleAnswer.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ possibleAnswer }: IRootState) => ({
  possibleAnswerList: possibleAnswer.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PossibleAnswer);
