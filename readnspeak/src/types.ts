export interface Book {
  book_id: number;
  isbn: string;
  title: string;
  author: string;
  publisher: string;
  publication_date: string;
  cover_image: string;
  category: string;
  language: string;
  page_count: number;
  created_at: string;
  rating : string;
  comments : string;
  description : string;
}
export interface Review {
  review_id : number;
  user_id: number;
  book_id: number;
  content: string;
  rating: number;
  is_spoiler: boolean;
  helpful_count: number;
  created_at: string;
  updated_at: string;
}

export interface User {
  user_id: number;
  username: string;
  email: string;
  password_hash: string;
  profile_picture: string;
  bio: string;
  last_login: string;
  role: string;
  created_at: string;
}

export interface Comment {
  comment_id: number;
  review_id: number;
  user_id: number;
  content: string;
  reply_to: number | null;
  is_flagged: boolean;
  created_at: string;
  updated_at: string;
}

export interface Like {
  like_id: number;
  user_id: number;
  review_id: number;
  reaction_time: string;
}

export interface Bookmark {
  bookmark_id: number;
  user_id: number;
  book_id: number;
  created_at: string;
}

export interface Follow {
  follow_id: number;
  follower_id: number;
  following_id: number;
  created_at: string;
}
