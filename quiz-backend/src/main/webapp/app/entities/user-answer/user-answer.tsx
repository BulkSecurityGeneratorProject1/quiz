import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './user-answer.reducer';
import { IUserAnswer } from 'app/shared/model/user-answer.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserAnswerProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class UserAnswer extends React.Component<IUserAnswerProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { userAnswerList, match } = this.props;
    return (
      <div>
        <h2 id="user-answer-heading">
          User Answers
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new User Answer
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Question</th>
                <th>Answer</th>
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userAnswerList.map((userAnswer, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${userAnswer.id}`} color="link" size="sm">
                      {userAnswer.id}
                    </Button>
                  </td>
                  <td>{userAnswer.questionId ? <Link to={`question/${userAnswer.questionId}`}>{userAnswer.questionId}</Link> : ''}</td>
                  <td>{userAnswer.answerId ? <Link to={`possible-answer/${userAnswer.answerId}`}>{userAnswer.answerId}</Link> : ''}</td>
                  <td>{userAnswer.userId ? userAnswer.userId : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${userAnswer.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userAnswer.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userAnswer.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ userAnswer }: IRootState) => ({
  userAnswerList: userAnswer.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserAnswer);
