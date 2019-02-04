import { IPossibleAnswer } from 'app/shared/model/possible-answer.model';

export const enum QuestionType {
  TEXT = 'TEXT',
  IMAGE = 'IMAGE',
  VIDEO = 'VIDEO'
}

export const enum AnswerType {
  SINGLE = 'SINGLE',
  MULTIPLE = 'MULTIPLE',
  TEXT = 'TEXT'
}

export interface IQuestion {
  id?: number;
  questionType?: QuestionType;
  answerType?: AnswerType;
  text?: string;
  imageContentType?: string;
  image?: any;
  answers?: IPossibleAnswer[];
}

export const defaultValue: Readonly<IQuestion> = {};
