import React, { useState } from 'react';
import { FaAngleRight, FaAngleLeft } from 'react-icons/fa';  

const BookImages = [
  {
    id: 0,
    img_url: '/img/XL.jpeg',
    img_name: '소년이 온다',
    img_author: '한강'
  },
  {
    id: 1,
    img_url: '/img/XL.jpeg',
    img_name: '소년이 온다',
    img_author: '한강'
  },
  {
    id: 2,
    img_url: '/img/XL.jpeg',
    img_name: '소년이 온다',
    img_author: '한강'
  },
  {
    id: 3,
    img_url: '/img/XL.jpeg',
    img_name: '소년이 온다',
    img_author: '한강'
  },
  {
    id: 4,
    img_url: '/img/han.png',
    img_name: '채식주의자',
    img_author: '한강'
  },
  {
    id: 5,
    img_url: '/img/han.png',
    img_name: '채식주의자',
    img_author: '한강'
  },
  {
    id: 6,
    img_url: '/img/han.png',
    img_name: '채식주의자',
    img_author: '한강'
  },
  {
    id: 7,
    img_url: '/img/han.png',
    img_name: '채식주의자',
    img_author: '한강'
  }
];

export const BookList = () => {
  const [currentIndex, setCurrentIndex] = useState(0); 
  const itemsPerPage = 4; 

  const displayedBooks = BookImages.slice(currentIndex, currentIndex + itemsPerPage);

  // 다음
  const handlerNext = () => {
    setCurrentIndex((prevIndex) => 
      prevIndex + itemsPerPage >= BookImages.length ? 0 : prevIndex + itemsPerPage
    );
  };

  // 이전
  const handlerPrevious = () => {
    setCurrentIndex((prevIndex) =>
      prevIndex - itemsPerPage < 0 ? BookImages.length - itemsPerPage : prevIndex - itemsPerPage
    );
  };

  return (
    <div className='mt-16'>
      <div>
        <span className='font-bold text-xl'>추천 도서</span>
        <div className='bg-white py-8 rounded-3xl mt-5 shadow-xl flex items-center justify-between'>
          
          {/* "이전" 버튼 */}
          <button 
            onClick={handlerPrevious} 
            className="p-2 rounded-full"
          >
            <FaAngleLeft size={24} />
          </button>

          <div className="flex gap-10">
            {displayedBooks.map((book) => (
              <div key={book.id} className="flex-shrink-0">
                <img
                  src={book.img_url} 
                  alt={book.img_name} 
                  className="md:w-32 lg:w-48 rounded-md shadow-xl"
                />
                <div>
                  <h3 className='font-bold mt-2 text-lg'>{book.img_name}</h3>
                  <p className='text-gray-500'>{book.img_author}</p>
                </div>
              </div>
            ))}
          </div>

          <button 
            onClick={handlerNext} 
            className="p-2 rounded-full"
          >
            <FaAngleRight size={24} />
          </button>
        </div>
      </div>

      <div className='mt-12'>
        <span className='font-bold text-xl'>리뷰가 가장 많은 도서</span>
        <div className='bg-white py-8 rounded-3xl mt-5 shadow-xl flex items-center justify-between'>
          
          {/* "이전" 버튼 */}
          <button 
            onClick={handlerPrevious} 
            className="p-2 rounded-full"
          >
            <FaAngleLeft size={24} />
          </button>

          <div className="flex gap-10">
            {displayedBooks.map((book) => (
              <div key={book.id} className="flex-shrink-0">
                <img
                  src={book.img_url} 
                  alt={book.img_name} 
                  className="md:w-32 lg:w-48 rounded-md  shadow-xl"
                />
                <div>
                  <h3 className='font-bold mt-2 text-lg'>{book.img_name}</h3>
                  <p className='text-gray-500'>{book.img_author}</p>
                </div>
              </div>
            ))}
          </div>

          <button 
            onClick={handlerNext} 
            className="p-2 rounded-full"
          >
            <FaAngleRight size={24} />
          </button>
        </div>
      </div>

<div className='mt-12 mb-12'>
        <span className='font-bold text-xl'>신간 도서</span>
        <div className='bg-white py-8 rounded-3xl mt-5 shadow-xl flex items-center justify-between'>
          
          {/* "이전" 버튼 */}
          <button 
            onClick={handlerPrevious} 
            className="p-2 rounded-full"
          >
            <FaAngleLeft size={24} />
          </button>

          <div className="flex gap-10">
            {displayedBooks.map((book) => (
              <div key={book.id} className="flex-shrink-0">
                <img
                  src={book.img_url} 
                  alt={book.img_name} 
                  className="md:w-32 lg:w-48 rounded-md  shadow-xl"
                />
                <div>
                  <h3 className='font-bold mt-2 text-lg'>{book.img_name}</h3>
                  <p className='text-gray-500'>{book.img_author}</p>
                </div>
              </div>
            ))}
          </div>

          <button 
            onClick={handlerNext} 
            className="p-2 rounded-full"
          >
            <FaAngleRight size={24} />
          </button>
        </div>
      </div>

    </div>
  );
};
