import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-answer.reducer';
import { IUserAnswer } from 'app/shared/model/user-answer.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserAnswerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UserAnswerDetail extends React.Component<IUserAnswerDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { userAnswerEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            UserAnswer [<b>{userAnswerEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>Question</dt>
            <dd>{userAnswerEntity.questionId ? userAnswerEntity.questionId : ''}</dd>
            <dt>Answer</dt>
            <dd>{userAnswerEntity.answerId ? userAnswerEntity.answerId : ''}</dd>
            <dt>User</dt>
            <dd>{userAnswerEntity.userId ? userAnswerEntity.userId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/user-answer" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/user-answer/${userAnswerEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ userAnswer }: IRootState) => ({
  userAnswerEntity: userAnswer.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserAnswerDetail);
