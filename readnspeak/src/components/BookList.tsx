import React, { useEffect, useState } from 'react';
import BookListDetail from './BookListDetail';
import { Book } from '../types'; 

async function getBookData(): Promise<Book[]> {
  const res = await fetch(`http://localhost:3000/data/books.json`);
  const books: Book[] = await res.json();
  return books;
}

// 나중에 리뷰가 가장 많은 도서, 신간 도서, 추천 도서를 가져오는 로직 수정 후 아래 개별로 넣기

export const BookList = () => {
  const [books, setBooks] = useState<Book[] | null>(null); 
  const [loading, setLoading] = useState(true); 

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const fetchedBooks = await getBookData();
        setBooks(fetchedBooks);
        setLoading(false);
      } catch (error) {
        console.error("책 데이터를 가져오는 중 오류 발생:", error);
        setLoading(false);
      }
    };
    
    fetchBooks();
  }, []);

  if (loading) {
    return <div>로딩 중...</div>;
  }

  if (!books || books.length === 0) {
    return <div>책을 찾을 수 없습니다.</div>;
  }

  return (
    <div className="mt-16">
      {/* 추천 도서 */}
      <BookListDetail title="추천 도서" books={books} />

      {/* 리뷰가 가장 많은 도서 */}
      <BookListDetail title="리뷰가 가장 많은 도서" books={books} />

      {/* 신간 도서 */}
      <BookListDetail title="신간 도서" books={books} />
    </div>
  );
};
