import React from 'react'
import BookListDetail from './BookListDetail'

const BookImages = [
  {
    id: 0,
    img_url: '/img/XL.jpeg',
    img_name: '소년이 온다',
    img_author: '한강',
  },
  {
    id: 1,
    img_url: '/img/XL.jpeg',
    img_name: '소년이 온다',
    img_author: '한강',
  },
  {
    id: 2,
    img_url: '/img/XL.jpeg',
    img_name: '소년이 온다',
    img_author: '한강',
  },
  {
    id: 3,
    img_url: '/img/XL.jpeg',
    img_name: '소년이 온다',
    img_author: '한강',
  },
  {
    id: 4,
    img_url: '/img/han.png',
    img_name: '채식주의자',
    img_author: '한강',
  },
  {
    id: 5,
    img_url: '/img/han.png',
    img_name: '채식주의자',
    img_author: '한강',
  },
  {
    id: 6,
    img_url: '/img/han.png',
    img_name: '채식주의자',
    img_author: '한강',
  },
  {
    id: 7,
    img_url: '/img/han.png',
    img_name: '채식주의자',
    img_author: '한강',
  },
]

export const BookList = () => {
  return (
    <div className="mt-16">
      {/* 추천 도서 */}
      <BookListDetail title="추천 도서" books={BookImages} />

      {/* 리뷰가 가장 많은 도서 */}
      <BookListDetail title="리뷰가 가장 많은 도서" books={BookImages} />

      {/* 신간 도서 */}
      <BookListDetail title="신간 도서" books={BookImages} />
    </div>
  )
}
