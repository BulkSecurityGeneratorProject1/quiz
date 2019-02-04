export interface IUserAnswer {
  id?: number;
  questionId?: number;
  answerId?: number;
  userId?: number;
}

export const defaultValue: Readonly<IUserAnswer> = {};
