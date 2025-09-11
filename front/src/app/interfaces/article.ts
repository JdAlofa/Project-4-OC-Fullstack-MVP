import { Comment } from "./comment";

export interface Article {
    id: number;
    title: string;
    content: string;
    themeId: number;
    themeTitle: string;
    authorUsername: string;
    createdAt: string;
    comments: Comment[];
}