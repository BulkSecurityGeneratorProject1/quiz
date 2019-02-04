import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserAnswer, defaultValue } from 'app/shared/model/user-answer.model';

export const ACTION_TYPES = {
  FETCH_USERANSWER_LIST: 'userAnswer/FETCH_USERANSWER_LIST',
  FETCH_USERANSWER: 'userAnswer/FETCH_USERANSWER',
  CREATE_USERANSWER: 'userAnswer/CREATE_USERANSWER',
  UPDATE_USERANSWER: 'userAnswer/UPDATE_USERANSWER',
  DELETE_USERANSWER: 'userAnswer/DELETE_USERANSWER',
  RESET: 'userAnswer/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserAnswer>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type UserAnswerState = Readonly<typeof initialState>;

// Reducer

export default (state: UserAnswerState = initialState, action): UserAnswerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERANSWER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERANSWER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_USERANSWER):
    case REQUEST(ACTION_TYPES.UPDATE_USERANSWER):
    case REQUEST(ACTION_TYPES.DELETE_USERANSWER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_USERANSWER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERANSWER):
    case FAILURE(ACTION_TYPES.CREATE_USERANSWER):
    case FAILURE(ACTION_TYPES.UPDATE_USERANSWER):
    case FAILURE(ACTION_TYPES.DELETE_USERANSWER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERANSWER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERANSWER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERANSWER):
    case SUCCESS(ACTION_TYPES.UPDATE_USERANSWER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERANSWER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/user-answers';

// Actions

export const getEntities: ICrudGetAllAction<IUserAnswer> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_USERANSWER_LIST,
  payload: axios.get<IUserAnswer>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IUserAnswer> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERANSWER,
    payload: axios.get<IUserAnswer>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUserAnswer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERANSWER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserAnswer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERANSWER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserAnswer> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERANSWER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
