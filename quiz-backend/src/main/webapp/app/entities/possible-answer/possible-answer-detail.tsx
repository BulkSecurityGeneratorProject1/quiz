import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './possible-answer.reducer';
import { IPossibleAnswer } from 'app/shared/model/possible-answer.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPossibleAnswerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PossibleAnswerDetail extends React.Component<IPossibleAnswerDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { possibleAnswerEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            PossibleAnswer [<b>{possibleAnswerEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="text">Text</span>
            </dt>
            <dd>{possibleAnswerEntity.text}</dd>
            <dt>
              <span id="correct">Correct</span>
            </dt>
            <dd>{possibleAnswerEntity.correct ? 'true' : 'false'}</dd>
            <dt>Question</dt>
            <dd>{possibleAnswerEntity.questionId ? possibleAnswerEntity.questionId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/possible-answer" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/possible-answer/${possibleAnswerEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ possibleAnswer }: IRootState) => ({
  possibleAnswerEntity: possibleAnswer.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PossibleAnswerDetail);
