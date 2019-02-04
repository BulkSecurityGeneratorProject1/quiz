export interface IPossibleAnswer {
  id?: number;
  text?: string;
  correct?: boolean;
  questionId?: number;
}

export const defaultValue: Readonly<IPossibleAnswer> = {
  correct: false
};
