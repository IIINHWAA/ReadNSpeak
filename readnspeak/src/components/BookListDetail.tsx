import React, { useState } from 'react'
import { FaAngleRight, FaAngleLeft } from 'react-icons/fa'
import Image from 'next/image'
import { Book } from '../types'

interface BookListProps {
  title: string
  books: Book[]
}

const BookListDetail: React.FC<BookListProps> = ({ title, books }) => {
  const [currentIndex, setCurrentIndex] = useState(0)
  const itemsPerPage = 4

  const displayedBooks = books.slice(currentIndex, currentIndex + itemsPerPage)

  const handlerNext = () => {
    setCurrentIndex((prevIndex) =>
      prevIndex + itemsPerPage >= books.length ? 0 : prevIndex + itemsPerPage,
    )
  }

  const handlerPrevious = () => {
    setCurrentIndex((prevIndex) =>
      prevIndex - itemsPerPage < 0
        ? books.length - itemsPerPage
        : prevIndex - itemsPerPage,
    )
  }

  return (
    <div className="mt-12">
      <span className="font-bold text-xl">{title}</span>
      <div className="bg-white py-8 rounded-3xl mt-2 shadow-xl flex items-center">
        <button onClick={handlerPrevious} className="p-2 rounded-full">
          <FaAngleLeft size={24} />
        </button>

        <div className="grid grid-cols-4 gap-10 w-full">
          {displayedBooks.map((book) => (
            <div key={book.book_id} className="flex flex-col items-start">
              <Image
                src={book.cover_image} 
                alt={book.title} 
                width={1740} 
                height={1000} 
                layout="responsive" 
                className="w-full h-auto rounded-md shadow-xl"
              />
              <div className="mt-2 w-full">
                <h3 className="font-bold text-lg text-left">{book.title}</h3>
                <p className="text-gray-500 text-left">{book.author}</p>
              </div>
            </div>
          ))}
        </div>

        <button onClick={handlerNext} className="p-2 rounded-full">
          <FaAngleRight size={24} />
        </button>
      </div>
    </div>
  )
}

export default BookListDetail
