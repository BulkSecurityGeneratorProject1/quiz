import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPossibleAnswer, defaultValue } from 'app/shared/model/possible-answer.model';

export const ACTION_TYPES = {
  FETCH_POSSIBLEANSWER_LIST: 'possibleAnswer/FETCH_POSSIBLEANSWER_LIST',
  FETCH_POSSIBLEANSWER: 'possibleAnswer/FETCH_POSSIBLEANSWER',
  CREATE_POSSIBLEANSWER: 'possibleAnswer/CREATE_POSSIBLEANSWER',
  UPDATE_POSSIBLEANSWER: 'possibleAnswer/UPDATE_POSSIBLEANSWER',
  DELETE_POSSIBLEANSWER: 'possibleAnswer/DELETE_POSSIBLEANSWER',
  RESET: 'possibleAnswer/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPossibleAnswer>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PossibleAnswerState = Readonly<typeof initialState>;

// Reducer

export default (state: PossibleAnswerState = initialState, action): PossibleAnswerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_POSSIBLEANSWER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_POSSIBLEANSWER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_POSSIBLEANSWER):
    case REQUEST(ACTION_TYPES.UPDATE_POSSIBLEANSWER):
    case REQUEST(ACTION_TYPES.DELETE_POSSIBLEANSWER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_POSSIBLEANSWER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_POSSIBLEANSWER):
    case FAILURE(ACTION_TYPES.CREATE_POSSIBLEANSWER):
    case FAILURE(ACTION_TYPES.UPDATE_POSSIBLEANSWER):
    case FAILURE(ACTION_TYPES.DELETE_POSSIBLEANSWER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_POSSIBLEANSWER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_POSSIBLEANSWER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_POSSIBLEANSWER):
    case SUCCESS(ACTION_TYPES.UPDATE_POSSIBLEANSWER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_POSSIBLEANSWER):
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

const apiUrl = 'api/possible-answers';

// Actions

export const getEntities: ICrudGetAllAction<IPossibleAnswer> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_POSSIBLEANSWER_LIST,
  payload: axios.get<IPossibleAnswer>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPossibleAnswer> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_POSSIBLEANSWER,
    payload: axios.get<IPossibleAnswer>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPossibleAnswer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_POSSIBLEANSWER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPossibleAnswer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_POSSIBLEANSWER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPossibleAnswer> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_POSSIBLEANSWER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
