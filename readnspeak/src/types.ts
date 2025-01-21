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
