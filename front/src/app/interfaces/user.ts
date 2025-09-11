import { Theme } from './theme';

export interface User {
  id: number;
  username: string;
  email: string;
  themes: Theme[];
}
